package com.activiti.demo.service;

import com.activiti.demo.model.ProcessInstanceKey;
import com.activiti.demo.model.ProcessName;
import com.activiti.demo.model.TaskAssignee;
import com.activiti.demo.model.TaskObject;

import java.util.List;

public interface WorkflowService {
    void deployProcess(ProcessName processName);

    void startProcess(ProcessInstanceKey processInstanceKey);

    List<TaskObject> findTaskByAssignee(TaskAssignee taskAssignee);
}
