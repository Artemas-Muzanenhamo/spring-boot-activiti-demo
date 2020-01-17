package com.activiti.demo.mapper;

import com.activiti.demo.model.TaskDTO;
import org.activiti.engine.task.Task;

import java.util.Optional;

public class TaskDTOMapper {
    public static TaskDTO toTaskDTO(Task task) {

        return Optional.ofNullable(task)
                .map(taskEntity -> new TaskDTO(taskEntity.getId(), taskEntity.getName(), taskEntity.getAssignee(), taskEntity.getDescription(),
                        taskEntity.getExecutionId(), taskEntity.getOwner(), taskEntity.getProcessInstanceId(), taskEntity.getCreateTime(),
                        taskEntity.getTaskDefinitionKey(), taskEntity.getDueDate(), taskEntity.getParentTaskId(), taskEntity.getTenantId(),
                        taskEntity.getTaskLocalVariables(), taskEntity.getProcessVariables(), taskEntity.getProcessDefinitionId(),
                        taskEntity.getDelegationState()))
                .orElse(new TaskDTO());
    }
}
