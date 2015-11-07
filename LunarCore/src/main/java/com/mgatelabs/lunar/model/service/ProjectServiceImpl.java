package com.mgatelabs.lunar.model.service;

import com.mgatelabs.lunar.model.dao.ProjectDao;
import com.mgatelabs.lunar.model.entities.Project;
import com.sun.istack.internal.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Michael Glen Fuller Jr on 10/24/2015.
 */

@Service("projectService")
@Transactional
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectDao dao;

    public void saveProject(@NotNull final Project project) {
        dao.saveProject(project);
    }

    public List<Project> findAllProjects() {
        return dao.findAllProjects();
    }

    public Project findByKey(@NotNull final String key) {
        return dao.findByKey(key);
    }

    public void updateProject(@NotNull final Project project) {
        dao.updateProject(project);
    }
}
