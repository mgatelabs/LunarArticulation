package com.mgatelabs.lunar.shell.projects.deployment;

import com.mgatelabs.lunar.Application;
import com.mgatelabs.lunar.model.entities.ProjectDeployment;
import com.mgatelabs.lunar.model.entities.ProstaType;
import com.mgatelabs.lunar.utils.AbstractAppSLV;
import com.mgatelabs.lunar.utils.CommitTransaction;
import com.mgatelabs.lunar.utils.ShellImpl;
import com.sun.istack.internal.NotNull;

import java.util.List;

/**
 * Created by Michael Glen Fuller JR on 11/17/15.
 * LunarArticulation For M-Gate Labs
 * Copyright 2015
 */
public class ToggleProjectDeploymentAppSLV extends AbstractAppSLV {

    private
    @NotNull
    final List<ProjectDeployment> sourceList;

    public ToggleProjectDeploymentAppSLV(@NotNull final List<ProjectDeployment> sourceList, @NotNull final ShellImpl shell, @NotNull final Application app) {
        super("Activate/Open Deployment", "deploystatus", shell, app);
        this.sourceList = sourceList;
    }


    @Override
    public void run() {
        super.run();

        info("Project Source List:");

        int i = 0;
        for (ProjectDeployment projectSource : sourceList) {
            info((i + 1) + ") [" + projectSource.getProsta().name() + "] " + projectSource.getDeploymentPath());
            i++;
        }

        int sourcePosition = promptForInt("Deployment # To Toggle?", -1);
        if (sourcePosition >= 1) {
            sourcePosition--;
            if (sourcePosition >= 0 && sourcePosition < sourceList.size()) {

                final ProjectDeployment projectToToggle = sourceList.get(sourcePosition);

                switch (projectToToggle.getProsta()) {
                    case ACTIVE:
                        projectToToggle.setProsta(ProstaType.OPEN);
                        break;
                    case OPEN:
                        projectToToggle.setProsta(ProstaType.ACTIVE);
                        break;
                    default:
                        return;
                }

                if (getApp().runCommitTransactionSilent(new CommitTransaction() {
                    @Override
                    public boolean commit(@NotNull Application app) throws Exception {
                        app.getProjectDeploymentService().update(projectToToggle);
                        return true;
                    }
                })) {
                } else {
                    error("Unable to modify project deployment");
                }

            } else {
                error("Invalid Deployment Position");
            }
        }
    }

}
