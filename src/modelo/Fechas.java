/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author mq12
 */
public class Fechas {
    public int ayer = -1;
    public Date  ayer(){
Calendar c = Calendar.getInstance();
c.add(Calendar.DATE, ayer);
Date diaAnterior = c.getTime();

//return ""+ formato.format(diaAnterior);+
return diaAnterior;
}
 public String fecha(Date ayer){
 SimpleDateFormat formato = new SimpleDateFormat("EEEEE dd  MMMMMM   yyyy");
 return ""+formato.format(ayer);
          
 }
 public String  rangoFecha(Date desde, Date hasta){
 SimpleDateFormat formato = new SimpleDateFormat("EEEEE dd  MMMMMM   yyyy");
 return ""+ formato.format(desde) + " AL " + formato.format(hasta);
 }
    public String fecha1(Date fecha){
 SimpleDateFormat formato = new SimpleDateFormat("dd/MMM/yyyy HH:mm:ss");
 return ""+formato.format(fecha);
          
 }
}
