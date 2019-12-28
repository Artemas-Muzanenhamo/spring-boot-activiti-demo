package com.activiti.demo.converter;

import com.activiti.demo.json.TaskIdJson;
import com.activiti.demo.model.TaskId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.activiti.demo.converter.TaskIdConverter.taskIdJsonToDto;
import static org.assertj.core.api.Assertions.assertThat;


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
}