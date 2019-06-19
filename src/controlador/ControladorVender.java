/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import dao.ProductoDao;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.Tproducto;
import modelo.Usuario;
import modelo.VentasModelo;
import vista.JDialogBuscarProducto;
import vista.JDialogMasDe1Producto;
import vista.JDialogVentaAgranel;
import vista.JDialogVentaFinal;
import vista.JDialogVerificadorPrecios;
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
    JDialogVentaFinal jDialogVentaFinal;
    JDialogVentaAgranel jDialogVentaAgranel;
    JDialogMasDe1Producto jDialogMasDe1Producto;
    JDialogBuscarProducto jDialogBuscarProducto;
    JDialogVerificadorPrecios jDialogVerificadorPrecios;
    ArrayList<JPanelTicket> jPanelTicketArray = new ArrayList<JPanelTicket>();
    ArrayList<DefaultTableModel> defaultTableModelArray = new ArrayList<DefaultTableModel>();
    String[] columnNames = {"CODIGO", "NOMBRE", "PRECIO ", "CANTIDAD", "TOTAL"};
    JPanelTicket jPanelTicket;
    byte numeroDeTicket = 2;
    DefaultTableModel tableModelVentas;
    //ProductoDao productoDao = new ProductoDao();
    VentasModelo ventasModelo;
    boolean precioVentaMenudeo = true; //si es falso el precio es mayoreo.
    List<Tproducto> productos;
    ProductoDao productoDao;

    public ControladorVender(Principal vistaPrincipal, JpanelVentas jpanelVentas, JPanelTicket jPanelTicket, JDialogVentaAgranel jDialogVentaAgranel, JDialogMasDe1Producto jDialogMasDe1Producto, JDialogVentaFinal jDialogVentaFinal, JDialogBuscarProducto jDialogBuscarProducto, JDialogVerificadorPrecios jDialogVerificadorPrecios) {
        this.jDialogVerificadorPrecios = jDialogVerificadorPrecios;
        this.vistaPrincipal = vistaPrincipal;
        this.jpanelVentas = jpanelVentas;
        this.jPanelTicket = jPanelTicket;
        this.jDialogVentaFinal = jDialogVentaFinal;
        this.jDialogVentaAgranel = jDialogVentaAgranel;
        this.jDialogMasDe1Producto = jDialogMasDe1Producto;
        this.jDialogBuscarProducto = jDialogBuscarProducto;
        System.out.println("Usuario " + this.vistaPrincipal.usuario.getNombre());
        ventasModelo = new VentasModelo(this.jDialogVentaAgranel, this.jDialogVentaFinal, this.vistaPrincipal.usuario, this.jPanelTicket, this.jpanelVentas, this.jPanelTicketArray, this.defaultTableModelArray);
        this.jpanelVentas.jButtonCrearTicket.addActionListener(this);
        this.jpanelVentas.jButtonEliminarTicket.addActionListener(this);
        this.jpanelVentas.jButtonCambiarTicket.addActionListener(this);
        this.jpanelVentas.jButtonInsertarMasD1.addActionListener(this);
        this.jpanelVentas.jButtonBuscar.addActionListener(this);
        this.jDialogMasDe1Producto.jButtonAceptar.addActionListener(this);
        this.jDialogBuscarProducto.jButtonAceptar.addActionListener(this);
        this.jDialogBuscarProducto.jButtonCancelar.addActionListener(this);
        this.jDialogBuscarProducto.jTextFieldBuscar.addKeyListener(this);
        this.jpanelVentas.jTextFieldCodigoBarras.addKeyListener(this);
        this.jpanelVentas.jButtonMayoreo.addActionListener(this);
        this.jDialogVerificadorPrecios.jTextFieldCodigoBarras.addKeyListener(this);
        this.jpanelVentas.jButtonAgregarProducto.addActionListener(this);

        this.jDialogMasDe1Producto.jButtonCancelar.addActionListener(this);

        productos = new ArrayList<>();
        jDialogBuscarProducto.jTableProductos.setModel(llenarTabla());
        productoDao = new ProductoDao();
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
        

        if (arg0.getSource() == jpanelVentas.jButtonAgregarProducto) {
             insertarProducto();
        }        
                
        if (arg0.getSource() == jpanelVentas.jButtonMayoreo) {
            ventasMayoreoMenudeo();
        }

        if (arg0.getSource() == jpanelVentas.jButtonInsertarMasD1) {

            mostrarInsertarProductoMasDe1();
        }
        if (arg0.getSource() == jDialogMasDe1Producto.jButtonCancelar) {
            jDialogMasDe1Producto.setVisible(false);

        }
        if (arg0.getSource() == jDialogBuscarProducto.jButtonAceptar) {
            agregarproductodesdeBuscar();
        }
        if (arg0.getSource() == jDialogBuscarProducto.jButtonAceptar) {
            agregarproductodesdeBuscar();
        }
        if (arg0.getSource() == jDialogBuscarProducto.jButtonCancelar) {
            jDialogBuscarProducto.setVisible(false);
        }
        if (arg0.getSource() == jpanelVentas.jButtonBuscar) {
            mostrarJdialogBuscar();
        }
        if (arg0.getSource() == jpanelVentas.jButtonCrearTicket) {
            addTicket();
        }
        if (arg0.getSource() == jpanelVentas.jButtonEliminarTicket) {
            eliminarTicket();
        }
        if (arg0.getSource() == jpanelVentas.jButtonCambiarTicket) {
            cambiarTicket();
        }
        if (arg0.getSource() == jDialogMasDe1Producto.jButtonAceptar) {

            insertarProductoMasDe1();
        }

    }

    @Override
    public void keyTyped(KeyEvent arg0) {

    }

    @Override
    public void keyPressed(KeyEvent ke) {

        if (ke.getSource() == jDialogBuscarProducto.jTextFieldBuscar) {
            int rows = jDialogBuscarProducto.jTableProductos.getRowCount();
            int selected = jDialogBuscarProducto.jTableProductos.getSelectedRow();

            if (ke.getKeyCode() == KeyEvent.VK_UP) {
                if (selected > 0) {
                    jDialogBuscarProducto.jTableProductos.setRowSelectionInterval(selected - 1, selected - 1);
                } else {
                    jDialogBuscarProducto.jTableProductos.setRowSelectionInterval(rows - 1, rows - 1);
                }
            }
            if (ke.getKeyCode() == KeyEvent.VK_DOWN) {
                System.out.println("presionaste tecla abajo total " + rows + " selected: " + selected);

                if (rows > (selected + 1)) {
                    jDialogBuscarProducto.jTableProductos.setRowSelectionInterval(selected + 1, selected + 1);
                } else {
                    jDialogBuscarProducto.jTableProductos.setRowSelectionInterval(0, 0);
                }

            }

        }

        if (ke.getSource() == jDialogVerificadorPrecios.jTextFieldCodigoBarras) {
            if (ke.getKeyCode() == KeyEvent.VK_ENTER) {
                verificadorDeprecios();
            }
            if (ke.getKeyCode() == KeyEvent.VK_ESCAPE) {
                jDialogVerificadorPrecios.setVisible(false);
                jDialogVerificadorPrecios.jLabelDescripcionj.setText("");
                jDialogVerificadorPrecios.jLabelNombre.setText("");
                jDialogVerificadorPrecios.jLabelPrecio.setText("");
            }
        }

        if (ke.getSource() == jpanelVentas.jTextFieldCodigoBarras) {
            if (ke.getKeyCode() == KeyEvent.VK_ENTER) {
                insertarProducto();
            }
            if (ke.getSource() == jpanelVentas.jTextFieldCodigoBarras) {
                if (ke.getKeyCode() == KeyEvent.VK_F4) {
                    jDialogVerificadorPrecios.setLocationRelativeTo(null);
                    jDialogVerificadorPrecios.setVisible(true);
                }

            }

        }
        if (ke.getSource() == jpanelVentas.jTextFieldCodigoBarras) {
            if (ke.getKeyCode() == KeyEvent.VK_F3) {
                ventasMayoreoMenudeo();

            }

        }
        if (ke.getSource() == jpanelVentas.jTextFieldCodigoBarras) {
            if (ke.getKeyCode() == KeyEvent.VK_F2) {
                mostrarInsertarProductoMasDe1();
            }

        }
        if (ke.getSource() == jpanelVentas.jTextFieldCodigoBarras) {
            if (ke.getKeyCode() == KeyEvent.VK_DELETE) {
                eliminarProducto();
            }

        }
        if (ke.getSource() == jpanelVentas.jTextFieldCodigoBarras) {
            if (ke.getKeyCode() == KeyEvent.VK_F12) {
                formaDepago();
            }

        }
        if (ke.getSource() == jpanelVentas.jTextFieldCodigoBarras) {
            if (ke.getKeyCode() == KeyEvent.VK_F1) {
                mostrarJdialogBuscar();
            }

        }
        if (ke.getSource() == jpanelVentas.jTextFieldCodigoBarras) {
            int index = jpanelVentas.jTabbedPaneTickets.getSelectedIndex();
            // jPanelTicketArray.get(index).jTableVender
            int rows = jPanelTicketArray.get(index).jTableVender.getRowCount();
            // int rows = jpanelVentas.jTableVender.getRowCount();
            int selected = jPanelTicketArray.get(index).jTableVender.getSelectedRow();
            if (ke.getKeyCode() == KeyEvent.VK_UP) {
                if (selected > 0) {
                    jPanelTicketArray.get(index).jTableVender.setRowSelectionInterval(selected - 1, selected - 1);
                } else {
                    jPanelTicketArray.get(index).jTableVender.setRowSelectionInterval(rows - 1, rows - 1);
                }
            }

            if (ke.getKeyCode() == KeyEvent.VK_DOWN) {
                System.out.println("presionaste tecla abajo total " + rows + " selected: " + selected);
                // jDialogBuscarProducto.jTableProductos.setRowSelectionInterval((selected+1), 0);
                if (rows > (selected + 1)) {
                    jPanelTicketArray.get(index).jTableVender.setRowSelectionInterval(selected + 1, selected + 1);
                } else {
                    jPanelTicketArray.get(index).jTableVender.setRowSelectionInterval(0, 0);
                }

            }

        }
    }

    @Override
    public void keyReleased(KeyEvent arg0) {

        if (arg0.getSource() == jDialogBuscarProducto.jTextFieldBuscar) {
            if (arg0.getKeyCode() == KeyEvent.VK_ESCAPE) {
                jDialogBuscarProducto.setVisible(false);
            }

            if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
                agregarproductodesdeBuscar();

            }

            if (arg0.getKeyCode() != KeyEvent.VK_UP && arg0.getKeyCode() != KeyEvent.VK_DOWN) {
                if (!jDialogBuscarProducto.jTextFieldBuscar.getText().equals("")) {
                    productosPorNombre(jDialogBuscarProducto.jTextFieldBuscar.getText());
                    System.out.println("buscar producto");
                }

            }

        }

    }

    private void insertarProducto() {
        int index = jpanelVentas.jTabbedPaneTickets.getSelectedIndex();
        DefaultTableModel d = ventasModelo.productos(jpanelVentas.jTextFieldCodigoBarras.getText(), precioVentaMenudeo, jPanelTicketArray.get(index).jTableVender, defaultTableModelArray.get(index), new BigDecimal("1"), false);
        jPanelTicketArray.get(index).jTableVender.setModel(d);
        jpanelVentas.jLabelTotal.setText("$ " + ventasModelo.totalTicket(d));
        jpanelVentas.jTextFieldCodigoBarras.setText("");

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

    private void insertarProductoMasDe1() {
        int index = jpanelVentas.jTabbedPaneTickets.getSelectedIndex();
        DefaultTableModel d = ventasModelo.productos(jDialogMasDe1Producto.jTextFieldCodigoBarras.getText(), precioVentaMenudeo, jPanelTicketArray.get(index).jTableVender, defaultTableModelArray.get(index), new BigDecimal(jDialogMasDe1Producto.jTextFieldCantidad.getText()), true);
        jPanelTicketArray.get(index).jTableVender.setModel(d);

        System.out.println("click insertar mas de un producto  SE OCULTA EL DIALOGO");
        jpanelVentas.jLabelTotal.setText("$ " + ventasModelo.totalTicket(d));
        jpanelVentas.jTextFieldCodigoBarras.setText("");
        jDialogMasDe1Producto.setVisible(false);
    }

    private void mostrarInsertarProductoMasDe1() {
        jDialogMasDe1Producto.pack();
        jDialogMasDe1Producto.setLocationRelativeTo(null);
        jDialogMasDe1Producto.setVisible(true);

    }

    private void eliminarProducto() {
        int index = jpanelVentas.jTabbedPaneTickets.getSelectedIndex();
        System.out.println("pressed delete");
        int row = jPanelTicketArray.get(index).jTableVender.getSelectedRow();

        defaultTableModelArray.get(index).removeRow(row);
        jPanelTicketArray.get(index).jTableVender.setModel(defaultTableModelArray.get(index));

        jpanelVentas.jLabelTotal.setText("$ " + ventasModelo.totalTicket(defaultTableModelArray.get(index)));
        jPanelTicketArray.get(index).jTableVender.setRowSelectionInterval(0, 0);
    }

    private void formaDepago() {
        int index = jpanelVentas.jTabbedPaneTickets.getSelectedIndex();
        ventasModelo.formaDePago(defaultTableModelArray.get(index));

    }

    private void mostrarJdialogBuscar() {
        jDialogBuscarProducto.setLocationRelativeTo(null);
        jDialogBuscarProducto.setVisible(true);
    }

    private void agregarproductodesdeBuscar() {
        if (jDialogBuscarProducto.jTableProductos.getRowCount() > 0) {
            System.out.println("line___________________ " + jDialogBuscarProducto.jTableProductos.getSelectedRow());
            int index = jpanelVentas.jTabbedPaneTickets.getSelectedIndex();
            DefaultTableModel d = ventasModelo.productos("" + jDialogBuscarProducto.jTableProductos.getValueAt(jDialogBuscarProducto.jTableProductos.getSelectedRow(), 0), precioVentaMenudeo, jPanelTicketArray.get(index).jTableVender, defaultTableModelArray.get(index), new BigDecimal("1"), false);
            jPanelTicketArray.get(index).jTableVender.setModel(d);
            jpanelVentas.jLabelTotal.setText("$ " + ventasModelo.totalTicket(d));
            jpanelVentas.jTextFieldCodigoBarras.setText("");
            jDialogBuscarProducto.setVisible(false);

        }

    }

    private void productosPorNombre(String text) {
        try {

            productos = productoDao.getPorNombre(text);

            jDialogBuscarProducto.jTableProductos.setModel(llenarTabla());
            jDialogBuscarProducto.jTableProductos.setRowSelectionInterval(0, 0);
            productoDao.cerrar();
        } catch (Exception ex) {
            Logger.getLogger(VentasModelo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public DefaultTableModel llenarTabla() {
        DefaultTableModel tableModel = new DefaultTableModel() {

            @Override
            public boolean isCellEditable(int row, int column) {
                //all cells false
                return false;
            }
        };

        String[] columnNames = {"CODIGO", "NOMBRE", "DEPARTAMENTO", "PRECIO", "CANTIDAD"};
        tableModel.setColumnIdentifiers(columnNames);
        Object[] fila = new Object[tableModel.getColumnCount()];

        for (int i = 0; i < productos.size(); i++) {

            fila[0] = productos.get(i).getCodigoBarras();
            fila[1] = productos.get(i).getNombre();
            fila[2] = productos.get(i).getDepartamento().getNombre();
            fila[3] = productos.get(i).getPrecioVentaUnitario();
            fila[4] = productos.get(i).getCantidad();
            tableModel.addRow(fila);

        }
        return tableModel;
    }

    private void verificadorDeprecios() {

        Tproducto p = productoDao.getByCodigoBarras(jDialogVerificadorPrecios.jTextFieldCodigoBarras.getText().trim());
        if (p == null) {
            jDialogVerificadorPrecios.jLabelNombre.setText("Nombre: ");
            jDialogVerificadorPrecios.jLabelDescripcionj.setText("Descripción: ");
            jDialogVerificadorPrecios.jLabelPrecio.setText("Precio: $ ");
            JOptionPane.showMessageDialog(null, "Codigo de barras no existe", "error", JOptionPane.ERROR_MESSAGE);

        } else {
            jDialogVerificadorPrecios.jLabelNombre.setText("Nombre: " + p.getNombre());
            jDialogVerificadorPrecios.jLabelDescripcionj.setText("Descripción: " + p.getDescripcion());
            jDialogVerificadorPrecios.jLabelPrecio.setText("Precio: $ " + p.getPrecioVentaUnitario());
        }

//
//            jDialogBuscarProducto.jTableProductos.setModel(llenarTabla());
//            jDialogBuscarProducto.jTableProductos.setRowSelectionInterval(0, 0);
        System.out.println("verificadpr de ´precios " + p);
        //  productoDao.cerrar();
    }

    private void ventasMayoreoMenudeo() {
        int index = jpanelVentas.jTabbedPaneTickets.getSelectedIndex();
        System.out.println(" cantidad ticket " + defaultTableModelArray.get(index).getRowCount());
        if (defaultTableModelArray.get(index).getRowCount() <= 0) {
           
            if (precioVentaMenudeo) {
                 precioVentaMenudeo = false;
                jpanelVentas.jLabelMayoreio.setVisible(true);
                jpanelVentas.jLabelMayoreio.setText("Ventas de mayoreo activado");
            } else {
                precioVentaMenudeo = true;
                jpanelVentas.jLabelMayoreio.setVisible(false);

            }
        } else {
            JOptionPane.showMessageDialog(null, "Primero vacie el ticket", "error", JOptionPane.ERROR_MESSAGE);
        }

        System.out.println("precio mayoreo " + precioVentaMenudeo);
    }
}
