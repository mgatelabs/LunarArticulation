package com.mgatelabs.lunar.model.dao;

import com.mgatelabs.lunar.model.entities.ProjectFileName;
import com.sun.istack.internal.NotNull;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by mmgate on 11/3/15.
 */
@Repository("projectFileNameDao")
public class ProjectFileNameDaoImpl extends AbstractDao implements ProjectFileNameDao {

    @Override
    public void save(@NotNull ProjectFileName fileName) {
        persist(fileName);
    }

    @Override
    public List<ProjectFileName> listAll(long projectNo) {
        Criteria criteria = getSession().createCriteria(ProjectFileName.class);
        criteria.add(Restrictions.eq("projectNo", projectNo));
        return (List<ProjectFileName>) criteria.list();
    }

    @Override
    public void update(@NotNull ProjectFileName fileName) {
        getSession().update(fileName);
    }
}
