/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import MYSQL.ConnectionMYSQLManager;
import hibernate.HibernateUtil;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import modelo.Tventadetalle;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author mq12
 */
public class MisVentasDao {
    ConnectionMYSQLManager con = new ConnectionMYSQLManager();
    private BigDecimal total;
    
     public DefaultTableModel getVentasDetalle(String nombre) {
                  setTotal(new BigDecimal("0.00"));
        DefaultTableModel tableModel = new DefaultTableModel() {

            @Override
            public boolean isCellEditable(int row, int column) {
                //all cells false
                return false;
            }
        };
        try {
            
            System.out.println("mis ventas .........................................");

            String[] columnNames = {"xID VENTA", "FECHA", "CODIGO", "NOMBRE", "DEPARTAMENTO", "CANTIDAD", "PRECIO", "PAGO CON","TOTAL"};
            tableModel.setColumnIdentifiers(columnNames);

           
             Connection conexion = con.getConnection(); //Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/puntoventa", "root", "Fedora12");
            // Preparamos la consulta 
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd ");
            String i = df.format(new Date());
            String f = df.format(new Date());
            Statement s = conexion.createStatement();
            
            String consultaSql = "select d.idVenta,v.fechaRegistro, d.codigoBarrasProducto, d.nombreProducto, dep.nombre, d.cantidad, d.precioventaUnitarioProducto,v.pago, d.totalprecioventa, v.precioProveedor  from tventadetalle d inner join tventa v  on v.idventa = d.idventa inner join tproducto p on p.idproducto = d.idproducto inner join departamento dep on p.categoria_id = dep.id inner join usuario u on u.id = v.usuario_id  where u.nombre = '" + nombre + "' and v.fecharegistro >='" + i + " 00:00:00' and v.fecharegistro <='" + f + " 23:59:59' and d.imprimir = 1 ORDER BY v.fechaRegistro desc";
            System.out.println("sentencia " + consultaSql);
            ResultSet rs = s.executeQuery(consultaSql);
            // Recorremos el resultado, mientras haya registros para leer, y escribimos el resultado en pantalla. 
           
            Object[] fila = new Object[tableModel.getColumnCount()];
            while (rs.next()) {
                fila[0] = "" + rs.getInt(1);
                fila[1] = "" + rs.getString(2);
                fila[2] = "" + rs.getString(3);
                fila[3] = "" + rs.getString(4);
                fila[4] = "" + rs.getString(5);
                fila[5] = "" + rs.getString(6);
                fila[6] = "" + rs.getString(7);
                fila[7] = "" + rs.getBigDecimal(8);
                fila[8] = "" + rs.getBigDecimal(9);                
                setTotal(getTotal().add(rs.getBigDecimal(9)));
                System.out.println("suma total " + getTotal());
                tableModel.addRow(fila);
            }
            // Cerramos la conexion a la base de datos. 
            conexion.close();
        } catch (Exception ex) {
            System.out.println("error " + ex);
            // Logger.getLogger(ControladorInventario.class.getName()).log(Level.SEVERE, null, ex);
        }
        return tableModel;
     }

    /**
     * @return the total
     */
    public BigDecimal getTotal() {
        return total;
    }

    /**
     * @param total the total to set
     */
    public void setTotal(BigDecimal total) {
        this.total = total;
    }
}
