package com.mgatelabs.lunar.model.service;

import com.mgatelabs.lunar.model.dao.ProjectFileNameDao;
import com.mgatelabs.lunar.model.entities.ProjectFileName;
import com.sun.istack.internal.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by mmgate on 11/3/15.
 */
@Service("projectFileNameService")
@Transactional
public class ProjectFileNameServiceImpl implements ProjectFileNameService {

    @Autowired
    private ProjectFileNameDao dao;

    @Override
    public void save(@NotNull ProjectFileName fileName) {
        dao.save(fileName);
    }

    @Override
    public List<ProjectFileName> listAll(long projectNo) {
        return dao.listAll(projectNo);
    }

    @Override
    public void update(@NotNull ProjectFileName fileName) {
        dao.update(fileName);
    }
}
