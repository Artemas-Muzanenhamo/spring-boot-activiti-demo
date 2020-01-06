package com.activiti.demo.web.converter;

import com.activiti.demo.exception.InvalidProcessInstanceKeyException;
import com.activiti.demo.model.ProcessInstanceKey;
import com.activiti.demo.web.json.ProcessInstanceKeyJson;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.activiti.demo.web.converter.ProcessInstanceKeyConverter.processInstanceKeyJsonToDto;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

    @Test
    @DisplayName("Should throw an Exception when the ProcessInstanceKeyJson value is null")
    void throwExceptionWhenProcessInstanceKeyValueIsNull() {
        ProcessInstanceKeyJson processInstanceKeyJson = new ProcessInstanceKeyJson(null);

        InvalidProcessInstanceKeyException exception = assertThrows(InvalidProcessInstanceKeyException.class, () -> processInstanceKeyJsonToDto(processInstanceKeyJson));

        assertThat(exception).hasMessage("Process instance key supplied is not valid");
    }

    @Test
    @DisplayName("Should throw an Exception when the ProcessInstanceKeyJson is null")
    void throwExceptionWhenProcessInstanceKeyIsNull() {
        InvalidProcessInstanceKeyException exception = assertThrows(InvalidProcessInstanceKeyException.class, () -> processInstanceKeyJsonToDto(null));

        assertThat(exception).hasMessage("Process instance key supplied is not valid");
    }
}