/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package validar;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JTextField;

/**
 *
 * @author mq12
 */
public class ValidarEntradas {


    public void moneda(JTextField textfiel) {
        textfiel.addKeyListener(new KeyAdapter() {

            @Override
            public void keyTyped(KeyEvent evt) {
                char caracter = evt.getKeyChar();
                String texto = textfiel.getText();
                if (texto.indexOf('.') > -1) {
                    System.out.println("encontrado");
                    if (((caracter < '0') || (caracter > '9')) && (caracter != evt.VK_BACK_SPACE)) {
                    evt.consume();
                }
                }else{
                if (((caracter < '0') || (caracter > '9')) && (caracter != evt.VK_BACK_SPACE) && (caracter != '.')) {
                    evt.consume();
                }
                
                
                }

                
                System.out.println("text " + textfiel.getText());

            }
        });
    }
    public void entero(JTextField textfiel) {
        textfiel.addKeyListener(new KeyAdapter() {

            @Override
            public void keyTyped(KeyEvent evt) {
                char caracter = evt.getKeyChar();
                    if (((caracter < '0') || (caracter > '9')) && (caracter != evt.VK_BACK_SPACE)) {
                    evt.consume();                
                }
               
                
                

                
                System.out.println("text " + textfiel.getText());

            }
        });
    }
}
