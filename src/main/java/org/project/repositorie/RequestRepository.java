package org.project.repositorie;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.project.entite.Request;

import java.util.List;

public class RequestRepository {
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("myPersistenceUnit");

    public void createRequest(Request request){
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(request);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.out.println("Erreur lors de la creation du request");
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
