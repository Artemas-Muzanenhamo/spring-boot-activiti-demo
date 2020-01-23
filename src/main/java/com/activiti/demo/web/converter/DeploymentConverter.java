package com.activiti.demo.web.converter;

import com.activiti.demo.model.DeploymentObject;
import com.activiti.demo.web.json.DeploymentJson;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class DeploymentConverter {
    public static List<DeploymentJson> toDeploymentJsonList(List<DeploymentObject> deploymentObjects) {
        return deploymentObjects.stream()
                .map(deployment -> new DeploymentJson(
                        deployment.getGetId(),
                        deployment.getGetName(),
                        deployment.getGetDeploymentTime(),
                        deployment.getGetCategory(),
                        deployment.getGetKey(),
                        deployment.getGetTenantId())
                )
                .collect(toList());
    }
}
