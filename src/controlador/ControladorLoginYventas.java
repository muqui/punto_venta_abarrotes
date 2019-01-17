/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import dao.ClienteDao;
import dao.ProductoDao;
import dao.ProductoDao1;
import dao.UsuarioDao;
import dao.VentaDao;
import encryption.Encryption;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.Cliente;
import modelo.ContenidoPaquete;
import modelo.Formadepago;
import modelo.Tproducto;
import modelo.Tventa;
import modelo.Tventadetalle;
import modelo.Usuario;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;

import vista.CrearAdmin;
import vista.JDialogBuscarProducto;
import vista.JDialogClientes;
import vista.JDialogMasDe1Producto;
import vista.JDialogVentaAgranel;
import vista.JDialogVentaFinal;
import vista.JpanelVentas;
import vista.Login;
import vista.Principal;

/**
 *
 * @author mq12
 */
public class ControladorLoginYventas implements ActionListener, KeyListener {

    boolean efectivo;
    String folio;
    Tproducto producto;
    BigDecimal importe = new BigDecimal("0.00");
    BigDecimal precioGranel = new BigDecimal("0.00");
    Timer timer = new Timer();
    DefaultTableModel tableModelVentas;
    Principal vistaPrincipal;
    JpanelVentas jpanelVentas;
    Login login;
    Tventa venta;
    List<Tventadetalle> listaVentaDetalle;
    List<Tproducto> productos;
    List<Cliente> clientes;
    ClienteDao clienteDao;
    Usuario usuario;
    UsuarioDao usuarioDao;
    ProductoDao productoDao;
    CrearAdmin crearAdmin;
    JDialogClientes jDialogClientes;
    VentaDao ventaDao;

    JDialogBuscarProducto jDialogBuscarProducto;
    JDialogMasDe1Producto jDialogMasDe1Producto;
    JDialogVentaAgranel jDialogVentaAgranel;
    JDialogVentaFinal jdialogVentaFinal;

