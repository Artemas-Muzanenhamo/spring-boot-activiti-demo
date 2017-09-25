package com.activiti.demo.controller;

import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WorkflowControllerTest {

	ProcessEngine processEngine; 
	
	@Before
	public void init(){
		processEngine = ProcessEngines.getDefaultProcessEngine();
	}
	
	@Test
	public void deploy(){  
	    Deployment deployment = processEngine.getRepositoryService()  
	                 .createDeployment()
	                 .addClasspathResource("processes/my-process.bpmn20.xml")
	                 .name("say-hello-process")
	                 .deploy();
	    System.out.println("DEPLOYMENT ID:"+deployment.getId());
	    System.out.println("DEPLOYMENT NAME:"+deployment.getName());
	}
	
    @Test
    public void start(){  
        ProcessInstance processInstance = processEngine.getRuntimeService()
            .startProcessInstanceByKey("my-process");
        System.out.println("PROCESS INSTANCE ID:-->"+processInstance.getId());  
        System.out.println("PROCESS INSTANCE DEF ID:-->"+processInstance.getProcessDefinitionId());
    }  
    
    @Test
    public void findTask(){
    	
    	ProcessInstance processInstance = processEngine.getRuntimeService()
                .startProcessInstanceByKey("my-process");
    	
        List<Task> taskList=processEngine.getTaskService()
                .createTaskQuery()
                .taskAssignee("artemas")
                .list();
        
        if (taskList != null && taskList.size() > 0) {
            for(Task task:taskList){
                System.out.println("ID:"+task.getId());
                System.out.println("TASK NAME："+task.getName());
                System.out.println("TASK CREATED TIME："+task.getCreateTime());
                System.out.println("TASK ASSIGNEE："+task.getAssignee());
                System.out.println("TASK PROCESS INSTANCE ID:"+task.getProcessInstanceId());
            }
		}

    }
    
//    @Test
//    public void completeTask(){
//        processEngine.getTaskService()
//                .complete("usertask1");
//    }
	
}
