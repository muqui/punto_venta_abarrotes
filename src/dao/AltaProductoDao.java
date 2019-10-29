/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import hibernate.HibernateUtil;
import modelo.Departamento;
import modelo.Tproducto;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author mq12
 */
public class AltaProductoDao {
    Session session;
    Transaction transaction;
    
    public boolean getByCodigoBarras(String codigoBarras) {
        boolean bandera = true;
        Tproducto producto = null;     
        session = HibernateUtil.getSessionFactory().openSession();
        String hql = "from Tproducto where codigoBarras=:codigoBarras";
        Query query = session.createQuery(hql);
        query.setParameter("codigoBarras", codigoBarras);
        producto = (Tproducto) query.uniqueResult();
        if (producto != null) {
                bandera = false;               
            }
        
        session.close();
        return bandera;

    }
    public Departamento getDepartamento(String nombre) {
        Departamento departamento = new Departamento();
        session = HibernateUtil.getSessionFactory().openSession();
         System.out.println("SE abrio hibernate");
        String hql = "from Departamento where nombre=:nombre";
        Query query = session.createQuery(hql);
        query.setParameter("nombre", nombre);
        departamento = (Departamento) query.uniqueResult();
        
         session.close();
        return departamento;

    }
    
}
