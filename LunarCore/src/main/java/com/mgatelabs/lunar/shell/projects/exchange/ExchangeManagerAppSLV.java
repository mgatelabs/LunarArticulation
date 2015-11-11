package com.mgatelabs.lunar.shell.projects.exchange;

import com.google.common.collect.Lists;
import com.mgatelabs.lunar.Application;
import com.mgatelabs.lunar.model.entities.Project;
import com.mgatelabs.lunar.utils.AbstractAppSLV;
import com.mgatelabs.lunar.utils.AbstractSLV;
import com.mgatelabs.lunar.utils.ShellImpl;
import com.sun.istack.internal.NotNull;

import java.util.List;

/**
 * Created by mmgate on 11/4/15.
 */
public class ExchangeManagerAppSLV extends AbstractAppSLV {

    private final Project project;
    private final long projectVersionNo;

    public ExchangeManagerAppSLV(final Project project, final long projectVersionNo, @NotNull final ShellImpl shell, @NotNull final Application app) {
        super("I/O Manager", "io", shell, app);
        this.project = project;
        this.projectVersionNo = projectVersionNo;
    }

    @Override
    public void run() {
        super.run();

        final List<AbstractSLV> shells = Lists.newArrayList();

        shells.add(new FromStringsExchangeAppSLV(project, projectVersionNo, getShell(), getApp()));

        shells.add(new ToStringsExchangeAppSLV(project, ToStringsExchangeAppSLV.ToStringModes.ALL, getShell(), getApp()));
        shells.add(new ToStringsExchangeAppSLV(project, ToStringsExchangeAppSLV.ToStringModes.MISSING, getShell(), getApp()));
        shells.add(new ToStringsExchangeAppSLV(project, ToStringsExchangeAppSLV.ToStringModes.TEST, getShell(), getApp()));

        shells.add(new ClearExchangeAppSLV(project, getShell(), getApp()));

        chooseSubShell(true, shells);
    }
}
