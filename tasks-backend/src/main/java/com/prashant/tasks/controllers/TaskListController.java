package com.prashant.tasks.controllers;

import com.prashant.tasks.domain.dto.TaskListDto;
import com.prashant.tasks.domain.entites.TaskList;
import com.prashant.tasks.mappers.TaskListMapper;
import com.prashant.tasks.services.TaskListService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(path = "/task-lists")
public class TaskListController {

    private final TaskListService taskListService;
    private final TaskListMapper taskListMapper;

//    or you can use lombok annotation -@RequiredArgsConstructor
    public TaskListController(TaskListService taskListService, TaskListMapper taskListMapper) {
        this.taskListService = taskListService;
        this.taskListMapper = taskListMapper;
    }


    @GetMapping
    public List<TaskListDto> listTaskList(){
       return taskListService.listTaskLists().stream().map(taskListMapper :: toDto).toList();
    }

    @PostMapping
    public TaskListDto createTaskList(@RequestBody TaskListDto taskListDto){
        TaskList createdTaskList =  taskListService.createTaskList(taskListMapper.fromDto(taskListDto));
        return taskListMapper.toDto(createdTaskList);
    }

    @GetMapping("/{task_list_id}")
    public Optional<TaskListDto> getTasklistById(@PathVariable("task_list_id") UUID taskListId){
        return taskListService.getTaskList(taskListId).map(taskListMapper::toDto);
    }

    @PutMapping(path = "/{task_list_id}")
    public TaskListDto updateTaskList(
            @PathVariable("task_list_id") UUID taskListId ,
            @RequestBody TaskListDto taskListDto
        ){
      TaskList updatedtasklist =  taskListService.updateTaskList(taskListId , taskListMapper.fromDto(taskListDto));
        return taskListMapper.toDto(updatedtasklist);
    }

    @DeleteMapping(path = "/{task_list_id}")
    public void deleteTasklist(@PathVariable("task_list_id") UUID id){
        taskListService.deleteTaskList(id);
    }



}
