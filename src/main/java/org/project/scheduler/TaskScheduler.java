package org.project.scheduler;


import org.project.Enum.TypeStatus;
import org.project.entite.Task;
import org.project.service.TaskService;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TaskScheduler {
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    private final TaskService taskService = new TaskService();

    public void startScheduler() {
        scheduler.scheduleAtFixedRate(this::checkAndUpdateTasks, 0, 1, TimeUnit.MINUTES);
    }

    private void checkAndUpdateTasks() {
        try {
            List<Task> tasks = taskService.getALlTasks();
            for (Task task : tasks) {
                if (task.getDateFin().isBefore(LocalDate.now()) && task.getStatus() != TypeStatus.termine) {
                    taskService.updateTaskStatus(task.getId(), TypeStatus.incomplete);
                }
            }
        } catch (Exception e) {
            System.err.println("Erreur lors de la vérification des tâches : " + e.getMessage());
            e.printStackTrace();
        }
    }
}
