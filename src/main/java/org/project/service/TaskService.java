package org.project.service;

import org.project.Enum.TypeRole;
import org.project.Enum.TypeStatus;
import org.project.entite.Task;
import org.project.entite.User;
import org.project.error.TaskAlreadyExistsException;
import org.project.error.TaskIsNullException;
import org.project.repositorie.TaskRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

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

        if (task == null){
            throw  new TaskIsNullException();
        }
        if (task.getDateDebut().isAfter(task.getDateFin())) {
            throw new IllegalArgumentException("La date de début ne peut pas être après la date de fin");
        }
        if (taskRepository.getTaskById(task.getId()) != null) {
            throw new TaskAlreadyExistsException(task.getId());
        }
            taskRepository.createTask(task) ;
    }
    public void deleteTask(int id){
        taskRepository.deleteTask(id);
    }
    public void updateTask(Task task){

        if (task == null){
            throw  new TaskIsNullException();
        }
        Task existingTask = taskRepository.getTaskById(task.getId());
        if (existingTask == null) {
            throw new TaskIsNullException();

        }
        taskRepository.updateTask(task);
    }

    public void updateTaskStatus(int id, TypeStatus typeStatus) {
        Task task = this.getById(id);
        task.setStatus(typeStatus);
        this.updateTask(task);
    }

}
