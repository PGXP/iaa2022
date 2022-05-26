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

            Double valores = 0.0;
            String status = "";
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
//                LOG.info(table.toString());
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
