/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import dao.ProductoDao;
import dao.VerificarPrecioDao;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.Tproducto;
import modelo.Usuario;
import java.util.TimerTask;
import modelo.VentasModelo;
import vista.JDialogBuscarProducto;
import vista.JDialogMasDe1Producto;
import vista.JDialogVentaAgranel;
import vista.JDialogVentaFinal;
import vista.JDialogVerificadorPrecios;
import vista.JPanelTicket;
import vista.JpanelVentas;
import vista.Principal;
import java.util.TimerTask;
import java.util.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

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
    String[] columnNames = {"CODIGO", "NOMBRE", "PRECIO ", "CANTIDAD", "TOTAL", ""};
    JPanelTicket jPanelTicket;
    byte numeroDeTicket = 2;
    DefaultTableModel tableModelVentas;
    //ProductoDao productoDao = new ProductoDao();
    VentasModelo ventasModelo;
    boolean precioVentaMenudeo = true; //si es falso el precio es mayoreo.
    List<Tproducto> productos;
    ProductoDao productoDao;
    Timer timer = new Timer();
    BigDecimal total;

    public ControladorVender(Principal vistaPrincipal, JpanelVentas jpanelVentas, JPanelTicket jPanelTicket, JDialogVentaAgranel jDialogVentaAgranel, JDialogMasDe1Producto jDialogMasDe1Producto, JDialogVentaFinal jDialogVentaFinal, JDialogBuscarProducto jDialogBuscarProducto, JDialogVerificadorPrecios jDialogVerificadorPrecios) {
        this.jDialogVerificadorPrecios = jDialogVerificadorPrecios;
        this.vistaPrincipal = vistaPrincipal;
        this.jpanelVentas = jpanelVentas;
        this.jPanelTicket = jPanelTicket;
        this.jDialogVentaFinal = jDialogVentaFinal;
        this.jDialogVentaAgranel = jDialogVentaAgranel;
        this.jDialogMasDe1Producto = jDialogMasDe1Producto;
        this.jDialogBuscarProducto = jDialogBuscarProducto;

        ventasModelo = new VentasModelo(this.jDialogVentaAgranel, this.jDialogVentaFinal, this.vistaPrincipal, this.jPanelTicket, this.jpanelVentas, this.jPanelTicketArray, this.defaultTableModelArray);
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
        this.jDialogVentaFinal.jTextFieldPagoCon.addKeyListener(this);
        this.jDialogMasDe1Producto.jButtonCancelar.addActionListener(this);
        this.jDialogMasDe1Producto.jTextFieldCodigoBarras.addKeyListener(this);
        this.jDialogMasDe1Producto.jTextFieldCantidad.addKeyListener(this);
        this.jpanelVentas.jButtonSuprimir.addActionListener(this);
        this.jpanelVentas.jButtonVerificador.addActionListener(this);
        this.jDialogVerificadorPrecios.jButtonAceptar.addActionListener(this);
        this.jpanelVentas.jButtonCobrar.addActionListener(this);

        //this.jDialogVentaAgranel.jTextFieldImporte.addKeyListener(this);
        // this.jDialogVentaAgranel.jTextFieldCantidad.addKeyListener(this);
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
        jPanelTicketArray.get(0).jTableVender.getColumnModel().getColumn(5).setMinWidth(0);
        jPanelTicketArray.get(0).jTableVender.getColumnModel().getColumn(5).setMaxWidth(0);
        jpanelVentas.jTextFieldCodigoBarras.requestFocus();
        muqui();
        t();

    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        if (arg0.getSource() == jpanelVentas.jButtonCobrar) {

            formaDepago();
        }

        if (arg0.getSource() == jDialogVerificadorPrecios.jButtonAceptar) {
            jDialogVerificadorPrecios.setVisible(false);
            jDialogVerificadorPrecios.jLabelCodigo.setText("Codigo");
            jDialogVerificadorPrecios.jLabelDescripcionj.setText("Descripción");
            jDialogVerificadorPrecios.jLabelNombre.setText("Nombre");
            jDialogVerificadorPrecios.jLabelPrecio.setText("Precio");
        }

        if (arg0.getSource() == jpanelVentas.jButtonVerificador) {

            jDialogVerificadorPrecios.setLocationRelativeTo(null);
            jDialogVerificadorPrecios.setVisible(true);
        }
        if (arg0.getSource() == jpanelVentas.jButtonSuprimir) {

            eliminarProducto();
        }

        if (arg0.getSource() == jpanelVentas.jButtonAgregarProducto) {
            insertarProducto();
        }

        if (arg0.getSource() == jpanelVentas.jButtonMayoreo) {
            if (vistaPrincipal.usuario.getNivel() == 0) {
                ventasMayoreoMenudeo();
            } else {
                // UIManager.put("OptionPane.minimumSize",new Dimension(500,500)); 
                JOptionPane.showMessageDialog(null, "No tienes los permisos para acceder.", "Acceso denegado", JOptionPane.ERROR_MESSAGE);
            }

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
                jDialogVerificadorPrecios.jLabelDescripcionj.setText("Descripción");
                jDialogVerificadorPrecios.jLabelNombre.setText("Nombre");
                jDialogVerificadorPrecios.jLabelPrecio.setText("Precio");
            }
        }

        if (ke.getSource() == jpanelVentas.jTextFieldCodigoBarras) {
            if (ke.getKeyCode() == KeyEvent.VK_ENTER) {
                insertarProducto();
            }
            if (ke.getSource() == jpanelVentas.jTextFieldCodigoBarras) {

                if (ke.getKeyCode() == KeyEvent.VK_F7) {
                    eliminarTicket();
                }
                if (ke.getKeyCode() == KeyEvent.VK_F6) {
                    cambiarTicket();
                }
                if (ke.getKeyCode() == KeyEvent.VK_F5) {
                    addTicket();
                }

                if (ke.getKeyCode() == KeyEvent.VK_F4) {
                    jDialogVerificadorPrecios.setLocationRelativeTo(null);
                    jDialogVerificadorPrecios.setVisible(true);
                }

            }

        }
        if (ke.getSource() == jpanelVentas.jTextFieldCodigoBarras) {
            if (ke.getKeyCode() == KeyEvent.VK_F3) {
                if (vistaPrincipal.usuario.getNivel() == 0) {
                    ventasMayoreoMenudeo();
                } else {
                    // UIManager.put("OptionPane.minimumSize",new Dimension(500,500)); 
                    JOptionPane.showMessageDialog(null, "No tienes los permisos para acceder.", "Acceso denegado", JOptionPane.ERROR_MESSAGE);
                }

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

        if (arg0.getSource() == jDialogMasDe1Producto.jTextFieldCodigoBarras || arg0.getSource() == jDialogMasDe1Producto.jTextFieldCantidad) {
            if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
                insertarProductoMasDe1();
            }
            if (arg0.getKeyCode() == KeyEvent.VK_ESCAPE) {
                jDialogMasDe1Producto.setVisible(false);
            }

        }
        if (arg0.getSource() == jDialogVentaFinal.jTextFieldPagoCon) {
            try {

                BigDecimal cantidad = new BigDecimal(jDialogVentaFinal.jTextFieldPagoCon.getText().trim());

                BigDecimal cambio = cantidad.subtract(total);
                jDialogVentaFinal.jTextFieldSuCambio.setText("" + cambio);
            } catch (Exception e) {
                System.out.println("Pago con NO se capturo " + e);
            }
        }

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
        jPanelTicketArray.get(index).jTableVender.getColumnModel().getColumn(5).setMinWidth(0);
        jPanelTicketArray.get(index).jTableVender.getColumnModel().getColumn(5).setMaxWidth(0);

        total = ventasModelo.totalTicket(d);
        jpanelVentas.jLabelTotalProductosVendidos.setText("" + ventasModelo.totalProductos(d) + " total productos");
        jpanelVentas.jLabelTotal.setText("$ " + total);
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
            jPanelTicketArray.get(indice).jTableVender.getColumnModel().getColumn(5).setMinWidth(0);
            jPanelTicketArray.get(indice).jTableVender.getColumnModel().getColumn(5).setMaxWidth(0);

            /*Despues de crearlo lo selecciona*/
            jpanelVentas.jTabbedPaneTickets.setSelectedIndex(indice);
            int index = jpanelVentas.jTabbedPaneTickets.getSelectedIndex();
            DefaultTableModel d = defaultTableModelArray.get(index);
            total = ventasModelo.totalTicket(d);
            jpanelVentas.jLabelTotalProductosVendidos.setText("" + ventasModelo.totalProductos(d) + " total productos");
            jpanelVentas.jLabelTotal.setText("$ " + total);

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

        int index = jpanelVentas.jTabbedPaneTickets.getSelectedIndex();
        DefaultTableModel d = defaultTableModelArray.get(index);

        total = ventasModelo.totalTicket(d);
        jpanelVentas.jLabelTotalProductosVendidos.setText("" + ventasModelo.totalProductos(d) + " total productos");
        jpanelVentas.jLabelTotal.setText("$ " + total);

    }

    private void insertarProductoMasDe1() {
        int index = jpanelVentas.jTabbedPaneTickets.getSelectedIndex();
        DefaultTableModel d = ventasModelo.productos(jDialogMasDe1Producto.jTextFieldCodigoBarras.getText(), precioVentaMenudeo, jPanelTicketArray.get(index).jTableVender, defaultTableModelArray.get(index), new BigDecimal(jDialogMasDe1Producto.jTextFieldCantidad.getText()), true);
        jPanelTicketArray.get(index).jTableVender.setModel(d);

        System.out.println("click insertar mas de un producto  SE OCULTA EL DIALOGO");
        total = ventasModelo.totalTicket(d);
        jpanelVentas.jLabelTotalProductosVendidos.setText("" + ventasModelo.totalProductos(d) + " total productos");
        jpanelVentas.jLabelTotal.setText("$ " + total);
        jpanelVentas.jTextFieldCodigoBarras.setText("");
        jDialogMasDe1Producto.jTextFieldCantidad.setText("");
        jDialogMasDe1Producto.jTextFieldCodigoBarras.setText("");
        jDialogMasDe1Producto.setVisible(false);
    }

    private void mostrarInsertarProductoMasDe1() {
        jDialogMasDe1Producto.pack();
        jDialogMasDe1Producto.setLocationRelativeTo(null);
        jDialogMasDe1Producto.setVisible(true);
        jDialogMasDe1Producto.jTextFieldCodigoBarras.requestFocus();

    }

    private void eliminarProducto() {
        int index = jpanelVentas.jTabbedPaneTickets.getSelectedIndex();
        System.out.println("pressed delete");
        int row = jPanelTicketArray.get(index).jTableVender.getSelectedRow();

        defaultTableModelArray.get(index).removeRow(row);
        jPanelTicketArray.get(index).jTableVender.setModel(defaultTableModelArray.get(index));
        total = ventasModelo.totalTicket(defaultTableModelArray.get(index));
        jpanelVentas.jLabelTotalProductosVendidos.setText("" + ventasModelo.totalProductos(defaultTableModelArray.get(index)) + " total productos");

        jpanelVentas.jLabelTotal.setText("$ " + total);
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
            total = ventasModelo.totalTicket(d);
            jpanelVentas.jLabelTotalProductosVendidos.setText("" + ventasModelo.totalProductos(d) + " total productos");
            jpanelVentas.jLabelTotal.setText("$ " + total);
            jpanelVentas.jTextFieldCodigoBarras.setText("");
            jDialogBuscarProducto.setVisible(false);
            jDialogBuscarProducto.jTextFieldBuscar.selectAll();

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
        VerificarPrecioDao verificarPrecioDao = new VerificarPrecioDao();

        Tproducto p = verificarPrecioDao.verificarProducto(jDialogVerificadorPrecios.jTextFieldCodigoBarras.getText().trim());

        if (p == null) {
            jDialogVerificadorPrecios.jLabelCodigo.setText("Codigo: ");
            jDialogVerificadorPrecios.jLabelNombre.setText("Nombre: ");
            jDialogVerificadorPrecios.jLabelDescripcionj.setText("Descripción: ");
            jDialogVerificadorPrecios.jLabelPrecio.setText("Precio: $ ");
            JOptionPane.showMessageDialog(null, "Codigo de barras no existe", "error", JOptionPane.ERROR_MESSAGE);

        } else {
            jDialogVerificadorPrecios.jLabelCodigo.setText("Codigo: " + p.getCodigoBarras());
            jDialogVerificadorPrecios.jLabelNombre.setText("Nombre: " + p.getNombre());
            jDialogVerificadorPrecios.jLabelDescripcionj.setText("Descripción: " + p.getDescripcion());
            jDialogVerificadorPrecios.jLabelPrecio.setText("Precio: $ " + p.getPrecioVentaUnitario());
        }
        jDialogVerificadorPrecios.jTextFieldCodigoBarras.setText("");

    }

    private void ventasMayoreoMenudeo() {
        int index = jpanelVentas.jTabbedPaneTickets.getSelectedIndex();
        System.out.println(" cantidad ticket " + defaultTableModelArray.get(index).getRowCount());
        if (defaultTableModelArray.get(index).getRowCount() <= 0) {
            precioVentaMenudeo = !precioVentaMenudeo;
            System.out.println("TIPO VENTA mayorero menudeo " + precioVentaMenudeo);

            if (precioVentaMenudeo) {

                jpanelVentas.jLabelMayoreio.setVisible(false);
                jpanelVentas.jLabelMayoreio.setText("Ventas de mayoreo activado");
            } else {

                jpanelVentas.jLabelMayoreio.setVisible(true);

            }
        } else {
            JOptionPane.showMessageDialog(null, "Primero vacie el ticket", "error", JOptionPane.ERROR_MESSAGE);
        }

        System.out.println("precio menudeo " + precioVentaMenudeo);
    }

    public void t() {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                if (jDialogBuscarProducto.isShowing()) {
                    jDialogBuscarProducto.jTextFieldBuscar.requestFocus();
                }
                if (jpanelVentas.isShowing()) {
                    jpanelVentas.jTextFieldCodigoBarras.requestFocus();
                }
                if (jDialogVentaFinal.isShowing()) {
                    jDialogVentaFinal.jTextFieldPagoCon.requestFocus();

                }

            }
        };
        timer.schedule(task, 10, 100);

    }

    public void muqui() {
        this.jpanelVentas.jTabbedPaneTickets.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent arg0) {
                // System.out.println("Tab: " + jpanelVentas.jTabbedPaneTickets.getSelectedIndex());
                int index = jpanelVentas.jTabbedPaneTickets.getSelectedIndex();
                DefaultTableModel d = defaultTableModelArray.get(index);

                total = ventasModelo.totalTicket(d);
                jpanelVentas.jLabelTotalProductosVendidos.setText("" + ventasModelo.totalProductos(d) + " total productos");
                System.out.println("TOTAL DE Productos" + ventasModelo.totalProductos(d));
                jpanelVentas.jLabelTotal.setText("$ " + total);
            }
        });
    }
}
