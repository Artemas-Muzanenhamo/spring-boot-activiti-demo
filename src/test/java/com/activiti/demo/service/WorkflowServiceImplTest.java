package com.activiti.demo.service;

import com.activiti.demo.model.*;
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

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class WorkflowServiceImplTest {
    private static final String TASK_ASSIGNEE = "some-task-assignee";
    private static final String TASK_ID = "1234";
    private static final String DEPLOYMENT_ID = "43254";
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
    @DisplayName("Should deploy a process given a valid Process Name")
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

    @Test
    @DisplayName("Should start a process given a valid Process Instance Key")
    void startProcess() {
        ProcessInstanceKey processInstanceKey = new ProcessInstanceKey("some-process-instance-key");
        given(processEngine.getRuntimeService()).willReturn(runtimeService);
        given(runtimeService.startProcessInstanceByKey(processInstanceKey.getProcessInstanceKey()))
                .willReturn(processInstance);

        workflowService.startProcess(processInstanceKey);

        verify(processEngine).getRuntimeService();
        verify(runtimeService).startProcessInstanceByKey(anyString());
    }

    @Test
    @DisplayName("Should return tasks given a valid Task Assignee")
    void findTasksByTaskAssignee() {
        TaskAssignee taskAssignee = new TaskAssignee(TASK_ASSIGNEE);
        List<Task> tasks = List.of(task);
        given(task.getAssignee()).willReturn(TASK_ASSIGNEE);
        given(processEngine.getTaskService()).willReturn(taskService);
        given(taskService.createTaskQuery()).willReturn(taskQuery);
        given(taskQuery.taskAssignee(taskAssignee.getTaskAssignee())).willReturn(taskQuery);
        given(taskQuery.list()).willReturn(tasks);

        List<TaskObject> taskByAssignee = workflowService.findTaskByAssignee(taskAssignee);

        assertThat(taskByAssignee).isNotEmpty();
        TaskObject taskObject = taskByAssignee.get(0);
        assertThat(taskObject).isNotNull();
        assertThat(taskObject.getAssignee()).isEqualTo(TASK_ASSIGNEE);
        verify(processEngine).getTaskService();
        verify(taskService).createTaskQuery();
        verify(taskQuery).taskAssignee(anyString());
        verify(taskQuery).list();
        verify(task).getAssignee();
    }

    @Test
    @DisplayName("Should return a task given a valid Task Id")
    void findTaskByTaskId() {
        TaskId taskId = new TaskId(TASK_ID);
        given(processEngine.getTaskService()).willReturn(taskService);
        given(taskService.createTaskQuery()).willReturn(taskQuery);
        given(taskQuery.taskId(taskId.getTaskId())).willReturn(taskQuery);

        TaskObject task = workflowService.findTaskByTaskId(taskId);

        assertThat(task).isNotNull();

        verify(processEngine).getTaskService();
        verify(taskService).createTaskQuery();
        verify(taskQuery).taskId(anyString());
    }

    @Test
    @DisplayName("Should return all tasks")
    void findAllTasks() {
        given(processEngine.getTaskService()).willReturn(taskService);
        given(taskService.createTaskQuery()).willReturn(taskQuery);
        given(taskQuery.list()).willReturn(List.of(task));

        List<TaskObject> tasks = workflowService.findAllTasks();

        assertThat(tasks).isNotEmpty();
        verify(processEngine).getTaskService();
        verify(taskService).createTaskQuery();
        verify(taskQuery).list();
    }

    @Test
    @DisplayName("Should complete a task given a valid Task Id")
    void completeTaskByTaskId() {
        TaskId taskId = new TaskId(TASK_ID);
        given(processEngine.getTaskService()).willReturn(taskService);

        workflowService.completeTask(taskId);

        verify(processEngine).getTaskService();
        verify(taskService).complete(TASK_ID);
    }

    @Test
    @DisplayName("Should return all deployed processes")
    void findAllDeployedProcesses() {
        List<Deployment> deployments = List.of(this.deployment);
        given(processEngine.getRepositoryService()).willReturn(repositoryService);
        given(repositoryService.createDeploymentQuery()).willReturn(deploymentQuery);
        given(deploymentQuery.list()).willReturn(deployments);

        List<DeploymentObject> processes = workflowService.findAllDeployedProcesses();

        assertThat(processes).isNotEmpty();

        verify(processEngine).getRepositoryService();
        verify(repositoryService).createDeploymentQuery();
        verify(deploymentQuery).list();
    }

    @Test
    @DisplayName("Should delete a deployed process given a Deployement Id")
    void deleteDeployedProcess() {
        DeploymentId deploymentId = new DeploymentId(DEPLOYMENT_ID);

        workflowService.deleteDeployedProcess(deploymentId);

        verify(repositoryService).deleteDeployment(DEPLOYMENT_ID);
    }
}