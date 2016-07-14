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
@Table(name="empresa", catalog="public", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"razon_social"})})
public class Empresa
  implements Serializable
{
  private static final long serialVersionUID = -5304022702792729682L;
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private Integer id;
  @Column(name="razon_social", nullable=false)
  private String razonSocial;
  private String direccion;
  private String telefono;
  private String nit;
  private String ciudad;
  @Column(name="foto_perfil", nullable=true)
  private byte[] fotoPerfil;
  @Column(name="peso_foto", nullable=true)
  private int pesoFoto;
  @Size(max=2)
  private String estado;
  @Column(name="usuario_registro", nullable=false)
  private String usuarioRegistro;
  private Date fecha_registro;
  @Column(name="fecha_modificacion", nullable=true)
  private Date fechaModificacion;
  @Column(name="actividad_economica", nullable=true)
  private String actividadEconomica;
  @Column(name="carnet_identidad", nullable=true, length=20)
  private String carnetIdentidad;
  @Column(name="representante_legal", nullable=true, length=200)
  private String representanteLegal;
  
  public Empresa()
  {
    this.id = Integer.valueOf(0);
    this.razonSocial = "";
    this.direccion = "";
    this.telefono = "";
    this.nit = "";
    this.ciudad = "";
    this.pesoFoto = 0;
  }
  
  public String toString()
  {
    return this.razonSocial;
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
    if (!(obj instanceof Empresa)) {
      return false;
    }
    if (((Empresa)obj).id == this.id) {
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
  
  public String getDireccion()
  {
    return this.direccion;
  }
  
  public void setDireccion(String direccion)
  {
    this.direccion = direccion;
  }
  
  public String getTelefono()
  {
    return this.telefono;
  }
  
  public void setTelefono(String telefono)
  {
    this.telefono = telefono;
  }
  
  public String getCiudad()
  {
    return this.ciudad;
  }
  
  public void setCiudad(String ciudad)
  {
    this.ciudad = ciudad;
  }
  
  public String getRazonSocial()
  {
    return this.razonSocial;
  }
  
  public void setRazonSocial(String razonSocial)
  {
    this.razonSocial = razonSocial;
  }
  
  public String getNit()
  {
    return this.nit;
  }
  
  public void setNit(String nit)
  {
    this.nit = nit;
  }
  
  public String getEstado()
  {
    return this.estado;
  }
  
  public void setEstado(String estado)
  {
    this.estado = estado;
  }
  
  public String getUsuarioRegistro()
  {
    return this.usuarioRegistro;
  }
  
  public void setUsuarioRegistro(String usuarioRegistro)
  {
    this.usuarioRegistro = usuarioRegistro;
  }
  
  public Date getFecha_registro()
  {
    return this.fecha_registro;
  }
  
  public void setFecha_registro(Date fecha_registro)
  {
    this.fecha_registro = fecha_registro;
  }
  
  public Date getFechaModificacion()
  {
    return this.fechaModificacion;
  }
  
  public void setFechaModificacion(Date fechaModificacion)
  {
    this.fechaModificacion = fechaModificacion;
  }
  
  public byte[] getFotoPerfil()
  {
    return this.fotoPerfil;
  }
  
  public void setFotoPerfil(byte[] fotoPerfil)
  {
    this.fotoPerfil = fotoPerfil;
  }
  
  public int getPesoFoto()
  {
    return this.pesoFoto;
  }
  
  public void setPesoFoto(int pesoFoto)
  {
    this.pesoFoto = pesoFoto;
  }
  
  public String getRepresentanteLegal()
  {
    return this.representanteLegal;
  }
  
  public void setRepresentanteLegal(String representanteLegal)
  {
    this.representanteLegal = representanteLegal;
  }
  
  public String getCarnetIdentidad()
  {
    return this.carnetIdentidad;
  }
  
  public void setCarnetIdentidad(String carnetIdentidad)
  {
    this.carnetIdentidad = carnetIdentidad;
  }
  
  public String getActividadEconomica()
  {
    return this.actividadEconomica;
  }
  
  public void setActividadEconomica(String actividadEconomica)
  {
    this.actividadEconomica = actividadEconomica;
  }
}
