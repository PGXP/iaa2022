/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.pgxp.migra.dao;

import br.com.pgxp.migra.dao.exceptions.NonexistentEntityException;
import br.com.pgxp.migra.entity.Exgrupo;
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
public class ExgrupoJpaController implements Serializable {

    public ExgrupoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public ExgrupoJpaController() {
        emf = createEntityManagerFactory("PU");
    }

    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Exgrupo exgrupo) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(exgrupo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Exgrupo exgrupo) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            exgrupo = em.merge(exgrupo);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = exgrupo.getId();
                if (findExgrupo(id) == null) {
                    throw new NonexistentEntityException("The exgrupo with id " + id + " no longer exists.");
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
            Exgrupo exgrupo;
            try {
                exgrupo = em.getReference(Exgrupo.class, id);
                exgrupo.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The exgrupo with id " + id + " no longer exists.", enfe);
            }
            em.remove(exgrupo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Exgrupo> findExgrupoEntities() {
        return findExgrupoEntities(true, -1, -1);
    }

    public List<Exgrupo> findExgrupoEntities(int maxResults, int firstResult) {
        return findExgrupoEntities(false, maxResults, firstResult);
    }

    private List<Exgrupo> findExgrupoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Exgrupo.class));
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

    public List<Exgrupo> findTabelasMesmoDia(Integer ano, Integer mes, Integer dia, String mercado, String produto) {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery("SELECT c FROM Exgrupo c where c.dia = :dia and c.mes = :mes and c.ano = :ano and c.mercado like :mercado and c.produto = :produto and c.valor is not null order by c.ano, c.mes, c.dia", Exgrupo.class)
                    .setParameter("dia", dia)
                    .setParameter("mes", mes)
                    .setParameter("ano", ano)
                    .setParameter("mercado", mercado + "%")
                    .setParameter("produto", produto)
                    .setMaxResults(1)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public List<Exgrupo> findTabelasMesmoMes(Integer ano, Integer mes, Integer dia, String mercado, String produto) {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery("SELECT c FROM Exgrupo c where c.dia >= :dia and c.mes = :mes and c.ano = :ano and c.mercado like :mercado and c.produto = :produto and c.valor is not null order by c.ano, c.mes, c.dia", Exgrupo.class)
                    .setParameter("dia", dia)
                    .setParameter("mes", mes)
                    .setParameter("ano", ano)
                    .setParameter("mercado", mercado + "%")
                    .setParameter("produto", produto)
                    .setMaxResults(1)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public List<Exgrupo> findTabelasMesmoMes(Integer ano, Integer mes, String mercado, String produto) {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery("SELECT c FROM Exgrupo c where c.mes = :mes and c.ano = :ano and c.mercado like :mercado and c.produto = :produto and c.valor is not null order by c.ano, c.mes, c.dia desc", Exgrupo.class)
                    .setParameter("mes", mes)
                    .setParameter("ano", ano)
                    .setParameter("mercado", mercado + "%")
                    .setParameter("produto", produto)
                    .setMaxResults(1)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public List<Exgrupo> findTabelasMesmoAno(Integer ano, Integer mes, String mercado, String produto) {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery("SELECT c FROM Exgrupo c where c.mes >= :mes and c.ano = :ano and c.mercado like :mercado and c.produto = :produto and c.valor is not null order by c.ano, c.mes, c.dia", Exgrupo.class)
                    .setParameter("mes", mes)
                    .setParameter("ano", ano)
                    .setParameter("mercado", mercado + "%")
                    .setParameter("produto", produto)
                    .setMaxResults(1)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public Exgrupo findExgrupo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Exgrupo.class, id);
        } finally {
            em.close();
        }
    }

    public int getExgrupoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Exgrupo> rt = cq.from(Exgrupo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
