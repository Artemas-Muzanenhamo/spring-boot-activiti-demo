package com.activiti.demo.converter;

import com.activiti.demo.json.DeploymentIdJson;
import com.activiti.demo.model.DeploymentId;

public class DeploymentIdConverter {
    public static DeploymentId deploymentIdJsonToDto(DeploymentIdJson deploymentIdJson) {
        return new DeploymentId(deploymentIdJson.getDeploymentId());
    }
}
