package com.activiti.demo.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
