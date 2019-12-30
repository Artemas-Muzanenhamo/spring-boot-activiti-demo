package com.activiti.demo.converter;

import com.activiti.demo.exception.InvalidTaskIdException;
import com.activiti.demo.web.json.TaskIdJson;
import com.activiti.demo.model.TaskId;

import java.util.Optional;

public class TaskIdConverter {
    public static TaskId taskIdJsonToDto(TaskIdJson taskIdJson) {
        return Optional.ofNullable(taskIdJson)
                .map(TaskIdJson::getTaskId)
                .map(TaskId::new)
                .orElseThrow(() -> new InvalidTaskIdException("Task Id is not valid"));
    }
}
