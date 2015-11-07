package com.mgatelabs.lunar.model.service;

import com.mgatelabs.lunar.model.dao.ProjectVersionDao;
import com.mgatelabs.lunar.model.entities.ProjectVersion;
import com.sun.istack.internal.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Michael Glen Fuller Jr on 10/25/2015.
 */
@Service("projectVersionService")
@Transactional
public class ProjectVersionServiceImpl implements ProjectVersionService {

    @Autowired
    private ProjectVersionDao dao;

    public void saveProjectVersion(@NotNull ProjectVersion projectVersion) {
        dao.saveProjectVersion(projectVersion);
    }

    public void updateProjectVersion(@NotNull ProjectVersion projectVersion) {
        dao.updateProjectVersion(projectVersion);
    }

    public List<ProjectVersion> listVersionForProject(long projectNo) {
        return dao.listVersionForProject(projectNo);
    }
}
