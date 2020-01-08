package com.activiti.demo.service;

import com.activiti.demo.model.*;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class WorkflowServiceImpl implements WorkflowService {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private ProcessEngine processEngine;
    private RepositoryService repositoryService;

    public WorkflowServiceImpl(ProcessEngine processEngine, RepositoryService repositoryService) {
        this.processEngine = processEngine;
        this.repositoryService = repositoryService;
    }

    @Override
    public void deployProcess(ProcessName processName) {
        Deployment deployment = processEngine.getRepositoryService()
                .createDeployment()
                .addClasspathResource("processes/my-process.bpmn20.xml")
                .name(processName.getProcessName())
                .deploy();
        log.info("DEPLOYMENT ID:" + deployment.getId());
        log.info("DEPLOYMENT NAME:" + deployment.getName());
    }

    @Override
    public void startProcess(ProcessInstanceKey processInstanceKey) {
        ProcessInstance processInstance = processEngine.getRuntimeService()
                .startProcessInstanceByKey(processInstanceKey.getProcessInstanceKey());
        log.info("PROCESS INSTANCE ID:-->" + processInstance.getId());
        log.info("PROCESS INSTANCE DEF ID:-->" + processInstance.getProcessDefinitionId());
    }

    @Override
    public List<TaskObject> findTaskByAssignee(TaskAssignee taskAssignee) {
        return processEngine.getTaskService()
                .createTaskQuery()
                .taskAssignee(taskAssignee.getTaskAssignee())
                .list()
                .stream()
                .map(WorkflowServiceImpl::createTaskObject)
                .collect(toList());
    }

    @Override
    public TaskObject findTaskByTaskId(TaskId taskId) {
        return processEngine.getTaskService().createTaskQuery()
                .taskId(taskId.getTaskId()).list()
                .stream()
                .map(WorkflowServiceImpl::createTaskObject)
                .findFirst()
                .orElse(new TaskObject());
    }

    @Override
    public List<TaskObject> findAllTasks() {
        return processEngine.getTaskService()
                .createTaskQuery()
                .list()
                .stream()
                .map(WorkflowServiceImpl::createTaskObject)
                .collect(toList());
    }

    @Override
    public void completeTask(TaskId taskId) {
        log.info("ABOUT TO DELETE TASKID: " + taskId.getTaskId());
        processEngine.getTaskService()
                .complete(taskId.getTaskId());
        log.info("DELETED TASKID: " + taskId.getTaskId());
    }

    @Override
    public List<DeploymentObject> findAllDeployedProcesses() {
        return processEngine.getRepositoryService().createDeploymentQuery().list()
                .stream()
                .map(WorkflowServiceImpl::createDeploymentObject)
                .collect(toList());
    }

    @Override
    public void deleteDeployedProcess(DeploymentId deploymentId) {
        log.info("ABOUT TO DELETE PROCESS: " + deploymentId.getDeploymentId());
        repositoryService.deleteDeployment(deploymentId.getDeploymentId());
    }

    private static DeploymentObject createDeploymentObject(Deployment deployment) {
        return new DeploymentObject(deployment.getId(), deployment.getName(),
                deployment.getDeploymentTime(), deployment.getCategory(), deployment.getKey(), deployment.getTenantId());
    }

    private static TaskObject createTaskObject(Task task) {
        return new TaskObject(task.getId(), task.getName(), task.getAssignee(), task.getDescription(),
                task.getExecutionId(), task.getOwner(), task.getProcessInstanceId(), task.getCreateTime(),
                task.getTaskDefinitionKey(), task.getDueDate(), task.getParentTaskId(), task.getTenantId(),
                task.getTaskLocalVariables(), task.getProcessVariables(), task.getProcessDefinitionId(),
                task.getDelegationState());
    }
}
