/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import controlador.ControladorAltaUsuario;
import controlador.ControladorClientes;
import controlador.ControladorCrearDepartamento;
import controlador.ControladorEliminarEgreso;
import controlador.ControladorEliminarIngreso;
import controlador.ControladorEliminarVenta;
import controlador.ControladorIngresoEgreso;
import controlador.ControladorInventario;
import controlador.ControladorLoginYventas;
import controlador.ControladorMisVentas;
import controlador.ControladorMostrarEgresos;
import controlador.ControladorMostrarIngresos;
import controlador.ControladorProductos;
import controlador.ControladorProductosModificar;
import controlador.ControladorReporteCompleto;
import controlador.ControladorReportes;
import controlador.RespaldoBaseDeDatos;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Usuario;
import vista.CrearAdmin;
import vista.JDialogBuscarProducto;
import vista.JDialogClientes;
import vista.JDialogEgreso;
import vista.JDialogIngreso;
import vista.JDialogMasDe1Producto;
import vista.JDialogVentaAgranel;
import vista.JpanelClientes;
import vista.JpanelProductos;
import vista.JpanelVentas;
import vista.Login;
import vista.Principal;
import vista.JDialogVentaFinal;
import vista.JPanelConfiguracion;
import vista.JPanelInventario;
import vista.JPanelReportes;
import vista.JPanelTicket;
import vista.cliente.JpanelClienteNuevo;
import vista.configuracion.JPanelAltaUsuario;
import vista.inventario.JPanelInventariolista;
import vista.producto.JDialogBuscarProductoModificar;
import vista.producto.JDialogModificarPaquete;
import vista.producto.JpanelContenidoPaquete;
import vista.producto.JpanelDepartamento;
import vista.producto.JpanelProductoModificar;
import vista.producto.JpanelProductoNuevo;
import vista.reporte.JDialogEliminarEgreso;
import vista.reporte.JDialogEliminarIngreso;
import vista.reporte.JDialogEliminarVenta;
import vista.reporte.JPanelMisVentas;
import vista.reporte.JPanelReporteCompleto;
import vista.reporte.JPanelReporteEgreso;
import vista.reporte.JPanelReporteIngreso;
import vista.reporte.JPanelReporteVentas;

/**
 *
 * @author mq12
 */
public class Main {

