package com.mgatelabs.lunar.shell.projects.language;

import com.google.common.collect.Lists;
import com.mgatelabs.lunar.Application;
import com.mgatelabs.lunar.model.entities.ProjectLanguage;
import com.mgatelabs.lunar.model.entities.ProstaType;
import com.mgatelabs.lunar.utils.AbstractAppSLV;
import com.mgatelabs.lunar.utils.AbstractSLV;
import com.mgatelabs.lunar.utils.CommitTransaction;
import com.mgatelabs.lunar.utils.ShellImpl;
import com.sun.istack.internal.NotNull;

import java.util.List;

/**
 * Created by mmgate on 11/3/15.
 */
public class ProjectLanguageManagerAppSLV extends AbstractAppSLV {

    private final long projectNo;

    public ProjectLanguageManagerAppSLV(long projectNo, @NotNull ShellImpl shell, @NotNull Application app) {
        super("Language Manager", "langman", shell, app);
        this.projectNo = projectNo;
    }

    @Override
    public void run() {
        super.run();

        final List<ProjectLanguage> languages = Lists.newArrayList();

        while (true) {
            languages.clear();
            getApp().runReadTransactionSilent(new CommitTransaction() {
                @Override
                public boolean commit(@NotNull Application app) throws Exception {
                    languages.addAll(app.getProjectLanguageService().listAll(projectNo));
                    return true;
                }
            });

            int activeCount = 0;
            for (ProjectLanguage language: languages) {
                if (language.getProsta() == ProstaType.ACTIVE) {
                    activeCount++;
                }
            }

            info("Found " + activeCount + "/" + languages.size() + " Active Language(s)");

            List<AbstractSLV> programs = Lists.newArrayList();

            if (languages.size() > 0) {
                programs.add(new ToggleProjectLanguageAppSLV(languages, getShell(), getApp()));
            }

            programs.add(new NewProjectLanguageAppSLV(projectNo, getShell(), getApp()));

            final AbstractSLV s = promptForShell(programs);
            if (s == null) {
                return;
            }
            s.run();
        }

    }

}
