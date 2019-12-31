package com.activiti.demo.web;

import com.activiti.demo.model.ProcessName;
import com.activiti.demo.service.WorkflowService;
import com.activiti.demo.web.json.ProcessNameJson;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class WorkflowControllerUnitTest {
    private WorkflowController workflowController;
    @Mock
    private WorkflowService workflowService;
    @Mock
    private ProcessEngine processEngine;
    @Mock
    private RepositoryService repositoryService;

    @BeforeEach
    void setUp() {
        workflowController = new WorkflowController(processEngine, repositoryService, workflowService);
    }

    @Test
    @DisplayName("Should deploy a process given a valid ProcessNameJson")
    void testDeploy() {
        ProcessNameJson processNameJson = new ProcessNameJson("some-process-name");
        ProcessName processName = new ProcessName("some-process-name");

        workflowController.deploy(processNameJson);

        verify(workflowService).deployProcess(processName);
    }
}
