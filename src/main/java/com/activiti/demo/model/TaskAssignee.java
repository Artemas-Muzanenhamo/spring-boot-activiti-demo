package com.activiti.demo.model;

import java.util.Objects;

public class TaskAssignee {
    private String taskAssignee;

    public TaskAssignee() { }

    public TaskAssignee(String taskAssignee) {
        this.taskAssignee = taskAssignee;
    }

    public String getTaskAssignee() {
        return taskAssignee;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskAssignee that = (TaskAssignee) o;
        return Objects.equals(taskAssignee, that.taskAssignee);
    }

    @Override
    public int hashCode() {
        return Objects.hash(taskAssignee);
    }
}
