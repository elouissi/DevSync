package org.project.repositorie;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.project.entite.Task;

import java.util.List;

public class TaskRepository {
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("myPersistenceUnit");

    public void createTask(Task task){
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(task);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.out.println("Erreur lors de la creation du task");
        }finally {
            em.close();
        }
    }
    public Task getTaskById(int id){
        EntityManager em = emf.createEntityManager();
        Task task = em.find(Task.class,id);
        em.close();
        return task;
    }
    public List<Task> getAllTasks(){
        EntityManager em = emf.createEntityManager();
        List<Task> tasks = em.createQuery("SELECT t FROM Task t", Task.class).getResultList();
        em.close();
        return tasks;
    }
    public void updateTask(Task task){
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(task);
        em.getTransaction().commit();
        em.close();
    }
    public void deleteTask(int id){
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Task task = em.find(Task.class,id);
        if (task!= null){
            em.remove(task);
        }
        em.getTransaction().commit();
        em.close();


    }
}
