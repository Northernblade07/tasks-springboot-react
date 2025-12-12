package com.prashant.tasks.services;

import com.prashant.tasks.domain.entites.Task;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TaskService {
    List<Task> listTasks(UUID taskListId);
    Task createTask(UUID taskListId , Task task);
    Optional<Task> getTask(UUID taskListId , UUID TaskId);
    Task updatetask(UUID taskListId , UUID taskId , Task task );
    void deleteTask(UUID taskListId , UUID taskId);
}