    public ControladorLoginYventas(Principal vistaPrincipal, Login login, Usuario usuario, JpanelVentas jpanelVentas, CrearAdmin crearAdmin, JDialogBuscarProducto jDialogBuscarProducto, JDialogMasDe1Producto jDialogMasDe1Producto, JDialogVentaAgranel jDialogVentaAgranel, JDialogVentaFinal jdialogVentaFinal, JDialogClientes jDialogClientes) {
        this.jDialogClientes = jDialogClientes;
        this.jdialogVentaFinal = jdialogVentaFinal;
        this.jDialogVentaAgranel = jDialogVentaAgranel;
        this.jDialogMasDe1Producto = jDialogMasDe1Producto;
        this.vistaPrincipal = vistaPrincipal;

        this.login = login;
        this.usuario = usuario;
        this.jDialogBuscarProducto = jDialogBuscarProducto;
        venta = new Tventa();
        this.crearAdmin = crearAdmin;
        usuarioDao = new UsuarioDao();
        productoDao = new ProductoDao();
        clienteDao = new ClienteDao();
        ventaDao = new VentaDao();
        this.jpanelVentas = jpanelVentas;
        login.jButtonCancelar.addActionListener(this);
        login.jButtonAceptar.addActionListener(this);
        jdialogVentaFinal.Cobrar.addActionListener(this);
        this.vistaPrincipal.jButtonVentas.addActionListener(this);
        this.vistaPrincipal.jButtonCerrarSession.addActionListener(this);
        this.jpanelVentas.jButtonAgregarProducto.addActionListener(this);
        this.jpanelVentas.jButtonCobrar.addActionListener(this);
        this.jpanelVentas.jButtonBuscar.addActionListener(this);
        this.jDialogClientes.jButtonAceptar.addActionListener(this);
        this.jDialogClientes.jTextFieldNombre.addKeyListener(this);
        this.jdialogVentaFinal.jTextFieldBuscarCliente.addKeyListener(this);
        this.jdialogVentaFinal.jButtonCliente.addActionListener(this);
        this.jdialogVentaFinal.jTextFieldPagoCon.addKeyListener(this);
        this.jDialogVentaAgranel.jButtonAceptar.addActionListener(this);
        this.vistaPrincipal.jButtonSalir.addActionListener(this);

        crearAdmin.jButtonCrear.addActionListener(this);

        tableModelVentas = new DefaultTableModel() {

            @Override
            public boolean isCellEditable(int row, int column) {
                //all cells false
                return false;
            }
        };
        String[] columnNames = {"CODIGO", "NOMBRE", "PRECIO ", "CANTIDAD", "TOTAL"};
        tableModelVentas.setColumnIdentifiers(columnNames);
        teclado();
        jpanelVentas.jTableVender.setModel(tableModelVentas);
        listaVentaDetalle = new ArrayList<>();
        clientes = new ArrayList<>();
        // this.setEventoText(jInternalFrameVender.txtBuscar);
//        setBuscarProducto(jDialogBuscarProducto.jTextFieldBuscar);
        productos = new ArrayList<>();
        // createKeybindings(jDialogBuscarProducto.jTableProductos);
        jpanelVentas.jTextFieldCodigoBarras.addKeyListener(this);
        jpanelVentas.jTableVender.addKeyListener(this);
        this.jDialogMasDe1Producto.jTextFieldCantidad.addKeyListener(this);
        this.jDialogBuscarProducto.jTextFieldBuscar.addKeyListener(this);
        this.jDialogMasDe1Producto.jTextFieldCodigoBarras.addKeyListener(this);
        this.jDialogVentaAgranel.jTextFieldCantidad.addKeyListener(this);
        this.jDialogVentaAgranel.jTextFieldImporte.addKeyListener(this);
        this.jDialogMasDe1Producto.jButtonAceptar.addActionListener(this);
        this.jDialogMasDe1Producto.jButtonCancelar.addActionListener(this);
        this.jdialogVentaFinal.jTextFieldBuscarCliente.addActionListener(this);
        this.jdialogVentaFinal.jButtonImprimirCobrar.addActionListener(this);
        this.jDialogBuscarProducto.jButtonAceptar.addActionListener(this);
        this.jDialogBuscarProducto.jButtonCancelar.addActionListener(this);
        this.jpanelVentas.jButtonInsertarMasD1.addActionListener(this);
        clientesPorNombre("");
        limpiarErroresMixto();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        /*
        *
        *Cerrar session
        *
         */
        if (e.getSource() == vistaPrincipal.jButtonCerrarSession) {
            System.out.println("sout");
            start();
        }
        if (e.getSource() == vistaPrincipal.jButtonSalir) {
            Thread respaldo = new RespaldoBaseDeDatos();
            respaldo.start();
            System.exit(0);

        }
        if (e.getSource() == jDialogVentaAgranel.jButtonAceptar) {
            ventaAgranel();
        }
        if (e.getSource() == jDialogVentaAgranel.jButtonCancelar) {
            jDialogVentaAgranel.setVisible(false);
        }
        if (e.getSource() == jDialogMasDe1Producto.jButtonCancelar) {
            jDialogMasDe1Producto.setVisible(false);
        }
        if (e.getSource() == jDialogMasDe1Producto.jButtonAceptar) {
            insertMasDe1Producto();
        }
        if (e.getSource() == jpanelVentas.jButtonInsertarMasD1) {
            mostrarDialogMasDe1Producto();
        }
        if (e.getSource() == jDialogBuscarProducto.jButtonCancelar) {
            jDialogBuscarProducto.setVisible(false);
        }
        if (e.getSource() == jDialogBuscarProducto.jButtonAceptar) {
            agregarproductodesdeBuscar();
        }
        if (e.getSource() == jDialogClientes.jButtonAceptar) {
            selectClienteMixto();
        }
        if (e.getSource() == jdialogVentaFinal.jButtonCliente) {
            jDialogClientes.jTableCliente.setModel(llenarTablaClientes());
            jDialogClientes.setLocationRelativeTo(null);
            jDialogClientes.setVisible(true);

        }
        if (e.getSource() == jdialogVentaFinal.jButtonImprimirCobrar) {
            try {
                cobrar();
                imprimir(folio);
            } catch (Exception ex) {
                Logger.getLogger(ControladorLoginYventas.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (e.getSource() == jpanelVentas.jButtonBuscar) {
            jDialogBuscarProducto.setLocationRelativeTo(null);
            jDialogBuscarProducto.setVisible(true);

        }
        if (e.getSource() == jpanelVentas.jButtonCobrar) {
            ticketVentaMostrar();

        }
        if (e.getSource() == jdialogVentaFinal.Cobrar) {
            try {
                cobrar();
            } catch (Exception ex) {
                Logger.getLogger(ControladorLoginYventas.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        if (e.getSource() == jpanelVentas.jButtonAgregarProducto) {
            agregarProductoAticket(jpanelVentas.jTextFieldCodigoBarras.getText().trim(), new BigDecimal("1"));
            jpanelVentas.jTableVender.setRowSelectionInterval(0, 0);
        }
        if (e.getSource() == login.jButtonCancelar) {
            System.exit(0);
        }
        if (e.getSource() == login.jButtonAceptar) {
            login();
        }
        if (e.getSource() == vistaPrincipal.jButtonVentas) {
            startVentas();
        }
        if (e.getSource() == crearAdmin.jButtonCrear) {
            try {
                saveAdmin();
            } catch (Exception ex) {
                Logger.getLogger(ControladorLoginYventas.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void start() {
        if (usuarioDao.getUsuarios().isEmpty()) {
            crearadmin();
        } else {
            vistaPrincipal.dispose(); // Fixes the issue
            vistaPrincipal.setUndecorated(true);  //full screen
            vistaPrincipal.setVisible(true);
            vistaPrincipal.jToolBar1.setVisible(false);
            login.setLocationRelativeTo(null);

            login.setVisible(true);

        }

    }

    private void login() {
        try {
            String user = login.jTextFieldUsuario.getText();
            String password = Encryption.encrypt(login.jPasswordFieldPass.getText());
            usuario = usuarioDao.login(user, password);

            if (usuario == null) {
                JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrecto ", "error", JOptionPane.ERROR_MESSAGE);
            } else {
                jDialogBuscarProducto.jTableProductos.setModel(llenarTabla());
                login.setVisible(false);
                vistaPrincipal.jToolBar1.setVisible(true);
                vistaPrincipal.usuario = usuario;
                vistaPrincipal.jLabelTitulo.setText("Usuario: " + login.jTextFieldUsuario.getText());

                login.jTextFieldUsuario.setText("");
                login.jPasswordFieldPass.setText("");
                startVentas();
                t();
            }

        } catch (Exception ex) {
            Logger.getLogger(ControladorLoginYventas.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void startVentas() {
        vistaPrincipal.jPanelPanelPrincipal.removeAll();
        vistaPrincipal.jPanelPanelPrincipal.setLayout(new java.awt.BorderLayout());
        vistaPrincipal.jPanelPanelPrincipal.add(jpanelVentas);
        vistaPrincipal.jPanelPanelPrincipal.validate();
        vistaPrincipal.jPanelPanelPrincipal.repaint();

    }

    private void crearadmin() {
        crearAdmin.setVisible(true);

    }

    private void saveAdmin() throws Exception {
        usuario.setNombre(crearAdmin.jTextFieldNombre.getText());
        usuario.setPassword(Encryption.encrypt(crearAdmin.jPasswordFieldPassword.getText()));

        usuario.setNivel(0); //valor temporal 
        usuarioDao.addUser(usuario);
        crearAdmin.setVisible(false);
        start();
    }

    private void teclado() {
        login.jPasswordFieldPass.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    login();
                }
            }
        });
        login.jTextFieldUsuario.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    login();
                }
            }
        });
        jpanelVentas.jTextFieldCodigoBarras.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    agregarProductoAticket(jpanelVentas.jTextFieldCodigoBarras.getText().trim(), new BigDecimal("1"));
                    jpanelVentas.jTableVender.setRowSelectionInterval(0, 0);

                }
            }
        });

    }

    private void agregarProductoAticket(String codigo, BigDecimal cantidad) {
        try {

            producto = productoDao.getByCodigoBarras(codigo);

            //  System.out.println("precio granel  " + precioGranel);
            if (producto != null) {
                precioGranel = producto.getPrecioVentaUnitario();
                // si es venta a granl
                if (producto.getComosevende().equals("Granel")) {
                    jDialogVentaAgranel.setLocationRelativeTo(null);
                    jDialogVentaAgranel.setVisible(true);

                } else {
                    producto.setCantidad(cantidad);
                }
                tableModelVentas(producto);
                jpanelVentas.jLabelTotal.setText("$ " + calcularCostos1());
                jpanelVentas.jTextFieldCodigoBarras.setText("");
            } else {
                JOptionPane.showMessageDialog(null, "Este producto no existe ", "error", JOptionPane.ERROR_MESSAGE);
                jpanelVentas.jTextFieldCodigoBarras.setText("");
            }

        } catch (Exception ex) {
            Logger.getLogger(ControladorLoginYventas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void tableModelVentas(Tproducto producto) {
        //  BigDecimal cantidad =importe.divide(precioGranel,3, BigDecimal.ROUND_HALF_UP);
        BigDecimal total = producto.getPrecioVentaUnitario().multiply(producto.getCantidad());
        total = total.setScale(1, BigDecimal.ROUND_HALF_UP);
        //System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxx total: " + total);
        tableModelVentas.addRow(new Object[]{producto.getCodigoBarras(), producto.getNombre(), producto.getPrecioVentaUnitario(), producto.getCantidad(), total});
        jpanelVentas.jTableVender.setModel(tableModelVentas);

    }

    public BigDecimal calcularCostos1() {
        BigDecimal totalVenta = new BigDecimal("0");
        try {

            for (int i = 0; i < tableModelVentas.getRowCount(); i++) {

                BigDecimal totalVentaPorProducto = (BigDecimal) tableModelVentas.getValueAt(i, 4);

                totalVenta = totalVenta.add(totalVentaPorProducto);
            }

            venta.setPrecioVentaTotal(totalVenta);

        } catch (Exception ex) {

        }
        return totalVenta;
    }

    private void cobrar() throws Exception {
        efectivo = false;
        VentaDao ventaDao = new VentaDao();
        int idVenta = 0;
        int formaPago = jdialogVentaFinal.jTabbedPaneVentasFinal.getSelectedIndex();
        if (formaPago == 0) { //PAGO EFECTIVO
            efectivo = true;
            idVenta = saveVenta();
            Tventa v = ventaDao.getUltimoRegistro(idVenta);
            ventaDao.insertFormaPago(new Formadepago(v, "EFECTIVO", v.getPrecioVentaTotal(), ""));
            postVenta();

        }
        if (formaPago == 1) { //PAGO CREDITO
            int selected = jdialogVentaFinal.jTableClientes.getSelectedRow();
            if (selected > 0) {
                idVenta = saveVenta();
                Tventa v = ventaDao.getUltimoRegistro(idVenta);
                ventaDao.insertFormaPago(new Formadepago(v, "CREDITO", v.getPrecioVentaTotal(), "" + jdialogVentaFinal.jTableClientes.getValueAt(selected, 0)));
                postVenta();
            } else {
                JOptionPane.showMessageDialog(null, "Seleccione cliente", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        if (formaPago == 2) { //PAGO TARJETA DEBITO/CREDITO
            limpiarErroresMixto();
            if (!"".equals(jdialogVentaFinal.jTextFieldReferenciaTarjeta.getText().trim())) {
                idVenta = saveVenta();
                Tventa v = ventaDao.getUltimoRegistro(idVenta);
                ventaDao.insertFormaPago(new Formadepago(venta, "TARJETA", venta.getPrecioVentaTotal(), jdialogVentaFinal.jTextFieldReferenciaTarjeta.getText().trim()));
                postVenta();
            } else {
                // bandera = false;
                jdialogVentaFinal.jLabelTarjetaError.setText("Capture referencia");
            }

        }
        if (formaPago == 3) { //PAGO VALE
            limpiarErroresMixto();
            if (!"".equals(jdialogVentaFinal.jTextFieldReferenciaVale.getText().trim())) {
                idVenta = saveVenta();
                Tventa v = ventaDao.getUltimoRegistro(idVenta);
                ventaDao.insertFormaPago(new Formadepago(venta, "VALE", venta.getPrecioVentaTotal(), jdialogVentaFinal.jTextFieldReferenciaVale.getText().trim()));
                postVenta();
            } else {
                // bandera = false;
                jdialogVentaFinal.jLabelErrorVale.setText("Capture referencia");
            }
        }
        if (formaPago == 4) { //PAGO MIXTO
            limpiarErroresMixto();
            BigDecimal sumatotal = new BigDecimal("0.00");
            BigDecimal efectivo = new BigDecimal("0.00");
            BigDecimal credito = new BigDecimal("0.00");
            BigDecimal tarjeta = new BigDecimal("0.00");
            BigDecimal vale = new BigDecimal("0.00");

            BigDecimal total = calcularCostos1();
            boolean bandera = true;
            if (!"".equals(jdialogVentaFinal.jTextFieldMixtoEfectivo.getText().trim())) {
                efectivo = new BigDecimal(jdialogVentaFinal.jTextFieldMixtoEfectivo.getText().trim());
            }
            if (!"".equals(jdialogVentaFinal.jTextFieldMixtoTarjetaCantidad.getText().trim())) {
                if ("".equals(jdialogVentaFinal.jTextFieldMixtoTarjetaReferencia.getText().trim())) {
                    jdialogVentaFinal.jLabelMixtoErrorTarjeta.setText("Capture referencia");
                    bandera = false;
                } else {

                    tarjeta = new BigDecimal(jdialogVentaFinal.jTextFieldMixtoTarjetaCantidad.getText().trim());

                }

            }
            if (!"".equals(jdialogVentaFinal.jTextFieldMixtoValeCantidad.getText().trim())) {
                if ("".equals(jdialogVentaFinal.jTextFieldMixtoValeReferencia.getText().trim())) {
                    jdialogVentaFinal.jLabelMixtoErrorVale.setText("Capture referencia");
                    bandera = false;
                } else {
                    vale = new BigDecimal(jdialogVentaFinal.jTextFieldMixtoValeCantidad.getText().trim());
                }

            }
            if (!"".equals(jdialogVentaFinal.jTextFieldMixtoCreditoCantidad.getText().trim())) {
                if ("".equals(jdialogVentaFinal.jTextFieldMixtoCreditoNombre.getText().trim())) {
                    jdialogVentaFinal.jLabelMixtoErrorCredito.setText("Sleccione un cliente");
                    bandera = false;
                } else {
                    credito = new BigDecimal(jdialogVentaFinal.jTextFieldMixtoCreditoCantidad.getText().trim());
                }

            }
            sumatotal = sumatotal.add(efectivo);
            sumatotal = sumatotal.add(tarjeta);
            sumatotal = sumatotal.add(vale);
            sumatotal = sumatotal.add(credito);

            System.out.println("efectivo " + efectivo + " tarjeta " + tarjeta + " vale  " + vale + " credito " + credito);
            System.out.println("suma total: " + sumatotal + " total " + total);
            if (bandera) {

                BigDecimal restante = total.subtract(sumatotal);
                jdialogVentaFinal.jLabelRestante.setText("Restante " + restante);
                int comparar = total.compareTo(sumatotal);

                if (comparar == 0) {
                    //TODO ESTA BIEN SE PUEDE GUARDAR LA VENTA
                    idVenta = saveVenta();
                    Tventa v = ventaDao.getUltimoRegistro(idVenta);
                    int efectivoInt = efectivo.compareTo(new BigDecimal("0.00"));
                    if (efectivoInt == 1) {
                        ventaDao.insertFormaPago(new Formadepago(v, "EFECTIVO", efectivo, ""));
                    }
                    int tarjetaInt = tarjeta.compareTo(new BigDecimal("0.00"));
                    if (tarjetaInt == 1) {
                        ventaDao.insertFormaPago(new Formadepago(venta, "TARJETA", tarjeta, jdialogVentaFinal.jTextFieldMixtoTarjetaReferencia.getText().trim()));
                    }
                    int valeInt = vale.compareTo(new BigDecimal("0.00"));
                    if (valeInt == 1) {
                        ventaDao.insertFormaPago(new Formadepago(venta, "VALE", vale, jdialogVentaFinal.jTextFieldMixtoValeReferencia.getText().trim()));
                    }
                    int creditoInt = credito.compareTo(new BigDecimal("0.00"));
                    if (creditoInt == 1) {
                        ventaDao.insertFormaPago(new Formadepago(v, "CREDITO", credito, "" + jdialogVentaFinal.jLabelMixtoIdCliente.getText()));

                    }
                    postVenta();

                }
                if (comparar == 1) {
                    JOptionPane.showMessageDialog(null, "La suma es menor al total", "Error", JOptionPane.ERROR_MESSAGE);
                }
                if (comparar == -1) {
                    JOptionPane.showMessageDialog(null, "La suma es mayor al total", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }

        }
        folio = String.valueOf(idVenta);

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
            // System.out.println("" + productos.get(i).getDepartamento().getNombre());
            tableModel.addRow(fila);

        }
        return tableModel;
    }

    @Override
    public void keyTyped(KeyEvent ke) {

    }

    @Override
    public void keyPressed(KeyEvent ke) {
        if (ke.getSource() == jdialogVentaFinal.jTextFieldPagoCon) {
            if (ke.getKeyCode() == KeyEvent.VK_F12) {
                try {
                    cobrar();
                } catch (Exception ex) {
                    Logger.getLogger(ControladorLoginYventas.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (ke.getKeyCode() == KeyEvent.VK_F11) {
                try {
                    cobrar();
                    imprimir(folio);
                } catch (Exception ex) {
                    Logger.getLogger(ControladorLoginYventas.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (ke.getKeyCode() == KeyEvent.VK_ESCAPE) {
                jdialogVentaFinal.setVisible(false);
            }
        }
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
        if (ke.getSource() == jDialogVentaAgranel.jTextFieldCantidad || ke.getSource() == jDialogVentaAgranel.jTextFieldImporte) {
            if (ke.getKeyCode() == KeyEvent.VK_ENTER) {

                ventaAgranel();
            }

        }

        if (ke.getSource() == jDialogMasDe1Producto.jTextFieldCodigoBarras || ke.getSource() == jDialogMasDe1Producto.jTextFieldCantidad) {
            if (ke.getKeyCode() == KeyEvent.VK_ENTER) {
                insertMasDe1Producto();
            }
            if (ke.getKeyCode() == KeyEvent.VK_ESCAPE) {
                jDialogMasDe1Producto.setVisible(false);

            }

        }
        if (ke.getSource() == jpanelVentas.jTextFieldCodigoBarras) {
            if (ke.getKeyCode() == KeyEvent.VK_F12) {
                ticketVentaMostrar();

            }
            if (ke.getKeyCode() == KeyEvent.VK_F1) {
                jDialogBuscarProducto.setLocationRelativeTo(null);
                jDialogBuscarProducto.setVisible(true);
            }
            if (ke.getKeyCode() == KeyEvent.VK_F2) {
                mostrarDialogMasDe1Producto();
            }
            if (ke.getKeyCode() == KeyEvent.VK_DELETE) {
                System.out.println("pressed delete");
                int row = jpanelVentas.jTableVender.getSelectedRow();
                System.out.println(" " + jpanelVentas.jTableVender.getValueAt(row, 0));
                tableModelVentas.removeRow(row);
                jpanelVentas.jTableVender.setModel(tableModelVentas);
                jpanelVentas.jLabelTotal.setText("$ " + calcularCostos1());
                jpanelVentas.jTableVender.setRowSelectionInterval(0, 0);

            }
            int rows = jpanelVentas.jTableVender.getRowCount();
            int selected = jpanelVentas.jTableVender.getSelectedRow();
            if (ke.getKeyCode() == KeyEvent.VK_UP) {
                if (selected > 0) {
                    jpanelVentas.jTableVender.setRowSelectionInterval(selected - 1, selected - 1);
                } else {
                    jpanelVentas.jTableVender.setRowSelectionInterval(rows - 1, rows - 1);
                }
            }

            if (ke.getKeyCode() == KeyEvent.VK_DOWN) {
                System.out.println("presionaste tecla abajo total " + rows + " selected: " + selected);
                // jDialogBuscarProducto.jTableProductos.setRowSelectionInterval((selected+1), 0);
                if (rows > (selected + 1)) {
                    jpanelVentas.jTableVender.setRowSelectionInterval(selected + 1, selected + 1);
                } else {
                    jpanelVentas.jTableVender.setRowSelectionInterval(0, 0);
                }

            }
        }
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        if (ke.getSource() == jdialogVentaFinal.jTextFieldPagoCon) {
            BigDecimal cantidad = new BigDecimal(jdialogVentaFinal.jTextFieldPagoCon.getText().trim());
            BigDecimal cambio = cantidad.subtract(venta.getPrecioVentaTotal());
            jdialogVentaFinal.jTextFieldSuCambio.setText("" + cambio);
        }

        //BUSCAR CLIENTES
        if (ke.getSource() == jDialogClientes.jTextFieldNombre) {

            if (ke.getKeyCode() != KeyEvent.VK_UP && ke.getKeyCode() != KeyEvent.VK_DOWN) {
                clientesPorNombre1(jDialogClientes.jTextFieldNombre.getText().trim());

            }

        }
        if (ke.getSource() == jdialogVentaFinal.jTextFieldBuscarCliente) {

            if (ke.getKeyCode() != KeyEvent.VK_UP && ke.getKeyCode() != KeyEvent.VK_DOWN) {
                clientesPorNombre(jdialogVentaFinal.jTextFieldBuscarCliente.getText().trim());

            }

        }
        if (ke.getSource() == jDialogBuscarProducto.jTextFieldBuscar) {
            if (ke.getKeyCode() == KeyEvent.VK_ESCAPE) {
                jDialogBuscarProducto.setVisible(false);
            }

            if (ke.getKeyCode() == KeyEvent.VK_ENTER) {
                agregarproductodesdeBuscar();

            }

            if (ke.getKeyCode() != KeyEvent.VK_UP && ke.getKeyCode() != KeyEvent.VK_DOWN) {
                productosPorNombre(jDialogBuscarProducto.jTextFieldBuscar.getText());
            }

        }

        if (ke.getSource() == jDialogVentaAgranel.jTextFieldCantidad) {
            try {
                float cantidad = Float.parseFloat(jDialogVentaAgranel.jTextFieldCantidad.getText().trim());
                importe = precioGranel;
                importe = importe.multiply(new BigDecimal(cantidad));
                importe = importe.setScale(2, BigDecimal.ROUND_HALF_UP);

                jDialogVentaAgranel.jTextFieldImporte.setText("" + importe);
            } catch (Exception e) {
                jDialogVentaAgranel.jTextFieldImporte.setText("");
            }

        }
        if (ke.getSource() == jDialogVentaAgranel.jTextFieldImporte) {
            try {
                BigDecimal importe = new BigDecimal(jDialogVentaAgranel.jTextFieldImporte.getText().trim());
                BigDecimal cantidad = importe.divide(precioGranel, 3, BigDecimal.ROUND_HALF_UP);
                jDialogVentaAgranel.jTextFieldCantidad.setText("" + cantidad);
            } catch (Exception e) {
                jDialogVentaAgranel.jTextFieldCantidad.setText("");
            }
        }
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
                if (jdialogVentaFinal.isShowing()) {
                    jdialogVentaFinal.jTextFieldPagoCon.requestFocus();

                }

                //System.out.println("soy un hilito");
            }
        };
        timer.schedule(task, 10, 1000);

    }

    private void mostrarDialogMasDe1Producto() {
        jDialogMasDe1Producto.setLocationRelativeTo(null);
        jDialogMasDe1Producto.setVisible(true);
    }

    private void insertMasDe1Producto() {
        agregarProductoAticket(jDialogMasDe1Producto.jTextFieldCodigoBarras.getText().trim(), new BigDecimal(jDialogMasDe1Producto.jTextFieldCantidad.getText().trim()));
        jDialogMasDe1Producto.setVisible(false);
        jDialogMasDe1Producto.jTextFieldCantidad.setText("");
        jDialogMasDe1Producto.jTextFieldCodigoBarras.setText("");
    }

    private void productosPorNombre(String text) {

        try {
            productos = productoDao.getPorNombre(text);
            System.out.println("Productos " + productos);
            jDialogBuscarProducto.jTableProductos.setModel(llenarTabla());
            jDialogBuscarProducto.jTableProductos.setRowSelectionInterval(0, 0);

        } catch (Exception ex) {
            Logger.getLogger(ControladorLoginYventas.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void ticketVentaMostrar() {

        if (tableModelVentas.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "No tiene productos", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            jdialogVentaFinal.jLabelTotal.setText("$ " + venta.getPrecioVentaTotal());
            jdialogVentaFinal.jLabelRestante.setText("Restante " + venta.getPrecioVentaTotal());
            jdialogVentaFinal.jTabbedPaneVentasFinal.setSelectedIndex(0);
            jdialogVentaFinal.setLocationRelativeTo(null);
            jdialogVentaFinal.setVisible(true);
            jdialogVentaFinal.jTextFieldPagoCon.requestFocus();

        }

    }

    private void clientesPorNombre(String Nombre) {
        try {
            clientes = clienteDao.getPorNombre(Nombre);
            System.out.println("Productos " + clientes);
            jdialogVentaFinal.jTableClientes.setModel(llenarTablaClientes());
            //  jDialogBuscarProducto.jTableProductos.setModel(llenarTabla());
            // jDialogBuscarProducto.jTableProductos.setRowSelectionInterval(0, 0);

        } catch (Exception ex) {
            Logger.getLogger(ControladorLoginYventas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void clientesPorNombre1(String Nombre) {
        try {
            clientes = clienteDao.getPorNombre(Nombre);
            System.out.println("Productos " + clientes);
            jDialogClientes.jTableCliente.setModel(llenarTablaClientes());
            //  jDialogBuscarProducto.jTableProductos.setModel(llenarTabla());
            // jDialogBuscarProducto.jTableProductos.setRowSelectionInterval(0, 0);

        } catch (Exception ex) {
            Logger.getLogger(ControladorLoginYventas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public DefaultTableModel llenarTablaClientes() {
        DefaultTableModel tableModel = new DefaultTableModel() {

            @Override
            public boolean isCellEditable(int row, int column) {
                //all cells false
                return false;
            }
        };

        String[] columnNames = {"ID", "NOMBRE"};
        tableModel.setColumnIdentifiers(columnNames);
        Object[] fila = new Object[tableModel.getColumnCount()];

        for (int i = 0; i < clientes.size(); i++) {
            fila[0] = clientes.get(i).getIdcliente();
            fila[1] = clientes.get(i).getNombreCompleto();
            //  fila[0] = productos.get(i).getCodigoBarras();
            //  fila[1] = productos.get(i).getNombre();

            tableModel.addRow(fila);

        }
        return tableModel;
    }

    private void limpiarErroresMixto() {
        jdialogVentaFinal.jLabelMixtoErrorEfectivo.setText("");
        jdialogVentaFinal.jLabelMixtoErrorTarjeta.setText("");
        jdialogVentaFinal.jLabelMixtoErrorVale.setText("");
        jdialogVentaFinal.jLabelTarjetaError.setText("");
        jdialogVentaFinal.jLabelErrorVale.setText("");
        jdialogVentaFinal.jLabelMixtoErrorCredito.setText("");
        jdialogVentaFinal.jLabelRestante.setText("Restante");
    }

    private int saveVenta() {
        int idVenta;

        for (int i = 0; i < tableModelVentas.getRowCount(); i++) {
            try {
                String codigo = (String) tableModelVentas.getValueAt(i, 0);
                Tproducto product = productoDao.getByCodigoBarras(codigo);

                String nombre = (String) tableModelVentas.getValueAt(i, 1);
                BigDecimal precioUnitario = (BigDecimal) tableModelVentas.getValueAt(i, 2);

                BigDecimal cantidad = (BigDecimal) tableModelVentas.getValueAt(i, 3);
                BigDecimal PrecioProveedor = product.getPrecioProveedor().multiply(cantidad);
                BigDecimal total = (BigDecimal) tableModelVentas.getValueAt(i, 4);
                String comoSeVende = product.getComosevende();

                if (product.getComosevende().equals("Paquete")) {
                    // System.out.println("modificar para vendeer paquete xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx" + product.getComosevende());
                    for (ContenidoPaquete item : productoDao.getContnidoPaquteList(product.getIdProducto())) {

                        BigDecimal cant = item.getCantidad();
                        cant = cant.multiply(cantidad);
                        //BigDecimal cant = cantidad;
                        BigDecimal prec = item.getPrecioUnitario();
                        // BigDecimal precProveedor = cant.multiply(item.getTproducto().getPrecioProveedor());
                        Tproducto pro = productoDao.getByID(item.getTproducto().getIdProducto());
                        // System.out.println("proveedoir lapicera " + pro);
                        BigDecimal precProveedor = pro.getPrecioProveedor().multiply(cant);
                        BigDecimal ttotal = cant.multiply(prec);
                        //System.out.println("Item LOCOX" + precProveedor);
                        listaVentaDetalle.add(new Tventadetalle(null, null, item.getTproducto().getCodigoBarras(), item.getTproducto().getNombre(), pro.getPrecioProveedor(), cant, ttotal, precProveedor, new BigDecimal("0.00"), new BigDecimal("0.00"), false, ""));
                    }
                }
                listaVentaDetalle.add(new Tventadetalle(null, null, codigo, nombre, precioUnitario, cantidad, total, PrecioProveedor, new BigDecimal("0.00"), new BigDecimal("0.00"), true, comoSeVende));

                calcularCostos1();
            } catch (Exception ex) {
                Logger.getLogger(ControladorLoginYventas.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        venta.setUsuario(usuario);
        venta.setFechaRegistro(new Date());
        // venta.setFormaDePago("Efectivo"); CORREGIR TIPO PAGO
        idVenta = ventaDao.insert(venta);
        //  System.out.println("idventa desde ek contolador " + idVenta);
        //  System.out.println("lista ventas   xxxxxxxxxxxxxx: " + listaVentaDetalle);
        Tproducto producto1;
        ProductoDao1 productoDao1 = new ProductoDao1();
        for (Tventadetalle item : this.listaVentaDetalle) {
            try {
                producto1 = productoDao1.getByCodigoBarras(item.getCodigoBarrasProducto());

                System.out.println("CANTIDAD " + producto1.getCantidad() + "  INVENTARIO " + producto1.getInventariar() + " CANTIDAD A DESCONTAR " + item.getCantidad());
                if (producto1.getInventariar()) {
                    producto1.setCantidad(producto1.getCantidad().subtract(item.getCantidad()));
                    productoDao1.modificarProducto(producto1);
                }

                item.setTventa(ventaDao.getUltimoRegistro(idVenta));
                item.setTproducto(producto1);

                ventaDao.insertVentaDetalle(item);
            } catch (Exception ex) {
                Logger.getLogger(ControladorLoginYventas.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return idVenta;
    }

    private void postVenta() {
//x

        listaVentaDetalle = new ArrayList<>();
        if (tableModelVentas.getRowCount() > 0) {
            for (int i1 = tableModelVentas.getRowCount() - 1; i1 > -1; i1--) {
                tableModelVentas.removeRow(i1);
            }
        }

        jpanelVentas.jTableVender.setModel(tableModelVentas);
        jpanelVentas.jLabelTotal.setText("$ 0");
        jdialogVentaFinal.jTextFieldPagoCon.setText("");
        jdialogVentaFinal.jTextFieldSuCambio.setText("");
        jdialogVentaFinal.jTextFieldBuscarCliente.setText("");
        jdialogVentaFinal.jTextFieldReferenciaTarjeta.setText("");
        jdialogVentaFinal.jTextFieldReferenciaVale.setText("");
        jdialogVentaFinal.jTextFieldMixtoEfectivo.setText("");
        jdialogVentaFinal.jTextFieldMixtoTarjetaCantidad.setText("");
        jdialogVentaFinal.jTextFieldMixtoTarjetaReferencia.setText("");
        jdialogVentaFinal.jTextFieldMixtoValeCantidad.setText("");
        jdialogVentaFinal.jTextFieldMixtoValeReferencia.setText("");
        jdialogVentaFinal.jTextFieldMixtoCreditoCantidad.setText("");
        jdialogVentaFinal.jTextFieldMixtoCreditoNombre.setText("");
        jdialogVentaFinal.jLabelMixtoIdCliente.setText("ID");
        jdialogVentaFinal.setVisible(false);

//x
    }

    private void selectClienteMixto() {
        int selected = jDialogClientes.jTableCliente.getSelectedRow();
        // System.out.println(" seleccion clientes " + selected);
        if (selected >= 0) {
            jdialogVentaFinal.jTextFieldMixtoCreditoNombre.setText("" + jDialogClientes.jTableCliente.getValueAt(selected, 1));
            jdialogVentaFinal.jLabelMixtoIdCliente.setText("" + jDialogClientes.jTableCliente.getValueAt(selected, 0));
            jDialogClientes.setVisible(false);
        } else {

            JOptionPane.showMessageDialog(null, "Seleccione cliente", "Error", JOptionPane.ERROR_MESSAGE);

        }

    }

    private void imprimir(String folio) throws SQLException, ClassNotFoundException {
        System.out.println("efectivo " + efectivo);
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/puntoventa", "root", "Fedora12");

            Map parameters = new HashMap();
            parameters.put("customerID", folio);
            JasperReport report;
            if (efectivo) {
                report = JasperCompileManager.compileReport("efectivo.jrxml");
            } else {
                System.out.println("falsooooooooooooooooooooooooooooooo        wwwwwwwwwwwwwwwwwwwww");
                report = JasperCompileManager.compileReport("multiple.jrxml");
            }

            JasperPrint print = JasperFillManager.fillReport(report, parameters, conexion);
            JasperPrintManager.printReport(print, false);
            // Exporta el informe a PDF  JasperExportManager.exportReportToPdfFile(print,"D:\\servidor\\ticket.pdf");
            //Para visualizar el pdf directamente desde java  JasperViewer.viewReport(print, false);
        } catch (JRException ex) {
            Logger.getLogger(ControladorLoginYventas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void agregarproductodesdeBuscar() {
        if (jDialogBuscarProducto.jTableProductos.getRowCount() > 0) {
            agregarProductoAticket("" + jDialogBuscarProducto.jTableProductos.getValueAt(jDialogBuscarProducto.jTableProductos.getSelectedRow(), 0), new BigDecimal("1"));
            jDialogBuscarProducto.setVisible(false);
        } else {
            JOptionPane.showMessageDialog(null, "Producto no encontrado ", "error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void ventaAgranel() {
        producto.setCantidad(new BigDecimal(jDialogVentaAgranel.jTextFieldCantidad.getText().trim()));
        jDialogVentaAgranel.setVisible(false);
        jDialogVentaAgranel.jTextFieldCantidad.setText("");
        jDialogVentaAgranel.jTextFieldImporte.setText("");
    }
}
