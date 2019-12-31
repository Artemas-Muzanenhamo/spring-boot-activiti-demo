package com.activiti.demo.web;

import net.minidev.json.JSONObject;
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
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Collections.emptyMap;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(WorkflowController.class)
@ActiveProfiles("test")
class WorkflowControllerTest {

    private static final String API_PROCESS_TASK_URL = "/api/process/task";
    private static final String API_PROCESS_TASKS_URL = "/api/process/tasks";
    private static final String API_PROCESS_COMPLETE_TASK_URL = "/api/process/complete-task";
    private static final String API_PROCESS_DEPLOYED_PROCESSES_URL = "/api/process/deployed-processes";
    private static final String API_PROCESS_DEPLOYED_PROCESSES_DELETE_URL = "/api/process/deployed-processes/delete";
    private static final String API_PROCESS_FIND_TASK_URL = "/api/process/find-task";
    private static final String API_PROCESS_START_TASK_URL = "/api/process/start-task";
    private static final String DEPLOYMENT_ID = "34578";
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ProcessEngine processEngine;
    @MockBean
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
    @DisplayName("Should deploy a process given a process name")
    void testDeployProcess() throws Exception {
        Map<String, String> processName = new HashMap<>();
        processName.put("processName", "say-hello-process");
        JSONObject jsonObject = new JSONObject(processName);
        given(processEngine.getRepositoryService()).willReturn(repositoryService);
        given(repositoryService.createDeployment()).willReturn(deploymentBuilder);
        given(deploymentBuilder.addClasspathResource(anyString())).willReturn(deploymentBuilder);
        given(deploymentBuilder.name(anyString())).willReturn(deploymentBuilder);
        given(deploymentBuilder.deploy()).willReturn(deployment);

        mockMvc.perform(post("/api/process/deploy")
                .accept(APPLICATION_JSON_UTF8_VALUE)
                .contentType(APPLICATION_JSON_UTF8_VALUE)
                .content(jsonObject.toJSONString()))
                .andExpect(status().isOk());

        verify(processEngine).getRepositoryService();
        verify(repositoryService).createDeployment();
        verify(deploymentBuilder).addClasspathResource(anyString());
        verify(deploymentBuilder).name(anyString());
        verify(deploymentBuilder).deploy();
        verify(deployment).getId();
    }

    @Test
    @DisplayName("Should throw a BAD_REQUEST exception when process name value passed is not valid")
    void throwExceptionWhenProcessNameValueIsInvalid() throws Exception {
        Map<String, String> processName = new HashMap<>();
        processName.put("processName", null);
        JSONObject jsonObject = new JSONObject(processName);

        mockMvc.perform(post("/api/process/deploy")
                .accept(APPLICATION_JSON_UTF8_VALUE)
                .contentType(APPLICATION_JSON_UTF8_VALUE)
                .content(jsonObject.toJSONString()))
                .andExpect(status().isBadRequest());

        verifyZeroInteractions(processEngine);
        verifyZeroInteractions(repositoryService);
        verifyZeroInteractions(deploymentBuilder);
        verifyZeroInteractions(deploymentBuilder);
        verifyZeroInteractions(deploymentBuilder);
        verifyZeroInteractions(deployment);
    }

    @Test
    @DisplayName("Should throw a BAD_REQUEST exception when process name passed is not valid")
    void throwExceptionWhenProcessNameIsInvalid() throws Exception {
        Map<String, String> processName = new HashMap<>();
        JSONObject jsonObject = new JSONObject(processName);

        mockMvc.perform(post("/api/process/deploy")
                .accept(APPLICATION_JSON_UTF8_VALUE)
                .contentType(APPLICATION_JSON_UTF8_VALUE)
                .content(jsonObject.toJSONString()))
                .andExpect(status().isBadRequest());

        verifyZeroInteractions(processEngine);
        verifyZeroInteractions(repositoryService);
        verifyZeroInteractions(deploymentBuilder);
        verifyZeroInteractions(deploymentBuilder);
        verifyZeroInteractions(deploymentBuilder);
        verifyZeroInteractions(deployment);
    }

