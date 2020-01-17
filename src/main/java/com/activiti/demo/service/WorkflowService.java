package com.activiti.demo.service;

import com.activiti.demo.model.*;

import java.util.List;

public interface WorkflowService {
    void deployProcess(ProcessName processName);

    void startProcess(ProcessInstanceKey processInstanceKey);

    List<TaskDTO> findTaskByAssignee(TaskAssignee taskAssignee);

    TaskDTO findTaskByTaskId(TaskId taskId);

    List<TaskDTO> findAllTasks();

    void completeTask(TaskId taskId);

    List<DeploymentObject> findAllDeployedProcesses();

    void deleteDeployedProcess(DeploymentId deploymentId);
}
