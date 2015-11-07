package com.mgatelabs.lunar.model.dao;

import com.mgatelabs.lunar.model.entities.Language;
import com.sun.istack.internal.NotNull;

import java.util.List;

/**
 * Created by Michael Glen Fuller Jr on 10/24/2015.
 */
public interface LanguageDao {
    void saveLanguage(@NotNull Language language);
    void updateLanguage(@NotNull Language language);
    List<Language> listAllLanguages();
}
