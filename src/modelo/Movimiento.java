package modelo;
// Generated 21/12/2018 12:33:33 PM by Hibernate Tools 4.3.1


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Movimiento generated by hbm2java
 */
@Entity
@Table(name="movimiento"
    
)
public class Movimiento  implements java.io.Serializable {


     private Integer id;
     private String nombre;
     private String tipo;

    public Movimiento() {
    }

	
    public Movimiento(String nombre) {
        this.nombre = nombre;
    }
    public Movimiento(String nombre, String tipo) {
       this.nombre = nombre;
       this.tipo = tipo;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)

    
    @Column(name="id", unique=true, nullable=false)
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }

    
    @Column(name="nombre", nullable=false, length=100)
    public String getNombre() {
        return this.nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    
    @Column(name="tipo", length=45)
    public String getTipo() {
        return this.tipo;
    }
    
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }




}

