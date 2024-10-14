package org.project.scheduler;

import com.sun.source.tree.BreakTree;
import org.project.Enum.TypeRequest;
import org.project.entite.Request;
import org.project.entite.User;
import org.project.service.RequestService;
import org.project.service.UserService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class RequestScheduler {
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    private final RequestService requestService = new RequestService();
    private final UserService userService = new UserService();
    private final  TokenScheduler tokenScheduler = new TokenScheduler();


    public void startScheduler() {
        scheduler.scheduleAtFixedRate(this::checkAndUpdateRequest, 0, 1, TimeUnit.MINUTES);
    }
    public boolean isRequestOlderThan12Hours(Request request){
        LocalDateTime created_at = request.getCreated_at().atStartOfDay();
        LocalDateTime now = LocalDateTime.now();
        long hoursDifference = ChronoUnit.HOURS.between(created_at, now);
        return hoursDifference > 12;

    }

    private void checkAndUpdateRequest() {
        try {
            List<Request> requests = requestService.getALlRequests();
            for (Request request : requests) {
                if (isRequestOlderThan12Hours(request) && request.getStatus() == TypeRequest.EN_ATTENT) {
                    request.setStatus(TypeRequest.EXPIRE);
                    requestService.update(request);
                    tokenScheduler.startScheduler(request);

                }
            }
        } catch (Exception e) {
            System.err.println("Erreur lors de la vérification des requests : " + e.getMessage());
            e.printStackTrace();
        }
    }
}
