package com.mgatelabs.lunar.model.dao;

import com.mgatelabs.lunar.model.entities.ProjectVersion;
import com.sun.istack.internal.NotNull;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Michael Glen Fuller Jr on 10/25/2015.
 */
@Repository("projectVersionDao")
public class ProjectVersionDaoImpl extends AbstractDao implements ProjectVersionDao {
    public void saveProjectVersion(@NotNull ProjectVersion projectVersion) {
        persist(projectVersion);
    }

    public void updateProjectVersion(@NotNull ProjectVersion projectVersion) {
        getSession().update(projectVersion);
    }

    public List<ProjectVersion> listVersionForProject(long projectNo) {
        Criteria criteria = getSession().createCriteria(ProjectVersion.class);
        criteria.add(Restrictions.eq("projectNo", projectNo));
        criteria.addOrder(Order.asc("major"));
        criteria.addOrder(Order.asc("minor"));
        criteria.addOrder(Order.asc("build"));
        return criteria.list();
    }
}
