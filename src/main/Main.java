/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import controlador.ControladorVender;
import controlador.ControlerLogin;
import vista.JpanelVentas;
import vista.Login;
import vista.Principal;

/**
 *
 * @author mq12
 */
public class Main {

    public static void main(String[] args) {
        //MODELO
        //VISTA
       
        Principal vistaPrincipal = new Principal();
        Login login = new Login(vistaPrincipal, true);
         JpanelVentas jpanelVentas = new  JpanelVentas();
         
        //CONTROLADOR
        ControlerLogin controllerLogin = new ControlerLogin(vistaPrincipal, login, jpanelVentas);
        ControladorVender controladorVender = new ControladorVender(vistaPrincipal, jpanelVentas);
        //iniciar la apliacion
        controllerLogin.iniciar();
        
    }
}
