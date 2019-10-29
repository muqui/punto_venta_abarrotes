/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import hibernate.HibernateUtil;
import modelo.Usuario;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author mq12
 */
public class LoginDao {

    Session session;
    Transaction transaction;

    public Usuario login(String user, String password) {
        Usuario u = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            String hql = "FROM Usuario WHERE nombre = '" + user
                    + "' and password = '" + password + "'";
            Query query = session.createQuery(hql);
            if (!query.list().isEmpty()) {
                u = (Usuario) query.list().get(0);
            }
            transaction.commit();

        } catch (Exception e) {
            throw e;
        } finally {
            session.close();
        }
        return u;
    }

}
