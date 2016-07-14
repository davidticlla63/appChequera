package bo.com.erp360.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="usuario_empresa", catalog="public")
public class UsuarioEmpresa
  implements Serializable
{
  private static final long serialVersionUID = -760977855120148759L;
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Column(name="id", unique=true, nullable=false)
  private Integer id;
  @ManyToOne(fetch=FetchType.EAGER, optional=false)
  @JoinColumn(name="id_usuario", nullable=false)
  private Usuario usuario;
  @ManyToOne(fetch=FetchType.EAGER, optional=false)
  @JoinColumn(name="id_empresa", nullable=false)
  private Empresa empresa;
  
  public UsuarioEmpresa()
  {
    this.id = Integer.valueOf(0);
  }
  
  public UsuarioEmpresa(Integer id, Usuario usuario, Empresa empresa)
  {
    this.id = id;
    this.usuario = usuario;
    this.empresa = empresa;
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
      if (((UsuarioEmpresa)obj).id == this.id) {
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
  
  public Usuario getUsuario()
  {
    return this.usuario;
  }
  
  public void setUsuario(Usuario usuario)
  {
    this.usuario = usuario;
  }
  
  public Empresa getEmpresa()
  {
    return this.empresa;
  }
  
  public void setEmpresa(Empresa empresa)
  {
    this.empresa = empresa;
  }
}
