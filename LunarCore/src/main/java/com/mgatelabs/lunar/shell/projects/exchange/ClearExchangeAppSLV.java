package com.mgatelabs.lunar.shell.projects.exchange;

import com.mgatelabs.lunar.Application;
import com.mgatelabs.lunar.model.entities.Project;
import com.mgatelabs.lunar.utils.AbstractAppSLV;
import com.mgatelabs.lunar.utils.ShellImpl;
import com.sun.istack.internal.NotNull;

import java.io.File;

/**
 * Created by mmgate on 11/4/15.
 */
public class ClearExchangeAppSLV extends AbstractAppSLV {

    private @NotNull final Project project;

    public ClearExchangeAppSLV(@NotNull final Project project, @NotNull final ShellImpl shell, @NotNull final Application app) {
        super("Clear", "clear", shell, app);
        this.project = project;
    }

    @Override
    public void run() {
        super.run();
        File exchangeFolder = new File( "." + File.separator + "exchange");
        if (exchangeFolder.exists()) {
            File projectFolder = new File("." + File.separator + "exchange" + File.separator + project.getKey());
            if (projectFolder.exists()) {
                File [] projectFiles = projectFolder.listFiles();
                for (File toDelete: projectFiles) {
                    toDelete.delete();
                }
            }
        }
    }
}
