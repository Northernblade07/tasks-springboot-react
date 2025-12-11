package com.prashant.tasks.mappers.impl;

import com.prashant.tasks.domain.dto.TaskListDto;
import com.prashant.tasks.domain.entites.Task;
import com.prashant.tasks.domain.entites.TaskList;
import com.prashant.tasks.domain.entites.TaskStatus;
import com.prashant.tasks.mappers.TaskListMapper;
import com.prashant.tasks.mappers.TaskMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class TaskListMapperImpl implements TaskListMapper {

    private final TaskMapper taskMapper;

    public TaskListMapperImpl(TaskMapper taskMapper) {
        this.taskMapper = taskMapper;
    }

    @Override
    public TaskList fromDto(TaskListDto taskListDto) {
        return new TaskList(taskListDto.id(),
                taskListDto.title(),
                taskListDto.description(),
                Optional.ofNullable(taskListDto.tasks())
                        .map(tasks -> tasks.stream()
                                .map(taskMapper::fromDto).toList()
                        ).orElse(null),
                null,
                null
        );
    }

    @Override
    public TaskListDto toDto(TaskList taskList) {
        return new TaskListDto(taskList.getId(),
                taskList.getTitle(),
                taskList.getDescription(),
                Optional.ofNullable(taskList.getTasks())
                        .map(List::size).orElse(0),
                calculateTasklistProgress(taskList.getTasks()),
                Optional.ofNullable(taskList.getTasks()).map(tasks -> tasks.stream().map(taskMapper::toDto).toList()).orElse(null)
                );
    }

    private Double calculateTasklistProgress(List<Task> tasks){
        if(tasks == null || tasks.isEmpty()){
            return 0.0;
        }
      long ClosedTaskcount =  tasks.stream().filter(task -> TaskStatus.CLOSED == task.getStatus()).count();
        return ClosedTaskcount /(double)tasks.size();
    }

}
