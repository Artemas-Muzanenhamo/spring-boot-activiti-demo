package com.activiti.demo.web.json;

public class ProcessInstanceKeyJson {
    private String processInstanceKey;

    public ProcessInstanceKeyJson() { }

    public ProcessInstanceKeyJson(String processInstanceKey) {
        this.processInstanceKey = processInstanceKey;
    }

    public String getProcessInstanceKey() {
        return processInstanceKey;
    }
}
