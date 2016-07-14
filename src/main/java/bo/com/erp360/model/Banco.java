package bo.com.erp360.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name="banco", schema="public", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"nombre", "id_empresa"})})
public class Banco
  implements Serializable
{
  private static final long serialVersionUID = 3839560137114900345L;
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private Integer id;
  private String nombre;
  private String telefono;
  private String direccion;
  private String correo;
  private String sigla;
  @Column(name="nombre_ejecutivo", nullable=true)
  private String nombreEjecutivo;
  @Column(name="tipo", nullable=true)
  private String tipo;
  @ManyToOne(fetch=FetchType.EAGER, optional=false)
  @JoinColumn(name="id_empresa", nullable=false)
  private Empresa empresa;
  @Size(max=2)
  private String estado;
  @Column(name="fecha_registro", nullable=false)
  private Date fechaRegistro;
  @Column(name="fecha_modificacion", nullable=true)
  private Date fechaModificacion;
  @Column(name="usuario_registro", nullable=false)
  private String usuarioRegistro;
  
  public Banco()
  {
    this.id = Integer.valueOf(0);
    this.nombre = "";
    this.telefono = "";
    this.tipo = "PUBLICO";
    this.direccion = "";
    this.correo = "";
    this.estado = "AC";
    setFechaRegistro(new Date());
  }
  
  public String toString()
  {
    return this.nombre;
  }
  
  public int hashCode()
  {
    int hash = 0;
    hash += (this.id != null ? this.id.hashCode() : 0);
    return hash;
  }
  
  public boolean equals(Object obj)
  {
    if (obj == null) {
      return false;
    }
    if (!(obj instanceof Banco)) {
      return false;
    }
    if (((Banco)obj).id == this.id) {
      return true;
    }
    return false;
  }
  
  public Integer getId()
  {
    return this.id;
  }
  
  public void setId(Integer id)
  {
    this.id = id;
  }
  
  public String getNombre()
  {
    return this.nombre;
  }
  
  public void setNombre(String nombre)
  {
    this.nombre = nombre;
  }
  
  public String getTelefono()
  {
    return this.telefono;
  }
  
  public void setTelefono(String telefono)
  {
    this.telefono = telefono;
  }
  
  public String getEstado()
  {
    return this.estado;
  }
  
  public void setEstado(String estado)
  {
    this.estado = estado;
  }
  
  public Empresa getEmpresa()
  {
    return this.empresa;
  }
  
  public void setEmpresa(Empresa empresa)
  {
    this.empresa = empresa;
  }
  
  public String getDireccion()
  {
    return this.direccion;
  }
  
  public void setDireccion(String direccion)
  {
    this.direccion = direccion;
  }
  
  public String getCorreo()
  {
    return this.correo;
  }
  
  public void setCorreo(String correo)
  {
    this.correo = correo;
  }
  
  public Date getFechaRegistro()
  {
    return this.fechaRegistro;
  }
  
  public void setFechaRegistro(Date fechaRegistro)
  {
    this.fechaRegistro = fechaRegistro;
  }
  
  public String getUsuarioRegistro()
  {
    return this.usuarioRegistro;
  }
  
  public void setUsuarioRegistro(String usuarioRegistro)
  {
    this.usuarioRegistro = usuarioRegistro;
  }
  
  public Date getFechaModificacion()
  {
    return this.fechaModificacion;
  }
  
  public void setFechaModificacion(Date fechaModificacion)
  {
    this.fechaModificacion = fechaModificacion;
  }
  
  public String getTipo()
  {
    return this.tipo;
  }
  
  public void setTipo(String tipo)
  {
    this.tipo = tipo;
  }
  
  public String getNombreEjecutivo()
  {
    return this.nombreEjecutivo;
  }
  
  public void setNombreEjecutivo(String nombreEjecutivo)
  {
    this.nombreEjecutivo = nombreEjecutivo;
  }
  
  public String getSigla()
  {
    return this.sigla;
  }
  
  public void setSigla(String sigla)
  {
    this.sigla = sigla;
  }
}
