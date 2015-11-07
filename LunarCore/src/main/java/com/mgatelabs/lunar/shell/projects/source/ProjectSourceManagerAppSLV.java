package com.mgatelabs.lunar.shell.projects.source;

import com.google.common.collect.Lists;
import com.mgatelabs.lunar.Application;
import com.mgatelabs.lunar.model.entities.Project;
import com.mgatelabs.lunar.model.entities.ProjectSource;
import com.mgatelabs.lunar.model.entities.ProjectVersion;
import com.mgatelabs.lunar.model.entities.ProstaType;
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

        while (true) {
            sources.clear();
            getApp().runReadTransactionSilent(new CommitTransaction() {
                @Override
                public boolean commit(@NotNull Application app) throws Exception {
                    sources.addAll(app.getProjectSourceService().listAllProjectSources(project.getProjectNo()));
                    return true;
                }
            });

            int activeCount = 0;
            for (ProjectSource projectSource: sources) {
                if (projectSource.getProsta() == ProstaType.ACTIVE) {
                    activeCount++;
                }
            }

            info("Found " + activeCount + "/" + sources.size() + " Active Source(s)");

            List<AbstractSLV> programs = Lists.newArrayList();

            if (activeCount > 0) {
                programs.add(new ScanProjectAppSLV(projectVersion, getShell(), getApp()));
            }

            if (sources.size() > 0) {

                programs.add(new ToggleProjectSourceAppSLV(sources, getShell(), getApp()));
            }

            programs.add(new NewProjectSourceAppSLV(project, getShell(), getApp()));

            final AbstractSLV s = promptForShell(programs);
            if (s == null) {
                return;
            }
            s.run();
        }

    }
}
