package bo.com.erp360.controller;

import bo.com.erp360.data.RolesRepository;
import bo.com.erp360.data.UsuarioRepository;
import bo.com.erp360.data.UsuarioRolRepository;
import bo.com.erp360.model.Empresa;
import bo.com.erp360.model.Roles;
import bo.com.erp360.model.Usuario;
import bo.com.erp360.model.UsuarioRol;
import bo.com.erp360.service.UsuarioRegistration;
import bo.com.erp360.service.UsuarioRolRegistration;
import bo.com.erp360.util.FacesUtil;
import bo.com.erp360.util.SessionMain;
import java.io.PrintStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.event.Event;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.SelectEvent;
import org.richfaces.cdi.push.Push;

@Named("usuarioController")
@ConversationScoped
public class UsuarioController
  implements Serializable
{
  private static final long serialVersionUID = 6211210765749674269L;
  public static final String PUSH_CDI_TOPIC = "pushCdi";
  @Inject
  private FacesContext facesContext;
  @Inject
  Conversation conversation;
  @Inject
  private RolesRepository rolesRepository;
  @Inject
  private UsuarioRolRepository usuarioRolRepository;
  @Inject
  private UsuarioRepository usuarioRepository;
  @Inject
  private UsuarioRolRegistration usuarioRolRegistration;
  @Inject
  private UsuarioRegistration usuarioRegistration;
  @Inject
  private SessionMain sessionMain;
  private String nombreUsuario;
  private Empresa empresaLogin;
  @Inject
  @Push(topic="pushCdi")
  Event<String> pushEventSucursal;
  private String tituloPanel;
  private String nombreRol;
  private String nombreEstado = "ACTIVO";
  @Produces
  @Named
  private Usuario newUsuario;
  private Usuario selectedUsuario;
  private UsuarioRol selectedUsuarioRol;
  private Roles selectedRol;
  private List<Usuario> listUsuario = new ArrayList();
  private List<Usuario> listFilterUsuario = new ArrayList();
  private List<Roles> listRol = new ArrayList();
  private String[] listEstado = { "ACTIVO", "INACTIVO" };
  private List<UsuarioRol> listUsuarioRol = new ArrayList();
  private List<UsuarioRol> listFilterUsuarioRol = new ArrayList();
  private boolean crear = true;
  private boolean registrar = false;
  private boolean modificar = false;
  private boolean stateInicial = true;
  private String tipoColumnRegistro = "col-md-4";
  private String tipoColumnTable = "col-md-12";
  
  @PostConstruct
  public void initNewUsuario()
  {
    System.out.println(" init new initNewUsuario");
    
    beginConversation();
    this.nombreUsuario = this.sessionMain.getUsuarioLogin().getLogin();
    this.empresaLogin = this.sessionMain.getEmpresaLogin();
    
    this.listRol = this.rolesRepository.findAllOrderByAsc();
    
    loadDefault();
  }
  
  private void loadDefault()
  {
    this.tipoColumnRegistro = "col-md-4";
    this.tipoColumnTable = "col-md-12";
    
    this.crear = true;
    this.registrar = false;
    this.modificar = false;
    this.stateInicial = true;
    this.newUsuario = new Usuario();
    this.selectedUsuario = new Usuario();
    this.listUsuario = this.usuarioRepository.findAllOrderedByID();
    
    this.listUsuarioRol = this.usuarioRolRepository.findAllOrderedByNombre();
    
    this.nombreRol = ((Roles)this.listRol.get(0)).getNombre();
    this.selectedRol = ((Roles)this.listRol.get(0));
    
    this.tituloPanel = "Registrar Usuario";
    this.modificar = false;
  }
  
  public void resetearFitrosTabla(String id)
  {
    DataTable table = (DataTable)FacesContext.getCurrentInstance().getViewRoot().findComponent(id);
    table.setSelection(null);
    table.reset();
  }
  
  public void beginConversation()
  {
    if (this.conversation.isTransient())
    {
      System.out.println("beginning conversation : " + this.conversation);
      this.conversation.begin();
      System.out.println("---> Init Conversation");
    }
  }
  
  public void endConversation()
  {
    if (!this.conversation.isTransient()) {
      this.conversation.end();
    }
  }
  
  public void registrarUsuario()
  {
    try
    {
      System.out.println("Ingreso a registrarUsuario: ");
      Date fechaActual = new Date();
      this.newUsuario.setFechaRegistro(new Date());
      this.newUsuario.setUsuarioRegistro(this.nombreUsuario);
      this.newUsuario.setState(this.nombreEstado.equals("ACTIVO") ? "AC" : "IN");
      if (this.usuarioRepository.findByLogin(this.newUsuario.getLogin()) != null)
      {
        FacesUtil.infoMessage("VALIDACION", "Usuario " + this.newUsuario.getLogin() + " ya existe.");
        resetearFitrosTabla("formTableUsuario:dataTableUser");
        return;
      }
      this.newUsuario = ((Usuario)this.usuarioRegistration.create(this.newUsuario));
      
      UsuarioRol usuarioRol = new UsuarioRol();
      usuarioRol.setRol(this.selectedRol);
      usuarioRol.setUsuario(this.newUsuario);
      usuarioRol.setEstado("AC");
      usuarioRol.setFechaRegistro(fechaActual);
      usuarioRol.setUsuarioRegistro(this.nombreUsuario);
      this.usuarioRolRegistration.create(usuarioRol);
      
      resetearFitrosTabla("formTableUsuario:dataTableUser");
      FacesUtil.infoMessage("Registro", "Usuario Registrado! " + this.newUsuario.getLogin());
      loadDefault();
    }
    catch (Exception e)
    {
      System.out.println("Error al registrar Usuario error: " + e.getMessage());
      resetearFitrosTabla("formTableUsuario:dataTableUser");
    }
  }
  
  public void modificarUsuario()
  {
    try
    {
      System.out.println("Ingreso a modificarUsuario: " + 
        this.newUsuario.getId());
      Date fechaActual = new Date();
      this.newUsuario.setFechaModificacion(new Date());
      this.newUsuario.setState(this.nombreEstado.equals("ACTIVO") ? "AC" : "IN");
      
      this.usuarioRegistration.update(this.newUsuario);
      UsuarioRol usuarioRol = this.usuarioRolRepository.findByUsuario(this.newUsuario);
      usuarioRol.setRol(this.selectedRol);
      usuarioRol.setFechaModificacion(fechaActual);
      this.usuarioRolRegistration.update(usuarioRol);
      
      FacesUtil.infoMessage("Usuario Modificado", this.newUsuario.getLogin());
      resetearFitrosTabla("formTableUsuario:dataTableUser");
      loadDefault();
    }
    catch (Exception e)
    {
      System.out.println("Error al Modificar. Usuario error: " + e.getMessage());
    }
  }
  
  public void eliminarUsuario()
  {
    try
    {
      Date fechaActual = new Date();
      System.out.println("Ingreso a eliminarUsuario " + 
        this.newUsuario.getId());
      this.newUsuario.setState("RM");
      this.newUsuario.setFechaModificacion(fechaActual);
      this.usuarioRegistration.update(this.newUsuario);
      
      this.selectedUsuarioRol.setEstado("RM");
      
      this.usuarioRolRegistration.update(this.selectedUsuarioRol);
      
      FacesUtil.infoMessage("Usuario Eliminado", this.newUsuario.getLogin());
      resetearFitrosTabla("formTableUsuario:dataTableUser");
      loadDefault();
    }
    catch (Exception e)
    {
      System.out.println("Error al Eliminar. Usuario error: " + e.getMessage());
    }
  }
  
  public void onRowSelect(SelectEvent event)
  {
    this.selectedRol = this.selectedUsuarioRol.getRol();
    this.nombreRol = this.selectedRol.getNombre();
    this.newUsuario = this.selectedUsuario;
    this.newUsuario = this.selectedUsuarioRol.getUsuario();
    this.nombreEstado = (this.newUsuario.getState().equals("AC") ? "ACTIVO" : "INACTIVO");
    this.crear = false;
    this.registrar = false;
    this.modificar = true;
    this.tipoColumnTable = "col-md-8";
    resetearFitrosTabla("formTableUsuario:dataTableUser");
  }
  
  public void actualizarFormReg()
  {
    this.crear = true;
    this.registrar = false;
    this.modificar = false;
    this.tipoColumnTable = "col-md-12";
    this.newUsuario = new Usuario();
    this.selectedUsuario = null;
    resetearFitrosTabla("formTableUsuario:dataTableUser");
  }
  
  public void cambiarAspecto()
  {
    this.crear = false;
    this.registrar = true;
    this.modificar = false;
    this.tipoColumnTable = "col-md-8";
    this.selectedUsuario = new Usuario();
    this.newUsuario = new Usuario();
  }
  
  public void validate(FacesContext arg0, UIComponent arg1, Object arg2)
    throws ValidatorException
  {
    if (((String)arg2).length() < 1) {
      throw new ValidatorException(new FacesMessage("Al menos 1 caracteres "));
    }
  }
  
  private Roles obtenerRolByLocal(String nombreRol)
  {
    for (Roles r : this.listRol) {
      if (r.getNombre().equals(nombreRol)) {
        return r;
      }
    }
    return null;
  }
  
  public String getTituloPanel()
  {
    return this.tituloPanel;
  }
  
  public void setTituloPanel(String tituloPanel)
  {
    this.tituloPanel = tituloPanel;
  }
  
  public boolean isModificar()
  {
    return this.modificar;
  }
  
  public void setModificar(boolean modificar)
  {
    this.modificar = modificar;
  }
  
  public Usuario getSelectedUsuario()
  {
    return this.selectedUsuario;
  }
  
  public void setSelectedUsuario(Usuario selectedUsuario)
  {
    this.selectedUsuario = selectedUsuario;
  }
  
  public List<Usuario> getListUsuario()
  {
    return this.listUsuario;
  }
  
  public void setListUsuario(List<Usuario> listUsuario)
  {
    this.listUsuario = listUsuario;
  }
  
  public String getNombreRol()
  {
    return this.nombreRol;
  }
  
  public void setNombreRol(String nombreRol)
  {
    this.nombreRol = nombreRol;
    this.selectedRol = obtenerRolByLocal(nombreRol);
  }
  
  public List<Usuario> getListFilterUsuario()
  {
    return this.listFilterUsuario;
  }
  
  public void setListFilterUsuario(List<Usuario> listFilterUsuario)
  {
    this.listFilterUsuario = listFilterUsuario;
  }
  
  public String getNombreEstado()
  {
    return this.nombreEstado;
  }
  
  public void setNombreEstado(String nombreEstado)
  {
    this.nombreEstado = nombreEstado;
  }
  
  public String[] getListEstado()
  {
    return this.listEstado;
  }
  
  public void setListEstado(String[] listEstado)
  {
    this.listEstado = listEstado;
  }
  
  public boolean isStateInicial()
  {
    return this.stateInicial;
  }
  
  public void setStateInicial(boolean stateInicial)
  {
    this.stateInicial = stateInicial;
  }
  
  public String getTipoColumnRegistro()
  {
    return this.tipoColumnRegistro;
  }
  
  public void setTipoColumnRegistro(String tipoColumnRegistro)
  {
    this.tipoColumnRegistro = tipoColumnRegistro;
  }
  
  public String getTipoColumnTable()
  {
    return this.tipoColumnTable;
  }
  
  public void setTipoColumnTable(String tipoColumnTable)
  {
    this.tipoColumnTable = tipoColumnTable;
  }
  
  public boolean isCrear()
  {
    return this.crear;
  }
  
  public void setCrear(boolean crear)
  {
    this.crear = crear;
  }
  
  public boolean isRegistrar()
  {
    return this.registrar;
  }
  
  public void setRegistrar(boolean registrar)
  {
    this.registrar = registrar;
  }
  
  public Roles getSelectedRol()
  {
    return this.selectedRol;
  }
  
  public void setSelectedRol(Roles selectedRol)
  {
    this.selectedRol = selectedRol;
  }
  
  public List<Roles> getListRol()
  {
    return this.listRol;
  }
  
  public void setListRol(List<Roles> listRol)
  {
    this.listRol = listRol;
  }
  
  public List<UsuarioRol> getListUsuarioRol()
  {
    return this.listUsuarioRol;
  }
  
  public void setListUsuarioRol(List<UsuarioRol> listUsuarioRol)
  {
    this.listUsuarioRol = listUsuarioRol;
  }
  
  public UsuarioRol getSelectedUsuarioRol()
  {
    return this.selectedUsuarioRol;
  }
  
  public void setSelectedUsuarioRol(UsuarioRol selectedUsuarioRol)
  {
    this.selectedUsuarioRol = selectedUsuarioRol;
  }
  
  public List<UsuarioRol> getListFilterUsuarioRol()
  {
    return this.listFilterUsuarioRol;
  }
  
  public void setListFilterUsuarioRol(List<UsuarioRol> listFilterUsuarioRol)
  {
    this.listFilterUsuarioRol = listFilterUsuarioRol;
  }
}
