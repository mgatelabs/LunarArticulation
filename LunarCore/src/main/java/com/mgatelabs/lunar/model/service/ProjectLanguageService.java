package com.mgatelabs.lunar.model.service;

import com.mgatelabs.lunar.model.entities.ProjectLanguage;
import com.sun.istack.internal.NotNull;

import java.util.List;

/**
 * Created by mmgate on 11/3/15.
 */
public interface ProjectLanguageService {
    void save(@NotNull final ProjectLanguage language);
    @NotNull
    List<ProjectLanguage> listAll(long projectNo);
    void update(@NotNull final ProjectLanguage language);
}
