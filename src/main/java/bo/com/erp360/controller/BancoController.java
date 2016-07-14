package bo.com.erp360.controller;

import bo.com.erp360.dao.BancoDao;
import bo.com.erp360.model.Banco;
import bo.com.erp360.model.Empresa;
import bo.com.erp360.model.Usuario;
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
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.SelectEvent;
import org.richfaces.cdi.push.Push;

@Named("bancosController")
@ConversationScoped
public class BancoController
  implements Serializable
{
  private static final long serialVersionUID = -7148739425514986109L;
  public static final String PUSH_CDI_TOPIC = "pushCdi";
  @Inject
  @Push(topic="pushCdi")
  Event<String> pushEventSucursal;
  @Inject
  private FacesContext facesContext;
  @Inject
  Conversation conversation;
  @Inject
  private BancoDao bancoDao;
  private boolean crear;
  private boolean registrar;
  private boolean modificar;
  private boolean permitirCredito;
  private String nombreEstado = "ACTIVO";
  @Produces
  @Named
  private Banco newBancos;
  private Banco selectedBancos;
  private List<Banco> listBancos = new ArrayList();
  private String[] listEstado = { "ACTIVO", "INACTIVO" };
  @Inject
  private SessionMain sessionMain;
  private Usuario usuarioLogin;
  private Empresa empresaLogin;
  
  @PostConstruct
  public void initNewBancos()
  {
    System.out.println(" init new initNewBanco");
    this.usuarioLogin = this.sessionMain.getUsuarioLogin();
    this.empresaLogin = this.sessionMain.getEmpresaLogin();
    loadDefault();
  }
  
  private void loadDefault()
  {
    this.crear = true;
    this.registrar = false;
    this.modificar = false;
    this.permitirCredito = false;
    
    this.newBancos = new Banco();
    this.selectedBancos = new Banco();
    this.listBancos = this.bancoDao.obtenerOrdenAscPorId();
  }
  
  public List<Banco> getListBancos()
  {
    return this.listBancos;
  }
  
  public void initConversation()
  {
    if ((!FacesContext.getCurrentInstance().isPostback()) && (this.conversation.isTransient()))
    {
      this.conversation.begin();
      System.out.println(">>>>>>>>>> CONVERSACION INICIADA...");
    }
  }
  
  public String endConversation()
  {
    if (!this.conversation.isTransient())
    {
      this.conversation.end();
      System.out.println(">>>>>>>>>> CONVERSACION TERMINADA...");
    }
    return "kardex_producto.xhtml?faces-redirect=true";
  }
  
  public void registrar()
  {
    try
    {
      String estado = this.nombreEstado.equals("ACTIVO") ? "AC" : "IN";
      String credito = this.permitirCredito ? "SI" : "NO";
      this.newBancos.setEstado(estado);
      this.newBancos.setFechaRegistro(new Date());
      this.newBancos.setUsuarioRegistro(this.usuarioLogin.getLogin());
      this.newBancos.setEmpresa(this.empresaLogin);
      Banco c = this.bancoDao.registrar(this.newBancos);
      if (c != null)
      {
        loadDefault();
        resetearFitrosTabla("formTableBancos:dataTableBanco");
      }
    }
    catch (Exception e)
    {
      FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, 
        e.getMessage(), "Registro Incorrecto.");
      this.facesContext.addMessage(null, m);
    }
  }
  
  public void loadDialogCuenta()
  {
    FacesUtil.showDialog("dlgPlanCuenta");
  }
  
  public void modificar()
  {
    try
    {
      String credito = this.permitirCredito ? "SI" : "NO";
      String estado = this.nombreEstado.equals("ACTIVO") ? "AC" : "IN";
      this.newBancos.setEstado(estado);
      this.newBancos.setFechaRegistro(new Date());
      this.newBancos.setUsuarioRegistro(this.usuarioLogin.getLogin());
      this.newBancos.setEmpresa(this.empresaLogin);
      
      Banco c = this.bancoDao.modificar(this.newBancos);
      if (c != null)
      {
        loadDefault();
        resetearFitrosTabla("formTableBancos:dataTableBanco");
      }
    }
    catch (Exception e)
    {
      FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, 
        e.getMessage(), "Registro Incorrecto.");
      this.facesContext.addMessage(null, m);
    }
  }
  
  public void eliminar()
  {
    try
    {
      boolean sw = this.bancoDao.eliminar(this.newBancos);
      if (sw) {
        resetearFitrosTabla("formTableBancos:dataTableBanco");
      }
    }
    catch (Exception e)
    {
      FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, 
        e.getMessage(), "Registro Incorrecto.");
      this.facesContext.addMessage(null, m);
    }
  }
  
  public void resetearFitrosTabla(String id)
  {
    DataTable table = (DataTable)FacesContext.getCurrentInstance().getViewRoot().findComponent(id);
    table.setSelection(null);
    table.reset();
  }
  
  public void actualizarFormReg()
  {
    this.crear = true;
    this.registrar = false;
    this.modificar = false;
    resetearFitrosTabla("formTableBancos:dataTableBanco");
    this.newBancos = new Banco();
    this.selectedBancos = new Banco();
  }
  
  public void cambiarAspecto()
  {
    this.crear = false;
    this.registrar = true;
    this.modificar = false;
    resetearFitrosTabla("formTableBancos:dataTableBanco");
  }
  
  public void onRowSelect(SelectEvent event)
  {
    this.newBancos = new Banco();
    this.newBancos = this.selectedBancos;
    this.nombreEstado = (this.newBancos.getEstado().equals("AC") ? "ACTIVO" : "INACTIVO");
    this.crear = false;
    this.registrar = false;
    this.modificar = true;
    resetearFitrosTabla("formTableBancos:dataTableBanco");
  }
  
  public boolean isModificar()
  {
    return this.modificar;
  }
  
  public void setModificar(boolean modificar)
  {
    this.modificar = modificar;
  }
  
  public Banco getSelectedBancos()
  {
    return this.selectedBancos;
  }
  
  public void setSelectedBancos(Banco selectedBancos)
  {
    this.selectedBancos = selectedBancos;
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
  
  public boolean isPermitirCredito()
  {
    return this.permitirCredito;
  }
  
  public void setPermitirCredito(boolean permitirCredito)
  {
    this.permitirCredito = permitirCredito;
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
}
