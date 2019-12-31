package com.activiti.demo.web;

import com.activiti.demo.exception.InvalidTaskIdException;
import com.activiti.demo.model.*;
import com.activiti.demo.service.WorkflowService;
import com.activiti.demo.web.json.*;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static com.activiti.demo.converter.DeploymentIdConverter.deploymentIdJsonToDto;
import static com.activiti.demo.converter.ProcessInstanceKeyConverter.processInstanceKeyJsonToDto;
import static com.activiti.demo.converter.ProcessNameConverter.processNameJsonToDto;
import static com.activiti.demo.converter.TaskAssigneeConverter.taskAssigneeJsonToDto;
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
    private WorkflowService workflowServiceImpl;

    @Autowired
    public WorkflowController(ProcessEngine processEngine, RepositoryService repositoryService, WorkflowService workflowServiceImpl) {
        this.processEngine = processEngine;
        this.repositoryService = repositoryService;
        this.workflowServiceImpl = workflowServiceImpl;
    }

    @PostMapping(value = "/deploy", produces = APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(value = OK)
    public void deploy(@RequestBody ProcessNameJson processNameJson) {
        ProcessName processName = processNameJsonToDto(processNameJson);
        workflowServiceImpl.deployProcess(processName);
    }

    @ResponseStatus(value = OK)
    @PostMapping(value = "/start-task", produces = APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public void start(@RequestBody ProcessInstanceKeyJson processInstanceKeyJson) {
        ProcessInstanceKey processInstanceKey = processInstanceKeyJsonToDto(processInstanceKeyJson);
        workflowServiceImpl.startProcess(processInstanceKey);
    }

    @ResponseStatus(value = OK)
    @PostMapping(value = "/find-task", produces = APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public List<TaskObject> findTasks(@RequestBody TaskAssigneeJson taskAssigneeJson) {
        TaskAssignee taskAssignee = taskAssigneeJsonToDto(taskAssigneeJson);
        return workflowServiceImpl.findTaskByAssignee(taskAssignee);
    }

    @ResponseStatus(value = OK)
    @PostMapping(value = "/task", produces = APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public TaskObject findTaskById(@RequestBody TaskIdJson taskIdJson) {
        TaskId taskId = taskIdJsonToDto(taskIdJson);
        validateTaskIdIsNumeric(taskId);
        return workflowServiceImpl.findTaskByTaskId(taskId);
    }

    @ResponseStatus(value = OK)
    @GetMapping(value = "/tasks", produces = APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public List<TaskObject> getAllTasks() {
        return workflowServiceImpl.findAllTasks();
    }

    @ResponseStatus(value = OK)
    @PostMapping(value = "/complete-task", produces = APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public void completeTask(@RequestBody TaskIdJson taskIdJson) {
        TaskId taskId = taskIdJsonToDto(taskIdJson);
        validateTaskIdIsNumeric(taskId);
        workflowServiceImpl.completeTask(taskId);
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
        DeploymentId deploymentId = deploymentIdJsonToDto(deploymentIdJson);
        log.info("ABOUT TO DELETE PROCESS: " + deploymentId.getDeploymentId());
        validateDeploymentIdIsNumeric(deploymentId);
        repositoryService.deleteDeployment(deploymentId.getDeploymentId());
    }

    private DeploymentObject createDeploymentObject(Deployment deployment) {
        return new DeploymentObject(deployment.getId(), deployment.getName(),
                deployment.getDeploymentTime(), deployment.getCategory(), deployment.getKey(), deployment.getTenantId());
    }

    private void validateTaskIdIsNumeric(TaskId taskId) {
        try {
            Long.valueOf(taskId.getTaskId());
        } catch (NumberFormatException e) {
            throw new InvalidTaskIdException("Task Id must be a number");
        }
    }

    private void validateDeploymentIdIsNumeric(DeploymentId deploymentId) {
        try {
            Long.valueOf(deploymentId.getDeploymentId());
        } catch (NumberFormatException e) {
            throw new InvalidTaskIdException("Deployment Id must be a number");
        }
    }
}
