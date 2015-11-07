package com.mgatelabs.lunar.utils;

import com.mgatelabs.lunar.Application;
import com.sun.istack.internal.NotNull;

import java.util.List;

/**
 * Created by Michael Glen Fuller Jr on 10/25/2015.
 */
public abstract class AbstractAppSLV extends AbstractSLV {

    private @NotNull final Application app;


    public AbstractAppSLV(@NotNull final String title, @NotNull final String shortName, @NotNull final ShellImpl shell, @NotNull final Application app) {
        super(title, shortName, shell);
        this.app = app;
    }

    public Application getApp() {
        return app;
    }

    protected void chooseSubShell(boolean loops, List<AbstractSLV> levels) {
        while (true) {
            final AbstractSLV abstractSLV = getShell().promptForShell(levels, getShortName());
            if (abstractSLV != null) {
                abstractSLV.run();
            }
            if (abstractSLV == null || (abstractSLV != null && !loops)) {
                shellExited("Loop Ended");
                break;
            }
            shellSubRestart();
        }
    }

    protected void shellSubRestart() {

    }
}
