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
import modelo.Tventadetalle;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author mq12
 */
public class MisVentasDao {

    //Session session;
    //Transaction transaction;
    public List<Tventadetalle> getVentasDetalle(Date desde, Date hasta, String usuario, String categoria) {
        Session session1 = HibernateUtil.getSessionFactory().openSession();
        session1.beginTransaction();
        List<Tventadetalle> ventas = null;
       
            System.out.println("lista total ventas ..............................");

            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd ");
            String i = df.format(desde);
            String f = df.format(hasta);

            String hql = " from Tventadetalle t where t.tventa.fechaRegistro >= '" + i + "00:00:00' AND t.tventa.fechaRegistro <= '" + f + "23:59:59'"
                    + " AND t.tventa.usuario.nombre LIKE '%" + usuario + "%' AND t.imprimir = 1 AND t.tproducto.departamento.nombre LIKE '%" + categoria + "%' ORDER BY  t.tventa.fechaRegistro desc";
            System.out.println("" + hql);
            // Query query = session.createQuery(hql);
            ventas = (List<Tventadetalle>) session1.createQuery(hql).list();
            session1.getTransaction().commit();

            for (Tventadetalle t : ventas) {
                System.out.println("Codigo barras " + t.getCodigoBarrasProducto());
            }

            System.out.println("lista total ventas .............................. FIN");

        
        return ventas;
    }

    public BigDecimal getVentas(Date desde, Date hasta, String usuario, String categoria) {
        System.out.println("total ventas ..............................");
        BigDecimal ventas = null;
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd ");
            String i = df.format(desde);
            String f = df.format(hasta);

            String hql = "select sum(totalPrecioVenta) from Tventadetalle t where t.tventa.fechaRegistro >= '" + i + "00:00:00' AND t.tventa.fechaRegistro <= '" + f + "23:59:59'"
                    + " AND t.tventa.usuario.nombre LIKE '%" + usuario + "%' AND t.imprimir = 1 AND t.tproducto.departamento.nombre LIKE '%" + categoria + "%' ORDER BY  t.tventa.fechaRegistro desc";

            Query query = session.createQuery(hql);
            System.out.println("Total " + ventas);
            ventas = (BigDecimal) query.uniqueResult();
            if (ventas == null) {
                ventas = new java.math.BigDecimal("0.00");
            }
            System.out.println("total ventas .............................. FIN");
            // session.close();
        } catch (Exception e) {
            System.out.println("error total " + e);
        } finally {
            return ventas;
        }
    }

//    public void cerrar() {
//        session.close();
//    }
}
