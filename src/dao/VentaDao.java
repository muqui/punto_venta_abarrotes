/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import hibernate.HibernateUtil;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import modelo.Formadepago;
import modelo.Tventa;
import modelo.Tventadetalle;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author mq12
 */
public class VentaDao {

    Session session;
    Transaction transaction;

    public int insert(Tventa tVenta) {
        int idVenta = 0;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = this.session.beginTransaction();
            session.save(tVenta);
            idVenta = tVenta.getIdVenta();
            System.out.println("Tventa ID " + tVenta.getIdVenta());
            transaction.commit();
        } catch (Exception e) {
        } finally {
            session.close();
        }

        return idVenta;
    }

    public void insertFormaPago(Formadepago formadepago) {

        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = this.session.beginTransaction();
            session.save(formadepago);

            transaction.commit();
        } catch (Exception e) {
        } finally {
            session.close();
        }

    }

    public boolean insertVentaDetalle(Tventadetalle tVentaDetalle) {
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = this.session.beginTransaction();
            session.save(tVentaDetalle);

            transaction.commit();
        } catch (Exception e) {
        } finally {
            if (this.session != null) {
                this.session.close();
            }
        }
        return true;
    }

    public Tventa getUltimoRegistro(int idVenta) throws Exception {
        session = HibernateUtil.getSessionFactory().openSession();
        String hql = "from Tventa where idVenta=:idVenta";
        Query query = session.createQuery(hql).setMaxResults(1);
        query.setParameter("idVenta", idVenta);

        return (Tventa) query.uniqueResult();
    }

    public List<Tventadetalle> getVentasDetalle(Date desde, Date hasta, String usuario, String categoria) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd ");
        String i = df.format(desde);
        String f = df.format(hasta);
        session = HibernateUtil.getSessionFactory().openSession();
        String hql = " from Tventadetalle t where t.tventa.fechaRegistro >= '" + i + "00:00:00' AND t.tventa.fechaRegistro <= '" + f + "23:59:59'"
                + " AND t.tventa.usuario.nombre LIKE '%" + usuario + "%' AND t.imprimir = 1 AND t.tproducto.departamento.nombre LIKE '%" + categoria + "%' ORDER BY  t.tventa.fechaRegistro desc";
        System.out.println("hql = " + hql);
        Query query = session.createQuery(hql);
        List<Tventadetalle> ventas = (List<Tventadetalle>) query.list();
        return ventas;
    }

    public BigDecimal getVentas( Date desde, Date hasta, String usuario, String categoria) throws Exception {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd ");
        String i = df.format(desde);
        String f = df.format(hasta);
        session = HibernateUtil.getSessionFactory().openSession();
       String hql = "select sum(totalPrecioVenta) from Tventadetalle t where t.tventa.fechaRegistro >= '" + i + "00:00:00' AND t.tventa.fechaRegistro <= '" + f + "23:59:59'"
                + " AND t.tventa.usuario.nombre LIKE '%" + usuario + "%' AND t.imprimir = 1 AND t.tproducto.departamento.nombre LIKE '%" + categoria + "%' ORDER BY  t.tventa.fechaRegistro desc";
        //  String hql = "select sum(totalPrecioVenta) from Tventadetalle t where t.tventa.fechaRegistro >= '" + i + "00:00:00' AND t.tventa.fechaRegistro <= '" + f + "23:59:59'  AND t.imprimir = 1 AND t.tproducto.departamento.nombre = '" + categoria + "' ";
        
        
        Query query = session.createQuery(hql);

        BigDecimal ventas = (BigDecimal) query.uniqueResult();
        if (ventas == null) {
            ventas = new java.math.BigDecimal("0.00");
        }
        return ventas;
    }
     public BigDecimal getVentasTotal( Date desde, Date hasta, String usuario, String categoria) throws Exception {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd ");
        String i = df.format(desde);
        String f = df.format(hasta);
        session = HibernateUtil.getSessionFactory().openSession();
        //MUESTRA LOS PRODUCTOS CON PAQUETES
          String hql = "select sum(totalPrecioVenta) from Tventadetalle t where t.tventa.fechaRegistro >= '" + i + "00:00:00' AND t.tventa.fechaRegistro <= '" + f + "23:59:59'  AND t.imprimir = 1 AND t.tproducto.departamento.nombre = '" + categoria + "' ";
        // MUESTRA LOS PRODUCTOS SIN PAQUETE
       // String hql = "select sum(totalPrecioVenta) from Tventadetalle t where t.tventa.fechaRegistro >= '" + i + "00:00:00' AND t.tventa.fechaRegistro <= '" + f + "23:59:59'  AND t.imprimir = 1 AND t.tproducto.departamento.nombre = '" + categoria + "' ";
        
        
        Query query = session.createQuery(hql);

        BigDecimal ventas = (BigDecimal) query.uniqueResult();
        if (ventas == null) {
            ventas = new java.math.BigDecimal("0.00");
        }
        return ventas;
    }
      public BigDecimal getVentasTotalModificado( Date desde, Date hasta, String usuario, String categoria) throws Exception {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd ");
        String i = df.format(desde);
        String f = df.format(hasta);
        session = HibernateUtil.getSessionFactory().openSession();
        //MUESTRA LOS PRODUCTOS CON PAQUETES
          String hql = "select sum(totalPrecioVenta) from Tventadetalle t where t.tventa.fechaRegistro >= '" + i + "00:00:00' AND t.tventa.fechaRegistro <= '" + f + "23:59:59'  AND t.comosevende != 'Paquete' AND t.tproducto.departamento.nombre = '" + categoria + "' ";
        // MUESTRA LOS PRODUCTOS SIN PAQUETE
       // String hql = "select sum(totalPrecioVenta) from Tventadetalle t where t.tventa.fechaRegistro >= '" + i + "00:00:00' AND t.tventa.fechaRegistro <= '" + f + "23:59:59'  AND t.imprimir = 1 AND t.tproducto.departamento.nombre = '" + categoria + "' ";
        
        
        Query query = session.createQuery(hql);

        BigDecimal ventas = (BigDecimal) query.uniqueResult();
        if (ventas == null) {
            ventas = new java.math.BigDecimal("0.00");
        }
        return ventas;
    }
    //total precio Proveedor
     public BigDecimal getPrecioProveedorTotal( Date desde, Date hasta, String usuario, String categoria) throws Exception {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd ");
        String i = df.format(desde);
        String f = df.format(hasta);
        session = HibernateUtil.getSessionFactory().openSession();
        String hql = "select sum(precioProveedor) from Tventadetalle t where t.tventa.fechaRegistro >= '" + i + "00:00:00' AND t.tventa.fechaRegistro <= '" + f + "23:59:59'"
                + " AND t.tventa.usuario.nombre LIKE '%" + usuario + "%' AND t.imprimir = 1 AND t.tproducto.departamento.nombre LIKE '%" + categoria + "%' ORDER BY  t.tventa.fechaRegistro desc";
        System.out.println("hql = " + hql);
        Query query = session.createQuery(hql);

        BigDecimal ventas = (BigDecimal) query.uniqueResult();
        if (ventas == null) {
            ventas = new java.math.BigDecimal("0.00");
        }
        return ventas;
    }

    public void cerrar() {
        System.out.println(" C E  R    R    A    R        S    E      S     I     O     N .................................................");
        this.session.close();
    }

}
