package com.mgatelabs.lunar.model.dao;

import com.mgatelabs.lunar.model.entities.ProjectDeployment;
import com.sun.istack.internal.NotNull;

import java.util.List;

/**
 * Created by Michael Glen Fuller JR on 11/17/15.
 * LunarArticulation For M-Gate Labs
 * Copyright 2015
 */
public interface ProjectDeploymentDao {
    void save(@NotNull final ProjectDeployment projectDeployment);

    void update(@NotNull final ProjectDeployment projectDeployment);

    List<ProjectDeployment> listAll(long projectNo);

    List<ProjectDeployment> listActive(long projectNo);
}
