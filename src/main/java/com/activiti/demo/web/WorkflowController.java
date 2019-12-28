package com.activiti.demo.web;

import com.activiti.demo.InvalidTaskIdException;
import com.activiti.demo.json.*;
import com.activiti.demo.model.*;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static com.activiti.demo.converter.ProcessInstanceKeyConverter.processInstanceKeyJsonToDto;
import static com.activiti.demo.converter.ProcessNameConverter.processNameJsonToDto;
import static com.activiti.demo.converter.TaskIdConverter.taskIdJsonToDto;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

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

    @PostMapping(value = "/deploy", produces = APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(value = OK)
    public void deploy(@RequestBody ProcessNameJson processNameJson) {
        ProcessName processName = processNameJsonToDto(processNameJson);
        Deployment deployment = processEngine.getRepositoryService()
                .createDeployment()
                .addClasspathResource("processes/my-process.bpmn20.xml")
                .name(processName.getProcessName())
                .deploy();
        log.info("DEPLOYMENT ID:" + deployment.getId());
        log.info("DEPLOYMENT NAME:" + deployment.getName());
    }

    @ResponseStatus(value = OK)
    @PostMapping(value = "/start-task", produces = APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public void start(@RequestBody ProcessInstanceKeyJson processInstanceKeyJson) {
        ProcessInstanceKey processInstanceKey = processInstanceKeyJsonToDto(processInstanceKeyJson);
        ProcessInstance processInstance = processEngine.getRuntimeService()
                .startProcessInstanceByKey(processInstanceKey.getProcessInstanceKey());
        log.info("PROCESS INSTANCE ID:-->" + processInstance.getId());
        log.info("PROCESS INSTANCE DEF ID:-->" + processInstance.getProcessDefinitionId());
    }

    @ResponseStatus(value = OK)
    @PostMapping(value = "/find-task", produces = APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public List<TaskObject> findTask(@RequestBody TaskAssigneeJson taskAssigneeJson) {
        return processEngine.getTaskService()
                .createTaskQuery()
                .taskAssignee(taskAssigneeJson.getTaskAssignee())
                .list()
                .stream()
                .map(this::createTaskObject)
                .collect(Collectors.toList());
    }

    @ResponseStatus(value = OK)
    @PostMapping(value = "/task", produces = APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public TaskObject findTaskById(@RequestBody TaskIdJson taskIdJson) {
        TaskId taskId = taskIdJsonToDto(taskIdJson);
        validateTaskIdIsNumeric(taskId);
        return processEngine.getTaskService().createTaskQuery()
                .taskId(taskId.getTaskId()).list()
                .stream()
                .map(this::createTaskObject)
                .findFirst()
                .orElse(new TaskObject());
    }

    @ResponseStatus(value = OK)
    @GetMapping(value = "/tasks", produces = APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public List<TaskObject> getAllTasks() {
        return processEngine.getTaskService()
                .createTaskQuery()
                .list()
                .stream()
                .map(this::createTaskObject)
                .collect(Collectors.toList());
    }

    @ResponseStatus(value = OK)
    @PostMapping(value = "/complete-task", produces = APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public void completeTask(@RequestBody TaskIdJson taskIdJson) {
        log.info("ABOUT TO DELETE TASKID: " + taskIdJson.getTaskId());
        TaskId taskId = taskIdJsonToDto(taskIdJson);
        validateTaskIdIsNumeric(taskId);
        processEngine.getTaskService()
                .complete(taskId.getTaskId());
        log.info("DELETED TASKID: " + taskIdJson.getTaskId());
    }

    @ResponseStatus(value = OK)
    @GetMapping(value = "/deployed-processes", produces = APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public List<DeploymentObject> getAllDeployedProcesses() {
        return processEngine.getRepositoryService().createDeploymentQuery().list()
                .stream()
                .map(this::createDeploymentObject)
                .collect(Collectors.toList());
    }

    @ResponseStatus(value = OK)
    @DeleteMapping(value = "/deployed-processes/delete", consumes = APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public void deleteDeployedProcess(@RequestBody DeploymentIdJson deploymentIdJson) {
        log.info("ABOUT TO DELETE PROCESS: " + deploymentIdJson.getDeploymentId());
        validateDeploymentIdIsNumeric(deploymentIdJson);
        repositoryService.deleteDeployment(deploymentIdJson.getDeploymentId());
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

    private void validateTaskIdIsNumeric(TaskId taskId) {
        try {
            Long.valueOf(taskId.getTaskId());
        } catch (NumberFormatException e) {
            throw new InvalidTaskIdException("Task Id is not valid");
        }
    }

    private void validateDeploymentIdIsNumeric(DeploymentIdJson json) {
        try {
            Long.valueOf(json.getDeploymentId());
        } catch (NumberFormatException e) {
            throw new InvalidTaskIdException("Deployment Id is not valid");
        }
    }
}
