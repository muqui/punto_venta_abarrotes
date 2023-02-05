/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import MYSQL.ConnectionMYSQLManager;
import hibernate.HibernateUtil;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import modelo.Egreso;
import modelo.Ingreso;
import modelo.Movimiento;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author mq12
 */

public class IngresoEgresoDao {

    ConnectionMYSQLManager con = new ConnectionMYSQLManager();
    private BigDecimal totalEgreso;
    Session session;
    Transaction transaction;

    public void addMovimiento(Movimiento movimiento) throws Exception {
        session = HibernateUtil.getSessionFactory().openSession();
        transaction = this.session.beginTransaction();
        session.save(movimiento);

        transaction.commit();
        session.close();
    }

    public boolean addIngreso(Ingreso ingreso) {
        boolean bandera = true;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = this.session.beginTransaction();

            session.save(ingreso);
            transaction.commit();
            session.close();
        } catch (Exception e) {
            bandera = false;
        }

        return bandera;
    }

    public List<Movimiento> getMovimientos(String tipo) {
        session = HibernateUtil.getSessionFactory().openSession();
        String hql = "from Movimiento where tipo LIKE :tipo";
        Query query = session.createQuery(hql);
        query.setParameter("tipo", tipo);
        List<Movimiento> productosPorNombre = (List<Movimiento>) query.list();
        session.close();
        return productosPorNombre;
    }

    public boolean addEgreso(Egreso egreso) {
        boolean bandera = true;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = this.session.beginTransaction();

            session.save(egreso);
            transaction.commit();
            session.close();
        } catch (Exception e) {
            bandera = false;
            System.out.println("Error al crear egreso " + e);
        }

        return bandera;
    }

    public List<Ingreso> getIngresos(Date desde, Date hasta) throws Exception {
        session = HibernateUtil.getSessionFactory().openSession();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd ");
        String i = df.format(desde);
        String f = df.format(hasta);

        String hql = " from Ingreso where fecha >= '" + i + "' AND fecha <= '" + f + "'";
        System.out.println("hql = " + hql);
        Query query = session.createQuery(hql);

        List<Ingreso> ingresos = (List<Ingreso>) query.list();
        session.close();
        return ingresos;
    }

    public BigDecimal getSumaIngresos(Date desde, Date hasta) throws Exception {
        session = HibernateUtil.getSessionFactory().openSession();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd ");
        String i = df.format(desde);
        String f = df.format(hasta);

        String hql = "select sum(cantidad) from Ingreso where fecha >= '" + i + "' AND fecha <= '" + f + "'";
        System.out.println("hql = " + hql);
        Query query = session.createQuery(hql);

        BigDecimal ingresos = (BigDecimal) query.uniqueResult();
        if (ingresos == null) {
            ingresos = new java.math.BigDecimal("0.00");
        }
        session.close();
        return ingresos;
    }

