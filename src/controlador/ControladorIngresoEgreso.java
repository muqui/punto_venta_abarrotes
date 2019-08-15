/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import dao.IngresoEgresoDao;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import modelo.Egreso;
import modelo.Fechas;
import modelo.Ingreso;
import modelo.Movimiento;
import vista.JDialogEgreso;
import vista.JDialogIngreso;
import vista.JpanelVentas;
import vista.Principal;

/**
 *
 * @author mq12
 */
public class ControladorIngresoEgreso implements ActionListener, KeyListener {

    JDialogIngreso jDialogIngreso;
    JDialogEgreso jDialogEgreso;
    Principal vistaPrincipal;
    JpanelVentas jpanelVentas;
    Ingreso ingreso = new Ingreso();
    Egreso egreso = new Egreso();
    IngresoEgresoDao ingresoEgresoDao = new IngresoEgresoDao();

    public ControladorIngresoEgreso(Principal vistaPrincipal, JDialogIngreso jDialogIngreso, JDialogEgreso jDialogEgreso, JpanelVentas jpanelVentas) {
        this.jDialogEgreso = jDialogEgreso;
        this.jDialogIngreso = jDialogIngreso;
        this.vistaPrincipal = vistaPrincipal;
        this.jpanelVentas = jpanelVentas;

        this.jDialogIngreso.jCheckBoxCrearIngreso.addActionListener(this);
        this.jDialogIngreso.jPanelCrearIngreso.setVisible(false);
        jpanelVentas.jTextFieldCodigoBarras.addKeyListener(this);
        jDialogIngreso.setSize(390, 300);
        this.jDialogIngreso.jButtonCrearIngreso.addActionListener(this);
        this.jDialogEgreso.jCheckBoxCrearEgreso.addActionListener(this);
        this.jDialogEgreso.jPanelCrearEgreso.setVisible(false);
        this.jDialogEgreso.jButtonCrear.addActionListener(this);
        jDialogEgreso.setSize(390, 300);
        llenarComboIngreso();
        llenarComboEgreso();
        jDialogIngreso.jDateChooserFecha.setDate(new Date());
        jDialogIngreso.jButtonAlta.addActionListener(this);
        jDialogIngreso.jButtonAyer.addActionListener(this);

        jDialogEgreso.jDateChooserFecha.setDate(new Date());
        jDialogEgreso.jButtonAlta.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == jDialogIngreso.jButtonAyer) {
            jDialogIngreso.jDateChooserFecha.setDate(new Fechas().ayer());
        }
        if (e.getSource() == jDialogEgreso.jButtonAyer) {
            jDialogEgreso.jDateChooserFecha.setDate(new Fechas().ayer());
        }
        if (e.getSource() == jDialogEgreso.jButtonAlta) {
            if (validarEgreso()) {
                if (guardarEgreso()) {
                    JOptionPane.showMessageDialog(null, "Movimiento Registrado.", "Exito.", JOptionPane.INFORMATION_MESSAGE);
                    jDialogEgreso.jTextFieldCantidad.setText("");
                    jDialogEgreso.jTextAreaDescripcion.setText("");
                } else {
                    JOptionPane.showMessageDialog(null, "Error al crear el registro x ", "error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        if (e.getSource() == jDialogIngreso.jButtonAlta) {
            if (validarIngreso()) {
                if (guardarIngreso()) {

                    JOptionPane.showMessageDialog(null, "Movimiento Registrado.", "Exito.", JOptionPane.INFORMATION_MESSAGE);
                    jDialogIngreso.jTextFieldCantidad.setText("");
                    jDialogIngreso.jTextAreaDescripcion.setText("");
                } else {
                    JOptionPane.showMessageDialog(null, "Error al crear el registro ", "error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }

        if (e.getSource() == jDialogEgreso.jButtonCrear) {
            if (!"".equals(jDialogEgreso.jTextFieldNombre.getText().trim())) {
                Movimiento m = ingresoEgresoDao.getMovimiento(jDialogEgreso.jTextFieldNombre.getText().trim(), "egreso");
                if (m != null) {
                    JOptionPane.showMessageDialog(null, "Egreso existente ", "error", JOptionPane.ERROR_MESSAGE);
                } else {
                    crearMovimiento(jDialogEgreso.jTextFieldNombre.getText(), "egreso");
                    JOptionPane.showMessageDialog(null, "Tipo egreso creado.", "Exito.", JOptionPane.INFORMATION_MESSAGE);
                    jDialogEgreso.jTextFieldNombre.setText("");
                    llenarComboEgreso();
                }

            } else {
                JOptionPane.showMessageDialog(null, "Capture Nombre ", "error", JOptionPane.ERROR_MESSAGE);
            }
        }
        if (e.getSource() == jDialogIngreso.jButtonCrearIngreso) {

            if (!"".equals(jDialogIngreso.jTextFieldNombre.getText().trim())) {
                Movimiento m = ingresoEgresoDao.getMovimiento(jDialogIngreso.jTextFieldNombre.getText().trim(), "ingreso");
                if (m != null) {

                    JOptionPane.showMessageDialog(null, "Ingreso existente ", "error", JOptionPane.ERROR_MESSAGE);
                } else {
                    crearMovimiento(jDialogIngreso.jTextFieldNombre.getText(), "ingreso");
                    JOptionPane.showMessageDialog(null, "Tipo ingreso creado.", "Exito.", JOptionPane.INFORMATION_MESSAGE);
                    jDialogIngreso.jTextFieldNombre.setText("");
                    llenarComboIngreso();
                }

            } else {
                JOptionPane.showMessageDialog(null, "Capture Nombre ", "error", JOptionPane.ERROR_MESSAGE);
            }

        }
        if (e.getSource() == jDialogIngreso.jCheckBoxCrearIngreso) {
            if (jDialogIngreso.jCheckBoxCrearIngreso.isSelected()) {
                jDialogIngreso.setSize(390, 200);
                jDialogIngreso.jPanelCrearIngreso.setVisible(true);
                jDialogIngreso.jPanelAltaIngreso.setVisible(false);
            } else {
                jDialogIngreso.setSize(390, 300);
                jDialogIngreso.jPanelCrearIngreso.setVisible(false);
                jDialogIngreso.jPanelAltaIngreso.setVisible(true);
            }
        }
        //
        if (e.getSource() == jDialogEgreso.jCheckBoxCrearEgreso) {
            if (jDialogEgreso.jCheckBoxCrearEgreso.isSelected()) {
                jDialogEgreso.setSize(390, 200);
                jDialogEgreso.jPanelCrearEgreso.setVisible(true);
                jDialogEgreso.jPanelAltaEgreso.setVisible(false);
            } else {
                jDialogEgreso.setSize(390, 300);
                jDialogEgreso.jPanelCrearEgreso.setVisible(false);
                jDialogEgreso.jPanelAltaEgreso.setVisible(true);
            }
        }


    }

    private void mostrarDialogIngreso() {
        jDialogIngreso.setLocationRelativeTo(null);
        jDialogIngreso.setVisible(true);
    }

    private void mostrarDialogEgreso() {
        jDialogEgreso.setLocationRelativeTo(null);
        jDialogEgreso.setVisible(true);
    }

    private void crearMovimiento(String Nombre, String tipo) {
        try {
            Movimiento movimiento = new Movimiento(Nombre, tipo);
            ingresoEgresoDao.addMovimiento(movimiento);
        } catch (Exception ex) {
            Logger.getLogger(ControladorIngresoEgreso.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void llenarComboIngreso() {
        try {

            jDialogIngreso.jComboBoxIngreso.setModel(new DefaultComboBoxModel());
            List<Movimiento> movimientos;
            movimientos = ingresoEgresoDao.getMovimientos("ingreso");
            System.out.println("movimientos " + movimientos);
            for (int i = 0; i < movimientos.size(); i++) {

                jDialogIngreso.jComboBoxIngreso.addItem("" + movimientos.get(i).getNombre());

            }
        } catch (Exception ex) {
            Logger.getLogger(ControladorProductos.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private boolean validarIngreso() {
        boolean bandera = true;
        ingreso.setFecha(jDialogIngreso.jDateChooserFecha.getDate());
        if ("".equals(jDialogIngreso.jTextFieldCantidad.getText().trim()) || "".endsWith(jDialogIngreso.jTextAreaDescripcion.getText().trim()) || ingreso.getFecha() == null) {
            System.out.println("bandera falso");
            bandera = false;
            JOptionPane.showMessageDialog(null, "Capture todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return bandera;
    }

    private boolean guardarIngreso() {
        try {
            ingreso.setUsuario(vistaPrincipal.usuario);
            ingreso.setNombre("" + jDialogIngreso.jComboBoxIngreso.getSelectedItem());
            ingreso.setCantidad(new BigDecimal(jDialogIngreso.jTextFieldCantidad.getText().trim()));
            ingreso.setFecha(jDialogIngreso.jDateChooserFecha.getDate());
            ingreso.setDescrpcion(jDialogIngreso.jTextAreaDescripcion.getText().trim());
            ingreso.setFechaMovimiento(new Date());

        } catch (Exception ex) {
            Logger.getLogger(ControladorIngresoEgreso.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ingresoEgresoDao.addIngreso(ingreso);
    }

    private void llenarComboEgreso() {
        try {

            jDialogEgreso.jComboBoxEgreso.setModel(new DefaultComboBoxModel());
            List<Movimiento> movimientos;
            movimientos = ingresoEgresoDao.getMovimientos("egreso");
            System.out.println("movimientos " + movimientos);
            for (int i = 0; i < movimientos.size(); i++) {

                jDialogEgreso.jComboBoxEgreso.addItem("" + movimientos.get(i).getNombre());

            }
        } catch (Exception ex) {
            Logger.getLogger(ControladorProductos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private boolean validarEgreso() {
        boolean bandera = true;
        egreso.setFecha(jDialogEgreso.jDateChooserFecha.getDate());
        if ("".equals(jDialogEgreso.jTextFieldCantidad.getText().trim()) || "".endsWith(jDialogEgreso.jTextAreaDescripcion.getText().trim()) || egreso.getFecha() == null) {
            System.out.println("bandera falso");
            bandera = false;
            JOptionPane.showMessageDialog(null, "Capture todos los campos .", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return bandera;
    }

    private boolean guardarEgreso() {
        try {
            egreso.setUsuario(vistaPrincipal.usuario);
            egreso.setNombre("" + jDialogEgreso.jComboBoxEgreso.getSelectedItem());
            egreso.setCantidad(new BigDecimal(jDialogEgreso.jTextFieldCantidad.getText().trim()));
            egreso.setFecha(jDialogEgreso.jDateChooserFecha.getDate());
            egreso.setDescripcion(jDialogEgreso.jTextAreaDescripcion.getText().trim());
            egreso.setFechaMovimiento(new Date());

        } catch (Exception ex) {
            Logger.getLogger(ControladorIngresoEgreso.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ingresoEgresoDao.addEgreso(egreso);
    }

    @Override
    public void keyTyped(KeyEvent ke) {

    }

    @Override
    public void keyPressed(KeyEvent ke) {

    }

    @Override
    public void keyReleased(KeyEvent ke) {

    }

}
