package com.mgatelabs.lunar.model.service;

import com.mgatelabs.lunar.model.entities.ProjectKeyText;
import com.sun.istack.internal.NotNull;

import java.util.List;

/**
 * Created by Michael Glen Fuller Jr on 10/25/2015.
 */
public interface ProjectKeyTextService {

    void saveProjectKeyText(ProjectKeyText projectKeyText);

    void updateProjectKeyText(ProjectKeyText projectKeyText);

    List<ProjectKeyText> listAllProjectKeyTexts(long projectNo);

    void delete(@NotNull final ProjectKeyText projectKeyText);

}
