package com.mgatelabs.lunar.shell.projects.versions;

import com.mgatelabs.lunar.Application;
import com.mgatelabs.lunar.model.entities.Project;
import com.mgatelabs.lunar.model.entities.ProjectVersion;
import com.mgatelabs.lunar.model.entities.ProstaType;
import com.mgatelabs.lunar.shell.BeanEditor;
import com.mgatelabs.lunar.utils.AbstractAppSLV;
import com.mgatelabs.lunar.utils.CommitTransaction;
import com.mgatelabs.lunar.utils.ShellImpl;
import com.sun.istack.internal.NotNull;

/**
 * Created by Michael Glen Fuller Jr on 10/26/2015.
 */
public class NewProjectVersionAppSLV extends AbstractAppSLV {

    private final Project sourceProject;

    public NewProjectVersionAppSLV(@NotNull final Project sourceProject, @NotNull ShellImpl shell, @NotNull Application app) {
        super("New Project Version", "newprojver", shell, app);
        this.sourceProject = sourceProject;
    }

    @Override
    public void run() {
        super.run();
        final BeanEditor<ProjectVersion> b = new BeanEditor<ProjectVersion>(ProjectVersion.class, getShell(), getApp());
        b.run();
        if (b.isSuccess()) {
            b.getPojo().setProjectNo(sourceProject.getProjectNo());
            b.getPojo().setProsta(ProstaType.OPEN);
            if(getApp().runCommitTransactionSilent(new CommitTransaction() {
                @Override
                public boolean commit(@NotNull Application app) throws Exception {
                    app.getProjectVersionService().saveProjectVersion(b.getPojo());
                    return true;
                }
            })) {
                shellExited("Project Version Created");
            } else {
                shellExited("Project Version Creation Failed");
            }
        }
    }
}
