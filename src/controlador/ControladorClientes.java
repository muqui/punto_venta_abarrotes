/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import dao.ClienteDao;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import modelo.Cliente;
import vista.JpanelClientes;
import vista.JpanelProductos;
import vista.Principal;
import vista.cliente.JpanelClienteNuevo;

/**
 *
 * @author mq12
 */
public class ControladorClientes implements ActionListener, KeyListener {

    Principal vistaPrincipal;
    JpanelClientes jpanelClientes;
    JpanelClienteNuevo jpanelClienteNuevo;
    
    ClienteDao clientedao;
    Cliente cliente = new Cliente();
    public ControladorClientes(Principal vistaPrincipal, JpanelClientes jpanelClientes, JpanelClienteNuevo jpanelClienteNuevo) {
      
        this.jpanelClienteNuevo = jpanelClienteNuevo;
        this.vistaPrincipal = vistaPrincipal;
        this.jpanelClientes = jpanelClientes;
        this.vistaPrincipal.jButtonClientes.addActionListener(this);
        this.jpanelClienteNuevo.jButtonGuardar.addActionListener(this);
        
        clientedao = new ClienteDao();
        clearErrorLabel();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vistaPrincipal.jButtonClientes ) {
            if(vistaPrincipal.usuario.getNivel() == 0){
                 show();
            }
           else{
                JOptionPane.showMessageDialog(null, "No tienes los permisos para acceder", "Acceso denegado", JOptionPane.ERROR_MESSAGE);
            }
        }
        if (e.getSource() == jpanelClienteNuevo.jButtonGuardar) {
            guardarCliente();
        }

    }

    public void show() {

        vistaPrincipal.jPanelPanelPrincipal.removeAll();
        vistaPrincipal.jPanelPanelPrincipal.setLayout(new java.awt.BorderLayout());
        vistaPrincipal.jPanelPanelPrincipal.add(jpanelClientes);
        vistaPrincipal.jPanelPanelPrincipal.validate();
        vistaPrincipal.jPanelPanelPrincipal.repaint();
        //muestra panel nuevo producto
        jpanelClientes.jPanelPrincipal.setLayout(new java.awt.BorderLayout());
        jpanelClientes.jPanelPrincipal.add(jpanelClienteNuevo);
        jpanelClientes.jPanelPrincipal.validate();
        jpanelClientes.jPanelPrincipal.repaint();
    }

    private boolean guardar() {
        clearErrorLabel();
       
        boolean bandera = true;
         if (!"".equals(jpanelClienteNuevo.jTextFieldNombre.getText().trim())) {
                cliente.setNombreCompleto(jpanelClienteNuevo.jTextFieldNombre.getText().trim());

            } else {
                bandera = false;
               jpanelClienteNuevo.jLabelErrorNombre.setText("Capture Nombre");
            }
          if (!"".equals(jpanelClienteNuevo.jTextFieldDireccion.getText().trim())) {
                cliente.setDireccion(jpanelClienteNuevo.jTextFieldDireccion.getText().trim());

            } else {
                bandera = false;
               jpanelClienteNuevo.jLabelErrorDireccion.setText("Capture dirección");
            }
           if (!"".equals(jpanelClienteNuevo.jTextFieldTelefono.getText().trim())) {
                cliente.setTelefono(jpanelClienteNuevo.jTextFieldTelefono.getText().trim());

            } else {
                bandera = false;
               jpanelClienteNuevo.jLabelErrorTelefono.setText("Capture Telefono");
            }
            if (!"".equals(jpanelClienteNuevo.jTextFieldLimite.getText().trim())) {
                cliente.setLimite(Integer.parseInt(jpanelClienteNuevo.jTextFieldLimite.getText().trim()));

            } else {
                bandera = false;
               jpanelClienteNuevo.jLabelErrorLimite.setText("Capture Limite");
            }
        return bandera;
    }
    public void guardarCliente(){
      if (guardar()) {

            try {
                if (clientedao.addProduct(cliente)) {
                    limpiarFormulario();
                }

            } catch (Exception ex) {
                Logger.getLogger(ControladorProductos.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    
    }
    private void clearErrorLabel() {
        jpanelClienteNuevo.jLabelErrorDireccion.setText("");
        jpanelClienteNuevo.jLabelErrorLimite.setText("");
        jpanelClienteNuevo.jLabelErrorNombre.setText("");
        jpanelClienteNuevo.jLabelErrorTelefono.setText("");
       
    }

    private void limpiarFormulario() {
       jpanelClienteNuevo.jTextFieldDireccion.setText("");
       jpanelClienteNuevo.jTextFieldLimite.setText("");
       jpanelClienteNuevo.jTextFieldNombre.setText("");
       jpanelClienteNuevo.jTextFieldTelefono.setText("");
              
    }

    @Override
    public void keyTyped(KeyEvent ke) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
