package com.mgatelabs.lunar.model.entities;

import com.mgatelabs.lunar.utils.fields.EditableAnnotation;
import com.mgatelabs.lunar.utils.fields.EditableTypes;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by Michael Glen Fuller JR on 11/17/15.
 * LunarArticulation For M-Gate Labs
 * Copyright 2015
 */
@Entity
@Table(name="deployments")
public class ProjectDeployment {
    private long projectDeploymentNo;
    private long projectNo;
    @EditableAnnotation(title = "Deployment Path", type = EditableTypes.STRING, length = 512)
    private String deploymentPath;
    private ProstaType prosta;

    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy="increment")
    public long getProjectDeploymentNo() {
        return projectDeploymentNo;
    }

    public void setProjectDeploymentNo(long projectDeploymentNo) {
        this.projectDeploymentNo = projectDeploymentNo;
    }

    @Column(nullable = false)
    public long getProjectNo() {
        return projectNo;
    }

    public void setProjectNo(long projectNo) {
        this.projectNo = projectNo;
    }

    @Column(length = 512, nullable = false)
    public String getDeploymentPath() {
        return deploymentPath;
    }

    public void setDeploymentPath(String deploymentPath) {
        this.deploymentPath = deploymentPath;
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
