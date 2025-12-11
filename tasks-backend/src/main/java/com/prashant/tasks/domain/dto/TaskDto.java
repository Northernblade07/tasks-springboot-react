package com.prashant.tasks.domain.dto;

import com.prashant.tasks.domain.entites.TaskPriority;
import com.prashant.tasks.domain.entites.TaskStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record TaskDto(UUID id,
                      String title,
                      String description,
                      LocalDateTime dueDate,
                      TaskStatus status,
                      TaskPriority priority
) {


}
