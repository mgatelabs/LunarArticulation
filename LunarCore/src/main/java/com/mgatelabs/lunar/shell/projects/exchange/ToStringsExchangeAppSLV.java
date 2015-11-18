package com.mgatelabs.lunar.shell.projects.exchange;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mgatelabs.lunar.Application;
import com.mgatelabs.lunar.model.entities.*;
import com.mgatelabs.lunar.utils.AbstractAppSLV;
import com.mgatelabs.lunar.utils.Closer;
import com.mgatelabs.lunar.utils.CommitTransaction;
import com.mgatelabs.lunar.utils.ShellImpl;
import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mmgate on 11/4/15.
 */
public class ToStringsExchangeAppSLV extends AbstractAppSLV {

    private
    @NotNull
    final Project project;
    private
    @NotNull
    final ToStringModes mode;

    final boolean deploy;


    public ToStringsExchangeAppSLV(@NotNull final Project project, @NotNull final ToStringModes mode, boolean deploy, @NotNull final ShellImpl shell, @NotNull final Application application) {
        super((deploy ? "Deploy " : "") + mode.getTitle(), mode.getKey() + (deploy ? "deploy" : ""), shell, application);
        this.project = project;
        this.mode = mode;
        this.deploy = deploy;
    }

    @Override
    public void run() {
        super.run();
        if (deploy) {
            final List<ProjectDeployment> deployments = Lists.newArrayList();
            getApp().runReadTransactionSilent(new CommitTransaction() {
                @Override
                public boolean commit(@NotNull Application app) throws Exception {
                    deployments.addAll(app.getProjectDeploymentService().listActive(project.getProjectNo()));
                    return true;
                }
            });
            for (ProjectDeployment projectDeployment: deployments) {
                final File projectPath = new File(projectDeployment.getDeploymentPath());
                if (projectPath.exists() && projectPath.isDirectory()) {
                    deployTo(projectDeployment.getDeploymentPath());
                }
            }

        } else {
            final String exchangePath = "." + File.separator + "exchange";
            final String projectPathStr = exchangePath + File.separator + project.getKey();
            final File projectPath = new File(projectPathStr);
            if (!projectPath.exists()) {
                projectPath.mkdirs();
            }
            deployTo(projectPathStr);

        }
    }

    private void deployTo(final String projectPathStr) {

        getApp().runReadTransactionSilent(new CommitTransaction() {
            @Override
            public boolean commit(@NotNull Application app) throws Exception {

                // Find all the file names
                final List<ProjectFileName> projectFileNames = app.getProjectFileNameService().listAll(project.getProjectNo());

                // Look for the keys for each file name
                final Map<String, List<ProjectKey>> fileNameKeys = Maps.newHashMap();
                for (ProjectFileName projectFileName : projectFileNames) {
                    if (projectFileName.getProsta() == ProstaType.ACTIVE) {
                        final List<ProjectKey> fileKeys = app.getProjectKeyService().listAllProjectKeys(project.getProjectNo(), projectFileName.getFileName());
                        if (fileKeys != null && fileKeys.size() > 0) {
                            fileNameKeys.put(projectFileName.getFileName(), fileKeys);
                        }
                    }
                }

                if (fileNameKeys.isEmpty()) {
                    shellExited("No FileNames available to exchange");
                    return false;
                }

                // Need all the keys
                List<ProjectKeyText> allTexts = app.getProjectKeyTextService().listAllProjectKeyTexts(project.getProjectNo());

                // Map the key to output text
                final Map<Long, Map<String, String>> keyToLangText = Maps.newHashMap();
                for (ProjectKeyText text : allTexts) {
                    if (!keyToLangText.containsKey(text.getProjectKeyNo())) {
                        keyToLangText.put(text.getProjectKeyNo(), new HashMap<String, String>());
                    }
                    final Map<String, String> langToText = keyToLangText.get(text.getProjectKeyNo());
                    langToText.put(text.getLanguageId(), text.getText());
                }

                final String develLanguage = project.getDevelopmentKey();

                final List<ProjectLanguage> languages = app.getProjectLanguageService().listAll(project.getProjectNo());
                for (ProjectLanguage projectLanguage : languages) {
                    // Only work on active languages
                    if (projectLanguage.getProsta() != ProstaType.ACTIVE) {
                        continue;
                    }
                    final String languagePath = projectPathStr + File.separator + projectLanguage.getLanguageId() + ".lproj";
                    final File languageFolder = new File(languagePath);
                    if (!languageFolder.exists()) {
                        if (!languageFolder.mkdirs()) {
                            shellExited("Could not make path:" + languagePath);
                            return false;
                        }
                    }

                    // Loop through filenames and write keys
                    for (Map.Entry<String, List<ProjectKey>> entry : fileNameKeys.entrySet()) {
                        final StringBuilder sb = new StringBuilder();
                        // Loop through keys
                        for (ProjectKey key : entry.getValue()) {
                            // Get the development text
                            final String develText = getTextFor(key.getProjectKeyNo(), develLanguage, "UNKNOWN", keyToLangText);
                            final String targetText = getTextFor(key.getProjectKeyNo(), projectLanguage.getLanguageId(), null, keyToLangText);
                            if (mode.isAll() || (!mode.isAll() && targetText == null)) {
                                sb.append("/* ").append(key.getComment()).append(" */\n");
                                String text = targetText != null ? targetText : develText;
                                if (mode == ToStringModes.TEST) {
                                    text = "$$" + text + "^^";
                                }
                                sb.append("\"").append(key.getKeyText().replaceAll("\"", "\\\"")).append("\" = \"").append(text.replaceAll("\"", "\\\"")).append("\";\n\n");
                            }
                            File targetFile = new File(languageFolder + File.separator + entry.getKey());
                            if (sb.length() > 0) {
                                OutputStreamWriter outputStreamWriter = null;
                                try {
                                    outputStreamWriter = new OutputStreamWriter(new FileOutputStream(targetFile), "UTF-16");
                                    outputStreamWriter.write(sb.toString());
                                } finally {
                                    Closer.close(outputStreamWriter);
                                }
                            } else if (targetFile.exists()) {
                                targetFile.delete();
                            }
                        }
                    }
                }
                return false;
            }
        });

    }

    @Nullable
    private String getTextFor(final long projectKey, @NotNull final String language, @Nullable final String failTo, @NotNull final Map<Long, Map<String, String>> keyToLangText) {
        final Map<String, String> langToText = keyToLangText.get(projectKey);
        return langToText != null ? langToText.get(language) : failTo;
    }

    public enum ToStringModes {
        MISSING("Dump Missing *.strings", "miss", false),
        ALL("Dump All  *.strings", "all", true),
        TEST("Test *.strings", "test", true);
        private final String title;
        private final String key;
        private final boolean all;

        ToStringModes(String title, String key, boolean all) {
            this.title = title;
            this.key = key;
            this.all = all;
        }

        public boolean isAll() {
            return all;
        }

        public String getTitle() {
            return title;
        }

        public String getKey() {
            return key;
        }
    }
}
