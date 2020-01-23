package com.activiti.demo.web.converter;

import com.activiti.demo.model.TaskDTO;
import com.activiti.demo.web.json.TaskJson;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.activiti.demo.web.converter.TaskConverter.taskDtoToJson;
import static com.activiti.demo.web.converter.TaskConverter.taskDtoListToJsonList;
import static org.assertj.core.api.Assertions.assertThat;

class TaskConverterTest {
    @Test
    @DisplayName("Should convert List<TaskDTO> to List<TaskJson>")
    void convertToListOfTaskJson() {
        TaskDTO taskDTO = new TaskDTO();
        List<TaskDTO> taskDTOS = List.of(taskDTO);

        List<TaskJson> tasks = taskDtoListToJsonList(taskDTOS);

        assertThat(tasks).isNotEmpty();
        TaskJson taskJson = tasks.get(0);
        assertThat(taskJson).isNotNull();
    }

    @Test
    @DisplayName("Should convert TaskDTO to TaskJson")
    void convertToTaskJson() {
        TaskDTO taskDTO = new TaskDTO();

        TaskJson task = taskDtoToJson(taskDTO);

        assertThat(task).isNotNull();
    }

    @Test
    @DisplayName("Should return an empty TaskJson when TaskDTO is null")
    void returnEmptyTaskJson() {
        TaskJson taskJson = taskDtoToJson(null);

        assertThat(taskJson).isNotNull();
    }
}