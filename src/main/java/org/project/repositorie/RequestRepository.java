package org.project.repositorie;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.project.entite.Request;
import org.project.entite.Task;

import java.util.List;

public class RequestRepository {
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("myPersistenceUnit");

    public void createRequest(Request request){
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();

            Task managedTask = em.merge(request.getTask());
            request.setTask(managedTask);

            System.out.println("Starting to create request: " + request);
            em.persist(request);
            System.out.println("Request persisted, about to commit");
            em.getTransaction().commit();
            System.out.println("Transaction committed successfully");
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.out.println("Erreur lors de la creation du request");
            e.printStackTrace();
        }finally {
            em.close();
        }
    }
    public List<Request> getAllRequests(){
        EntityManager em = emf.createEntityManager();
        List<Request> requests = em.createQuery("SELECT t FROM Request t", Request.class).getResultList();
        em.close();
        return requests;
    }
    public Request getRequestById(int id){
        EntityManager em = emf.createEntityManager();
        Request request = em.find(Request.class,id);
        em.close();
        return request;
    }
    public void updateRequest(Request request){
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(request);
        em.getTransaction().commit();
        em.close();
    }

}
