package com.mgatelabs.lunar.model.dao;

import com.mgatelabs.lunar.model.entities.ProjectDeployment;
import com.mgatelabs.lunar.model.entities.ProstaType;
import com.sun.istack.internal.NotNull;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Michael Glen Fuller JR on 11/17/15.
 * LunarArticulation For M-Gate Labs
 * Copyright 2015
 */
@Repository("projectDeploymentDao")
public class ProjectDeploymentDaoImpl extends AbstractDao implements ProjectDeploymentDao {
    @Override
    public void save(@NotNull ProjectDeployment projectDeployment) {
        persist(projectDeployment);
    }

    @Override
    public void update(@NotNull ProjectDeployment projectDeployment) {
        getSession().update(projectDeployment);
    }

    @Override
    public List<ProjectDeployment> listAll(long projectNo) {
        Criteria criteria = getSession().createCriteria(ProjectDeployment.class);
        criteria.add(Restrictions.eq("projectNo", projectNo));
        return criteria.list();
    }

    @Override
    public List<ProjectDeployment> listActive(long projectNo) {
        Criteria criteria = getSession().createCriteria(ProjectDeployment.class);
        criteria.add(Restrictions.eq("projectNo", projectNo));
        criteria.add(Restrictions.eq("prosta", ProstaType.ACTIVE));
        return criteria.list();
    }
}
