package com.activiti.demo.web.converter;

import com.activiti.demo.exception.InvalidTaskAssigneeException;
import com.activiti.demo.model.TaskAssignee;
import com.activiti.demo.web.json.TaskAssigneeJson;

import java.util.Optional;

public class TaskAssigneeConverter {
    public static TaskAssignee taskAssigneeJsonToDto(TaskAssigneeJson taskAssigneeJson) {
        return Optional.ofNullable(taskAssigneeJson)
                .map(TaskAssigneeJson::getTaskAssignee)
                .filter(TaskAssigneeConverter::nonEmpty)
                .map(TaskAssignee::new)
                .orElseThrow(() -> new InvalidTaskAssigneeException("Task assignee supplied is not valid"));
    }

    private static boolean nonEmpty(String taskAssignee) {
        return !taskAssignee.isEmpty();
    }
}
