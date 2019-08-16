package com.activiti.demo.controller;

import com.activiti.demo.model.DeploymentObject;
import com.activiti.demo.model.TaskObject;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/process")
public class WorkflowController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private ProcessEngine processEngine;
    private RepositoryService repositoryService;

    @Autowired
    public WorkflowController(ProcessEngine processEngine, RepositoryService repositoryService) {
        this.processEngine = processEngine;
        this.repositoryService = repositoryService;
    }

    @PostMapping(value = "/deploy", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public void deploy(@RequestBody Map<String, String> processName) {
        Deployment deployment = processEngine.getRepositoryService()
                .createDeployment()
                .addClasspathResource("processes/my-process.bpmn20.xml")
                .name(processName.get("processName"))
                .deploy();
        log.info("DEPLOYMENT ID:" + deployment.getId());
        log.info("DEPLOYMENT NAME:" + deployment.getName());
    }

    @ResponseStatus(value = HttpStatus.OK)
    @PostMapping(value = "/start-task", produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public void start(@RequestBody Map<String, String> processInstanceKey) {
        ProcessInstance processInstance = processEngine.getRuntimeService()
                .startProcessInstanceByKey(processInstanceKey.get("processInstanceKey"));
        log.info("PROCESS INSTANCE ID:-->" + processInstance.getId());
        log.info("PROCESS INSTANCE DEF ID:-->" + processInstance.getProcessDefinitionId());
    }

    @ResponseStatus(value = HttpStatus.OK)
    @PostMapping(value = "/find-task", produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<TaskObject> findTask(@RequestBody(required = true) Map<String, String> taskAssignee) {
        return processEngine.getTaskService()
                .createTaskQuery()
                .taskAssignee(taskAssignee.get("taskAssignee"))
                .list()
                .stream()
                .map(this::createTaskObject)
                .collect(Collectors.toList());
    }

    @ResponseStatus(value = HttpStatus.OK)
    @PostMapping(value = "/task", produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public TaskObject findTaskById(@RequestBody Map<String, String> taskId) {
        return processEngine.getTaskService().createTaskQuery()
                .taskId(taskId.get("taskId")).list()
                .stream()
                .map(this::createTaskObject)
                .findFirst()
                .orElse(new TaskObject());
    }

    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping(value = "/tasks", produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<TaskObject> getAllTasks() {
        return processEngine.getTaskService()
                .createTaskQuery()
                .list()
                .stream()
                .map(this::createTaskObject)
                .collect(Collectors.toList());
    }

    @ResponseStatus(value = HttpStatus.OK)
    @PostMapping(value = "/complete-task", produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public void completeTask(@RequestBody Map<String, String> taskId) {
        log.info("ABOUT TO DELETE TASKID: " + taskId.get("taskId"));
        processEngine.getTaskService()
                .complete(taskId.get("taskId"));
        log.info("DELETED TASKID: " + taskId.get("taskId"));
    }

    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping(value = "/deployed-processes", produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<DeploymentObject> getAllDeployedProcesses() {
        return processEngine.getRepositoryService().createDeploymentQuery().list()
                .stream()
                .map(this::createDeploymentObject)
                .collect(Collectors.toList());
    }

    private DeploymentObject createDeploymentObject(Deployment deployment) {
        return new DeploymentObject(deployment.getId(), deployment.getName(),
                deployment.getDeploymentTime(), deployment.getCategory(), deployment.getKey(), deployment.getTenantId());
    }

    private TaskObject createTaskObject(Task task) {
        return new TaskObject(task.getId(), task.getName(), task.getAssignee(), task.getDescription(),
                task.getExecutionId(), task.getOwner(), task.getProcessInstanceId(), task.getCreateTime(),
                task.getTaskDefinitionKey(), task.getDueDate(), task.getParentTaskId(), task.getTenantId(),
                task.getTaskLocalVariables(), task.getProcessVariables(), task.getProcessDefinitionId(),
                task.getDelegationState());
    }

}
