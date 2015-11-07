package com.mgatelabs.lunar.model.service;

import com.mgatelabs.lunar.model.dao.LanguageDao;
import com.mgatelabs.lunar.model.entities.Language;
import com.sun.istack.internal.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Michael Glen Fuller Jr on 10/24/2015.
 */
@Service("languageService")
@Transactional
public class LanguageServiceImpl implements LanguageService {

    @Autowired
    private LanguageDao dao;

    @Override
    public void saveLanguage(@NotNull Language language) {
        dao.saveLanguage(language);
    }

    @Override
    public void updateLanguage(@NotNull Language language) {
        dao.updateLanguage(language);
    }

    @Override
    public List<Language> listAllLanguages() {
        return dao.listAllLanguages();
    }
}
