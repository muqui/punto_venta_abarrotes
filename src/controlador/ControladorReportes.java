/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import dao.DepartamentoDao;
import dao.UsuarioDao;
import dao.VentaDao;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
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
    DepartamentoDao departamentodao = new DepartamentoDao();
    VentaDao ventaDao = new VentaDao();
    UsuarioDao usuarioDao = new UsuarioDao();

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
            show();
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

            listaventas = ventaDao.getVentasDetalle(new Date(), new Date(), "", "");

            BigDecimal ventas = ventaDao.getVentas(new Date(), new Date(), "", "");
            BigDecimal costo = ventaDao.getPrecioProveedorTotal(new Date(), new Date(), "", "");
            jPanelReporteVentas.jLabelGanaciaTotal.setText("Ganancia Total: $ " + ventas.subtract(costo));
            jPanelReporteVentas.jLabelTotalVentas.setText("" + ventas);
            jPanelReporteVentas.jTableVentasDetalle.setModel(llenartabla());
            ventaDao.cerrar();
        } catch (Exception ex) {
            Logger.getLogger(ControladorReportes.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void llenarComboDepartamento() {
        try {
            jPanelReporteVentas.jComboBoxDepartamento.setModel(new DefaultComboBoxModel());

            List<Departamento> departamentos;
            departamentos = departamentodao.getDepartamento();
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
        usuarios = usuarioDao.getUsuarios();
        jPanelReporteVentas.jComboBoxUsuario.addItem("Todos los usuarios");
        for (int i = 0; i < usuarios.size(); i++) {
            jPanelReporteVentas.jComboBoxUsuario.addItem("" + usuarios.get(i).getNombre());
        }

    }

    private DefaultTableModel llenartabla() {

        DefaultTableModel tableModel = new DefaultTableModel() {

            @Override
            public boolean isCellEditable(int row, int column) {
                //all cells false
                return false;
            }
        };

        String[] columnNames = {"ID VENTA", "FECHA", "CODIGO", "NOMBRE", "DEPARTAMENTO", "USUARIO", "CANTIDAD", "PRECIO", "COSTO TOTAL PROVEEDOR", "TOTAL"};
        tableModel.setColumnIdentifiers(columnNames);
        Object[] fila = new Object[tableModel.getColumnCount()];

        for (int i = 0; i < listaventas.size(); i++) {
            fila[0] = listaventas.get(i).getTventa().getIdVenta();
            fila[1] = listaventas.get(i).getTventa().getFechaRegistro();
            fila[2] = listaventas.get(i).getCodigoBarrasProducto();
            fila[3] = listaventas.get(i).getNombreProducto();
            fila[4] = listaventas.get(i).getTproducto().getDepartamento().getNombre();
            fila[5] = listaventas.get(i).getTventa().getUsuario().getNombre();
            fila[6] = listaventas.get(i).getCantidad();
            fila[7] = listaventas.get(i).getPrecioVentaUnitarioProducto();
            fila[8] = listaventas.get(i).getPrecioProveedor();
            fila[9] = listaventas.get(i).getTotalPrecioVenta();

            tableModel.addRow(fila);

        }
        return tableModel;
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
            listaventas = ventaDao.getVentasDetalle(jPanelReporteVentas.jDateChooserDesde.getDate(), jPanelReporteVentas.jDateChooserHasta.getDate(), usuario, categoria);
            BigDecimal ventas = ventaDao.getVentas(jPanelReporteVentas.jDateChooserDesde.getDate(), jPanelReporteVentas.jDateChooserHasta.getDate(), usuario, categoria);
            BigDecimal costo = ventaDao.getPrecioProveedorTotal(jPanelReporteVentas.jDateChooserDesde.getDate(), jPanelReporteVentas.jDateChooserHasta.getDate(), usuario, categoria);

            jPanelReporteVentas.jLabelTotalVentas.setText("" + ventas);
            System.out.println("total costo proveedor" + costo);
            jPanelReporteVentas.jLabelGanaciaTotal.setText("Ganancia Total: " + ventas.subtract(costo));
            jPanelReporteVentas.jTableVentasDetalle.setModel(llenartabla());
            ventaDao.cerrar();
        } catch (Exception ex) {
            Logger.getLogger(ControladorReportes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
