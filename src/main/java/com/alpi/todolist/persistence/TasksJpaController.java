package com.alpi.todolist.persistence;

import com.alpi.todolist.logic.Tasks;
import com.alpi.todolist.persistence.exceptions.NonexistentEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;


public class TasksJpaController implements Serializable {

    public TasksJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;
    
    public TasksJpaController() {
        emf = Persistence.createEntityManagerFactory("TodoListPU");
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Tasks tasks) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(tasks);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Tasks tasks) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            tasks = em.merge(tasks);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = tasks.getId();
                if (findTasks(id) == null) {
                    throw new NonexistentEntityException("The tasks with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(int id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tasks tasks;
            try {
                tasks = em.getReference(Tasks.class, id);
                tasks.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tasks with id " + id + " no longer exists.", enfe);
            }
            em.remove(tasks);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Tasks> findTasksEntities() {
        return findTasksEntities(true, -1, -1);
    }

    public List<Tasks> findTasksEntities(int maxResults, int firstResult) {
        return findTasksEntities(false, maxResults, firstResult);
    }

    private List<Tasks> findTasksEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Tasks.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Tasks findTasks(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Tasks.class, id);
        } finally {
            em.close();
        }
    }

    public int getTasksCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Tasks> rt = cq.from(Tasks.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
