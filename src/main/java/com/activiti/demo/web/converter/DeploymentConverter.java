package com.activiti.demo.web.converter;

import com.activiti.demo.model.DeploymentDTO;
import com.activiti.demo.web.json.DeploymentJson;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class DeploymentConverter {
    public static List<DeploymentJson> toDeploymentJsonList(List<DeploymentDTO> deploymentDTOS) {
        return Stream.ofNullable(deploymentDTOS)
                .flatMap(Collection::stream)
                .map(deployment -> new DeploymentJson(
                        deployment.getId(),
                        deployment.getName(),
                        deployment.getDeploymentTime(),
                        deployment.getCategory(),
                        deployment.getKey(),
                        deployment.getTenantId())
                ).collect(toList());
    }
}
