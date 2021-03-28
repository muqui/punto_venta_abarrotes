/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import dao.ModificarProductoDao;
import dao.ProductoDao;
import java.awt.Button;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.ContenidoPaquete;
import modelo.Departamento;
import modelo.Tproducto;
import vista.JpanelProductos;
import vista.Principal;
import vista.producto.JDialogBuscarProductoModificar;
import vista.producto.JDialogModificarPaquete;
import vista.producto.JpanelProductoModificar;

/**
 *
 * @author mq12
 */
public class ControladorProductosModificar implements ActionListener, KeyListener, MouseListener {

    Principal vistaPrincipal;
    JpanelProductos jpanelProductos;
    Tproducto producto, productoPaquete, productoUnidad, productoContenido;
    JpanelProductoModificar jpanelProductoModificar;
    JDialogBuscarProductoModificar jDialogBuscarProductoModificar;
    JDialogModificarPaquete jDialogModificarPaquete;
    ModificarProductoDao modificarProductoDao = new ModificarProductoDao();
    ProductoDao productoDao = new ProductoDao();
           
    List<ContenidoPaquete> contenidoPaqueteList = new ArrayList<>();
    List<Tproducto> productos;
    BigDecimal CostoTotalActual = new BigDecimal("0.00");
    BigDecimal CostoTotalNuevo = new BigDecimal("0.00");
    BigDecimal CostoTotalPromediado = new BigDecimal("0.00");
    BigDecimal cantidadTotalSumada = new BigDecimal("0.00");
    BigDecimal cantidadTotalActual = new BigDecimal("0.00");
    BigDecimal cantidadTotalNuevo = new BigDecimal("0.00");
    DefaultTableModel tableModel = new DefaultTableModel();

