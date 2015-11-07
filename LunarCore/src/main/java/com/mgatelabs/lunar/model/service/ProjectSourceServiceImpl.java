package com.mgatelabs.lunar.model.service;

import com.mgatelabs.lunar.model.dao.ProjectSourceDao;
import com.mgatelabs.lunar.model.entities.ProjectSource;
import com.sun.istack.internal.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Michael Glen Fuller Jr on 10/25/2015.
 */
@Service("projectSourceService")
@Transactional
public class ProjectSourceServiceImpl implements ProjectSourceService {

    @Autowired
    private ProjectSourceDao dao;

    @Override
    public void saveProjectSource(@NotNull ProjectSource projectSource) {
        dao.saveProjectSource(projectSource);
    }

    @Override
    public void updateProjectSource(@NotNull ProjectSource source) {
        dao.updateProjectSource(source);
    }

    @Override
    public List<ProjectSource> listAllProjectSources(long projectNo) {
        return dao.listAllProjectSources(projectNo);
    }

    @Override
    public List<ProjectSource> listActiveProjectSources(long projectNo) {
        return dao.listActiveProjectSources(projectNo);
    }
}
