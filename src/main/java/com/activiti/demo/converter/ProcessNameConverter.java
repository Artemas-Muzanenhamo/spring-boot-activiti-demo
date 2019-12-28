package com.activiti.demo.converter;

import com.activiti.demo.json.ProcessNameJson;
import com.activiti.demo.model.ProcessName;

public class ProcessNameConverter {
    public static ProcessName processNameJsonToDto(ProcessNameJson processNameJson) {
        return new ProcessName(processNameJson.getProcessName());
    }
}
