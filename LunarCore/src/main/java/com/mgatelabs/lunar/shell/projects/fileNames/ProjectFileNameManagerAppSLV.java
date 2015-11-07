package com.mgatelabs.lunar.shell.projects.fileNames;

import com.google.common.collect.Lists;
import com.mgatelabs.lunar.Application;
import com.mgatelabs.lunar.model.entities.ProjectFileName;
import com.mgatelabs.lunar.model.entities.ProjectLanguage;
import com.mgatelabs.lunar.model.entities.ProstaType;
import com.mgatelabs.lunar.shell.projects.language.NewProjectLanguageAppSLV;
import com.mgatelabs.lunar.shell.projects.language.ToggleProjectLanguageAppSLV;
import com.mgatelabs.lunar.utils.AbstractAppSLV;
import com.mgatelabs.lunar.utils.AbstractSLV;
import com.mgatelabs.lunar.utils.CommitTransaction;
import com.mgatelabs.lunar.utils.ShellImpl;
import com.sun.istack.internal.NotNull;

import java.util.List;

/**
 * Created by mmgate on 11/3/15.
 */
public class ProjectFileNameManagerAppSLV extends AbstractAppSLV {

    private final long projectNo;

    public ProjectFileNameManagerAppSLV(long projectNo, @NotNull ShellImpl shell, @NotNull Application app) {
        super("FileName Manager", "fileman", shell, app);
        this.projectNo = projectNo;
    }

    @Override
    public void run() {
        super.run();

        final List<ProjectFileName> fileNames = Lists.newArrayList();

        while (true) {
            fileNames.clear();
            getApp().runReadTransactionSilent(new CommitTransaction() {
                @Override
                public boolean commit(@NotNull Application app) throws Exception {
                    fileNames.addAll(app.getProjectFileNameService().listAll(projectNo));
                    return true;
                }
            });

            int activeCount = 0;
            for (ProjectFileName fileName: fileNames) {
                if (fileName.getProsta() == ProstaType.ACTIVE) {
                    activeCount++;
                }
            }

            info("Found " + activeCount + "/" + fileNames.size() + " Active File(s)");

            List<AbstractSLV> programs = Lists.newArrayList();

            if (fileNames.size() > 0) {
                programs.add(new ToggleProjectFileNameAppSLV(fileNames, getShell(), getApp()));
            }

            final AbstractSLV s = promptForShell(programs);
            if (s == null) {
                return;
            }
            s.run();
        }

    }

}
