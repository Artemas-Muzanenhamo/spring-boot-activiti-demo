package com.activiti.demo.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.activiti.demo.model.TaskObject;
import com.activiti.demo.service.MyProcessService;

@RestController
public class MyFirstWorkFlow {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	ProcessEngine processEngine;
	
	@Autowired
	RepositoryService repositoryService;
	
	@Autowired
	MyProcessService myProcessService;
	
	@PostMapping(value = "/deploy", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value=HttpStatus.OK)
	public void deploy(@RequestBody(required = true) Map<String, String> processName){
		myProcessService.deployProcess(processName);
	}
	
	@ResponseStatus(value=HttpStatus.OK)
	@PostMapping(value = "/start-task", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public void start(@RequestBody(required = true) Map<String, String> processInstanceKey){
		myProcessService.startProcessByProcessInstanceKey(processInstanceKey);
	}
	
	@ResponseStatus(value=HttpStatus.OK)
	@PostMapping(value = "/find-task", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<TaskObject> findTask(@RequestBody(required = true) Map<String, String> taskAssignee){
		return myProcessService.findTask(taskAssignee);
	}
	
	@ResponseStatus(value=HttpStatus.OK)
	@PostMapping(value = "/complete-task", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public void completeTask(@RequestBody(required = true) Map<String, String> taskId) {
		myProcessService.completeTask(taskId);
	}

}
