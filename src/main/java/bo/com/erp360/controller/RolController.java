package bo.com.erp360.controller;

import bo.com.erp360.data.RolesRepository;
import bo.com.erp360.model.Empresa;
import bo.com.erp360.model.Roles;
import bo.com.erp360.model.Usuario;
import bo.com.erp360.service.RolRegistration;
import bo.com.erp360.util.FacesUtil;
import bo.com.erp360.util.SessionMain;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.event.Event;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.apache.log4j.Logger;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.SelectEvent;
import org.richfaces.cdi.push.Push;

@Named("rolController")
@ConversationScoped
public class RolController
  implements Serializable
{
  private static final long serialVersionUID = 1730442750062837853L;
  public static final String PUSH_CDI_TOPIC = "pushCdi";
  @Inject
  private FacesContext facesContext;
  @Inject
  Conversation conversation;
  @Inject
  private RolRegistration rolRegistration;
  @Inject
  private RolesRepository rolesRepository;
  private Logger log = Logger.getLogger(getClass());
  @Inject
  private SessionMain sessionMain;
  private String nombreUsuario;
  private Empresa empresaLogin;
  @Inject
  @Push(topic="pushCdi")
  Event<String> pushEventSucursal;
  private boolean crear = true;
  private boolean registrar = false;
  private boolean modificar = false;
  private String tituloPanel = "Registrar Roles";
  private String tipoColumnTable;
  private String nombreEstado = "ACTIVO";
  private Roles newRol;
  private Roles selectedRol;
  private List<Roles> listRol = new ArrayList();
  private List<Roles> listFilterRol = new ArrayList();
  private List<Usuario> listUsuario = new ArrayList();
  private String[] listEstado = { "ACTIVO", "INACTIVO" };
  
  @PostConstruct
  public void initNewRoles()
  {
    this.log.info(" init new initNewRoles");
    beginConversation();
    this.nombreUsuario = this.sessionMain.getUsuarioLogin().getLogin();
    this.empresaLogin = this.sessionMain.getEmpresaLogin();
    
    loadDefault();
  }
  
  private void loadDefault()
  {
    this.tipoColumnTable = "col-md-12";
    this.newRol = new Roles();
    this.selectedRol = new Roles();
    this.listRol = this.rolesRepository.findAllOrderByAsc();
    this.modificar = false;
    this.crear = true;
    this.registrar = false;
  }
  
  public void beginConversation()
  {
    if (this.conversation.isTransient())
    {
      this.log.info("beginning conversation : " + this.conversation);
      this.conversation.begin();
      this.log.info("---> Init Conversation");
    }
  }
  
  public void endConversation()
  {
    if (!this.conversation.isTransient()) {
      this.conversation.end();
    }
  }
  
  public void resetearFitrosTabla(String id)
  {
    DataTable table = (DataTable)FacesContext.getCurrentInstance().getViewRoot().findComponent(id);
    table.setSelection(null);
    table.reset();
  }
  
  public void registrarRol()
  {
    if ((this.newRol.getNombre().isEmpty()) || (this.newRol.getDescripcion().isEmpty()))
    {
      FacesUtil.infoMessage("VALIDACION", "No pueden haber campos vacios.");
      resetearFitrosTabla("formTableRoles:dataTableRoles");
      return;
    }
    try
    {
      this.newRol.setEstado(this.nombreEstado.equals("ACTIVO") ? "AC" : "IN");
      this.newRol.setFechaRegistro(new Date());
      this.newRol.setUsuarioRegistro(this.nombreUsuario);
      if (this.rolesRepository.findRolByNombre(this.newRol.getNombre()) != null)
      {
        FacesUtil.infoMessage("VALIDACION", "Rol " + this.newRol.getNombre() + " ya existe.");
        resetearFitrosTabla("formTableRoles:dataTableRoles");
        return;
      }
      this.rolRegistration.create(this.newRol);
      FacesUtil.showDialog("Rol registrado " + this.newRol.getNombre());
      resetearFitrosTabla("formTableRoles:dataTableRoles");
      loadDefault();
    }
    catch (Exception e)
    {
      this.log.error("registrarRoles error: " + e.getMessage());
    }
  }
  
  public void modificarRol()
  {
    try
    {
      this.rolRegistration.update(this.newRol);
      FacesUtil.showDialog("Rol modificado " + this.newRol.getNombre());
      resetearFitrosTabla("formTableRoles:dataTableRoles");
      loadDefault();
    }
    catch (Exception e)
    {
      this.log.error("modificarRoles error: " + e.getMessage());
      FacesUtil.errorMessage("Error al modificar Rol");
    }
  }
  
  public void eliminarRol()
  {
    try
    {
      this.rolRegistration.update(this.newRol);
      FacesUtil.showDialog("Rol Eliminado" + this.newRol.getNombre());
      FacesUtil.resetDataTable("formTableRoles:dataTableRoles");
      loadDefault();
    }
    catch (Exception e)
    {
      this.log.error("eliminarRoles error: " + e.getMessage());
      FacesUtil.errorMessage("Error al eliminar Rol");
    }
  }
  
  public void actualizarForm()
  {
    this.crear = true;
    this.registrar = false;
    this.modificar = false;
    this.tipoColumnTable = "col-md-12";
    this.selectedRol = new Roles();
    this.newRol = new Roles();
    resetearFitrosTabla("formTableRoles:dataTableRoles");
  }
  
  public void onRowSelect(SelectEvent event)
  {
    this.newRol = this.selectedRol;
    this.crear = false;
    this.registrar = false;
    this.modificar = true;
    this.tipoColumnTable = "col-md-8";
    
    resetearFitrosTabla("formTableRoles:dataTableRoles");
  }
  
  public void cambiarAspecto()
  {
    this.crear = false;
    this.registrar = true;
    this.modificar = false;
    this.tipoColumnTable = "col-md-8";
    this.selectedRol = new Roles();
    this.newRol = new Roles();
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
  
  public List<Usuario> getListUsuario()
  {
    return this.listUsuario;
  }
  
  public void setListUsuario(List<Usuario> listUsuario)
  {
    this.listUsuario = listUsuario;
  }
  
  public String getTest()
  {
    return "test";
  }
  
  public Roles getNewRol()
  {
    return this.newRol;
  }
  
  public void setNewRol(Roles newRol)
  {
    this.newRol = newRol;
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
  
  public List<Roles> getListFilterRol()
  {
    return this.listFilterRol;
  }
  
  public void setListFilterRol(List<Roles> listFilterRol)
  {
    this.listFilterRol = listFilterRol;
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
  
  public String getTipoColumnTable()
  {
    return this.tipoColumnTable;
  }
  
  public void setTipoColumnTable(String tipoColumnTable)
  {
    this.tipoColumnTable = tipoColumnTable;
  }
  
  public String[] getListEstado()
  {
    return this.listEstado;
  }
  
  public void setListEstado(String[] listEstado)
  {
    this.listEstado = listEstado;
  }
  
  public String getNombreEstado()
  {
    return this.nombreEstado;
  }
  
  public void setNombreEstado(String nombreEstado)
  {
    this.nombreEstado = nombreEstado;
  }
}
