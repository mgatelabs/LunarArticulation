package com.mgatelabs.lunar.model.service;

import com.mgatelabs.lunar.model.dao.ProjectLanguageDao;
import com.mgatelabs.lunar.model.entities.ProjectLanguage;
import com.sun.istack.internal.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by mmgate on 11/3/15.
 */
@Service("ProjectLanguageService")
@Transactional
public class ProjectLanguageServiceImpl implements ProjectLanguageService {

    @Autowired
    private ProjectLanguageDao dao;

    @Override
    public void save(@NotNull ProjectLanguage language) {
        dao.save(language);
    }

    @Override
    public List<ProjectLanguage> listAll(long projectNo) {
        return dao.listAll(projectNo);
    }

    @Override
    public void update(@NotNull ProjectLanguage language) {
        dao.update(language);
    }
}
