/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import hibernate.HibernateUtil;
import java.util.List;
import modelo.Usuario;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author mq12
 */
public class UsuarioDao {

    Session session;
    Transaction transaction;

    public void addUser(Usuario usuario) throws Exception {
        session = HibernateUtil.getSessionFactory().openSession();
        transaction = this.session.beginTransaction();
        session.save(usuario);
        transaction.commit();
        session.close();
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

  

    public Usuario getUsuarioXnombre(String usuario) throws Exception {
        Usuario u = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            String hql = "FROM Usuario WHERE nombre = '" + usuario + "'";
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
