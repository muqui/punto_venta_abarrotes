package modelo;
// Generated 21/12/2018 12:33:33 PM by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Cliente generated by hbm2java
 */
@Entity
@Table(name="cliente"
    
)
public class Cliente  implements java.io.Serializable {


     private Integer idcliente;
     private String nombreCompleto;
     private String direccion;
     private String telefono;
     private Integer limite;
     private Set<PagoCliente> pagoClientes = new HashSet<PagoCliente>(0);

    public Cliente() {
    }

    public Cliente(String nombreCompleto, String direccion, String telefono, Integer limite, Set<PagoCliente> pagoClientes) {
       this.nombreCompleto = nombreCompleto;
       this.direccion = direccion;
       this.telefono = telefono;
       this.limite = limite;
       this.pagoClientes = pagoClientes;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)

    
    @Column(name="idcliente", unique=true, nullable=false)
    public Integer getIdcliente() {
        return this.idcliente;
    }
    
    public void setIdcliente(Integer idcliente) {
        this.idcliente = idcliente;
    }

    
    @Column(name="nombre_completo", length=45)
    public String getNombreCompleto() {
        return this.nombreCompleto;
    }
    
    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    
    @Column(name="direccion", length=45)
    public String getDireccion() {
        return this.direccion;
    }
    
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    
    @Column(name="telefono", length=45)
    public String getTelefono() {
        return this.telefono;
    }
    
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    
    @Column(name="limite")
    public Integer getLimite() {
        return this.limite;
    }
    
    public void setLimite(Integer limite) {
        this.limite = limite;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="cliente")
    public Set<PagoCliente> getPagoClientes() {
        return this.pagoClientes;
    }
    
    public void setPagoClientes(Set<PagoCliente> pagoClientes) {
        this.pagoClientes = pagoClientes;
    }




}


