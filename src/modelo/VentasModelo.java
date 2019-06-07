/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import com.toedter.calendar.JDayChooser;
import dao.ProductoDao;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import vista.JDialogVentaAgranel;

/**
 *
 * @author mq12
 */
public class VentasModelo implements ActionListener {

    JDialogVentaAgranel jDialogVentaAgranel;
    ProductoDao productoDao = new ProductoDao();

    public VentasModelo(JDialogVentaAgranel jDialogVentaAgranel) {
        this.jDialogVentaAgranel = jDialogVentaAgranel;
        jDialogVentaAgranel.jButtonAceptar.addActionListener(this);

    }

    public DefaultTableModel productos(String codigo, boolean precioVentaMenudeo, JTable table, DefaultTableModel defaultTableModel) {
        Tproducto producto = productoDao.getByCodigoBarras(codigo);
        if (producto.getComosevende().equals("Granel")) {
            jDialogVentaAgranel.setLocationRelativeTo(null);
            jDialogVentaAgranel.setVisible(true);
            jDialogVentaAgranel.pack();
        }
        productoDao.cerrar();
        int existe = existeProducto(codigo, defaultTableModel);
        if (existe < 0) {
           
            Object[] name = new Object[]{codigo, producto.getNombre(), producto.getPrecioVentaUnitario(), 1, producto.getPrecioVentaUnitario()};
            defaultTableModel.addRow(name);
        } else {
            cambiarcantidad(existe, defaultTableModel, producto.getPrecioVentaUnitario());
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

    public DefaultTableModel cambiarcantidad(int posicion, DefaultTableModel tableModelVentas, BigDecimal precio) {
        int cantidad = (int) tableModelVentas.getValueAt(posicion, 3);
        cantidad++;
        BigDecimal totalVentaPorProducto = precio;
        totalVentaPorProducto = totalVentaPorProducto.multiply(new BigDecimal(cantidad));

        tableModelVentas.setValueAt(cantidad, posicion, 3);
        tableModelVentas.setValueAt(totalVentaPorProducto, posicion, 4);
        System.out.println("cambiar cantidad: " + tableModelVentas.getValueAt(posicion, 3));

        return tableModelVentas;

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == jDialogVentaAgranel.jButtonAceptar) {
            System.out.println("boton aceptar");
        }

    }

}
