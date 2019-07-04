/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import hibernate.HibernateUtil;
import java.math.BigDecimal;
import java.util.List;
import modelo.Departamento;
import modelo.Tproducto;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author mq12
 */
public class InventarioDao {
    
    
    Session session;
    Transaction transaction;
    
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
       public List<Departamento> getDepartamento() throws Exception {
        session = HibernateUtil.getSessionFactory().openSession();
        String hql = "from Departamento ";
        Query query = session.createQuery(hql);

        List<Departamento> productosPorNombre = (List<Departamento>) query.list();

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
         session.close();
        return total;
    }
    
     public List<Tproducto> productosInventario(String nombre) {
        session = HibernateUtil.getSessionFactory().openSession();
        String hql = "from Tproducto t where t.comosevende <> 'Paquete' and t.departamento.nombre = :nombre";
        Query query = session.createQuery(hql);
        query.setParameter("nombre", nombre);
        List<Tproducto> productosPorNombre = (List<Tproducto>) query.list();
        session.close();
        return productosPorNombre;
    }
     
     public void cerrar(){
         session.close();
     }
}
