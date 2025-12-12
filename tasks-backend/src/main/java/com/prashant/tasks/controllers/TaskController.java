package com.prashant.tasks.controllers;

import com.prashant.tasks.domain.dto.TaskDto;
import com.prashant.tasks.domain.entites.Task;
import com.prashant.tasks.mappers.TaskMapper;
import com.prashant.tasks.services.TaskService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(path = "/task-lists/{taskListId}")
public class TaskController {

    private final TaskService taskService;
    private final TaskMapper taskMapper;
    public TaskController(TaskService taskService, TaskMapper taskMapper) {
        this.taskService = taskService;
        this.taskMapper = taskMapper;
    }

    @GetMapping(path = "/tasks")
    public List<TaskDto> listTasks(@PathVariable("taskListId") UUID taskListId){
        return taskService.listTasks(taskListId).stream().map(taskMapper::toDto).toList();
    }

    @PostMapping(path = "/tasks")
    public TaskDto createTask(@PathVariable("taskListId") UUID id,@RequestBody TaskDto taskDto){
       Task task = taskService.createTask( id, taskMapper.fromDto(taskDto));
        return taskMapper.toDto(task);
    }

    @GetMapping(path = "/tasks/{task_id}")
    public Optional<TaskDto> getTask(@PathVariable("taskListId") UUID taskListId , @PathVariable("task_id") UUID taskId){
        return taskService.getTask(taskListId , taskId).map(taskMapper::toDto);
    }

    @PutMapping(path = "/tasks/{task_id}")
    public TaskDto updateTask(@PathVariable("taskListId") UUID taksListId , @PathVariable("task_id") UUID taskId , @RequestBody TaskDto taskDto){
        Task task = taskService.updatetask(taksListId , taskId , taskMapper.fromDto(taskDto));
        return taskMapper.toDto(task);

    }

    @DeleteMapping(path = "/tasks/{task_id}")
    public void deleteTask(@PathVariable("taskListId") UUID taskListId , @PathVariable("task_id") UUID taskid){
        taskService.deleteTask(taskListId , taskid);
    }
}
