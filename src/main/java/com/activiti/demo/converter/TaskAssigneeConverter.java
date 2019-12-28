package com.activiti.demo.converter;

import com.activiti.demo.json.TaskAssignee;
import com.activiti.demo.json.TaskAssigneeJson;

public class TaskAssigneeConverter {
    public static TaskAssignee taskAssigneeJsonToDto(TaskAssigneeJson taskAssigneeJson) {
        return new TaskAssignee(taskAssigneeJson.getTaskAssignee());
    }
}
