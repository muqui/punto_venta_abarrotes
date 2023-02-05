/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import dao.IngresoEgresoDao;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import modelo.Egreso;
import modelo.Fechas;
import modelo.Ingreso;
import vista.JDialogEgreso;
import vista.JPanelReportes;
import vista.Principal;
import vista.reporte.JPanelReporteEgreso;
import vista.reporte.JPanelReporteIngreso;

/**
 *
 * @author mq12
 */

public class ControladorMostrarEgresos implements ActionListener {

    private Fechas fechas = new Fechas();
    JDialogEgreso jDialogEgreso;
    JPanelReporteEgreso jPanelReporteEgreso;
    JPanelReportes jpanelReportes;
    Principal vistaPrincipal;
    private List<Egreso> listaEgreso;
    IngresoEgresoDao ingresoEgresoDao = new IngresoEgresoDao();
    private Date desde;
    private Date hasta;

    public ControladorMostrarEgresos(Principal vistaPrincipal, JPanelReportes jpanelReportes, JPanelReporteEgreso jPanelReporteEgreso, JDialogEgreso jDialogEgreso) {
        this.jDialogEgreso = jDialogEgreso;
        this.vistaPrincipal = vistaPrincipal;
        this.jpanelReportes = jpanelReportes;
        this.jPanelReporteEgreso = jPanelReporteEgreso;
        this.jpanelReportes.jButtonEgreso.addActionListener(this);
        this.jPanelReporteEgreso.jButtonBuscar.addActionListener(this);
        this.jPanelReporteEgreso.jButtonDiaAnterior.addActionListener(this);
        this.jPanelReporteEgreso.jButtonHoy.addActionListener(this);

        this.jPanelReporteEgreso.jDateChooserDesde.setDate(new Date());
        this.jPanelReporteEgreso.jDateChooserHasta.setDate(new Date());
        this.jPanelReporteEgreso.jButtonCapturarEgreso.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == jPanelReporteEgreso.jButtonCapturarEgreso) {
            mostrarDialogEgreso();
        }
        if (e.getSource() == jPanelReporteEgreso.jButtonHoy) {
            hoy();
        }
        if (e.getSource() == jPanelReporteEgreso.jButtonDiaAnterior) {
            try {
                diaAnterior();
            } catch (Exception ex) {
                Logger.getLogger(ControladorMostrarEgresos.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (e.getSource() == jPanelReporteEgreso.jButtonBuscar) {
            try {
                listaEgreso = ingresoEgresoDao.getEgresos(jPanelReporteEgreso.jDateChooserDesde.getDate(), jPanelReporteEgreso.jDateChooserHasta.getDate());
                jPanelReporteEgreso.jTableEgresos.setModel(llenarTablaIngreso());
                jPanelReporteEgreso.jLabelEgreso.setText("Egresos del dia " + fechas.rangoFecha(jPanelReporteEgreso.jDateChooserDesde.getDate(), jPanelReporteEgreso.jDateChooserHasta.getDate()));
                jPanelReporteEgreso.jLabelTotal.setText("" + ingresoEgresoDao.getSumaEgresos(jPanelReporteEgreso.jDateChooserDesde.getDate(), jPanelReporteEgreso.jDateChooserHasta.getDate()));
                // ingresoEgresoDao.cerrar();
            } catch (Exception ex) {
                Logger.getLogger(ControladorMostrarEgresos.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (e.getSource() == jpanelReportes.jButtonEgreso) {
            show();
        }

    }

    public void show() {
        try {

            jpanelReportes.jPanelPrincipal.removeAll();
            jpanelReportes.jPanelPrincipal.setLayout(new java.awt.BorderLayout());
            jpanelReportes.jPanelPrincipal.add(jPanelReporteEgreso);
            jpanelReportes.jPanelPrincipal.validate();
            jpanelReportes.jPanelPrincipal.repaint();
            hoy();

        } catch (Exception ex) {
            Logger.getLogger(ControladorReportes.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public DefaultTableModel llenarTablaIngreso() {
        DefaultTableModel tableModel = new DefaultTableModel() {

            @Override
            public boolean isCellEditable(int row, int column) {
                //all cells false
                return false;
            }
        };

        String[] columnNames = {"ID", "FECHA", "MOVIMIENTO", "DESCRIPCION", "CANTIDAD"};
        tableModel.setColumnIdentifiers(columnNames);
        Object[] fila = new Object[tableModel.getColumnCount()];

        for (int i = 0; i < listaEgreso.size(); i++) {
            fila[0] = listaEgreso.get(i).getId();
            fila[1] = listaEgreso.get(i).getFecha();
            fila[2] = listaEgreso.get(i).getNombre();
            fila[3] = listaEgreso.get(i).getDescripcion();
            fila[4] = listaEgreso.get(i).getCantidad();

            tableModel.addRow(fila);

        }
        return tableModel;
    }

    private void diaAnterior() throws Exception {
        desde = fechas.ayer();
        hasta = fechas.ayer();
        listaEgreso = ingresoEgresoDao.getEgresos(desde, hasta);
        jPanelReporteEgreso.jTableEgresos.setModel(llenarTablaIngreso());
        jPanelReporteEgreso.jLabelEgreso.setText("Egresos del dia " + fechas.fecha(hasta));
        jPanelReporteEgreso.jLabelTotal.setText("" + ingresoEgresoDao.getSumaEgresos(desde, hasta));
        // ingresoEgresoDao.cerrar();
        fechas.ayer--;
    }

    private void hoy() {
        try {
            fechas.ayer = -1;
            jPanelReporteEgreso.jLabelEgreso.setText("Egresos del dia " + fechas.fecha(new Date()));
            listaEgreso = ingresoEgresoDao.getEgresos(new Date(), new Date());
            //jPanelReporteEgreso.jLabelTotal.setText("" + ingresoEgresoDao.getSumaEgresos(new Date(), new Date()));
            jPanelReporteEgreso.jLabelTotal.setText("" + ingresoEgresoDao.getTotalEgreso());
            jPanelReporteEgreso.jTableEgresos.setModel(llenarTablaIngreso());
            // ingresoEgresoDao.cerrar();
        } catch (Exception ex) {
            Logger.getLogger(ControladorMostrarEgresos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void mostrarDialogEgreso() {
        jDialogEgreso.setLocationRelativeTo(null);
        jDialogEgreso.setVisible(true);
    }

}
