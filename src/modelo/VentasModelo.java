/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import dao.ProductoDao;
import dao.VentaDao;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import vista.JDialogVentaAgranel;
import vista.JDialogVentaFinal;
import vista.JPanelTicket;
import vista.JpanelVentas;
import vista.Principal;

/**
 *
 * @author mq12
 */
public class VentasModelo implements ActionListener, KeyListener  {

    JDialogVentaAgranel jDialogVentaAgranel;
    JDialogVentaFinal jDialogVentaFinal;
    JPanelTicket jPanelTicket;
    JpanelVentas jpanelVentas;
    ArrayList<JPanelTicket> jPanelTicketArray;
    ArrayList<DefaultTableModel> defaultTableModelArray;

    ProductoDao productoDao;
    BigDecimal cantidad = new BigDecimal("0.00");
    BigDecimal precio = new BigDecimal("0.00");
    BigDecimal precioUnitario = new BigDecimal("0.00");
    boolean efectivo;
    DefaultTableModel defaultableModelTicket;
    List<Tventadetalle> listaVentaDetalle;
    List<Formadepago> listFormaDePago;
    Tventa venta;
    VentaDao ventaDao;
    // Usuario usuario;
    Principal vistaPrincipal;
    List<Tproducto> productos;

    public VentasModelo(JDialogVentaAgranel jDialogVentaAgranel, JDialogVentaFinal jDialogVentaFinal, Principal vistaPrincipal, JPanelTicket jPanelTicket, JpanelVentas jpanelVentas, ArrayList<JPanelTicket> jPanelTicketArray, ArrayList<DefaultTableModel> defaultTableModelArray) {
        this.jDialogVentaAgranel = jDialogVentaAgranel;
        this.jDialogVentaFinal = jDialogVentaFinal;
        this.jPanelTicket = jPanelTicket;
        this.jpanelVentas = jpanelVentas;

        this.vistaPrincipal = vistaPrincipal;
        this.jPanelTicketArray = jPanelTicketArray;
        this.defaultTableModelArray = defaultTableModelArray;
        listaVentaDetalle = new ArrayList<>();
        listFormaDePago = new ArrayList<>();
        venta = new Tventa();
        productos = new ArrayList<>();
        productoDao = new ProductoDao();

        this.jDialogVentaAgranel.jButtonAceptar.addActionListener(this);
        this.jDialogVentaFinal.jTextFieldPagoCon.addKeyListener(this);
        this.jDialogVentaAgranel.jTextFieldImporte.addKeyListener(this);
        this.jDialogVentaAgranel.jTextFieldCantidad.addKeyListener(this);
        this.jDialogVentaFinal.Cobrar.addActionListener(this);
        this.jDialogVentaFinal.jButtonCancelar.addActionListener(this);
    }

