package com.mgatelabs.lunar.shell.projects.exchange;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mgatelabs.lunar.Application;
import com.mgatelabs.lunar.model.entities.*;
import com.mgatelabs.lunar.parser.StringsParser;
import com.mgatelabs.lunar.utils.*;
import com.sun.istack.internal.NotNull;

import java.io.File;
import java.util.*;

/**
 * Created by mmgate on 11/4/15.
 */
public class FromStringsExchangeAppSLV extends AbstractAppSLV {

    private @NotNull final Project project;
    private final long projectVersionNo;

    public FromStringsExchangeAppSLV(@NotNull final Project project, final long projectVersionNo, @NotNull final ShellImpl shell, @NotNull final Application app) {
        super("Import *.strings", "impstr", shell, app);
        this.project = project;
        this.projectVersionNo = projectVersionNo;
    }

    @Override
    public void run() {
        super.run();

        final String languageId = promptForString("Language to Import:");

        if (getApp().isValidLanguageIdentifier(languageId)) {
            final String exchangeFolderPath = PathUtils.getPathForExchangeLanguage(project, languageId);
            final File exchangeFolder = new File(exchangeFolderPath);
            if (!exchangeFolder.exists()) {
                shellExited("Could not locate folder: " + exchangeFolderPath);
                return;
            }

            getApp().runCommitTransactionSilent(new CommitTransaction() {
                @Override
                public boolean commit(@NotNull Application app) throws Exception {

                    final List<ProjectLanguage> languages = app.getProjectLanguageService().listAll(project.getProjectNo());
                    boolean languageEnabled = false;
                    for (ProjectLanguage language: languages) {
                        if (language.getLanguageId().equals(languageId) && language.getProsta() == ProstaType.ACTIVE) {
                            languageEnabled = true;
                            break;
                        }
                    }
                    if (!languageEnabled) {
                        shellExited("Selected Language is not active");
                        return false;
                    }

                    boolean isDevelopmentLanguage = project.getDevelopmentKey().equals(languageId);

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
                        shellExited("No FileNames available to import");
                        return false;
                    }

                    final Calendar c = Calendar.getInstance(TimeZone.getTimeZone("UTC"));

                    final Date updatedTime = c.getTime();

                    // Need all the keys
                    List<ProjectKeyText> allTexts = app.getProjectKeyTextService().listAllProjectKeyTexts(project.getProjectNo());

                    // Map the key to output text
                    final Map<Long, ProjectKeyText> keyToLangText = Maps.newHashMap();
                    for (ProjectKeyText text : allTexts) {
                        // We only care about the target language
                        if (text.getLanguageId().equals(languageId)) {
                            keyToLangText.put(text.getProjectKeyNo(), text);
                        }
                    }

                    long updatedProjectKeys = 0;
                    long updatedProjectKeyTexts = 0;
                    long insertedProjectTextTexts = 0;

                    for (Map.Entry<String, List<ProjectKey>> entry : fileNameKeys.entrySet()) {
                        List<ProjectKey> fileKeys = entry.getValue();
                        if (fileKeys.size() > 0) {
                            final Map<String, ProjectKey> keyToNo = Maps.newHashMap();
                            for (ProjectKey projectKey: fileKeys) {
                                keyToNo.put(projectKey.getKeyText(), projectKey);
                            }
                            String filePath = PathUtils.getPathForExchangeFileName(project, languageId, entry.getKey());
                            File targetFile = new File(filePath);
                            if (targetFile.exists()) {
                                final List<KeyValueCommentItem> newTextItems = Lists.newArrayList();
                                final List<KeyValueCommentItem> sampleTextItems = StringsParser.parseFile(targetFile);

                                // Remove unknown values
                                for (KeyValueCommentItem sampleItem: sampleTextItems) {
                                    if (!sampleItem.getText().startsWith(ValueUtils.IGNORE_PREFIX)) {
                                        newTextItems.add(sampleItem);
                                    }
                                }
                                sampleTextItems.clear();

                                if (newTextItems.size() > 0) {
                                    final Insertion insertion = new Insertion();
                                    insertion.setFilename(entry.getKey());
                                    insertion.setInserted(updatedTime);
                                    insertion.setProjectNo(project.getProjectNo());
                                    insertion.setProjectSourceNo(new Long(-1));
                                    insertion.setProjectVersionNo(projectVersionNo);
                                    insertion.setType(InsertionType.IMPORT);
                                    app.getInsertionService().saveInsertion(insertion);

                                    for (KeyValueCommentItem item: newTextItems) {
                                        // Look up the project key
                                        ProjectKey projectKey = keyToNo.get(item.getKey());
                                        if (projectKey != null) {

                                            // Does the project key need an update>
                                            if (isDevelopmentLanguage && !projectKey.getComment().equals(item.getComment())) {
                                                projectKey.setComment(item.getComment());
                                                projectKey.setUpdated(c.getTime());
                                                app.getProjectKeyService().updateProjectKey(projectKey);
                                                updatedProjectKeys++;
                                            }
                                            // Find the language key
                                            ProjectKeyText projectKeyText = keyToLangText.get(projectKey.getProjectKeyNo());
                                            if (projectKeyText != null) {
                                                if (!item.getText().equals(projectKeyText.getText())) {
                                                    projectKeyText.setText(item.getText());
                                                    projectKeyText.setUpdated(updatedTime);
                                                    app.getProjectKeyTextService().updateProjectKeyText(projectKeyText);
                                                    updatedProjectKeyTexts++;
                                                }
                                            } else {
                                                projectKeyText = new ProjectKeyText();
                                                projectKeyText.setProjectNo(project.getProjectNo());
                                                projectKeyText.setProjectKeyNo(projectKey.getProjectKeyNo());
                                                projectKeyText.setUpdated(updatedTime);
                                                projectKeyText.setInserted(updatedTime);
                                                projectKeyText.setText(item.getText());
                                                projectKeyText.setLanguageId(languageId);
                                                app.getProjectKeyTextService().saveProjectKeyText(projectKeyText);
                                                insertedProjectTextTexts++;
                                            }
                                        } else {
                                            info("Unknown Text Key: " + item.getKey());
                                        }
                                    }
                                }
                            } else {
                                info("File Not Found: " + entry.getKey());
                            }
                        } else {
                            info("Ignoring File: " + entry.getKey());
                        }
                    }

                    info("Updated Keys: " + updatedProjectKeys);
                    info("Updated Key Texts: " + updatedProjectKeyTexts);
                    info("Inserted Key Texts: " + insertedProjectTextTexts);

                    return true;
                }
            });


        } else {
            shellExited("Unknown language");
        }
    }
}
