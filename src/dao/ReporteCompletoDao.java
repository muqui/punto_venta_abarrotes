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
import modelo.Departamento;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
/**
 *
 * @author mq12
 */
public class ReporteCompletoDao {

    Session session;
    Transaction transaction;

    public List<Departamento> getDepartamento() throws Exception {
        session = HibernateUtil.getSessionFactory().openSession();
        String hql = "from Departamento ";
        Query query = session.createQuery(hql);

        List<Departamento> productosPorNombre = (List<Departamento>) query.list();
        session.close();
        return productosPorNombre;
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
        session.close();
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
        session.close();
        return ventas;
    }

}
