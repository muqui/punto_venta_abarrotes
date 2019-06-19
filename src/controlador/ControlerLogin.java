/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import dao.LoginDao;
import encryption.Encryption;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import modelo.Usuario;
import vista.JpanelVentas;
import vista.Login;
import vista.Principal;

/**
 *
 * @author mq12
 */
public class ControlerLogin implements ActionListener {

    Principal vistaPrincipal;
    Login login;
    JpanelVentas jpanelVentas;
    Usuario usuario;
    LoginDao loginDao;

    public ControlerLogin(Principal vistaPrincipal, Login login, JpanelVentas jpanelVentas) {
        this.vistaPrincipal = vistaPrincipal;
        this.login = login;
        this.jpanelVentas = jpanelVentas;
        usuario = new Usuario();
        loginDao = new LoginDao();
        this.login.jButtonAceptar.addActionListener(this);
        this.login.jButtonCancelar.addActionListener(this);
        this.vistaPrincipal.jButtonSalir.addActionListener(this);
        this.vistaPrincipal.jButtonCerrarSession.addActionListener(this);
        this.vistaPrincipal.jButtonVentas.addActionListener(this);
    }

    public void iniciar() {
        vistaPrincipal.jLabelTitulo.setVisible(false);
        vistaPrincipal.jButtonSalir.setVisible(false);
        vistaPrincipal.jToolBar1.setVisible(false);
        vistaPrincipal.jPanelPanelPrincipal.setVisible(false);
        vistaPrincipal.pack();
        vistaPrincipal.setSize(1, 1);

        vistaPrincipal.setLocationRelativeTo(null);
        vistaPrincipal.setVisible(true);

        login.setLocationRelativeTo(null);
        login.setVisible(true);
    }

    private void login() {
        try {
            String user = login.jTextFieldUsuario.getText();
            String password = Encryption.encrypt(login.jPasswordFieldPass.getText());
            usuario = loginDao.login(user, password);

            if (usuario == null) {
                JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrecto ", "error", JOptionPane.ERROR_MESSAGE);
            } else {

                login.setVisible(false);

                vistaPrincipal.dispose(); // Fixes the issue
                vistaPrincipal.setUndecorated(true);  //full screen
                vistaPrincipal.setVisible(true);
                vistaPrincipal.setExtendedState(vistaPrincipal.getExtendedState() | JFrame.MAXIMIZED_BOTH);
                vistaPrincipal.jLabelTitulo.setVisible(true);
                vistaPrincipal.jButtonSalir.setVisible(true);
                vistaPrincipal.jToolBar1.setVisible(true);
                vistaPrincipal.jPanelPanelPrincipal.setVisible(true);
                this.vistaPrincipal.usuario = usuario;
                System.out.println("usuario login : " + vistaPrincipal.usuario);
                vistaPrincipal.jLabelTitulo.setText("Usuario: " + login.jTextFieldUsuario.getText());
                vistaPrincipal.jPanelPanelPrincipal.removeAll();
                vistaPrincipal.jPanelPanelPrincipal.setLayout(new java.awt.BorderLayout());
                vistaPrincipal.jPanelPanelPrincipal.add(jpanelVentas);
                vistaPrincipal.jPanelPanelPrincipal.validate();
                vistaPrincipal.jPanelPanelPrincipal.repaint();
                login.jTextFieldUsuario.setText("");
                login.jPasswordFieldPass.setText("");

            }

        } catch (Exception ex) {
            Logger.getLogger(ControlerLogin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        if (arg0.getSource() == login.jButtonAceptar) {
            login();
        }
        if (arg0.getSource() == login.jButtonCancelar) {
            System.exit(0);
        }
        if (arg0.getSource() == vistaPrincipal.jButtonSalir) {
            System.exit(0);

        }
        if (arg0.getSource() == vistaPrincipal.jButtonCerrarSession) {
            iniciar();
        }
        if (arg0.getSource() == vistaPrincipal.jButtonVentas) {
            vistaPrincipal.jPanelPanelPrincipal.removeAll();
            vistaPrincipal.jPanelPanelPrincipal.setLayout(new java.awt.BorderLayout());
            vistaPrincipal.jPanelPanelPrincipal.add(jpanelVentas);
            vistaPrincipal.jPanelPanelPrincipal.validate();
            vistaPrincipal.jPanelPanelPrincipal.repaint();
        }
    }

}
