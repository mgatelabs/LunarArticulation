package com.mgatelabs.lunar.model.service;

import com.mgatelabs.lunar.model.dao.ProjectKeyDao;
import com.mgatelabs.lunar.model.entities.ProjectKey;
import com.sun.istack.internal.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Michael Glen Fuller Jr on 10/25/2015.
 */
@Service("projectKeyService")
@Transactional
public class ProjectKeyServiceImpl implements ProjectKeyService {

    @Autowired
    private ProjectKeyDao dao;

    @Override
    public void saveProjectKey(ProjectKey projectKey) {
        dao.saveProjectKey(projectKey);
    }

    @Override
    public void updateProjectKey(ProjectKey projectKey) {
        dao.updateProjectKey(projectKey);
    }

    @Override
    public List<ProjectKey> listAllProjectKeys(long projectNo) {
        return dao.listAllProjectKeys(projectNo);
    }

    @Override
    public List<ProjectKey> listAllProjectKeys(long projectNo, @NotNull String fileName) {
        return dao.listAllProjectKeys(projectNo, fileName);
    }

    @Override
    public void delete(@NotNull final ProjectKey projectKey) {
        dao.delete(projectKey);
    }
}
