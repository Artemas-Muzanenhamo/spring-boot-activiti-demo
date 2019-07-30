package com.activiti.demo.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import net.minidev.json.JSONObject;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class WorkflowControllerTest {

	@Autowired
	MockMvc mockMvc;
	
	@Autowired
    WorkflowController workflowController;

	@Mock
	private ProcessEngine processEngine;

	@Mock
	private TaskService taskService;

	@Mock
	private Task task;

	@Test
	void deployTest() throws Exception{
		Map<String, String> processName = new HashMap<>();
		processName.put("processName", "say-hello-process");
		JSONObject jsonObject = new JSONObject(processName);
		
	    mockMvc.perform(MockMvcRequestBuilders.post("/api/process/deploy")
	    		.contentType(MediaType.APPLICATION_JSON_VALUE)
	    		.content(jsonObject.toJSONString()))
	    .andExpect(status().isOk());
	}
	
    @Test
	void startWorkflowTest() throws Exception{
    	Map<String, String> variables = new HashMap<>();
    	variables.put("processInstanceKey", "my-process");
    	JSONObject jsonObject = new JSONObject(variables);
    	
        mockMvc.perform(MockMvcRequestBuilders.post("/api/process/start-task")
        		.contentType(MediaType.APPLICATION_JSON_VALUE)
        		.content(jsonObject.toJSONString()))
        .andExpect(status().isOk());
    }
    
    @Test
	void findTask() throws Exception{
    	Map<String, String> variables = new HashMap<>();
    	variables.put("taskAssignee", "artemas");
    	JSONObject jsonObject = new JSONObject(variables);
    	
    	mockMvc.perform(MockMvcRequestBuilders.post("/api/process/find-task")
    			.contentType(MediaType.APPLICATION_JSON_VALUE)
    			.content(jsonObject.toJSONString()))
    	.andExpect(status().isOk());
    }

    @Test
	void findTaskById() throws Exception{
		when(processEngine.getTaskService()).thenReturn(taskService);
    	Map<String, String> variables = new HashMap<>();
		variables.put("taskId", "1");
    	JSONObject jsonObject = new JSONObject(variables);

    	mockMvc.perform(MockMvcRequestBuilders.post("/api/process/task")
    			.contentType(MediaType.APPLICATION_JSON_VALUE)
    			.content(jsonObject.toJSONString()))
    	.andExpect(status().isOk());
    }
    
    @Test
	void getAllTasks() throws Exception{
    	mockMvc.perform(MockMvcRequestBuilders.get("/api/process/tasks"))
    	.andExpect(status().isOk());
    }
    
    @Test
	void completeTask() throws Exception {
    	String taskId = workflowController.getAllTasks().stream().findFirst().get().getId();
    	Map<String, String> variables = new HashMap<>();
    	variables.put("taskId", taskId);
    	JSONObject jsonObject = new JSONObject(variables);
    	
    	mockMvc.perform(MockMvcRequestBuilders.post("/api/process/complete-task")
    			.contentType(MediaType.APPLICATION_JSON_VALUE)
    			.content(jsonObject.toJSONString()))
    	.andExpect(status().isOk());
    }
}
