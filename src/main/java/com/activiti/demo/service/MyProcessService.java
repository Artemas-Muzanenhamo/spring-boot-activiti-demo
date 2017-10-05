package com.activiti.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.activiti.demo.model.TaskObject;

@Service
public class MyProcessService {
	
	Logger log = LoggerFactory.getLogger(MyProcessService.class);
	
	@Autowired
	ProcessEngine processEngine;

	public void deployProcess(Map<String, String> processName) {
		Deployment deployment = processEngine
				.getRepositoryService().createDeployment()
                .addClasspathResource("processes/my-process.bpmn20.xml")
                .name(processName.get("processName"))
                .deploy();
		log.info("DEPLOYMENT ID:"+deployment.getId());
		log.info("DEPLOYMENT NAME:"+deployment.getName());
	}
	
	public void startProcessByProcessInstanceKey(Map<String, String> processInstanceKey){
		ProcessInstance processInstance = processEngine.getRuntimeService()
	            .startProcessInstanceByKey(processInstanceKey.get("processInstanceKey"));
	        log.info("PROCESS INSTANCE ID:-->"+processInstance.getId());  
	        log.info("PROCESS INSTANCE DEF ID:-->"+processInstance.getProcessDefinitionId());
	}

	public List<TaskObject> findTask(Map<String, String> taskAssignee) {

		List<TaskObject> assignee = new ArrayList<>();

		List<Task> taskList = processEngine.getTaskService().createTaskQuery()
				.taskAssignee(taskAssignee.get("taskAssignee")).list();

		taskList.stream().forEach(task -> {
			log.info("ID:" + task.getId());
			log.info("TASK NAME：" + task.getName());
			log.info("TASK CREATED TIME：" + task.getCreateTime());
			log.info("TASK ASSIGNEE：" + task.getAssignee());
			log.info("TASK PROCESS INSTANCE ID:" + task.getProcessInstanceId());

			assignee.add(new TaskObject(task.getId(), task.getName(), task.getAssignee(), task.getDescription(),
					task.getExecutionId(), task.getOwner(), task.getProcessInstanceId(), task.getCreateTime(),
					task.getTaskDefinitionKey(), task.getDueDate(), task.getParentTaskId(), task.getTenantId(),
					task.getTaskLocalVariables(), task.getProcessVariables(), task.getProcessDefinitionId(),
					task.getDelegationState()));
		});

		return assignee;

	}
	
	public void completeTask(Map<String, String> taskId) {
		log.info("ABOUT TO DELETE TASKID: " + taskId.get("taskId"));
		processEngine.getTaskService().complete(taskId.get("taskId"));
		log.info("DELETED TASKID: " + taskId.get("taskId"));
	}

}
