/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import dao.ReporteDao;
import dao.VentaDao;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.Departamento;
import modelo.Tventadetalle;
import modelo.Usuario;
import vista.JPanelReportes;
import vista.Principal;
import vista.reporte.JPanelReporteVentas;

/**
 *
 * @author mq12
 */
public class ControladorReportes implements ActionListener {

    Principal vistaPrincipal;
    JPanelReportes jpanelReportes;
    JPanelReporteVentas jPanelReporteVentas;
    private List<Tventadetalle> listaventas;
    ReporteDao reporteDao = new ReporteDao();
    VentaDao ventaDao = new VentaDao();

    public ControladorReportes(Principal vistaPrincipal, JPanelReportes jpanelReportes, JPanelReporteVentas jPanelReporteVentas) {
        this.vistaPrincipal = vistaPrincipal;
        this.jpanelReportes = jpanelReportes;
        this.jPanelReporteVentas = jPanelReporteVentas;
        this.vistaPrincipal.jButtonReporte.addActionListener(this);
        this.jpanelReportes.jButtonVentas.addActionListener(this);

        this.jPanelReporteVentas.jButtonBuscar.addActionListener(this);
        llenarComboDepartamento();
        llenarComboUsuario();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == vistaPrincipal.jButtonReporte || e.getSource() == jpanelReportes.jButtonVentas) {
            if (vistaPrincipal.usuario.getNivel() == 0) {
                show();
            } else {
                JOptionPane.showMessageDialog(null, "No tienes los permisos para acceder", "Acceso denegado", JOptionPane.ERROR_MESSAGE);
            }
        }
        if (e.getSource() == jPanelReporteVentas.jButtonBuscar) {

            reporteTotalVentas();
        }
    }

    public void show() {
        try {
            //muestra panel productos
            vistaPrincipal.jPanelPanelPrincipal.removeAll();
            vistaPrincipal.jPanelPanelPrincipal.setLayout(new java.awt.BorderLayout());
            vistaPrincipal.jPanelPanelPrincipal.add(jpanelReportes);
            vistaPrincipal.jPanelPanelPrincipal.validate();
            vistaPrincipal.jPanelPanelPrincipal.repaint();

            jpanelReportes.jPanelPrincipal.removeAll();
            jpanelReportes.jPanelPrincipal.setLayout(new java.awt.BorderLayout());
            jpanelReportes.jPanelPrincipal.add(jPanelReporteVentas);
            jpanelReportes.jPanelPrincipal.validate();
            jpanelReportes.jPanelPrincipal.repaint();
            llenarComboDepartamento();
            llenarComboUsuario();
            jPanelReporteVentas.jDateChooserDesde.setDate(new Date());
            jPanelReporteVentas.jDateChooserHasta.setDate(new Date());

            BigDecimal ventas = reporteDao.getVentas(new Date(), new Date(), "", "", "totalprecioventa");
            BigDecimal costo = reporteDao.getVentas(new Date(), new Date(), "", "", "d.precioProveedor");
            System.out.println("costo  " + costo);
            jPanelReporteVentas.jLabelGanaciaTotal.setText("Ganancia Total: $ " + ventas.subtract(costo));
            jPanelReporteVentas.jLabelTotalVentas.setText("" + ventas);
            jPanelReporteVentas.jTableVentasDetalle.setModel(reporteDao.getVentasDetalle(new Date(), new Date(), "", ""));

        } catch (Exception ex) {
            Logger.getLogger(ControladorReportes.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void llenarComboDepartamento() {
        try {
            jPanelReporteVentas.jComboBoxDepartamento.setModel(new DefaultComboBoxModel());

            List<Departamento> departamentos;
            departamentos = reporteDao.getDepartamento();
            jPanelReporteVentas.jComboBoxDepartamento.addItem("Todos los departamentos");
            for (int i = 0; i < departamentos.size(); i++) {

                jPanelReporteVentas.jComboBoxDepartamento.addItem("" + departamentos.get(i).getNombre());

            }
        } catch (Exception ex) {
            Logger.getLogger(ControladorProductos.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void llenarComboUsuario() {
        jPanelReporteVentas.jComboBoxUsuario.setModel(new DefaultComboBoxModel());
        List<Usuario> usuarios;
        usuarios = reporteDao.getUsuarios();
        jPanelReporteVentas.jComboBoxUsuario.addItem("Todos los usuarios");
        for (int i = 0; i < usuarios.size(); i++) {
            jPanelReporteVentas.jComboBoxUsuario.addItem("" + usuarios.get(i).getNombre());
        }

    }


    private void reporteTotalVentas() {
        try {
            String usuario = "" + jPanelReporteVentas.jComboBoxUsuario.getSelectedItem();
            if (usuario.equals("Todos los usuarios")) {
                usuario = "";
            }

            String categoria = "" + jPanelReporteVentas.jComboBoxDepartamento.getSelectedItem();
            if (categoria.equals("Todos los departamentos")) {
                categoria = "";
            }

            jPanelReporteVentas.jTableVentasDetalle.setModel(reporteDao.getVentasDetalle(jPanelReporteVentas.jDateChooserDesde.getDate(), jPanelReporteVentas.jDateChooserHasta.getDate(), usuario, categoria));

            BigDecimal ventas = reporteDao.getVentas(jPanelReporteVentas.jDateChooserDesde.getDate(), jPanelReporteVentas.jDateChooserHasta.getDate(),usuario, categoria, "totalprecioventa");
            BigDecimal costo = reporteDao.getVentas(jPanelReporteVentas.jDateChooserDesde.getDate(), jPanelReporteVentas.jDateChooserHasta.getDate(), usuario,categoria, "d.precioProveedor");
      
            jPanelReporteVentas.jLabelGanaciaTotal.setText("Ganancia Total: $ " + ventas.subtract(costo));
            jPanelReporteVentas.jLabelTotalVentas.setText("" + ventas);


            System.out.println("CORONA NAVARRO DETALLE");
        } catch (Exception ex) {
            Logger.getLogger(ControladorReportes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
