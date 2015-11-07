package com.mgatelabs.lunar.shell.projects;

import com.mgatelabs.lunar.Application;
import com.mgatelabs.lunar.model.entities.Project;
import com.mgatelabs.lunar.shell.BeanEditor;
import com.mgatelabs.lunar.utils.AbstractAppSLV;
import com.mgatelabs.lunar.utils.CommitTransaction;
import com.mgatelabs.lunar.utils.ShellImpl;
import com.sun.istack.internal.NotNull;

/**
 * Created by Michael Glen Fuller Jr on 10/26/2015.
 */
public class NewProjectAppSLV extends AbstractAppSLV {

    public NewProjectAppSLV(@NotNull ShellImpl shell, @NotNull Application app) {
        super("New Project", "newproj", shell, app);
    }

    @Override
    public void run() {
        super.run();

        final BeanEditor<Project> b = new BeanEditor<Project>(Project.class, getShell(), getApp());

        b.run();

        if (b.isSuccess()) {
            if(getApp().runCommitTransactionSilent(new CommitTransaction() {
                @Override
                public boolean commit(@NotNull Application app) throws Exception {
                    app.getProjectService().saveProject(b.getPojo());
                    return true;
                }
            })) {
                shellExited("Project Created");
            } else {
                shellExited("Project Creation Failed");
            }
        }
    }
}
