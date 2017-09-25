package com.activiti.demo.rest;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class MyFirstWorkFlowTest {

	@Autowired
	MockMvc mockMvc;
	
//	@Test
//	public void deployTest() throws Exception{
//	    mockMvc.perform(MockMvcRequestBuilders.get("/deploy")).andExpect(status().isOk());
//	}
	
    @Test
    public void startWorkflowTest() throws Exception{  
        mockMvc.perform(MockMvcRequestBuilders.post("/start")
        		.param("processInstanceKey", "my-process"))
        .andExpect(status().isOk());
    }  
    
    @Test
    public void findTask() throws Exception{
    	mockMvc.perform(MockMvcRequestBuilders.post("/findTask").param("taskAssignee", "artemas")
    			.contentType(MediaType.APPLICATION_JSON_UTF8))
    			.andExpect(status().isOk());
    }
    
//    @Test
//    public void completeTask(){
//        processEngine.getTaskService()
//                .complete("usertask1");
//    }
	
}
