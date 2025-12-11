package com.prashant.tasks.mappers;

import com.prashant.tasks.domain.dto.TaskListDto;
import com.prashant.tasks.domain.entites.TaskList;

public interface TaskListMapper {
    TaskList fromDto(TaskListDto taskListDto);

    TaskListDto toDto(TaskList taskList);
}
