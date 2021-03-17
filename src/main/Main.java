/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import controlador.ControladorAltaUsuario;
import controlador.ControladorConfigurarBaseDatos;
import controlador.ControladorCrearDepartamento;
import controlador.ControladorEliminarEgreso;
import controlador.ControladorEliminarIngreso;
import controlador.ControladorEliminarVenta;
import controlador.ControladorIngresoEgreso;
import controlador.ControladorMisVentas;
import controlador.ControladorProductos;
import controlador.ControladorVender;
import controlador.ControlerLogin;
import modelo.Usuario;
import vista.JDialogBuscarProducto;
import vista.JDialogMasDe1Producto;
import vista.JDialogVentaAgranel;
import vista.JDialogVentaFinal;
import vista.JDialogVerificadorPrecios;
import vista.JPanelTicket;
import vista.JpanelVentas;
import vista.Login;
import vista.Principal;
import vista.reporte.JPanelMisVentas;
import vista.JpanelProductos;
import vista.producto.JpanelProductoNuevo;
import vista.producto.JpanelContenidoPaquete;
import controlador.ControladorInventario;
import controlador.ControladorMostrarEgresos;
import controlador.ControladorMostrarIngresos;
import controlador.ControladorProductosModificar;
import controlador.ControladorReporteCompleto;
import controlador.ControladorReportes;
import controlador.ControladorXbox;
import modelo.RespaldoBaseDeDatos;
import vista.CrearAdmin;
import vista.JDialogEgreso;
import vista.JDialogIngreso;
import vista.JPanelInventario;
import vista.JPanelReportes;
import vista.inventario.JPanelInventariolista;
import vista.reporte.JPanelReporteVentas;
import vista.reporte.JPanelReporteIngreso;
import vista.reporte.JPanelReporteEgreso;
import vista.reporte.JPanelReporteCompleto;
import vista.reporte.JDialogEliminarIngreso;
import vista.reporte.JDialogEliminarEgreso;
import vista.reporte.JDialogEliminarVenta;
import vista.configuracion.JPanelAltaUsuario;
import vista.configuracion.JPanelBaseDeDatos;
import vista.JPanelConfiguracion;
import vista.jpanelXBOX;
import vista.producto.JDialogBuscarProductoModificar;
import vista.producto.JDialogModificarPaquete;
import vista.producto.JpanelProductoModificar;
import vista.producto.JpanelDepartamento;

/**
 *
 * @author mq12
 */
public class Main {

