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
@Table(name="usuario_rol", catalog="public")
public class UsuarioRol
  implements Serializable
{
  private static final long serialVersionUID = -1381801811631362656L;
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Column(name="id", unique=true, nullable=false)
  private Integer id;
  @ManyToOne(fetch=FetchType.EAGER, optional=true)
  @JoinColumn(name="id_usuario", nullable=false)
  private Usuario usuario;
  @ManyToOne(fetch=FetchType.EAGER, optional=true)
  @JoinColumn(name="id_roles", nullable=false)
  private Roles rol;
  @Size(max=2)
  private String estado;
  @Column(name="fecha_registro", nullable=false)
  private Date fechaRegistro;
  @Column(name="fecha_modificacion", nullable=true)
  private Date fechaModificacion;
  @Column(name="usuario_registro", nullable=false)
  private String usuarioRegistro;
  
  public UsuarioRol()
  {
    this.id = Integer.valueOf(0);
  }
  
  public String toString()
  {
    return String.valueOf(this.id);
  }
  
  public int hashCode()
  {
    int hash = 0;
    hash += (this.id != null ? this.id.hashCode() : 0);
    return hash;
  }
  
  public boolean equals(Object obj)
  {
    try
    {
      if (((UsuarioRol)obj).id == this.id) {
        return true;
      }
      return false;
    }
    catch (Exception e) {}
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
  
  public String getEstado()
  {
    return this.estado;
  }
  
  public void setEstado(String estado)
  {
    this.estado = estado;
  }
  
  public Date getFechaRegistro()
  {
    return this.fechaRegistro;
  }
  
  public void setFechaRegistro(Date fechaRegistro)
  {
    this.fechaRegistro = fechaRegistro;
  }
  
  public Date getFechaModificacion()
  {
    return this.fechaModificacion;
  }
  
  public void setFechaModificacion(Date fechaModificacion)
  {
    this.fechaModificacion = fechaModificacion;
  }
  
  public String getUsuarioRegistro()
  {
    return this.usuarioRegistro;
  }
  
  public void setUsuarioRegistro(String usuarioRegistro)
  {
    this.usuarioRegistro = usuarioRegistro;
  }
  
  public Usuario getUsuario()
  {
    return this.usuario;
  }
  
  public void setUsuario(Usuario usuario)
  {
    this.usuario = usuario;
  }
  
  public Roles getRol()
  {
    return this.rol;
  }
  
  public void setRol(Roles rol)
  {
    this.rol = rol;
  }
}
