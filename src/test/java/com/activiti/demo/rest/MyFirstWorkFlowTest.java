package com.activiti.demo.rest;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import net.minidev.json.JSONObject;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class MyFirstWorkFlowTest {

	@Autowired
	MockMvc mockMvc;
	
//	@Test
//	public void deployTest() throws Exception{
//		Map<String, String> processName = new HashMap<>();
//		processName.put("processName", "say-hello-process");
//		JSONObject jsonObject = new JSONObject(processName);
//		
//	    mockMvc.perform(MockMvcRequestBuilders.post("/deploy")
//	    		.contentType(MediaType.APPLICATION_JSON_VALUE)
//	    		.content(jsonObject.toJSONString()))
//	    .andExpect(status().isOk());
//	}
	
    @Test
    public void startWorkflowTest() throws Exception{
    	Map<String, String> variables = new HashMap<>();
    	variables.put("processInstanceKey", "my-process");
    	JSONObject jsonObject = new JSONObject(variables);
    	
        mockMvc.perform(MockMvcRequestBuilders.post("/start")
        		.contentType(MediaType.APPLICATION_JSON_VALUE)
        		.content(jsonObject.toJSONString()))
        .andExpect(status().isOk());
    }
    
    @Test
    public void findTask() throws Exception{
    	Map<String, String> variables = new HashMap<>();
    	variables.put("taskAssignee", "artemas");
    	JSONObject jsonObject = new JSONObject(variables);
    	
    	mockMvc.perform(MockMvcRequestBuilders.post("/findTask")
    			.contentType(MediaType.APPLICATION_JSON_VALUE)
    			.content(jsonObject.toJSONString()))
    	.andExpect(status().isOk());
    }
    
//    @Test
//    public void completeTask(){
//        processEngine.getTaskService()
//                .complete("usertask1");
//    }
	
}
