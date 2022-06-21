/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.pgxp.migra.runner;

import br.com.pgxp.migra.dao.Grupo15JpaController;
import br.com.pgxp.migra.dao.TabelasJpaController;
import br.com.pgxp.migra.entity.Grupo15;
import br.com.pgxp.migra.entity.Locals;
import br.com.pgxp.migra.entity.Produtos;
import br.com.pgxp.migra.entity.Tabelas;
import static java.time.Duration.between;
import java.time.Instant;
import static java.time.Instant.now;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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
public class Grupo15RunnerUpdate implements Runnable {

    private static final Logger LOG = getLogger(Grupo15RunnerUpdate.class.getName());

    private Date date;
    private Locals locals;
    private Produtos produtos;
    private EntityManagerFactory emf;
    private Grupo15 table;

    /**
     *
     */
    @Override
    public void run() {

        try {
            Calendar cal = new GregorianCalendar();
            cal.setTime(date); // Give your own date

            TabelasJpaController tdao = new TabelasJpaController(emf);
            Grupo15JpaController edao = new Grupo15JpaController(emf);

            Double valores = 0.0;
            String status = "";

            List<Tabelas> tabalas = new ArrayList<>();

            if (tabalas.isEmpty()) {
                tabalas = tdao.findTabelas(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DAY_OF_MONTH),
                        locals.getIdbairro(), locals.getIdfilial(), locals.getIdlocal(), produtos.getIdproduto());
                status = "COLETADO";
            }

            for (int i = 0; i <= 350; i++) {
                if (tabalas.isEmpty()) {
                    tabalas = tdao.findTabelasLocal(cal.get(Calendar.YEAR), cal.get(Calendar.DAY_OF_YEAR), i,
                            locals.getIdbairro(), locals.getIdfilial(), locals.getIdlocal(), produtos.getIdproduto());
                    status = "LOCAL_" + i;
                }

            }

            for (int i = 0; i <= 350; i++) {
                if (tabalas.isEmpty()) {
                    tabalas = tdao.findTabelasFilialQt(cal.get(Calendar.YEAR), cal.get(Calendar.DAY_OF_YEAR), i,
                            locals.getIdlocal(), produtos.getIdproduto());
                    status = "FILIAL_" + i;
                }
            }

            for (int i = 0; i <= 350; i++) {
                if (tabalas.isEmpty()) {
                    tabalas = tdao.findTabelasBairroQt(cal.get(Calendar.YEAR), cal.get(Calendar.DAY_OF_YEAR), i,
                            locals.getIdbairro(), produtos.getIdproduto());
                    status = "BAIRRO_" + i;
                }
            }

            for (int i = 0; i <= 350; i++) {
                if (tabalas.isEmpty()) {
                    tabalas = tdao.findTabelasCidadeQt(cal.get(Calendar.YEAR), cal.get(Calendar.DAY_OF_YEAR), i,
                            produtos.getIdproduto());
                    status = "CIDADE_" + i;
                }
            }

            for (int i = 0; i <= 350; i++) {
                if (tabalas.isEmpty()) {
                    tabalas = tdao.findTabelasCidadeTotal(cal.get(Calendar.YEAR), cal.get(Calendar.DAY_OF_YEAR), i,
                            produtos.getIdproduto());
                    status = "GERAL_" + i;
                }
            }

            if (!tabalas.isEmpty()) {
                for (Tabelas tabala : tabalas) {
                    valores += tabala.getValor();
                }
                table.setValor(valores / tabalas.size());
                table.setStatus(status);
            }

            edao.edit(table);

            LOG.log(INFO, "Grupo15 Update {0} ", new Object[]{table.toString()});

        } catch (Exception ex) {
            Logger.getLogger(Grupo15RunnerUpdate.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Locals getLocals() {
        return locals;
    }

    public void setLocals(Locals locals) {
        this.locals = locals;
    }

    public Produtos getProdutos() {
        return produtos;
    }

    public void setProdutos(Produtos produtos) {
        this.produtos = produtos;
    }

    public EntityManagerFactory getEmf() {
        return emf;
    }

    public void setEmf(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public Grupo15 getTable() {
        return table;
    }

    public void setTable(Grupo15 table) {
        this.table = table;
    }

}
