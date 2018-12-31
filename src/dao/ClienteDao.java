/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import hibernate.HibernateUtil;
import java.util.List;
import javax.swing.JOptionPane;
import modelo.Cliente;
import modelo.Tproducto;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author mq12
 */
public class ClienteDao {
       Session session;
    Transaction transaction;
      public boolean addProduct(Cliente cliente) {
        boolean resultado = true;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = this.session.beginTransaction();
            session.save(cliente);
            transaction.commit();
            JOptionPane.showMessageDialog(null, "Cliente " + cliente.getNombreCompleto() + " agregado.");

        } catch (Exception e) {
            resultado = false;
            JOptionPane.showMessageDialog(null, "Error al crear cliente: \n" + e, "error", JOptionPane.ERROR_MESSAGE);
        } finally {
            session.close();
        }
        return resultado;
    }
     public List<Cliente> getPorNombre(String nombreCompleto) throws Exception {
         session = HibernateUtil.getSessionFactory().openSession();
        String hql = "from Cliente where nombreCompleto LIKE :nombreCompleto";
        Query query = session.createQuery(hql);
        query.setParameter("nombreCompleto", "%" + nombreCompleto + "%");
        List<Cliente> clientesPorNombre = (List<Cliente>) query.list();

        return clientesPorNombre;
    }  
      
}
