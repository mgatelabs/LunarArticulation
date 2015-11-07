package com.mgatelabs.lunar.shell.projects;

import com.google.common.collect.Lists;
import com.mgatelabs.lunar.Application;
import com.mgatelabs.lunar.model.entities.Project;
import com.mgatelabs.lunar.model.entities.ProjectVersion;
import com.mgatelabs.lunar.model.entities.ProstaType;
import com.mgatelabs.lunar.shell.projects.exchange.ExchangeManagerAppSLV;
import com.mgatelabs.lunar.shell.projects.fileNames.ProjectFileNameManagerAppSLV;
import com.mgatelabs.lunar.shell.projects.language.ProjectLanguageManagerAppSLV;
import com.mgatelabs.lunar.shell.projects.source.ProjectSourceManagerAppSLV;
import com.mgatelabs.lunar.shell.projects.versions.NewProjectVersionAppSLV;
import com.mgatelabs.lunar.shell.projects.versions.SwitchProjectVersionAppSLV;
import com.mgatelabs.lunar.utils.AbstractAppSLV;
import com.mgatelabs.lunar.utils.AbstractSLV;
import com.mgatelabs.lunar.utils.CommitTransaction;
import com.mgatelabs.lunar.utils.ShellImpl;
import com.sun.istack.internal.NotNull;

import java.util.List;

/**
 * Created by Michael Glen Fuller Jr on 10/26/2015.
 */
public class ProjectAppSLV extends AbstractAppSLV {
    private final Project project;

    public ProjectAppSLV(@NotNull ShellImpl shell, @NotNull Application app, @NotNull final Project project) {
        super(project.getTitle(), project.getKey(), shell, app);
        this.project = project;
    }

    @Override
    public void run() {
        super.run();

        while (true) {
            info("Project Info:");

            final List<ProjectVersion> versionList = Lists.newArrayList();
            getApp().runReadTransactionSilent(new CommitTransaction() {
                @Override
                public boolean commit(@NotNull Application app) throws Exception {
                    versionList.addAll(app.getProjectVersionService().listVersionForProject(project.getProjectNo()));
                    return true;
                }
            });
            ProjectVersion selectedProjectVersion = null;
            for (ProjectVersion pv: versionList) {
                if (pv.getProsta() == ProstaType.ACTIVE) {
                    selectedProjectVersion = pv;
                    break;
                }
            }

            final List<AbstractSLV> shells = Lists.newArrayList();

            if (selectedProjectVersion != null) {
                info("Current Version: " + selectedProjectVersion.getCodename());
            } else {
                error("A Project Version has not been selected");
            }

            if (versionList.size() > 0) {

                if (selectedProjectVersion != null) {
                    shells.add(new ExchangeManagerAppSLV(project, selectedProjectVersion.getProjectVersionNo(), getShell(), getApp()));

                    shells.add(new ProjectSourceManagerAppSLV(project, selectedProjectVersion, getShell(), getApp()));
                }

                shells.add(new SwitchProjectVersionAppSLV(versionList, getShell(), getApp()));

                shells.add(new ProjectLanguageManagerAppSLV(project.getProjectNo(), getShell(), getApp()));

                shells.add(new ProjectFileNameManagerAppSLV(project.getProjectNo(), getShell(), getApp()));

                shells.add(new RestartProjectAppSLV(project.getProjectNo() ,getShell(), getApp()));

            } else {
                info("No Project Versions Exist, please Create");
            }
            shells.add(new NewProjectVersionAppSLV(project, getShell(), getApp()));

            final AbstractSLV s = promptForShell(shells);
            if (s == null) {
                return;
            }
            s.run();
        }
    }
}
