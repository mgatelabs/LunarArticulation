package com.mgatelabs.lunar.shell.projects.source;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mgatelabs.lunar.Application;
import com.mgatelabs.lunar.model.entities.*;
import com.mgatelabs.lunar.parser.StringsParser;
import com.mgatelabs.lunar.utils.*;
import com.sun.istack.internal.NotNull;

import java.io.*;
import java.util.*;

/**
 * Created by mmgate on 11/1/15.
 */
public class ScanProjectAppSLV extends AbstractAppSLV {

    private @NotNull final ProjectVersion projectVersion;

    public ScanProjectAppSLV(@NotNull final ProjectVersion projectVersion, @NotNull final ShellImpl shell, @NotNull final Application app) {
        super("Scan Source Code", "scan", shell, app);
        this.projectVersion = projectVersion;
    }

    @Override
    public void run() {
        super.run();

        // Make sure they have an active project source
        final List<ProjectSource> sourceList = Lists.newArrayList();
        getApp().runReadTransactionSilent(new CommitTransaction() {
            @Override
            public boolean commit(@NotNull Application app) throws Exception {
                sourceList.addAll(app.getProjectSourceService().listActiveProjectSources(projectVersion.getProjectNo()));
                return true;
            }
        });

        if (sourceList.size() == 0) {
            shellExited("No Active Project Sources Found");
            return;
        }

        if (!cleanTempFolder()) {
            shellExited("Could not clean temp folder");
            return;
        }

        try {
            for (final ProjectSource projectSource : sourceList) {
                Map<String, List<KeyValueCommentItem>> sourceResults = scanProjectSource(projectSource);

                final Set<String> knownFileNames = Sets.newHashSet();
                List<ProjectFileName> savedFileNames = getApp().getProjectFileNameService().listAll(projectVersion.getProjectNo());
                for (ProjectFileName fileName: savedFileNames) {
                    knownFileNames.add(fileName.getFileName());
                }

                // Loop through source file names
                for (final Map.Entry<String, List<KeyValueCommentItem>> sourceItem: sourceResults.entrySet()) {
                    getApp().runCommitTransactionSilent(new CommitTransaction() {
                        @Override
                        public boolean commit(@NotNull Application app) throws Exception {

                            info("");
                            info("Scanning: " + sourceItem.getKey());

                            final Calendar c = Calendar.getInstance(TimeZone.getTimeZone("UTC"));

                            // Make the Insertion Record
                            final Insertion insertion = new Insertion();
                            insertion.setFilename(sourceItem.getKey());
                            insertion.setInserted(c.getTime());
                            insertion.setProjectNo(projectSource.getProjectNo());
                            insertion.setProjectSourceNo(projectSource.getProjectSourceNo());
                            insertion.setProjectVersionNo(projectVersion.getProjectVersionNo());
                            insertion.setType(InsertionType.SOURCE);

                            // Insert the record
                            getApp().getInsertionService().saveInsertion(insertion);

                            final Set<ProjectKey> newItems = Sets.newHashSet();

                            Set<String> checkSet = Sets.newHashSet();
                            for (ProjectKey key : getApp().getProjectKeyService().listAllProjectKeys(projectVersion.getProjectNo(), sourceItem.getKey())) {
                                checkSet.add(key.getKeyText());
                            }

                            info("Existing Count: " + checkSet.size());
                            info("Compare Count:  " + sourceItem.getValue().size());

                            for (KeyValueCommentItem possibleItem : sourceItem.getValue()) {

                                if (!checkSet.contains(possibleItem.getKey())) {
                                    checkSet.add(possibleItem.getKey());

                                    final ProjectKey newKey = new ProjectKey();
                                    newKey.setProjectNo(projectVersion.getProjectNo());
                                    newKey.setProjectVersionNo(projectVersion.getProjectVersionNo());
                                    newKey.setInserted(c.getTime());
                                    newKey.setComment(possibleItem.getComment());
                                    newKey.setInsertionNo(insertion.getInsertionNo());
                                    newKey.setFileName(sourceItem.getKey());
                                    newKey.setKeyText(possibleItem.getKey());
                                    newKey.setUpdated(newKey.getInserted());
                                    newKey.setProsta(ProstaType.ACTIVE);

                                    newItems.add(newKey);
                                }
                            }

                            for (ProjectKey key : newItems) {
                                getApp().getProjectKeyService().saveProjectKey(key);
                            }

                            // We only commit if new items were found, otherwise ignore

                            info("Inserted: " + newItems.size() + " Key(s)");

                            if (newItems.size() > 0) {

                                if (!knownFileNames.contains(sourceItem.getKey())) {
                                    knownFileNames.add(sourceItem.getKey());

                                    final ProjectFileName projectFileName = new ProjectFileName();
                                    projectFileName.setProjectNo(projectVersion.getProjectNo());
                                    projectFileName.setFileName(sourceItem.getKey());
                                    projectFileName.setProsta(ProstaType.OPEN);

                                    getApp().getProjectFileNameService().save(projectFileName);
                                }

                            }

                            return newItems.size() > 0;
                        }
                    });
                }

                info("");

            }
        } catch (Exception ex) {
            ex.printStackTrace();
            shellExited("Exception");
            return;
        }
    }

    private boolean cleanTempFolder() {
        final File f = new File("./import");
        final File [] files = f.listFiles();
        for (File file: files) {
            if(!file.delete()) {
                error("Unable to delete temp file: " + f.getName());
                return false;
            }
        }
        return true;
    }

    private Map<String, List<KeyValueCommentItem>> scanProjectSource(ProjectSource projectSource) throws Exception{
        Map<String, List<KeyValueCommentItem>> results = Maps.newHashMap();

        String tempPath = new File("./import").getCanonicalPath();

        StringBuilder sb = new StringBuilder();
        sb.append("cd \"").append(projectSource.getSourcePath()).append("\"\n");
        sb.append("genstrings -o ").append(tempPath).append(" ").append(projectSource.getExtension());

        File genFile = new File("./bin/gen.sh");

        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(genFile);
            fileWriter.write(sb.toString());
        } finally {
            Closer.close(fileWriter);
        }
        final Process p = Runtime.getRuntime().exec("./bin/gen.sh");
        new Thread(new Runnable() {
            public void run() {
                BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
                String line;
                try {
                    while ((line = input.readLine()) != null)
                        System.out.println(line);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        p.waitFor();

        // Find the New Files
        final File f = new File("./import");
        final File [] files = f.listFiles();
        for (File file: files) {
            if (file.getName().endsWith(".strings")) {
                List<KeyValueCommentItem> items = StringsParser.parseFile(file);
                results.put(file.getName(), items);
            }
        }

        return results;
    }
}
