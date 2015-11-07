package com.mgatelabs.lunar.model.service;

import com.mgatelabs.lunar.model.entities.ProjectVersion;
import com.sun.istack.internal.NotNull;

import java.util.List;

/**
 * Created by Michael Glen Fuller Jr on 10/25/2015.
 */
public interface ProjectVersionService {
    void saveProjectVersion(@NotNull final ProjectVersion projectVersion);

    void updateProjectVersion(@NotNull final ProjectVersion projectVersion);

    List<ProjectVersion> listVersionForProject(long projectNo);
}
