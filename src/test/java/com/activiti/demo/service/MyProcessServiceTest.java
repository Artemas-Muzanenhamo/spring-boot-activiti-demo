package com.activiti.demo.service;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class MyProcessServiceTest {
	
	@Autowired
	ProcessEngine processEngine;
	
	@Test
	public void whenAProcessIsDeployedThenCheckIfTheProcessNameExists() throws Exception{
		Map<String, String> processName = new HashMap<>();
		processName.put("processName", "processName");
		
		Deployment deployment = processEngine
				.getRepositoryService().createDeployment()
                .addClasspathResource("processes/my-process.bpmn20.xml")
                .name(processName.get("processName"))
                .deploy();
		
		assertEquals("processName", deployment.getName());
	}
	
	@Test
	public void whenAValidProcessKeyIsPassedThenStartAProcessInstance() throws Exception{
		Map<String, String> processKey = new HashMap<>();
		processKey.put("processInstanceKey", "my-process");
		
		ProcessInstance processInstance = processEngine.getRuntimeService()
				.startProcessInstanceByKey(processKey.get("processInstanceKey"));
		
		assertEquals("say-hello-process", processInstance.getProcessDefinitionName());
	}
	
}
