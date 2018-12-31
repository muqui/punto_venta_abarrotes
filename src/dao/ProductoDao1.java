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
public class ProductoDao1 {

     private Session session = HibernateUtil.getSessionFactory().openSession();
       

    public ProductoDao1() {
       
    }
    public void iniciarSesion(){
       
    }

    public boolean modificarProducto(Tproducto producto) {
        boolean resultado = true;
        try {
             Transaction transaction = getSession().beginTransaction();
            getSession().update(producto);
            transaction.commit();
          //  JOptionPane.showMessageDialog(null, "Producto " + producto.getDescripcion() + " Modificado.");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERROR nx " + e);
            System.out.println("error " + e);
            resultado = false;
        } finally {
           // session.close();
        }
        return resultado;
    }
      public Departamento getDepartamento(String nombre) {
        String hql = "from Departamento where nombre=:nombre";
        Query query = getSession().createQuery(hql);
        query.setParameter("nombre", nombre);       
        return (Departamento) query.uniqueResult();

    }

    public Tproducto getByCodigoBarras(String codigoBarras) throws Exception {
       // session = HibernateUtil.getSessionFactory().openSession();
        String hql = "from Tproducto where codigoBarras=:codigoBarras";
        Query query = getSession().createQuery(hql);
        query.setParameter("codigoBarras", codigoBarras);
        
        return (Tproducto) query.uniqueResult();

    }

    public boolean addContenidoPaquete(ContenidoPaquete contenidoPaquete) {
        boolean resultado = true;
        try {
            
            Transaction  transaction = this.getSession().beginTransaction();
            getSession().save(contenidoPaquete);
            transaction.commit();
            // JOptionPane.showMessageDialog(null, "Departamento:  " + contenidoPaquete.getId() + " agregado.");

        } catch (Exception e) {
            resultado = false;
            JOptionPane.showMessageDialog(null, "Error al crear contnido paquete: \n" + e, "error ", JOptionPane.ERROR_MESSAGE);
        } finally {
            //session.close();
        }
        return resultado;
    }

    public void borrarContenidoPaquete(int idPaquete) {
        try {
            System.out.println("borrar");
            
            String hql = "delete ContenidoPaquete where idPaquete = :idPaquete";
            Query query = getSession().createQuery(hql);
            query.setParameter("idPaquete", idPaquete);
            query.executeUpdate();
        } catch (Exception e) {
            System.out.println("errrrt" + e);
        } finally {
          //  session.close();
        }

    }
      public List<Tproducto> getPorNombre(String nombre) throws Exception {
       // session = HibernateUtil.getSessionFactory().openSession();
        String hql = "from Tproducto where nombre LIKE :nombre";
        Query query = getSession().createQuery(hql);
        query.setParameter("nombre", "%" + nombre + "%");
        List<Tproducto> productosPorNombre = (List<Tproducto>) query.list();
       // session.close();
        return productosPorNombre;
    }
      public void cerrar(){
          getSession().close();
      }
      public void limpiar(){
          getSession().clear();
      }

    /**
     * @return the session
     */
    public Session getSession() {
        return session;
    }

    /**
     * @param session the session to set
     */
    public void setSession(Session session) {
        this.session = session;
    }

}