    public static void main(String[] args) {

        //MODELO
        Usuario usuario = new Usuario();
        //VISTA

        Principal vistaPrincipal = new Principal();
        Login login = new Login(vistaPrincipal, true);
        JpanelVentas jpanelVentas = new JpanelVentas();
        jpanelXBOX jpanelXbox = new jpanelXBOX();
        JPanelTicket jPanelTicket = new JPanelTicket();
        JDialogVentaAgranel jDialogVentaAgranel = new JDialogVentaAgranel(vistaPrincipal, true);
        JDialogMasDe1Producto jDialogMasDe1Producto = new JDialogMasDe1Producto(vistaPrincipal, true);
        JDialogVentaFinal jDialogVentaFinal = new JDialogVentaFinal(vistaPrincipal, true);
        JPanelMisVentas jpanelMisVentas = new JPanelMisVentas();
        JDialogBuscarProducto jDialogBuscarProducto = new JDialogBuscarProducto(vistaPrincipal, true);
        JDialogVerificadorPrecios jDialogVerificadorPrecios = new JDialogVerificadorPrecios(vistaPrincipal, true);
        JpanelProductos jpanelProductos = new JpanelProductos();
        JpanelProductoNuevo jpanelProductoNuevo = new JpanelProductoNuevo();
        JpanelContenidoPaquete jpanelContenidoPaquete = new JpanelContenidoPaquete();
        JPanelInventario jPanelInventario = new JPanelInventario();
        JPanelInventariolista jPanelInventariolista = new JPanelInventariolista();
        JDialogEgreso jDialogEgreso = new JDialogEgreso(vistaPrincipal, true);
        JDialogIngreso jDialogIngreso = new JDialogIngreso(vistaPrincipal, true);
        JPanelReporteVentas jPanelReporteVentas = new JPanelReporteVentas();
        JPanelReportes jpanelReportes = new JPanelReportes();
        JPanelReporteIngreso jPanelReporteIngreso = new JPanelReporteIngreso();
        JPanelReporteEgreso jPanelReporteEgreso = new JPanelReporteEgreso();
        JPanelReporteCompleto jPanelReporteCompleto = new JPanelReporteCompleto();
        JDialogEliminarIngreso jDialogEliminarIngreso = new JDialogEliminarIngreso(vistaPrincipal, true);
        JDialogEliminarEgreso jDialogEliminarEgreso = new JDialogEliminarEgreso(vistaPrincipal, true);
        JDialogEliminarVenta jDialogEliminarVenta = new JDialogEliminarVenta(vistaPrincipal, true);
        JPanelAltaUsuario jPanelAltaUsuario = new JPanelAltaUsuario();
        JPanelConfiguracion jPanelConfiguracion = new JPanelConfiguracion();
        JPanelBaseDeDatos jPanelBaseDeDatos = new JPanelBaseDeDatos();
        JpanelProductoModificar jpanelProductoModificar = new JpanelProductoModificar();
        JDialogBuscarProductoModificar jDialogBuscarProductoModificar = new JDialogBuscarProductoModificar(vistaPrincipal, true);
        JDialogModificarPaquete jDialogModificarPaquete = new JDialogModificarPaquete(vistaPrincipal, true);
        CrearAdmin crearAdmin = new CrearAdmin(vistaPrincipal, true);
        JpanelDepartamento jpanelDepartamento = new JpanelDepartamento();
        //CONTROLADOR
        ControlerLogin controllerLogin = new ControlerLogin(vistaPrincipal, login, jpanelVentas, crearAdmin);
        controllerLogin.iniciar();
        ControladorMisVentas controladorMisVentas = new ControladorMisVentas(vistaPrincipal, jpanelMisVentas);
        ControladorVender controladorVender = new ControladorVender(vistaPrincipal, jpanelVentas, jPanelTicket, jDialogVentaAgranel, jDialogMasDe1Producto, jDialogVentaFinal, jDialogBuscarProducto, jDialogVerificadorPrecios);
        ControladorXbox controladorXbox = new ControladorXbox(vistaPrincipal, jpanelXbox);
        ControladorProductos controladorProductos = new ControladorProductos(vistaPrincipal, jpanelProductos, jpanelProductoNuevo, jpanelContenidoPaquete);
        ControladorInventario controladorInventario = new ControladorInventario(vistaPrincipal, jPanelInventario, jPanelInventariolista);
        ControladorReportes controladorReportes = new ControladorReportes(vistaPrincipal, jpanelReportes, jPanelReporteVentas);
        ControladorIngresoEgreso controladorIngresoEgreso = new ControladorIngresoEgreso(vistaPrincipal, jDialogIngreso, jDialogEgreso, jpanelVentas);
        ControladorMostrarIngresos controladorMostrarIngresos = new ControladorMostrarIngresos(vistaPrincipal, jpanelReportes, jPanelReporteIngreso, jDialogIngreso);
        ControladorMostrarEgresos controladorMostrarEgresos = new ControladorMostrarEgresos(vistaPrincipal, jpanelReportes, jPanelReporteEgreso, jDialogEgreso);
        ControladorReporteCompleto controladorReporteCompleto = new ControladorReporteCompleto(vistaPrincipal, jpanelReportes, jPanelReporteCompleto);
        ControladorEliminarIngreso controladorEliminarIngreso = new ControladorEliminarIngreso(vistaPrincipal, jpanelReportes, jPanelReporteIngreso, jDialogEliminarIngreso);
        ControladorEliminarEgreso controladorEliminarEgreso = new ControladorEliminarEgreso(vistaPrincipal, jpanelReportes, jPanelReporteEgreso, jDialogEliminarEgreso);
        ControladorEliminarVenta controladorEliminarVenta = new ControladorEliminarVenta(vistaPrincipal, jpanelReportes, jPanelReporteVentas, jDialogEliminarVenta);
        ControladorAltaUsuario controladorAltaUsuario = new ControladorAltaUsuario(vistaPrincipal, jPanelConfiguracion, jPanelAltaUsuario);
        ControladorConfigurarBaseDatos controladorConfigurarBaseDatos = new ControladorConfigurarBaseDatos(vistaPrincipal, jPanelConfiguracion, jPanelBaseDeDatos);
        ControladorProductosModificar controladorProductosModificar = new ControladorProductosModificar(vistaPrincipal, jpanelProductos, jpanelProductoModificar, jDialogBuscarProductoModificar, jDialogModificarPaquete);
        ControladorCrearDepartamento controladorCrearDepartamento = new ControladorCrearDepartamento(vistaPrincipal, jpanelDepartamento, jpanelProductos);
        Thread respaldo = new RespaldoBaseDeDatos();
        
        respaldo.start();
//iniciar la apliacion
    }
}
