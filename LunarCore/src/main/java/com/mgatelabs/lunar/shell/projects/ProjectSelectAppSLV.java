package com.mgatelabs.lunar.shell.projects;

import com.google.common.collect.Lists;
import com.mgatelabs.lunar.Application;
import com.mgatelabs.lunar.model.entities.Project;
import com.mgatelabs.lunar.utils.*;
import com.sun.istack.internal.NotNull;

import java.util.List;

/**
 * Created by Michael Glen Fuller Jr on 10/25/2015.
 */
public class ProjectSelectAppSLV extends AbstractAppSLV {

    private List<AbstractSLV> levels;

    public ProjectSelectAppSLV(@NotNull ShellImpl shell, @NotNull Application app) {
        super("Project Selection", "ps", shell, app);
        levels = Lists.newArrayList();
    }

    @Override
    public void run() {
        super.run();
        shellSubRestart();
        chooseSubShell(true, levels);
    }

    @Override
    protected void shellSubRestart() {
        super.shellSubRestart();
        levels.clear();
        getApp().runReadTransactionSilent(new CommitTransaction() {
            @Override
            public boolean commit(@NotNull Application app) throws Exception {
                final List<Project> projects = getApp().getProjectService().findAllProjects();
                for (Project project : projects) {
                    levels.add(new ProjectAppSLV(getShell(), getApp(), project));
                }

                return true;
            }
        });
        levels.add(new NewProjectAppSLV(getShell(), getApp()));
    }
}
