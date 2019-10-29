package controlador;


import dao.InventarioDao;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;
import modelo.Departamento;
import modelo.Tproducto;
import vista.JPanelInventario;
import vista.Principal;
import vista.inventario.JPanelInventariolista;

/**
 *
 * @author mq12
 */
public class ControladorInventario implements ActionListener, ItemListener {

    Principal vistaPrincipal;
    JPanelInventario jPanelInventario;
    JPanelInventariolista JPanelInventariolista;
    List<Tproducto> productos;
    InventarioDao inventarioDao = new InventarioDao();
  

    public ControladorInventario(Principal vistaPrincipal, JPanelInventario jPanelInventario, JPanelInventariolista JPanelInventariolista) {
        this.vistaPrincipal = vistaPrincipal;
        this.jPanelInventario = jPanelInventario;
        this.JPanelInventariolista = JPanelInventariolista;
        vistaPrincipal.jButtonInventario.addActionListener(this);
        this.JPanelInventariolista.jComboBoxPorCategoria.addItemListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vistaPrincipal.jButtonInventario || e.getSource() == jPanelInventario.jButtonInventaio) {
            show();
            llenarComboDepartamento();
        }

    }

    public void show() {
        try {
            productos = inventarioDao.productosInventario();
            vistaPrincipal.jPanelPanelPrincipal.removeAll();
            vistaPrincipal.jPanelPanelPrincipal.setLayout(new java.awt.BorderLayout());
            vistaPrincipal.jPanelPanelPrincipal.add(jPanelInventario);
            vistaPrincipal.jPanelPanelPrincipal.validate();
            vistaPrincipal.jPanelPanelPrincipal.repaint();
            //muestra panel nuevo producto
            jPanelInventario.jPanelPrincipal.removeAll();

            jPanelInventario.jPanelPrincipal.setLayout(new java.awt.BorderLayout());
            jPanelInventario.jPanelPrincipal.add(JPanelInventariolista);
            jPanelInventario.jPanelPrincipal.validate();
            jPanelInventario.jPanelPrincipal.repaint();
            BigDecimal total = inventarioDao.totalInventario();
            total = total.setScale(2, BigDecimal.ROUND_HALF_EVEN);
            JPanelInventariolista.jLabelCostoInventario.setText("Costo del inventario " + total);
            JPanelInventariolista.jTableInvntario.setModel(llenarTabla());

        } catch (Exception ex) {
            Logger.getLogger(ControladorInventario.class.getName()).log(Level.SEVERE, null, ex);
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

        String[] columnNames = {"CODIGO", "NOMBRE", "PRECIO VENTA", "PRECIO COMPRA", "CANTIDAD", "TOTAL VENTA", "TOTAL COMPRA"};
        tableModel.setColumnIdentifiers(columnNames);
        Object[] fila = new Object[tableModel.getColumnCount()];

        for (int i = 0; i < productos.size(); i++) {

            fila[0] = productos.get(i).getCodigoBarras();
            fila[1] = productos.get(i).getNombre();
            fila[2] = productos.get(i).getPrecioVentaUnitario();
            fila[3] = productos.get(i).getPrecioProveedor();
            fila[4] = productos.get(i).getCantidad();
            BigDecimal totalVenta;
            BigDecimal totalCompra;
            BigDecimal cantidad = productos.get(i).getCantidad();
            totalCompra = productos.get(i).getPrecioProveedor().multiply(cantidad);
            totalVenta = productos.get(i).getPrecioVentaUnitario().multiply(cantidad);
            totalVenta = totalVenta.setScale(2, BigDecimal.ROUND_HALF_EVEN);
            totalCompra = totalCompra.setScale(2, BigDecimal.ROUND_HALF_EVEN);
            fila[5] = totalVenta;
            fila[6] = totalCompra;
            tableModel.addRow(fila);

        }
        return tableModel;
    }

    public void llenarComboDepartamento() {
        try {
            JPanelInventariolista.jComboBoxPorCategoria.setModel(new DefaultComboBoxModel());
            List<Departamento> departamentos;
            departamentos = inventarioDao.getDepartamento();
            JPanelInventariolista.jComboBoxPorCategoria.addItem("todos los departamentos");
            for (int i = 0; i < departamentos.size(); i++) {
                JPanelInventariolista.jComboBoxPorCategoria.addItem("" + departamentos.get(i).getNombre());

            }
        } catch (Exception ex) {
            Logger.getLogger(ControladorProductos.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        BigDecimal total = null;
        if (e.getStateChange() == ItemEvent.SELECTED) {
            String seleccion = "" + JPanelInventariolista.jComboBoxPorCategoria.getSelectedItem();
            //  System.out.println(" categoria" + seleccion);
            try {
                if (seleccion.equalsIgnoreCase("todos los departamentos")) {
                    productos = inventarioDao.productosInventario();
                    System.out.println(" SELECCIONSTA TODAS LAS CATEGORIAS" + seleccion);
                    total = inventarioDao.totalInventario();
                } else {
                    productos = inventarioDao.productosInventario(seleccion);
                    total = inventarioDao.totalInventario(seleccion);
                }

            } catch (Exception ex) {
                Logger.getLogger(ControladorInventario.class.getName()).log(Level.SEVERE, null, ex);
            }

            total = total.setScale(2, BigDecimal.ROUND_HALF_EVEN);
            JPanelInventariolista.jLabelCostoInventario.setText("Costo del inventario " + total);
            JPanelInventariolista.jTableInvntario.setModel(llenarTabla());

        }
       
    }
}