    public DefaultTableModel productos(String codigo, boolean precioVentaMenudeo, JTable table, DefaultTableModel defaultTableModel, BigDecimal cantidad, boolean masde1producto) {

        ProductoDao productoDaoTicket = new ProductoDao();
        Tproducto productoTicket = productoDaoTicket.getByCodigoBarras(codigo);
        if (productoTicket == null) {
            JOptionPane.showMessageDialog(null, "Este producto no existe.", "Error", JOptionPane.ERROR_MESSAGE);

            return defaultTableModel;
        }
        if(productoTicket.getHabilitado() == false){
             JOptionPane.showMessageDialog(null, "Producto Desabilitado.", "Error", JOptionPane.ERROR_MESSAGE);

            return defaultTableModel;
        }
        this.cantidad = cantidad;
      

        if (precioVentaMenudeo) {
            precio = productoTicket.getPrecioVentaUnitario();
            precioUnitario = productoTicket.getPrecioVentaUnitario();

            //   System.out.println(" precio " + precio + "precio menudeo  " + precioVentaMenudeo);
        } else {

            precio = productoTicket.getPrecioMayoreo();
            precioUnitario = productoTicket.getPrecioMayoreo();

            System.out.println(" precio " + precio + "precio menudeo  " + precioVentaMenudeo);
        }

        if (!masde1producto && productoTicket.getComosevende().equals("Granel")) {
            if (productoTicket.getComosevende().equals("Granel")) {
                jDialogVentaAgranel.pack();
                jDialogVentaAgranel.setLocationRelativeTo(null);
                jDialogVentaAgranel.setVisible(true);

                System.out.println("como se vende:  " + productoTicket.getComosevende());
            }
        }
        if (masde1producto && productoTicket.getComosevende().equals("Unidad") && this.cantidad.remainder(new BigDecimal(1)) != BigDecimal.ZERO) {
            JOptionPane.showMessageDialog(null, "Este producto se vende por pieza.", "error", JOptionPane.ERROR_MESSAGE);
            System.out.println("esto esta prohibod cantidad " + this.cantidad);
            return defaultTableModel;
        }
        productoDaoTicket.cerrar();
        int existe = existeProducto(codigo, defaultTableModel);
        if (existe < 0) {
            precio = precio.multiply(this.cantidad);
            precio = precio.setScale(1, BigDecimal.ROUND_HALF_UP);
            JButton sumar = new JButton("");
             sumar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/mas_1.jpg"))); 
            sumar.setName("+");
            JButton restar = new JButton("");
            restar.setName("-");
             restar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/menos_1.jpg"))); 
            Object[] name = new Object[]{codigo, productoTicket.getNombre(), precioUnitario, this.cantidad, precio, productoTicket.getComosevende(), sumar, restar};
            defaultTableModel.addRow(name);
        } else {
             System.out.println("18/03/2021 this.cantidad " + cantidad);
            cambiarcantidad(existe, defaultTableModel, precioUnitario, this.cantidad);
            
        }

        return defaultTableModel;
    }

    public int existeProducto(String codigo, DefaultTableModel tableModelVentas) {
        int posision = -1;
        if (tableModelVentas.getColumnCount() > 0) {
            for (int i = 0; i < tableModelVentas.getRowCount(); i++) {
                if (codigo.equals(tableModelVentas.getValueAt(i, 0))) {
                    posision = i;

                }
            }
        }
        return posision;
    }

    public DefaultTableModel cambiarcantidad(int posicion, DefaultTableModel tableModelVentas, BigDecimal precio, BigDecimal incremento) {
        BigDecimal cantidad = (BigDecimal) tableModelVentas.getValueAt(posicion, 3);
       
       
           
        if ( cantidad.compareTo(new BigDecimal("1")) == 1) {
             cantidad = cantidad.add(incremento);
        }else
          if ( cantidad.compareTo(new BigDecimal("1")) == 0) {
              System.out.println(" es uno oooooooooo"); 
              if(incremento.compareTo(new BigDecimal("1")) == 0){
                  cantidad = cantidad.add(new BigDecimal("1"));
              }
              else
                  
              cantidad = cantidad.add(new BigDecimal("0"));
        }
        
          
             System.out.println("Cantidad delmodelo " + cantidad);
           System.out.println("Incremento " + incremento);
       
        BigDecimal totalVentaPorProducto = precio;
        totalVentaPorProducto = totalVentaPorProducto.multiply(cantidad);
        totalVentaPorProducto = totalVentaPorProducto.setScale(1, BigDecimal.ROUND_HALF_UP);
        tableModelVentas.setValueAt(cantidad, posicion, 3);
        tableModelVentas.setValueAt(totalVentaPorProducto, posicion, 4);

       
        
        
         return tableModelVentas;
         
       
    }

