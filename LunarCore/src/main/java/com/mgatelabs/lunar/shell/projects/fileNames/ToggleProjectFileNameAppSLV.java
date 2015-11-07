package com.mgatelabs.lunar.shell.projects.fileNames;

import com.mgatelabs.lunar.Application;
import com.mgatelabs.lunar.model.entities.ProjectFileName;
import com.mgatelabs.lunar.model.entities.ProjectLanguage;
import com.mgatelabs.lunar.model.entities.ProstaType;
import com.mgatelabs.lunar.utils.AbstractAppSLV;
import com.mgatelabs.lunar.utils.CommitTransaction;
import com.mgatelabs.lunar.utils.ShellImpl;
import com.sun.istack.internal.NotNull;

import java.util.List;

/**
 * Created by mmgate on 11/3/15.
 */
public class ToggleProjectFileNameAppSLV extends AbstractAppSLV {

    private
    @NotNull
    final List<ProjectFileName> fileNames;

    public ToggleProjectFileNameAppSLV(@NotNull final List<ProjectFileName> fileNames, @NotNull final ShellImpl shell, @NotNull final Application app) {
        super("Activate/Open File Name", "status", shell, app);
        this.fileNames = fileNames;
    }


    @Override
    public void run() {
        super.run();

        info("Project File Name List:");

        int i = 0;
        for (ProjectFileName item : fileNames) {
            info((i + 1) + ") [" + item.getProsta().name() + "] " + item.getFileName());
            i++;
        }

        int filePosition = promptForInt("File Name # To Toggle?", -1);
        if (filePosition >= 1) {
            filePosition--;
            if (filePosition >= 0 && filePosition < fileNames.size()) {

                final ProjectFileName itemToToggle = fileNames.get(filePosition);

                switch (itemToToggle.getProsta()) {
                    case ACTIVE:
                        itemToToggle.setProsta(ProstaType.OPEN);
                        break;
                    case OPEN:
                        itemToToggle.setProsta(ProstaType.ACTIVE);
                        break;
                    default:
                        return;
                }

                if (getApp().runCommitTransactionSilent(new CommitTransaction() {
                    @Override
                    public boolean commit(@NotNull Application app) throws Exception {
                        app.getProjectFileNameService().update(itemToToggle);
                        return true;
                    }
                })) {
                } else {
                    error("Unable to modify project file name");
                }

            } else {
                error("Invalid File Name # Position");
            }
        }
    }

}
