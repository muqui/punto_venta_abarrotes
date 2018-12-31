/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author mq12
 */
public class RespaldoBaseDeDatos {

    public void GenerarBackupMySQL() throws IOException {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd   HH-mm-ss");
        LocalDateTime now = LocalDateTime.now();
        Runtime runtime = Runtime.getRuntime();

        File backupFile = new File("E:\\RespaldosDB\\" + dtf.format(now) + "_respaldo.sql"); 
       // File backupFile = new File("D:\\servidor\\CYBER\\backupMuquiventas2018\\" + dtf.format(now) + "_respaldo.sql"); 
        FileWriter fw = new FileWriter(backupFile);
        Process child = runtime.exec("C:\\Program Files\\MySQL\\MySQL Server 5.5\\bin\\mysqldump --opt --password=Fedora12 --user=root --databases puntoventa");
        InputStreamReader irs = new InputStreamReader(child.getInputStream());
        BufferedReader br = new BufferedReader(irs);
        String line;

        while ((line = br.readLine()) != null) {
            fw.write(line + "\n");
        }
        fw.close();
        irs.close();
        br.close();
      
    }
}
