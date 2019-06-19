/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import dao.MisVentasDao;
import hibernate.HibernateUtil;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import modelo.Tventa;
import modelo.Tventadetalle;
import org.hibernate.Session;
import vista.Principal;
import vista.reporte.JPanelMisVentas;

/**
 *
 * @author mq12
 */
public class ControladorMisVentas implements ActionListener {

    Principal vistaPrincipal;
    JPanelMisVentas jpanelMisVentas;
    private List<Tventadetalle> listaventas;
    MisVentasDao misVentasDao = new MisVentasDao();

    public ControladorMisVentas(Principal vistaPrincipal, JPanelMisVentas jpanelMisVentas) {
        this.vistaPrincipal = vistaPrincipal;
        this.jpanelMisVentas = jpanelMisVentas;
        vistaPrincipal.jButtonMisVentas.addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vistaPrincipal.jButtonMisVentas) {
            show();

        }

    }

    public void show() {
        BigDecimal total = new BigDecimal("0.00");
        DefaultTableModel tableModel = new DefaultTableModel() {

            @Override
            public boolean isCellEditable(int row, int column) {
                //all cells false
                return false;
            }
        };
        try {
            System.out.println("mis ventas .........................................");

            String[] columnNames = {"ID VENTA", "FECHA", "CODIGO", "NOMBRE", "DEPARTAMENTO", "USUARIO", "CANTIDAD", "PRECIO", "COSTO TOTAL PROVEEDOR", "TOTAL"};
            tableModel.setColumnIdentifiers(columnNames);

            vistaPrincipal.jPanelPanelPrincipal.removeAll();
            vistaPrincipal.jPanelPanelPrincipal.setLayout(new java.awt.BorderLayout());
            vistaPrincipal.jPanelPanelPrincipal.add(jpanelMisVentas);
            vistaPrincipal.jPanelPanelPrincipal.validate();
            vistaPrincipal.jPanelPanelPrincipal.repaint();

            //muestra panel nuevo producto
            System.out.println("mis ventas ......................................... repaint");

            Class.forName("com.mysql.jdbc.Driver");
            // Establecemos la conexión con la base de datos. 
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/puntoventa", "root", "Fedora12");
            // Preparamos la consulta 
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd ");
        String i = df.format(new Date());
        String f = df.format(new Date());
            Statement s = conexion.createStatement();
            String consultaSql = "select d.idVenta,v.fechaRegistro, d.codigoBarrasProducto, d.nombreProducto, dep.nombre, u.nombre, d.cantidad, d.precioventaUnitarioProducto, d.precioproveedor, d.totalprecioventa  from tventadetalle d inner join tventa v  on v.idventa = d.idventa inner join tproducto p on p.idproducto = d.idproducto inner join departamento dep on p.categoria_id = dep.id inner join usuario u on u.id = v.usuario_id  where u.nombre = '"+ vistaPrincipal.usuario.getNombre()+"' and v.fecharegistro >='"+ i +" 00:00:00' and v.fecharegistro <='"+ f +" 23:59:59' and d.imprimir = 1";
            System.out.println("sentencia " +  consultaSql);
            ResultSet rs = s.executeQuery(consultaSql);
            // Recorremos el resultado, mientras haya registros para leer, y escribimos el resultado en pantalla. 
             total = new BigDecimal("0.00");
            Object[] fila = new Object[tableModel.getColumnCount()];
            while (rs.next()) {
                fila[0] = "" + rs.getInt(1);
                fila[1] = "" + rs.getString(2);
                fila[2] = "" + rs.getString(3);
                fila[3] = "" + rs.getString(4);
                fila[4] = "" + rs.getString(5);
                fila[5] = "" + rs.getString(6);
                fila[6] = "" + rs.getString(7);
                fila[7] = "" + rs.getString(8);
                fila[8] = "" + rs.getString(9);
                fila[9] = "" + rs.getBigDecimal(10);
                total = total.add(rs.getBigDecimal(10));
                System.out.println("suma total " + total);
                tableModel.addRow(fila);
            }
            // Cerramos la conexion a la base de datos. 
            conexion.close();
        } catch (Exception ex) {
            System.out.println("error " + ex);
            // Logger.getLogger(ControladorInventario.class.getName()).log(Level.SEVERE, null, ex);
        }
        jpanelMisVentas.jTableVentasDetalle.setModel(tableModel);
          jpanelMisVentas.jLabelTotalVentas.setText("" + total);

    }

    private DefaultTableModel llenartabla() {

        DefaultTableModel tableModel = new DefaultTableModel() {

            @Override
            public boolean isCellEditable(int row, int column) {
                //all cells false
                return false;
            }
        };

        String[] columnNames = {"ID VENTA", "FECHA", "CODIGO", "NOMBRE", "DEPARTAMENTO", "USUARIO", "CANTIDAD", "PRECIO", "COSTO TOTAL PROVEEDOR", "TOTAL"};
        tableModel.setColumnIdentifiers(columnNames);
        Object[] fila = new Object[tableModel.getColumnCount()];

        for (int i = 0; i < listaventas.size(); i++) {
            fila[0] = listaventas.get(i).getTventa().getIdVenta();
            fila[1] = listaventas.get(i).getTventa().getFechaRegistro();
            fila[2] = listaventas.get(i).getCodigoBarrasProducto();
            fila[3] = listaventas.get(i).getNombreProducto();
            fila[4] = listaventas.get(i).getTproducto().getDepartamento().getNombre();
            fila[5] = listaventas.get(i).getTventa().getUsuario().getNombre();
            fila[6] = listaventas.get(i).getCantidad();
            fila[7] = listaventas.get(i).getPrecioVentaUnitarioProducto();
            fila[8] = listaventas.get(i).getPrecioProveedor();
            fila[9] = listaventas.get(i).getTotalPrecioVenta();

            tableModel.addRow(fila);

        }
        return tableModel;
    }

}
