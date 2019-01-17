/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import dao.VentaDao;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import modelo.Tventadetalle;
import vista.Principal;
import vista.reporte.JPanelMisVentas;

/**
 *
 * @author mq12
 */
public class ControladorMisVentas implements ActionListener {

    Principal vistaPrincipal;
    JPanelMisVentas jpanelMisVentas;
    private List<Tventadetalle> listaventas;
    VentaDao ventaDao = new VentaDao();
    public ControladorMisVentas(Principal vistaPrincipal, JPanelMisVentas jpanelMisVentas) {
        this.vistaPrincipal = vistaPrincipal;
        this.jpanelMisVentas = jpanelMisVentas;
        vistaPrincipal.jButtonMisVentas.addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vistaPrincipal.jButtonMisVentas) {
            show();
            System.out.println("mis cventaa sssssssssssssssssss" + vistaPrincipal.usuario.getNombre() +  " " +vistaPrincipal.usuario.getNivel());
        }

    }

    public void show() {
        try {
            vistaPrincipal.jPanelPanelPrincipal.removeAll();
            vistaPrincipal.jPanelPanelPrincipal.setLayout(new java.awt.BorderLayout());
            vistaPrincipal.jPanelPanelPrincipal.add(jpanelMisVentas);
            vistaPrincipal.jPanelPanelPrincipal.validate();
            vistaPrincipal.jPanelPanelPrincipal.repaint();
            //muestra panel nuevo producto

            listaventas = ventaDao.getVentasDetalle(new Date(), new Date(), vistaPrincipal.usuario.getNombre(), "");
             BigDecimal ventas = ventaDao.getVentas(new Date(), new Date(), vistaPrincipal.usuario.getNombre(), "");
             jpanelMisVentas.jLabelTotalVentas.setText("" + ventas);
             jpanelMisVentas.jTableVentasDetalle.setModel(llenartabla());
        } catch (Exception ex) {
            Logger.getLogger(ControladorInventario.class.getName()).log(Level.SEVERE, null, ex);
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

}
