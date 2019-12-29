package com.activiti.demo.converter;

import com.activiti.demo.exception.InvalidTaskAssigneeException;
import com.activiti.demo.web.json.TaskAssignee;
import com.activiti.demo.web.json.TaskAssigneeJson;

import java.util.Optional;

public class TaskAssigneeConverter {
    public static TaskAssignee taskAssigneeJsonToDto(TaskAssigneeJson taskAssigneeJson) {
        return Optional.ofNullable(taskAssigneeJson)
                .map(TaskAssigneeJson::getTaskAssignee)
                .map(TaskAssignee::new)
                .orElseThrow(() -> new InvalidTaskAssigneeException("Task assignee supplied is not valid"));
    }
}
