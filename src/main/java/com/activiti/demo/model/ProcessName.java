package com.activiti.demo.model;

import java.util.Objects;

public class ProcessName {
    private String processName;

    public ProcessName() { }

    public ProcessName(String processName) {
        this.processName = processName;
    }

    public String getProcessName() {
        return processName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProcessName that = (ProcessName) o;
        return Objects.equals(processName, that.processName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(processName);
    }
}
