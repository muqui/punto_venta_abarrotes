package modelo;
// Generated 21/12/2018 12:33:33 PM by Hibernate Tools 4.3.1


import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Tproducto generated by hbm2java
 */
@Entity
@Table(name="tproducto"
    
    , uniqueConstraints = @UniqueConstraint(columnNames="codigoBarras") 
)
public class Tproducto  implements java.io.Serializable {

    @Override
    public String toString() {
        return "Tproducto{" + "idProducto=" + idProducto + ", codigoBarras=" + codigoBarras + ", nombre=" + nombre + ", precioVentaUnitario=" + precioVentaUnitario + ", cantidad=" + cantidad + ", descripcion=" + descripcion + ", precioProveedor=" + precioProveedor + ", precioMayoreo=" + precioMayoreo + ", comosevende=" + comosevende + ", inventariar=" + inventariar + ", minimo=" + minimo + ", iva=" + iva + ", ieps=" + ieps + '}';
    }


     private Integer idProducto;
     private Departamento departamento;
     private String codigoBarras;
     private String nombre;
     private BigDecimal precioVentaUnitario;
     private BigDecimal cantidad;
     private String descripcion;
     private BigDecimal precioProveedor;
     private BigDecimal precioMayoreo;
     private String comosevende;
     private Boolean inventariar;
     private Integer minimo;
     private BigDecimal iva;
     private BigDecimal ieps;
     private Boolean habilitado;
     private Set<Tventadetalle> tventadetalles = new HashSet<Tventadetalle>(0);
     private Set<ContenidoPaquete> contenidoPaquetes = new HashSet<ContenidoPaquete>(0);

    public Tproducto() {
    }

	
    public Tproducto(Departamento departamento, String codigoBarras, String nombre, BigDecimal precioVentaUnitario, BigDecimal cantidad, String descripcion, BigDecimal precioProveedor) {
        this.departamento = departamento;
        this.codigoBarras = codigoBarras;
        this.nombre = nombre;
        this.precioVentaUnitario = precioVentaUnitario;
        this.cantidad = cantidad;
        this.descripcion = descripcion;
        this.precioProveedor = precioProveedor;
    }
    public Tproducto(Departamento departamento, String codigoBarras, String nombre, BigDecimal precioVentaUnitario, BigDecimal cantidad, String descripcion, BigDecimal precioProveedor, BigDecimal precioMayoreo, String comosevende, Boolean inventariar, Integer minimo, BigDecimal iva, BigDecimal ieps, Set<Tventadetalle> tventadetalles, Set<ContenidoPaquete> contenidoPaquetes) {
       this.departamento = departamento;
       this.codigoBarras = codigoBarras;
       this.nombre = nombre;
       this.precioVentaUnitario = precioVentaUnitario;
       this.cantidad = cantidad;
       this.descripcion = descripcion;
       this.precioProveedor = precioProveedor;
       this.precioMayoreo = precioMayoreo;
       this.comosevende = comosevende;
       this.inventariar = inventariar;
       this.minimo = minimo;
       this.iva = iva;
       this.ieps = ieps;
       this.tventadetalles = tventadetalles;
       this.contenidoPaquetes = contenidoPaquetes;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)

    
    @Column(name="idProducto", unique=true, nullable=false)
    public Integer getIdProducto() {
        return this.idProducto;
    }
    
    public void setIdProducto(Integer idProducto) {
        this.idProducto = idProducto;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="categoria_id", nullable=false)
    public Departamento getDepartamento() {
        return this.departamento;
    }
    
    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }

    
    @Column(name="codigoBarras", unique=true, nullable=false, length=70)
    public String getCodigoBarras() {
        return this.codigoBarras;
    }
    
    public void setCodigoBarras(String codigoBarras) {
        this.codigoBarras = codigoBarras;
    }

    
    @Column(name="nombre", nullable=false, length=700)
    public String getNombre() {
        return this.nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    
    @Column(name="precioVentaUnitario", nullable=false, precision=18)
    public BigDecimal getPrecioVentaUnitario() {
        return this.precioVentaUnitario;
    }
    
    public void setPrecioVentaUnitario(BigDecimal precioVentaUnitario) {
        this.precioVentaUnitario = precioVentaUnitario;
    }

    
    @Column(name="cantidad", nullable=false, precision=18)
    public BigDecimal getCantidad() {
        return this.cantidad;
    }
    
    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }

    
    @Column(name="descripcion", nullable=false, length=100)
    public String getDescripcion() {
        return this.descripcion;
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    
    @Column(name="precioProveedor", nullable=false, precision=18)
    public BigDecimal getPrecioProveedor() {
        return this.precioProveedor;
    }
    
    public void setPrecioProveedor(BigDecimal precioProveedor) {
        this.precioProveedor = precioProveedor;
    }

    
    @Column(name="precioMayoreo", precision=18)
    public BigDecimal getPrecioMayoreo() {
        return this.precioMayoreo;
    }
    
    public void setPrecioMayoreo(BigDecimal precioMayoreo) {
        this.precioMayoreo = precioMayoreo;
    }

    
    @Column(name="comosevende", length=45)
    public String getComosevende() {
        return this.comosevende;
    }
    
    public void setComosevende(String comosevende) {
        this.comosevende = comosevende;
    }

    
    @Column(name="inventariar")
    public Boolean getInventariar() {
        return this.inventariar;
    }
    
    public void setInventariar(Boolean inventariar) {
        this.inventariar = inventariar;
    }

    /**
     * @return the habilitado
     */
    @Column(name="habilitado")
    public Boolean getHabilitado() {
        return this.habilitado;
    }

    /**
     * @param habilitado the habilitado to set
     */
    public void setHabilitado(Boolean habilitado) {
        this.habilitado = habilitado;
    }
    
    @Column(name="minimo")
    public Integer getMinimo() {
        return this.minimo;
    }
    
    public void setMinimo(Integer minimo) {
        this.minimo = minimo;
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

@OneToMany(fetch=FetchType.LAZY, mappedBy="tproducto")
    public Set<Tventadetalle> getTventadetalles() {
        return this.tventadetalles;
    }
    
    public void setTventadetalles(Set<Tventadetalle> tventadetalles) {
        this.tventadetalles = tventadetalles;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="tproducto")
    public Set<ContenidoPaquete> getContenidoPaquetes() {
        return this.contenidoPaquetes;
    }
    
    public void setContenidoPaquetes(Set<ContenidoPaquete> contenidoPaquetes) {
        this.contenidoPaquetes = contenidoPaquetes;
    }

    




}


