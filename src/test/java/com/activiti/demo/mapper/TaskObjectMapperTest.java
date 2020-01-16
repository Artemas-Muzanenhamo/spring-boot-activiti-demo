package com.activiti.demo.mapper;

import com.activiti.demo.model.TaskObject;
import org.activiti.engine.impl.persistence.entity.TaskEntityImpl;
import org.activiti.engine.task.Task;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static com.activiti.demo.mapper.TaskObjectMapper.createTaskObject;
import static org.assertj.core.api.Assertions.assertThat;

class TaskObjectMapperTest {
    @Mock
    private Task task;

    @Test
    @DisplayName("Should map Task DTO to TaskObject")
    void mapToTaskObject() {
        task = new TaskEntityImpl();

        TaskObject taskObject = createTaskObject(task);

        assertThat(taskObject).isNotNull();
    }

    @Test
    @DisplayName("Should map null Task DTO to an empty TaskObject")
    void mapNullDtoToEmptyTaskObject() {
        TaskObject taskObject = createTaskObject(task);

        assertThat(taskObject).isNotNull();
    }
}