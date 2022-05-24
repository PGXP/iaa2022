/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.pgxp.migra.dao;

import br.com.pgxp.migra.dao.exceptions.NonexistentEntityException;
import br.com.pgxp.migra.entity.Dolar;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author desktop
 */
public class DolarJpaController implements Serializable {

    public DolarJpaController() {

    }

    public EntityManager getEntityManager() {
        return Persistence.createEntityManagerFactory("br.com.pgxp_Migra_jar_1.0.0PU").createEntityManager();
    }

    public void create(Dolar dolar) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(dolar);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Dolar dolar) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            dolar = em.merge(dolar);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = dolar.getId();
                if (findDolar(id) == null) {
                    throw new NonexistentEntityException("The dolar with id " + id + " no longer exists.");
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
            Dolar dolar;
            try {
                dolar = em.getReference(Dolar.class, id);
                dolar.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The dolar with id " + id + " no longer exists.", enfe);
            }
            em.remove(dolar);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Dolar> findDolarEntities() {
        return findDolarEntities(true, -1, -1);
    }

    public List<Dolar> findDolarEntities(int maxResults, int firstResult) {
        return findDolarEntities(false, maxResults, firstResult);
    }

    private List<Dolar> findDolarEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Dolar.class));
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

    public Dolar findDolar(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Dolar.class, id);
        } finally {
            em.close();
        }
    }

    public List<Dolar> findAll() {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery("SELECT c FROM Dolar c order by c.data", Dolar.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public int getDolarCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Dolar> rt = cq.from(Dolar.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
