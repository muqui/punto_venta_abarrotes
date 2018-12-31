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
import modelo.Fechas;
import modelo.Ingreso;
import vista.JDialogIngreso;
import vista.JPanelReportes;
import vista.Principal;
import vista.reporte.JPanelReporteIngreso;

/**
 *
 * @author mq12
 */
public class ControladorMostrarIngresos implements ActionListener {

    private Fechas fechas = new Fechas();
     JDialogIngreso jDialogIngreso;
    JPanelReporteIngreso jPanelReporteIngreso;
    JPanelReportes jpanelReportes;
    Principal vistaPrincipal;
    private List<Ingreso> listaingreso;
    IngresoEgresoDao ingresoEgresoDao = new IngresoEgresoDao();
    private Date desde;
    private Date hasta;

    public ControladorMostrarIngresos(Principal vistaPrincipal, JPanelReportes jpanelReportes, JPanelReporteIngreso jPanelReporteIngreso ,  JDialogIngreso jDialogIngreso) {
        this.jDialogIngreso = jDialogIngreso;
        this.vistaPrincipal = vistaPrincipal;
        this.jpanelReportes = jpanelReportes;
        this.jPanelReporteIngreso = jPanelReporteIngreso;
        this.jpanelReportes.jButtonIngreso.addActionListener(this);
        this.jPanelReporteIngreso.jButtonBuscar.addActionListener(this);
        this.jPanelReporteIngreso.jButtonDiaAnterior.addActionListener(this);
        this.jPanelReporteIngreso.jButtonHoy.addActionListener(this);
        this.jPanelReporteIngreso.jDateChooserDesde.setDate(new Date());
        this.jPanelReporteIngreso.jDateChooserHasta.setDate(new Date());
        this.jPanelReporteIngreso.jButtonCapturarIngreso.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == jPanelReporteIngreso.jButtonCapturarIngreso){
            mostrarDialogIngreso();
        }
        if (e.getSource() == jPanelReporteIngreso.jButtonHoy) {
            hoy();
        }
        if (e.getSource() == jPanelReporteIngreso.jButtonDiaAnterior) {
            try {
                diaAnterior();
            } catch (Exception ex) {
                Logger.getLogger(ControladorMostrarIngresos.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (e.getSource() == jPanelReporteIngreso.jButtonBuscar) {
            try {
                listaingreso = ingresoEgresoDao.getIngresos(jPanelReporteIngreso.jDateChooserDesde.getDate(), jPanelReporteIngreso.jDateChooserHasta.getDate());
                jPanelReporteIngreso.jTableIngresos.setModel(llenarTablaIngreso());
                jPanelReporteIngreso.jLabelIngresos.setText("Ingresos del dia " + fechas.rangoFecha(jPanelReporteIngreso.jDateChooserDesde.getDate(), jPanelReporteIngreso.jDateChooserHasta.getDate()));
                jPanelReporteIngreso.jLabelTotalVentas.setText("" + ingresoEgresoDao.getSumaIngresos(jPanelReporteIngreso.jDateChooserDesde.getDate(), jPanelReporteIngreso.jDateChooserHasta.getDate()));
               // ingresoEgresoDao.cerrar();
            } catch (Exception ex) {
                Logger.getLogger(ControladorMostrarIngresos.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (e.getSource() == jpanelReportes.jButtonIngreso) {
            show();
        }

    }

    public void show() {
        try {
            jpanelReportes.jPanelPrincipal.removeAll();
            jpanelReportes.jPanelPrincipal.setLayout(new java.awt.BorderLayout());
            jpanelReportes.jPanelPrincipal.add(jPanelReporteIngreso);
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

        String[] columnNames = {"ID","FECHA", "MOVIMIENTO", "DESCRIPCION", "CANTIDAD"};
        tableModel.setColumnIdentifiers(columnNames);
        Object[] fila = new Object[tableModel.getColumnCount()];

        for (int i = 0; i < listaingreso.size(); i++) {
            fila[0] = listaingreso.get(i).getId();
            fila[1] = listaingreso.get(i).getFecha();
            fila[2] = listaingreso.get(i).getNombre();
            fila[3] = listaingreso.get(i).getDescrpcion();
            fila[4] = listaingreso.get(i).getCantidad();

            tableModel.addRow(fila);

        }
        return tableModel;
    }

    private void diaAnterior() throws Exception {
        desde = fechas.ayer();
        hasta = fechas.ayer();
        listaingreso = ingresoEgresoDao.getIngresos(desde, hasta);
        jPanelReporteIngreso.jTableIngresos.setModel(llenarTablaIngreso());
        jPanelReporteIngreso.jLabelIngresos.setText("Ingresos del dia " + fechas.fecha(hasta));
        jPanelReporteIngreso.jLabelTotalVentas.setText("" + ingresoEgresoDao.getSumaIngresos(desde, hasta));
       // ingresoEgresoDao.cerrar();
        fechas.ayer--;
    }

    private void hoy() {
        try {
            fechas.ayer = -1;
            jPanelReporteIngreso.jLabelIngresos.setText("Ingresos del dia " + fechas.fecha(new Date()));
            listaingreso = ingresoEgresoDao.getIngresos(new Date(), new Date());
            jPanelReporteIngreso.jLabelTotalVentas.setText("" + ingresoEgresoDao.getSumaIngresos(new Date(), new Date()));
            jPanelReporteIngreso.jTableIngresos.setModel(llenarTablaIngreso());
           // ingresoEgresoDao.cerrar();
        } catch (Exception ex) {
            Logger.getLogger(ControladorMostrarIngresos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void mostrarDialogIngreso() {
        jDialogIngreso.setLocationRelativeTo(null);
        jDialogIngreso.setVisible(true);
    }

}
