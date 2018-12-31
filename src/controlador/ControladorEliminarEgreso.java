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
import vista.reporte.JPanelReporteEgreso;

/**
 *
 * @author mq12
 */
public class ControladorEliminarEgreso implements ActionListener {

    private Fechas fechas = new Fechas();

    JPanelReporteEgreso jPanelReporteEgreso;
    JDialogEliminarEgreso jDialogEliminarEgreso;
    JPanelReportes jpanelReportes;
    Principal vistaPrincipal;
    private List<Egreso> listaEgreso;
    IngresoEgresoDao ingresoEgresoDao = new IngresoEgresoDao();
    private Date desde;
    private Date hasta;

    public ControladorEliminarEgreso(Principal vistaPrincipal, JPanelReportes jpanelReportes, JPanelReporteEgreso jPanelReporteEgreso, JDialogEliminarEgreso jDialogEliminarEgreso) {
        this.jDialogEliminarEgreso = jDialogEliminarEgreso;
        this.vistaPrincipal = vistaPrincipal;
        this.jpanelReportes = jpanelReportes;
        this.jPanelReporteEgreso = jPanelReporteEgreso;
        this.jPanelReporteEgreso.jButtonHoy.addActionListener(this);
        this.jPanelReporteEgreso.jButtonEliminarEgreso.addActionListener(this);
        this.jDialogEliminarEgreso.jButtonEliminar.addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == jPanelReporteEgreso.jButtonEliminarEgreso) {
            jDialogEliminarEgreso.setLocationRelativeTo(null);
            jDialogEliminarEgreso.setVisible(true);
        }
        if (e.getSource() == jDialogEliminarEgreso.jButtonEliminar) {
            eliminar(jDialogEliminarEgreso.jTextFieldIDegreso.getText().trim());
        }
    }

    private void eliminar(String trim) {
        int r = ingresoEgresoDao.borrarEgreso(trim);
        if (r == 1) {
            jDialogEliminarEgreso.setVisible(false);
            JOptionPane.showMessageDialog(null, "Producto " + trim + " Eliminado.");
            jPanelReporteEgreso.jButtonHoy.doClick();

        } else {
            JOptionPane.showMessageDialog(null, "Error al eliminar EGRESO \n", "error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
