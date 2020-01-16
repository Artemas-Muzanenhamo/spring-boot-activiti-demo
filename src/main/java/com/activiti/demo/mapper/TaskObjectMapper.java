package com.activiti.demo.mapper;

import com.activiti.demo.model.TaskObject;
import org.activiti.engine.task.Task;

import java.util.Optional;

public class TaskObjectMapper {
    public static TaskObject createTaskObject(Task task) {

        return Optional.ofNullable(task)
                .map(taskEntity -> new TaskObject(taskEntity.getId(), taskEntity.getName(), taskEntity.getAssignee(), taskEntity.getDescription(),
                        taskEntity.getExecutionId(), taskEntity.getOwner(), taskEntity.getProcessInstanceId(), taskEntity.getCreateTime(),
                        taskEntity.getTaskDefinitionKey(), taskEntity.getDueDate(), taskEntity.getParentTaskId(), taskEntity.getTenantId(),
                        taskEntity.getTaskLocalVariables(), taskEntity.getProcessVariables(), taskEntity.getProcessDefinitionId(),
                        taskEntity.getDelegationState()))
                .orElse(new TaskObject());
    }
}
