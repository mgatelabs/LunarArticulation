package com.mgatelabs.lunar.shell.projects.source;

import com.mgatelabs.lunar.Application;
import com.mgatelabs.lunar.model.entities.ProjectSource;
import com.mgatelabs.lunar.model.entities.ProstaType;
import com.mgatelabs.lunar.utils.AbstractAppSLV;
import com.mgatelabs.lunar.utils.CommitTransaction;
import com.mgatelabs.lunar.utils.ShellImpl;
import com.sun.istack.internal.NotNull;

import java.util.List;

/**
 * Created by mmgate on 11/1/15.
 */
public class ToggleProjectSourceAppSLV extends AbstractAppSLV {

    private
    @NotNull
    final List<ProjectSource> sourceList;

    public ToggleProjectSourceAppSLV(@NotNull final List<ProjectSource> sourceList, @NotNull final ShellImpl shell, @NotNull final Application app) {
        super("Activate/Open Source", "status", shell, app);
        this.sourceList = sourceList;
    }


    @Override
    public void run() {
        super.run();

        info("Project Source List:");

        int i = 0;
        for (ProjectSource projectSource : sourceList) {
            info((i + 1) + ") [" + projectSource.getProsta().name() + "] " + projectSource.getSourcePath());
            i++;
        }

        int sourcePosition = promptForInt("Source # To Toggle?", -1);
        if (sourcePosition >= 1) {
            sourcePosition--;
            if (sourcePosition >= 0 && sourcePosition < sourceList.size()) {

                final ProjectSource projectToToggle = sourceList.get(sourcePosition);

                switch (projectToToggle.getProsta()) {
                    case ACTIVE:
                        projectToToggle.setProsta(ProstaType.OPEN);
                        break;
                    case OPEN:
                        projectToToggle.setProsta(ProstaType.ACTIVE);
                        break;
                    default:
                        return;
                }

                if (getApp().runCommitTransactionSilent(new CommitTransaction() {
                    @Override
                    public boolean commit(@NotNull Application app) throws Exception {
                        app.getProjectSourceService().updateProjectSource(projectToToggle);
                        return true;
                    }
                })) {
                } else {
                    error("Unable to modify project source");
                }

            } else {
                error("Invalid Source Position");
            }
        }
    }
}
