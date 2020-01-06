package com.activiti.demo.web.converter;

import com.activiti.demo.exception.InvalidTaskIdException;
import com.activiti.demo.web.json.TaskIdJson;
import com.activiti.demo.model.TaskId;

import java.util.Optional;

public class TaskIdConverter {
    private static final String MESSAGE = "Task Id is not valid";

    public static TaskId taskIdJsonToDto(TaskIdJson taskIdJson) {
        return Optional.ofNullable(taskIdJson)
                .map(TaskIdJson::getTaskId)
                .filter(TaskIdConverter::nonEmpty)
                .map(TaskId::new)
                .orElseThrow(() -> new InvalidTaskIdException(MESSAGE));
    }

    private static boolean nonEmpty(String taskId) {
        return !taskId.isEmpty();
    }
}
