package com.activiti.demo.web.converter;

import com.activiti.demo.model.TaskDTO;
import com.activiti.demo.web.json.TaskJson;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class TaskConverter {
    public static List<TaskJson> taskDtoListToJsonList(List<TaskDTO> taskDTOS) {
        return taskDTOS.stream()
                .map(TaskConverter::taskDtoToJson)
                .collect(toList());
    }

    public static TaskJson taskDtoToJson(TaskDTO taskDTO) {
        return new TaskJson(
                taskDTO.getId(), taskDTO.getName(), taskDTO.getAssignee(), taskDTO.getDescription(), taskDTO.getExecutionId(),
                taskDTO.getOwner(), taskDTO.getProcessInstanceId(), taskDTO.getCreateTime(), taskDTO.getTaskDefinitionKey(),
                taskDTO.getDueDate(), taskDTO.getParentTaskId(), taskDTO.getTenantId(), taskDTO.getTaskLocalVariables(),
                taskDTO.getProcessVariables(), taskDTO.getProcessDefinitionId(), taskDTO.getDelegationState()
        );
    }
}
