/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import hibernate.HibernateUtil;
import java.util.List;
import javax.swing.JOptionPane;
import modelo.Departamento;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author mq12
 */
public class DepartamentoDao {

    Session session;
    Transaction transaction;

    public Departamento getDepartamento(String nombre) {
        session = HibernateUtil.getSessionFactory().openSession();
        String hql = "from Departamento where nombre=:nombre";

        Query query = session.createQuery(hql);
        query.setParameter("nombre", nombre);
        
        return (Departamento) query.uniqueResult();

    }

    public boolean addDepartamento(Departamento departamento) {
        boolean resultado = true;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = this.session.beginTransaction();
            session.save(departamento);
            transaction.commit();
            JOptionPane.showMessageDialog(null, "Departamento:  " + departamento.getNombre() + " agregado.");

        } catch (Exception e) {
            resultado = false;
            JOptionPane.showMessageDialog(null, "Error al crear departamento: \n" + e, "error", JOptionPane.ERROR_MESSAGE);
        } finally {
            session.close();
        }
        return resultado;
    }
    
    public void cerrar(){
        session.close();
    }
    
}
