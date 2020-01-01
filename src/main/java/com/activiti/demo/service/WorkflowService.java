package com.activiti.demo.service;

import com.activiti.demo.model.*;

import java.util.List;

public interface WorkflowService {
    void deployProcess(ProcessName processName);

    void startProcess(ProcessInstanceKey processInstanceKey);

    List<TaskObject> findTaskByAssignee(TaskAssignee taskAssignee);

    TaskObject findTaskByTaskId(TaskId taskId);

    List<TaskObject> findAllTasks();

    void completeTask(TaskId taskId);

    List<DeploymentObject> findAllDeployedProcesses();

    void deleteDeployedProcess(DeploymentId deploymentId);
}
