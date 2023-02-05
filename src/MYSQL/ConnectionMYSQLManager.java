/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MYSQL;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 *
 * @author mq12
 */
public class ConnectionMYSQLManager {

    private String url ;
    private String driverName;
    private String username;
    private String password;
    private Connection con;

    public Connection getConnection() throws FileNotFoundException, IOException {

        try {

            //loada database information from hibernate.properties"
            Properties prop = new Properties();
            InputStream input = new FileInputStream("hibernate.properties");
            prop.load(input);
          
            try {
                 url = prop.getProperty("hibernate.connection.url");
                 driverName =  prop.getProperty("hibernate.connection.driver_class");
                 username = prop.getProperty("hibernate.connection.username");
                 password = prop.getProperty("hibernate.connection.password");
                 
                 System.out.println("url x= " +  url + " driverName x= " + driverName);

                con = DriverManager.getConnection(url, username, password);
            } catch (SQLException ex) {
                // log an exception. fro example:
                System.out.println("Failed to create the database connection." + ex);
            }
        } catch (Exception ex) {
            // log an exception. for example:
            System.out.println("Driver not found.");
        }
        return con;
    }
}