    @Test
    @DisplayName("Should start a workflow given a processInstanceKey")
    void startWorkflowTest() throws Exception {
        Map<String, String> processInstanceKey = Map.of("processInstanceKey", "my-process");
        given(processEngine.getRuntimeService()).willReturn(runtimeService);
        given(runtimeService.startProcessInstanceByKey(processInstanceKey.get("processInstanceKey")))
                .willReturn(processInstance);
        Map<String, String> variables = new HashMap<>();
        variables.put("processInstanceKey", "my-process");
        JSONObject jsonObject = new JSONObject(variables);

        mockMvc.perform(post(API_PROCESS_START_TASK_URL)
                .contentType(APPLICATION_JSON_VALUE)
                .content(jsonObject.toJSONString()))
                .andExpect(status().isOk());

        verify(processEngine).getRuntimeService();
        verify(runtimeService).startProcessInstanceByKey(anyString());
    }

    @Test
    @DisplayName("Should throw a BAD_REQUEST when the processInstanceKey value is null")
    void throwExceptionWhenProcessInstanceKeyValueIsNull() throws Exception {
        Map<String, String> variables = new HashMap<>();
        variables.put("processInstanceKey", null);
        JSONObject jsonObject = new JSONObject(variables);

        mockMvc.perform(post(API_PROCESS_START_TASK_URL)
                .contentType(APPLICATION_JSON_VALUE)
                .content(jsonObject.toJSONString()))
                .andExpect(status().isBadRequest());

        verifyZeroInteractions(processEngine);
        verifyZeroInteractions(runtimeService);
    }

    @Test
    @DisplayName("Should throw a BAD_REQUEST when the processInstanceKey is null")
    void throwExceptionWhenProcessInstanceKeyIsNull() throws Exception {
        Map<String, String> variables = new HashMap<>();
        JSONObject jsonObject = new JSONObject(variables);

        mockMvc.perform(post(API_PROCESS_START_TASK_URL)
                .contentType(APPLICATION_JSON_VALUE)
                .content(jsonObject.toJSONString()))
                .andExpect(status().isBadRequest());

        verifyZeroInteractions(processEngine);
        verifyZeroInteractions(runtimeService);
    }

    @Test
    @DisplayName("Should find a task given a task assignee")
    void findTask() throws Exception {
        Map<String, String> assignee = new HashMap<>();
        assignee.put("taskAssignee", "artemas");
        JSONObject jsonObject = new JSONObject(assignee);
        List<Task> tasks = List.of(this.task);
        given(processEngine.getTaskService()).willReturn(taskService);
        given(taskService.createTaskQuery()).willReturn(taskQuery);
        given(taskQuery.taskAssignee(assignee.get("taskAssignee"))).willReturn(taskQuery);
        given(taskQuery.list()).willReturn(tasks);

        mockMvc.perform(post(API_PROCESS_FIND_TASK_URL)
                .contentType(APPLICATION_JSON_VALUE)
                .content(jsonObject.toJSONString()))
                .andExpect(status().isOk());

        verify(processEngine).getTaskService();
        verify(taskService).createTaskQuery();
        verify(taskQuery).taskAssignee(anyString());
        verify(taskQuery).list();
    }

    @Test
    @DisplayName("Should throw a BAD_REQUEST exception when the TaskAssignee value is null")
    void throwExceptionWhenTaskAssigneeValueIsNull() throws Exception {
        Map<String, String> assignee = new HashMap<>();
        assignee.put("taskAssignee", null);
        JSONObject jsonObject = new JSONObject(assignee);

        mockMvc.perform(post(API_PROCESS_FIND_TASK_URL)
                .contentType(APPLICATION_JSON_VALUE)
                .content(jsonObject.toJSONString()))
                .andExpect(status().isBadRequest());

        verifyZeroInteractions(processEngine);
        verifyZeroInteractions(taskService);
        verifyZeroInteractions(taskQuery);
        verifyZeroInteractions(taskQuery);
    }

