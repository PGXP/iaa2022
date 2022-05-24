/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.pgxp.migra;

import br.com.pgxp.migra.dao.ClimaJpaController;
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
public class Clima {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        TabelasJpaController tdao = new TabelasJpaController();
        ClimaJpaController cdao = new ClimaJpaController();

        for (br.com.pgxp.migra.entity.Clima clima : cdao.findClimaEntities()) {

            try {
                SimpleDateFormat sdfSource = new SimpleDateFormat("yyyy-MM-dd");
                String sdia = clima.getAno() + "-" + clima.getMes() + "-" + clima.getDia();
                Date date = sdfSource.parse(sdia);

                Calendar cal = new GregorianCalendar();
                cal.setTime(date); // Give your own date

//                List<Tabelas> tables = tdao.findTabelas(clima.getAno(), clima.getMes(), clima.getDia());

//                if (!tables.isEmpty()) {

                    clima.setDiaano(cal.get(Calendar.DAY_OF_YEAR));
                    clima.setSemana(cal.get(Calendar.DAY_OF_WEEK));
                    clima.setSemanaano(cal.get(Calendar.WEEK_OF_YEAR));
                    cdao.edit(clima);
//                }

            } catch (ParseException ex) {
                Logger.getLogger(Clima.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(Clima.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

}
