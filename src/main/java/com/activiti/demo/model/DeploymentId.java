package com.activiti.demo.model;

import java.util.Objects;

public class DeploymentId {
    private String deploymentId;

    public DeploymentId() { }

    public DeploymentId(String deploymentId) {
        this.deploymentId = deploymentId;
    }

    public String getDeploymentId() {
        return deploymentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeploymentId that = (DeploymentId) o;
        return Objects.equals(deploymentId, that.deploymentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(deploymentId);
    }
}
