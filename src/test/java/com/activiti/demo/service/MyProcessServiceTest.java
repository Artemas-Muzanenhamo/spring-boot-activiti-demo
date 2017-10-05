package com.activiti.demo.service;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.activiti.demo.model.TaskObject;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class MyProcessServiceTest {
	
	@Autowired
	ProcessEngine processEngine;
	
	@Autowired
	MyProcessService myProcessService;
	
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
	
	@Test
	public void whenAValidTaskAssigneeIsPassedThenReturnTasksAssignedToThem() throws Exception{
		Map<String, String> taskAssignee = new HashMap<>();
		taskAssignee.put("taskAssignee", "artemas");
		
		List<TaskObject> task = myProcessService.findTask(taskAssignee);
		assertEquals("artemas", task.stream().findFirst().get().getAssignee());
	}
	
}
