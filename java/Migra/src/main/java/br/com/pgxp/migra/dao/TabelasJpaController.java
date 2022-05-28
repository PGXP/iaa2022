/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.pgxp.migra.dao;

import br.com.pgxp.migra.dao.exceptions.NonexistentEntityException;
import br.com.pgxp.migra.entity.Tabelas;
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
public class TabelasJpaController implements Serializable {

    public TabelasJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public TabelasJpaController() {
        emf = createEntityManagerFactory("PU");
    }

    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Tabelas tabelas) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(tabelas);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Tabelas tabelas) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            tabelas = em.merge(tabelas);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tabelas.getId();
                if (findTabelas(id) == null) {
                    throw new NonexistentEntityException("The tabelas with id " + id + " no longer exists.");
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
            Tabelas tabelas;
            try {
                tabelas = em.getReference(Tabelas.class, id);
                tabelas.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tabelas with id " + id + " no longer exists.", enfe);
            }
            em.remove(tabelas);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Tabelas> findTabelasEntities() {
        return findTabelasEntities(true, -1, -1);
    }

    public List<Tabelas> findTabelasEntities(int maxResults, int firstResult) {
        return findTabelasEntities(false, maxResults, firstResult);
    }

    private List<Tabelas> findTabelasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Tabelas.class));
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

    public Tabelas findTabelas(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Tabelas.class, id);
        } finally {
            em.close();
        }
    }

    public List<Tabelas> findTabelas(Integer ano, Integer mes, Integer dia) {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery("SELECT c FROM Tabelas c where c.dia = :dia and c.mes = :mes and c.ano = :ano", Tabelas.class)
                    .setParameter("dia", dia)
                    .setParameter("mes", mes)
                    .setParameter("ano", ano)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public List<Tabelas> findTabelas(Integer ano, Integer mes, Integer dia, Integer idbairro, Integer idfilial, Integer idlocal, Integer idproduto) {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery("SELECT c FROM Tabelas c where c.dia = :dia and c.mes = :mes and c.ano = :ano and c.idbairro = :idbairro and c.idfilial = :idfilial and c.idlocal = :idlocal and c.idproduto = :idproduto", Tabelas.class)
                    .setParameter("dia", dia)
                    .setParameter("mes", mes)
                    .setParameter("ano", ano)
                    .setParameter("idbairro", idbairro)
                    .setParameter("idfilial", idfilial)
                    .setParameter("idlocal", idlocal)
                    .setParameter("idproduto", idproduto)
                    .setMaxResults(1)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public List<Tabelas> findTabelasLocal(Integer ano, Integer diaano, Integer qtde, Integer idbairro, Integer idfilial, Integer idlocal, Integer idproduto) {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery("SELECT c FROM Tabelas c where c.diaano >= :dia and c.diaano <= :qtde and c.ano = :ano and c.idbairro = :idbairro and c.idfilial = :idfilial and c.idlocal = :idlocal and c.idproduto = :idproduto", Tabelas.class)
                    .setParameter("dia", diaano)
                    .setParameter("qtde", diaano + qtde)
                    .setParameter("ano", ano)
                    .setParameter("idbairro", idbairro)
                    .setParameter("idfilial", idfilial)
                    .setParameter("idlocal", idlocal)
                    .setParameter("idproduto", idproduto)
                    .setMaxResults(1)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public List<Tabelas> findTabelasLocalAno(Integer ano, Integer idbairro, Integer idfilial, Integer idlocal, Integer idproduto) {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery("SELECT c FROM Tabelas c where c.ano = :ano and c.idbairro = :idbairro and c.idfilial = :idfilial and c.idlocal = :idlocal and c.idproduto = :idproduto", Tabelas.class)
                    .setParameter("ano", ano)
                    .setParameter("idbairro", idbairro)
                    .setParameter("idfilial", idfilial)
                    .setParameter("idlocal", idlocal)
                    .setParameter("idproduto", idproduto)
                    .setMaxResults(1)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public List<Tabelas> findTabelas(Integer ano, Integer semana, Integer idbairro, Integer idfilial, Integer idlocal, Integer idproduto) {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery("SELECT c FROM Tabelas c where c.semanaano = :semana and c.ano = :ano and c.idbairro = :idbairro and c.idfilial = :idfilial and c.idlocal = :idlocal and c.idproduto = :idproduto", Tabelas.class)
                    .setParameter("semana", semana)
                    .setParameter("ano", ano)
                    .setParameter("idbairro", idbairro)
                    .setParameter("idfilial", idfilial)
                    .setParameter("idlocal", idlocal)
                    .setParameter("idproduto", idproduto)
                    .setMaxResults(1)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public List<Tabelas> findTabelasFilial(Integer ano, Integer mes, Integer dia, Integer idlocal, Integer idproduto) {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery("SELECT c FROM Tabelas c where c.dia = :dia and c.mes = :mes and c.ano = :ano and c.idlocal = :idlocal and c.idproduto = :idproduto order by c.ano, c.mes, c.dia", Tabelas.class)
                    .setParameter("dia", dia)
                    .setParameter("mes", mes)
                    .setParameter("ano", ano)
                    .setParameter("idlocal", idlocal)
                    .setParameter("idproduto", idproduto)
                    .setMaxResults(1)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public List<Tabelas> findTabelasFilialQt(Integer ano, Integer diaano, Integer qtde, Integer idlocal, Integer idproduto) {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery("SELECT c FROM Tabelas c where c.diaano >= :dia and c.diaano <= :qtde and c.ano = :ano and c.idlocal = :idlocal and c.idproduto = :idproduto order by c.ano, c.mes, c.dia", Tabelas.class)
                    .setParameter("dia", diaano)
                    .setParameter("qtde", diaano + qtde)
                    .setParameter("ano", ano)
                    .setParameter("idlocal", idlocal)
                    .setParameter("idproduto", idproduto)
                    .setMaxResults(1)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public List<Tabelas> findTabelasFilialAno(Integer ano, Integer idlocal, Integer idproduto) {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery("SELECT c FROM Tabelas c where c.ano = :ano and c.idlocal = :idlocal and c.idproduto = :idproduto order by c.ano, c.mes, c.dia", Tabelas.class)
                    .setParameter("ano", ano)
                    .setParameter("idlocal", idlocal)
                    .setParameter("idproduto", idproduto)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public List<Tabelas> findTabelasFilial(Integer ano, Integer semana, Integer idlocal, Integer idproduto) {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery("SELECT c FROM Tabelas c where c.semanaano = :semana and c.ano = :ano and c.idlocal = :idlocal and c.idproduto = :idproduto order by c.ano, c.mes, c.dia", Tabelas.class)
                    .setParameter("semana", semana)
                    .setParameter("ano", ano)
                    .setParameter("idlocal", idlocal)
                    .setParameter("idproduto", idproduto)
                    .setMaxResults(1)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public List<Tabelas> findTabelasBairro(Integer ano, Integer mes, Integer dia, Integer idbairro, Integer idproduto) {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery("SELECT c FROM Tabelas c where c.dia = :dia and c.mes = :mes and c.ano = :ano and c.idbairro = :idbairro and c.idproduto = :idproduto order by c.ano, c.mes, c.dia", Tabelas.class)
                    .setParameter("dia", dia)
                    .setParameter("mes", mes)
                    .setParameter("ano", ano)
                    .setParameter("idbairro", idbairro)
                    .setParameter("idproduto", idproduto)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public List<Tabelas> findTabelasBairro(Integer ano, Integer semana, Integer idbairro, Integer idproduto) {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery("SELECT c FROM Tabelas c where c.semanaano = :semana and c.ano = :ano and c.idbairro = :idbairro and c.idproduto = :idproduto order by c.ano, c.mes, c.dia", Tabelas.class)
                    .setParameter("semana", semana)
                    .setParameter("ano", ano)
                    .setParameter("idbairro", idbairro)
                    .setParameter("idproduto", idproduto)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public List<Tabelas> findTabelasBairroQt(Integer ano, Integer diaano, Integer qtde, Integer idbairro, Integer idproduto) {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery("SELECT c FROM Tabelas c where c.diaano >= :dia and c.diaano <= :qtde and c.ano = :ano and c.idbairro = :idbairro and c.idproduto = :idproduto order by c.ano, c.mes, c.dia", Tabelas.class)
                    .setParameter("dia", diaano)
                    .setParameter("qtde", diaano + qtde)
                    .setParameter("ano", ano)
                    .setParameter("idbairro", idbairro)
                    .setParameter("idproduto", idproduto)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public List<Tabelas> findTabelasBairroAno(Integer ano, Integer idbairro, Integer idproduto) {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery("SELECT c FROM Tabelas c where c.ano = :ano and c.idbairro = :idbairro and c.idproduto = :idproduto order by c.ano, c.mes, c.dia", Tabelas.class)
                    .setParameter("ano", ano)
                    .setParameter("idbairro", idbairro)
                    .setParameter("idproduto", idproduto)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public List<Tabelas> findTabelasCidade(Integer ano, Integer mes, Integer dia, Integer idproduto) {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery("SELECT c FROM Tabelas c where c.dia = :dia and c.mes = :mes and c.ano = :ano and c.idproduto = :idproduto order by c.ano, c.mes, c.dia", Tabelas.class)
                    .setParameter("dia", dia)
                    .setParameter("mes", mes)
                    .setParameter("ano", ano)
                    .setParameter("idproduto", idproduto)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public List<Tabelas> findTabelasCidade(Integer ano, Integer semana, Integer idproduto) {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery("SELECT c FROM Tabelas c where c.semanaano = :semana and c.ano = :ano and c.idproduto = :idproduto order by c.ano, c.mes, c.dia", Tabelas.class)
                    .setParameter("semana", semana)
                    .setParameter("ano", ano)
                    .setParameter("idproduto", idproduto)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public List<Tabelas> findTabelasCidadeQt(Integer ano, Integer diaano, Integer qtde, Integer idproduto) {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery("SELECT c FROM Tabelas c where c.diaano >= :dia and c.diaano <= :qtde and c.ano = :ano and c.idproduto = :idproduto order by c.ano, c.mes, c.dia", Tabelas.class)
                    .setParameter("dia", diaano)
                    .setParameter("qtde", diaano + qtde)
                    .setParameter("ano", ano)
                    .setParameter("idproduto", idproduto)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public List<Tabelas> findTabelasCidadeAno(Integer ano, Integer idproduto) {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery("SELECT c FROM Tabelas c where c.ano = :ano and c.idproduto = :idproduto order by c.ano, c.mes, c.dia", Tabelas.class)
                    .setParameter("ano", ano)
                    .setParameter("idproduto", idproduto)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public List<Tabelas> findTabelas(Integer ano, Integer mes) {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery("SELECT c FROM Tabelas c where c.mes = :mes and c.ano = :ano", Tabelas.class)
                    .setParameter("mes", mes)
                    .setParameter("ano", ano)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public int getTabelasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Tabelas> rt = cq.from(Tabelas.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
