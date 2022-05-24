/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.pgxp.migra.runner;

import br.com.pgxp.migra.dao.ExgrupoJpaController;
import br.com.pgxp.migra.entity.Exgrupo;
import static java.time.Duration.between;
import java.time.Instant;
import static java.time.Instant.now;
import java.util.List;
import java.util.logging.Level;
import static java.util.logging.Level.INFO;
import java.util.logging.Logger;
import static java.util.logging.Logger.getLogger;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Serpro
 */
public class AjustaRunner implements Runnable {

    private static final Logger LOG = getLogger(AjustaRunner.class.getName());

    private EntityManagerFactory emf;
    private Integer id;

    /**
     *
     */
    @Override
    public void run() {
        try {

            Instant start = now();

            ExgrupoJpaController edao = new ExgrupoJpaController(emf);

            Exgrupo exgrupo = edao.findExgrupo(id);

            if (exgrupo.getValor() == null) {

                String mercado = exgrupo.getMercado().split(" ")[0];

                if (!mercado.isEmpty()) {

                    List<Exgrupo> lst = edao.findTabelasMesmoDia(exgrupo.getAno(), exgrupo.getMes(), exgrupo.getDia(), mercado, exgrupo.getProduto());

                    if (lst.isEmpty()) {
                        lst = edao.findTabelasMesmoMes(exgrupo.getAno(), exgrupo.getMes(), exgrupo.getDia(), mercado, exgrupo.getProduto());
                    }

                    if (lst.isEmpty()) {
                        lst = edao.findTabelasMesmoMes(exgrupo.getAno(), exgrupo.getMes(), mercado, exgrupo.getProduto());
                    }

                    if (lst.isEmpty()) {
                        lst = edao.findTabelasMesmoAno(exgrupo.getAno(), exgrupo.getMes(), mercado, exgrupo.getProduto());
                    }

                    if (!lst.isEmpty()) {
                        exgrupo.setValor(lst.get(0).getValor());
                        edao.edit(exgrupo);
                        LOG.info(exgrupo.toString());
                        Instant finish = now();
                        LOG.log(INFO, "Ajusta {0} seg", new Object[]{between(start, finish).getNano()});
                    }
                }

            }

            //LOG.log(Level.INFO, "Work {0} -> {1} -> {2}", new Object[]{estacao.getCodigo().toString(), lista.size(), between(start, finish).getNano()});
        } catch (Exception ex) {
            Logger.getLogger(AjustaRunner.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public EntityManagerFactory getEmf() {
        return emf;
    }

    public void setEmf(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