    public ControladorProductosModificar(Principal vistaPrincipal, JpanelProductos jpanelProductos, JpanelProductoModificar jpanelProductoModificar, JDialogBuscarProductoModificar jDialogBuscarProductoModificar, JDialogModificarPaquete jDialogModificarPaquete) {
        this.vistaPrincipal = vistaPrincipal;
        this.jpanelProductos = jpanelProductos;
        this.jpanelProductoModificar = jpanelProductoModificar;
        this.jDialogBuscarProductoModificar = jDialogBuscarProductoModificar;
        this.jDialogModificarPaquete = jDialogModificarPaquete;
        this.jpanelProductos.jButtonModificar.addActionListener(this);
        this.jpanelProductoModificar.jButtonBuscar.addActionListener(this);
        this.jpanelProductoModificar.jTextFieldCaptureCodigoBarras.addKeyListener(this);
        jDialogBuscarProductoModificar.jTextFieldBuscar.addKeyListener(this);
        this.jpanelProductoModificar.jButtonGuardar.addActionListener(this);
        this.jDialogModificarPaquete.jButtonGuardar.addActionListener(this);
        this.jDialogModificarPaquete.jButtonPaqueteAceptar.addActionListener(this);
        this.jDialogModificarPaquete.jButtonPaqueteAgregar.addActionListener(this);
        this.jDialogModificarPaquete.jButtonLimpiar.addActionListener(this);
        this.jDialogBuscarProductoModificar.jButtonAceptar.addActionListener(this);
         jDialogBuscarProductoModificar.jTableProductos.addMouseListener(this);  // inicia escuchador
        limpiarCamposActualizar();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == jpanelProductos.jButtonModificar) {
            show();
        }
        if (e.getSource() == this.jpanelProductoModificar.jButtonGuardar) {
            modificarUnidad();
        }
        if (e.getSource() == jDialogModificarPaquete.jButtonGuardar) {
            modificarPaquete();
        }
        if (e.getSource() == this.jDialogModificarPaquete.jButtonPaqueteAceptar) {
            try {
                boolean bandera = true;
                productoContenido = productoDao.getByCodigoBarras("" + jDialogModificarPaquete.jTextFieldPaqueteCodigo.getText().trim());
                System.out.println("productpo " + productoContenido);

                if (productoContenido == null) {
                    JOptionPane.showMessageDialog(vistaPrincipal, "Este producto no existe", "Error", JOptionPane.ERROR_MESSAGE);
                    bandera = false;
                }

                if (productoContenido != null && productoContenido.getComosevende().equalsIgnoreCase("Paquete")) {
                    JOptionPane.showMessageDialog(vistaPrincipal, "Este producto es un paquete", "Error", JOptionPane.ERROR_MESSAGE);
                    bandera = false;
                }

                if (bandera) {
                    jDialogModificarPaquete.jTextFieldPaqueteNombre.setText(productoContenido.getDescripcion());
                    jDialogModificarPaquete.jTextFieldPaquetePrecioCosto.setText("" + productoContenido.getPrecioProveedor());
                }

                
            } catch (Exception ex) {
                Logger.getLogger(ControladorProductos.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        if (e.getSource() == this.jDialogModificarPaquete.jButtonPaqueteAgregar) {
            boolean continuar = true;
            jDialogModificarPaquete.jLabelErrorCantidadPaquete.setText("");
            jDialogModificarPaquete.jLabelErrorPrecioPaquete.setText("");

            if ("".equals(jDialogModificarPaquete.jTextFieldPaqueteNombre.getText().trim())) {
                continuar = false;
                JOptionPane.showMessageDialog(vistaPrincipal, "Seleccione un producto.", "Error", JOptionPane.ERROR_MESSAGE);
            }

            if ("".equals(jDialogModificarPaquete.jTextFieldPaqueteCantidad.getText().trim())) {
                jDialogModificarPaquete.jLabelErrorCantidadPaquete.setText("Capture  cantidad");
                continuar = false;
            }

            if ("".equals(jDialogModificarPaquete.jTextFieldPaquetePrecioVenta.getText().trim())) {
                jDialogModificarPaquete.jLabelErrorPrecioPaquete.setText("Capture  precio");
                continuar = false;
            }

            if (continuar) {
                try {
                    contenidoPaqueteList.add(new ContenidoPaquete(productoContenido, new BigDecimal(jDialogModificarPaquete.jTextFieldPaqueteCantidad.getText().trim()), new BigDecimal(jDialogModificarPaquete.jTextFieldPaquetePrecioVenta.getText().trim()), 0));
                    jDialogModificarPaquete.jTableContenido.setModel(llenarTablaPaquete());
                } catch (Exception ee) {

                    System.out.println("errorr " + ee);
                    JOptionPane.showMessageDialog(vistaPrincipal, "Capture Numeros validos" + continuar, "Error", JOptionPane.ERROR_MESSAGE);
                }

            }
        }
        if (e.getSource() == jDialogModificarPaquete.jButtonLimpiar) {
            limpiarTablaProductos();

        }
        if (e.getSource() == jpanelProductoModificar.jButtonBuscar) {

            jDialogBuscarProductoModificar.setVisible(true);
        }
        if (e.getSource() == jDialogBuscarProductoModificar.jButtonAceptar) {

            int selected = jDialogBuscarProductoModificar.jTableProductos.getSelectedRow();
            modificar("" + jDialogBuscarProductoModificar.jTableProductos.getValueAt(selected, 0));
            jDialogBuscarProductoModificar.setVisible(false);
        }
    }

    @Override
    public void keyTyped(KeyEvent ke) {

    }

    @Override
    public void keyPressed(KeyEvent ke) {

        if (ke.getSource() == jpanelProductoModificar.jTextFieldCaptureCodigoBarras) {
            if (ke.getKeyCode() == KeyEvent.VK_ENTER) {
                modificar(jpanelProductoModificar.jTextFieldCaptureCodigoBarras.getText().trim());
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        if (ke.getSource() == jDialogBuscarProductoModificar.jTextFieldBuscar) {
            productosPorNombre(jDialogBuscarProductoModificar.jTextFieldBuscar.getText());
        }

    }

    private void show() {
        //muestra panel productos
        vistaPrincipal.jPanelPanelPrincipal.removeAll();
        vistaPrincipal.jPanelPanelPrincipal.setLayout(new java.awt.BorderLayout());
        vistaPrincipal.jPanelPanelPrincipal.add(jpanelProductos);
        vistaPrincipal.jPanelPanelPrincipal.validate();
        vistaPrincipal.jPanelPanelPrincipal.repaint();
        //muestra panel nuevo producto
        jpanelProductos.jPanelPincipal.removeAll();
        jpanelProductos.jPanelPincipal.setLayout(new java.awt.BorderLayout());
        jpanelProductos.jPanelPincipal.add(jpanelProductoModificar);
        jpanelProductos.jPanelPincipal.validate();
        jpanelProductos.jPanelPincipal.repaint();
        jpanelProductoModificar.jLabel1ErrorDescripcion.setText("");
        llenarComboDepartamento();

    }

    public void llenarComboDepartamento() {
        try {
            jpanelProductoModificar.jComboBoxDepartamento.setModel(new DefaultComboBoxModel());
            List<Departamento> departamentos;
            departamentos = modificarProductoDao.getDepartamento();
            for (int i = 0; i < departamentos.size(); i++) {
                jpanelProductoModificar.jComboBoxDepartamento.addItem("" + departamentos.get(i).getNombre());

            }
           
        } catch (Exception ex) {
            Logger.getLogger(ControladorProductos.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void modificar(String codigo) {
        try {
            producto = productoDao.getByCodigoBarras(codigo);

            if (producto.getComosevende().equalsIgnoreCase("Paquete")) {
                System.out.println("paquete");
                productoPaquete = producto;
                jDialogModificarPaquete.jTextFieldCodigoBarras.setText(productoPaquete.getCodigoBarras());
                jDialogModificarPaquete.jTextFieldDescripcion.setText(productoPaquete.getDescripcion());
                productoDao.cerrar();
                jDialogModificarPaquete.setVisible(true);
            } else {
                System.out.println("NO paquete");
                productoUnidad = producto;
                jpanelProductoModificar.jTextFieldCodigoBarras.setText(producto.getCodigoBarras());
                jpanelProductoModificar.jTextFieldPrecioCosto.setText("" + productoUnidad.getPrecioProveedor());
                jpanelProductoModificar.jTextFieldCantidadInventario.setText("" + productoUnidad.getCantidad());
                jpanelProductoModificar.jTextFieldDescripcion.setText(productoUnidad.getDescripcion());
                jpanelProductoModificar.jTextFieldPrecioMenudeo.setText("" + productoUnidad.getPrecioVentaUnitario());
                jpanelProductoModificar.jTextFieldPrecioMayoreo.setText("" + productoUnidad.getPrecioMayoreo());
                jpanelProductoModificar.jTextFieldMinimoInventario.setText("" + productoUnidad.getMinimo());
                jpanelProductoModificar.jComboBoxDepartamento.setSelectedItem(productoUnidad.getDepartamento().getNombre());
                if (productoUnidad.getComosevende().equals("Unidad")) {
                    jpanelProductoModificar.jRadioButtonUnidad.setSelected(true);
                }
                if (productoUnidad.getComosevende().equals("Granel")) {
                    jpanelProductoModificar.jRadioButtonGranel.setSelected(true);
                }
                if (productoUnidad.getComosevende().equals("Paquete")) {
                    jpanelProductoModificar.jRadioButtonPaquete.setSelected(true);
                }
                productoDao.cerrar();
            }

        } catch (Exception ex) {

            Logger.getLogger(ControladorProductosModificar.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void modificarPaquete() {
        boolean bandera = true;

        if (jDialogModificarPaquete.jTextFieldDescripcion.getText().trim().equals("")) {
            jDialogModificarPaquete.jLabelErrorDescripcion.setText("Capture una descripcion");
            bandera = false;
        } else {
            jDialogModificarPaquete.jLabelErrorDescripcion.setText("");
        }
        if (contenidoPaqueteList.size() <= 0) {
            bandera = false;
            JOptionPane.showMessageDialog(vistaPrincipal, "Agrege productos al paquete", "Error", JOptionPane.ERROR_MESSAGE);
        }
        if (bandera) {
            productoPaquete.setInventariar(false);
            productoPaquete.setCantidad(new BigDecimal("0.00"));
            productoPaquete.setMinimo(0);
            BigDecimal precioVentaT = new BigDecimal("0.0");
            BigDecimal precioCostoT = new BigDecimal("0.0");
            for (int i = 0; i < jDialogModificarPaquete.jTableContenido.getRowCount(); i++) {

                BigDecimal precioVenta = (BigDecimal) jDialogModificarPaquete.jTableContenido.getValueAt(i, 2);
                BigDecimal precioCosto = (BigDecimal) jDialogModificarPaquete.jTableContenido.getValueAt(i, 3);
                BigDecimal cantidad = (BigDecimal) jDialogModificarPaquete.jTableContenido.getValueAt(i, 1);
                precioCosto = precioCosto.multiply(cantidad);
                precioVenta = precioVenta.multiply(cantidad);

                precioVentaT = precioVentaT.add(precioVenta);
                precioCostoT = precioCostoT.add(precioCosto);
                System.out.println("Venta " + precioVentaT + "  Costo " + precioCostoT);
            }
            productoPaquete.setDescripcion(jDialogModificarPaquete.jTextFieldDescripcion.getText().trim());
            productoPaquete.setNombre(jDialogModificarPaquete.jTextFieldDescripcion.getText().trim());
            productoPaquete.setPrecioVentaUnitario(precioVentaT);
            productoPaquete.setPrecioMayoreo(precioVentaT);
            productoPaquete.setPrecioProveedor(precioCostoT);

            modificarProductoDao.modificarProducto(productoPaquete);
            modificarProductoDao.borrarContenidoPaquete(productoPaquete.getIdProducto());
            for (ContenidoPaquete item : this.contenidoPaqueteList) {
                item.setIdPaquete(productoPaquete.getIdProducto());
                productoDao.addContenidoPaquete(item);
            }
            jDialogModificarPaquete.setLocationRelativeTo(null);
            jDialogModificarPaquete.setVisible(false);

        }
        limpiarTablaProductos();

        //////////////////////////////////////////
        // productoDao.modificarProducto(productoPaquete);
    }

    public void modificarUnidad() {
        boolean bandera = true;

        CostoTotalActual = productoUnidad.getPrecioProveedor().multiply(productoUnidad.getCantidad());
        cantidadTotalActual = productoUnidad.getCantidad();

        if (jpanelProductoModificar.jTextFieldDescripcion.getText().trim().equals("")) {
            jpanelProductoModificar.jLabel1ErrorDescripcion.setText("Capture una descripcion");
            bandera = false;
        } else {
            productoUnidad.setDescripcion(jpanelProductoModificar.jTextFieldDescripcion.getText().trim());
            productoUnidad.setNombre(jpanelProductoModificar.jTextFieldDescripcion.getText().trim());
            jpanelProductoModificar.jLabel1ErrorDescripcion.setText("");
        }
        if (jpanelProductoModificar.jTextFieldPrecioCosto.getText().trim().equals("")) {
            jpanelProductoModificar.jLabel1ErrorCosto.setText("Capture precio costo");
            bandera = false;
        } else {
            productoUnidad.setPrecioProveedor(new BigDecimal(jpanelProductoModificar.jTextFieldPrecioCosto.getText().trim()));
            jpanelProductoModificar.jLabel1ErrorCosto.setText("");
        }
        if (jpanelProductoModificar.jTextFieldPrecioMenudeo.getText().trim().equals("")) {
            jpanelProductoModificar.jLabel14ErrorPrecioventa.setText("Capture precio venta");
            bandera = false;
        } else {
            jpanelProductoModificar.jLabel14ErrorPrecioventa.setText("");
            productoUnidad.setPrecioVentaUnitario(new BigDecimal(jpanelProductoModificar.jTextFieldPrecioMenudeo.getText().trim()));
        }
        if (jpanelProductoModificar.jTextFieldPrecioMayoreo.getText().trim().equals("")) {
            jpanelProductoModificar.jLabelErrorPrecioMayoreo.setText("capture precio mayoreo");
            bandera = false;

        } else {
            productoUnidad.setPrecioMayoreo(new BigDecimal(jpanelProductoModificar.jTextFieldPrecioMayoreo.getText().trim()));
            jpanelProductoModificar.jLabelErrorPrecioMayoreo.setText("");
        }
        if (jpanelProductoModificar.jCheckBoxInventario.isSelected()) {
            if (jpanelProductoModificar.jTextFieldCantidadInventario.getText().trim().equals("")) {
                jpanelProductoModificar.jLabelErrorCantidad.setText("capture cantidad");
                bandera = false;
            } else {
                productoUnidad.setCantidad(new BigDecimal(jpanelProductoModificar.jTextFieldCantidadInventario.getText().trim()));
                jpanelProductoModificar.jLabelErrorCantidad.setText("");
            }
            if (jpanelProductoModificar.jTextFieldMinimoInventario.getText().trim().equals("")) {
                jpanelProductoModificar.jLabel1ErrorMinimo.setText("capture minimo");
                bandera = false;
            } else {
                productoUnidad.setMinimo(Integer.parseInt(jpanelProductoModificar.jTextFieldMinimoInventario.getText().trim()));
                jpanelProductoModificar.jLabel1ErrorMinimo.setText("");
            }

        } else {
            productoUnidad.setMinimo(0);
            productoUnidad.setCantidad(new BigDecimal("0.00"));

        }

        String departamento = jpanelProductoModificar.jComboBoxDepartamento.getSelectedItem().toString();
        productoUnidad.setDepartamento(productoDao.getDepartamento(departamento));
        // Unidad, Granel, Paquete
        if (jpanelProductoModificar.jRadioButtonUnidad.isSelected()) {
            productoUnidad.setComosevende("Unidad");
        } else if (jpanelProductoModificar.jRadioButtonPaquete.isSelected()) {
            productoUnidad.setComosevende("Paquete");
        } else if (jpanelProductoModificar.jRadioButtonGranel.isSelected()) {
            productoUnidad.setComosevende("Granel");
        }

        CostoTotalNuevo = productoUnidad.getCantidad().multiply(productoUnidad.getPrecioProveedor());
        CostoTotalNuevo = CostoTotalNuevo.setScale(2, BigDecimal.ROUND_HALF_UP);
        cantidadTotalNuevo = productoUnidad.getCantidad();
        System.out.println("CostoTotalActual " + CostoTotalActual + "cantidad actual " + cantidadTotalActual);
        System.out.println("CostoTotalNuevo " + CostoTotalNuevo + "cantidadTotalNuevo " + cantidadTotalNuevo);
        CostoTotalPromediado = CostoTotalActual.add(CostoTotalNuevo);
        CostoTotalPromediado = CostoTotalPromediado.setScale(2, BigDecimal.ROUND_HALF_UP);
        cantidadTotalSumada = cantidadTotalActual.add(cantidadTotalNuevo);
        //AÑADIR PRODUCTOS
        if (jpanelProductoModificar.jComboBoxMovimiento.getSelectedIndex() == 0) {
            cantidadTotalSumada = cantidadTotalSumada.setScale(2, BigDecimal.ROUND_HALF_UP);
            productoUnidad.setPrecioProveedor(CostoTotalPromediado.divide(cantidadTotalSumada, 2, RoundingMode.HALF_UP));
            System.out.println(" !!!!!!!!!!!!!!!!!!!!!!!!!!!!   TOTAL COSTO ACTUAL: " + CostoTotalActual + " cantidad: " + cantidadTotalActual);
            System.out.println(" !!!!!!!!!!!!!!!!!!!!!!!!!!!!   TOTAL COSTO NUEVO: " + CostoTotalNuevo + " cantidad por ingresar: " + cantidadTotalNuevo);
            System.out.println(" !!!!!!!!!!!!!!!!!!!!!!!!!!!!   TOTAL COSTO calculado: " + CostoTotalPromediado + " cantidad Total Sumada: " + cantidadTotalSumada);

            productoUnidad.setCantidad(cantidadTotalSumada);
        }
        //MODIFICAR PRECIO 
        if (jpanelProductoModificar.jComboBoxMovimiento.getSelectedIndex() == 1) {
            productoUnidad.setPrecioProveedor(new BigDecimal(jpanelProductoModificar.jTextFieldPrecioCosto.getText().trim()));

        }
        //  MODIFICAR CANTIDAD
        if (jpanelProductoModificar.jComboBoxMovimiento.getSelectedIndex() == 2) {

            productoUnidad.setCantidad(new BigDecimal(jpanelProductoModificar.jTextFieldCantidadInventario.getText().trim()));

        }
        if (bandera) {
            modificarProductoDao.modificarProducto(productoUnidad);
            limpiarCamposActualizar();
        }

    }

    private void limpiarTablaProductos() {
        contenidoPaqueteList = new ArrayList<>();
        if (tableModel.getRowCount() > 0) {
            for (int i1 = tableModel.getRowCount() - 1; i1 > -1; i1--) {
                tableModel.removeRow(i1);
            }
        }
        jDialogModificarPaquete.jTableContenido.setModel(tableModel);
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

    private void limpiarCamposActualizar() {
        jpanelProductoModificar.jTextFieldCaptureCodigoBarras.setText("");
        jpanelProductoModificar.jTextFieldCodigoBarras.setText("");
        jpanelProductoModificar.jTextFieldPrecioCosto.setText("");
        jpanelProductoModificar.jTextFieldCantidadInventario.setText("");
        jpanelProductoModificar.jTextFieldDescripcion.setText("");
        jpanelProductoModificar.jTextFieldPrecioMenudeo.setText("");
        jpanelProductoModificar.jTextFieldPrecioMayoreo.setText("");
        jpanelProductoModificar.jTextFieldMinimoInventario.setText("");
//        jpanelProductoModificar.jComboBoxDepartamento.setSelectedItem("");

        jpanelProductoModificar.jLabel1ErrorCodigoBarras.setText("");
        jpanelProductoModificar.jLabel1ErrorDescripcion.setText("");
        jpanelProductoModificar.jLabel1ErrorCosto.setText("");
        jpanelProductoModificar.jLabel14ErrorPrecioventa.setText("");
        jpanelProductoModificar.jLabelErrorPrecioMayoreo.setText("");
        jpanelProductoModificar.jLabelErrorCantidad.setText("");
        jpanelProductoModificar.jLabel1ErrorMinimo.setText("");

    }

    private void productosPorNombre(String text) {

        try {
            productos = productoDao.getPorNombre(text);
            System.out.println("Productos " + productos);
            jDialogBuscarProductoModificar.jTableProductos.setModel(llenarTabla());
            jDialogBuscarProductoModificar.jTableProductos.setRowSelectionInterval(0, 0);
            jDialogBuscarProductoModificar.jTableProductos.setDefaultRenderer(Object.class, new helpers.Render() ); // permite anadir boton a la tabla
            jDialogBuscarProductoModificar.jTableProductos.setRowHeight(40);
//            jDialogBuscarProductoModificar.jTableProductos.getColumnModel().getColumn(4).setMaxWidth(300);// por razon desconozida pongo doble esta linea
//            jDialogBuscarProductoModificar.jTableProductos.getColumnModel().getColumn(4).setMaxWidth(300);// por razon desconozida pongo doble esta linea
        } catch (Exception ex) {
            Logger.getLogger(ControladorProductosModificar.class.getName()).log(Level.SEVERE, null, ex);
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

        String[] columnNames = {"CODIGO_1", "NOMBRE", "PRECIO", "CANTIDAD", "HABILITADO"};
        tableModel.setColumnIdentifiers(columnNames);
        Object[] fila = new Object[tableModel.getColumnCount()];
      
        for (int i = 0; i < productos.size(); i++) {
             
             boolean habilitado = productos.get(i).getHabilitado();
             JButton sumar =  habilitado ? new JButton("Habilitado") : new JButton("Deshabilitado");
              
             System.out.println("Habilitado 0 ? " + habilitado);
            

            fila[0] = productos.get(i).getCodigoBarras();
            fila[1] = productos.get(i).getNombre();
            fila[2] = productos.get(i).getPrecioVentaUnitario();
            fila[3] = productos.get(i).getCantidad();
           
             
            fila[4] = sumar;
            tableModel.addRow(fila);
            
        }
        return tableModel;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("Mouse producto");
                
           int  column= jDialogBuscarProductoModificar.jTableProductos.getColumnModel().getColumnIndexAtX(e.getX());
        int row = e.getY() / jDialogBuscarProductoModificar.jTableProductos.getRowHeight();
          
           
        if (row < jDialogBuscarProductoModificar.jTableProductos.getRowCount() && row >= 0 && column < jDialogBuscarProductoModificar.jTableProductos.getColumnCount() && column >=0 ) {
            
            Object value = jDialogBuscarProductoModificar.jTableProductos.getValueAt(row, column);
            Object codigoBarras =  jDialogBuscarProductoModificar.jTableProductos.getValueAt(row, 0);
            
            if(value instanceof JButton){
              ((JButton)value).doClick();
               JButton boton = (JButton) value;
               
              Tproducto producto = productoDao.getByCodigoBarras(codigoBarras.toString()); // regresa el producto
              productoDao.cerrar();
              producto.setHabilitado(!producto.getHabilitado());
              String titulo =  producto.getHabilitado() ?  "Habilitado":  "Deshabilitado";
              modificarProductoDao.modificarProducto(producto);
                boton.setText(titulo);
         }

        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
       
    }

    @Override
    public void mouseReleased(MouseEvent e) {
       
    }

    @Override
    public void mouseEntered(MouseEvent e) {
       
    }

    @Override
    public void mouseExited(MouseEvent e) {
      
    }
}
