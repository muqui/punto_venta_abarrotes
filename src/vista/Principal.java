/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import modelo.Usuario;

/**
 *
 * @author mq12
 */
public class Principal extends javax.swing.JFrame {
 public  Usuario usuario = new Usuario();
    /**
     * Creates new form Principal
     */
    public Principal() {

        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
             
            initComponents();
           
               Image icon = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/logo.png"));
        setIconImage(icon);

            this.setExtendedState(MAXIMIZED_BOTH);
           
           
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jToolBar1 = new javax.swing.JToolBar();
        jButtonVentas = new javax.swing.JButton();
        jButtonProductos = new javax.swing.JButton();
        jButtonInventario = new javax.swing.JButton();
        jButtonClientes = new javax.swing.JButton();
        jButtonConfiguracion = new javax.swing.JButton();
        jButtonReporte = new javax.swing.JButton();
        jButtonMisVentas = new javax.swing.JButton();
        jButtonXbox = new javax.swing.JButton();
        jButtonCerrarSession = new javax.swing.JButton();
        jPanelPanelPrincipal = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jButtonSalir = new javax.swing.JButton();
        jLabelTitulo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(0, 0, 0));

        jToolBar1.setBackground(new java.awt.Color(255, 255, 255));
        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);

        jButtonVentas.setBackground(new java.awt.Color(255, 255, 255));
        jButtonVentas.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButtonVentas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/vender24x24.jpg"))); // NOI18N
        jButtonVentas.setText("Ventas");
        jButtonVentas.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButtonVentas.setFocusable(false);
        jButtonVentas.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jButtonVentas.setMaximumSize(new java.awt.Dimension(180, 49));
        jButtonVentas.setMinimumSize(new java.awt.Dimension(60, 49));
        jButtonVentas.setPreferredSize(new java.awt.Dimension(50, 49));
        jButtonVentas.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(jButtonVentas);

        jButtonProductos.setBackground(new java.awt.Color(255, 255, 255));
        jButtonProductos.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButtonProductos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/producto24x24.png"))); // NOI18N
        jButtonProductos.setText("Productos");
        jButtonProductos.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButtonProductos.setFocusable(false);
        jButtonProductos.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jButtonProductos.setMaximumSize(new java.awt.Dimension(180, 49));
        jButtonProductos.setMinimumSize(new java.awt.Dimension(125, 49));
        jButtonProductos.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(jButtonProductos);

        jButtonInventario.setBackground(new java.awt.Color(255, 255, 255));
        jButtonInventario.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButtonInventario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/inventario24x24.png"))); // NOI18N
        jButtonInventario.setText("Inventario");
        jButtonInventario.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButtonInventario.setFocusable(false);
        jButtonInventario.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jButtonInventario.setMaximumSize(new java.awt.Dimension(180, 49));
        jButtonInventario.setMinimumSize(new java.awt.Dimension(131, 49));
        jButtonInventario.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(jButtonInventario);

        jButtonClientes.setBackground(new java.awt.Color(255, 255, 255));
        jButtonClientes.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButtonClientes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/clientes24x24.png"))); // NOI18N
        jButtonClientes.setText("clientes");
        jButtonClientes.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButtonClientes.setFocusable(false);
        jButtonClientes.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jButtonClientes.setMaximumSize(new java.awt.Dimension(180, 49));
        jButtonClientes.setMinimumSize(new java.awt.Dimension(105, 49));
        jButtonClientes.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(jButtonClientes);

        jButtonConfiguracion.setBackground(new java.awt.Color(255, 255, 255));
        jButtonConfiguracion.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButtonConfiguracion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/configuracion24x24.png"))); // NOI18N
        jButtonConfiguracion.setText("Configuracion");
        jButtonConfiguracion.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButtonConfiguracion.setFocusable(false);
        jButtonConfiguracion.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jButtonConfiguracion.setMaximumSize(new java.awt.Dimension(180, 49));
        jButtonConfiguracion.setMinimumSize(new java.awt.Dimension(161, 49));
        jButtonConfiguracion.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(jButtonConfiguracion);

        jButtonReporte.setBackground(new java.awt.Color(255, 255, 255));
        jButtonReporte.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButtonReporte.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/reporte24x24.png"))); // NOI18N
        jButtonReporte.setText("Reporte");
        jButtonReporte.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButtonReporte.setFocusable(false);
        jButtonReporte.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jButtonReporte.setMaximumSize(new java.awt.Dimension(180, 49));
        jButtonReporte.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(jButtonReporte);

        jButtonMisVentas.setBackground(new java.awt.Color(255, 255, 255));
        jButtonMisVentas.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButtonMisVentas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/mi_reporte24x24.png"))); // NOI18N
        jButtonMisVentas.setText("Mis ventas");
        jButtonMisVentas.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButtonMisVentas.setFocusable(false);
        jButtonMisVentas.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jButtonMisVentas.setMaximumSize(new java.awt.Dimension(180, 49));
        jButtonMisVentas.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(jButtonMisVentas);

        jButtonXbox.setBackground(new java.awt.Color(255, 255, 255));
        jButtonXbox.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButtonXbox.setText("XBOX");
        jButtonXbox.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButtonXbox.setFocusable(false);
        jButtonXbox.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButtonXbox.setMaximumSize(new java.awt.Dimension(180, 49));
        jButtonXbox.setMinimumSize(new java.awt.Dimension(129, 31));
        jButtonXbox.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(jButtonXbox);

        jButtonCerrarSession.setBackground(new java.awt.Color(255, 255, 255));
        jButtonCerrarSession.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButtonCerrarSession.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/cerrarSession24x24.jpg"))); // NOI18N
        jButtonCerrarSession.setText("Cerrar");
        jButtonCerrarSession.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButtonCerrarSession.setFocusable(false);
        jButtonCerrarSession.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jButtonCerrarSession.setMaximumSize(new java.awt.Dimension(180, 49));
        jButtonCerrarSession.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(jButtonCerrarSession);

        jPanelPanelPrincipal.setBackground(new java.awt.Color(255, 255, 255));
        jPanelPanelPrincipal.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        javax.swing.GroupLayout jPanelPanelPrincipalLayout = new javax.swing.GroupLayout(jPanelPanelPrincipal);
        jPanelPanelPrincipal.setLayout(jPanelPanelPrincipalLayout);
        jPanelPanelPrincipalLayout.setHorizontalGroup(
            jPanelPanelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanelPanelPrincipalLayout.setVerticalGroup(
            jPanelPanelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 360, Short.MAX_VALUE)
        );

        jButtonSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/salir24x24.jpg"))); // NOI18N

        jLabelTitulo.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabelTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 1091, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 163, Short.MAX_VALUE))
                            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanelPanelPrincipal, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabelTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButtonSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButtonSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelPanelPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Principal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton jButtonCerrarSession;
    public javax.swing.JButton jButtonClientes;
    public javax.swing.JButton jButtonConfiguracion;
    public javax.swing.JButton jButtonInventario;
    public javax.swing.JButton jButtonMisVentas;
    public javax.swing.JButton jButtonProductos;
    public javax.swing.JButton jButtonReporte;
    public javax.swing.JButton jButtonSalir;
    public javax.swing.JButton jButtonVentas;
    public javax.swing.JButton jButtonXbox;
    private javax.swing.JLabel jLabel2;
    public javax.swing.JLabel jLabelTitulo;
    public javax.swing.JPanel jPanelPanelPrincipal;
    public javax.swing.JToolBar jToolBar1;
    // End of variables declaration//GEN-END:variables
}
