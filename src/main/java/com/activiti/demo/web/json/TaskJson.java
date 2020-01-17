package com.activiti.demo.web.json;

import org.activiti.engine.task.DelegationState;

import java.util.Date;
import java.util.Map;

public class TaskJson {
    private String id;
    private String name;
    private String assignee;
    private String description;
    private String executionId;
    private String owner;
    private String processInstanceId;
    private Date createTime;
    private String taskDefinitionKey;
    private Date dueDate;
    private String parentTaskId;
    private String tenantId;
    private Map<String, Object> taskLocalVariables;
    private Map<String, Object> processVariables;
    private String processDefinitionId;
    private DelegationState delegationState;

    public TaskJson() {
    }

    public TaskJson(String id, String name, String assignee, String description, String executionId, String owner,
                    String processInstanceId, Date createTime, String taskDefinitionKey, Date dueDate,
                    String parentTaskId, String tenantId, Map<String, Object> taskLocalVariables,
                    Map<String, Object> processVariables, String processDefinitionId, DelegationState delegationState) {
        this.id = id;
        this.name = name;
        this.assignee = assignee;
        this.description = description;
        this.executionId = executionId;
        this.owner = owner;
        this.processInstanceId = processInstanceId;
        this.createTime = createTime;
        this.taskDefinitionKey = taskDefinitionKey;
        this.dueDate = dueDate;
        this.parentTaskId = parentTaskId;
        this.tenantId = tenantId;
        this.taskLocalVariables = taskLocalVariables;
        this.processVariables = processVariables;
        this.processDefinitionId = processDefinitionId;
        this.delegationState = delegationState;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAssignee() {
        return assignee;
    }

    public String getDescription() {
        return description;
    }

    public String getExecutionId() {
        return executionId;
    }

    public String getOwner() {
        return owner;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public String getTaskDefinitionKey() {
        return taskDefinitionKey;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public String getParentTaskId() {
        return parentTaskId;
    }

    public String getTenantId() {
        return tenantId;
    }

    public Map<String, Object> getTaskLocalVariables() {
        return taskLocalVariables;
    }

    public Map<String, Object> getProcessVariables() {
        return processVariables;
    }

    public String getProcessDefinitionId() {
        return processDefinitionId;
    }

    public DelegationState getDelegationState() {
        return delegationState;
    }
}
