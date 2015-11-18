package com.mgatelabs.lunar.shell.projects.deployment;

import com.mgatelabs.lunar.Application;
import com.mgatelabs.lunar.model.entities.Project;
import com.mgatelabs.lunar.model.entities.ProjectDeployment;
import com.mgatelabs.lunar.model.entities.ProstaType;
import com.mgatelabs.lunar.shell.BeanEditor;
import com.mgatelabs.lunar.utils.AbstractAppSLV;
import com.mgatelabs.lunar.utils.CommitTransaction;
import com.mgatelabs.lunar.utils.ShellImpl;
import com.sun.istack.internal.NotNull;

/**
 * Created by Michael Glen Fuller JR on 11/17/15.
 * LunarArticulation For M-Gate Labs
 * Copyright 2015
 */
public class NewProjectDeploymentAppSLV extends AbstractAppSLV {

    private @NotNull
    final Project project;

    public NewProjectDeploymentAppSLV(@NotNull final Project project, @NotNull ShellImpl shell, @NotNull Application app) {
        super("New Project Deployment", "newprojdeploy", shell, app);
        this.project = project;
    }

    @Override
    public void run() {
        super.run();
        final BeanEditor<ProjectDeployment> b = new BeanEditor<>(ProjectDeployment.class, getShell(), getApp());
        b.run();
        if (b.isSuccess()) {
            b.getPojo().setProjectNo(project.getProjectNo());
            b.getPojo().setProsta(ProstaType.OPEN);
            if(getApp().runCommitTransactionSilent(new CommitTransaction() {
                @Override
                public boolean commit(@NotNull Application app) throws Exception {
                    app.getProjectDeploymentService().save(b.getPojo());
                    return true;
                }
            })) {
                shellExited("Project Deployment Created");
            } else {
                shellExited("Project Deployment Creation Failed");
            }
        }
    }
}
