package com.prashant.tasks.services.impl;

import com.prashant.tasks.domain.entites.Task;
import com.prashant.tasks.domain.entites.TaskList;
import com.prashant.tasks.repositories.TaskListRepository;
import com.prashant.tasks.services.TaskListService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
class TaskListServiceImpl implements TaskListService {

    private final TaskListRepository taskListRepository;

    TaskListServiceImpl(TaskListRepository taskListRepository) {
        this.taskListRepository = taskListRepository;
    }

    @Override
    public List<TaskList> listTaskLists() {
        return taskListRepository.findAll();
    }

    @Override
    public TaskList createTaskList(TaskList taskList) {
        if(taskList.getId() !=null){
            throw new  IllegalArgumentException("taskList already has an Id!");
        }

        if(taskList.getTitle() ==null || taskList.getTitle().isEmpty()){
            throw new IllegalArgumentException("title should be present");
        }

        LocalDateTime now = LocalDateTime.now();
        return  taskListRepository.save(new TaskList(
                null,
                taskList.getTitle(),
                taskList.getDescription(),
                null,
                now,
                now

        ));
    }

    @Override
    public Optional<TaskList> getTaskList(UUID id) {
        return  taskListRepository.findById(id);
    }

    @Override
    public TaskList updateTaskList(UUID id, TaskList taskList) {
        if(taskList.getId() == null){
            throw new IllegalArgumentException("task list has no id");
        }
        if(!Objects.equals(taskList.getId() , id)){
            throw new IllegalArgumentException("not permitted");
        }

        TaskList existingTaskList = taskListRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("Task list not found"));

        existingTaskList.setTitle(taskList.getTitle());
        existingTaskList.setDescription(taskList.getDescription());
        existingTaskList.setUpdatedAt(LocalDateTime.now());

        return taskListRepository.save(existingTaskList);
    }

    @Override
    public void deleteTaskList(UUID id) {
        taskListRepository.deleteById(id);
    }

}