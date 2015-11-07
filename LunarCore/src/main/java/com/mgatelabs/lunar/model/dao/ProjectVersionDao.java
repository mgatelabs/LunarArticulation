package com.mgatelabs.lunar.model.dao;

import com.mgatelabs.lunar.model.entities.ProjectVersion;
import com.sun.istack.internal.NotNull;

import java.util.List;

/**
 * Created by Michael Glen Fuller Jr on 10/25/2015.
 */
public interface ProjectVersionDao {
    void saveProjectVersion(@NotNull final ProjectVersion projectVersion);

    void updateProjectVersion(@NotNull final ProjectVersion projectVersion);

    List<ProjectVersion> listVersionForProject(long projectNo);
}
