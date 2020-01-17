package com.activiti.demo.web.converter;

import com.activiti.demo.model.TaskDTO;
import com.activiti.demo.web.json.TaskJson;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class TaskConverter {
    public static List<TaskJson> tasksDtoToJson(List<TaskDTO> taskDTOS) {
        return taskDTOS.stream()
                .map(task -> new TaskJson(
                        task.getId(), task.getName(), task.getAssignee(), task.getDescription(), task.getExecutionId(),
                        task.getOwner(), task.getProcessInstanceId(), task.getCreateTime(), task.getTaskDefinitionKey(),
                        task.getDueDate(), task.getParentTaskId(), task.getTenantId(), task.getTaskLocalVariables(),
                        task.getProcessVariables(), task.getProcessDefinitionId(), task.getDelegationState()
                ))
                .collect(toList());
    }
}
