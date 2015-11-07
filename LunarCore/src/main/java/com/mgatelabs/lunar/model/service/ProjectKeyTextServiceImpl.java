package com.mgatelabs.lunar.model.service;

import com.mgatelabs.lunar.model.dao.ProjectKeyTextDao;
import com.mgatelabs.lunar.model.entities.ProjectKeyText;
import com.sun.istack.internal.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Michael Glen Fuller Jr on 10/25/2015.
 */
@Service("projectKeyTextService")
@Transactional
public class ProjectKeyTextServiceImpl implements ProjectKeyTextService {

    @Autowired
    private ProjectKeyTextDao dao;

    @Override
    public void saveProjectKeyText(ProjectKeyText projectKeyText) {
        dao.saveProjectKeyText(projectKeyText);
    }

    @Override
    public void updateProjectKeyText(ProjectKeyText projectKeyText) {
        dao.updateProjectKeyText(projectKeyText);
    }

    @Override
    public List<ProjectKeyText> listAllProjectKeyTexts(long projectNo) {
        return dao.listAllProjectKeyTexts(projectNo);
    }

    @Override
    public void delete(@NotNull final ProjectKeyText projectKeyText) {
        dao.delete(projectKeyText);
    }
}
