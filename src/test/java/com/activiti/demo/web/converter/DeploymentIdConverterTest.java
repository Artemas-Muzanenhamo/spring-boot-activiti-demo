package com.activiti.demo.web.converter;

import com.activiti.demo.exception.InvalidDeploymentIdException;
import com.activiti.demo.model.DeploymentId;
import com.activiti.demo.web.json.DeploymentIdJson;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.activiti.demo.web.converter.DeploymentIdConverter.deploymentIdJsonToDto;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

    @Test
    @DisplayName("Should throw an exception when the DeploymentId value is null")
    void testWhenDeploymentIdValueIsNull() {
        DeploymentIdJson deploymentIdJson = new DeploymentIdJson(null);

        InvalidDeploymentIdException exception = assertThrows(InvalidDeploymentIdException.class, () -> deploymentIdJsonToDto(deploymentIdJson));

        assertThat(exception).hasMessage("DeploymentId is not valid");
    }

    @Test
    @DisplayName("Should throw an exception when the DeploymentId is null")
    void testWhenDeploymentIdIsNull() {
        DeploymentIdJson deploymentIdJson = new DeploymentIdJson(null);

        InvalidDeploymentIdException exception = assertThrows(InvalidDeploymentIdException.class, () -> deploymentIdJsonToDto(deploymentIdJson));

        assertThat(exception).hasMessage("DeploymentId is not valid");
    }
}