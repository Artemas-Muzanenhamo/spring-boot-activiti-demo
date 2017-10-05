package com.activiti.demo.service;

import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
