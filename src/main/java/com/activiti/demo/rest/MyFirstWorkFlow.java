package com.activiti.demo.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyFirstWorkFlow {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	ProcessEngine processEngine;
	
	@Autowired
	RepositoryService repositoryService;
	
	@GetMapping(value = "/deploy", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value=HttpStatus.OK)
	public void deploy(){
		Deployment deployment = repositoryService.createDeployment()
                .addClasspathResource("processes/my-process.bpmn20.xml")
                .name("say-hello-process")
                .deploy();
		log.info("DEPLOYMENT ID:"+deployment.getId());
		log.info("DEPLOYMENT NAME:"+deployment.getName());
	}
	
	@ResponseStatus(value=HttpStatus.OK)
	@PostMapping(value = "/start-task", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public void start(@RequestBody(required = true) Map<String, String> processInstanceKey){
		ProcessInstance processInstance = processEngine.getRuntimeService()
	            .startProcessInstanceByKey(processInstanceKey.get("processInstanceKey"));
	        log.info("PROCESS INSTANCE ID:-->"+processInstance.getId());  
	        log.info("PROCESS INSTANCE DEF ID:-->"+processInstance.getProcessDefinitionId());
	}
	
	@ResponseStatus(value=HttpStatus.OK)
	@PostMapping(value = "/find-task", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Map<String, String> findTask(@RequestBody(required = true) Map<String, String> taskAssignee){
		
		Map<String, String> assignee = new HashMap<>();
		
		List<Task> taskList = processEngine.getTaskService()
                .createTaskQuery()
                .taskAssignee(taskAssignee.get("taskAssignee"))
                .list();
        
		taskList.stream().forEach(task -> {
			log.info("ID:"+task.getId());
            log.info("TASK NAME："+task.getName());
            log.info("TASK CREATED TIME："+task.getCreateTime());
            log.info("TASK ASSIGNEE："+task.getAssignee());
            log.info("TASK PROCESS INSTANCE ID:"+task.getProcessInstanceId());
            
			assignee.put(task.getId(), task.getName());
		});
        
        return assignee;
	}
	
	@ResponseStatus(value=HttpStatus.OK)
	@PostMapping(value = "/complete-task", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public void completeTask(@RequestBody(required = true) Map<String, String> taskId) {
		processEngine.getTaskService().complete(taskId.get("taskId"));
	}

}
