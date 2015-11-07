package com.mgatelabs.lunar.model.entities;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Michael Glen Fuller Jr on 10/25/2015.
 */
@Entity
@Table(name="projectKeyText", uniqueConstraints = @UniqueConstraint(columnNames = {"projectNo", "languageId", "projectKeyNo"}))
public class ProjectKeyText {
    private long projectKeyTextNo;
    private long projectKeyNo;
    private long projectNo;
    private String languageId;
    private String text;
    private Date inserted;
    private Date updated;

    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy="increment")
    public long getProjectKeyTextNo() {
        return projectKeyTextNo;
    }

    public void setProjectKeyTextNo(long projectKeyTextNo) {
        this.projectKeyTextNo = projectKeyTextNo;
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

    @Column(nullable = false)
    public long getProjectKeyNo() {
        return projectKeyNo;
    }

    public void setProjectKeyNo(long projectKeyNo) {
        this.projectKeyNo = projectKeyNo;
    }

    @Column(nullable = false, length = 2048)
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Column(nullable = false)
    @Type(type = "timestamp")
    public Date getInserted() {
        return inserted;
    }

    public void setInserted(Date inserted) {
        this.inserted = inserted;
    }

    @Column(nullable = false)
    @Type(type = "timestamp")
    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }
}
