package com.activiti.demo.service;

import com.activiti.demo.mapper.TaskDTOMapper;
import com.activiti.demo.model.*;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.lang.String.format;
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
        log.info(format("DEPLOYMENT ID: %s", deployment.getId()));
        log.info(format("DEPLOYMENT NAME: %s", deployment.getName()));
    }

    @Override
    public void startProcess(ProcessInstanceKey processInstanceKey) {
        ProcessInstance processInstance = processEngine.getRuntimeService()
                .startProcessInstanceByKey(processInstanceKey.getProcessInstanceKey());
        log.info(format("PROCESS INSTANCE ID: %s", processInstance.getId()));
        log.info(format("PROCESS INSTANCE ID: %s", processInstance.getId()));
        log.info(format("PROCESS INSTANCE DEF ID: %s", processInstance.getProcessDefinitionId()));
    }

    @Override
    public List<TaskDTO> findTaskByAssignee(TaskAssignee taskAssignee) {
        return processEngine.getTaskService()
                .createTaskQuery()
                .taskAssignee(taskAssignee.getTaskAssignee())
                .list()
                .stream()
                .map(TaskDTOMapper::toTaskDTO)
                .collect(toList());
    }

    @Override
    public TaskDTO findTaskByTaskId(TaskId taskId) {
        return processEngine.getTaskService().createTaskQuery()
                .taskId(taskId.getTaskId()).list()
                .stream()
                .map(TaskDTOMapper::toTaskDTO)
                .findFirst()
                .orElse(new TaskDTO());
    }

    @Override
    public List<TaskDTO> findAllTasks() {
        return processEngine.getTaskService()
                .createTaskQuery()
                .list()
                .stream()
                .map(TaskDTOMapper::toTaskDTO)
                .collect(toList());
    }

    @Override
    public void completeTask(TaskId taskId) {
        log.info(format("ABOUT TO COMPLETE A TASK WITH TASK ID: %s", taskId.getTaskId()));
        processEngine.getTaskService()
                .complete(taskId.getTaskId());
        log.info(format("COMPLETED A TASK WITH TASK ID: %s", taskId.getTaskId()));
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
        log.info(format("ABOUT TO DELETE PROCESS WITH DEPLOYMENT ID: %s", deploymentId.getDeploymentId()));
        repositoryService.deleteDeployment(deploymentId.getDeploymentId());
    }

    private static DeploymentObject createDeploymentObject(Deployment deployment) {
        return new DeploymentObject(deployment.getId(), deployment.getName(),
                deployment.getDeploymentTime(), deployment.getCategory(), deployment.getKey(), deployment.getTenantId());
    }
}
