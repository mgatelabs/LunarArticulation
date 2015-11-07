package com.mgatelabs.lunar.model.dao;

import com.mgatelabs.lunar.model.entities.ProjectSource;
import com.mgatelabs.lunar.model.entities.ProstaType;
import com.sun.istack.internal.NotNull;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Michael Glen Fuller Jr on 10/25/2015.
 */
@Repository("projectSourceDao")
public class ProjectSourceDaoImpl extends AbstractDao implements ProjectSourceDao {
    @Override
    public void saveProjectSource(@NotNull ProjectSource projectSource) {
        persist(projectSource);
    }

    @Override
    public void updateProjectSource(@NotNull ProjectSource source) {
        getSession().update(source);
    }

    @Override
    public List<ProjectSource> listAllProjectSources(long projectNo) {
        Criteria criteria = getSession().createCriteria(ProjectSource.class);
        criteria.add(Restrictions.eq("projectNo", projectNo));
        return criteria.list();
    }

    @Override
    public List<ProjectSource> listActiveProjectSources(long projectNo) {
        Criteria criteria = getSession().createCriteria(ProjectSource.class);
        criteria.add(Restrictions.eq("projectNo", projectNo));
        criteria.add(Restrictions.eq("prosta", ProstaType.ACTIVE));
        return criteria.list();
    }
}
