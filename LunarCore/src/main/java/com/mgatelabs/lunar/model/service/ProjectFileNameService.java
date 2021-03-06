package com.mgatelabs.lunar.model.service;

import com.mgatelabs.lunar.model.entities.ProjectFileName;
import com.sun.istack.internal.NotNull;

import java.util.List;

/**
 * Created by mmgate on 11/3/15.
 */
public interface ProjectFileNameService {
    void save(@NotNull final ProjectFileName fileName);
    @NotNull
    List<ProjectFileName> listAll(long projectNo);
    void update(@NotNull final ProjectFileName fileName);
}
