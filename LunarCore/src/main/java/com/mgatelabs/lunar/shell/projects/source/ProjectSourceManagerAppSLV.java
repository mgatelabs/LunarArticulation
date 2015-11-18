package com.mgatelabs.lunar.shell.projects.source;

import com.google.common.collect.Lists;
import com.mgatelabs.lunar.Application;
import com.mgatelabs.lunar.model.entities.*;
import com.mgatelabs.lunar.shell.projects.deployment.NewProjectDeploymentAppSLV;
import com.mgatelabs.lunar.shell.projects.deployment.ToggleProjectDeploymentAppSLV;
import com.mgatelabs.lunar.utils.AbstractAppSLV;
import com.mgatelabs.lunar.utils.AbstractSLV;
import com.mgatelabs.lunar.utils.CommitTransaction;
import com.mgatelabs.lunar.utils.ShellImpl;
import com.sun.istack.internal.NotNull;

import java.util.List;

/**
 * Created by mmgate on 11/1/15.
 */
public class ProjectSourceManagerAppSLV extends AbstractAppSLV {

    private @NotNull final Project project;
    private @NotNull final ProjectVersion projectVersion;

    public ProjectSourceManagerAppSLV(@NotNull Project project, @NotNull final ProjectVersion projectVersion, @NotNull ShellImpl shell, @NotNull Application app) {
        super("Source Manager", "srcman", shell, app);
        this.project = project;
        this.projectVersion = projectVersion;
    }

    @Override
    public void run() {
        super.run();

        final List<ProjectSource> sources = Lists.newArrayList();

        final List<ProjectDeployment> deployments = Lists.newArrayList();

        while (true) {
            sources.clear();
            deployments.clear();
            getApp().runReadTransactionSilent(new CommitTransaction() {
                @Override
                public boolean commit(@NotNull Application app) throws Exception {
                    sources.addAll(app.getProjectSourceService().listAllProjectSources(project.getProjectNo()));
                    deployments.addAll(app.getProjectDeploymentService().listAll(project.getProjectNo()));
                    return true;
                }
            });

            int sourceActiveCount = 0;
            for (ProjectSource projectSource: sources) {
                if (projectSource.getProsta() == ProstaType.ACTIVE) {
                    sourceActiveCount++;
                }
            }

            int deploymentActiveCount = 0;
            for (ProjectDeployment projectDeployment: deployments) {
                if (projectDeployment.getProsta() == ProstaType.ACTIVE) {
                    deploymentActiveCount++;
                }
            }

            info("Found " + sourceActiveCount + "/" + sources.size() + " Active Source(s)");
            info("Found " + deploymentActiveCount + "/" + deployments.size() + " Active Deployment(s)");

            List<AbstractSLV> programs = Lists.newArrayList();

            if (sourceActiveCount > 0) {
                programs.add(new ScanProjectAppSLV(projectVersion, getShell(), getApp()));
            }

            if (sources.size() > 0) {

                programs.add(new ToggleProjectSourceAppSLV(sources, getShell(), getApp()));
            }

            if (deployments.size() > 0) {

                programs.add(new ToggleProjectDeploymentAppSLV(deployments, getShell(), getApp()));
            }

            programs.add(new NewProjectSourceAppSLV(project, getShell(), getApp()));

            programs.add(new NewProjectDeploymentAppSLV(project, getShell(), getApp()));

            final AbstractSLV s = promptForShell(programs);
            if (s == null) {
                return;
            }
            s.run();
        }

    }
}
