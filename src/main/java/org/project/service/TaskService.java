package org.project.service;

import org.project.Enum.TypeRole;
import org.project.Enum.TypeStatus;
import org.project.entite.Task;
import org.project.entite.User;
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
    public List<Task> getTasksForManager(User manager, String filter) {

        if (!manager.getRole().equals(TypeRole.MANAGER)) {
            throw new IllegalArgumentException("L'utilisateur n'est pas un manager.");
        }


        List<Task> allTasks = taskRepository.getAllTasks();


        List<Task> managerTasks = allTasks.stream()
                .filter(task -> task.getCreatedBy().equals(manager))
                .collect(Collectors.toList());


        return applyDateFilter(managerTasks, filter);
    }
    private List<Task> applyDateFilter(List<Task> tasks, String filter) {
        LocalDate now = LocalDate.now();

        switch (filter.toLowerCase()) {
            case "semaine":
                return tasks.stream()
                        .filter(task -> task.getDateDebut() != null &&
                                task.getDateDebut().isAfter(now.minusWeeks(1)))
                        .collect(Collectors.toList());
            case "mois":
                return tasks.stream()
                        .filter(task -> task.getDateDebut() != null &&
                                task.getDateDebut().isAfter(now.minusMonths(1)))
                        .collect(Collectors.toList());
            case "année":
                return tasks.stream()
                        .filter(task -> task.getDateDebut() != null &&
                                task.getDateDebut().isAfter(now.minusYears(1)))
                        .collect(Collectors.toList());
            default:

                return tasks;
        }
    }

}
