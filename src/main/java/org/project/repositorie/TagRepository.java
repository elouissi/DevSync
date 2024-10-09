package org.project.repositorie;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.project.entite.Tag;
import org.project.entite.Task;

import java.util.List;

public class TagRepository {

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("myPersistenceUnit");

    public void createTag(Tag tag){
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(tag);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.out.println("Erreur lors de la creation du tag");
        }finally {
            em.close();
        }
    }
    public List<Tag> getAllTags(){
        EntityManager em = emf.createEntityManager();
        List<Tag> tags = em.createQuery("SELECT t FROM Tag t", Tag.class).getResultList();
        em.close();
        return tags;
    }
    public Tag getTagById(int id){
        EntityManager em = emf.createEntityManager();
        Tag tag = em.find(Tag.class,id);
        em.close();
        return tag;
    }

    }
