package com.activiti.demo.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyFirstWorkFlow {
	
	@Autowired
	ProcessEngine processEngine;
	
	@Autowired
	RepositoryService repositoryService;
	
	@GetMapping("/deploy")
	public void deploy(){
		Deployment deployment = repositoryService.createDeployment()
                .addClasspathResource("processes/my-process.bpmn20.xml")
                .name("say-hello-process")
                .deploy();
		System.out.println("DEPLOYMENT ID:"+deployment.getId());
		System.out.println("DEPLOYMENT NAME:"+deployment.getName());
	}
	
	@PostMapping("/start")
	@ResponseBody
	public void start(@RequestParam(value="processInstanceKey", required = true) String processInstanceKey){
		ProcessInstance processInstance = processEngine.getRuntimeService()
	            .startProcessInstanceByKey(processInstanceKey);
	        System.out.println("PROCESS INSTANCE ID:-->"+processInstance.getId());  
	        System.out.println("PROCESS INSTANCE DEF ID:-->"+processInstance.getProcessDefinitionId());
	}
	
	@PostMapping("/findTask")
	@ResponseBody
	public Map<String, String> findTask(@RequestParam(value="taskAssignee", required = true) String taskAssignee){
		
		Map<String, String> assignee = new HashMap<>();
		
		List<Task> taskList = processEngine.getTaskService()
                .createTaskQuery()
                .taskAssignee(taskAssignee)
                .list();
        
        if (taskList != null && taskList.size() > 0) {
            for(Task task:taskList){
                System.out.println("ID:"+task.getId());
                System.out.println("TASK NAME："+task.getName());
                System.out.println("TASK CREATED TIME："+task.getCreateTime());
                System.out.println("TASK ASSIGNEE："+task.getAssignee());
                System.out.println("TASK PROCESS INSTANCE ID:"+task.getProcessInstanceId());
                
				assignee.put("ID", task.getId());
				assignee.put("TASK NAME：", task.getName());
            }
            
		}
        return assignee;
	}

}
