package com.mgatelabs.lunar.model.entities;

import com.mgatelabs.lunar.utils.fields.EditableAnnotation;
import com.mgatelabs.lunar.utils.fields.EditableTypes;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by mmgate on 11/3/15.
 */
@Entity
@Table(name="projectLanguages", uniqueConstraints = @UniqueConstraint(name = "USR_PROLANG_PRO_LANG",columnNames = {"projectNo", "languageId"}))
public class ProjectLanguage {

    private long projectLanguageNo;
    private long projectNo;
    @EditableAnnotation(title = "Project Language", type = EditableTypes.LANGUAGE, length = 6)
    private String languageId;
    private ProstaType prosta;


    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy="increment")
    public long getProjectLanguageNo() {
        return projectLanguageNo;
    }

    public void setProjectLanguageNo(long projectLanguageNo) {
        this.projectLanguageNo = projectLanguageNo;
    }

    @Column(nullable = false)
    public long getProjectNo() {
        return projectNo;
    }

    public void setProjectNo(long projectNo) {
        this.projectNo = projectNo;
    }

    @Column(length = 6, nullable = false)
    public String getLanguageId() {
        return languageId;
    }

    public void setLanguageId(String languageId) {
        this.languageId = languageId;
    }

    @Column(length = 10)
    @Enumerated(EnumType.STRING)
    public ProstaType getProsta() {
        return prosta;
    }

    public void setProsta(ProstaType prosta) {
        this.prosta = prosta;
    }
}
