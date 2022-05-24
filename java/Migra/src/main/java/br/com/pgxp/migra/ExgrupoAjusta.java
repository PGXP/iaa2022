/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.pgxp.migra;

import br.com.pgxp.migra.dao.ExgrupoJpaController;
import br.com.pgxp.migra.dao.LocalsJpaController;
import br.com.pgxp.migra.dao.ProdutosJpaController;
import br.com.pgxp.migra.entity.Exgrupo;
import br.com.pgxp.migra.entity.Locals;
import br.com.pgxp.migra.entity.Produtos;
import br.com.pgxp.migra.runner.AjustaRunner;
import br.com.pgxp.migra.runner.ExgrupoRunner;
import static java.lang.Runtime.getRuntime;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import static java.time.Duration.between;
import java.time.Instant;
import static java.time.Instant.now;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.ExecutorService;
import static java.util.concurrent.Executors.newFixedThreadPool;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import static java.util.logging.Level.INFO;
import java.util.logging.Logger;
import static java.util.logging.Logger.getLogger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author desktop
 */
public class ExgrupoAjusta {

    private static final Logger LOG = getLogger(ExgrupoAjusta.class.getName());
    private static final int MAX_THREADS = getRuntime().availableProcessors();

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        try {

            LOG.log(INFO, "ExgrupoMigra init with {0} processors", MAX_THREADS);
            Instant start = now();
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("br.com.pgxp_Migra_jar_1.0.0PU");
            ExecutorService executorGerador = newFixedThreadPool(MAX_THREADS);

            ExgrupoJpaController edao = new ExgrupoJpaController(emf);

            for (int i = 1; i <= edao.getExgrupoCount(); i++) {

                AjustaRunner ir = new AjustaRunner();
                ir.setEmf(emf);
                ir.setEdao(edao);
                ir.setId(i);
                executorGerador.execute(ir);

            }

            executorGerador.shutdown();

            try {
                executorGerador.awaitTermination(720, TimeUnit.HOURS);
            } catch (InterruptedException ie) {
                LOG.severe(ie.getLocalizedMessage());
            }

//        emf.close();
            Instant finish = now();
            LOG.log(INFO, "ExgrupoMigra {0} seg", new Object[]{between(start, finish).getSeconds()});

        } catch (Exception ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
