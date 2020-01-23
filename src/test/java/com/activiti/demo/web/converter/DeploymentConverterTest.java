package com.activiti.demo.web.converter;

import com.activiti.demo.model.DeploymentObject;
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
        List<DeploymentObject> deploymentObjects = List.of(new DeploymentObject());

        List<DeploymentJson> deploymentJsonList = toDeploymentJsonList(deploymentObjects);

        assertThat(deploymentJsonList).isNotNull();
        DeploymentJson deploymentJson = deploymentJsonList.get(0);
        DeploymentObject deploymentObject = deploymentObjects.get(0);
        assertThat(deploymentJson.getId()).isEqualTo(deploymentObject.getId());
        assertThat(deploymentJson.getName()).isEqualTo(deploymentObject.getName());
        assertThat(deploymentJson.getDeploymentTime()).isEqualTo(deploymentObject.getDeploymentTime());
        assertThat(deploymentJson.getCategory()).isEqualTo(deploymentObject.getCategory());
        assertThat(deploymentJson.getKey()).isEqualTo(deploymentObject.getKey());
        assertThat(deploymentJson.getTenantId()).isEqualTo(deploymentObject.getTenantId());
    }

}