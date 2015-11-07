package com.mgatelabs.lunar.model.entities;

import com.mgatelabs.lunar.utils.fields.EditableAnnotation;
import com.mgatelabs.lunar.utils.fields.EditableTypes;
import com.sun.istack.internal.Nullable;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by Michael Glen Fuller Jr on 10/25/2015.
 */
@Entity
@Table(name="projectSources")
public class ProjectSource {
    private long projectSourceNo;
    private long projectNo;
    @EditableAnnotation(title = "Source Path", type = EditableTypes.STRING, length = 512)
    private String sourcePath;
    @EditableAnnotation(title = "File Extension", type = EditableTypes.STRING, length = 12)
    private String extension;
    private ProstaType prosta;

    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy="increment")
    public long getProjectSourceNo() {
        return projectSourceNo;
    }

    public void setProjectSourceNo(long projectSourceNo) {
        this.projectSourceNo = projectSourceNo;
    }

    @Column(nullable = false)
    public long getProjectNo() {
        return projectNo;
    }

    public void setProjectNo(long projectNo) {
        this.projectNo = projectNo;
    }

    @Column(length = 512, nullable = false)
    public String getSourcePath() {
        return sourcePath;
    }

    public void setSourcePath(String sourcePath) {
        this.sourcePath = sourcePath;
    }

    @Column(length = 12, nullable = false)
    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
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
