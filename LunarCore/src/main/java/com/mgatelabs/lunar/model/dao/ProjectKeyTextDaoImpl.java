package com.mgatelabs.lunar.model.dao;

import com.mgatelabs.lunar.model.entities.ProjectKeyText;
import com.sun.istack.internal.NotNull;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Michael Glen Fuller Jr on 10/25/2015.
 */
@Repository("projectKeyTextDao")
public class ProjectKeyTextDaoImpl extends AbstractDao implements ProjectKeyTextDao {
    @Override
    public void saveProjectKeyText(ProjectKeyText projectKeyText) {
        persist(projectKeyText);
    }

    @Override
    public void updateProjectKeyText(ProjectKeyText projectKeyText) {
        getSession().update(projectKeyText);
    }

    @Override
    public List<ProjectKeyText> listAllProjectKeyTexts(long projectNo) {
        Criteria criteria = getSession().createCriteria(ProjectKeyText.class);
        criteria.add(Restrictions.eq("projectNo", projectNo));
        return criteria.list();
    }

    @Override
    public void delete(@NotNull final ProjectKeyText projectKeyText) {
        getSession().delete(projectKeyText);
    }
}
