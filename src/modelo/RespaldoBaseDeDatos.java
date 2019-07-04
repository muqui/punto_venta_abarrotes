/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mq12
 */
public class RespaldoBaseDeDatos extends Thread {

    @Override
    public void run() {
        try {
            System.out.println("inicia respaldo ........................................");
            GenerarBackupMySQL();
        } catch (IOException ex) {
            Logger.getLogger(RespaldoBaseDeDatos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void GenerarBackupMySQL() throws IOException {
        Properties prop = new Properties();

        InputStream input = new FileInputStream("hibernate.properties");
        prop.load(input);
        String dataBase = prop.getProperty("hibernate.connection.database");
        String respaldoURL = prop.getProperty("hibernate.connection.backup");
        System.out.println("respaldoURL=  "+ respaldoURL);
        input.close();
        //DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd   HH-mm-ss");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        
        LocalDateTime now = LocalDateTime.now();
        Runtime runtime = Runtime.getRuntime();

        //File backupFile = new File("E:\\RespaldosDB\\" + dtf.format(now) + "_respaldo.sql");
        File backupFile = new File(respaldoURL+"\\" +dataBase+"_"+dtf.format(now) + ".sql");
        FileWriter fw = new FileWriter(backupFile);
        Process child = runtime.exec("C:\\Program Files\\MySQL\\MySQL Server 5.5\\bin\\mysqldump --opt --password=Fedora12 --user=root --databases " + dataBase);
        InputStreamReader irs = new InputStreamReader(child.getInputStream());
        BufferedReader br = new BufferedReader(irs);
        String line;

        while ((line = br.readLine()) != null) {
            fw.write(line + "\n");
        }
        fw.close();
        irs.close();
        br.close();
        System.out.println("FIN respaldo ........................................");

    }
}
