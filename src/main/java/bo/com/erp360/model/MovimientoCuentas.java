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
@Table(name="movimiento_cuentas", schema="public")
public class MovimientoCuentas
  implements Serializable
{
  private static final long serialVersionUID = -2950142516605202454L;
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private Integer id;
  @Column(name="numero_cheque", nullable=true, length=30)
  private int numeroCheque;
  @Column(name="numero_transaccion", nullable=true, length=30)
  private int numeroTransaccion;
  @Column(name="transaccion", nullable=false, length=30)
  private String transaccion;
  @Column(name="tipo_transaccion", nullable=false, length=30)
  private String tipoTransaccion;
  @Column(name="descripcion", nullable=false)
  private String descripcion;
  private String lugar;
  private double itf = 0.0D;
  private double saldo = 0.0D;
  @Column(name="monto_nacional", nullable=false)
  private double montoNacional = 0.0D;
  @Column(name="monto_extranjero", nullable=false)
  private double montoExtranjero = 0.0D;
  @Column(name="monto_literal", nullable=true)
  private String montoLiteral;
  @Column(name="tipo_moneda", nullable=false)
  private String tipoMoneda;
  @Column(name="fecha_limite_emision", nullable=false)
  private Date fechaLimiteEmision;
  @Column(name="tipo_cambio", nullable=false)
  private double tipoCambio;
  private String pagare;
  @ManyToOne(fetch=FetchType.EAGER, optional=false)
  @JoinColumn(name="id_cuenta", nullable=false)
  private Cuenta cuenta;
  @ManyToOne(fetch=FetchType.EAGER, optional=false)
  @JoinColumn(name="id_empresa", nullable=false)
  private Empresa empresa;
  @Column(name="cobrado", nullable=false)
  private boolean cobrado = false;
  @Size(max=2)
  private String estado;
  @Column(name="fecha_registro", nullable=false)
  private Date fechaRegistro;
  @Column(name="fecha_modificacion", nullable=true)
  private Date fechaModificacion;
  @Column(name="usuario_registro", nullable=false)
  private String usuarioRegistro;
  
  public MovimientoCuentas()
  {
    this.id = Integer.valueOf(0);
    this.estado = "AC";
    setFechaRegistro(new Date());
  }
  
  public String toString()
  {
    return this.descripcion;
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
    if (!(obj instanceof MovimientoCuentas)) {
      return false;
    }
    if (((MovimientoCuentas)obj).id == this.id) {
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
  
  public double getMontoNacional()
  {
    return this.montoNacional;
  }
  
  public void setMontoNacional(double montoNacional)
  {
    this.montoNacional = montoNacional;
  }
  
  public double getMontoExtranjero()
  {
    return this.montoExtranjero;
  }
  
  public void setMontoExtranjero(double montoExtranjero)
  {
    this.montoExtranjero = montoExtranjero;
  }
  
  public int getNumeroCheque()
  {
    return this.numeroCheque;
  }
  
  public void setNumeroCheque(int numeroCheque)
  {
    this.numeroCheque = numeroCheque;
  }
  
  public String getLugar()
  {
    return this.lugar;
  }
  
  public void setLugar(String lugar)
  {
    this.lugar = lugar;
  }
  
  public String getMontoLiteral()
  {
    return this.montoLiteral;
  }
  
  public void setMontoLiteral(String montoLiteral)
  {
    this.montoLiteral = montoLiteral;
  }
  
  public String getTipoMoneda()
  {
    return this.tipoMoneda;
  }
  
  public void setTipoMoneda(String tipoMoneda)
  {
    this.tipoMoneda = tipoMoneda;
  }
  
  public Date getFechaLimiteEmision()
  {
    return this.fechaLimiteEmision;
  }
  
  public void setFechaLimiteEmision(Date fechaLimiteEmision)
  {
    this.fechaLimiteEmision = fechaLimiteEmision;
  }
  
  public String getPagare()
  {
    return this.pagare;
  }
  
  public void setPagare(String pagare)
  {
    this.pagare = pagare;
  }
  
  public int getNumeroTransaccion()
  {
    return this.numeroTransaccion;
  }
  
  public void setNumeroTransaccion(int numeroTransaccion)
  {
    this.numeroTransaccion = numeroTransaccion;
  }
  
  public String getTipoTransaccion()
  {
    return this.tipoTransaccion;
  }
  
  public void setTipoTransaccion(String tipoTransaccion)
  {
    this.tipoTransaccion = tipoTransaccion;
  }
  
  public String getDescripcion()
  {
    return this.descripcion;
  }
  
  public void setDescripcion(String descripcion)
  {
    this.descripcion = descripcion;
  }
  
  public String getTransaccion()
  {
    return this.transaccion;
  }
  
  public void setTransaccion(String transaccion)
  {
    this.transaccion = transaccion;
  }
  
  public Cuenta getCuenta()
  {
    return this.cuenta;
  }
  
  public void setCuenta(Cuenta cuenta)
  {
    this.cuenta = cuenta;
  }
  
  public double getTipoCambio()
  {
    return this.tipoCambio;
  }
  
  public void setTipoCambio(double tipoCambio)
  {
    this.tipoCambio = tipoCambio;
  }
  
  public boolean isCobrado()
  {
    return this.cobrado;
  }
  
  public void setCobrado(boolean cobrado)
  {
    this.cobrado = cobrado;
  }
  
  public Empresa getEmpresa()
  {
    return this.empresa;
  }
  
  public void setEmpresa(Empresa empresa)
  {
    this.empresa = empresa;
  }
  
  public double getItf()
  {
    return this.itf;
  }
  
  public void setItf(double itf)
  {
    this.itf = itf;
  }
  
  public double getSaldo()
  {
    return this.saldo;
  }
  
  public void setSaldo(double saldo)
  {
    this.saldo = saldo;
  }
}