    public BigDecimal totalTicket(DefaultTableModel tableModelVentas) {
        BigDecimal totalVenta = new BigDecimal("0");
        try {
            for (int i = 0; i < tableModelVentas.getRowCount(); i++) {

                BigDecimal totalVentaPorProducto = (BigDecimal) tableModelVentas.getValueAt(i, 4);

                totalVenta = totalVenta.add(totalVentaPorProducto);
            }
        } catch (Exception ex) {

        }
        return totalVenta;
    }

    public BigDecimal totalProductos(DefaultTableModel tableModelVentas) {

        BigDecimal cantidad = new BigDecimal("0");
        try {
            for (int i = 0; i < tableModelVentas.getRowCount(); i++) {
                ;
                String comoSevende = (String) tableModelVentas.getValueAt(i, 5);
                if (comoSevende.equalsIgnoreCase("Granel")) {
                    cantidad = cantidad.add(new BigDecimal("1"));
                } else {
                    BigDecimal c = (BigDecimal) tableModelVentas.getValueAt(i, 3);
                    cantidad = cantidad.add(c);
                }

            }
        } catch (Exception ex) {
            System.out.println("Error total Productos " + ex);
        }
        return cantidad;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == jDialogVentaFinal.jButtonCancelar) {
            jDialogVentaFinal.setVisible(false);
        }

