package com.activiti.demo.web.converter;

import com.activiti.demo.model.DeploymentDTO;
import com.activiti.demo.web.json.DeploymentJson;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.activiti.demo.web.converter.DeploymentConverter.toDeploymentJsonList;
import static org.assertj.core.api.Java6Assertions.assertThat;

class DeploymentConverterTest {

    @Test
    @DisplayName("Should convert a list of DeploymentObjects to a list of DeploymentJson")
    void convertToDeploymentJson() {
        List<DeploymentDTO> deploymentDTOS = List.of(new DeploymentDTO());

        List<DeploymentJson> deploymentJsonList = toDeploymentJsonList(deploymentDTOS);

        assertThat(deploymentJsonList).isNotNull();
        DeploymentJson deploymentJson = deploymentJsonList.get(0);
        DeploymentDTO deploymentDTO = deploymentDTOS.get(0);
        assertThat(deploymentJson.getId()).isEqualTo(deploymentDTO.getId());
        assertThat(deploymentJson.getName()).isEqualTo(deploymentDTO.getName());
        assertThat(deploymentJson.getDeploymentTime()).isEqualTo(deploymentDTO.getDeploymentTime());
        assertThat(deploymentJson.getCategory()).isEqualTo(deploymentDTO.getCategory());
        assertThat(deploymentJson.getKey()).isEqualTo(deploymentDTO.getKey());
        assertThat(deploymentJson.getTenantId()).isEqualTo(deploymentDTO.getTenantId());
    }

}