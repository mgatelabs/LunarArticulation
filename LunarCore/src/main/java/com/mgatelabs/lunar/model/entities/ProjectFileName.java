package com.mgatelabs.lunar.model.entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by mmgate on 11/2/15.
 */
@Entity
@Table(name="projectFileNames", uniqueConstraints = @UniqueConstraint(name = "USR_PFN_PRO_FN",columnNames = {"projectNo", "fileName"}) )
public class ProjectFileName {

    private long projectFileNameNo;

    private long projectNo;

    private String fileName;

    private ProstaType prosta;

    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy="increment")
    public long getProjectFileNameNo() {
        return projectFileNameNo;
    }

    public void setProjectFileNameNo(long projectFileNameNo) {
        this.projectFileNameNo = projectFileNameNo;
    }

    @Column
    public long getProjectNo() {
        return projectNo;
    }

    public void setProjectNo(long projectNo) {
        this.projectNo = projectNo;
    }

    @Column(nullable = false, length = 100)
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Column(length = 10, nullable = false)
    @Enumerated(EnumType.STRING)
    public ProstaType getProsta() {
        return prosta;
    }

    public void setProsta(ProstaType prosta) {
        this.prosta = prosta;
    }
}
