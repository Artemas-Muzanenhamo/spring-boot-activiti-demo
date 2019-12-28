package com.activiti.demo.converter;

import com.activiti.demo.json.TaskIdJson;
import com.activiti.demo.model.TaskId;

public class TaskIdConverter {
    public static TaskId taskIdJsonToDto(TaskIdJson taskIdJson) {
        return new TaskId(taskIdJson.getTaskId());
    }
}
