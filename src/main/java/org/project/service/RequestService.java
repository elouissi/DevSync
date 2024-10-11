package org.project.service;

import org.project.entite.Request;
import org.project.repositorie.RequestRepository;

import java.util.List;

public class RequestService {
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
    public void createRequest(Request request){
        requestRepository.createRequest(request) ;
    }
    public void update(Request request){
        requestRepository.updateRequest(request);
    }
    public boolean hasExistingRequest(int taskId, int userId) {
        return getALlRequests()
                .stream()
                .anyMatch(request ->
                        request.getTask_id().getId() == taskId &&
                                request.getUser_id().getId() == userId
                );
    }
}
