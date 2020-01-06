package com.activiti.demo.web.converter;

import com.activiti.demo.exception.InvalidTaskAssigneeException;
import com.activiti.demo.model.TaskAssignee;
import com.activiti.demo.web.json.TaskAssigneeJson;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.activiti.demo.web.converter.TaskAssigneeConverter.taskAssigneeJsonToDto;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TaskAssigneeConverterTest {

    private static final String ASSIGNEE = "some-assignee";
    private static final String MESSAGE = "Task assignee supplied is not valid";

    @Test
    @DisplayName("Should convert TaskAssigneeJson to DTO")
    void convertTaskAssigneeJsonToDto() {
        TaskAssigneeJson taskAssigneeJson = new TaskAssigneeJson(ASSIGNEE);

        TaskAssignee taskAssignee = taskAssigneeJsonToDto(taskAssigneeJson);

        assertThat(taskAssignee).isNotNull();
        assertThat(taskAssignee.getTaskAssignee()).isEqualTo(ASSIGNEE);
    }

    @Test
    @DisplayName("Should throw an exception when TaskAssignee value is null")
    void throwExceptionWhenTaskAssigneeVaueIsNull() {
        TaskAssigneeJson taskAssigneeJson = new TaskAssigneeJson(null);

        InvalidTaskAssigneeException exception = assertThrows(InvalidTaskAssigneeException.class, () -> taskAssigneeJsonToDto(taskAssigneeJson));

        assertThat(exception).hasMessage(MESSAGE);
    }

    @Test
    @DisplayName("Should throw an exception when TaskAssignee value is empty")
    void throwExceptionWhenTaskAssigneeVaueIsEmpty() {
        TaskAssigneeJson taskAssigneeJson = new TaskAssigneeJson("");

        InvalidTaskAssigneeException exception = assertThrows(InvalidTaskAssigneeException.class, () -> taskAssigneeJsonToDto(taskAssigneeJson));

        assertThat(exception).hasMessage(MESSAGE);
    }
}