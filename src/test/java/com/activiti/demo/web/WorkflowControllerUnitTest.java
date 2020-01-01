package com.activiti.demo.web;

import com.activiti.demo.model.*;
import com.activiti.demo.service.WorkflowService;
import com.activiti.demo.web.json.*;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class WorkflowControllerUnitTest {
    private static final String PROCESS_NAME = "some-process-name";
    private static final String PROCESS_INSTANCE_KEY = "some-process-instance-key";
    private static final String TASK_ASSIGNEE = "some-task-assignee";
    private static final String TASK_ID = "123";
    public static final String DEPLOYMENT_ID = "43253";
    private WorkflowController workflowController;
    @Mock
    private WorkflowService workflowService;
    @Mock
    private ProcessEngine processEngine;
    @Mock
    private RepositoryService repositoryService;
    @Mock
    private TaskObject taskObject;
    @Mock
    private DeploymentObject deploymentObject;

    @BeforeEach
    void setUp() {
        workflowController = new WorkflowController(workflowService);
    }

    @Test
    @DisplayName("Should deploy a process given a valid ProcessNameJson")
    void testDeploy() {
        ProcessNameJson processNameJson = new ProcessNameJson(PROCESS_NAME);
        ProcessName processName = new ProcessName(PROCESS_NAME);

        workflowController.deploy(processNameJson);

        verify(workflowService).deployProcess(processName);
    }

    @Test
    @DisplayName("Should start a task given a valid ProcessInstanceKey")
    void testStartTask() {
        ProcessInstanceKeyJson processInstanceKeyJson = new ProcessInstanceKeyJson(PROCESS_INSTANCE_KEY);
        ProcessInstanceKey processInstanceKey = new ProcessInstanceKey(PROCESS_INSTANCE_KEY);

        workflowController.start(processInstanceKeyJson);

        verify(workflowService).startProcess(processInstanceKey);
    }

    @Test
    @DisplayName("Should retrieve tasks given a valid Task Assignee")
    void findTasks() {
        TaskAssigneeJson taskAssigneeJson = new TaskAssigneeJson(TASK_ASSIGNEE);
        TaskAssignee taskAssignee = new TaskAssignee(TASK_ASSIGNEE);
        List<TaskObject> taskObjects = List.of(taskObject);
        given(taskObject.getAssignee()).willReturn(TASK_ASSIGNEE);
        given(workflowService.findTaskByAssignee(taskAssignee)).willReturn(taskObjects);

        List<TaskObject> tasks = workflowController.findTasks(taskAssigneeJson);

        assertThat(tasks).isNotNull();
        assertThat(tasks).hasSize(1);

        TaskObject task = tasks.get(0);
        assertThat(task.getAssignee()).isEqualTo(TASK_ASSIGNEE);

        verify(taskObject).getAssignee();
        verify(workflowService).findTaskByAssignee(taskAssignee);
    }

    @Test
    @DisplayName("Should retrieve a task given a valid Task Id")
    void findTasksByTaskId() {
        TaskIdJson taskIdJson = new TaskIdJson(TASK_ID);
        TaskId taskId = new TaskId(TASK_ID);
        given(workflowService.findTaskByTaskId(taskId)).willReturn(taskObject);
        given(taskObject.getId()).willReturn(TASK_ID);

        TaskObject task = workflowController.findTaskById(taskIdJson);

        assertThat(task).isNotNull();
        assertThat(task.getId()).isEqualTo(TASK_ID);

        verify(workflowService).findTaskByTaskId(taskId);
        verify(taskObject).getId();
    }

    @Test
    @DisplayName("Should retrieve all tasks")
    void findAllTasks() {
        List<TaskObject> taskObjects = List.of(this.taskObject);
        given(workflowService.findAllTasks()).willReturn(taskObjects);

        List<TaskObject> tasks = workflowController.getAllTasks();

        assertThat(tasks).isNotEmpty();
        TaskObject task = tasks.get(0);
        assertThat(task).isEqualTo(taskObject);
        verify(workflowService).findAllTasks();
    }

    @Test
    @DisplayName("Should complete a task given a valid Task Id")
    void completeTaskGivenATaskId() {
        TaskIdJson taskIdJson = new TaskIdJson(TASK_ID);
        TaskId taskId = new TaskId(TASK_ID);

        workflowController.completeTask(taskIdJson);

        verify(workflowService).completeTask(taskId);
    }

    @Test
    @DisplayName("Should get all deployed processes")
    void getAllDeployedProcesses() {
        List<DeploymentObject> deployedProcesses = List.of(this.deploymentObject);
        given(workflowService.findAllDeployedProcesses()).willReturn(deployedProcesses);

        List<DeploymentObject> processes = workflowController.getAllDeployedProcesses();

        assertThat(processes).isNotEmpty();
        verify(workflowService).findAllDeployedProcesses();
    }

    @Test
    @DisplayName("Should delete a deployed process given a valid Deployment Id")
    void deleteDeployedProcess() {
        DeploymentIdJson deploymentIdJson = new DeploymentIdJson(DEPLOYMENT_ID);
        DeploymentId deploymentId = new DeploymentId(DEPLOYMENT_ID);

        workflowController.deleteDeployedProcess(deploymentIdJson);

        verify(workflowService).deleteDeployedProcess(deploymentId);
    }
}
