package com.activiti.demo;

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
public class ActivitiDemoApplicationTests {

	ProcessEngine processEngine;
	
	@Before
	public void init() {
		processEngine = ProcessEngines.getDefaultProcessEngine();
	}
	
	@Test
	public void contextLoads() {
	}
	
	@Test
	public void deploy(){  
	    Deployment deployment = processEngine.getRepositoryService()  
	                 .createDeployment()
	                 .addClasspathResource("processes/my-process.bpmn20.xml")
	                 .name("say-hello-process")
	                 .deploy();
	    System.out.println("ID:"+deployment.getId());
	    System.out.println("Name:"+deployment.getName());
	}
	
    @Test 
    public void start(){  
        ProcessInstance processInstance=processEngine.getRuntimeService()  
            .startProcessInstanceByKey("my-process");
        System.out.println("ID:-->"+processInstance.getId());  
        System.out.println("ID:-->"+processInstance.getProcessDefinitionId());  
    }  
//    
//    @Test
//    public void findTask(){
//        List<Task> taskList=processEngine.getTaskService()
//                .createTaskQuery()
//                .taskAssignee("李四")
//                .list(); 
//        
//        if (taskList != null && taskList.size()>0) {
//            for(Task task:taskList){
//                System.out.println("任务ID:"+task.getId());
//                System.out.println("任务名称："+task.getName());
//                System.out.println("任务创建时间："+task.getCreateTime());
//                System.out.println("任务委派人："+task.getAssignee());
//                System.out.println("流程实例ID:"+task.getProcessInstanceId());
//            }
//		}
//
//    }
//    
//    @Test
//    public void completeTask(){
//        processEngine.getTaskService()
//                .complete("usertask1");
//    }

}
