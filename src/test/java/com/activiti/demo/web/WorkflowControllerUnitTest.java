package com.activiti.demo.web;

import com.activiti.demo.model.*;
import com.activiti.demo.service.WorkflowService;
import com.activiti.demo.web.json.*;
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
    private static final String DEPLOYMENT_ID = "43253";
    private WorkflowController workflowController;
    @Mock
    private WorkflowService workflowService;
    @Mock
    private TaskDTO taskDTO;
    @Mock
    private DeploymentDTO deploymentDTO;

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
        List<TaskDTO> taskDTOS = List.of(taskDTO);
        given(taskDTO.getAssignee()).willReturn(TASK_ASSIGNEE);
        given(workflowService.findTaskByAssignee(taskAssignee)).willReturn(taskDTOS);

        List<TaskJson> tasks = workflowController.findTasks(taskAssigneeJson);

        assertThat(tasks).isNotNull();
        assertThat(tasks).hasSize(1);

        TaskJson task = tasks.get(0);
        assertThat(task.getAssignee()).isEqualTo(TASK_ASSIGNEE);

        verify(taskDTO).getAssignee();
        verify(workflowService).findTaskByAssignee(taskAssignee);
    }

    @Test
    @DisplayName("Should retrieve a task given a valid Task Id")
    void findTasksByTaskId() {
        TaskIdJson taskIdJson = new TaskIdJson(TASK_ID);
        TaskId taskId = new TaskId(TASK_ID);
        given(workflowService.findTaskByTaskId(taskId)).willReturn(taskDTO);
        given(taskDTO.getId()).willReturn(TASK_ID);

        TaskJson task = workflowController.findTaskById(taskIdJson);

        assertThat(task).isNotNull();
        assertThat(task.getId()).isEqualTo(TASK_ID);

        verify(workflowService).findTaskByTaskId(taskId);
        verify(taskDTO).getId();
    }

    @Test
    @DisplayName("Should retrieve all tasks")
    void findAllTasks() {
        List<TaskDTO> taskDTOS = List.of(taskDTO);
        given(workflowService.findAllTasks()).willReturn(taskDTOS);

        List<TaskJson> tasks = workflowController.getAllTasks();

        assertThat(tasks).isNotEmpty();
        TaskJson task = tasks.get(0);
        assertThat(task.getId()).isEqualTo(taskDTO.getId());
        assertThat(task.getName()).isEqualTo(taskDTO.getName());
        assertThat(task.getAssignee()).isEqualTo(taskDTO.getAssignee());
        assertThat(task.getDescription()).isEqualTo(taskDTO.getDescription());
        assertThat(task.getExecutionId()).isEqualTo(taskDTO.getExecutionId());
        assertThat(task.getOwner()).isEqualTo(taskDTO.getOwner());
        assertThat(task.getProcessInstanceId()).isEqualTo(taskDTO.getProcessInstanceId());
        assertThat(task.getCreateTime()).isEqualTo(taskDTO.getCreateTime());
        assertThat(task.getTaskDefinitionKey()).isEqualTo(taskDTO.getTaskDefinitionKey());
        assertThat(task.getDueDate()).isEqualTo(taskDTO.getDueDate());
        assertThat(task.getParentTaskId()).isEqualTo(taskDTO.getParentTaskId());
        assertThat(task.getTenantId()).isEqualTo(taskDTO.getTenantId());
        assertThat(task.getTaskLocalVariables()).isEqualTo(taskDTO.getTaskLocalVariables());
        assertThat(task.getProcessVariables()).isEqualTo(taskDTO.getProcessVariables());
        assertThat(task.getProcessDefinitionId()).isEqualTo(taskDTO.getProcessDefinitionId());
        assertThat(task.getDelegationState()).isEqualTo(taskDTO.getDelegationState());
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
        List<DeploymentDTO> deployedProcesses = List.of(deploymentDTO);
        given(workflowService.findAllDeployedProcesses()).willReturn(deployedProcesses);

        List<DeploymentJson> processes = workflowController.getAllDeployedProcesses();

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
