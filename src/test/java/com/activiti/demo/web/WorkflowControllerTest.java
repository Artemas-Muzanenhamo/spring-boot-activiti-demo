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

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
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
    @DisplayName("Should find a task given a task assignee")
    void findTask() throws Exception {
        Map<String, String> assignee = new HashMap<>();
        assignee.put("taskAssignee", "artemas");
        List<Task> tasks = List.of(this.task);
        given(processEngine.getTaskService()).willReturn(taskService);
        given(taskService.createTaskQuery()).willReturn(taskQuery);
        given(taskQuery.taskAssignee(assignee.get("taskAssignee"))).willReturn(taskQuery);
        given(taskQuery.list()).willReturn(tasks);
        JSONObject jsonObject = new JSONObject(assignee);

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
    @DisplayName("Should find and retrieve a task given a task id")
    void findTaskById() throws Exception {
        Map<String, String> taskId = new HashMap<>();
        taskId.put("taskId", "1");
        given(processEngine.getTaskService()).willReturn(taskService);
        given(taskService.createTaskQuery()).willReturn(taskQuery);
        given(taskQuery.taskId(taskId.get("taskId"))).willReturn(taskQuery);
        JSONObject jsonObject = new JSONObject(taskId);

        mockMvc.perform(post(API_PROCESS_TASK_URL)
                .contentType(APPLICATION_JSON_VALUE)
                .content(jsonObject.toJSONString()))
                .andExpect(status().isOk());

        verify(processEngine).getTaskService();
        verify(taskService).createTaskQuery();
        verify(taskQuery).taskId(anyString());
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
        given(processEngine.getTaskService()).willReturn(taskService);
        JSONObject jsonObject = new JSONObject(taskId);

        mockMvc.perform(post(API_PROCESS_COMPLETE_TASK_URL)
                .contentType(APPLICATION_JSON_VALUE)
                .content(jsonObject.toJSONString()))
                .andExpect(status().isOk());

        verify(processEngine).getTaskService();
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
                .andExpect(content().string("Deployment Id is not valid"));

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
                .andExpect(content().string("Task Id is not valid"));
    }
}
