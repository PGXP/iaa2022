/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.pgxp.migra;

import br.com.pgxp.migra.dao.Grupo15JpaController;
import br.com.pgxp.migra.dao.LocalsJpaController;
import br.com.pgxp.migra.dao.ProdutosJpaController;
import br.com.pgxp.migra.dao.TabelasJpaController;
import br.com.pgxp.migra.entity.Grupo15;
import br.com.pgxp.migra.entity.Locals;
import br.com.pgxp.migra.entity.Produtos;
import br.com.pgxp.migra.entity.Tabelas;
import br.com.pgxp.migra.runner.Grupo15Runner;
import static java.lang.Runtime.getRuntime;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import static java.time.Duration.between;
import java.time.Instant;
import static java.time.Instant.now;
import java.util.ArrayList;
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
public class Grupo15MigraSingle {

    private static final Logger LOG = getLogger(Grupo15MigraSingle.class.getName());
    private static final int MAX_THREADS = getRuntime().availableProcessors();

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        try {

            LOG.log(INFO, "Grupo15MigraSigle init");
            Instant start = now();
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("br.com.pgxp_Migra_jar_1.0.0PU");

            ProdutosJpaController pdao = new ProdutosJpaController(emf);
            LocalsJpaController ldao = new LocalsJpaController(emf);
            Grupo15JpaController edao = new Grupo15JpaController(emf);
            TabelasJpaController tdao = new TabelasJpaController(emf);

            for (Produtos produtos : pdao.findProdutosEntitiesByGrupo(15)) {
                for (Locals locals : ldao.findAll()) {

                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    Date startDate = formatter.parse("2010-05-01");
                    Date endDate = formatter.parse("2020-04-30");

                    Calendar inicio = Calendar.getInstance();
                    inicio.setTime(startDate);
                    Calendar fim = Calendar.getInstance();
                    fim.setTime(endDate);

                    for (Date date = inicio.getTime(); inicio.before(fim); inicio.add(Calendar.DATE, 1), date = inicio.getTime()) {

                        Calendar cal = new GregorianCalendar();
                        cal.setTime(date);

                        if (edao.findTabelasValida(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DAY_OF_MONTH), locals.getNome(), produtos.getNome()).isEmpty()) {
                            Double valores = 0.0;
                            String status = "";
                            List<Tabelas> tabalas = new ArrayList<>();
                            Grupo15 table = new Grupo15();
                            table.setAno(cal.get(Calendar.YEAR));
                            table.setMes(cal.get(Calendar.MONTH) + 1);
                            table.setDia(cal.get(Calendar.DAY_OF_MONTH));
                            table.setDiaano(cal.get(Calendar.DAY_OF_YEAR));
                            table.setSemana(cal.get(Calendar.DAY_OF_WEEK));
                            table.setSemanaano(cal.get(Calendar.WEEK_OF_YEAR));
                            table.setMercado(locals.getNome());
                            table.setProduto(produtos.getNome());
                            if (tabalas.isEmpty()) {
                                tabalas = tdao.findTabelas(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DAY_OF_MONTH),
                                        locals.getIdbairro(), locals.getIdfilial(), locals.getIdlocal(), produtos.getIdproduto());
                                status = "COLETADO";
                            }
                            if (tabalas.isEmpty()) {
                                tabalas = tdao.findTabelas(cal.get(Calendar.YEAR), cal.get(Calendar.WEEK_OF_YEAR),
                                        locals.getIdbairro(), locals.getIdfilial(), locals.getIdlocal(), produtos.getIdproduto());
                                status = "LOCAL_SEMANAANO";
                            }
                            if (tabalas.isEmpty()) {
                                tabalas = tdao.findTabelasLocal(cal.get(Calendar.YEAR), cal.get(Calendar.DAY_OF_YEAR), 30,
                                        locals.getIdbairro(), locals.getIdfilial(), locals.getIdlocal(), produtos.getIdproduto());
                                status = "LOCAL_30";
                            }
                            if (tabalas.isEmpty()) {
                                tabalas = tdao.findTabelasLocal(cal.get(Calendar.YEAR), cal.get(Calendar.DAY_OF_YEAR), 60,
                                        locals.getIdbairro(), locals.getIdfilial(), locals.getIdlocal(), produtos.getIdproduto());
                                status = "LOCAL_60";
                            }
                            if (tabalas.isEmpty()) {
                                tabalas = tdao.findTabelasLocal(cal.get(Calendar.YEAR), cal.get(Calendar.DAY_OF_YEAR), 90,
                                        locals.getIdbairro(), locals.getIdfilial(), locals.getIdlocal(), produtos.getIdproduto());
                                status = "LOCAL_90";
                            }
                            if (tabalas.isEmpty()) {
                                tabalas = tdao.findTabelasLocalAno(cal.get(Calendar.YEAR),
                                        locals.getIdbairro(), locals.getIdfilial(), locals.getIdlocal(), produtos.getIdproduto());
                                status = "LOCAL_ANO";
                            }
                            if (tabalas.isEmpty()) {
                                tabalas = tdao.findTabelasFilial(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DAY_OF_MONTH),
                                        locals.getIdlocal(), produtos.getIdproduto());
                                status = "FILIAL_DIA";
                            }
                            if (tabalas.isEmpty()) {
                                tabalas = tdao.findTabelasFilial(cal.get(Calendar.YEAR), cal.get(Calendar.WEEK_OF_YEAR),
                                        locals.getIdlocal(), produtos.getIdproduto());
                                status = "FILIAL_SEMANAANO";
                            }
                            if (tabalas.isEmpty()) {
                                tabalas = tdao.findTabelasFilialQt(cal.get(Calendar.YEAR), cal.get(Calendar.DAY_OF_YEAR), 30,
                                        locals.getIdlocal(), produtos.getIdproduto());
                                status = "FILIAL_30";
                            }
                            if (tabalas.isEmpty()) {
                                tabalas = tdao.findTabelasFilialQt(cal.get(Calendar.YEAR), cal.get(Calendar.DAY_OF_YEAR), 60,
                                        locals.getIdlocal(), produtos.getIdproduto());
                                status = "FILIAL_60";
                            }
                            if (tabalas.isEmpty()) {
                                tabalas = tdao.findTabelasFilialQt(cal.get(Calendar.YEAR), cal.get(Calendar.DAY_OF_YEAR), 90,
                                        locals.getIdlocal(), produtos.getIdproduto());
                                status = "FILIAL_90";
                            }
                            if (tabalas.isEmpty()) {
                                tabalas = tdao.findTabelasBairro(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DAY_OF_MONTH),
                                        locals.getIdbairro(), produtos.getIdproduto());
                                status = "BAIRRO_DIA";
                            }
                            if (tabalas.isEmpty()) {
                                tabalas = tdao.findTabelasBairro(cal.get(Calendar.YEAR), cal.get(Calendar.WEEK_OF_YEAR),
                                        locals.getIdbairro(), produtos.getIdproduto());
                                status = "BAIRRO_SEMANA";
                            }
                            if (tabalas.isEmpty()) {
                                tabalas = tdao.findTabelasBairroQt(cal.get(Calendar.YEAR), cal.get(Calendar.DAY_OF_YEAR), 30,
                                        locals.getIdbairro(), produtos.getIdproduto());
                                status = "BAIRRO_30";
                            }
                            if (tabalas.isEmpty()) {
                                tabalas = tdao.findTabelasBairroQt(cal.get(Calendar.YEAR), cal.get(Calendar.DAY_OF_YEAR), 60,
                                        locals.getIdbairro(), produtos.getIdproduto());
                                status = "BAIRRO_60";
                            }
                            if (tabalas.isEmpty()) {
                                tabalas = tdao.findTabelasBairroQt(cal.get(Calendar.YEAR), cal.get(Calendar.DAY_OF_YEAR), 90,
                                        locals.getIdbairro(), produtos.getIdproduto());
                                status = "BAIRRO_90";
                            }
                            if (tabalas.isEmpty()) {
                                tabalas = tdao.findTabelasCidade(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DAY_OF_MONTH),
                                        produtos.getIdproduto());
                                status = "CIDADE_DIA";
                            }
                            if (tabalas.isEmpty()) {
                                tabalas = tdao.findTabelasCidade(cal.get(Calendar.YEAR), cal.get(Calendar.WEEK_OF_YEAR),
                                        produtos.getIdproduto());
                                status = "CIDADE_SEMANA";
                            }
                            if (tabalas.isEmpty()) {
                                tabalas = tdao.findTabelasCidadeQt(cal.get(Calendar.YEAR), cal.get(Calendar.DAY_OF_YEAR), 30,
                                        produtos.getIdproduto());
                                status = "CIDADE_30";
                            }
                            if (tabalas.isEmpty()) {
                                tabalas = tdao.findTabelasCidadeQt(cal.get(Calendar.YEAR), cal.get(Calendar.DAY_OF_YEAR), 60,
                                        produtos.getIdproduto());
                                status = "CIDADE_60";
                            }
                            if (tabalas.isEmpty()) {
                                tabalas = tdao.findTabelasCidadeQt(cal.get(Calendar.YEAR), cal.get(Calendar.DAY_OF_YEAR), 90,
                                        produtos.getIdproduto());
                                status = "CIDADE_90";
                            }
                            if (tabalas.isEmpty()) {
                                tabalas = tdao.findTabelasCidadeAno(cal.get(Calendar.YEAR), produtos.getIdproduto());
                                status = "CIDADE_ANO";
                            }
                            if (!tabalas.isEmpty()) {
                                for (Tabelas tabala : tabalas) {
                                    valores += tabala.getValor();
                                }
                                table.setValor(valores / tabalas.size());
                                table.setStatus(status);
                            }

                            edao.create(table);

                        }
                    }
                    Instant finish = now();
                    LOG.log(INFO, "Grupo15Migra {0} seg {1} {2}", new Object[]{between(start, finish).getNano(), produtos.getNome(), locals.getNome()});

                    System.gc();
                }

            }

            Instant finish = now();
            LOG.log(INFO, "Grupo15Migra Final {0} seg", new Object[]{between(start, finish).getSeconds()});

        } catch (ParseException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
