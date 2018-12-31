package modelo;
// Generated 21/12/2018 12:33:33 PM by Hibernate Tools 4.3.1


import java.math.BigDecimal;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Tventa generated by hbm2java
 */
@Entity
@Table(name="tventa"
   
)
public class Tventa  implements java.io.Serializable {


     private Integer idVenta;
     private Usuario usuario;
     private BigDecimal precioVentaTotal;
     private Date fechaRegistro;
     private Set<Tventadetalle> tventadetalles = new HashSet<Tventadetalle>(0);
     private Set<Formadepago> formadepagos = new HashSet<Formadepago>(0);

    public Tventa() {
    }

	
    public Tventa(Usuario usuario, BigDecimal precioVentaTotal, Date fechaRegistro) {
        this.usuario = usuario;
        this.precioVentaTotal = precioVentaTotal;
        this.fechaRegistro = fechaRegistro;
    }
    public Tventa(Usuario usuario, BigDecimal precioVentaTotal, Date fechaRegistro, Set<Tventadetalle> tventadetalles, Set<Formadepago> formadepagos) {
       this.usuario = usuario;
       this.precioVentaTotal = precioVentaTotal;
       this.fechaRegistro = fechaRegistro;
       this.tventadetalles = tventadetalles;
       this.formadepagos = formadepagos;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)

    
    @Column(name="idVenta", unique=true, nullable=false)
    public Integer getIdVenta() {
        return this.idVenta;
    }
    
    public void setIdVenta(Integer idVenta) {
        this.idVenta = idVenta;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="usuario_id", nullable=false)
    public Usuario getUsuario() {
        return this.usuario;
    }
    
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    
    @Column(name="precioVentaTotal", nullable=false, precision=18)
    public BigDecimal getPrecioVentaTotal() {
        return this.precioVentaTotal;
    }
    
    public void setPrecioVentaTotal(BigDecimal precioVentaTotal) {
        this.precioVentaTotal = precioVentaTotal;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="fechaRegistro", nullable=false, length=19)
    public Date getFechaRegistro() {
        return this.fechaRegistro;
    }
    
    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="tventa")
    public Set<Tventadetalle> getTventadetalles() {
        return this.tventadetalles;
    }
    
    public void setTventadetalles(Set<Tventadetalle> tventadetalles) {
        this.tventadetalles = tventadetalles;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="tventa")
    public Set<Formadepago> getFormadepagos() {
        return this.formadepagos;
    }
    
    public void setFormadepagos(Set<Formadepago> formadepagos) {
        this.formadepagos = formadepagos;
    }




}


