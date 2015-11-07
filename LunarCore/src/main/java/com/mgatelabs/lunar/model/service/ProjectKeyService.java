package com.mgatelabs.lunar.model.service;

import com.mgatelabs.lunar.model.entities.ProjectKey;
import com.sun.istack.internal.NotNull;

import java.util.List;

/**
 * Created by Michael Glen Fuller Jr on 10/25/2015.
 */
public interface ProjectKeyService {
    void saveProjectKey(ProjectKey projectKey);
    void updateProjectKey(ProjectKey projectKey);
    List<ProjectKey> listAllProjectKeys(long projectNo);
    @NotNull
    List<ProjectKey> listAllProjectKeys(final long projectNo, @NotNull final String fileName);
    void delete(@NotNull final ProjectKey projectKey);
}
