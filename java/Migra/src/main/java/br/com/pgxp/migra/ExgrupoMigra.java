/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.pgxp.migra;

import br.com.pgxp.migra.dao.LocalsJpaController;
import br.com.pgxp.migra.dao.ProdutosJpaController;
import br.com.pgxp.migra.entity.Locals;
import br.com.pgxp.migra.entity.Produtos;
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
public class ExgrupoMigra {

    private static final Logger LOG = getLogger(ExgrupoMigra.class.getName());
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

            ProdutosJpaController pdao = new ProdutosJpaController(emf);
            LocalsJpaController ldao = new LocalsJpaController(emf);

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date startDate = formatter.parse("2010-05-01");
            Date endDate = formatter.parse("2020-03-30");

            Calendar inicio = Calendar.getInstance();
            inicio.setTime(startDate);
            Calendar fim = Calendar.getInstance();
            fim.setTime(endDate);

            for (Date date = inicio.getTime(); inicio.before(fim); inicio.add(Calendar.DATE, 1), date = inicio.getTime()) {

                Calendar cal = new GregorianCalendar();
                cal.setTime(date); // Give your own date

                for (Locals findLocalsEntity : ldao.findAll()) {
                    for (Produtos findProdutosEntity : pdao.findProdutosEntitiesByGrupo(15)) {

                        ExgrupoRunner ir = new ExgrupoRunner();
                        ir.setDate(date);
                        ir.setLocals(findLocalsEntity);
                        ir.setProdutos(findProdutosEntity);
                        ir.setEmf(emf);
                        executorGerador.execute(ir);

                    }
                }
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

        } catch (ParseException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
