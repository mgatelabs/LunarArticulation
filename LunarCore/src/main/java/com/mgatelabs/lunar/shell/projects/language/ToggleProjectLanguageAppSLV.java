package com.mgatelabs.lunar.shell.projects.language;

import com.mgatelabs.lunar.Application;
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
public class ToggleProjectLanguageAppSLV extends AbstractAppSLV {

    private
    @NotNull
    final List<ProjectLanguage> languages;

    public ToggleProjectLanguageAppSLV(@NotNull final List<ProjectLanguage> languages, @NotNull final ShellImpl shell, @NotNull final Application app) {
        super("Activate/Open Language", "status", shell, app);
        this.languages = languages;
    }


    @Override
    public void run() {
        super.run();

        info("Project Language List:");

        int i = 0;
        for (ProjectLanguage language : languages) {
            info((i + 1) + ") [" + language.getProsta().name() + "] " + language.getLanguageId() + " - " + getApp().getNameForLanguageId(language.getLanguageId()));
            i++;
        }

        int languagePosition = promptForInt("Language # To Toggle?", -1);
        if (languagePosition >= 1) {
            languagePosition--;
            if (languagePosition >= 0 && languagePosition < languages.size()) {

                final ProjectLanguage languageToToggle = languages.get(languagePosition);

                switch (languageToToggle.getProsta()) {
                    case ACTIVE:
                        languageToToggle.setProsta(ProstaType.OPEN);
                        break;
                    case OPEN:
                        languageToToggle.setProsta(ProstaType.ACTIVE);
                        break;
                    default:
                        return;
                }

                if (getApp().runCommitTransactionSilent(new CommitTransaction() {
                    @Override
                    public boolean commit(@NotNull Application app) throws Exception {
                        app.getProjectLanguageService().update(languageToToggle);
                        return true;
                    }
                })) {
                } else {
                    error("Unable to modify project language");
                }

            } else {
                error("Invalid Language # Position");
            }
        }
    }

}
