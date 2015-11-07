package com.mgatelabs.lunar.model.entities;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Michael Glen Fuller Jr on 10/25/2015.
 */
@Entity
@Table(name="projectKeys", uniqueConstraints = @UniqueConstraint(columnNames = {"projectNo", "fileName", "keyText"}))
public class ProjectKey implements Comparable<ProjectKey> {
    private long projectKeyNo;
    private long projectNo;
    private long projectVersionNo;
    private long insertionNo;
    private String fileName;
    private String keyText;

    private Date inserted;
    private Date updated;
    private Date closed;
    private String comment;
    private ProstaType prosta;

    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy="increment")
    public long getProjectKeyNo() {
        return projectKeyNo;
    }

    public void setProjectKeyNo(long projectKeyNo) {
        this.projectKeyNo = projectKeyNo;
    }

    @Column(nullable = false)
    public long getProjectNo() {
        return projectNo;
    }

    public void setProjectNo(long projectNo) {
        this.projectNo = projectNo;
    }

    @Column(nullable = false)
    public long getProjectVersionNo() {
        return projectVersionNo;
    }

    public void setProjectVersionNo(long projectVersionNo) {
        this.projectVersionNo = projectVersionNo;
    }

    @Column(nullable = false)
    public long getInsertionNo() {
        return insertionNo;
    }

    public void setInsertionNo(long insertionNo) {
        this.insertionNo = insertionNo;
    }

    @Column(nullable = false, length = 100)
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Column(nullable = false, length = 100)
    public String getKeyText() {
        return keyText;
    }

    public void setKeyText(String keyText) {
        this.keyText = keyText;
    }

    @Column
    @Type(type = "timestamp")
    public Date getInserted() {
        return inserted;
    }

    public void setInserted(Date inserted) {
        this.inserted = inserted;
    }

    @Column
    @Type(type = "timestamp")
    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    @Column
    @Type(type = "timestamp")
    public Date getClosed() {
        return closed;
    }

    public void setClosed(Date closed) {
        this.closed = closed;
    }

    @Column(length = 2048, nullable = false)
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Column(length = 10)
    @Enumerated(EnumType.STRING)
    public ProstaType getProsta() {
        return prosta;
    }

    public void setProsta(ProstaType prosta) {
        this.prosta = prosta;
    }

    @Override
    public int compareTo(ProjectKey o) {
        int fileNameCompare = fileName.compareTo(o.getFileName());
        if (fileNameCompare != 0) {
            return fileNameCompare;
        }
        int keyNameCompare = keyText.compareTo(o.getKeyText());
        return keyNameCompare;
    }
}
