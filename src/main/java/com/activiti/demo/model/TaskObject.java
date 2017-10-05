package com.activiti.demo.model;

import java.util.Date;
import java.util.Map;

import org.activiti.engine.task.DelegationState;

public class TaskObject{

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
	
	public TaskObject(String id, String name, String assignee) {
		super();
		this.id = id;
		this.name = name;
		this.assignee = assignee;
	}
	
	
	
	public TaskObject(String id, String name, String assignee, String description, String executionId,
			String owner, String processInstanceId, Date createTime, String taskDefinitionKey, Date dueDate,
			String parentTaskId, String tenantId, Map<String, Object> taskLocalVariables,
			Map<String, Object> processVariables, String processDefinitionId, DelegationState delegationState) {
		super();
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
	
	public String getDescription() {
		return description;
	}
	
	public String getOwner() {
		return owner;
	}
	
	public String getAssignee() {
		return assignee;
	}
	
	public String getProcessInstanceId() {
		return processInstanceId;
	}
	
	public String getExecutionId() {
		return executionId;
	}
	
	public String getProcessDefinitionId() {
		return processDefinitionId;
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
	
	public void setName(String name) {
		name = this.name;
	}
	
	public void setLocalizedName(String name) {
		name = this.name;
	}
	
	public void setDescription(String description) {
		description = this.description;
	}
	
	public void setLocalizedDescription(String description) {
		description = this.description;
	}
	
	public void setOwner(String owner) {
		owner = this.owner;
	}
	
	public void setAssignee(String assignee) {
		assignee = this.assignee;
	}
	
	public DelegationState getDelegationState() {

		return delegationState;
	}
	
	public void setDelegationState(DelegationState delegationState) {
		delegationState = this.delegationState;
	}
	
	public void setDueDate(Date dueDate) {
		dueDate = this.dueDate;
	}
	
	public void setParentTaskId(String parentTaskId) {
		parentTaskId = this.parentTaskId;
	}
	
	public void setTenantId(String tenantId) {
		tenantId = this.tenantId;
	}
	
}