    @Test
    @DisplayName("Should throw a BAD_REQUEST exception when the TaskAssignee is null")
    void throwExceptionWhenTaskAssigneeIsNull() throws Exception {
        Map<String, String> assignee = new HashMap<>();
        JSONObject jsonObject = new JSONObject(assignee);

        mockMvc.perform(post(API_PROCESS_FIND_TASK_URL)
                .contentType(APPLICATION_JSON_VALUE)
                .content(jsonObject.toJSONString()))
                .andExpect(status().isBadRequest());

        verifyZeroInteractions(processEngine);
        verifyZeroInteractions(taskService);
        verifyZeroInteractions(taskQuery);
        verifyZeroInteractions(taskQuery);
    }

    @Test
    @DisplayName("Should find and retrieve a task given a task id")
    void findTaskById() throws Exception {
        Map<String, String> taskId = new HashMap<>();
        taskId.put("taskId", "1");
        JSONObject jsonObject = new JSONObject(taskId);
        given(processEngine.getTaskService()).willReturn(taskService);
        given(taskService.createTaskQuery()).willReturn(taskQuery);
        given(taskQuery.taskId(taskId.get("taskId"))).willReturn(taskQuery);

        mockMvc.perform(post(API_PROCESS_TASK_URL)
                .contentType(APPLICATION_JSON_VALUE)
                .content(jsonObject.toJSONString()))
                .andExpect(status().isOk());

        verify(processEngine).getTaskService();
        verify(taskService).createTaskQuery();
        verify(taskQuery).taskId(anyString());
    }

    @Test
    @DisplayName("Should throw a BAD_REQUEST exception when the taskId value is null")
    void throwExceptionWhenTaskIdValueIsNull() throws Exception {
        Map<String, String> taskId = new HashMap<>();
        taskId.put("taskId", null);
        JSONObject jsonObject = new JSONObject(taskId);

        mockMvc.perform(post(API_PROCESS_TASK_URL)
                .contentType(APPLICATION_JSON_VALUE)
                .content(jsonObject.toJSONString()))
                .andExpect(status().isBadRequest());

        verifyZeroInteractions(processEngine);
        verifyZeroInteractions(taskService);
        verifyZeroInteractions(taskQuery);
    }

    @Test
    @DisplayName("Should throw a BAD_REQUEST exception when the taskId is null")
    void throwExceptionWhenTaskIdIsNull() throws Exception {
        Map<String, String> taskId = new HashMap<>();
        JSONObject jsonObject = new JSONObject(taskId);

        mockMvc.perform(post(API_PROCESS_TASK_URL)
                .contentType(APPLICATION_JSON_VALUE)
                .content(jsonObject.toJSONString()))
                .andExpect(status().isBadRequest());

        verifyZeroInteractions(processEngine);
        verifyZeroInteractions(taskService);
        verifyZeroInteractions(taskQuery);
    }

    @Test
    @DisplayName("Should retrieve all tasks")
    void getAllTasks() throws Exception {
        given(processEngine.getTaskService()).willReturn(taskService);
        given(taskService.createTaskQuery()).willReturn(taskQuery);

        mockMvc.perform(get(API_PROCESS_TASKS_URL))
                .andExpect(status().isOk());

        verify(processEngine).getTaskService();
        verify(taskService).createTaskQuery();
    }

    @Test
    @DisplayName("Should complete a task given a task id")
    void completeTask() throws Exception {
        Map<String, String> taskId = new HashMap<>();
        taskId.put("taskId", "123");
        JSONObject jsonObject = new JSONObject(taskId);
        given(processEngine.getTaskService()).willReturn(taskService);

        mockMvc.perform(post(API_PROCESS_COMPLETE_TASK_URL)
                .contentType(APPLICATION_JSON_VALUE)
                .content(jsonObject.toJSONString()))
                .andExpect(status().isOk());

        verify(processEngine).getTaskService();
    }

    @Test
    @DisplayName("Should throw a BAD_REQUEST exception when TaskId value is null when completing a task")
    void invalidTaskIdValueForCompleteTask() throws Exception {
        Map<String, String> taskId = new HashMap<>();
        taskId.put("taskId", null);
        JSONObject jsonObject = new JSONObject(taskId);

        mockMvc.perform(post(API_PROCESS_COMPLETE_TASK_URL)
                .contentType(APPLICATION_JSON_VALUE)
                .content(jsonObject.toJSONString()))
                .andExpect(status().isBadRequest());

        verifyZeroInteractions(processEngine);
    }

