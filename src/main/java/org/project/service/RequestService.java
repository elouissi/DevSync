package org.project.service;

import org.project.entite.Request;
import org.project.repositorie.RequestRepository;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class RequestService  {
    private RequestRepository requestRepository;
    public RequestService(){
        requestRepository = new RequestRepository();
    }



    public List<Request> getALlRequests(){
        return requestRepository.getAllRequests();
    }
    public Request getById(int id){
        return requestRepository.getRequestById(id);
    }
    public void createRequest(Request request) {
        try {
            requestRepository.createRequest(request);
        } catch (Exception e) {
            System.out.println("Erreur lors de la création de la demande : " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void update(Request request){
        requestRepository.updateRequest(request);
    }
    public boolean hasExistingRequest(int taskId, int userId) {
        return getALlRequests()
                .stream()
                .anyMatch(request ->
                        request.getTask().getId() == taskId &&
                                request.getUser().getId() == userId
                );
    }
}
