/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;
//import dao.DepartamentoDao;
import dao.IngresoEgresoDao;
import dao.ReporteCompletoDao;
import dao.VentaDao;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import modelo.Departamento;
import modelo.Movimiento;
import modelo.Reporte;
import vista.JPanelReportes;
import vista.Principal;
import vista.reporte.JPanelReporteCompleto;
/**
 *
 * @author mq12
 */
public class ControladorReporteCompleto implements ActionListener {

    Principal vistaPrincipal;
    JPanelReportes jpanelReportes;
    JPanelReporteCompleto jPanelReporteCompleto;
    IngresoEgresoDao ingresoEgresoDao = new IngresoEgresoDao();
   
    ReporteCompletoDao reporteCompletoDao = new ReporteCompletoDao();
    Date desde = new Date();
    Date hasta = new Date();

    public ControladorReporteCompleto(Principal vistaPrincipal, JPanelReportes jpanelReportes, JPanelReporteCompleto jPanelReporteCompleto) {
        this.vistaPrincipal = vistaPrincipal;
        this.jpanelReportes = jpanelReportes;
        this.jPanelReporteCompleto = jPanelReporteCompleto;
        this.jpanelReportes.jButtonCompleto.addActionListener(this);
        this.jPanelReporteCompleto.jButtonMostrar.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == jPanelReporteCompleto.jButtonMostrar) {
            reporte();
        }
        if (e.getSource() == jpanelReportes.jButtonCompleto) {
            try {
                show();
            } catch (Exception ex) {
                Logger.getLogger(ControladorReporteCompleto.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void show() throws Exception {
        jpanelReportes.jPanelPrincipal.removeAll();
        jpanelReportes.jPanelPrincipal.setLayout(new java.awt.BorderLayout());
        jpanelReportes.jPanelPrincipal.add(jPanelReporteCompleto);
        jpanelReportes.jPanelPrincipal.validate();
        jpanelReportes.jPanelPrincipal.repaint();
        jPanelReporteCompleto.jTableReporteCompleto.setModel(llenartabla());
    }

    private DefaultTableModel llenartabla() throws Exception {

        DefaultTableModel tableModel = new DefaultTableModel() {

            @Override
            public boolean isCellEditable(int row, int column) {

                return false;
            }
        };
        List<Departamento> departamento = reporteCompletoDao.getDepartamento();
        List<Movimiento> ingreso = ingresoEgresoDao.getMovimientos("ingreso");
        List<Movimiento> egreso = ingresoEgresoDao.getMovimientos("egreso");
        List<Reporte> reporte = new ArrayList<>();
        int ingresoT = ingreso.size() + departamento.size();
        int egresoT = egreso.size();
        int t = 0;
        if (ingresoT >= egresoT) {
            t = ingresoT;
        } else {
            t = egresoT;
        }
        String[] columnNames = {"INGRESO", "CANTIDAD", "EGRESO", "CANTIDAD", "TOTAL"};
        tableModel.setColumnIdentifiers(columnNames);
        Object[] fila = new Object[tableModel.getColumnCount()];
        int contadorIngreso = 0;
        int contadorDepartamento = ingreso.size();

        for (int i = 0; i < t; i++) {
            Reporte r = new Reporte();

            if (ingresoT > i) {
                if (contadorIngreso < ingreso.size()) {
                    r.setIngreso(ingreso.get(i).getNombre());
                    r.setCantidadIngreso(ingresoEgresoDao.getSumaIngresos(desde, hasta, r.getIngreso()));
                   
                    for (int j = 0; j < egreso.size(); j++) {
                        if (r.getIngreso().equalsIgnoreCase(egreso.get(j).getNombre())) {
                            r.setEgreso(egreso.get(j).getNombre());
                         
                            egreso.remove(j);
                            break;

                        }
                    }
                    contadorIngreso++;
                   
                } else {
                    r.setIngreso(departamento.get(i - contadorDepartamento).getNombre());
                   
                    r.setCantidadIngreso(reporteCompletoDao.getVentasTotalModificado(desde, hasta, "", r.getIngreso()));
                    System.out.println("alex1 " +r.getIngreso() +  " " + r.getCantidadIngreso());
                    for (int j = 0; j < egreso.size(); j++) {
                        if (r.getIngreso().equalsIgnoreCase(egreso.get(j).getNombre())) {
                            r.setEgreso(egreso.get(j).getNombre());
                            egreso.remove(j);
                            break;
                        }
                    }

                }

            }
            reporte.add(r);
        }

        for (int j = 0; j < egreso.size(); j++) {
            Reporte r = new Reporte();
            r.setEgreso(egreso.get(j).getNombre());
            //  System.out.println("RESTANTE " + egreso.get(j).getNombre());
            reporte.add(r);
        }
        for (Reporte re : reporte) {
            re.setCantidadEgreso(ingresoEgresoDao.getSumaEgresos(desde, hasta, re.getEgreso()));
            
            fila[0] = re.getIngreso();
            fila[1] = re.getCantidadIngreso();
            fila[2] = re.getEgreso();
            fila[3] = re.getCantidadEgreso();
            re.setTotal(re.getCantidadIngreso().subtract(re.getCantidadEgreso()));
            fila[4] = re.getTotal();

            
            tableModel.addRow(fila);
            
        }

        return tableModel;
    }

    private void reporte() {
        try {
            desde = jPanelReporteCompleto.jDateChooserDesde.getDate();
            hasta = jPanelReporteCompleto.jDateChooserHasta.getDate();
            BigDecimal total = new BigDecimal("0.00");
            BigDecimal ingreso = ingresoEgresoDao.getSumaIngresos(desde, hasta);
            BigDecimal egreso = ingresoEgresoDao.getSumaEgresos(desde, hasta);
            BigDecimal ventas = reporteCompletoDao.getVentas(desde, hasta, "", "");
            System.out.println("abcdefg j" + ventas);
            total = ingreso.add(ventas);
            total = total.subtract(egreso);
            jPanelReporteCompleto.jLabelTotalVentas.setText("" + total);
            jPanelReporteCompleto.jTableReporteCompleto.setModel(llenartabla());
            System.out.println("");
        } catch (Exception ex) {
            Logger.getLogger(ControladorReporteCompleto.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
