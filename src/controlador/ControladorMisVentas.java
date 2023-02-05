/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import MYSQL.ConnectionMYSQLManager;
import dao.MisVentasDao;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.List;
import modelo.Tventadetalle;
import vista.Principal;
import vista.reporte.JPanelMisVentas;

/**
 *
 * @author mq12
 */
public class ControladorMisVentas implements ActionListener {
    ConnectionMYSQLManager con = new ConnectionMYSQLManager();
    Principal vistaPrincipal;
    JPanelMisVentas jpanelMisVentas;
    private List<Tventadetalle> listaventas;
     MisVentasDao misVentasDao = new MisVentasDao();

    public ControladorMisVentas(Principal vistaPrincipal, JPanelMisVentas jpanelMisVentas) {
        this.vistaPrincipal = vistaPrincipal;
        this.jpanelMisVentas = jpanelMisVentas;
        vistaPrincipal.jButtonMisVentas.addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vistaPrincipal.jButtonMisVentas) {
            show();

        }

    }

    public void show() {
        BigDecimal total = new BigDecimal("0.00");
        try {
            
            vistaPrincipal.jPanelPanelPrincipal.removeAll();
            vistaPrincipal.jPanelPanelPrincipal.setLayout(new java.awt.BorderLayout());
            vistaPrincipal.jPanelPanelPrincipal.add(jpanelMisVentas);
            vistaPrincipal.jPanelPanelPrincipal.validate();
            vistaPrincipal.jPanelPanelPrincipal.repaint();

        } catch (Exception ex) {
            System.out.println("error " + ex);
           }
        jpanelMisVentas.jTableVentasDetalle.setModel(misVentasDao.getVentasDetalle(vistaPrincipal.usuario.getNombre()));
        jpanelMisVentas.jLabelTotalVentas.setText("" + misVentasDao.getTotal());

    }



}
