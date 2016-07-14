package bo.com.erp360.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name="usuario", catalog="public", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"login"})})
public class Usuario
  implements Serializable
{
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private Integer id;
  private String nombre;
  private String email;
  private String login;
  private String password;
  @Size(max=2)
  private String state;
  @Column(name="fecha_registro", nullable=false)
  private Date fechaRegistro;
  @Column(name="fecha_modificacion", nullable=true)
  private Date fechaModificacion;
  @Column(name="foto_perfil", nullable=true)
  private byte[] fotoPerfil;
  @Column(name="peso_foto", nullable=true)
  private int pesoFoto;
  @Column(name="usuario_registro", nullable=false)
  private String usuarioRegistro;
  
  public Usuario()
  {
    this.id = Integer.valueOf(0);
    this.nombre = "";
    this.email = " ";
    this.login = "";
    this.password = "";
    this.state = "AC";
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
    if (!(obj instanceof Usuario)) {
      return false;
    }
    if (((Usuario)obj).id == this.id) {
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
  
  public String getEmail()
  {
    return this.email;
  }
  
  public void setEmail(String email)
  {
    this.email = email;
  }
  
  public String getLogin()
  {
    return this.login;
  }
  
  public void setLogin(String login)
  {
    this.login = login;
  }
  
  public String getPassword()
  {
    return this.password;
  }
  
  public void setPassword(String password)
  {
    this.password = password;
  }
  
  public String getState()
  {
    return this.state;
  }
  
  public void setState(String state)
  {
    this.state = state;
  }
  
  public Date getFechaRegistro()
  {
    return this.fechaRegistro;
  }
  
  public void setFechaRegistro(Date fechaRegistro)
  {
    this.fechaRegistro = fechaRegistro;
  }
  
  public byte[] getFotoPerfil()
  {
    return this.fotoPerfil;
  }
  
  public void setFotoPerfil(byte[] fotoPerfil)
  {
    this.fotoPerfil = fotoPerfil;
  }
  
  public String getNombre()
  {
    return this.nombre;
  }
  
  public void setNombre(String nombre)
  {
    this.nombre = nombre;
  }
  
  public String getUsuarioRegistro()
  {
    return this.usuarioRegistro;
  }
  
  public void setUsuarioRegistro(String usuarioRegistro)
  {
    this.usuarioRegistro = usuarioRegistro;
  }
  
  public int getPesoFoto()
  {
    return this.pesoFoto;
  }
  
  public void setPesoFoto(int pesoFoto)
  {
    this.pesoFoto = pesoFoto;
  }
  
  public Date getFechaModificacion()
  {
    return this.fechaModificacion;
  }
  
  public void setFechaModificacion(Date fechaModificacion)
  {
    this.fechaModificacion = fechaModificacion;
  }
}
