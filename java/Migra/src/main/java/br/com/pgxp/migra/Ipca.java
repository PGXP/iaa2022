/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.pgxp.migra;

import br.com.pgxp.migra.dao.ClimaJpaController;
import br.com.pgxp.migra.dao.DolarJpaController;
import br.com.pgxp.migra.dao.IpcaJpaController;
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
public class Ipca {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        TabelasJpaController tdao = new TabelasJpaController();
        IpcaJpaController idao = new IpcaJpaController();

        for (br.com.pgxp.migra.entity.Ipca ipca : idao.findIpcaEntities()) {

            try {

                Calendar cal = new GregorianCalendar();
                cal.setTime(ipca.getData()); // Give your own date

                ipca.setMes(cal.get(Calendar.MONTH) + 1);
                ipca.setAno(cal.get(Calendar.YEAR));

                idao.edit(ipca);

            } catch (ParseException ex) {
                Logger.getLogger(Ipca.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(Ipca.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

}
