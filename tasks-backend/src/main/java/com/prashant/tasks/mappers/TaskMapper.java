package com.prashant.tasks.mappers;

import com.prashant.tasks.domain.dto.TaskDto;
import com.prashant.tasks.domain.entites.Task;

public interface TaskMapper {

    Task fromDto(TaskDto taskDto);

    TaskDto toDto(Task task);
}
