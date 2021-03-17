/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import vista.Principal;
import vista.jpanelXBOX;

/**
 *
 * @author mq12
 */
public class ControladorXbox implements ActionListener{
     Principal vistaPrincipal;
    jpanelXBOX jpanelXbox;
public    ControladorXbox(Principal vistaPrincipal, jpanelXBOX jpanelXbox){
     
     this.vistaPrincipal = vistaPrincipal;
     this.jpanelXbox = jpanelXbox;
       this.vistaPrincipal.jButtonXbox.addActionListener(this);
       this.jpanelXbox.jButtonXbox1.addActionListener(this);
} 

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vistaPrincipal.jButtonXbox) {
             vistaPrincipal.jPanelPanelPrincipal.removeAll();
            vistaPrincipal.jPanelPanelPrincipal.setLayout(new java.awt.BorderLayout());
            vistaPrincipal.jPanelPanelPrincipal.add(jpanelXbox);
            vistaPrincipal.jPanelPanelPrincipal.validate();
            vistaPrincipal.jPanelPanelPrincipal.repaint();
         

        }
         if (e.getSource() == jpanelXbox.jButtonXbox1 ) {
             
            

        }
    }

}
