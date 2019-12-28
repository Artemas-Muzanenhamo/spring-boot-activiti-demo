package com.activiti.demo.converter;

import com.activiti.demo.web.json.TaskAssignee;
import com.activiti.demo.web.json.TaskAssigneeJson;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.activiti.demo.converter.TaskAssigneeConverter.taskAssigneeJsonToDto;
import static org.assertj.core.api.Assertions.assertThat;

class TaskAssigneeConverterTest {

    private static final String ASSIGNEE = "some-assignee";

    @Test
    @DisplayName("Should convert TaskAssigneeJson to DTO")
    void convertTaskAssigneeJsonToDto() {
        TaskAssigneeJson taskAssigneeJson = new TaskAssigneeJson(ASSIGNEE);

        TaskAssignee taskAssignee = taskAssigneeJsonToDto(taskAssigneeJson);

        assertThat(taskAssignee).isNotNull();
        assertThat(taskAssignee.getTaskAssignee()).isEqualTo(ASSIGNEE);
    }
}