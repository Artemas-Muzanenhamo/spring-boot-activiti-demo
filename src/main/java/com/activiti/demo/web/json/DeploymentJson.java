package com.activiti.demo.web.json;

import java.util.Date;

public class DeploymentJson {
    private String id;
    private String name;
    private Date deploymentTime;
    private String category;
    private String key;
    private String tenantId;

    public DeploymentJson() { }

    public DeploymentJson(String id, String name, Date deploymentTime, String category, String key, String tenantId) {
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

    public String getName() {
        return name;
    }

    public Date getDeploymentTime() {
        return deploymentTime;
    }

    public String getCategory() {
        return category;
    }

    public String getKey() {
        return key;
    }

    public String getTenantId() {
        return tenantId;
    }
}
