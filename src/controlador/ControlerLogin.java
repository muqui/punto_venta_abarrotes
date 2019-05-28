/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import vista.Login;
import vista.Principal;

/**
 *
 * @author mq12
 */
public class ControlerLogin {
     Principal vistaPrincipal;
        Login login;
    public ControlerLogin(Principal vistaPrincipal, Login login) {
       this.vistaPrincipal = vistaPrincipal;
       this.login = login;
    }
    
}
