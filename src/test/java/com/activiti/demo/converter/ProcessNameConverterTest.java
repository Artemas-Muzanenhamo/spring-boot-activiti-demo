package com.activiti.demo.converter;

import com.activiti.demo.exception.InvalidProcessNameException;
import com.activiti.demo.web.json.ProcessNameJson;
import com.activiti.demo.model.ProcessName;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.activiti.demo.converter.ProcessNameConverter.processNameJsonToDto;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ProcessNameConverterTest {

    private static final String PROCESS_NAME = "some-process";

    @Test
    @DisplayName("Should convert ProcessNameJson to ProcessName DTO")
    void convertProcessNameJsonToDto() {
        ProcessNameJson processNameJson = new ProcessNameJson(PROCESS_NAME);

        ProcessName processName = processNameJsonToDto(processNameJson);

        assertThat(processName).isNotNull();
        assertThat(processName.getProcessName()).isEqualTo(PROCESS_NAME);
    }

    @Test
    @DisplayName("Should throw an Exception when the ProcessNameJson value is null")
    void throwExceptionWhenProcessNameValueIsNull() {
        ProcessNameJson processNameJson = new ProcessNameJson(null);

        InvalidProcessNameException exception = assertThrows(InvalidProcessNameException.class, () -> processNameJsonToDto(processNameJson));

        assertThat(exception).hasMessage("Process name supplied is not valid");
    }

}