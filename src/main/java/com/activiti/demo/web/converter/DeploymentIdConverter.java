package com.activiti.demo.web.converter;

import com.activiti.demo.exception.InvalidDeploymentIdException;
import com.activiti.demo.web.json.DeploymentIdJson;
import com.activiti.demo.model.DeploymentId;

import java.util.Optional;

public class DeploymentIdConverter {
    public static DeploymentId deploymentIdJsonToDto(DeploymentIdJson deploymentIdJson) {
        return Optional.ofNullable(deploymentIdJson)
                .map(DeploymentIdJson::getDeploymentId)
                .map(DeploymentId::new)
                .orElseThrow(() -> new InvalidDeploymentIdException("DeploymentId is not valid"));
    }
}
