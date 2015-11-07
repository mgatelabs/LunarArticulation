package com.mgatelabs.lunar.model.dao;

import com.mgatelabs.lunar.model.entities.ProjectKey;
import com.sun.istack.internal.NotNull;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Michael Glen Fuller Jr on 10/25/2015.
 */
@Repository("projectKeyDao")
public class ProjectKeyDaoImpl extends AbstractDao implements ProjectKeyDao {
    @Override
    public void saveProjectKey(ProjectKey projectKey) {
        persist(projectKey);
    }

    @Override
    public void updateProjectKey(ProjectKey projectKey) {
        getSession().update(projectKey);
    }

    @Override
    public List<ProjectKey> listAllProjectKeys(long projectNo) {
        Criteria criteria = getSession().createCriteria(ProjectKey.class);
        criteria.add(Restrictions.eq("projectNo", projectNo));
        return criteria.list();
    }

    @Override
    public List<ProjectKey> listAllProjectKeys(long projectNo, @NotNull final String fileName) {
        Criteria criteria = getSession().createCriteria(ProjectKey.class);
        criteria.add(Restrictions.eq("projectNo", projectNo));
        criteria.add(Restrictions.eq("fileName", fileName));
        return criteria.list();
    }

    @Override
    public void delete(@NotNull final ProjectKey projectKey) {
        getSession().delete(projectKey);
    }
}
