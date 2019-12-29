package com.activiti.demo.converter;

import com.activiti.demo.exception.InvalidProcessInstanceKey;
import com.activiti.demo.web.json.ProcessInstanceKeyJson;
import com.activiti.demo.model.ProcessInstanceKey;

import java.util.Optional;

public class ProcessInstanceKeyConverter {
    public static ProcessInstanceKey processInstanceKeyJsonToDto(ProcessInstanceKeyJson processInstanceKeyJson) {
        return Optional.ofNullable(processInstanceKeyJson)
                .map(ProcessInstanceKeyJson::getProcessInstanceKey)
                .map(ProcessInstanceKey::new)
                .orElseThrow(() -> new InvalidProcessInstanceKey("Process instance key supplied is not valid"));
    }
}
