/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.pgxp.migra;

import br.com.pgxp.migra.dao.Produto270JpaController;
import br.com.pgxp.migra.dao.LocalsJpaController;
import br.com.pgxp.migra.dao.ProdutosJpaController;
import br.com.pgxp.migra.entity.Produto270;
import br.com.pgxp.migra.entity.Locals;
import br.com.pgxp.migra.entity.Produtos;
import br.com.pgxp.migra.runner.Produto270RunnerCreate;
import br.com.pgxp.migra.runner.Produto270RunnerUpdate;
import static java.lang.Runtime.getRuntime;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import static java.time.Duration.between;
import java.time.Instant;
import static java.time.Instant.now;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
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
public class Produto270Migra {

    private static final Logger LOG = getLogger(Produto270Migra.class.getName());
    private static final int MAX_THREADS = getRuntime().availableProcessors();

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        try {

            LOG.log(INFO, "Produto270Migra init with {0} processors", MAX_THREADS);
            Instant start = now();
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("br.com.pgxp_Migra_jar_1.0.0PU");

            ProdutosJpaController pdao = new ProdutosJpaController(emf);
            LocalsJpaController ldao = new LocalsJpaController(emf);
            Produto270JpaController edao = new Produto270JpaController(emf);

//            for (Produtos produtos : pdao.findProdutos(262)) {
            Produtos produtos = pdao.findProdutos(270);
            for (Locals locals : ldao.findAll()) {

                ExecutorService executorGerador = newFixedThreadPool(MAX_THREADS);

                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                Date startDate = formatter.parse("2010-05-01");
                Date endDate = formatter.parse("2020-05-01");

                Calendar inicio = Calendar.getInstance();
                inicio.setTime(startDate);
                Calendar fim = Calendar.getInstance();
                fim.setTime(endDate);

                for (Date date = inicio.getTime(); inicio.before(fim); inicio.add(Calendar.DATE, 1), date = inicio.getTime()) {

                    Calendar cal = new GregorianCalendar();
                    cal.setTime(date);
                    List<Produto270> grps = edao.findTabelasValida(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DAY_OF_MONTH), locals.getNome(), produtos.getNome());

                    if (grps.isEmpty()) {
                        Produto270RunnerCreate ir = new Produto270RunnerCreate();
                        ir.setDate(date);
                        ir.setLocals(locals);
                        ir.setProdutos(produtos);
                        ir.setEmf(emf);
                        ir.setTable(new Produto270());
                        executorGerador.execute(ir);
                    } else {
//                        if (grps.get(0).getValor() == null) {
                            Produto270RunnerUpdate ir = new Produto270RunnerUpdate();
                            ir.setDate(date);
                            ir.setLocals(locals);
                            ir.setProdutos(produtos);
                            ir.setEmf(emf);
                            ir.setTable(grps.get(0));
                            executorGerador.execute(ir);
//                        }
                    }
                }

                executorGerador.shutdown();

                try {
                    executorGerador.awaitTermination(720, TimeUnit.MINUTES);
                } catch (InterruptedException ie) {
                    LOG.severe(ie.getLocalizedMessage());
                }

                System.gc();
                Instant finish = now();
                LOG.log(INFO, "Produto270Migra {0} seg {1} -> {2}", new Object[]{between(start, finish).getSeconds(), produtos.getNome(), locals.getNome()});
            }

            Instant finish = now();
            LOG.log(INFO, "Produto270Migra Final {0} seg", new Object[]{between(start, finish).getSeconds()});

//        emf.close();
        } catch (ParseException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
