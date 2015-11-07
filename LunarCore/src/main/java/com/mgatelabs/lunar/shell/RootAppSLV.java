package com.mgatelabs.lunar.shell;

import com.google.common.collect.Lists;
import com.mgatelabs.lunar.Application;
import com.mgatelabs.lunar.shell.projects.ProjectSelectAppSLV;
import com.mgatelabs.lunar.utils.AbstractAppSLV;
import com.mgatelabs.lunar.utils.AbstractSLV;
import com.mgatelabs.lunar.utils.ShellImpl;
import com.sun.istack.internal.NotNull;

import java.util.List;

/**
 * Created by Michael Glen Fuller Jr on 10/25/2015.
 */
public class RootAppSLV extends AbstractAppSLV {
    public RootAppSLV(@NotNull Application app) {
        super("Root", "root", new ShellImpl(), app);
    }

    @Override
    public void run() {
        super.run();

        List<AbstractSLV> levels = Lists.newArrayList();

        levels.add(new ProjectSelectAppSLV(getShell(), getApp()));
        //levels.add(new NewProjectAppSLV(getShell(), getApp()));

        //levels.add(new BeanEditor<Project>(Project.class, getShell(), getApp()));

        chooseSubShell(true, levels);
    }
}
