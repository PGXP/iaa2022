/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.pgxp.migra.dao;

import br.com.pgxp.migra.dao.exceptions.NonexistentEntityException;
import br.com.pgxp.migra.dao.exceptions.PreexistingEntityException;
import br.com.pgxp.migra.entity.Bairros;
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
public class BairrosJpaController implements Serializable {

    public BairrosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public BairrosJpaController() {
        emf = createEntityManagerFactory("PU");
    }

    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Bairros bairros) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(bairros);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findBairros(bairros.getId()) != null) {
                throw new PreexistingEntityException("Bairros " + bairros + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Bairros bairros) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            bairros = em.merge(bairros);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = bairros.getId();
                if (findBairros(id) == null) {
                    throw new NonexistentEntityException("The bairros with id " + id + " no longer exists.");
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
            Bairros bairros;
            try {
                bairros = em.getReference(Bairros.class, id);
                bairros.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The bairros with id " + id + " no longer exists.", enfe);
            }
            em.remove(bairros);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Bairros> findBairrosEntities() {
        return findBairrosEntities(true, -1, -1);
    }

    public List<Bairros> findBairrosEntities(int maxResults, int firstResult) {
        return findBairrosEntities(false, maxResults, firstResult);
    }

    private List<Bairros> findBairrosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Bairros.class));
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

    public Bairros findBairros(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Bairros.class, id);
        } finally {
            em.close();
        }
    }

    public int getBairrosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Bairros> rt = cq.from(Bairros.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
