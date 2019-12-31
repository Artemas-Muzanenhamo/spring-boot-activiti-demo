package com.activiti.demo.model;

import java.util.Objects;

public class TaskId {
    private String taskId;

    public TaskId() { }

    public TaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskId() {
        return taskId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskId taskId1 = (TaskId) o;
        return Objects.equals(taskId, taskId1.taskId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(taskId);
    }
}
