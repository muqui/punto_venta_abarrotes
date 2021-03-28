/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import hibernate.HibernateUtil;
import java.util.List;
import javax.swing.JOptionPane;
import modelo.ContenidoPaquete;
import modelo.Departamento;
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

    public ProductoDao() {
      
    }

    public Tproducto getByCodigoBarras(String codigoBarras) {
        Tproducto producto = null;
       
        session = HibernateUtil.getSessionFactory().openSession();
        String hql = "from Tproducto where codigoBarras=:codigoBarras ";
        Query query = session.createQuery(hql);
        query.setParameter("codigoBarras", codigoBarras);
        producto = (Tproducto) query.uniqueResult();

        return producto;

    }

    public List<ContenidoPaquete> getContenidoPaquete(int idPaquete) throws Exception {
        // session = HibernateUtil.getSessionFactory().openSession();
        String hql = "from ContenidoPaquete where idPaquete = :idPaquete";
        Query query = session.createQuery(hql);
        query.setParameter("idPaquete", idPaquete);
        List<ContenidoPaquete> getContnidoPaquteList = (List<ContenidoPaquete>) query.list();
        //   session.close();
        return getContnidoPaquteList;
    }

    public Tproducto getProductoByID(int id) throws Exception {
        //  session = HibernateUtil.getSessionFactory().openSession();
        String hql = "from Tproducto where idProducto=:idProducto";
        Query query = session.createQuery(hql);
        query.setParameter("idProducto", id);
        //  session.close();
        return (Tproducto) query.uniqueResult();

    }

    public List<Tproducto> getPorNombre(String nombre) throws Exception {
        session = HibernateUtil.getSessionFactory().openSession();
        String hql = "from Tproducto where nombre LIKE :nombre ";
        Query query = session.createQuery(hql);
        query.setParameter("nombre", "%" + nombre + "%");
        List<Tproducto> productosPorNombre = (List<Tproducto>) query.list();

        // session.close();
        return productosPorNombre;
    }
    
      public List<Tproducto> getPorNombreDeshabilitado(String nombre) throws Exception {
        session = HibernateUtil.getSessionFactory().openSession();
        String hql = "from Tproducto where nombre LIKE :nombre and habilitado <> false";
        Query query = session.createQuery(hql);
        query.setParameter("nombre", "%" + nombre + "%");
        List<Tproducto> productosPorNombre = (List<Tproducto>) query.list();

        // session.close();
        return productosPorNombre;
    }

    /*
     *
     * Controlador Productos
     *
     */
    public int addProductInt(Tproducto producto) {
        int resultado = 0;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = this.session.beginTransaction();
            session.save(producto);
            resultado = producto.getIdProducto();
            transaction.commit();
            JOptionPane.showMessageDialog(null, "Producto " + producto.getDescripcion() + " agregado.");

        } catch (Exception e) {
            resultado = 0;
            JOptionPane.showMessageDialog(null, "Error al agregar producto: \n" + e, "error", JOptionPane.ERROR_MESSAGE);
        } finally {
            session.close();
            System.out.println("add Product int cerrar");
        }
        return resultado;
    }

    public boolean addContenidoPaquete(ContenidoPaquete contenidoPaquete) {
        boolean resultado = true;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = this.session.beginTransaction();
            session.save(contenidoPaquete);
            transaction.commit();
            // JOptionPane.showMessageDialog(null, "Departamento:  " + contenidoPaquete.getId() + " agregado.");

        } catch (Exception e) {
            resultado = false;
            JOptionPane.showMessageDialog(null, "Error al crear contnido paquete: \n" + e, "error ", JOptionPane.ERROR_MESSAGE);
        } finally {
            session.close();
        }
        return resultado;
    }

    public Departamento getDepartamento(String nombre) {
        session = HibernateUtil.getSessionFactory().openSession();
         System.out.println("SE abrio hibernate");
        String hql = "from Departamento where nombre=:nombre";

        Query query = session.createQuery(hql);
        query.setParameter("nombre", nombre);
        // session.close();
        return (Departamento) query.uniqueResult();

    }

    public List<Departamento> getDepartamento() throws Exception {
        session = HibernateUtil.getSessionFactory().openSession();
        String hql = "from Departamento ";
        Query query = session.createQuery(hql);

        List<Departamento> productosPorNombre = (List<Departamento>) query.list();

        return productosPorNombre;
    }

    
    
    public void cerrar() {
       
        session.close();
    }
}
