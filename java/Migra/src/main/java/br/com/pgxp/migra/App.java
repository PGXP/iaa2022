/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.pgxp.migra;

import br.com.pgxp.migra.dao.TabelasJpaController;
import br.com.pgxp.migra.entity.Tabelas;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author desktop
 */
public class App {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        TabelasJpaController tdao = new TabelasJpaController();
        
        for (Tabelas table : tdao.findTabelasEntities()) {
            
            try {
                SimpleDateFormat sdfSource = new SimpleDateFormat("yyyy-MM-dd");
                String sdia = table.getAno() + "-" + table.getMes() + "-" + table.getDia();
                Date date = sdfSource.parse(sdia);
                
                Calendar cal = new GregorianCalendar();
                cal.setTime(date); // Give your own date
                
                table.setDiaano(cal.get(Calendar.DAY_OF_YEAR));
                table.setSemana(cal.get(Calendar.DAY_OF_WEEK));
                table.setSemanaano(cal.get(Calendar.WEEK_OF_YEAR));
                
                tdao.edit(table);
                
            } catch (ParseException ex) {
                Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    
}
