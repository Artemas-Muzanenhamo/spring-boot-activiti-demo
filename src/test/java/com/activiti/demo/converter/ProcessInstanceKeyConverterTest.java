package com.activiti.demo.converter;

import com.activiti.demo.json.ProcessInstanceKeyJson;
import com.activiti.demo.model.ProcessInstanceKey;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.activiti.demo.converter.ProcessInstanceKeyConverter.processInstanceKeyJsonToDto;
import static org.assertj.core.api.Assertions.assertThat;

class ProcessInstanceKeyConverterTest {

    private static final String PROCESS_INSTANCE_KEY = "234422";

    @Test
    @DisplayName("Should convert ProcessInstanceKeyJson to DTO")
    void convertProcessInstanceKeyJsonToDto() {
        ProcessInstanceKeyJson processInstanceKeyJson = new ProcessInstanceKeyJson(PROCESS_INSTANCE_KEY);

        ProcessInstanceKey processInstanceKey = processInstanceKeyJsonToDto(processInstanceKeyJson);

        assertThat(processInstanceKey).isNotNull();
        assertThat(processInstanceKey.getProcessInstanceKey()).isEqualTo(PROCESS_INSTANCE_KEY);
    }
}