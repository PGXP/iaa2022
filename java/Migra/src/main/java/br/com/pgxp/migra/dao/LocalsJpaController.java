/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.pgxp.migra.dao;

import br.com.pgxp.migra.dao.exceptions.NonexistentEntityException;
import br.com.pgxp.migra.entity.Locals;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import static javax.persistence.Persistence.createEntityManagerFactory;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author desktop
 */
public class LocalsJpaController implements Serializable {

    public LocalsJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public LocalsJpaController() {
        emf = createEntityManagerFactory("PU");
    }

    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Locals locals) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(locals);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Locals locals) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            locals = em.merge(locals);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = locals.getId();
                if (findLocals(id) == null) {
                    throw new NonexistentEntityException("The locals with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Locals locals;
            try {
                locals = em.getReference(Locals.class, id);
                locals.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The locals with id " + id + " no longer exists.", enfe);
            }
            em.remove(locals);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    
    public List<Locals> findAll() {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery("SELECT p FROM Locals p ORDER BY p.nome", Locals.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public List<Locals> findLocalsEntities() {
        return findLocalsEntities(true, -1, -1);
    }

    public List<Locals> findLocalsEntities(int maxResults, int firstResult) {
        return findLocalsEntities(false, maxResults, firstResult);
    }

    private List<Locals> findLocalsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Locals.class));
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

    public Locals findLocals(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Locals.class, id);
        } finally {
            em.close();
        }
    }

    public int getLocalsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Locals> rt = cq.from(Locals.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
