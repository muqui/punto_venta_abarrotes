/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.math.BigDecimal;

/**
 *
 * @author mq12
 */
public class Reporte {

    private String ingreso;
    private BigDecimal cantidadIngreso;
    private String egreso;
    private BigDecimal cantidadEgreso;
    private BigDecimal total;

    public Reporte() {
     ingreso = "";
     egreso = "";
     cantidadEgreso = new BigDecimal("0.00");
     cantidadIngreso= new BigDecimal("0.00");
     total = new BigDecimal("0.00");
    }

    /**
     * @return the ingreso
     */
    public String getIngreso() {
        return ingreso;
    }

    /**
     * @param ingreso the ingreso to set
     */
    public void setIngreso(String ingreso) {
        this.ingreso = ingreso;
    }

    /**
     * @return the cantidadIngreso
     */
    public BigDecimal getCantidadIngreso() {
        return cantidadIngreso;
    }

    /**
     * @param cantidadIngreso the cantidadIngreso to set
     */
    public void setCantidadIngreso(BigDecimal cantidadIngreso) {
        this.cantidadIngreso = cantidadIngreso;
    }

    /**
     * @return the egreso
     */
    public String getEgreso() {
        return egreso;
    }

    /**
     * @param egreso the egreso to set
     */
    public void setEgreso(String egreso) {
        this.egreso = egreso;
    }

    /**
     * @return the cantidadEgreso
     */
    public BigDecimal getCantidadEgreso() {
        return cantidadEgreso;
    }

    /**
     * @param cantidadEgreso the cantidadEgreso to set
     */
    public void setCantidadEgreso(BigDecimal cantidadEgreso) {
        this.cantidadEgreso = cantidadEgreso;
    }

    /**
     * @return the total
     */
    public BigDecimal getTotal() {
        return total;
    }

    /**
     * @param total the total to set
     */
    public void setTotal(BigDecimal total) {
        this.total = total;
    }

}
