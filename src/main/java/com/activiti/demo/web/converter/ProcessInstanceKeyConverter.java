package com.activiti.demo.web.converter;

import com.activiti.demo.exception.InvalidProcessInstanceKeyException;
import com.activiti.demo.web.json.ProcessInstanceKeyJson;
import com.activiti.demo.model.ProcessInstanceKey;

import java.util.Optional;

public class ProcessInstanceKeyConverter {
    public static ProcessInstanceKey processInstanceKeyJsonToDto(ProcessInstanceKeyJson processInstanceKeyJson) {
        return Optional.ofNullable(processInstanceKeyJson)
                .map(ProcessInstanceKeyJson::getProcessInstanceKey)
                .map(ProcessInstanceKey::new)
                .orElseThrow(() -> new InvalidProcessInstanceKeyException("Process instance key supplied is not valid"));
    }
}
