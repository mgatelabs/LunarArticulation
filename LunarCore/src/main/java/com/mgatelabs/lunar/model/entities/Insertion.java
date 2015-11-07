package com.mgatelabs.lunar.model.entities;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Michael Glen Fuller Jr on 10/25/2015.
 */
@Entity
@Table(name="insertions")
public class Insertion {
    private long insertionNo;
    private long projectNo;
    private long projectVersionNo;
    private Long projectSourceNo;
    private String filename;
    private InsertionType type;
    private Date inserted;

    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy="increment")
    public long getInsertionNo() {
        return insertionNo;
    }

    public void setInsertionNo(long insertionNo) {
        this.insertionNo = insertionNo;
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

    @Column(nullable = true)
    public Long getProjectSourceNo() {
        return projectSourceNo;
    }

    public void setProjectSourceNo(Long projectSourceNo) {
        this.projectSourceNo = projectSourceNo;
    }

    @Column(nullable = false, length = 255)
    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    @Column(length = 6, nullable = false)
    @Enumerated(EnumType.STRING)
    public InsertionType getType() {
        return type;
    }

    public void setType(InsertionType type) {
        this.type = type;
    }

    @Column(nullable = false)
    @Type(type = "timestamp")
    public Date getInserted() {
        return inserted;
    }

    public void setInserted(Date inserted) {
        this.inserted = inserted;
    }
}
