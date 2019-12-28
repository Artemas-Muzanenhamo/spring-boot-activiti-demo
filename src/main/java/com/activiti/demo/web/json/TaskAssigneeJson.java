package com.activiti.demo.web.json;

public class TaskAssigneeJson {
    private String taskAssignee;

    public TaskAssigneeJson() { }

    public TaskAssigneeJson(String taskAssignee) {
        this.taskAssignee = taskAssignee;
    }

    public String getTaskAssignee() {
        return taskAssignee;
    }
}
