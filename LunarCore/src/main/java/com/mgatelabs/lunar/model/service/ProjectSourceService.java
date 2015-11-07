package com.mgatelabs.lunar.model.service;

import com.mgatelabs.lunar.model.entities.ProjectSource;
import com.sun.istack.internal.NotNull;

import java.util.List;

/**
 * Created by Michael Glen Fuller Jr on 10/25/2015.
 */
public interface ProjectSourceService {

    void saveProjectSource(@NotNull final ProjectSource projectSource);

    void updateProjectSource(@NotNull final ProjectSource source);

    List<ProjectSource> listAllProjectSources(long projectNo);

    List<ProjectSource> listActiveProjectSources(long projectNo);

}
