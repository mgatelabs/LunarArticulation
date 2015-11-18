package com.mgatelabs.lunar.model.service;

import com.mgatelabs.lunar.model.dao.ProjectDeploymentDao;
import com.mgatelabs.lunar.model.entities.ProjectDeployment;
import com.sun.istack.internal.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Michael Glen Fuller JR on 11/17/15.
 * LunarArticulation For M-Gate Labs
 * Copyright 2015
 */
@Service("projectDeploymentService")
@Transactional
public class ProjectDeploymentServiceImpl implements ProjectDeploymentService {

    @Autowired
    private ProjectDeploymentDao dao;

    @Override
    public void save(@NotNull ProjectDeployment projectDeployment) {
        dao.save(projectDeployment);
    }

    @Override
    public void update(@NotNull ProjectDeployment projectDeployment) {
        dao.update(projectDeployment);
    }

    @Override
    public List<ProjectDeployment> listAll(long projectNo) {
        return dao.listAll(projectNo);
    }

    @Override
    public List<ProjectDeployment> listActive(long projectNo) {
        return dao.listActive(projectNo);
    }
}