        if (e.getSource() == jDialogVentaFinal.Cobrar) {
            realizarCobro(defaultableModelTicket);
        }
        if (e.getSource() == jDialogVentaAgranel.jButtonAceptar) {
            insertarVentaAgranel();
        }

    }

    private void realizarCobro(DefaultTableModel defaultableModelTicket) {

        ventaDao = new VentaDao();

        efectivo = false;

        int idVenta = 0;
        int formaPago = jDialogVentaFinal.jTabbedPaneVentasFinal.getSelectedIndex();
        if (formaPago == 0) { //PAGO EFECTIVO
            efectivo = true;
            listFormaDePago.add(new Formadepago(venta, "EFECTIVO", null, ""));
            idVenta = saveVenta(defaultableModelTicket);

            postVenta();

        }

        if (formaPago == 1) { //PAGO CREDITO
            int selected = jDialogVentaFinal.jTableClientes.getSelectedRow();
            if (selected > 0) {
                listFormaDePago.add(new Formadepago(venta, "CREDITO", null, "" + jDialogVentaFinal.jTableClientes.getValueAt(selected, 0)));
                idVenta = saveVenta(defaultableModelTicket);

                postVenta();
            } else {
                JOptionPane.showMessageDialog(null, "Seleccione cliente", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        if (formaPago == 2) { //PAGO TARJETA DEBITO/CREDITO
            limpiarErroresMixto();
            if (!"".equals(jDialogVentaFinal.jTextFieldReferenciaTarjeta.getText().trim())) {
                listFormaDePago.add(new Formadepago(venta, "TARJETA", venta.getPrecioVentaTotal(), jDialogVentaFinal.jTextFieldReferenciaTarjeta.getText().trim()));
                idVenta = saveVenta(defaultableModelTicket);
                postVenta();
            } else {
                // bandera = false;
                jDialogVentaFinal.jLabelTarjetaError.setText("Capture referencia");
            }

        }
        if (formaPago == 3) { //PAGO VALE
            limpiarErroresMixto();
            if (!"".equals(jDialogVentaFinal.jTextFieldReferenciaVale.getText().trim())) {
                listFormaDePago.add(new Formadepago(venta, "VALE", venta.getPrecioVentaTotal(), jDialogVentaFinal.jTextFieldReferenciaVale.getText().trim()));
                idVenta = saveVenta(defaultableModelTicket);
                postVenta();
            } else {
                // bandera = false;
                jDialogVentaFinal.jLabelErrorVale.setText("Capture referencia");
            }
        }
        if (formaPago == 4) { //PAGO MIXTO
            limpiarErroresMixto();
            BigDecimal sumatotal = new BigDecimal("0.00");
            BigDecimal efectivo = new BigDecimal("0.00");
            BigDecimal credito = new BigDecimal("0.00");
            BigDecimal tarjeta = new BigDecimal("0.00");
            BigDecimal vale = new BigDecimal("0.00");

            BigDecimal total = totalTicket(defaultableModelTicket);
            boolean bandera = true;
            if (!"".equals(jDialogVentaFinal.jTextFieldMixtoEfectivo.getText().trim())) {
                efectivo = new BigDecimal(jDialogVentaFinal.jTextFieldMixtoEfectivo.getText().trim());
            }
            if (!"".equals(jDialogVentaFinal.jTextFieldMixtoTarjetaCantidad.getText().trim())) {
                if ("".equals(jDialogVentaFinal.jTextFieldMixtoTarjetaReferencia.getText().trim())) {
                    jDialogVentaFinal.jLabelMixtoErrorTarjeta.setText("Capture referencia");
                    bandera = false;
                } else {

                    tarjeta = new BigDecimal(jDialogVentaFinal.jTextFieldMixtoTarjetaCantidad.getText().trim());

                }

            }
            if (!"".equals(jDialogVentaFinal.jTextFieldMixtoValeCantidad.getText().trim())) {
                if ("".equals(jDialogVentaFinal.jTextFieldMixtoValeReferencia.getText().trim())) {
                    jDialogVentaFinal.jLabelMixtoErrorVale.setText("Capture referencia");
                    bandera = false;
                } else {
                    vale = new BigDecimal(jDialogVentaFinal.jTextFieldMixtoValeCantidad.getText().trim());
                }

            }
            if (!"".equals(jDialogVentaFinal.jTextFieldMixtoCreditoCantidad.getText().trim())) {
                if ("".equals(jDialogVentaFinal.jTextFieldMixtoCreditoNombre.getText().trim())) {
                    jDialogVentaFinal.jLabelMixtoErrorCredito.setText("Sleccione un cliente");
                    bandera = false;
                } else {
                    credito = new BigDecimal(jDialogVentaFinal.jTextFieldMixtoCreditoCantidad.getText().trim());
                }

            }
            sumatotal = sumatotal.add(efectivo);
            sumatotal = sumatotal.add(tarjeta);
            sumatotal = sumatotal.add(vale);
            sumatotal = sumatotal.add(credito);

            if (bandera) {

                BigDecimal restante = total.subtract(sumatotal);
                jDialogVentaFinal.jLabelRestante.setText("Restante " + restante);
                int comparar = total.compareTo(sumatotal);

                if (comparar == 0) {
                    //TODO ESTA BIEN SE PUEDE GUARDAR LA VENTA
                    idVenta = saveVenta(defaultableModelTicket);

                    int efectivoInt = efectivo.compareTo(new BigDecimal("0.00"));
                    if (efectivoInt == 1) {

                        listFormaDePago.add(new Formadepago(venta, "EFECTIVO", efectivo, ""));

                    }
                    int tarjetaInt = tarjeta.compareTo(new BigDecimal("0.00"));
                    if (tarjetaInt == 1) {
                        listFormaDePago.add(new Formadepago(venta, "TARJETA", tarjeta, jDialogVentaFinal.jTextFieldMixtoTarjetaReferencia.getText().trim()));

                    }
                    int valeInt = vale.compareTo(new BigDecimal("0.00"));
                    if (valeInt == 1) {
                        listFormaDePago.add(new Formadepago(venta, "VALE", vale, jDialogVentaFinal.jTextFieldMixtoValeReferencia.getText().trim()));

                    }
                    int creditoInt = credito.compareTo(new BigDecimal("0.00"));
                    if (creditoInt == 1) {
                        listFormaDePago.add(new Formadepago(venta, "CREDITO", credito, "" + jDialogVentaFinal.jLabelMixtoIdCliente.getText()));

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
        // folio = String.valueOf(idVenta);
    }

    private int saveVenta(DefaultTableModel tableModelVentas) {
        productoDao = new ProductoDao();
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

                    for (ContenidoPaquete item : productoDao.getContenidoPaquete(product.getIdProducto())) {

                        BigDecimal cant = item.getCantidad();
                        cant = cant.multiply(cantidad);

                        BigDecimal prec = item.getPrecioUnitario();

                        Tproducto pro = productoDao.getProductoByID(item.getTproducto().getIdProducto());

                        BigDecimal precProveedor = pro.getPrecioProveedor().multiply(cant);
                        BigDecimal ttotal = cant.multiply(prec);
                        System.out.println("Contenido del paquete antes de enviar a guardar " + pro.getCodigoBarras());
                        listaVentaDetalle.add(new Tventadetalle(pro, null, item.getTproducto().getCodigoBarras(), item.getTproducto().getNombre(), pro.getPrecioProveedor(), cant, ttotal, precProveedor, new BigDecimal("0.00"), new BigDecimal("0.00"), false, ""));
                        //  productoDao.cerrar();
                    }
                }
                listaVentaDetalle.add(new Tventadetalle(product, null, codigo, nombre, precioUnitario, cantidad, total, PrecioProveedor, new BigDecimal("0.00"), new BigDecimal("0.00"), true, comoSeVende));

            } catch (Exception ex) {
                // Logger.getLogger(ControladorLoginYventas.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        Set<Tventadetalle> setTventadetalle = new HashSet<Tventadetalle>(listaVentaDetalle);
        venta.setTventadetalles(setTventadetalle);
        String pagoCon = jDialogVentaFinal.jTextFieldPagoCon.getText();
        venta.setPago(pagoCon);
        venta.setUsuario(vistaPrincipal.usuario);
        venta.setFechaRegistro(new Date());
        venta.setPrecioVentaTotal(totalTicket(tableModelVentas));
        idVenta = ventaDao.insert(venta, listFormaDePago);

        return idVenta;
    }

    public void formaDePago(DefaultTableModel tableModelVentas) {
        if (tableModelVentas.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "No tiene productos", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            this.defaultableModelTicket = tableModelVentas;
            jDialogVentaFinal.jLabelTotal.setText("$ " + totalTicket(tableModelVentas));
            jDialogVentaFinal.jLabelRestante.setText("Restante " + totalTicket(tableModelVentas));
            jDialogVentaFinal.jTabbedPaneVentasFinal.setSelectedIndex(0);
            jDialogVentaFinal.setLocationRelativeTo(null);
            jDialogVentaFinal.setVisible(true);
            jDialogVentaFinal.jTextFieldPagoCon.requestFocus();

        }
    }

    @Override
    public void keyTyped(KeyEvent arg0) {

    }

    @Override
    public void keyPressed(KeyEvent ke) {

        if (ke.getSource() == jDialogVentaFinal.jTextFieldPagoCon) {
            if (ke.getKeyCode() == KeyEvent.VK_F12) {
                realizarCobro(defaultableModelTicket);
            }
            if (ke.getKeyCode() == KeyEvent.VK_ESCAPE) {
                jDialogVentaFinal.setVisible(false);
            }

        }

        if (ke.getSource() == jDialogVentaAgranel.jTextFieldCantidad || ke.getSource() == jDialogVentaAgranel.jTextFieldImporte) {

            if (ke.getKeyCode() == KeyEvent.VK_ENTER) {
                insertarVentaAgranel();

            }

        }

    }

    @Override
    public void keyReleased(KeyEvent arg0) {

        if (arg0.getSource() == jDialogVentaAgranel.jTextFieldCantidad) {

            try {

                // jDialogVentaAgranel.jTextFieldImporte.setText("" + jDialogVentaAgranel.jTextFieldCantidad.getText());
                float cantidad = Float.parseFloat(jDialogVentaAgranel.jTextFieldCantidad.getText().trim());
                BigDecimal importe = precio;
                importe = importe.multiply(new BigDecimal(cantidad));
                importe = importe.setScale(2, BigDecimal.ROUND_HALF_UP);

                jDialogVentaAgranel.jTextFieldImporte.setText("" + importe);
            } catch (Exception e) {
                jDialogVentaAgranel.jTextFieldImporte.setText("");
            }

        }
        if (arg0.getSource() == jDialogVentaAgranel.jTextFieldImporte) {

            try {

                BigDecimal importe = new BigDecimal(jDialogVentaAgranel.jTextFieldImporte.getText().trim());
                BigDecimal cantidad = importe.divide(precio, 3, BigDecimal.ROUND_HALF_UP);
                jDialogVentaAgranel.jTextFieldCantidad.setText("" + cantidad);
            } catch (Exception e) {
                jDialogVentaAgranel.jTextFieldCantidad.setText("");
            }

        }

    }

    private void postVenta() {
        int index = jpanelVentas.jTabbedPaneTickets.getSelectedIndex();
        DefaultTableModel d = defaultTableModelArray.get(index);
        if (d.getRowCount() > 0) {
            for (int i1 = d.getRowCount() - 1; i1 > -1; i1--) {
                d.removeRow(i1);
            }
        }
        jPanelTicketArray.get(index).jTableVender.setModel(d);
        listFormaDePago = new ArrayList<>();
        listaVentaDetalle = new ArrayList<>();
        venta = new Tventa();
        jpanelVentas.jLabelTotal.setText("$ 0");
        jDialogVentaFinal.jTextFieldPagoCon.setText("");
        jDialogVentaFinal.jTextFieldSuCambio.setText("");
        jDialogVentaFinal.jTextFieldBuscarCliente.setText("");
        jDialogVentaFinal.jTextFieldReferenciaTarjeta.setText("");
        jDialogVentaFinal.jTextFieldReferenciaVale.setText("");
        jDialogVentaFinal.jTextFieldMixtoEfectivo.setText("");
        jDialogVentaFinal.jTextFieldMixtoTarjetaCantidad.setText("");
        jDialogVentaFinal.jTextFieldMixtoTarjetaReferencia.setText("");
        jDialogVentaFinal.jTextFieldMixtoValeCantidad.setText("");
        jDialogVentaFinal.jTextFieldMixtoValeReferencia.setText("");
        jDialogVentaFinal.jTextFieldMixtoCreditoCantidad.setText("");
        jDialogVentaFinal.jTextFieldMixtoCreditoNombre.setText("");
        jDialogVentaFinal.jLabelMixtoIdCliente.setText("ID");
        jDialogVentaFinal.setVisible(false);
        jpanelVentas.jLabelTotalProductosVendidos.setText("0 total productos");
        ventaDao.cerrar();
        productoDao.cerrar();

    }

    private void limpiarErroresMixto() {
        jDialogVentaFinal.jLabelMixtoErrorEfectivo.setText("");
        jDialogVentaFinal.jLabelMixtoErrorTarjeta.setText("");
        jDialogVentaFinal.jLabelMixtoErrorVale.setText("");
        jDialogVentaFinal.jLabelTarjetaError.setText("");
        jDialogVentaFinal.jLabelErrorVale.setText("");
        jDialogVentaFinal.jLabelMixtoErrorCredito.setText("");
        jDialogVentaFinal.jLabelRestante.setText("Restante");
    }

    private void insertarVentaAgranel() {

        try {
            cantidad = new BigDecimal(jDialogVentaAgranel.jTextFieldCantidad.getText());
            jDialogVentaAgranel.setVisible(false);
            jDialogVentaAgranel.jTextFieldCantidad.setText("");
            jDialogVentaAgranel.jTextFieldImporte.setText("");
        } catch (Exception e) {
            System.out.println("metodo insertatVentaAgranel: " + e);
        }

    }

   

    

    

    

   

}
