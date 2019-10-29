/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hibernate;

import java.io.FileInputStream;
import java.io.InputStream;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.SessionFactory;

/**
 * Hibernate Utility class with a convenient method to get Session Factory
 * object.
 *
 * @author mq12
 */

import java.util.Properties;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

public class HibernateUtil {

private static final SessionFactory sessionFactory = buildSessionFactory();

private static SessionFactory buildSessionFactory() {
    try {

        Properties dbConnectionProperties = new Properties();
        try {
           // dbConnectionProperties.load(HibernateUtil.class.getClassLoader().getSystemClassLoader().getResourceAsStream("hibernate.properties"));
           InputStream is = new FileInputStream("hibernate.properties");
           dbConnectionProperties.load(is);
        } catch(Exception e) {
            e.printStackTrace();
            // Log
        }           

        return new AnnotationConfiguration().mergeProperties(dbConnectionProperties).configure("hibernate.cfg.xml").buildSessionFactory();          


    } catch (Throwable ex) {
        ex.printStackTrace();
//            throw new ExceptionInInitializerError(ex);
    }
    return null;
}

public static SessionFactory getSessionFactory() {
    return sessionFactory;
}

}

