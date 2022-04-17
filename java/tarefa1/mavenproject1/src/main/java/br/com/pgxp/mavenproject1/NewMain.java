/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.pgxp.mavenproject1;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.core.converters.CSVLoader;

/**
 *
 * @author desktop
 */
public class NewMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            // load CSV
            CSVLoader loader = new CSVLoader();
            loader.setFieldSeparator(";");
            loader.setSource(new File("/home/desktop/Documentos/bibliaV0.csv"));
            Instances data = loader.getDataSet();

            // save ARFF
            ArffSaver saver = new ArffSaver();
            saver.setInstances(data);
            saver.setFile(new File("/home/desktop/Documentos/bibliaV0.arff"));
            saver.setDestination(new File("/home/desktop/Documentos/bibliaV0.arff"));
            saver.writeBatch();
        } catch (IOException ex) {
            Logger.getLogger(NewMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
