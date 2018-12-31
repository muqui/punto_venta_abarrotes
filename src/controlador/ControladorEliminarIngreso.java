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
import javax.swing.JOptionPane;
import modelo.Egreso;
import modelo.Fechas;
import vista.JDialogEgreso;
import vista.JPanelReportes;
import vista.Principal;
import vista.reporte.JDialogEliminarEgreso;
import vista.reporte.JDialogEliminarIngreso;
import vista.reporte.JPanelReporteEgreso;
import vista.reporte.JPanelReporteIngreso;

/**
 *
 * @author mq12
 */
public class ControladorEliminarIngreso implements ActionListener {

    private Fechas fechas = new Fechas();

    JPanelReporteIngreso jPanelReporteIngreso;
    JDialogEliminarIngreso jDialogEliminarIngreso;
    JPanelReportes jpanelReportes;
    Principal vistaPrincipal;
    private List<Egreso> listaEgreso;
    IngresoEgresoDao ingresoEgresoDao = new IngresoEgresoDao();
    private Date desde;
    private Date hasta;

    public ControladorEliminarIngreso(Principal vistaPrincipal, JPanelReportes jpanelReportes, JPanelReporteIngreso jPanelReporteIngreso,  JDialogEliminarIngreso jDialogEliminarIngreso) {
        this.jDialogEliminarIngreso = jDialogEliminarIngreso;
        this.vistaPrincipal = vistaPrincipal;
        this.jpanelReportes = jpanelReportes;
        this.jPanelReporteIngreso = jPanelReporteIngreso;
        this.jPanelReporteIngreso.jButtonHoy.addActionListener(this);
        this.jPanelReporteIngreso.jButtonEliminarIngreso.addActionListener(this);
        this.jDialogEliminarIngreso.jButtonEliminar.addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == jPanelReporteIngreso.jButtonEliminarIngreso) {
            jDialogEliminarIngreso.setLocationRelativeTo(null);
            jDialogEliminarIngreso.setVisible(true);
        }
        if (e.getSource() == jDialogEliminarIngreso.jButtonEliminar) {
            eliminar(jDialogEliminarIngreso.jTextFieldIDIngreso.getText().trim());
        }
    }

    private void eliminar(String trim) {
        int r = ingresoEgresoDao.borrarIngreso(trim);
        if (r == 1) {
            jDialogEliminarIngreso.setVisible(false);
            JOptionPane.showMessageDialog(null, "Producto " + trim + " Eliminado.");
            jPanelReporteIngreso.jButtonHoy.doClick();

        } else {
            JOptionPane.showMessageDialog(null, "Error al eliminar EGRESO \n", "error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
