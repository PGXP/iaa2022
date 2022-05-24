/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.pgxp.migra;

import br.com.pgxp.migra.dao.ClimaJpaController;
import br.com.pgxp.migra.dao.DolarJpaController;
import br.com.pgxp.migra.dao.TabelasJpaController;
import br.com.pgxp.migra.entity.Tabelas;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author desktop
 */
public class Dolar {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        TabelasJpaController tdao = new TabelasJpaController();
        DolarJpaController ddao = new DolarJpaController();

        for (br.com.pgxp.migra.entity.Dolar dolar : ddao.findAll()) {

            try {

                Calendar cal = new GregorianCalendar();
                cal.setTime(dolar.getData()); // Give your own date

                dolar.setDia(cal.get(Calendar.DAY_OF_MONTH));
                dolar.setMes(cal.get(Calendar.MONTH)+1);
                dolar.setAno(cal.get(Calendar.YEAR));

                dolar.setDiaano(cal.get(Calendar.DAY_OF_YEAR));
                dolar.setSemana(cal.get(Calendar.DAY_OF_WEEK));
                dolar.setSemanaano(cal.get(Calendar.WEEK_OF_YEAR));

                ddao.edit(dolar);

            } catch (ParseException ex) {
                Logger.getLogger(Dolar.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(Dolar.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }
    private static final Logger LOG = Logger.getLogger(Dolar.class.getName());

}
