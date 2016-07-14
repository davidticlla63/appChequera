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
@Table(name="cuenta", schema="public")
public class Cuenta
  implements Serializable
{
  private static final long serialVersionUID = 6878998142336154575L;
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private Integer id;
  @Column(name="tipo_cuenta", nullable=false, length=30)
  private String tipoCuenta;
  @Column(name="tipo_moneda", nullable=true)
  private String tipoMoneda;
  private String propietario;
  @Column(name="numero_cuenta", nullable=false, length=30)
  private String numeroCuenta;
  @ManyToOne(fetch=FetchType.EAGER)
  @JoinColumn(name="id_banco", nullable=false)
  private Banco banco;
  @ManyToOne(fetch=FetchType.EAGER, optional=false)
  @JoinColumn(name="id_empresa", nullable=false)
  private Empresa empresa;
  @Column(name="ciudad", nullable=false)
  private String ciudad;
  @Size(max=2)
  private String estado;
  @Column(name="fecha_registro", nullable=false)
  private Date fechaRegistro;
  @Column(name="fecha_modificacion", nullable=true)
  private Date fechaModificacion;
  @Column(name="usuario_registro", nullable=false)
  private String usuarioRegistro;
  
  public Cuenta()
  {
    this.id = Integer.valueOf(0);
    this.tipoMoneda = "BOLIVIANOS";
    this.tipoCuenta = "CUENTA CORRIENTE";
    this.estado = "AC";
    setFechaRegistro(new Date());
  }
  
  public String toString()
  {
    return this.tipoCuenta;
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
    if (!(obj instanceof Cuenta)) {
      return false;
    }
    if (((Cuenta)obj).id == this.id) {
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
  
  public Banco getBanco()
  {
    return this.banco;
  }
  
  public void setBanco(Banco banco)
  {
    this.banco = banco;
  }
  
  public Empresa getEmpresa()
  {
    return this.empresa;
  }
  
  public void setEmpresa(Empresa empresa)
  {
    this.empresa = empresa;
  }
  
  public String getNumeroCuenta()
  {
    return this.numeroCuenta;
  }
  
  public void setNumeroCuenta(String numeroCuenta)
  {
    this.numeroCuenta = numeroCuenta;
  }
  
  public String getPropietario()
  {
    return this.propietario;
  }
  
  public void setPropietario(String propietario)
  {
    this.propietario = propietario;
  }
  
  public String getTipoCuenta()
  {
    return this.tipoCuenta;
  }
  
  public void setTipoCuenta(String tipoCuenta)
  {
    this.tipoCuenta = tipoCuenta;
  }
  
  public String getTipoMoneda()
  {
    return this.tipoMoneda;
  }
  
  public void setTipoMoneda(String tipoMoneda)
  {
    this.tipoMoneda = tipoMoneda;
  }
  
  public String getCiudad()
  {
    return this.ciudad;
  }
  
  public void setCiudad(String ciudad)
  {
    this.ciudad = ciudad;
  }
}
