package com.activiti.demo.converter;

import com.activiti.demo.web.json.DeploymentIdJson;
import com.activiti.demo.model.DeploymentId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.activiti.demo.converter.DeploymentIdConverter.deploymentIdJsonToDto;
import static org.assertj.core.api.Assertions.assertThat;

class DeploymentIdConverterTest {

    private static final String DEPLOYMENT_ID = "1323";

    @Test
    @DisplayName("Should convert DeploymentIdJson to DeploymentId DTO")
    void convertDeploymentIdJsonToDto() {
        DeploymentIdJson deploymentIdJson = new DeploymentIdJson(DEPLOYMENT_ID);

        DeploymentId deploymentId = deploymentIdJsonToDto(deploymentIdJson);

        assertThat(deploymentId).isNotNull();
        assertThat(deploymentId.getDeploymentId()).isEqualTo(DEPLOYMENT_ID);
    }
}