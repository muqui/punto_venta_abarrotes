/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import vista.JPanelConfiguracion;
import vista.Principal;
import vista.configuracion.JPanelBaseDeDatos;

/**
 *
 * @author mq12
 */
public class ControladorConfigurarBaseDatos implements ActionListener {

    Principal vistaPrincipal;
    JPanelConfiguracion jPanelConfiguracion;
    JPanelBaseDeDatos jPanelBaseDeDatos;
    JFileChooser jFileChooserRespaldo = new JFileChooser();

    public ControladorConfigurarBaseDatos(Principal vistaPrincipal, JPanelConfiguracion jPanelConfiguracion, JPanelBaseDeDatos jPanelBaseDeDatos) {
        this.vistaPrincipal = vistaPrincipal;
        this.jPanelConfiguracion = jPanelConfiguracion;
        this.jPanelBaseDeDatos = jPanelBaseDeDatos;
        this.jPanelConfiguracion.jButtonBaseDeDatos.addActionListener(this);
        this.jPanelBaseDeDatos.jButtonGuardar.addActionListener(this);
        this.jPanelBaseDeDatos.jButtonDirectorio.addActionListener(this);
        jFileChooserRespaldo.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        if (arg0.getSource() == jPanelBaseDeDatos.jButtonGuardar) {
            guardar();

        }
        if (arg0.getSource() == jPanelBaseDeDatos.jButtonDirectorio) {
            jFileChooserRespaldo.showSaveDialog(null);
            System.out.println("directorio");
            jPanelBaseDeDatos.jTextFieldRESPALDOS.setText("" + jFileChooserRespaldo.getSelectedFile());
            System.out.println(jFileChooserRespaldo.getSelectedFile());

        }
        if (arg0.getSource() == jPanelConfiguracion.jButtonBaseDeDatos) {

            show();

        }
    }

    private void show() {
        System.out.println("configurar base datos");
        jPanelConfiguracion.jPanelPrincipal.removeAll();
        jPanelConfiguracion.jPanelPrincipal.setLayout(new java.awt.BorderLayout());
        jPanelConfiguracion.jPanelPrincipal.add(jPanelBaseDeDatos);
        jPanelConfiguracion.jPanelPrincipal.validate();
        jPanelConfiguracion.jPanelPrincipal.repaint();
        try {
            Properties prop = new Properties();
           // InputStream input = getClass().getClassLoader().getResourceAsStream("hibernate.properties");
            InputStream input = new FileInputStream("hibernate.properties");
            prop.load(input);
            jPanelBaseDeDatos.jTextFieldURL.setText(prop.getProperty("hibernate.connection.url"));
            jPanelBaseDeDatos.jTextFieldDRIVER.setText(prop.getProperty("hibernate.connection.driver_class"));
            jPanelBaseDeDatos.jTextFieldDIALECTO.setText(prop.getProperty("hibernate.dialect"));
            jPanelBaseDeDatos.jTextFieldUSUARIO.setText(prop.getProperty("hibernate.connection.username"));
            jPanelBaseDeDatos.jTextFieldPASSWORD.setText(prop.getProperty("hibernate.connection.password"));
            jPanelBaseDeDatos.jTextFieldBASEDATOS.setText(prop.getProperty("hibernate.connection.database"));
            jPanelBaseDeDatos.jTextFieldRESPALDOS.setText(prop.getProperty("hibernate.connection.backup"));
            input.close();

        } catch (IOException e) {
            System.out.println("eroor " + e);
        }

    }

    private void guardar() {
        try {
            Properties prop = new Properties();
            //InputStream input = getClass().getClassLoader().getResourceAsStream("hibernate.properties");
            InputStream input = new FileInputStream("hibernate.properties");
           
            prop.load(input);
            input.close();
            // URL resource = Thread.currentThread().getContextClassLoader().getResource("hibernate.properties");
           
            FileOutputStream out = new FileOutputStream("hibernate.properties");
            prop.setProperty("hibernate.connection.url", jPanelBaseDeDatos.jTextFieldURL.getText());
            prop.setProperty("hibernate.connection.driver_class", jPanelBaseDeDatos.jTextFieldDRIVER.getText());
            prop.setProperty("hibernate.dialect", jPanelBaseDeDatos.jTextFieldDIALECTO.getText());
            prop.setProperty("hibernate.connection.username", jPanelBaseDeDatos.jTextFieldUSUARIO.getText());
            prop.setProperty("hibernate.connection.password", jPanelBaseDeDatos.jTextFieldPASSWORD.getText());
            prop.setProperty("hibernate.connection.database", jPanelBaseDeDatos.jTextFieldBASEDATOS.getText());
            prop.setProperty("hibernate.connection.backup", jPanelBaseDeDatos.jTextFieldRESPALDOS.getText());
            System.out.println("cambio a properties");
            System.out.println("" + prop);
            prop.store(out, null);
            
            out.close();
             JOptionPane.showMessageDialog(null, "Cambios guardados el programa se cerrara.  ", "OK", JOptionPane.INFORMATION_MESSAGE);
             System.exit(0);
        } catch (IOException e) {
            System.out.println("eroor " + e);
        } 
    }

}
