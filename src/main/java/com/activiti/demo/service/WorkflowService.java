package com.activiti.demo.service;

import com.activiti.demo.model.ProcessName;

public interface WorkflowService {
    void deployProcess(ProcessName processName);
}
