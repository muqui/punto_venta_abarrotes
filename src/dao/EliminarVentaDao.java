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
import javax.swing.JOptionPane;
import modelo.ContenidoPaquete;
import modelo.Departamento;
import modelo.Formadepago;
import modelo.Tproducto;
import modelo.Tventa;
import modelo.Tventadetalle;
import modelo.Usuario;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author mq12
 */
public class EliminarVentaDao {
 Session session;
  

    public void eliminarVenta1(int idVenta) {
          Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd ");
        String i = df.format(new Date());
        try {
            String hql = "from Tventadetalle as t where t.tventa.idVenta = :idVenta";
            Query query = session.createQuery(hql);
            query.setParameter("idVenta", idVenta);
            List<Tventadetalle> productosPorNombre = (List<Tventadetalle>) query.list();

            for (Tventadetalle p : productosPorNombre) {
                System.out.println("cantidadx  " + p.getCantidad() +  "id= " + p.getTproducto().getDescripcion());
                hql = "from Tproducto where idProducto=:idProducto";
                query = session.createQuery(hql);
                query.setParameter("idProducto", p.getTproducto().getIdProducto());
                Tproducto product0 = (Tproducto) query.uniqueResult();
                System.out.println("cantidad stock " + product0.getCantidad());
                BigDecimal cantidad = product0.getCantidad().add(p.getCantidad());
                System.out.println("");
                System.out.println("cantidad " +cantidad + " producto codido de barras  " + product0.getCodigoBarras());
                product0.setCantidad(cantidad);
                session.update(product0);

            }

            hql = "from Tventa as t where  t.fechaRegistro >= '" + i + "00:00:00' AND t.fechaRegistro <= '" + i + "23:59:59' AND t.idVenta = :idVenta";
            System.out.println("g " + hql);
            query = session.createQuery(hql);
            query.setParameter("idVenta", idVenta);
            Tventa venta = (Tventa) query.uniqueResult();
            if (venta != null) {
                System.out.println("resultado MUQUI " + venta.getIdVenta());
                hql = "delete Tventadetalle as t  where t.tventa.idVenta = :idVenta";
                query = session.createQuery(hql);
                query.setParameter("idVenta", idVenta);
                query.executeUpdate();

                hql = "delete Formadepago as f  where f.tventa.idVenta = :idVenta";
                query = session.createQuery(hql);
                query.setParameter("idVenta", idVenta);
                query.executeUpdate();

                System.out.println("res ñññ ===   " + query.executeUpdate());
                hql = "delete Tventa where idVenta = :idVenta";
                query = session.createQuery(hql);
                query.setParameter("idVenta", idVenta);
                query.executeUpdate();
                JOptionPane.showMessageDialog(null, "Producto " + idVenta + " Eliminado.");
            } else {

                JOptionPane.showMessageDialog(null, "Error al eliminar producto: \n", "error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar producto: \n" + e, "error", JOptionPane.ERROR_MESSAGE);
            transaction.rollback();
        }

        transaction.commit();

    }
    public void cerrar(){
     session.close();
    }

}
