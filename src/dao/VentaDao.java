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
    public VentaDao() {
        // session = HibernateUtil.getSessionFactory().openSession();
        // transaction = this.session.beginTransaction();
    }

    public int insert(Tventa venta, List<Formadepago> listFormaDePago) {
        int idVenta = 0;
        try {
             session = HibernateUtil.getSessionFactory().openSession();
             transaction = session.beginTransaction();
            for (Tventadetalle v : venta.getTventadetalles()) {
                v.setTventa(venta);
            }

            session.save(venta);

            for (Formadepago f : listFormaDePago) {
                f.setTventa(venta);
                f.setCantidad(venta.getPrecioVentaTotal());
                session.save(f);
            }
            idVenta = venta.getIdVenta();
            System.out.println("Tventa ID " + venta.getIdVenta() + " detalle " + venta.getTventadetalles());
            transaction.commit();
            //  session.close();
        } catch (Exception e) {
            System.out.println("error + " + e);
        } finally {

        }

        return idVenta;
    }

  

    public void cerrar() {
        session.close();
    }
}
