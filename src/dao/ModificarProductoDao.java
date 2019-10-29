/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import hibernate.HibernateUtil;
import javax.swing.JOptionPane;
import modelo.Departamento;
import modelo.Tproducto;
import org.hibernate.Query;

/**
 *
 * @author mq12
 */
public class ModificarProductoDao {

    Session session;
    Transaction transaction;

    public List<Departamento> getDepartamento() throws Exception {
        session = HibernateUtil.getSessionFactory().openSession();
        String hql = "from Departamento ";
        Query query = session.createQuery(hql);

        List<Departamento> productosPorNombre = (List<Departamento>) query.list();

        return productosPorNombre;
    }

    public boolean modificarProducto(Tproducto producto) {
        boolean resultado = true;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = this.session.beginTransaction();
            session.update(producto);
            transaction.commit();
            JOptionPane.showMessageDialog(null, "Producto " + producto.getDescripcion() + " Modificado.");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERROR nx " + e);
            System.out.println("error " + e);
            resultado = false;
        } finally {
            session.close();
            System.out.println("cerar 19/04/2018");
        }
        return resultado;
    }

    public void borrarContenidoPaquete(int idPaquete) {
        try {
            System.out.println("borrar");
            session = HibernateUtil.getSessionFactory().openSession();
            String hql = "delete ContenidoPaquete where idPaquete = :idPaquete";
            Query query = session.createQuery(hql);
            query.setParameter("idPaquete", idPaquete);
            query.executeUpdate();
        } catch (Exception e) {
            System.out.println("errrrt" + e);
        } finally {
            session.close();
        }

    }
    public void cerrar(){
        session.close();
    }

}
