package org.project.scheduler;

import org.project.Enum.TypeStatus;
import org.project.entite.Task;
import org.project.entite.User;
import org.project.service.TaskService;
import org.project.service.UserService;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class UserScheduler {
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    private final UserService userService = new UserService();

    public void startScheduler() {
        scheduler.scheduleAtFixedRate(this::checkAndUpdateUsers, 0, 12, TimeUnit.HOURS);
    }

    private void checkAndUpdateUsers() {
        try {
            List<User> users = userService.getAllUsers();
            for (User user : users) {
                if (user.getJeton_Monsuel() < 2 ) {
                    while (user.getJeton_Monsuel() <2){
                        user.setJeton_Monsuel(user.getJeton_Monsuel() +1 );
                        userService.updateUser(user);

                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Erreur lors de la vérification des users : " + e.getMessage());
            e.printStackTrace();
        }
    }
}
