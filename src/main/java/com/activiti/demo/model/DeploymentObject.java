package com.activiti.demo.model;

import java.util.Date;

public class DeploymentObject {

    private String id;
    private String name;
    private Date deploymentTime;
    private String category;
    private String key;
    private String tenantId;

    public DeploymentObject() { }

    public DeploymentObject(String id, String name, Date deploymentTime, String category, String key, String tenantId) {
        this.id = id;
        this.name = name;
        this.deploymentTime = deploymentTime;
        this.category = category;
        this.key = key;
        this.tenantId = tenantId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDeploymentTime() {
        return deploymentTime;
    }

    public void setDeploymentTime(Date deploymentTime) {
        this.deploymentTime = deploymentTime;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }
}
