/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hibernate;

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
            dbConnectionProperties.load(HibernateUtil.class.getClassLoader().getSystemClassLoader().getResourceAsStream("hibernate.properties"));
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
//public class HibernateUtil {
//
//    private static final SessionFactory sessionFactory;
//    
//    static {
//        try {
//            // Create the SessionFactory from standard (hibernate.cfg.xml) 
//            // config file.
//            sessionFactory = new AnnotationConfiguration().configure().buildSessionFactory();
//        } catch (Throwable ex) {
//            // Log the exception. 
//            System.err.println("Initial SessionFactory creation failed." + ex);
//            throw new ExceptionInInitializerError(ex);
//        }
//    }
//    
//    public static SessionFactory getSessionFactory() {
//        return sessionFactory;
//    }
//}
