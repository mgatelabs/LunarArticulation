package com.mgatelabs.lunar.model.dao;

import com.mgatelabs.lunar.model.entities.Language;
import com.sun.istack.internal.NotNull;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Michael Glen Fuller Jr on 10/24/2015.
 */
@Repository("languageDao")
public class LanguageDaoImpl extends AbstractDao implements LanguageDao {

    @Override
    public void saveLanguage(@NotNull Language language) {
        persist(language);
    }

    @Override
    public void updateLanguage(@NotNull Language language) {
        getSession().update(language);
    }

    @Override
    public List<Language> listAllLanguages() {
        Criteria criteria = getSession().createCriteria(Language.class);
        return criteria.list();
    }
}
