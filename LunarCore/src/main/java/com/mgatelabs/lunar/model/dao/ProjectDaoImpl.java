package com.mgatelabs.lunar.model.dao;

import com.mgatelabs.lunar.model.entities.Project;
import com.sun.istack.internal.NotNull;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Michael Glen Fuller Jr on 10/24/2015.
 */
@Repository("projectDao")
public class ProjectDaoImpl extends AbstractDao implements ProjectDao {

    public void saveProject(@NotNull final Project project) {
        persist(project);
    }

    public List<Project> findAllProjects() {
        Criteria criteria = getSession().createCriteria(Project.class);
        return (List<Project>) criteria.list();
    }

    public Project findByKey(@NotNull final String key) {
        Criteria criteria = getSession().createCriteria(Project.class);
        criteria.add(Restrictions.eq("key", key));
        return (Project) criteria.uniqueResult();
    }

    public void updateProject(Project project) {
        getSession().update(project);
    }
}
