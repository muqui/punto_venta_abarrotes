/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import hibernate.HibernateUtil;
import java.math.BigDecimal;
import java.util.List;
import javax.swing.JOptionPane;
import modelo.ContenidoPaquete;
import modelo.Departamento;
import modelo.Tproducto;
import modelo.Usuario;
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
        }
        finally{
            session.close();
            System.out.println("cerar 19/04/2018");
        }
        return resultado;
    }

    public boolean addProduct(Tproducto producto) {
        boolean resultado = true;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = this.session.beginTransaction();
            session.save(producto);
            transaction.commit();
            JOptionPane.showMessageDialog(null, "Producto " + producto.getDescripcion() + " agregado.");

        } catch (Exception e) {
            resultado = false;
            JOptionPane.showMessageDialog(null, "Error al agregar producto: \n" + e, "error", JOptionPane.ERROR_MESSAGE);
        } finally {
            session.close();
        }
        return resultado;
    }
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
        }
        return resultado;
    }
    public Departamento getDepartamento(String nombre) {
        session = HibernateUtil.getSessionFactory().openSession();
        String hql = "from Departamento where nombre=:nombre";

        Query query = session.createQuery(hql);
        query.setParameter("nombre", nombre);
        // session.close();
        return (Departamento) query.uniqueResult();

    }

    public Tproducto getByCodigoBarras(String codigoBarras)  {
        System.out.println("session abierta");
        session = HibernateUtil.getSessionFactory().openSession();
        String hql = "from Tproducto where codigoBarras=:codigoBarras";
        Query query = session.createQuery(hql);
        query.setParameter("codigoBarras", codigoBarras);
       // session.close();
        return (Tproducto) query.uniqueResult();
        

    }
     public Tproducto getByID(int id) throws Exception {
        session = HibernateUtil.getSessionFactory().openSession();
        String hql = "from Tproducto where idProducto=:idProducto";
        Query query = session.createQuery(hql);
        query.setParameter("idProducto", id);
        
        return (Tproducto) query.uniqueResult();

    }

    public List<Tproducto> getPorNombre(String nombre) throws Exception {
        session = HibernateUtil.getSessionFactory().openSession();
        String hql = "from Tproducto where nombre LIKE :nombre";
        Query query = session.createQuery(hql);
        query.setParameter("nombre", "%" + nombre + "%");
        List<Tproducto> productosPorNombre = (List<Tproducto>) query.list();
       // session.close();
        return productosPorNombre;
    }

    public List<Tproducto> productos() throws Exception {
        session = HibernateUtil.getSessionFactory().openSession();
        String hql = "from Tproducto";
        Query query = session.createQuery(hql);
        List<Tproducto> productosPorNombre = (List<Tproducto>) query.list();
        session.close();
        return productosPorNombre;
    }
     public List<Tproducto> productosInventario() throws Exception {
        session = HibernateUtil.getSessionFactory().openSession();
        String hql = "from Tproducto where comosevende <> 'Paquete'";
        Query query = session.createQuery(hql);
        List<Tproducto> productosPorNombre = (List<Tproducto>) query.list();
        session.close();
        return productosPorNombre;
    }

    public BigDecimal totalInventario() {
        session = HibernateUtil.getSessionFactory().openSession();
        String hql = "select sum(t.cantidad * t.precioProveedor ) from Tproducto as t where t.comosevende <> 'Paquete'";
        Query query = session.createQuery(hql);
        BigDecimal total = (BigDecimal) query.uniqueResult();
        return total;

    }

    public void cerrar() {
        session.close();

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
    
    
    
     public List<ContenidoPaquete> getContnidoPaquteList(int idPaquete) throws Exception {
        session = HibernateUtil.getSessionFactory().openSession();
        String hql = "from ContenidoPaquete where idPaquete = :idPaquete";
        Query query = session.createQuery(hql);
        query.setParameter("idPaquete", idPaquete);
        List<ContenidoPaquete> getContnidoPaquteList = (List<ContenidoPaquete>) query.list();
       // session.close();
        return getContnidoPaquteList;
    }
    public void borrarContenidoPaquete(int idPaquete){
        try {
            System.out.println("borrar");
              session = HibernateUtil.getSessionFactory().openSession();
     String hql = "delete ContenidoPaquete where idPaquete = :idPaquete";
        Query query = session.createQuery(hql);
        query.setParameter("idPaquete", idPaquete);
        query.executeUpdate();
        } catch (Exception e) {
            System.out.println("errrrt" + e);
        }
        finally{
        session.close();
        }
        
    } 

    public void modificarProducto1(Tproducto producto) {
      
    }

    public List<Tproducto> productosInventario(String nombre) {
        session = HibernateUtil.getSessionFactory().openSession();
        String hql = "from Tproducto t where t.comosevende <> 'Paquete' and t.departamento.nombre = :nombre";
        Query query = session.createQuery(hql);
        query.setParameter("nombre", nombre);
        List<Tproducto> productosPorNombre = (List<Tproducto>) query.list();
        //session.close();
        return productosPorNombre;
    }

    public BigDecimal totalInventario(String nombre) {
        BigDecimal total = new BigDecimal("0.00");
        session = HibernateUtil.getSessionFactory().openSession();
        String hql = "select sum(t.cantidad * t.precioProveedor ) from Tproducto as t where t.comosevende <> 'Paquete' and t.departamento.nombre = :nombre";
        Query query = session.createQuery(hql);
           query.setParameter("nombre", nombre);
        try {
               total = (BigDecimal) query.uniqueResult();
               if(total == null){
                total = new BigDecimal("0.00");
               }
               System.out.println("total 27-04-2018 " +  total);
        } catch (Exception e) {
            System.out.println("error  27-04-2018 " + e);
        }
       
        return total;
    }
}
