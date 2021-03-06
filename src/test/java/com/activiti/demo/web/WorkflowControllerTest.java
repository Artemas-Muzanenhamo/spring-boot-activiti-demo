package com.activiti.demo.web;

import com.activiti.demo.model.*;
import com.activiti.demo.service.WorkflowService;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;

import static java.util.Collections.emptyMap;
import static org.mockito.ArgumentMatchers.any;
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
    private static final String DEPLOY_PROCESS_ERROR_MESSAGE = "Process name supplied is not valid";
    private static final String START_PROCESS_ERROR_MESSAGE = "Process instance key supplied is not valid";
    private static final String FIND_TASK_BY_ASSIGNEE_ERROR_MESSAGE = "Task assignee supplied is not valid";
    private static final String FIND_TASK_BY_ID_ERROR_MESSAGE = "Task Id is not valid";
    private static final String COMPLETE_TASK_BY_TASK_ID_ERROR_MESSAGE = "Task Id is not valid";
    private static final String DEPLOY_PROCESS_BY_DEPLOYMENT_ID_ERROR_MESSAGE = "DeploymentId is not valid";

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private WorkflowService workflowService;

    @Test
    @DisplayName("Should deploy a process given a process name")
    void testDeployProcess() throws Exception {
        Map<String, String> processName = new HashMap<>();
        processName.put("processName", "say-hello-process");
        JSONObject jsonObject = new JSONObject(processName);

        mockMvc.perform(post("/api/process/deploy")
                .accept(APPLICATION_JSON_UTF8_VALUE)
                .contentType(APPLICATION_JSON_UTF8_VALUE)
                .content(jsonObject.toJSONString()))
                .andExpect(status().isOk());

        verify(workflowService).deployProcess(any(ProcessName.class));
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
                .andExpect(status().isBadRequest())
                .andExpect(content().string(DEPLOY_PROCESS_ERROR_MESSAGE));

        verifyZeroInteractions(workflowService);
    }

    @Test
    @DisplayName("Should throw a BAD_REQUEST exception when process name value passed is empty")
    void throwExceptionWhenProcessNameValueIsEmpty() throws Exception {
        Map<String, String> processName = new HashMap<>();
        processName.put("processName", "");
        JSONObject jsonObject = new JSONObject(processName);

        mockMvc.perform(post("/api/process/deploy")
                .accept(APPLICATION_JSON_UTF8_VALUE)
                .contentType(APPLICATION_JSON_UTF8_VALUE)
                .content(jsonObject.toJSONString()))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(DEPLOY_PROCESS_ERROR_MESSAGE));

        verifyZeroInteractions(workflowService);
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
                .andExpect(status().isBadRequest())
                .andExpect(content().string(DEPLOY_PROCESS_ERROR_MESSAGE));

        verifyZeroInteractions(workflowService);
    }

    @Test
    @DisplayName("Should start a workflow given a processInstanceKey")
    void startWorkflowTest() throws Exception {
        Map<String, String> variables = new HashMap<>();
        variables.put("processInstanceKey", "my-process");
        JSONObject jsonObject = new JSONObject(variables);

        mockMvc.perform(post(API_PROCESS_START_TASK_URL)
                .contentType(APPLICATION_JSON_VALUE)
                .content(jsonObject.toJSONString()))
                .andExpect(status().isOk());

        verify(workflowService).startProcess(any(ProcessInstanceKey.class));
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
                .andExpect(status().isBadRequest())
                .andExpect(content().string(START_PROCESS_ERROR_MESSAGE));

        verifyZeroInteractions(workflowService);
    }

    @Test
    @DisplayName("Should throw a BAD_REQUEST when the processInstanceKey value is empty")
    void throwExceptionWhenProcessInstanceKeyValueIsEmpty() throws Exception {
        Map<String, String> variables = new HashMap<>();
        variables.put("processInstanceKey", "");
        JSONObject jsonObject = new JSONObject(variables);

        mockMvc.perform(post(API_PROCESS_START_TASK_URL)
                .contentType(APPLICATION_JSON_VALUE)
                .content(jsonObject.toJSONString()))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(START_PROCESS_ERROR_MESSAGE));

        verifyZeroInteractions(workflowService);
    }

    @Test
    @DisplayName("Should throw a BAD_REQUEST when the processInstanceKey is null")
    void throwExceptionWhenProcessInstanceKeyIsNull() throws Exception {
        Map<String, String> variables = new HashMap<>();
        JSONObject jsonObject = new JSONObject(variables);

        mockMvc.perform(post(API_PROCESS_START_TASK_URL)
                .contentType(APPLICATION_JSON_VALUE)
                .content(jsonObject.toJSONString()))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(START_PROCESS_ERROR_MESSAGE));

        verifyZeroInteractions(workflowService);
    }

    @Test
    @DisplayName("Should find a task given a task assignee")
    void findTask() throws Exception {
        Map<String, String> assignee = new HashMap<>();
        assignee.put("taskAssignee", "artemas");
        JSONObject jsonObject = new JSONObject(assignee);

        mockMvc.perform(post(API_PROCESS_FIND_TASK_URL)
                .contentType(APPLICATION_JSON_VALUE)
                .content(jsonObject.toJSONString()))
                .andExpect(status().isOk());

        verify(workflowService).findTaskByAssignee(any(TaskAssignee.class));
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
                .andExpect(status().isBadRequest())
                .andExpect(content().string(FIND_TASK_BY_ASSIGNEE_ERROR_MESSAGE));

        verifyZeroInteractions(workflowService);
    }

    @Test
    @DisplayName("Should throw a BAD_REQUEST exception when the TaskAssignee value is empty")
    void throwExceptionWhenTaskAssigneeValueIsEmpty() throws Exception {
        Map<String, String> assignee = new HashMap<>();
        assignee.put("taskAssignee", "");
        JSONObject jsonObject = new JSONObject(assignee);

        mockMvc.perform(post(API_PROCESS_FIND_TASK_URL)
                .contentType(APPLICATION_JSON_VALUE)
                .content(jsonObject.toJSONString()))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(FIND_TASK_BY_ASSIGNEE_ERROR_MESSAGE));

        verifyZeroInteractions(workflowService);
    }

    @Test
    @DisplayName("Should throw a BAD_REQUEST exception when the TaskAssignee is null")
    void throwExceptionWhenTaskAssigneeIsNull() throws Exception {
        Map<String, String> assignee = new HashMap<>();
        JSONObject jsonObject = new JSONObject(assignee);

        mockMvc.perform(post(API_PROCESS_FIND_TASK_URL)
                .contentType(APPLICATION_JSON_VALUE)
                .content(jsonObject.toJSONString()))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(FIND_TASK_BY_ASSIGNEE_ERROR_MESSAGE));

        verifyZeroInteractions(workflowService);
    }

    @Test
    @DisplayName("Should find and retrieve a task given a task id")
    void findTaskById() throws Exception {
        Map<String, String> taskId = new HashMap<>();
        taskId.put("taskId", "1");
        JSONObject jsonObject = new JSONObject(taskId);

        mockMvc.perform(post(API_PROCESS_TASK_URL)
                .contentType(APPLICATION_JSON_VALUE)
                .content(jsonObject.toJSONString()))
                .andExpect(status().isOk());

        verify(workflowService).findTaskByTaskId(any(TaskId.class));
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
                .andExpect(status().isBadRequest())
                .andExpect(content().string(FIND_TASK_BY_ID_ERROR_MESSAGE));
    }

    @Test
    @DisplayName("Should throw a BAD_REQUEST exception when the taskId value is empty")
    void throwExceptionWhenTaskIdValueIsEmpty() throws Exception {
        Map<String, String> taskId = new HashMap<>();
        taskId.put("taskId", "");
        JSONObject jsonObject = new JSONObject(taskId);

        mockMvc.perform(post(API_PROCESS_TASK_URL)
                .contentType(APPLICATION_JSON_VALUE)
                .content(jsonObject.toJSONString()))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(FIND_TASK_BY_ID_ERROR_MESSAGE));
    }

    @Test
    @DisplayName("Should throw a BAD_REQUEST exception when the taskId is null")
    void throwExceptionWhenTaskIdIsNull() throws Exception {
        Map<String, String> taskId = new HashMap<>();
        JSONObject jsonObject = new JSONObject(taskId);

        mockMvc.perform(post(API_PROCESS_TASK_URL)
                .contentType(APPLICATION_JSON_VALUE)
                .content(jsonObject.toJSONString()))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(FIND_TASK_BY_ID_ERROR_MESSAGE));
    }

    @Test
    @DisplayName("Should retrieve all tasks")
    void getAllTasks() throws Exception {
        mockMvc.perform(get(API_PROCESS_TASKS_URL))
                .andExpect(status().isOk());

        verify(workflowService).findAllTasks();
    }

    @Test
    @DisplayName("Should complete a task given a task id")
    void completeTask() throws Exception {
        Map<String, String> taskId = new HashMap<>();
        taskId.put("taskId", "123");
        JSONObject jsonObject = new JSONObject(taskId);

        mockMvc.perform(post(API_PROCESS_COMPLETE_TASK_URL)
                .contentType(APPLICATION_JSON_VALUE)
                .content(jsonObject.toJSONString()))
                .andExpect(status().isOk());

        verify(workflowService).completeTask(any(TaskId.class));
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
                .andExpect(status().isBadRequest())
                .andExpect(content().string(COMPLETE_TASK_BY_TASK_ID_ERROR_MESSAGE));

        verifyZeroInteractions(workflowService);
    }

    @Test
    @DisplayName("Should throw a BAD_REQUEST exception when TaskId value is empty when completing a task")
    void invalidTaskIdValueForCompleteTaskIsEmpty() throws Exception {
        Map<String, String> taskId = new HashMap<>();
        taskId.put("taskId", "");
        JSONObject jsonObject = new JSONObject(taskId);

        mockMvc.perform(post(API_PROCESS_COMPLETE_TASK_URL)
                .contentType(APPLICATION_JSON_VALUE)
                .content(jsonObject.toJSONString()))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(COMPLETE_TASK_BY_TASK_ID_ERROR_MESSAGE));

        verifyZeroInteractions(workflowService);
    }

    @Test
    @DisplayName("Should throw a BAD_REQUEST exception when TaskId is null when completing a task")
    void invalidTaskIdForCompleteTask() throws Exception {
        Map<String, String> taskId = emptyMap();
        JSONObject jsonObject = new JSONObject(taskId);

        mockMvc.perform(post(API_PROCESS_COMPLETE_TASK_URL)
                .contentType(APPLICATION_JSON_VALUE)
                .content(jsonObject.toJSONString()))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(COMPLETE_TASK_BY_TASK_ID_ERROR_MESSAGE));

        verifyZeroInteractions(workflowService);
    }

    @Test
    @DisplayName("Should get all deployed processes")
    void getAllDeployedProcesses() throws Exception {
        mockMvc.perform(get(API_PROCESS_DEPLOYED_PROCESSES_URL))
                .andExpect(status().isOk());

        verify(workflowService).findAllDeployedProcesses();
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

        verify(workflowService).deleteDeployedProcess(any(DeploymentId.class));
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
                .andExpect(content().string(DEPLOY_PROCESS_BY_DEPLOYMENT_ID_ERROR_MESSAGE));

        verifyZeroInteractions(workflowService);
    }

    @Test
    @DisplayName("Should throw a BAD_REQUEST exception when the DeploymentIdJson value is empty")
    void testDeploymentJsonValueIsEmpty() throws Exception {
        Map<String, String> processId = new HashMap<>();
        processId.put("deploymentId", "");
        JSONObject deploymentIdJson = new JSONObject(processId);

        mockMvc.perform(delete(API_PROCESS_DEPLOYED_PROCESSES_DELETE_URL)
                .contentType(APPLICATION_JSON_VALUE)
                .content(deploymentIdJson.toJSONString()))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(DEPLOY_PROCESS_BY_DEPLOYMENT_ID_ERROR_MESSAGE));

        verifyZeroInteractions(workflowService);
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
                .andExpect(content().string(DEPLOY_PROCESS_BY_DEPLOYMENT_ID_ERROR_MESSAGE));

        verifyZeroInteractions(workflowService);
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
    @DisplayName("Should throw a BAD_REQUEST exception when trying to find a task with a non numeric id")
    void testNonNumericTaskId() throws Exception {
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
