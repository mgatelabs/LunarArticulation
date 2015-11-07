package com.mgatelabs.lunar.model.dao;

import com.mgatelabs.lunar.model.entities.ProjectLanguage;
import com.sun.istack.internal.NotNull;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by mmgate on 11/3/15.
 */
@Repository("ProjectLanguageDao")
public class ProjectLanguageDaoImpl extends AbstractDao implements ProjectLanguageDao {
    @Override
    public void save(@NotNull ProjectLanguage language) {
        persist(language);
    }

    @Override
    public List<ProjectLanguage> listAll(long projectNo) {
        Criteria criteria = getSession().createCriteria(ProjectLanguage.class);
        criteria.add(Restrictions.eq("projectNo", projectNo));
        return (List<ProjectLanguage>) criteria.list();
    }

    @Override
    public void update(@NotNull ProjectLanguage language) {
        getSession().update(language);
    }
}
