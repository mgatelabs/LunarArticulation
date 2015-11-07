package com.mgatelabs.lunar.model.service;

import com.mgatelabs.lunar.model.entities.Project;

import java.util.List;

/**
 * Created by Michael Glen Fuller Jr on 10/24/2015.
 */
public interface ProjectService {

    void saveProject(Project project);

    List<Project> findAllProjects();

    Project findByKey(String key);

    void updateProject(Project project);
}
