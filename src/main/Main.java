/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

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
        JPanelTicket jPanelTicket = new JPanelTicket();
        JDialogVentaAgranel jDialogVentaAgranel = new JDialogVentaAgranel(vistaPrincipal, true);
        JDialogMasDe1Producto jDialogMasDe1Producto = new JDialogMasDe1Producto(vistaPrincipal, true);
        JDialogVentaFinal jDialogVentaFinal = new JDialogVentaFinal(vistaPrincipal, true);
        JPanelMisVentas jpanelMisVentas = new JPanelMisVentas();
        JDialogBuscarProducto jDialogBuscarProducto = new JDialogBuscarProducto(vistaPrincipal, true);
        JDialogVerificadorPrecios jDialogVerificadorPrecios  = new JDialogVerificadorPrecios(vistaPrincipal, true);
        JpanelProductos jpanelProductos = new JpanelProductos();
        JpanelProductoNuevo jpanelProductoNuevo = new JpanelProductoNuevo();
        JpanelContenidoPaquete jpanelContenidoPaquete = new JpanelContenidoPaquete();
        //CONTROLADOR
        ControlerLogin controllerLogin = new ControlerLogin(vistaPrincipal, login, jpanelVentas);
        controllerLogin.iniciar();
        ControladorMisVentas controladorMisVentas = new ControladorMisVentas(vistaPrincipal, jpanelMisVentas);
        ControladorVender controladorVender = new ControladorVender(vistaPrincipal, jpanelVentas, jPanelTicket, jDialogVentaAgranel, jDialogMasDe1Producto, jDialogVentaFinal, jDialogBuscarProducto, jDialogVerificadorPrecios);
        ControladorProductos controladorProductos = new ControladorProductos(vistaPrincipal, jpanelProductos, jpanelProductoNuevo, jpanelContenidoPaquete);
//iniciar la apliacion
    }
}
