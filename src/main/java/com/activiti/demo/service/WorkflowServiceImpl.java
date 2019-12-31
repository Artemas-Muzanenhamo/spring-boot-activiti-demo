package com.activiti.demo.service;

import com.activiti.demo.model.ProcessName;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.repository.Deployment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class WorkflowServiceImpl implements WorkflowService {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private ProcessEngine processEngine;

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
}
