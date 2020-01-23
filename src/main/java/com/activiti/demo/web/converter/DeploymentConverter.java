package com.activiti.demo.web.converter;

import com.activiti.demo.model.DeploymentObject;
import com.activiti.demo.web.json.DeploymentJson;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class DeploymentConverter {
    public static List<DeploymentJson> toDeploymentJsonList(List<DeploymentObject> deploymentObjects) {
        return deploymentObjects.stream()
                .map(deployment -> new DeploymentJson(
                        deployment.getId(),
                        deployment.getName(),
                        deployment.getDeploymentTime(),
                        deployment.getCategory(),
                        deployment.getKey(),
                        deployment.getTenantId())
                )
                .collect(toList());
    }
}
