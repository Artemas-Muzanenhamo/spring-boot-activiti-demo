package com.activiti.demo.web;

import com.activiti.demo.model.ProcessInstanceKey;
import com.activiti.demo.model.ProcessName;
import com.activiti.demo.model.TaskAssignee;
import com.activiti.demo.model.TaskObject;
import com.activiti.demo.service.WorkflowService;
import com.activiti.demo.web.json.ProcessInstanceKeyJson;
import com.activiti.demo.web.json.ProcessNameJson;
import com.activiti.demo.web.json.TaskAssigneeJson;
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
    private WorkflowController workflowController;
    @Mock
    private WorkflowService workflowService;
    @Mock
    private ProcessEngine processEngine;
    @Mock
    private RepositoryService repositoryService;

    @BeforeEach
    void setUp() {
        workflowController = new WorkflowController(processEngine, repositoryService, workflowService);
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
        TaskObject taskObject = new TaskObject("some-id", "some-name", TASK_ASSIGNEE);
        List<TaskObject> taskObjects = List.of(taskObject);
        given(workflowService.findTaskByAssignee(taskAssignee)).willReturn(taskObjects);

        List<TaskObject> tasks = workflowController.findTasks(taskAssigneeJson);

        assertThat(tasks).isNotNull();
        assertThat(tasks).hasSize(1);
        TaskObject task = tasks.get(0);
        assertThat(task.getAssignee()).isEqualTo(TASK_ASSIGNEE);
    }
}
