/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import dao.EliminarVentaDao;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import vista.JPanelReportes;
import vista.Principal;
import vista.reporte.JDialogEliminarVenta;
import vista.reporte.JPanelReporteCompleto;
import vista.reporte.JPanelReporteVentas;

/**
 *
 * @author mq12
 */
public class ControladorEliminarVenta implements ActionListener {

    JDialogEliminarVenta jDialogEliminarVenta;
    Principal vistaPrincipal;
    JPanelReportes jpanelReportes;
    JPanelReporteVentas jPanelReporteVentas;
    EliminarVentaDao eliminarVentaDao;

    public ControladorEliminarVenta(Principal vistaPrincipal, JPanelReportes jpanelReportes, JPanelReporteVentas jPanelReporteVentas, JDialogEliminarVenta jDialogEliminarVenta) {
        this.vistaPrincipal = vistaPrincipal;
        this.jpanelReportes = jpanelReportes;
        this.jPanelReporteVentas = jPanelReporteVentas;
        this.jDialogEliminarVenta = jDialogEliminarVenta;
        this.jPanelReporteVentas.jButtonEliminarVenta.addActionListener(this);
        this.jDialogEliminarVenta.jButtonEliminar.addActionListener(this);
        this.vistaPrincipal.jButtonReporte.addActionListener(this);
        eliminarVentaDao = new EliminarVentaDao();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == jPanelReporteVentas.jButtonEliminarVenta) {
             jDialogEliminarVenta.setLocationRelativeTo(null);
            jDialogEliminarVenta.setVisible(true);
        }
        if (e.getSource() == jDialogEliminarVenta.jButtonEliminar) {
            eliminarVentaDao.eliminarVenta1(Integer.parseInt(jDialogEliminarVenta.jTextFieldIDventa.getText().trim()));
            jDialogEliminarVenta.setVisible(false);
          
            vistaPrincipal.jButtonReporte.doClick();
        }

    }

}
