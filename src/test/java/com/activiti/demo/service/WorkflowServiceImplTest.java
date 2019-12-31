package com.activiti.demo.service;

import com.activiti.demo.model.ProcessName;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.DeploymentQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class WorkflowServiceImplTest {
    @InjectMocks
    private WorkflowServiceImpl workflowService;
    @Mock
    private ProcessEngine processEngine;
    @Mock
    private RepositoryService repositoryService;
    @Mock
    private RuntimeService runtimeService;
    @Mock
    private TaskService taskService;
    @Mock
    private ProcessInstance processInstance;
    @Mock
    private TaskQuery taskQuery;
    @Mock
    private Task task;
    @Mock
    private DeploymentBuilder deploymentBuilder;
    @Mock
    private Deployment deployment;
    @Mock
    private DeploymentQuery deploymentQuery;

    @Test
    @DisplayName("Should deploy a process given a valid ProcessName")
    void deployProcess() {
        ProcessName processName = new ProcessName("some-process-name");
        given(processEngine.getRepositoryService()).willReturn(repositoryService);
        given(repositoryService.createDeployment()).willReturn(deploymentBuilder);
        given(deploymentBuilder.addClasspathResource(anyString())).willReturn(deploymentBuilder);
        given(deploymentBuilder.name(anyString())).willReturn(deploymentBuilder);
        given(deploymentBuilder.deploy()).willReturn(deployment);

        workflowService.deployProcess(processName);

        verify(processEngine).getRepositoryService();
        verify(repositoryService).createDeployment();
        verify(deploymentBuilder).addClasspathResource(anyString());
        verify(deploymentBuilder).name(anyString());
        verify(deploymentBuilder).deploy();
        verify(deployment).getId();
    }
}