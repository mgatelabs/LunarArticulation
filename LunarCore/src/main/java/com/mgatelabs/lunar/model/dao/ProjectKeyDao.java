package com.mgatelabs.lunar.model.dao;

import com.mgatelabs.lunar.model.entities.ProjectKey;
import com.sun.istack.internal.NotNull;

import java.util.List;

/**
 * Created by Michael Glen Fuller Jr on 10/25/2015.
 */
public interface ProjectKeyDao {
    void saveProjectKey(ProjectKey projectKey);
    void updateProjectKey(ProjectKey projectKey);
    List<ProjectKey> listAllProjectKeys(long projectNo);
    List<ProjectKey> listAllProjectKeys(long projectNo, @NotNull final String fileName);
    void delete(@NotNull final ProjectKey projectKey);
}
