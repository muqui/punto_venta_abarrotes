/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import dao.DepartamentoDao;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import modelo.Departamento;
import vista.JpanelProductos;
import vista.Principal;
import vista.producto.JpanelDepartamento;

/**
 *
 * @author mq12
 */
public class ControladorCrearDepartamento implements ActionListener {

    JpanelProductos jpanelProductos;
    Principal vistaPrincipal;
    JpanelDepartamento jpanelDepartamento;
    DepartamentoDao departamentoDao = new DepartamentoDao();

    public ControladorCrearDepartamento(Principal vistaPrincipal, JpanelDepartamento jpanelDepartamento, JpanelProductos jpanelProductos) {
        this.vistaPrincipal = vistaPrincipal;
        this.jpanelDepartamento = jpanelDepartamento;
        this.jpanelProductos = jpanelProductos;
        this.jpanelProductos.jButtonDepartamento.addActionListener(this);
        jpanelDepartamento.jButtonCrear.addActionListener(this);
        jpanelDepartamento.jLabelError.setText("");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == jpanelProductos.jButtonDepartamento) {
            //muestra panel productos
            vistaPrincipal.jPanelPanelPrincipal.removeAll();
            vistaPrincipal.jPanelPanelPrincipal.setLayout(new java.awt.BorderLayout());
            vistaPrincipal.jPanelPanelPrincipal.add(jpanelProductos);
            vistaPrincipal.jPanelPanelPrincipal.validate();
            vistaPrincipal.jPanelPanelPrincipal.repaint();
            //muestra panel departamento
            jpanelProductos.jPanelPincipal.removeAll();
            jpanelProductos.jPanelPincipal.setLayout(new java.awt.BorderLayout());
            jpanelProductos.jPanelPincipal.add(jpanelDepartamento);
            jpanelProductos.jPanelPincipal.validate();
            jpanelProductos.jPanelPincipal.repaint();

        }
        if (e.getSource() == jpanelDepartamento.jButtonCrear) {
            jpanelDepartamento.jLabelError.setText("");
            if ("".endsWith(jpanelDepartamento.jTextFieldNombreDepartamento.getText().trim())) {
                jpanelDepartamento.jLabelError.setText("Capture nombre.");

            } else {
                Departamento d = departamentoDao.getDepartamento(jpanelDepartamento.jTextFieldNombreDepartamento.getText().trim());
                if (d != null) {
                    jpanelDepartamento.jLabelError.setText("Este departamento ya xiste.");
                } else {
                    departamentoDao.cerrar();
                    d = new Departamento();
                    d.setNombre(jpanelDepartamento.jTextFieldNombreDepartamento.getText().trim());
                    departamentoDao.addDepartamento(d);
                    jpanelDepartamento.jTextFieldNombreDepartamento.setText("");
                }
            }
        }
    }

}
