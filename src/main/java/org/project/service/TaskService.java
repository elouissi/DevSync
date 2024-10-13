package org.project.service;

import org.project.Enum.TypeStatus;
import org.project.entite.Task;
import org.project.repositorie.TaskRepository;

import java.util.List;

public class TaskService {
    private TaskRepository taskRepository;
    public TaskService(){
        taskRepository = new TaskRepository();
    }
    public List<Task> getALlTasks(){
        return taskRepository.getAllTasks();
    }
    public Task getById(int id){
        return taskRepository.getTaskById(id);
    }
    public void createTask(Task task){
         taskRepository.createTask(task) ;
    }
    public void deleteTask(int id){
        taskRepository.deleteTask(id);
    }
    public void updateTask(Task task){
        taskRepository.updateTask(task);
    }

    public void updateTaskStatus(int id, TypeStatus typeStatus) {
        Task task = this.getById(id);
        task.setStatus(typeStatus);
        this.updateTask(task);
    }
}
