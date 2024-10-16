package org.project.scheduler;

import org.project.entite.User;
import org.project.service.UserService;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class UserMontlyScheduler {
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    private final UserService userService = new UserService();

    public void startScheduler() {
        scheduler.scheduleAtFixedRate(this::checkAndUpdateUsers, 0, 30, TimeUnit.DAYS);
    }

    private void checkAndUpdateUsers() {
        try {
            List<User> users = userService.getAllUsers();
            for (User user : users) {
                if(user.getJeton_Annuel()!=1){
                    user.setJeton_Annuel(1);
                    userService.updateUser(user);
                }
            }
        } catch (Exception e) {
            System.err.println("Erreur lors de la vérification des users : " + e.getMessage());
            e.printStackTrace();
        }
    }
}
