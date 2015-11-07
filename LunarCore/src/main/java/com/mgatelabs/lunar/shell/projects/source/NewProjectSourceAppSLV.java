package com.mgatelabs.lunar.shell.projects.source;

import com.mgatelabs.lunar.Application;
import com.mgatelabs.lunar.model.entities.Project;
import com.mgatelabs.lunar.model.entities.ProjectSource;
import com.mgatelabs.lunar.model.entities.ProjectVersion;
import com.mgatelabs.lunar.model.entities.ProstaType;
import com.mgatelabs.lunar.shell.BeanEditor;
import com.mgatelabs.lunar.utils.AbstractAppSLV;
import com.mgatelabs.lunar.utils.CommitTransaction;
import com.mgatelabs.lunar.utils.ShellImpl;
import com.sun.istack.internal.NotNull;

/**
 * Created by mmgate on 11/1/15.
 */
public class NewProjectSourceAppSLV extends AbstractAppSLV {

    private @NotNull final Project project;

    public NewProjectSourceAppSLV(@NotNull final Project project, @NotNull ShellImpl shell, @NotNull Application app) {
        super("New Project Source", "newprojsrc", shell, app);
        this.project = project;
    }

    @Override
    public void run() {
        super.run();
        final BeanEditor<ProjectSource> b = new BeanEditor<>(ProjectSource.class, getShell(), getApp());
        b.run();
        if (b.isSuccess()) {
            b.getPojo().setProjectNo(project.getProjectNo());
            b.getPojo().setProsta(ProstaType.OPEN);
            if(getApp().runCommitTransactionSilent(new CommitTransaction() {
                @Override
                public boolean commit(@NotNull Application app) throws Exception {
                    app.getProjectSourceService().saveProjectSource(b.getPojo());
                    return true;
                }
            })) {
                shellExited("Project Source Created");
            } else {
                shellExited("Project Source Creation Failed");
            }
        }
    }
}
