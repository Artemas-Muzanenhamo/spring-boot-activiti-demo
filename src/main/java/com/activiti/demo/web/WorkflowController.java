package com.activiti.demo.web;

import com.activiti.demo.exception.InvalidTaskIdException;
import com.activiti.demo.model.*;
import com.activiti.demo.service.WorkflowService;
import com.activiti.demo.web.json.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.activiti.demo.web.converter.DeploymentConverter.toDeploymentJsonList;
import static com.activiti.demo.web.converter.DeploymentIdConverter.deploymentIdJsonToDto;
import static com.activiti.demo.web.converter.ProcessInstanceKeyConverter.processInstanceKeyJsonToDto;
import static com.activiti.demo.web.converter.ProcessNameConverter.processNameJsonToDto;
import static com.activiti.demo.web.converter.TaskAssigneeConverter.taskAssigneeJsonToDto;
import static com.activiti.demo.web.converter.TaskConverter.taskDtoListToJsonList;
import static com.activiti.demo.web.converter.TaskConverter.taskDtoToJson;
import static com.activiti.demo.web.converter.TaskIdConverter.taskIdJsonToDto;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/process")
public class WorkflowController {

    private WorkflowService workflowServiceImpl;

    @Autowired
    public WorkflowController(WorkflowService workflowServiceImpl) {
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
    public List<TaskJson> findTasks(@RequestBody TaskAssigneeJson taskAssigneeJson) {
        TaskAssignee taskAssignee = taskAssigneeJsonToDto(taskAssigneeJson);
        List<TaskDTO> tasks = workflowServiceImpl.findTaskByAssignee(taskAssignee);
        return taskDtoListToJsonList(tasks);
    }

    @ResponseStatus(value = OK)
    @PostMapping(value = "/task", produces = APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public TaskJson findTaskById(@RequestBody TaskIdJson taskIdJson) {
        TaskId taskId = taskIdJsonToDto(taskIdJson);
        validateTaskIdIsNumeric(taskId);
        TaskDTO task = workflowServiceImpl.findTaskByTaskId(taskId);
        return taskDtoToJson(task);
    }

    @ResponseStatus(value = OK)
    @GetMapping(value = "/tasks", produces = APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public List<TaskJson> getAllTasks() {
        List<TaskDTO> tasks = workflowServiceImpl.findAllTasks();
        return taskDtoListToJsonList(tasks);
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
    public List<DeploymentJson> getAllDeployedProcesses() {
        List<DeploymentObject> deployedProcesses = workflowServiceImpl.findAllDeployedProcesses();
        return toDeploymentJsonList(deployedProcesses);
    }

    @ResponseStatus(value = OK)
    @DeleteMapping(value = "/deployed-processes/delete", consumes = APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public void deleteDeployedProcess(@RequestBody DeploymentIdJson deploymentIdJson) {
        DeploymentId deploymentId = deploymentIdJsonToDto(deploymentIdJson);
        validateDeploymentIdIsNumeric(deploymentId);
        workflowServiceImpl.deleteDeployedProcess(deploymentId);
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