    public static void main(String[] args) {
        Thread respaldo = new RespaldoBaseDeDatos(false);
        respaldo.start();
        System.out.println("estado hilo " + respaldo.getName());
        //RESPALDO BASE DE DATOS
 //       RespaldoBaseDeDatos respaldoBaseDeDatos = new RespaldoBaseDeDatos();
//        try {
//            respaldoBaseDeDatos.GenerarBackupMySQL();
//        } catch (IOException ex) {
//           Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
//        }

        //Modelo
        Usuario usuario = new Usuario();

        //Vista
        JPanelTicket jPanelTicket = new JPanelTicket();
        Principal vistaPrincipal = new Principal();
        JpanelClientes jpanelClientes = new JpanelClientes();
        JpanelVentas jpanelVentas = new JpanelVentas();
        JpanelProductos jpanelProductos = new JpanelProductos();
        JpanelProductoNuevo jpanelProductoNuevo = new JpanelProductoNuevo();
        JpanelProductoModificar jpanelProductoModificar = new JpanelProductoModificar();
        JpanelDepartamento jpanelDepartamento = new JpanelDepartamento();
        JPanelInventario jPanelInventario = new JPanelInventario();
        JPanelInventariolista JPanelInventariolista = new JPanelInventariolista();
        JPanelReportes jpanelReportes = new JPanelReportes();
        JPanelReporteVentas jPanelReporteVentas = new JPanelReporteVentas();
        JpanelClienteNuevo jpanelClienteNuevo = new JpanelClienteNuevo();
        JPanelConfiguracion jPanelConfiguracion = new JPanelConfiguracion();  //x
        JPanelAltaUsuario jPanelAltaUsuario = new JPanelAltaUsuario();  //x
        JDialogModificarPaquete jDialogModificarPaquete = new JDialogModificarPaquete(vistaPrincipal, true);
        JpanelContenidoPaquete jpanelContenidoPaquete = new JpanelContenidoPaquete();
        CrearAdmin crearAdmin = new CrearAdmin(vistaPrincipal, true);
        JDialogClientes jDialogClientes = new JDialogClientes(vistaPrincipal, true);
        Login login = new Login(vistaPrincipal, true);
        JDialogBuscarProducto jDialogBuscarProducto = new JDialogBuscarProducto(vistaPrincipal, true);
        JDialogBuscarProductoModificar jDialogBuscarProductoModificar = new JDialogBuscarProductoModificar(vistaPrincipal, true);
        JDialogMasDe1Producto jDialogMasDe1Producto = new JDialogMasDe1Producto(vistaPrincipal, true);
        JDialogVentaAgranel jDialogVentaAgranel = new JDialogVentaAgranel(vistaPrincipal, true);
        JDialogVentaFinal jdialogVentaFinal = new JDialogVentaFinal(vistaPrincipal, true);
        JDialogIngreso jDialogIngreso = new JDialogIngreso(vistaPrincipal, true);
        JDialogEgreso jDialogEngreso = new JDialogEgreso(vistaPrincipal, true);
        JPanelReporteIngreso jPanelReporteIngreso = new JPanelReporteIngreso();
        JPanelReporteEgreso jPanelReporteEgreso = new JPanelReporteEgreso();
        JPanelReporteCompleto jPanelReporteCompleto = new JPanelReporteCompleto();
        JDialogEliminarVenta JDialogEliminarVenta = new JDialogEliminarVenta(vistaPrincipal, true);
        JDialogEliminarEgreso jDialogEliminarEgreso = new JDialogEliminarEgreso(vistaPrincipal, true);
        JDialogEliminarIngreso jDialogEliminarIngreso = new JDialogEliminarIngreso(vistaPrincipal, true);
        JPanelMisVentas jPanelMisVentas = new JPanelMisVentas(); 
        //Controlador
        ControladorMisVentas controladorMisVentas = new ControladorMisVentas(vistaPrincipal, jPanelMisVentas);
        ControladorEliminarVenta controladorEliminarVenta = new ControladorEliminarVenta(vistaPrincipal, jpanelReportes, jPanelReporteVentas, JDialogEliminarVenta);
        ControladorReporteCompleto controladorReporteCompleto = new ControladorReporteCompleto(vistaPrincipal, jpanelReportes, jPanelReporteCompleto);
        ControladorMostrarEgresos controladorMostrarEgresos = new ControladorMostrarEgresos(vistaPrincipal, jpanelReportes, jPanelReporteEgreso, jDialogEngreso);
        ControladorMostrarIngresos controladorMostrarIngresos = new ControladorMostrarIngresos(vistaPrincipal, jpanelReportes, jPanelReporteIngreso, jDialogIngreso);
        ControladorIngresoEgreso controladorIngresoEgreso = new ControladorIngresoEgreso(vistaPrincipal, jDialogIngreso, jDialogEngreso, jpanelVentas);
        ControladorAltaUsuario controladorAltaUsuario = new ControladorAltaUsuario(vistaPrincipal, jPanelConfiguracion, jPanelAltaUsuario);
        ControladorInventario ControladorInventario = new ControladorInventario(vistaPrincipal, jPanelInventario, JPanelInventariolista);
        ControladorCrearDepartamento controladorCrearDepartamento = new ControladorCrearDepartamento(vistaPrincipal, jpanelDepartamento, jpanelProductos);
        ControladorClientes controladorClientes = new ControladorClientes(vistaPrincipal, jpanelClientes, jpanelClienteNuevo);
        ControladorProductos controladorProductos = new ControladorProductos(vistaPrincipal, jpanelProductos, jpanelProductoNuevo, jpanelContenidoPaquete);
        ControladorReportes controladorReportes = new ControladorReportes(vistaPrincipal, jpanelReportes, jPanelReporteVentas);
        ControladorLoginYventas controladorLogin = new ControladorLoginYventas(vistaPrincipal, login, usuario, jpanelVentas, crearAdmin, jDialogBuscarProducto, jDialogMasDe1Producto, jDialogVentaAgranel, jdialogVentaFinal, jDialogClientes, jPanelTicket);
        ControladorProductosModificar controladorProductosModificar = new ControladorProductosModificar(vistaPrincipal, jpanelProductos, jpanelProductoModificar, jDialogBuscarProductoModificar, jDialogModificarPaquete);
        ControladorEliminarEgreso controladorEliminarEgreso = new ControladorEliminarEgreso(vistaPrincipal, jpanelReportes, jPanelReporteEgreso, jDialogEliminarEgreso);
         ControladorEliminarIngreso controladorEliminarIngreso = new ControladorEliminarIngreso(vistaPrincipal, jpanelReportes, jPanelReporteIngreso, jDialogEliminarIngreso);
//inicio
        controladorLogin.start();
    }
}
