package com.activiti.demo.controller;

import net.minidev.json.JSONObject;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(WorkflowController.class)
@ActiveProfiles("test")
class WorkflowControllerTest {

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

    @Test
    void deployTest() throws Exception {
        Map<String, String> processName = new HashMap<>();
        processName.put("processName", "say-hello-process");
        given(processEngine.getRepositoryService()).willReturn(repositoryService);
        given(repositoryService.createDeployment()).willReturn(deploymentBuilder);
        given(deploymentBuilder.addClasspathResource(anyString())).willReturn(deploymentBuilder);
        given(deploymentBuilder.name(anyString())).willReturn(deploymentBuilder);
        given(deploymentBuilder.deploy()).willReturn(deployment);
        JSONObject jsonObject = new JSONObject(processName);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/process/deploy")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
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
    void startWorkflowTest() throws Exception {
        Map<String, String> processInstanceKey = Map.of("processInstanceKey", "my-process");
        given(processEngine.getRuntimeService()).willReturn(runtimeService);
        given(runtimeService.startProcessInstanceByKey(processInstanceKey.get("processInstanceKey")))
                .willReturn(processInstance);
        Map<String, String> variables = new HashMap<>();
        variables.put("processInstanceKey", "my-process");
        JSONObject jsonObject = new JSONObject(variables);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/process/start-task")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(jsonObject.toJSONString()))
                .andExpect(status().isOk());

        verify(processEngine).getRuntimeService();
        verify(runtimeService).startProcessInstanceByKey(anyString());
    }

    @Test
    void findTask() throws Exception {
        Map<String, String> assignee = new HashMap<>();
        assignee.put("taskAssignee", "artemas");
        List<Task> tasks = List.of(this.task);
        given(processEngine.getTaskService()).willReturn(taskService);
        given(taskService.createTaskQuery()).willReturn(taskQuery);
        given(taskQuery.taskAssignee(assignee.get("taskAssignee"))).willReturn(taskQuery);
        given(taskQuery.list()).willReturn(tasks);
        JSONObject jsonObject = new JSONObject(assignee);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/process/find-task")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(jsonObject.toJSONString()))
                .andExpect(status().isOk());

        verify(processEngine).getTaskService();
        verify(taskService).createTaskQuery();
        verify(taskQuery).taskAssignee(anyString());
        verify(taskQuery).list();
    }

    @Test
    void findTaskById() throws Exception {
        Map<String, String> taskId = new HashMap<>();
        taskId.put("taskId", "1");
        given(processEngine.getTaskService()).willReturn(taskService);
        given(taskService.createTaskQuery()).willReturn(taskQuery);
        given(taskQuery.taskId(taskId.get("taskId"))).willReturn(taskQuery);
        JSONObject jsonObject = new JSONObject(taskId);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/process/task")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(jsonObject.toJSONString()))
                .andExpect(status().isOk());

        verify(processEngine).getTaskService();
        verify(taskService).createTaskQuery();
        verify(taskQuery).taskId(anyString());
    }

    @Test
    void getAllTasks() throws Exception {
        given(processEngine.getTaskService()).willReturn(taskService);
        given(taskService.createTaskQuery()).willReturn(taskQuery);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/process/tasks"))
                .andExpect(status().isOk());

        verify(processEngine).getTaskService();
        verify(taskService).createTaskQuery();
    }

    @Test
    void completeTask() throws Exception {
        Map<String, String> taskId = new HashMap<>();
        taskId.put("taskId", "123");
        given(processEngine.getTaskService()).willReturn(taskService);
        JSONObject jsonObject = new JSONObject(taskId);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/process/complete-task")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(jsonObject.toJSONString()))
                .andExpect(status().isOk());

        verify(processEngine).getTaskService();
    }
}
