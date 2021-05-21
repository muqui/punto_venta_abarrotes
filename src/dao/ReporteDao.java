/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import hibernate.HibernateUtil;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import javax.swing.table.DefaultTableModel;
import modelo.Departamento;
import modelo.Tventadetalle;
import modelo.Usuario;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author mq12
 */
public class ReporteDao {

    Session session;
    Transaction transaction;

    public List<Departamento> getDepartamento() throws Exception {
        session = HibernateUtil.getSessionFactory().openSession();
        String hql = "from Departamento ";
        Query query = session.createQuery(hql);

        List<Departamento> productosPorNombre = (List<Departamento>) query.list();
        session.close();
        return productosPorNombre;
    }

    public List<Usuario> getUsuarios() {
        List<Usuario> users = null;
        session = HibernateUtil.getSessionFactory().openSession();
        transaction = this.session.beginTransaction();
        users = session.createCriteria(Usuario.class).list();
        transaction.commit();
        session.close();
        return users;
    }

    public DefaultTableModel getVentasDetalle(Date desde, Date hasta, String usuario, String categoria) {
        DefaultTableModel tableModel = new DefaultTableModel() {

            @Override
            public boolean isCellEditable(int row, int column) {
                //all cells false
                return false;
            }
        };

        try {
            Properties prop = new Properties();
           // InputStream input = getClass().getClassLoader().getResourceAsStream("hibernate.properties");
            InputStream input = new FileInputStream("hibernate.properties");
            prop.load(input);
            String clase = prop.getProperty("hibernate.connection.driver_class");
            Class.forName(clase);
            String[] columnNames = {"ID VENTA", "FECHA", "CODIGO", "NOMBRE", "DEPARTAMENTO", "USUARIO", "CANTIDAD", "PRECIO", "COSTO TOTAL PROVEEDOR","PAGO CON", "TOTAL"};
            tableModel.setColumnIdentifiers(columnNames);
            Object[] fila = new Object[tableModel.getColumnCount()];
            // Establecemos la conexión con la base de datos. 
            String url = prop.getProperty("hibernate.connection.url");
            String user = prop.getProperty("hibernate.connection.username");
            String password = prop.getProperty("hibernate.connection.password");
            Connection conexion = DriverManager.getConnection(url, user, password);
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd ");
            String i = df.format(desde);
            String f = df.format(hasta);
            Statement s = conexion.createStatement();

            String consultaSql = "select d.idVenta,v.fechaRegistro, d.codigoBarrasProducto, d.nombreProducto, dep.nombre, u.nombre, d.cantidad, d.precioventaUnitarioProducto, d.precioproveedor,v.pago, d.totalprecioventa  from tventadetalle d inner join tventa v  on v.idventa = d.idventa inner join tproducto p on p.idproducto = d.idproducto inner join departamento dep on p.categoria_id = dep.id inner join usuario u on u.id = v.usuario_id  where u.nombre LIKE  '%" + usuario + "%' and v.fecharegistro >='" + i + " 00:00:00' and v.fecharegistro <= '" + f + " 23:59:29' and d.imprimir = 1 and dep.nombre LIKE '%" + categoria + "%' ORDER BY v.fechaRegistro desc";
            System.out.println("sentencia " + consultaSql);
            ResultSet rs = s.executeQuery(consultaSql);
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
                 fila[10] = "" + rs.getBigDecimal(11);
                System.out.println("productos ............................................. " + rs.getInt(1));
                tableModel.addRow(fila);
            }

            System.out.println("productos ............................................. ");
            conexion.close();
        } catch (Exception e) {
            System.out.println("error productos 27/06/2018 ..... " + e);
        }
        return tableModel;
    }

    public BigDecimal getVentas(Date desde, Date hasta, String usuario, String categoria, String campo) {
        BigDecimal ventas = new BigDecimal("0.00");

        try {
            Properties prop = new Properties();
           // InputStream input = getClass().getClassLoader().getResourceAsStream("hibernate.properties");
            InputStream input = new FileInputStream("hibernate.properties");
            prop.load(input);
            String clase = prop.getProperty("hibernate.connection.driver_class");
            Class.forName(clase);

            // Establecemos la conexión con la base de datos. 
            String url = prop.getProperty("hibernate.connection.url");
            String user = prop.getProperty("hibernate.connection.username");
            String password = prop.getProperty("hibernate.connection.password");
            Connection conexion = DriverManager.getConnection(url, user, password);
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd ");
            String i = df.format(desde);
            String f = df.format(hasta);
            Statement s = conexion.createStatement();

            String consultaSql = "select  sum(" + campo + ")  from tventadetalle d inner join tventa v  on v.idventa = d.idventa inner join tproducto p on p.idproducto = d.idproducto inner join departamento dep on p.categoria_id = dep.id inner join usuario u on u.id = v.usuario_id  where u.nombre LIKE  '%" + usuario + "%' and v.fecharegistro >='" + i + " 00:00:00' and v.fecharegistro <= '" + f + " 23:59:29' and d.imprimir = 1 and dep.nombre LIKE '%" + categoria + "%' ";
            System.out.println("sentencia " + consultaSql);
            ResultSet rs = s.executeQuery(consultaSql);

            while (rs.next()) {
                //Retrieve by column name
                System.out.println("TOTAL 4 =  " + rs.getBigDecimal(1));

                ventas = rs.getBigDecimal(1);

            }
            if (ventas == null) {
                ventas = new java.math.BigDecimal("0.00");
            }
            rs.close();
            System.out.println("ventas  ............................................. " + ventas);
            conexion.close();
        } catch (Exception e) {
            System.out.println("error productos 27/06/2018 2 ..... " + e);
        }
        return ventas;
    }

}
