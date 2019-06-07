/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import hibernate.HibernateUtil;
import modelo.Tproducto;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author mq12
 */
public class ProductoDao {

    Session session;
    Transaction transaction;

    public Tproducto getByCodigoBarras(String codigoBarras) {

        session = HibernateUtil.getSessionFactory().openSession();
        String hql = "from Tproducto where codigoBarras=:codigoBarras";
        Query query = session.createQuery(hql);
        query.setParameter("codigoBarras", codigoBarras);
        return (Tproducto) query.uniqueResult();

    }

    public void cerrar() {

        session.close();
    }
}
