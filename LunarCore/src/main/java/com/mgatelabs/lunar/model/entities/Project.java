package com.mgatelabs.lunar.model.entities;

import com.mgatelabs.lunar.utils.fields.EditableAnnotation;
import com.mgatelabs.lunar.utils.fields.EditableTypes;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by Michael Glen Fuller Jr on 10/24/2015.
 */
@Entity
@Table(name="projects")
public class Project {

    private long projectNo;

    @EditableAnnotation(title = "Project Title", type = EditableTypes.STRING, length = 32)
    private String title;

    @EditableAnnotation(title = "Project Key", type = EditableTypes.KEY, length = 16)
    private String key;

    @EditableAnnotation(title = "Development Language", type = EditableTypes.LANGUAGE, length = 6)
    private String developmentKey;

    @EditableAnnotation(title = "Main Language", type = EditableTypes.LANGUAGE, length = 6)
    private String mainKey;

    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy="increment")
    public long getProjectNo() {
        return projectNo;
    }

    public void setProjectNo(long projectNo) {
        this.projectNo = projectNo;
    }

    @Column(name = "key", unique = true, nullable = false, length = 16)
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Column(name = "title", unique = true, nullable = false, length = 32)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(name = "devel", length = 6)
    public String getDevelopmentKey() {
        return developmentKey;
    }

    public void setDevelopmentKey(String developmentKey) {
        this.developmentKey = developmentKey;
    }
    @Column(name = "base", length = 6)
    public String getMainKey() {
        return mainKey;
    }

    public void setMainKey(String mainKey) {
        this.mainKey = mainKey;
    }
}
