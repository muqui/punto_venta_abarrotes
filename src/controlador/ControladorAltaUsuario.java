/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import dao.UsuarioDao;
import encryption.Encryption;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import modelo.Usuario;
import vista.JPanelConfiguracion;
import vista.Principal;
import vista.configuracion.JPanelAltaUsuario;

/**
 *
 * @author mq12
 */
public class ControladorAltaUsuario implements ActionListener {

    Principal vistaPrincipal;
    JPanelConfiguracion jPanelConfiguracion;
    JPanelAltaUsuario jPanelAltaUsuario;
    UsuarioDao usuarioDao = new UsuarioDao();
      Usuario altausuario = new Usuario();
    public ControladorAltaUsuario(Principal vistaPrincipal, JPanelConfiguracion jPanelConfiguracion, JPanelAltaUsuario jPanelAltaUsuario) {
        this.vistaPrincipal = vistaPrincipal;
        this.jPanelConfiguracion = jPanelConfiguracion;
        this.jPanelAltaUsuario = jPanelAltaUsuario;
        this.vistaPrincipal.jButtonConfiguracion.addActionListener(this);
        this.jPanelAltaUsuario.jButtonCrear.addActionListener(this);
        limpiarCampoError();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == jPanelAltaUsuario.jButtonCrear) {
           if(validarFormulario()){
               crearUsuario();
           }
            
        }
        if (e.getSource() == vistaPrincipal.jButtonConfiguracion) {
            show();
        }
    }

    public void show() {
        //muestra panel productos
        vistaPrincipal.jPanelPanelPrincipal.removeAll();
        vistaPrincipal.jPanelPanelPrincipal.setLayout(new java.awt.BorderLayout());
        vistaPrincipal.jPanelPanelPrincipal.add(jPanelConfiguracion);
        vistaPrincipal.jPanelPanelPrincipal.validate();
        vistaPrincipal.jPanelPanelPrincipal.repaint();

        jPanelConfiguracion.jPanelPrincipal.removeAll();
        jPanelConfiguracion.jPanelPrincipal.setLayout(new java.awt.BorderLayout());
        jPanelConfiguracion.jPanelPrincipal.add(jPanelAltaUsuario);
        jPanelConfiguracion.jPanelPrincipal.validate();
        jPanelConfiguracion.jPanelPrincipal.repaint();

    }

    private boolean validarFormulario() {
        boolean bandera = true;
      
        try {

            limpiarCampoError();
            Usuario u = usuarioDao.getUsuarioXnombre(jPanelAltaUsuario.jTextFieldNombre.getText().trim());
            System.out.println("Usuario " + u);
            //NOMBRE
            if (!"".equals(jPanelAltaUsuario.jTextFieldNombre.getText().trim())) {
                if (u != null) {
                    jPanelAltaUsuario.jLabelErrorNombre.setText("Este usuario ya existe!");
                    bandera = false;
                } else {
                    altausuario.setNombre(jPanelAltaUsuario.jTextFieldNombre.getText().trim());
                }
            } else {
                bandera = false;
                jPanelAltaUsuario.jLabelErrorNombre.setText("Capture nombre");
            }
            //CONTRASEÑA
            if (!"".equals(jPanelAltaUsuario.jPasswordFieldContrasena.getText().trim())) {
                if (jPanelAltaUsuario.jPasswordFieldContrasena.getText().length() < 8) {
                     bandera = false;
                      jPanelAltaUsuario.jLabelErrorContrasena.setText("se requiere 8 caracteres");
                }
                else{
                    // usuario.setPassword(Encryption.encrypt(jPanelAltaUsuario.jPasswordFieldContrasena.getText().trim()));
                    altausuario.setPassword(Encryption.encrypt(jPanelAltaUsuario.jPasswordFieldContrasena.getText().trim()));
                }

            } else {
                bandera = false;
                jPanelAltaUsuario.jLabelErrorContrasena.setText("capture una contraseña");
            }
            //CONFIRMAR
            if(!jPanelAltaUsuario.jPasswordFieldContrasena.getText().equals(jPanelAltaUsuario.jPasswordFieldConfirmar.getText())){
             jPanelAltaUsuario.jLabelErrorConfirmar.setText("Las contraseñas con coinciden.");
            }
           

        } catch (Exception ex) {
            Logger.getLogger(ControladorAltaUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("bandera " + bandera);
        return bandera;
    }

    private void limpiarCampoError() {

        jPanelAltaUsuario.jLabelErrorConfirmar.setText("");
        jPanelAltaUsuario.jLabelErrorContrasena.setText("");
        jPanelAltaUsuario.jLabelErrorNombre.setText("");

    }

    private void crearUsuario() {
        try {
            altausuario.setNivel(jPanelAltaUsuario.jComboBoxNivel.getSelectedIndex());
            usuarioDao.addUser(altausuario);
           JOptionPane.showMessageDialog (null, "Usuario creado.", "Exito.", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            Logger.getLogger(ControladorAltaUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

}
