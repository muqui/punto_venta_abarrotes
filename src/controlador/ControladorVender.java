/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import modelo.VentasModelo;
import vista.JDialogVentaAgranel;
import vista.JPanelTicket;
import vista.JpanelVentas;
import vista.Principal;

/**
 *
 * @author mq12
 */
public class ControladorVender implements ActionListener, KeyListener {

    Principal vistaPrincipal;
    JpanelVentas jpanelVentas;
    JDialogVentaAgranel jDialogVentaAgranel;
    ArrayList<JPanelTicket> jPanelTicketArray = new ArrayList<JPanelTicket>();
    ArrayList<DefaultTableModel> defaultTableModelArray = new ArrayList<DefaultTableModel>();
    String[] columnNames = {"CODIGO", "NOMBRE", "PRECIO ", "CANTIDAD", "TOTAL"};
    JPanelTicket jPanelTicket;
    byte numeroDeTicket = 2;
    DefaultTableModel tableModelVentas;
    //ProductoDao productoDao = new ProductoDao();
    VentasModelo ventasModelo;
    boolean precioVentaMenudeo = true; //si es falso el precio es mayoreo.

    public ControladorVender(Principal vistaPrincipal, JpanelVentas jpanelVentas, JPanelTicket jPanelTicket, JDialogVentaAgranel jDialogVentaAgranel) {
        this.vistaPrincipal = vistaPrincipal;
        this.jpanelVentas = jpanelVentas;
        this.jPanelTicket = jPanelTicket;
        this.jDialogVentaAgranel = jDialogVentaAgranel;
        ventasModelo = new VentasModelo(jDialogVentaAgranel);
        jpanelVentas.jButtonCrearTicket.addActionListener(this);
        jpanelVentas.jButtonEliminarTicket.addActionListener(this);
        jpanelVentas.jButtonCambiarTicket.addActionListener(this);
        jpanelVentas.jTextFieldCodigoBarras.addKeyListener(this);

        jPanelTicketArray.add(new JPanelTicket());
        jpanelVentas.jTabbedPaneTickets.add("Ticket 1", jPanelTicketArray.get(0));
        defaultTableModelArray.add(tableModelVentas = new DefaultTableModel() {

            @Override
            public boolean isCellEditable(int row, int column) {
                //all cells false
                return false;
            }
        });
        defaultTableModelArray.get(0).setColumnIdentifiers(columnNames);
        jPanelTicketArray.get(0).jTableVender.setModel(defaultTableModelArray.get(0));
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

    @Override
    public void keyTyped(KeyEvent arg0) {

    }

    @Override
    public void keyPressed(KeyEvent ke) {
        if (ke.getSource() == jpanelVentas.jTextFieldCodigoBarras) {
            if (ke.getKeyCode() == KeyEvent.VK_ENTER) {
                insertarProducto();
            }

        }
    }

    @Override
    public void keyReleased(KeyEvent arg0) {

    }

    private void insertarProducto() {
        int index = jpanelVentas.jTabbedPaneTickets.getSelectedIndex();

        DefaultTableModel d = ventasModelo.productos(jpanelVentas.jTextFieldCodigoBarras.getText(), precioVentaMenudeo, jPanelTicketArray.get(index).jTableVender, defaultTableModelArray.get(index));
        jPanelTicketArray.get(index).jTableVender.setModel(d);

    }

    private void addTicket() {
        //Limite maximo de tickets es 10
        if (numeroDeTicket < 11) {
            String nombre = "Ticket " + (numeroDeTicket++);

            byte indice = (byte) (numeroDeTicket - 2);
            System.out.println("crear ticket numero: " + numeroDeTicket + "indice  " + indice);

            jPanelTicketArray.add(new JPanelTicket());
            defaultTableModelArray.add(new DefaultTableModel() {

                @Override
                public boolean isCellEditable(int row, int column) {
                    //all cells false
                    return false;
                }
            });
            defaultTableModelArray.get(indice).setColumnIdentifiers(columnNames);
            jpanelVentas.jTabbedPaneTickets.add(nombre, jPanelTicketArray.get(indice));
            jPanelTicketArray.get(indice).jTableVender.setModel(defaultTableModelArray.get(indice));

        }

    }

    private void eliminarTicket() {
        if (numeroDeTicket > 2) {
            byte eliminar = (byte) (numeroDeTicket - 2);
            System.out.println("Eliminar " + eliminar + " numero " + numeroDeTicket);
            jpanelVentas.jTabbedPaneTickets.remove(eliminar);
            defaultTableModelArray.remove(eliminar);
            jPanelTicketArray.remove(eliminar);
            numeroDeTicket--;
        }

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
