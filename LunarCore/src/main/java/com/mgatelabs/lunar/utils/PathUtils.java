package com.mgatelabs.lunar.utils;

import com.mgatelabs.lunar.model.entities.Project;

import java.io.File;

/**
 * Created by mmgate on 11/4/15.
 */
public class PathUtils {

    public static String getPathForExchangeProject(Project project) {
        return "." + File.separator + "exchange" + File.separator + project.getKey();
    }

    public static String getPathForExchangeLanguage(Project project, String languageId) {
        return getPathForExchangeProject(project) + File.separator + languageId + ".lproj";
    }

    public static String getPathForExchangeFileName(Project project, String languageId, String fileName) {
        return getPathForExchangeLanguage(project, languageId) + File.separator + fileName;
    }

}
