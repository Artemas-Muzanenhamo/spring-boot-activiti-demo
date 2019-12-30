package com.activiti.demo.converter;

import com.activiti.demo.exception.InvalidTaskIdException;
import com.activiti.demo.web.json.TaskIdJson;
import com.activiti.demo.model.TaskId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.activiti.demo.converter.TaskIdConverter.taskIdJsonToDto;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


class TaskIdConverterTest {

    private static final String TASK_ID = "12353";

    @Test
    @DisplayName("Should convert TaskIdJson to TaskId DTO")
    void convertTaskIdJsonToDTO() {
        TaskIdJson taskIdJson = new TaskIdJson(TASK_ID);

        TaskId taskId = taskIdJsonToDto(taskIdJson);

        assertThat(taskId).isNotNull();
        assertThat(taskId.getTaskId()).isEqualTo(TASK_ID);
    }

    @Test
    @DisplayName("Should throw an exception when a TaskIdJson value is null")
    void throwExceptionWhenTaskIdJsonValueIsNull() {
        TaskIdJson taskIdJson = new TaskIdJson(null);

        InvalidTaskIdException exception = assertThrows(InvalidTaskIdException.class, () -> taskIdJsonToDto(taskIdJson));

        assertThat(exception).hasMessage("Task Id is not valid");
    }

    @Test
    @DisplayName("Should throw an exception when a TaskIdJson is null")
    void throwExceptionWhenTaskIdJsonIsNull() {
        InvalidTaskIdException exception = assertThrows(InvalidTaskIdException.class, () -> taskIdJsonToDto(null));

        assertThat(exception).hasMessage("Task Id is not valid");
    }
}