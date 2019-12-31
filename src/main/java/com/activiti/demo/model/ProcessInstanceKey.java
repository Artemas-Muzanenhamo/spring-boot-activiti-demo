package com.activiti.demo.model;

import java.util.Objects;

public class ProcessInstanceKey {
    private String processInstanceKey;

    public ProcessInstanceKey() { }

    public ProcessInstanceKey(String processInstanceKey) {
        this.processInstanceKey = processInstanceKey;
    }

    public String getProcessInstanceKey() {
        return processInstanceKey;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProcessInstanceKey that = (ProcessInstanceKey) o;
        return Objects.equals(processInstanceKey, that.processInstanceKey);
    }

    @Override
    public int hashCode() {
        return Objects.hash(processInstanceKey);
    }
}
