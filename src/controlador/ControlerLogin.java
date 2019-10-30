/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import dao.LoginDao;
import dao.UsuarioDao;
import encryption.Encryption;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import modelo.Usuario;
import vista.CrearAdmin;
import vista.JpanelVentas;
import vista.Login;
import vista.Principal;

/**
 *
 * @author mq12
 */
public class ControlerLogin implements ActionListener, KeyListener {

    Principal vistaPrincipal;
    Login login;
    JpanelVentas jpanelVentas;
    Usuario usuario;
    UsuarioDao usuarioDao = new UsuarioDao();
    Usuario admin = new Usuario();
    CrearAdmin crearAdmin;
    LoginDao loginDao;

    public ControlerLogin(Principal vistaPrincipal, Login login, JpanelVentas jpanelVentas, CrearAdmin crearAdmin) {
        this.vistaPrincipal = vistaPrincipal;
        this.login = login;
        this.jpanelVentas = jpanelVentas;
        this.crearAdmin = crearAdmin;
        usuario = new Usuario();
        loginDao = new LoginDao();
        this.login.jButtonAceptar.addActionListener(this);
        this.login.jButtonCancelar.addActionListener(this);
        this.vistaPrincipal.jButtonSalir.addActionListener(this);
        this.vistaPrincipal.jButtonCerrarSession.addActionListener(this);
        this.vistaPrincipal.jButtonVentas.addActionListener(this);
        this.login.jTextFieldUsuario.addKeyListener(this);
        this.login.jPasswordFieldPass.addKeyListener(this);
        this.crearAdmin.jButtonCrear.addActionListener(this);
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
        /*Si usuarios es null se crea el primer usuario*/
        if (loginDao.getUsers() == null) {

            crearAdmin.setLocationRelativeTo(null);
            crearAdmin.setVisible(true);

        } else {
            login.setLocationRelativeTo(null);
            login.setVisible(true);
        }

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
        if (arg0.getSource() == crearAdmin.jButtonCrear) {
            if (validarFormulario()) {
                crearAdmin();
                 JOptionPane.showMessageDialog(null,"Usuario creado!", "Usuario", JOptionPane.INFORMATION_MESSAGE);
                crearAdmin.setVisible(false);
                iniciar();
            }

        }
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
            jpanelVentas.jTextFieldCodigoBarras.requestFocus();
        }
    }

    @Override
    public void keyTyped(KeyEvent arg0) {

    }

    @Override
    public void keyPressed(KeyEvent arg0) {
        if (arg0.getSource() == login.jTextFieldUsuario || arg0.getSource() == login.jPasswordFieldPass) {
            if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
                login();
            }

        }

    }

    @Override
    public void keyReleased(KeyEvent arg0) {

    }

    /*Crea el primer usuario como admin*/
    private void crearAdmin() {

        admin.setNivel(0);

        try {
            usuarioDao.addUser(admin);
        } catch (Exception ex) {
            Logger.getLogger(ControlerLogin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private boolean validarFormulario() {
        boolean bandera = true;
        String error = "";
        /*Nombre*/
        if (!"".equals(crearAdmin.jTextFieldNombre.getText().trim())) {
            admin.setNombre(crearAdmin.jTextFieldNombre.getText().trim());
        } else {
           
           error = "Nombre no puede ser vacio. \n";
            bandera = false;

        }
        //CONTRASEÑA
        if (!"".equals(crearAdmin.jPasswordFieldPassword.getText().trim())) {
            if (crearAdmin.jPasswordFieldPassword.getText().trim().length() < 5) {
                bandera = false;
               
                error = error + "Contraseña debe ser mayor a 6 caracteres. \n";
            } else {

                admin.setPassword(Encryption.encrypt(crearAdmin.jPasswordFieldPassword.getText().trim()));
            }

        } else {
            bandera = false;
            
             error = error + "Contraseña  no puede ser vacia. \n";
        }
        /*Contraseñas */
        if (!crearAdmin.jPasswordFieldPassword.getText().trim().equals(crearAdmin.jPasswordFieldConfirmar.getText().trim())) {
           
            error = error + "Las contraseñas no coinciden. \n";
            bandera = false;
        }

        if (bandera == false) {
            JOptionPane.showMessageDialog(null, error, "error", JOptionPane.ERROR_MESSAGE);
        }

        return bandera;
    }

}