//    public List<Egreso> getEgresos(Date desde, Date hasta) throws Exception {
//        session = HibernateUtil.getSessionFactory().openSession();
//        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd ");
//        String i = df.format(desde);
//        String f = df.format(hasta);
//
//        String hql = " from Egreso where fecha >= '" + i + "' AND fecha <= '" + f + "'";
//        System.out.println("hql = " + hql);
//        Query query = session.createQuery(hql);
//
//        List<Egreso> egresos = (List<Egreso>) query.list();
//        session.close();
//        return egresos;
//    }
    public BigDecimal getSumaEgresos(Date desde, Date hasta) throws Exception {
        session = HibernateUtil.getSessionFactory().openSession();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd ");
        String i = df.format(desde);
        String f = df.format(hasta);

        String hql = "select sum(cantidad) from Egreso where fecha >= '" + i + "' AND fecha <= '" + f + "'";
        System.out.println("hql = " + hql);
        Query query = session.createQuery(hql);

        BigDecimal ingresos = (BigDecimal) query.uniqueResult();
        if (ingresos == null) {
            ingresos = new java.math.BigDecimal("0.00");
        }
        session.close();
        return ingresos;
    }

    public BigDecimal getSumaEgresos(Date desde, Date hasta, String movimiento) throws Exception {
        session = HibernateUtil.getSessionFactory().openSession();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd ");
        String i = df.format(desde);
        String f = df.format(hasta);

        String hql = "select sum(cantidad) from Egreso where fecha >= '" + i + "' AND fecha <= '" + f + "' AND nombre = '" + movimiento + "'";
        System.out.println("hql = " + hql);
        Query query = session.createQuery(hql);

        BigDecimal ingresos = (BigDecimal) query.uniqueResult();
        if (ingresos == null) {
            ingresos = new java.math.BigDecimal("0.00");
        }
        session.close();
        return ingresos;
    }
    public BigDecimal getSumaIngresos(Date desde, Date hasta, String movimiento) throws Exception {
        session = HibernateUtil.getSessionFactory().openSession();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd ");
        String i = df.format(desde);
        String f = df.format(hasta);

        String hql = "select sum(cantidad) from Ingreso where fecha >= '" + i + "' AND fecha <= '" + f + "' AND nombre = '" + movimiento + "'";

        Query query = session.createQuery(hql);

        BigDecimal ingresos = (BigDecimal) query.uniqueResult();
        if (ingresos == null) {
            ingresos = new java.math.BigDecimal("0.00");
        }
        session.close();
        return ingresos;
    }

    public void cerrar() {
        session.close();
    }

    public Movimiento getMovimiento(String nombre, String tipo) {
        session = HibernateUtil.getSessionFactory().openSession();
        String hql = "from Movimiento where nombre=:nombre and tipo=:tipo";

        Query query = session.createQuery(hql);
        query.setParameter("nombre", nombre);
        query.setParameter("tipo", tipo);
        // session.close();
        return (Movimiento) query.uniqueResult();

    }

    public int borrarEgreso(String id) {
        int r = 0;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd ");
        String i = df.format(new Date());
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        String hql = "delete Egreso where id = :id AND  fechaMovimiento >= '" + i + "00:00:00' AND fechaMovimiento <= '" + i + "23:59:59' ";
        Query query = session.createQuery(hql);
        query.setParameter("id", Integer.parseInt(id));
        r = query.executeUpdate();

        transaction.commit();
        session.close();
        System.out.println("exito!!!!!!!!!!!!!!!!!!!!!! ??????????????????????????????????????????????????????????? " + r);
        return r;
    }

    public int borrarIngreso(String id) {
        int r = 0;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd ");
        String i = df.format(new Date());
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        String hql = "delete Ingreso where id = :id AND  fechaMovimiento >= '" + i + "00:00:00' AND fechaMovimiento <= '" + i + "23:59:59' ";
        Query query = session.createQuery(hql);
        query.setParameter("id", Integer.parseInt(id));
        r = query.executeUpdate();

        transaction.commit();
        session.close();
        System.out.println("exito!!!!!!!!!!!!!!!!!!!!!! ??????????????????????????????????????????????????????????? " + r);
        return r;
    }

    //CAMBIOS A BASE DE DATOS SIN APOYO
    public List<Egreso> getEgresos(Date desde, Date hasta) throws Exception {
        totalEgreso = new BigDecimal("0.00");
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd ");
        String i = df.format(desde);
        String f = df.format(hasta);

        List<Egreso> egresos = new ArrayList<>();
        Connection conexion = con.getConnection(); //Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/puntoventa", "root", "Fedora12");
        Statement s = conexion.createStatement();
        String consultaSql = " select * from Egreso where fecha >= '" + i + "' AND fecha <= '" + f + "'";
        System.out.println("Consulta Egresos " + consultaSql);
        ResultSet rs = s.executeQuery(consultaSql);

        while (rs.next()) {

            Egreso egreso = new Egreso();
            //  fila[0] = "" + rs.getInt(1);

            egreso.setId(rs.getInt(1));
            egreso.setFecha(rs.getDate(7));
            egreso.setNombre(rs.getString(6));
            egreso.setDescripcion(rs.getString(3));
            egreso.setCantidad(rs.getBigDecimal(2));
            totalEgreso = getTotalEgreso().add(egreso.getCantidad());
            egresos.add(egreso);
        }
        // Cerramos la conexion a la base de datos. 
        conexion.close();

        return egresos;
    }

    /**
     * @return the totalEgreso
     */
    public BigDecimal getTotalEgreso() {
        return totalEgreso;
    }

}
