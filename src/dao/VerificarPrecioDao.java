/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Properties;
import modelo.Tproducto;

/**
 *
 * @author mq12
 */
public class VerificarPrecioDao {
    
    public Tproducto verificarProducto(String codigo) {
        Tproducto p = null;
        
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
            
            Statement s = conexion.createStatement();
            
            String consultaSql = " select * from tproducto where codigoBarras = '" + codigo + "'";
            System.out.println("sentencia " + consultaSql);
            ResultSet rs = s.executeQuery(consultaSql);
            while (rs.next()) {
                p = new Tproducto();
                p.setCodigoBarras(rs.getString("codigoBarras"));
                p.setNombre(rs.getString("nombre"));
                p.setDescripcion(rs.getString("descripcion"));
                p.setPrecioVentaUnitario(rs.getBigDecimal("precioVentaUnitario"));
                rs.getString("codigoBarras");
                System.out.println("Codigo barras " + rs.getString("codigoBarras"));
                
            }
            
            System.out.println("productos ............................................. ");
            conexion.close();
        } catch (Exception e) {
            System.out.println("error productos ..... " + e);
        }
        return p;
    }
    
}
