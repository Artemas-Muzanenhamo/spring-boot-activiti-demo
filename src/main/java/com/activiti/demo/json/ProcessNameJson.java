package com.activiti.demo.json;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize
public class ProcessNameJson {
    private String processName;

    public ProcessNameJson() { }

    public ProcessNameJson(String processName) {
        this.processName = processName;
    }

    public String getProcessName() {
        return processName;
    }
}
