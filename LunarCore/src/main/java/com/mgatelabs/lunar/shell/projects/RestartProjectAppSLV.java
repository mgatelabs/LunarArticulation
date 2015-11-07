package com.mgatelabs.lunar.shell.projects;

import com.mgatelabs.lunar.Application;
import com.mgatelabs.lunar.model.entities.ProjectKey;
import com.mgatelabs.lunar.model.entities.ProjectKeyText;
import com.mgatelabs.lunar.utils.AbstractAppSLV;
import com.mgatelabs.lunar.utils.CommitTransaction;
import com.mgatelabs.lunar.utils.ShellImpl;
import com.sun.istack.internal.NotNull;

import java.util.List;

/**
 * Created by mmgate on 11/2/15.
 */
public class RestartProjectAppSLV extends AbstractAppSLV {
    private long projectNo;

    public RestartProjectAppSLV(long projectNo, @NotNull ShellImpl shell, @NotNull Application app) {
        super("Reset Language Data", "reset", shell, app);
        this.projectNo = projectNo;
    }

    @Override
    public void run() {

        String yes = promptForString("Please enter 'yes' to continue:");
        if (!"yes".equalsIgnoreCase(yes)) {
            shellExited("You did not enter 'yes'");
            return;
        }

        super.run();
        getApp().runCommitTransactionSilent(new CommitTransaction() {
            @Override
            public boolean commit(@NotNull Application app) throws Exception {

                for (ProjectKeyText projectKeyText: getApp().getProjectKeyTextService().listAllProjectKeyTexts(projectNo)) {
                    app.getProjectKeyTextService().delete(projectKeyText);
                }

                for (ProjectKey projectKey: getApp().getProjectKeyService().listAllProjectKeys(projectNo)) {
                    app.getProjectKeyService().delete(projectKey);
                }

                return true;
            }
        });
    }
}
