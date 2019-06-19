package modelo;
// Generated 21/12/2018 12:33:33 PM by Hibernate Tools 4.3.1


import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Tventadetalle generated by hbm2java
 */
@Entity
@Table(name="tventadetalle"
    
)
public class Tventadetalle  implements java.io.Serializable {


     private Integer idVentaDetalle;
     private Tproducto tproducto;
     private Tventa tventa;
     private String codigoBarrasProducto;
     private String nombreProducto;
     private BigDecimal precioVentaUnitarioProducto;
     private BigDecimal cantidad;
     private BigDecimal totalPrecioVenta;
     private BigDecimal precioProveedor;
     private BigDecimal iva;
     private BigDecimal ieps;
     private Boolean imprimir;
     private String comosevende;

    public Tventadetalle() {
    }

	
    public Tventadetalle(Tproducto tproducto, Tventa tventa, String codigoBarrasProducto, String nombreProducto, BigDecimal precioVentaUnitarioProducto, BigDecimal cantidad, BigDecimal totalPrecioVenta, BigDecimal precioProveedor) {
        this.tproducto = tproducto;
        this.tventa = tventa;
        this.codigoBarrasProducto = codigoBarrasProducto;
        this.nombreProducto = nombreProducto;
        this.precioVentaUnitarioProducto = precioVentaUnitarioProducto;
        this.cantidad = cantidad;
        this.totalPrecioVenta = totalPrecioVenta;
        this.precioProveedor = precioProveedor;
    }
    public Tventadetalle(Tproducto tproducto, Tventa tventa, String codigoBarrasProducto, String nombreProducto, BigDecimal precioVentaUnitarioProducto, BigDecimal cantidad, BigDecimal totalPrecioVenta, BigDecimal precioProveedor, BigDecimal iva, BigDecimal ieps, Boolean imprimir, String comosevende) {
       this.tproducto = tproducto;
       this.tventa = tventa;
       this.codigoBarrasProducto = codigoBarrasProducto;
       this.nombreProducto = nombreProducto;
       this.precioVentaUnitarioProducto = precioVentaUnitarioProducto;
       this.cantidad = cantidad;
       this.totalPrecioVenta = totalPrecioVenta;
       this.precioProveedor = precioProveedor;
       this.iva = iva;
       this.ieps = ieps;
       this.imprimir = imprimir;
       this.comosevende = comosevende;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)

    
    @Column(name="idVentaDetalle", unique=true, nullable=false)
    public Integer getIdVentaDetalle() {
        return this.idVentaDetalle;
    }
    
    public void setIdVentaDetalle(Integer idVentaDetalle) {
        this.idVentaDetalle = idVentaDetalle;
    }

@ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="idProducto", nullable=false)
    public Tproducto getTproducto() {
        return this.tproducto;
    }
    
    public void setTproducto(Tproducto tproducto) {
        this.tproducto = tproducto;
    }

@ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="idVenta", nullable=false)
    public Tventa getTventa() {
        return this.tventa;
    }
    
    public void setTventa(Tventa tventa) {
        this.tventa = tventa;
    }

    
    @Column(name="codigoBarrasProducto", nullable=false, length=70)
    public String getCodigoBarrasProducto() {
        return this.codigoBarrasProducto;
    }
    
    public void setCodigoBarrasProducto(String codigoBarrasProducto) {
        this.codigoBarrasProducto = codigoBarrasProducto;
    }

    
    @Column(name="nombreProducto", nullable=false, length=700)
    public String getNombreProducto() {
        return this.nombreProducto;
    }
    
    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    
    @Column(name="precioVentaUnitarioProducto", nullable=false, precision=18)
    public BigDecimal getPrecioVentaUnitarioProducto() {
        return this.precioVentaUnitarioProducto;
    }
    
    public void setPrecioVentaUnitarioProducto(BigDecimal precioVentaUnitarioProducto) {
        this.precioVentaUnitarioProducto = precioVentaUnitarioProducto;
    }

    
    @Column(name="cantidad", nullable=false, precision=18)
    public BigDecimal getCantidad() {
        return this.cantidad;
    }
    
    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }

    
    @Column(name="totalPrecioVenta", nullable=false, precision=18)
    public BigDecimal getTotalPrecioVenta() {
        return this.totalPrecioVenta;
    }
    
    public void setTotalPrecioVenta(BigDecimal totalPrecioVenta) {
        this.totalPrecioVenta = totalPrecioVenta;
    }

    
    @Column(name="precioProveedor", nullable=false, precision=18)
    public BigDecimal getPrecioProveedor() {
        return this.precioProveedor;
    }
    
    public void setPrecioProveedor(BigDecimal precioProveedor) {
        this.precioProveedor = precioProveedor;
    }

    
    @Column(name="IVA", precision=18)
    public BigDecimal getIva() {
        return this.iva;
    }
    
    public void setIva(BigDecimal iva) {
        this.iva = iva;
    }

    
    @Column(name="IEPS", precision=18)
    public BigDecimal getIeps() {
        return this.ieps;
    }
    
    public void setIeps(BigDecimal ieps) {
        this.ieps = ieps;
    }

    
    @Column(name="imprimir")
    public Boolean getImprimir() {
        return this.imprimir;
    }
    
    public void setImprimir(Boolean imprimir) {
        this.imprimir = imprimir;
    }

    
    @Column(name="comosevende", length=45)
    public String getComosevende() {
        return this.comosevende;
    }
    
    public void setComosevende(String comosevende) {
        this.comosevende = comosevende;
    }




}


