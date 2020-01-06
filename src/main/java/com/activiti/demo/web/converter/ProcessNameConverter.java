package com.activiti.demo.web.converter;

import com.activiti.demo.exception.InvalidProcessNameException;
import com.activiti.demo.web.json.ProcessNameJson;
import com.activiti.demo.model.ProcessName;

import java.util.Optional;

public class ProcessNameConverter {
    public static ProcessName processNameJsonToDto(ProcessNameJson processNameJson) {
        return Optional.ofNullable(processNameJson)
                .map(ProcessNameJson::getProcessName)
                .filter(ProcessNameConverter::nonEmpty)
                .map(ProcessName::new)
                .orElseThrow(() -> new InvalidProcessNameException("Process name supplied is not valid"));
    }

    private static boolean nonEmpty(String processName) {
        return !processName.isEmpty();
    }
}
