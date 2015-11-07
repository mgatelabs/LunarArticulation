package com.mgatelabs.lunar.model.entities;

import com.mgatelabs.lunar.utils.fields.EditableAnnotation;
import com.mgatelabs.lunar.utils.fields.EditableTypes;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by Michael Glen Fuller Jr on 10/25/2015.
 */
@Entity
@Table(name="projectVersions")
public class ProjectVersion {

    private long projectVersionNo;
    private long projectNo;
    @EditableAnnotation(title = "Version Codename", type = EditableTypes.STRING, length = 32)
    private String codename;
    @EditableAnnotation(title = "Major #", type = EditableTypes.INT, minValue = 1, maxValue = 256)
    private int major;
    @EditableAnnotation(title = "Minor #", type = EditableTypes.INT, minValue = 0, maxValue = 256)
    private int minor;
    @EditableAnnotation(title = "Build #", type = EditableTypes.INT, minValue = 0, maxValue = 1024)
    private int build;
    private ProstaType prosta;

    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy="increment")
    public long getProjectVersionNo() {
        return projectVersionNo;
    }

    public void setProjectVersionNo(long projectVersionNo) {
        this.projectVersionNo = projectVersionNo;
    }

    @Column(nullable = false)
    public long getProjectNo() {
        return projectNo;
    }

    public void setProjectNo(long projectNo) {
        this.projectNo = projectNo;
    }

    @Column(length = 32)
    public String getCodename() {
        return codename;
    }

    public void setCodename(String codename) {
        this.codename = codename;
    }

    @Column(nullable = false)
    public int getMajor() {
        return major;
    }

    public void setMajor(int major) {
        this.major = major;
    }

    @Column(nullable = false)
    public int getMinor() {
        return minor;
    }

    public void setMinor(int minor) {
        this.minor = minor;
    }

    @Column(nullable = false)
    public int getBuild() {
        return build;
    }

    public void setBuild(int build) {
        this.build = build;
    }

    @Column(length = 8)
    @Enumerated(EnumType.STRING)
    public ProstaType getProsta() {
        return prosta;
    }

    public void setProsta(ProstaType prosta) {
        this.prosta = prosta;
    }
}
