package com.activiti.demo.model;

import java.util.Date;

public class DeploymentObject {

    private String getId;
    private String getName;
    private Date getDeploymentTime;
    private String getCategory;
    private String getKey;
    private String getTenantId;

    public DeploymentObject() { }

    public DeploymentObject(String getId, String getName, Date getDeploymentTime, String getCategory, String getKey, String getTenantId) {
        this.getId = getId;
        this.getName = getName;
        this.getDeploymentTime = getDeploymentTime;
        this.getCategory = getCategory;
        this.getKey = getKey;
        this.getTenantId = getTenantId;
    }

    public String getGetId() {
        return getId;
    }

    public void setGetId(String getId) {
        this.getId = getId;
    }

    public String getGetName() {
        return getName;
    }

    public void setGetName(String getName) {
        this.getName = getName;
    }

    public Date getGetDeploymentTime() {
        return getDeploymentTime;
    }

    public void setGetDeploymentTime(Date getDeploymentTime) {
        this.getDeploymentTime = getDeploymentTime;
    }

    public String getGetCategory() {
        return getCategory;
    }

    public void setGetCategory(String getCategory) {
        this.getCategory = getCategory;
    }

    public String getGetKey() {
        return getKey;
    }

    public void setGetKey(String getKey) {
        this.getKey = getKey;
    }

    public String getGetTenantId() {
        return getTenantId;
    }

    public void setGetTenantId(String getTenantId) {
        this.getTenantId = getTenantId;
    }
}
