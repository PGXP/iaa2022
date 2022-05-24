/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.pgxp.migra.dao;

import br.com.pgxp.migra.dao.exceptions.NonexistentEntityException;
import br.com.pgxp.migra.entity.Ipca;
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
public class IpcaJpaController implements Serializable {

    public IpcaJpaController() {

    }

    public EntityManager getEntityManager() {
        return Persistence.createEntityManagerFactory("br.com.pgxp_Migra_jar_1.0.0PU").createEntityManager();
    }

    public void create(Ipca ipca) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(ipca);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Ipca ipca) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ipca = em.merge(ipca);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = ipca.getId();
                if (findIpca(id) == null) {
                    throw new NonexistentEntityException("The ipca with id " + id + " no longer exists.");
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
            Ipca ipca;
            try {
                ipca = em.getReference(Ipca.class, id);
                ipca.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ipca with id " + id + " no longer exists.", enfe);
            }
            em.remove(ipca);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Ipca> findIpcaEntities() {
        return findIpcaEntities(true, -1, -1);
    }

    public List<Ipca> findIpcaEntities(int maxResults, int firstResult) {
        return findIpcaEntities(false, maxResults, firstResult);
    }

    private List<Ipca> findIpcaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Ipca.class));
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

    public Ipca findIpca(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Ipca.class, id);
        } finally {
            em.close();
        }
    }

    public int getIpcaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Ipca> rt = cq.from(Ipca.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
