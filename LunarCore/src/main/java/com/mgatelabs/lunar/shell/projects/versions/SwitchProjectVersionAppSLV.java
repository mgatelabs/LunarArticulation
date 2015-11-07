package com.mgatelabs.lunar.shell.projects.versions;

import com.google.common.collect.Lists;
import com.mgatelabs.lunar.Application;
import com.mgatelabs.lunar.model.entities.Project;
import com.mgatelabs.lunar.model.entities.ProjectVersion;
import com.mgatelabs.lunar.model.entities.ProstaType;
import com.mgatelabs.lunar.utils.AbstractAppSLV;
import com.mgatelabs.lunar.utils.CommitTransaction;
import com.mgatelabs.lunar.utils.ShellImpl;
import com.sun.istack.internal.NotNull;

import java.util.List;

/**
 * Created by Michael Glen Fuller Jr on 11/1/2015.
 */
public class SwitchProjectVersionAppSLV extends AbstractAppSLV {
    private final List<ProjectVersion> versionList;

    public SwitchProjectVersionAppSLV(List<ProjectVersion> versionList, @NotNull ShellImpl shell, @NotNull Application app) {
        super("Switch Project Version", "swprover", shell, app);
        this.versionList = versionList;
    }

    @Override
    public void run() {
        super.run();

        info("Version List:");

        int i = 0;
        for (ProjectVersion projectVersion: versionList) {
            info((i+1)  + ") " + projectVersion.getCodename());
            i++;
        }

        int versionPosition = promptForInt("Version # To Activate?", -1);
        if (versionPosition >= 1) {
            versionPosition--;
            if (versionPosition >= 0 && versionPosition < versionList.size()) {

                final List<ProjectVersion> versionsToDeactivate = Lists.newArrayList();
                for (ProjectVersion projectVersion: versionList) {
                    if (projectVersion.getProsta() == ProstaType.ACTIVE) {
                        versionsToDeactivate.add(projectVersion);
                    }
                }
                final ProjectVersion projectToActivate = versionList.get(versionPosition);

                if(getApp().runCommitTransactionSilent(new CommitTransaction() {
                    @Override
                    public boolean commit(@NotNull Application app) throws Exception {

                        for (ProjectVersion version: versionsToDeactivate) {
                            version.setProsta(ProstaType.OPEN);
                            app.getProjectVersionService().updateProjectVersion(version);
                        }

                        projectToActivate.setProsta(ProstaType.ACTIVE);
                        app.getProjectVersionService().updateProjectVersion(projectToActivate);

                        return true;
                    }
                })) {

                } else {
                    error("Unable to update active project version");
                }

            } else {
                error("Invalid Version Position");
            }
        }
    }
}
