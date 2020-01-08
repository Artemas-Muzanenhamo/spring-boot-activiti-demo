package com.activiti.demo.web.converter;

import com.activiti.demo.exception.InvalidDeploymentIdException;
import com.activiti.demo.web.json.DeploymentIdJson;
import com.activiti.demo.model.DeploymentId;

import java.util.Optional;

public class DeploymentIdConverter {

    private static final String MESSAGE = "DeploymentId is not valid";

    public static DeploymentId deploymentIdJsonToDto(DeploymentIdJson deploymentIdJson) {
        return Optional.ofNullable(deploymentIdJson)
                .map(DeploymentIdJson::getDeploymentId)
                .filter(DeploymentIdConverter::nonEmpty)
                .map(DeploymentId::new)
                .orElseThrow(() -> new InvalidDeploymentIdException(MESSAGE));
    }

    private static boolean nonEmpty(String deploymentId) {
        return !deploymentId.isEmpty();
    }
}
