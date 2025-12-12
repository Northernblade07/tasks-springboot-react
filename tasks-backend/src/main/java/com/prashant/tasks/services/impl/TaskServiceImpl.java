package com.prashant.tasks.services.impl;

import com.prashant.tasks.domain.entites.Task;
import com.prashant.tasks.domain.entites.TaskList;
import com.prashant.tasks.domain.entites.TaskPriority;
import com.prashant.tasks.domain.entites.TaskStatus;
import com.prashant.tasks.repositories.TaskListRepository;
import com.prashant.tasks.repositories.TaskRepository;
import com.prashant.tasks.services.TaskService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final TaskListRepository taskListRepository;
    public TaskServiceImpl(TaskRepository taskRepository, TaskListRepository taskListRepository) {
        this.taskRepository = taskRepository;
        this.taskListRepository = taskListRepository;
    }

    @Override
    public List<Task> listTasks(UUID taskListId) {
       return taskRepository.findByTaskListId(taskListId);
    }
    @Transactional
    @Override
    public Task createTask(UUID taskListId, Task task) {
        if(task.getId() != null){
            throw new IllegalArgumentException("task has an id");
        }
        if(task.getTitle() == null || task.getTitle().isEmpty()){
            throw new IllegalArgumentException("task should have a title");
        }

        TaskPriority taskPriority = Optional.ofNullable(task.getPriority()).orElse(TaskPriority.MEDIUM);
        TaskStatus taskStatus = TaskStatus.OPEN;
        TaskList taskList = taskListRepository.findById(taskListId).orElseThrow(()-> new IllegalArgumentException("Invalid taks list id provided"));

        LocalDateTime now = LocalDateTime.now();

        Task saveTask = new Task(
                null,
                task.getTitle(),
                task.getDescription(),
                task.getDueDate(),
                taskStatus,
                taskPriority,
                taskList,
                now,
                now
        );
//        taskList.getTasks().add(saveTask);
        return taskRepository.save(saveTask);
    }

    @Override
    public Optional<Task> getTask(UUID taskListId, UUID TaskId) {
       return taskRepository.findByTaskListIdAndId(taskListId , TaskId);
    }

    @Transactional
    @Override
    public Task updatetask(UUID taskListId, UUID taskId, Task task) {
        if(taskListId == null){
            throw new IllegalArgumentException("invalid tasklist id");
        }

        if(task.getId()==null){
            throw new IllegalArgumentException("task has no id");
        }
        if(task.getPriority() == null|| task.getStatus() == null){
            throw new IllegalArgumentException("fields are incomplete - priority or status");
        }
        if(!Objects.equals(taskId , task.getId())){
            throw new IllegalArgumentException("invalid task id");
        }

       Task task1 = taskRepository.findByTaskListIdAndId(taskListId , taskId).orElseThrow(()-> new IllegalArgumentException("task not found"));

        task1.setTitle(task.getTitle());
        task1.setDescription(task.getDescription());
        task1.setPriority(task.getPriority());
        task1.setStatus(task.getStatus());
        task1.setDueDate(task.getDueDate());
        task1.setUpdatedAt(LocalDateTime.now());


        return taskRepository.save(task1);

    }

    @Transactional
    @Override
    public void deleteTask(UUID taskListId, UUID taskId) {
        taskRepository.deleteByTaskListIdAndId(taskListId , taskId);
    }

}
