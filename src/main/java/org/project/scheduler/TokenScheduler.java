package org.project.scheduler;

import org.project.Enum.TypeRequest;
import org.project.entite.Request;
import org.project.entite.User;
import org.project.service.UserService;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TokenScheduler {
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    private final UserService userService = new UserService();

    public void startScheduler(Request request) {
        scheduler.scheduleAtFixedRate(() -> checkAndUpdateUserTokens(request), 0, 2, TimeUnit.MINUTES);
    }

    private void checkAndUpdateUserTokens(Request request) {
        try {
            if (request.getStatus() == TypeRequest.EXPIRE) {

                User user = request.getUser();
                user.setJeton_Monsuel(user.getJeton_Monsuel() * 2);
                userService.updateUser(user);

                System.out.println("Jeton de l'utilisateur mis à jour avec succès.");
            }

        } catch (Exception e) {
            System.err.println("Erreur lors de la mise à jour des jetons de l'utilisateur : " + e.getMessage());
            e.printStackTrace();
        }
    }
}