    @Test
    @DisplayName("Should throw a BAD_REQUEST exception when TaskId is null when completing a task")
    void invalidTaskIdForCompleteTask() throws Exception {
        Map<String, String> taskId = emptyMap();
        JSONObject jsonObject = new JSONObject(taskId);

        mockMvc.perform(post(API_PROCESS_COMPLETE_TASK_URL)
                .contentType(APPLICATION_JSON_VALUE)
                .content(jsonObject.toJSONString()))
                .andExpect(status().isBadRequest());

        verifyZeroInteractions(processEngine);
    }

    @Test
    @DisplayName("Should get all deployed processes")
    void getAllDeployedProcesses() throws Exception {
        given(processEngine.getRepositoryService()).willReturn(repositoryService);
        given(repositoryService.createDeploymentQuery()).willReturn(deploymentQuery);

        mockMvc.perform(get(API_PROCESS_DEPLOYED_PROCESSES_URL))
                .andExpect(status().isOk());

        verify(processEngine).getRepositoryService();
        verify(repositoryService).createDeploymentQuery();
    }

    @Test
    @DisplayName("Should delete a deployed process given a deployment Id")
    void testDeleteDeployedProcess() throws Exception {
        Map<String, String> processId = Map.of("deploymentId", DEPLOYMENT_ID);
        JSONObject deploymentIdJson = new JSONObject(processId);

        mockMvc.perform(delete(API_PROCESS_DEPLOYED_PROCESSES_DELETE_URL)
                .contentType(APPLICATION_JSON_VALUE)
                .content(deploymentIdJson.toJSONString()))
                .andExpect(status().isOk());

        verify(repositoryService).deleteDeployment(DEPLOYMENT_ID);
    }

    @Test
    @DisplayName("Should throw a BAD_REQUEST exception when the DeploymentIdJson value is null")
    void testDeploymentJsonValueIsNull() throws Exception {
        Map<String, String> processId = new HashMap<>();
        processId.put("deploymentId", null);
        JSONObject deploymentIdJson = new JSONObject(processId);

        mockMvc.perform(delete(API_PROCESS_DEPLOYED_PROCESSES_DELETE_URL)
                .contentType(APPLICATION_JSON_VALUE)
                .content(deploymentIdJson.toJSONString()))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("DeploymentId is not valid"));

        verifyZeroInteractions(repositoryService);
    }

    @Test
    @DisplayName("Should throw a BAD_REQUEST exception when the DeploymentIdJson is null")
    void testDeploymentJsonIsNull() throws Exception {
        Map<String, String> processId = new HashMap<>();
        JSONObject deploymentIdJson = new JSONObject(processId);

        mockMvc.perform(delete(API_PROCESS_DEPLOYED_PROCESSES_DELETE_URL)
                .contentType(APPLICATION_JSON_VALUE)
                .content(deploymentIdJson.toJSONString()))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("DeploymentId is not valid"));

        verifyZeroInteractions(repositoryService);
    }

    @Test
    @DisplayName("Should throw a content bad request exception when no content trying to find task")
    void testNullTaskObject() throws Exception {
        mockMvc.perform(post(API_PROCESS_FIND_TASK_URL)
                .contentType(APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should throw a BAD_REQUEST exception when deployment id is not a number")
    void testNullDeploymentIDValue() throws Exception {
        Map<String, String> processId = Map.of("deploymentId", "some deployment id");
        JSONObject deploymentIdJson = new JSONObject(processId);

        mockMvc.perform(delete(API_PROCESS_DEPLOYED_PROCESSES_DELETE_URL)
                .contentType(APPLICATION_JSON_VALUE)
                .content(deploymentIdJson.toJSONString()))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Deployment Id must be a number"));
    }

    @Test
    @DisplayName("Should throw a BAD_REQUEST exception when trying to find a task id with an invalid id")
    void testNullTaskId() throws Exception {
        Map<String, String> taskId = new HashMap<>();
        taskId.put("taskId", "some task id");
        JSONObject jsonObject = new JSONObject(taskId);

        mockMvc.perform(post(API_PROCESS_TASK_URL)
                .contentType(APPLICATION_JSON_VALUE)
                .content(jsonObject.toJSONString()))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Task Id must be a number"));
    }
}
