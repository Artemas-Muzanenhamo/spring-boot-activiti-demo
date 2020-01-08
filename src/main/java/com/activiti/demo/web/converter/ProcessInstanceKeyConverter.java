package com.activiti.demo.web.converter;

import com.activiti.demo.exception.InvalidProcessInstanceKeyException;
import com.activiti.demo.web.json.ProcessInstanceKeyJson;
import com.activiti.demo.model.ProcessInstanceKey;

import java.util.Optional;

public class ProcessInstanceKeyConverter {

    private static final String MESSAGE = "Process instance key supplied is not valid";

    public static ProcessInstanceKey processInstanceKeyJsonToDto(ProcessInstanceKeyJson processInstanceKeyJson) {
        return Optional.ofNullable(processInstanceKeyJson)
                .map(ProcessInstanceKeyJson::getProcessInstanceKey)
                .filter(ProcessInstanceKeyConverter::nonEmpty)
                .map(ProcessInstanceKey::new)
                .orElseThrow(() -> new InvalidProcessInstanceKeyException(MESSAGE));
    }

    private static boolean nonEmpty(String processInstanceKey) {
        return !processInstanceKey.isEmpty();
    }
}
