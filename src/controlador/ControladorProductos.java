package controlador;

import dao.AltaProductoDao;
import dao.ProductoDao;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.ContenidoPaquete;
import modelo.Departamento;
import modelo.Tproducto;
import vista.JpanelProductos;
import vista.Principal;
import vista.producto.JpanelContenidoPaquete;
import vista.producto.JpanelProductoNuevo;

/**
 *
 * @author mq12
 */
public class ControladorProductos implements ActionListener {

    Principal vistaPrincipal;
    JpanelProductos jpanelProductos;
    JpanelProductoNuevo jpanelProductoNuevo;
    JpanelContenidoPaquete jpanelContenidoPaquete;
    ProductoDao productoDao;
    // DepartamentoDao departamentodao = new DepartamentoDao();
    Tproducto producto = new Tproducto();
    Tproducto productoContenido = new Tproducto();
    List<ContenidoPaquete> contenidoPaqueteList;
    DefaultTableModel tableModel = new DefaultTableModel();

    public ControladorProductos(Principal vistaPrincipal, JpanelProductos jpanelProductos, JpanelProductoNuevo jpanelProductoNuevo, JpanelContenidoPaquete jpanelContenidoPaquete) {
        this.vistaPrincipal = vistaPrincipal;
        this.jpanelProductos = jpanelProductos;
        this.jpanelProductoNuevo = jpanelProductoNuevo;
        this.jpanelContenidoPaquete = jpanelContenidoPaquete;

        jpanelProductoNuevo.jButtonGuardar.addActionListener(this);
        jpanelProductos.jButtonAlta.addActionListener(this);
        vistaPrincipal.jButtonProductos.addActionListener(this);
        this.jpanelProductoNuevo.jComboBoxComoSevende.addActionListener(this);
        this.jpanelContenidoPaquete.jButtonPaqueteAceptar.addActionListener(this);
        this.jpanelContenidoPaquete.jButtonPaqueteAgregar.addActionListener(this);
        this.jpanelContenidoPaquete.jButtonLimpiar.addActionListener(this);
        this.jpanelContenidoPaquete.jButtonGuardar.addActionListener(this);
        contenidoPaqueteList = new ArrayList<>();

        clearErrorLabel();
        mayusculasCodigoBarras();
        llenarComboDepartamento();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == jpanelContenidoPaquete.jButtonGuardar) {
            guardarPaqueteDB();
        }
        if (e.getSource() == jpanelContenidoPaquete.jButtonLimpiar) {
            limpiarTablaProducos();

        }
        if (e.getSource() == this.jpanelContenidoPaquete.jButtonPaqueteAgregar) {
            boolean continuar = true;
            jpanelContenidoPaquete.jLabelErrorCantidadPaquete.setText("");
            jpanelContenidoPaquete.jLabelErrorPrecioPaquete.setText("");

            if ("".equals(jpanelContenidoPaquete.jTextFieldPaqueteNombre.getText().trim())) {
                continuar = false;
                JOptionPane.showMessageDialog(vistaPrincipal, "Seleccione un producto.", "Error", JOptionPane.ERROR_MESSAGE);
            }

            if ("".equals(jpanelContenidoPaquete.jTextFieldPaqueteCantidad.getText().trim())) {
                jpanelContenidoPaquete.jLabelErrorCantidadPaquete.setText("Capture  cantidad");
                continuar = false;
            }

            if ("".equals(jpanelContenidoPaquete.jTextFieldPaquetePrecioVenta.getText().trim())) {
                jpanelContenidoPaquete.jLabelErrorPrecioPaquete.setText("Capture  precio");
                continuar = false;
            }

            if (continuar) {
                try {
                    contenidoPaqueteList.add(new ContenidoPaquete(productoContenido, new BigDecimal(jpanelContenidoPaquete.jTextFieldPaqueteCantidad.getText().trim()), new BigDecimal(jpanelContenidoPaquete.jTextFieldPaquetePrecioVenta.getText().trim()), 0));
                    jpanelContenidoPaquete.jTableContenido.setModel(llenarTablaPaquete());
                } catch (Exception ee) {
                    JOptionPane.showMessageDialog(vistaPrincipal, "Capture Numeros validos", "Error", JOptionPane.ERROR_MESSAGE);
                }

            }

        }
        if (e.getSource() == this.jpanelContenidoPaquete.jButtonPaqueteAceptar) {
            try {
                productoContenido = productoDao.getByCodigoBarras("" + jpanelContenidoPaquete.jTextFieldPaqueteCodigo.getText().trim());
                jpanelContenidoPaquete.jTextFieldPaqueteNombre.setText(productoContenido.getDescripcion());
                jpanelContenidoPaquete.jTextFieldPaquetePrecioCosto.setText("" + productoContenido.getPrecioProveedor());

            } catch (Exception ex) {
                Logger.getLogger(ControladorProductos.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        if (e.getSource() == jpanelProductoNuevo.jComboBoxComoSevende) {

            if (jpanelProductoNuevo.jComboBoxComoSevende.getSelectedIndex() == 2) {

                jpanelProductoNuevo.jLabel5.setVisible(false);
                jpanelProductoNuevo.jLabel7.setVisible(false);
                jpanelProductoNuevo.jLabel8.setVisible(false);
                jpanelProductoNuevo.jLabel11.setVisible(false);
                jpanelProductoNuevo.jLabel10.setVisible(false);
                jpanelProductoNuevo.jTextFieldPrecioCosto.setVisible(false);
                jpanelProductoNuevo.jLabel1ErrorCosto.setVisible(false);

                jpanelProductoNuevo.jTextFieldPrecioMenudeo.setVisible(false);
                jpanelProductoNuevo.jLabel14ErrorPrecioventa.setVisible(false);

                jpanelProductoNuevo.jTextFieldPrecioMayoreo.setVisible(false);
                jpanelProductoNuevo.jLabelErrorPrecioMayoreo.setVisible(false);

                jpanelProductoNuevo.jTextFieldCantidadInventario.setVisible(false);
                jpanelProductoNuevo.jLabelErrorCantidad.setVisible(false);
                jpanelProductoNuevo.jTextFieldMinimoInventario.setVisible(false);
                jpanelProductoNuevo.jLabel1ErrorMinimo.setVisible(false);
                jpanelProductoNuevo.jCheckBoxInventario.setVisible(false);
                // jpanelContenidoPaquete.jPanelContenidoPaquete.setVisible(true);
                jpanelProductoNuevo.jButtonGuardar.setText("Siguiente");
                llenarComboDepartamento();

            } else {

                jpanelProductoNuevo.jLabel5.setVisible(true);
                jpanelProductoNuevo.jLabel7.setVisible(true);
                jpanelProductoNuevo.jLabel8.setVisible(true);
                jpanelProductoNuevo.jLabel11.setVisible(true);
                jpanelProductoNuevo.jLabel10.setVisible(true);
                jpanelProductoNuevo.jTextFieldPrecioCosto.setVisible(true);
                jpanelProductoNuevo.jLabel1ErrorCosto.setVisible(true);

                jpanelProductoNuevo.jTextFieldPrecioMenudeo.setVisible(true);
                jpanelProductoNuevo.jLabel14ErrorPrecioventa.setVisible(true);

                jpanelProductoNuevo.jTextFieldPrecioMayoreo.setVisible(true);
                jpanelProductoNuevo.jLabelErrorPrecioMayoreo.setVisible(true);

                jpanelProductoNuevo.jTextFieldCantidadInventario.setVisible(true);
                jpanelProductoNuevo.jLabelErrorCantidad.setVisible(true);
                jpanelProductoNuevo.jTextFieldMinimoInventario.setVisible(true);
                jpanelProductoNuevo.jLabel1ErrorMinimo.setVisible(true);
                jpanelProductoNuevo.jCheckBoxInventario.setVisible(true);

                jpanelProductoNuevo.jButtonGuardar.setText("Guardar");
                llenarComboDepartamento();
            }

        }
        if (e.getSource() == vistaPrincipal.jButtonProductos || e.getSource() == jpanelProductos.jButtonAlta) {
            show();

        }
        if (e.getSource() == jpanelProductoNuevo.jButtonGuardar) {
            if (jpanelProductoNuevo.jButtonGuardar.getText().equals("Guardar")) {
                guardarProducto();
            } else {
                //Guardar paquete
                guardarPaquete();
            }

        }

    }

    public void show() {
        //muestra panel productos
        vistaPrincipal.jPanelPanelPrincipal.removeAll();
        vistaPrincipal.jPanelPanelPrincipal.setLayout(new java.awt.BorderLayout());
        vistaPrincipal.jPanelPanelPrincipal.add(jpanelProductos);
        vistaPrincipal.jPanelPanelPrincipal.validate();
        vistaPrincipal.jPanelPanelPrincipal.repaint();
        //muestra panel nuevo producto
        jpanelProductos.jPanelPincipal.removeAll();
        jpanelProductos.jPanelPincipal.setLayout(new java.awt.BorderLayout());
        jpanelProductos.jPanelPincipal.add(jpanelProductoNuevo);
        jpanelProductos.jPanelPincipal.validate();
        jpanelProductos.jPanelPincipal.repaint();
        jpanelProductoNuevo.jLabel1ErrorDescripcion.setText("");
           llenarComboDepartamento();
        perderFocus();
    }

    private void guardarProducto() {

        if (guardar()) {

            try {
                int idProducto = productoDao.addProductInt(producto);
                if (idProducto > 0) {
                    if (jpanelProductoNuevo.jComboBoxComoSevende.getSelectedIndex() == 2) {
                        for (ContenidoPaquete item : this.contenidoPaqueteList) {
                            item.setIdPaquete(idProducto);
                            productoDao.addContenidoPaquete(item);
                        }
                        System.out.println("guardar    c   o   n  t     n  i  do del paqu e te " + idProducto);
                    }
                    limpiarFormulario();
                }

            } catch (Exception ex) {
                Logger.getLogger(ControladorProductos.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public boolean guardar() {
        //productoDao = new ProductoDao();
        AltaProductoDao altaProductoDao = new AltaProductoDao();
        clearErrorLabel();

        boolean bandera = true;

        try {
            boolean productoExiste = altaProductoDao.getByCodigoBarras(jpanelProductoNuevo.jTextFieldCodigoBarras.getText());
            if (productoExiste == false) {
                bandera = false;
                jpanelProductoNuevo.jLabel1ErrorCodigoBarras.setText("Este producto ya existe.");
            }

            if (!"".equals(jpanelProductoNuevo.jTextFieldCodigoBarras.getText().trim())) {
                producto.setCodigoBarras(jpanelProductoNuevo.jTextFieldCodigoBarras.getText().trim());

            } else {
                bandera = false;
                jpanelProductoNuevo.jLabel1ErrorCodigoBarras.setText("capture Codigo de Barras");
            }

            if (!"".equals(jpanelProductoNuevo.jTextFieldDescripcion.getText().trim())) {
                producto.setDescripcion(jpanelProductoNuevo.jTextFieldDescripcion.getText().trim());
                producto.setNombre(jpanelProductoNuevo.jTextFieldDescripcion.getText().trim());
            } else {
                bandera = false;
                jpanelProductoNuevo.jLabel1ErrorDescripcion.setText("capture descripción");
            }

            // Unidad, Granel, Paquete
            int seleccion = jpanelProductoNuevo.jComboBoxComoSevende.getSelectedIndex();

            if (seleccion == 0) {
                producto.setComosevende("Unidad");
            } else if (seleccion == 1) {
                producto.setComosevende("Granel");
            } else if (seleccion == 2) {
                producto.setComosevende("Paquete");
            }
            String departamento = jpanelProductoNuevo.jComboBoxDepartamento.getSelectedItem().toString();
            producto.setDepartamento(altaProductoDao.getDepartamento(departamento));
           
            // VALIDAR PAQUETE
            if (jpanelProductoNuevo.jComboBoxComoSevende.getSelectedIndex() == 2) {
                producto.setInventariar(false);
                producto.setCantidad(new BigDecimal("10000000"));
                producto.setMinimo(0);
                BigDecimal precioVentaT = new BigDecimal("0.0");
                BigDecimal precioCostoT = new BigDecimal("0.0");
                for (int i = 0; i < jpanelContenidoPaquete.jTableContenido.getRowCount(); i++) {

                    BigDecimal precioVenta = (BigDecimal) jpanelContenidoPaquete.jTableContenido.getValueAt(i, 2);
                    BigDecimal precioCosto = (BigDecimal) jpanelContenidoPaquete.jTableContenido.getValueAt(i, 3);
                    BigDecimal cantidad = (BigDecimal) jpanelContenidoPaquete.jTableContenido.getValueAt(i, 1);
                    precioCosto = precioCosto.multiply(cantidad);
                    precioVenta = precioVenta.multiply(cantidad);

                    precioVentaT = precioVentaT.add(precioVenta);
                    precioCostoT = precioCostoT.add(precioCosto);
                    System.out.println("Venta " + precioVentaT + "  Costo " + precioCostoT);

                }
                producto.setPrecioVentaUnitario(precioVentaT);
                producto.setPrecioMayoreo(precioVentaT);
                producto.setPrecioProveedor(precioCostoT);

            } else { // VALIDAD UNIDAD - GRANEL
                if (!"".equals(jpanelProductoNuevo.jTextFieldPrecioCosto.getText().trim()) && !".".equals(jpanelProductoNuevo.jTextFieldPrecioCosto.getText().trim())) {
                    producto.setPrecioProveedor(new BigDecimal(jpanelProductoNuevo.jTextFieldPrecioCosto.getText().trim()));

                } else {
                    bandera = false;
                    jpanelProductoNuevo.jLabel1ErrorCosto.setText("Capture precio costo.");
                }

                if (!"".equals(jpanelProductoNuevo.jTextFieldPrecioMenudeo.getText().trim()) && !".".equals(jpanelProductoNuevo.jTextFieldPrecioMenudeo.getText().trim())) {
                    producto.setPrecioVentaUnitario(new BigDecimal(jpanelProductoNuevo.jTextFieldPrecioMenudeo.getText().trim()));

                } else {
                    bandera = false;
                    jpanelProductoNuevo.jLabel14ErrorPrecioventa.setText("Capture precion venta.");
                }
                if (!"".equals(jpanelProductoNuevo.jTextFieldPrecioMayoreo.getText().trim()) && !".".equals(jpanelProductoNuevo.jTextFieldPrecioMayoreo.getText().trim())) {
                    producto.setPrecioMayoreo(new BigDecimal(jpanelProductoNuevo.jTextFieldPrecioMayoreo.getText().trim()));

                } else {
                    bandera = false;
                    jpanelProductoNuevo.jLabelErrorPrecioMayoreo.setText("Capture precio Mayoreo");
                }

                if (jpanelProductoNuevo.jCheckBoxInventario.isSelected()) {
                    if (!"".equals(jpanelProductoNuevo.jTextFieldCantidadInventario.getText().trim())) {
                        producto.setCantidad(new BigDecimal("" + jpanelProductoNuevo.jTextFieldCantidadInventario.getText().trim()));

                    } else {
                        bandera = false;
                        jpanelProductoNuevo.jLabelErrorCantidad.setText("Capture Cantidad");
                    }
                    if (!"".equals(jpanelProductoNuevo.jTextFieldMinimoInventario.getText().trim())) {
                        producto.setMinimo(Integer.parseInt(jpanelProductoNuevo.jTextFieldMinimoInventario.getText().trim()));

                    } else {
                        bandera = false;
                        jpanelProductoNuevo.jLabel1ErrorMinimo.setText("Capture Cantidad");
                    }

                    producto.setInventariar(true);
                } else {
                    producto.setInventariar(false);
                    producto.setCantidad(new BigDecimal("0"));
                    producto.setMinimo(0);
                }
            }

        } catch (Exception ex) {
            Logger.getLogger(ControladorProductos.class.getName()).log(Level.SEVERE, null, ex);

        }
        System.out.println("FIN GUARDAR .........................");
        return bandera;
    }

    private void clearErrorLabel() {
        jpanelProductoNuevo.jLabel14ErrorPrecioventa.setText("");
        jpanelProductoNuevo.jLabel1ErrorCodigoBarras.setText("");
        jpanelProductoNuevo.jLabel1ErrorCosto.setText("");
        jpanelProductoNuevo.jLabel1ErrorDescripcion.setText("");
        jpanelProductoNuevo.jLabel1ErrorMinimo.setText("");
        jpanelProductoNuevo.jLabelErrorCantidad.setText("");
        jpanelProductoNuevo.jLabelErrorPrecioMayoreo.setText("");

    }

    private void limpiarFormulario() {
        jpanelProductoNuevo.jTextFieldCantidadInventario.setText("");
        jpanelProductoNuevo.jTextFieldCodigoBarras.setText("");
        jpanelProductoNuevo.jTextFieldDescripcion.setText("");
//        jpanelProductoNuevo.jTextFieldGanacia.setText("");
        jpanelProductoNuevo.jTextFieldMinimoInventario.setText("");
        jpanelProductoNuevo.jTextFieldPrecioCosto.setText("");
        jpanelProductoNuevo.jTextFieldPrecioMayoreo.setText("");
        jpanelProductoNuevo.jTextFieldPrecioMenudeo.setText("");
        limpiarTablaProducos();
    }

    private void perderFocus() {
        jpanelProductoNuevo.jTextFieldCodigoBarras.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent fe) {
                jpanelProductoNuevo.jLabel1ErrorCodigoBarras.setText("");
            }

            @Override
            public void focusLost(FocusEvent fe) {
                try {
                    Tproducto p = productoDao.getByCodigoBarras(jpanelProductoNuevo.jTextFieldCodigoBarras.getText());
                    System.out.println("p" + p);
                    if (p != null) {
                        jpanelProductoNuevo.jLabel1ErrorCodigoBarras.setText("Este producto ya existe.");
                    }

                } catch (Exception ex) {
                    Logger.getLogger(ControladorProductos.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    private void mayusculasCodigoBarras() {
        jpanelProductoNuevo.jTextFieldCodigoBarras.addKeyListener(new KeyAdapter() {

            public void keyTyped(KeyEvent e) {
                char keyChar = e.getKeyChar();
                if (Character.isLowerCase(keyChar)) {
                    e.setKeyChar(Character.toUpperCase(keyChar));
                }
            }

        });

    }

    private void llenarComboDepartamento() {
        productoDao = new ProductoDao();
        jpanelProductoNuevo.jComboBoxDepartamento.setModel(new DefaultComboBoxModel());
        try {

            List<Departamento> departamentos;
            departamentos = productoDao.getDepartamento();
            for (int i = 0; i < departamentos.size(); i++) {

                jpanelProductoNuevo.jComboBoxDepartamento.addItem("" + departamentos.get(i).getNombre());

            }
        } catch (Exception ex) {
            Logger.getLogger(ControladorProductos.class.getName()).log(Level.SEVERE, null, ex);
        }
        productoDao.cerrar();
    }

    public DefaultTableModel llenarTablaPaquete() {
        tableModel = new DefaultTableModel() {

            @Override
            public boolean isCellEditable(int row, int column) {
                //all cells false
                return false;
            }
        };

        String[] columnNames = {"CODIGO", "CANTIDAD", "PRECIO VENTA", "PRECIO COSTO"};
        tableModel.setColumnIdentifiers(columnNames);
        Object[] fila = new Object[tableModel.getColumnCount()];

        for (int i = 0; i < contenidoPaqueteList.size(); i++) {
            fila[0] = contenidoPaqueteList.get(i).getTproducto().getCodigoBarras();
            fila[1] = contenidoPaqueteList.get(i).getCantidad();
            fila[2] = contenidoPaqueteList.get(i).getPrecioUnitario();
            fila[3] = contenidoPaqueteList.get(i).getTproducto().getPrecioProveedor();
            tableModel.addRow(fila);

        }
        return tableModel;
    }

    private void limpiarTablaProducos() {
        contenidoPaqueteList = new ArrayList<>();
        if (tableModel.getRowCount() > 0) {
            for (int i1 = tableModel.getRowCount() - 1; i1 > -1; i1--) {
                tableModel.removeRow(i1);
            }
        }
        jpanelContenidoPaquete.jTableContenido.setModel(tableModel);
    }

    private void guardarPaquete() {
        jpanelContenidoPaquete.jLabelErrorCantidadPaquete.setText("");
        jpanelContenidoPaquete.jLabelErrorPrecioPaquete.setText("");
        boolean bandera = true;

        try {

            Tproducto p = productoDao.getByCodigoBarras(jpanelProductoNuevo.jTextFieldCodigoBarras.getText());
            System.out.println("p" + p);
            if (p != null) {
                bandera = false;
                jpanelProductoNuevo.jLabel1ErrorCodigoBarras.setText("Este producto ya existe.");
            }
            if (!"".equals(jpanelProductoNuevo.jTextFieldCodigoBarras.getText().trim())) {
                producto.setCodigoBarras(jpanelProductoNuevo.jTextFieldCodigoBarras.getText().trim());

            } else {
                bandera = false;
                jpanelProductoNuevo.jLabel1ErrorCodigoBarras.setText("capture Codigo de Barras");
            }

            if (!"".equals(jpanelProductoNuevo.jTextFieldDescripcion.getText().trim())) {
                producto.setDescripcion(jpanelProductoNuevo.jTextFieldDescripcion.getText().trim());
                producto.setNombre(jpanelProductoNuevo.jTextFieldDescripcion.getText().trim());
            } else {
                bandera = false;
                jpanelProductoNuevo.jLabel1ErrorDescripcion.setText("capture descripción");
            }
            System.out.println("Paquete x");
            if (bandera == true) {
                //muestra panel nuevo producto
                jpanelProductos.jPanelPincipal.removeAll();
                jpanelProductos.jPanelPincipal.setLayout(new java.awt.BorderLayout());
                jpanelProductos.jPanelPincipal.add(jpanelContenidoPaquete);
                jpanelProductos.jPanelPincipal.validate();
                jpanelProductos.jPanelPincipal.repaint();
            }
        } catch (Exception ex) {
            Logger.getLogger(ControladorProductos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void guardarPaqueteDB() {

        if (contenidoPaqueteList.size() > 0) {
            if (guardar()) {

                try {
                    int idProducto = productoDao.addProductInt(producto);
                    if (idProducto > 0) {
                        if (jpanelProductoNuevo.jComboBoxComoSevende.getSelectedIndex() == 2) {
                            for (ContenidoPaquete item : this.contenidoPaqueteList) {
                                item.setIdPaquete(idProducto);
                                productoDao.addContenidoPaquete(item);
                            }
                            System.out.println("guardar    c   o   n  t     n  i  do del paqu e te " + idProducto);
                        }
                        limpiarFormulario();
                    }

                } catch (Exception ex) {
                    Logger.getLogger(ControladorProductos.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else {

            JOptionPane.showMessageDialog(vistaPrincipal, "Agrege productos al paquete", "Error", JOptionPane.ERROR_MESSAGE);

        }

    }
}
