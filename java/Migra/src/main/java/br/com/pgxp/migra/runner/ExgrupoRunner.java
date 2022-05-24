/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.pgxp.migra.runner;

import br.com.pgxp.migra.dao.ExgrupoJpaController;
import br.com.pgxp.migra.dao.TabelasJpaController;
import br.com.pgxp.migra.entity.Exgrupo;
import br.com.pgxp.migra.entity.Locals;
import br.com.pgxp.migra.entity.Produtos;
import br.com.pgxp.migra.entity.Tabelas;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import static java.util.logging.Logger.getLogger;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Serpro
 */
public class ExgrupoRunner implements Runnable {

    private static final Logger LOG = getLogger(ExgrupoRunner.class.getName());

    private Date date;
    private Locals locals;
    private Produtos produtos;
    private EntityManagerFactory emf;

    /**
     *
     */
    @Override
    public void run() {
        try {

            Calendar cal = new GregorianCalendar();
            cal.setTime(date); // Give your own date

            TabelasJpaController tdao = new TabelasJpaController(emf);
            ExgrupoJpaController edao = new ExgrupoJpaController(emf);

            List<Tabelas> tabalas = new ArrayList<>();

            Exgrupo table = new Exgrupo();
            table.setAno(cal.get(Calendar.YEAR));
            table.setMes(cal.get(Calendar.MONTH) + 1);
            table.setDia(cal.get(Calendar.DAY_OF_MONTH));
            table.setDiaano(cal.get(Calendar.DAY_OF_YEAR));
            table.setSemana(cal.get(Calendar.DAY_OF_WEEK));
            table.setSemanaano(cal.get(Calendar.WEEK_OF_YEAR));
            table.setMercado(locals.getNome());
            table.setProduto(produtos.getNome());

            tabalas = tdao.findTabelas(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DAY_OF_MONTH),
                    locals.getIdfilial(), locals.getIdlocal(), produtos.getIdproduto());

            if (tabalas.isEmpty()) {
                tabalas = tdao.findTabelasUltimoValor(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DAY_OF_MONTH),
                        locals.getIdfilial(), locals.getIdlocal(), produtos.getIdproduto());
            }

            if (tabalas.isEmpty()) {
                tabalas = tdao.findTabelasProximoValor(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DAY_OF_MONTH),
                        locals.getIdfilial(), locals.getIdlocal(), produtos.getIdproduto());
            }

            if (!tabalas.isEmpty()) {
                table.setValor(tabalas.get(0).getValor());
            }

            edao.edit(table);

            //LOG.log(Level.INFO, "Work {0} -> {1} -> {2}", new Object[]{estacao.getCodigo().toString(), lista.size(), between(start, finish).getNano()});
        } catch (Exception ex) {
            Logger.getLogger(ExgrupoRunner.class.getName()).log(Level.SEVERE, null, ex);
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

}
