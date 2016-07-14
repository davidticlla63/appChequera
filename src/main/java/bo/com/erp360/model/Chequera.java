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
@Table(name="chequera", schema="public")
public class Chequera
  implements Serializable
{
  private static final long serialVersionUID = -181829592812339159L;
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private Integer id;
  private String descripcion = "CHEQUE";
  private String serie;
  @Column(name="responsable", nullable=true)
  private String responsable;
  @Column(name="numero_inicial", nullable=false)
  private int numeroInicial = 0;
  private int secuencia = 0;
  @Column(name="numero_final", nullable=false)
  private int numeroFinal = 50;
  @ManyToOne(fetch=FetchType.EAGER, optional=false)
  @JoinColumn(name="id_empresa", nullable=false)
  private Empresa empresa;
  @ManyToOne(fetch=FetchType.EAGER, optional=false)
  @JoinColumn(name="id_cuenta", nullable=false)
  private Cuenta cuenta;
  @ManyToOne(fetch=FetchType.EAGER)
  @JoinColumn(name="id_banco", nullable=false)
  private Banco banco;
  @Size(max=2)
  private String estado;
  private boolean activo = false;
  @Column(name="fecha_registro", nullable=false)
  private Date fechaRegistro;
  @Column(name="fecha_modificacion", nullable=true)
  private Date fechaModificacion;
  @Column(name="usuario_registro", nullable=false)
  private String usuarioRegistro;
  
  public Chequera()
  {
    this.id = Integer.valueOf(0);
    this.estado = "AC";
    setFechaRegistro(new Date());
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
    if (!(obj instanceof Chequera)) {
      return false;
    }
    if (((Chequera)obj).id == this.id) {
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
  
  public int getNumeroInicial()
  {
    return this.numeroInicial;
  }
  
  public void setNumeroInicial(int numeroCheque)
  {
    this.numeroInicial = numeroCheque;
  }
  
  public int getNumeroFinal()
  {
    return this.numeroFinal;
  }
  
  public void setNumeroFinal(int nuemroMaxCheque)
  {
    this.numeroFinal = nuemroMaxCheque;
  }
  
  public Empresa getEmpresa()
  {
    return this.empresa;
  }
  
  public void setEmpresa(Empresa empresa)
  {
    this.empresa = empresa;
  }
  
  public boolean isActivo()
  {
    return this.activo;
  }
  
  public void setActivo(boolean activo)
  {
    this.activo = activo;
  }
  
  public int getSecuencia()
  {
    return this.secuencia;
  }
  
  public void setSecuencia(int secuencia)
  {
    this.secuencia = secuencia;
  }
  
  public Cuenta getCuenta()
  {
    return this.cuenta;
  }
  
  public void setCuenta(Cuenta cuenta)
  {
    this.cuenta = cuenta;
  }
  
  public String getSerie()
  {
    return this.serie;
  }
  
  public void setSerie(String serie)
  {
    this.serie = serie;
  }
  
  public String getResponsable()
  {
    return this.responsable;
  }
  
  public void setResponsable(String responsable)
  {
    this.responsable = responsable;
  }
  
  public Banco getBanco()
  {
    return this.banco;
  }
  
  public void setBanco(Banco banco)
  {
    this.banco = banco;
  }
  
  public String getDescripcion()
  {
    return this.descripcion;
  }
  
  public void setDescripcion(String descripcion)
  {
    this.descripcion = descripcion;
  }
}
