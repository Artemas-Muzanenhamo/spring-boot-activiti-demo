package com.activiti.demo.converter;

import com.activiti.demo.json.ProcessInstanceKeyJson;
import com.activiti.demo.model.ProcessInstanceKey;

public class ProcessInstanceKeyConverter {
    public static ProcessInstanceKey processInstanceKeyJsonToDto(ProcessInstanceKeyJson processInstanceKeyJson) {
        return new ProcessInstanceKey(processInstanceKeyJson.getProcessInstanceKey());
    }
}
