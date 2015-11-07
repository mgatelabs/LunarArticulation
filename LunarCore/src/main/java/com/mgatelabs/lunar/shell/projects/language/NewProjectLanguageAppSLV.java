package com.mgatelabs.lunar.shell.projects.language;

import com.mgatelabs.lunar.Application;
import com.mgatelabs.lunar.model.entities.ProjectLanguage;
import com.mgatelabs.lunar.model.entities.ProstaType;
import com.mgatelabs.lunar.shell.BeanEditor;
import com.mgatelabs.lunar.utils.AbstractAppSLV;
import com.mgatelabs.lunar.utils.CommitTransaction;
import com.mgatelabs.lunar.utils.ShellImpl;
import com.sun.istack.internal.NotNull;

/**
 * Created by mmgate on 11/3/15.
 */
public class NewProjectLanguageAppSLV extends AbstractAppSLV {

    private final long projectNo;

    public NewProjectLanguageAppSLV(final long projectNo, @NotNull ShellImpl shell, @NotNull Application app) {
        super("New Project Language", "newprojsrc", shell, app);
        this.projectNo = projectNo;
    }

    @Override
    public void run() {
        super.run();
        final BeanEditor<ProjectLanguage> b = new BeanEditor<>(ProjectLanguage.class, getShell(), getApp());
        b.run();
        if (b.isSuccess()) {
            b.getPojo().setProjectNo(projectNo);
            b.getPojo().setProsta(ProstaType.OPEN);
            if(getApp().runCommitTransactionSilent(new CommitTransaction() {
                @Override
                public boolean commit(@NotNull Application app) throws Exception {
                    app.getProjectLanguageService().save(b.getPojo());
                    return true;
                }
            })) {
                shellExited("Project Language Created");
            } else {
                shellExited("Project Language Creation Failed");
            }
        }
    }

}
