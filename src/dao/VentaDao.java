/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import hibernate.HibernateUtil;
import java.util.List;
import modelo.Formadepago;
import modelo.Tproducto;
import modelo.Tventa;
import modelo.Tventadetalle;
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
        System.out.println("Vendedor " +  venta.getUsuario().getNombre());
        int idVenta = 0;

        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            for (Tventadetalle v : venta.getTventadetalles()) {
                Tproducto p = v.getTproducto();
                p.setCantidad(p.getCantidad().subtract(v.getCantidad()));
                System.out.println("Codigo de barras a modificar = " + p.getCodigoBarras());
                v.setTventa(venta);
                if (p.getInventariar()) {
                    session.merge(p); //actualiza la cantidad de productos
                }

            }

            //session.save(venta);
            session.persist(venta);

            for (Formadepago f : listFormaDePago) {
                f.setTventa(venta);
                f.setCantidad(venta.getPrecioVentaTotal());
                session.persist(f);
                //session.save(f);
            }
            idVenta = venta.getIdVenta();

            transaction.commit();
            System.out.println("Se vendio "  );
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
