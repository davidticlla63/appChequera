package bo.com.erp360.controller;

import bo.com.erp360.model.Empresa;
import bo.com.erp360.model.Usuario;
import bo.com.erp360.service.UsuarioRegistration;
import bo.com.erp360.util.FacesUtil;
import bo.com.erp360.util.SessionMain;
import java.io.PrintStream;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named("perfilController")
@SessionScoped
public class PerfilController
  implements Serializable
{
  private static final long serialVersionUID = -2989737706810995315L;
  @Inject
  private UsuarioRegistration usuarioRegistration;
  private Usuario newUsuario;
  private boolean modificar = false;
  private boolean modificarPerfil;
  private String nombreUsuario;
  @Inject
  private SessionMain sessionMain;
  private Empresa empresaLogin;
  
  @PostConstruct
  public void initNewPerfil()
  {
    System.out.println("initNewPerfil()");
    setNombreUsuario(this.sessionMain.getUsuarioLogin().getLogin());
    setEmpresaLogin(this.sessionMain.getEmpresaLogin());
    this.newUsuario = this.sessionMain.getUsuarioLogin();
    this.modificar = false;
    this.modificarPerfil = false;
  }
  
  public void actionButtonModificar()
  {
    System.out.println("actionButtonModificar");
    this.modificarPerfil = true;
  }
  
  public void modificarUsuario()
  {
    this.usuarioRegistration.update(this.newUsuario);
    this.sessionMain.setUsuarioLogin(this.newUsuario);
    FacesUtil.infoMessage("Usuario modificado correctamente", this.newUsuario.getNombre());
    this.modificarPerfil = false;
  }
  
  public boolean isModificar()
  {
    return this.modificar;
  }
  
  public void setModificar(boolean modificar)
  {
    this.modificar = modificar;
  }
  
  public void cambiarModificar()
  {
    setModificar(false);
  }
  
  public String getNombreUsuario()
  {
    return this.nombreUsuario;
  }
  
  public void setNombreUsuario(String nombreUsuario)
  {
    this.nombreUsuario = nombreUsuario;
  }
  
  public Empresa getEmpresaLogin()
  {
    return this.empresaLogin;
  }
  
  public void setEmpresaLogin(Empresa empresaLogin)
  {
    this.empresaLogin = empresaLogin;
  }
  
  public boolean isModificarPerfil()
  {
    return this.modificarPerfil;
  }
  
  public void setModificarPerfil(boolean modificarPerfil)
  {
    this.modificarPerfil = modificarPerfil;
  }
  
  public Usuario getNewUsuario()
  {
    return this.newUsuario;
  }
  
  public void setNewUsuario(Usuario newUsuario)
  {
    this.newUsuario = newUsuario;
  }
}
