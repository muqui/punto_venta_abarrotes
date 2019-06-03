/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import vista.JPanelTicket;
import vista.JpanelVentas;
import vista.Principal;

/**
 *
 * @author mq12
 */
public class ControladorVender implements ActionListener {

    Principal vistaPrincipal;
    JpanelVentas jpanelVentas;
    JPanelTicket jPanelTicket;
    byte numeroDeTicket = 1;

    public ControladorVender(Principal vistaPrincipal, JpanelVentas jpanelVentas) {
        this.vistaPrincipal = vistaPrincipal;
        this.jpanelVentas = jpanelVentas;

        jpanelVentas.jButtonCrearTicket.addActionListener(this);
        jpanelVentas.jButtonEliminarTicket.addActionListener(this);
        jpanelVentas.jButtonCambiarTicket.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        if (arg0.getSource() == jpanelVentas.jButtonCrearTicket) {
            addTicket();
        }
        if (arg0.getSource() == jpanelVentas.jButtonEliminarTicket) {
            eliminarTicket();
        }
        if (arg0.getSource() == jpanelVentas.jButtonCambiarTicket) {
            cambiarTicket();
        }
    }

    private void addTicket() {
        //Limite maximo de tickets es 10
        if (numeroDeTicket < 11) {
            String nombre = "Ticket " + (numeroDeTicket++);

            jpanelVentas.jTabbedPaneTickets.add(nombre, new JPanelTicket());
        }

    }

    private void eliminarTicket() {
        byte eliminar = (byte) (numeroDeTicket - 2);
        System.out.println("Eliminar " + eliminar + " numero " + numeroDeTicket);
        jpanelVentas.jTabbedPaneTickets.remove(eliminar);
        numeroDeTicket--;
    }

    private void cambiarTicket() {
        byte seleccionado = (byte) jpanelVentas.jTabbedPaneTickets.getSelectedIndex();

        if (seleccionado == (jpanelVentas.jTabbedPaneTickets.getTabCount() - 1)) {
            seleccionado = 0;
        } else {
            seleccionado++;
        }

        jpanelVentas.jTabbedPaneTickets.setSelectedIndex(seleccionado);
      
    }

}
