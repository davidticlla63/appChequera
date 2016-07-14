package bo.com.erp360.controller;

import bo.com.erp360.dao.BancoDao;
import bo.com.erp360.dao.ChequeraDao;
import bo.com.erp360.dao.CuentaDao;
import bo.com.erp360.model.Banco;
import bo.com.erp360.model.Chequera;
import bo.com.erp360.model.Cuenta;
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
import org.primefaces.model.TreeNode;
import org.richfaces.cdi.push.Push;

@Named("cuentasController")
@ConversationScoped
public class CuentaController
  implements Serializable
{
  private static final long serialVersionUID = -7962140858225961561L;
  public static final String PUSH_CDI_TOPIC = "pushCdi";
  @Inject
  @Push(topic="pushCdi")
  Event<String> pushEventCuenta;
  @Inject
  private FacesContext facesContext;
  @Inject
  Conversation conversation;
  @Inject
  private CuentaDao cuentaDao;
  @Inject
  private BancoDao bancoDao;
  @Inject
  private ChequeraDao chequeraDao;
  private boolean crear;
  private boolean registrar;
  private boolean modificar;
  private boolean permitirCredito;
  private String nombreEstado = "ACTIVO";
  @Produces
  @Named
  private Cuenta newCuentas;
  private Cuenta selectedCuentas;
  private Banco selectedBanco;
  private Chequera chequera;
  private Chequera selectedChequera;
  private List<Cuenta> listCuentas = new ArrayList();
  private String[] listEstado = { "ACTIVO", "INACTIVO" };
  private List<Banco> listBancos = new ArrayList();
  private List<Chequera> listChequeras = new ArrayList();
  @Inject
  private SessionMain sessionMain;
  private Usuario usuarioLogin;
  private Empresa empresaLogin;
  private String filterByCuenta;
  private boolean mostrarTableBusqueda;
  private TreeNode selectedNodeCuenta;
  private TreeNode selectedNode;
  private boolean seleccionadaChequera = false;
  private boolean estadoButtonDialog = false;
  
  @PostConstruct
  public void initNewCuentas()
  {
    System.out.println(" init new initNewCuenta");
    this.usuarioLogin = this.sessionMain.getUsuarioLogin();
    this.empresaLogin = this.sessionMain.getEmpresaLogin();
    loadDefault();
  }
  
  private void loadDefault()
  {
    System.out.println("Incio loadDefault()");
    this.listChequeras = new ArrayList();
    this.crear = true;
    this.registrar = false;
    this.modificar = false;
    this.permitirCredito = false;
    this.filterByCuenta = "";
    this.selectedBanco = new Banco();
    this.newCuentas = new Cuenta();
    this.selectedCuentas = new Cuenta();
    this.listCuentas = this.cuentaDao
      .findAllActiveParameterOrderedForId(this.empresaLogin);
    this.listBancos = this.bancoDao.obtenerPorEmpresa(this.empresaLogin);
  }
  
  public List<Cuenta> getListCuentas()
  {
    return this.listCuentas;
  }
  
  public void initConversation()
  {
    if ((!FacesContext.getCurrentInstance().isPostback()) && 
      (this.conversation.isTransient()))
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
      this.newCuentas.setEstado(estado);
      this.newCuentas.setFechaRegistro(new Date());
      this.newCuentas.setUsuarioRegistro(this.usuarioLogin.getLogin());
      this.newCuentas.setEmpresa(this.empresaLogin);
      this.newCuentas.setBanco(this.selectedBanco.getId().intValue() == 0 ? null : 
        this.selectedBanco);
      
      Cuenta c = this.cuentaDao.registrar(this.newCuentas);
      if (c != null)
      {
        System.out.println("actualizo banco");
        loadDefault();
        resetearFitrosTabla("formTableCuentas:dataTableCuenta");
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
      String estado = this.nombreEstado.equals("ACTIVO") ? "AC" : "IN";
      this.newCuentas.setEstado(estado);
      this.newCuentas.setFechaRegistro(new Date());
      this.newCuentas.setUsuarioRegistro(this.usuarioLogin.getLogin());
      this.newCuentas.setBanco(this.selectedBanco.getId().intValue() == 0 ? null : 
        this.selectedBanco);
      Cuenta c = this.cuentaDao.modificar(this.newCuentas);
      if (c != null)
      {
        loadDefault();
        resetearFitrosTabla("formTableCuentas:dataTableCuenta");
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
      boolean sw = this.cuentaDao.eliminar(this.newCuentas);
      if (sw) {
        resetearFitrosTabla("formTableCuentas:dataTableCuenta");
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
    DataTable table = (DataTable)FacesContext.getCurrentInstance()
      .getViewRoot().findComponent(id);
    table.setSelection(null);
    table.reset();
  }
  
  public void actualizarFormReg()
  {
    this.crear = true;
    this.registrar = false;
    this.modificar = false;
    resetearFitrosTabla("formTableCuentas:dataTableCuenta");
    this.newCuentas = new Cuenta();
    this.selectedCuentas = new Cuenta();
  }
  
  public void cambiarAspecto()
  {
    this.crear = false;
    this.registrar = true;
    this.modificar = false;
    resetearFitrosTabla("formTableCuentas:dataTableCuenta");
  }
  
  public void onRowSelect(SelectEvent event)
  {
    this.newCuentas = new Cuenta();
    this.newCuentas = this.selectedCuentas;
    this.nombreEstado = (this.newCuentas.getEstado().equals("AC") ? "ACTIVO" : 
      "INACTIVO");
    this.selectedBanco = this.newCuentas.getBanco();
    this.listChequeras = this.chequeraDao.obtenerPorCuenta(this.newCuentas);
    this.crear = false;
    this.registrar = false;
    this.modificar = true;
    resetearFitrosTabla("formTableCuentas:dataTableCuenta");
  }
  
  public void onRowChequeraSelect(SelectEvent event)
  {
    this.chequera = this.selectedChequera;
    this.seleccionadaChequera = true;
    System.out.println("onRowSelectChequera -> selectedChequera:" + 
      this.selectedChequera.getSerie());
  }
  
  public List<Banco> completeBanco(String query)
  {
    this.listBancos = new ArrayList();
    List<Banco> results = new ArrayList();
    this.listBancos = this.bancoDao.obtenerPorNombreConsulta(query);
    for (Banco i : this.listBancos) {
      if (i.getNombre().toUpperCase().startsWith(query.toUpperCase())) {
        results.add(i);
      }
    }
    return results;
  }
  
  public void onRowSelectBancoClick(SelectEvent event)
  {
    String nombre = event.getObject().toString();
    for (Banco i : this.listBancos) {
      if (i.getNombre().equals(nombre))
      {
        this.selectedBanco = i;
        return;
      }
    }
  }
  
  public void buttonCancelarChequera()
  {
    this.selectedChequera = new Chequera();
    this.chequera = new Chequera();
    this.seleccionadaChequera = false;
  }
  
  public void buttonAgregarChequera()
  {
    this.chequera = new Chequera();
    this.chequera.setCuenta(this.newCuentas);
    this.chequera.setBanco(this.newCuentas.getBanco());
    this.chequera.setResponsable(this.newCuentas.getPropietario());
    setEstadoButtonDialog(true);
    FacesUtil.showDialog("dlgChequera");
  }
  
  public void buttonActivarChequera()
  {
    for (Chequera chequera : this.listChequeras)
    {
      if (this.selectedChequera.getId().intValue() == chequera.getId()
        .intValue()) {
        chequera.setActivo(true);
      } else {
        chequera.setActivo(false);
      }
      this.chequeraDao.modificar(chequera);
    }
    this.listChequeras = this.chequeraDao.obtenerPorCuenta(this.newCuentas);
    this.seleccionadaChequera = false;
    this.chequera = new Chequera();
    this.selectedChequera = new Chequera();
    FacesUtil.updateComponent("formReg:dataTableChequera");
    FacesUtil.infoMessage("Chequera Activada", "");
  }
  
  public void buttonModificarChequera()
  {
    setEstadoButtonDialog(false);
    FacesUtil.showDialog("dlgChequera");
  }
  
  public void buttonEliminarChequera()
  {
    if (this.selectedChequera != null)
    {
      for (Chequera chequera : this.listChequeras) {
        if (this.chequera.getId().intValue() == chequera.getId()
          .intValue())
        {
          this.listChequeras.remove(chequera);
          
          this.chequera.setEstado("RM");
          this.chequera.setActivo(false);
          this.chequera.setFechaModificacion(new Date());
          this.chequera = this.chequeraDao.modificar(this.chequera);
          this.listChequeras = this.chequeraDao.obtenerPorCuenta(this.newCuentas);
          
          break;
        }
      }
      FacesUtil.updateComponent("formReg:dataTableChequera");
    }
    else
    {
      FacesUtil.warnMessage("Seleccione una chequera");
    }
    this.seleccionadaChequera = false;
    this.chequera = new Chequera();
    this.selectedChequera = new Chequera();
  }
  
  public void agregarChequera()
  {
    try
    {
      System.out.println("agregarChequera()");
      System.out.println("getAutorizacion() " + this.chequera.getSerie());
      int neg = (this.listChequeras.size() + 1) * -1;
      System.out.println("neg : " + neg);
      
      this.chequera.setEstado("AC");
      this.chequera.setActivo(false);
      this.chequera.setEmpresa(this.empresaLogin);
      this.chequera.setUsuarioRegistro(this.usuarioLogin.getLogin());
      this.chequera.setFechaRegistro(new Date());
      if (this.listChequeras == null) {
        this.listChequeras = new ArrayList();
      }
      boolean existe = false;
      for (Chequera chequera : this.listChequeras) {
        if (this.chequera.getSerie().equals(chequera.getSerie())) {
          existe = true;
        }
      }
      if (existe)
      {
        FacesUtil.warnMessage("Autorizacion de chequera duplicada.!");
      }
      else
      {
        this.chequera.setFechaModificacion(new Date());
        this.chequera = this.chequeraDao.registrar(this.chequera);
        this.listChequeras = this.chequeraDao.obtenerPorCuenta(this.newCuentas);
        this.chequera = new Chequera();
        FacesUtil.infoMessage("Chequera Agregada!", "");
        FacesUtil.hideDialog("dlgChequera");
      }
    }
    catch (Exception e)
    {
      FacesUtil.warnMessage("No se pudo registrar la chequera.!");
    }
  }
  
  public void modificarChequera()
  {
    System.out.println("modificarChequera");
    try
    {
      for (Chequera chequera : this.listChequeras) {
        if (this.chequera.getSerie().equals(chequera.getSerie()))
        {
          this.chequera = chequera;
          this.chequera.setFechaModificacion(new Date());
          this.chequeraDao.modificar(this.chequera);
          this.listChequeras = this.chequeraDao.obtenerPorCuenta(this.newCuentas);
          break;
        }
      }
      this.chequera = new Chequera();
      FacesUtil.infoMessage("Chequera Modificada!", "");
      FacesUtil.hideDialog("dlgChequera");
    }
    catch (Exception e)
    {
      FacesUtil.warnMessage("No se pudo modificar la chequera.!");
    }
  }
  
  public void eliminarChequera()
  {
    try
    {
      this.chequera.setActivo(false);
      this.chequera.setFechaModificacion(new Date());
      this.chequera.setEstado("RM");
      this.chequeraDao.update(this.chequera);
      this.listChequeras = this.chequeraDao.obtenerPorCuenta(this.newCuentas);
    }
    catch (Exception e)
    {
      FacesUtil.warnMessage("No se pudo eliminar la chequera.!");
    }
  }
  
  public void dialogCancelarChequera()
  {
    this.seleccionadaChequera = false;
    this.selectedChequera = new Chequera();
    this.chequera = new Chequera();
    FacesUtil.updateComponent("formReg:dataTableChequera");
    FacesUtil.hideDialog("dlgChequera");
  }
  
  public boolean isModificar()
  {
    return this.modificar;
  }
  
  public void setModificar(boolean modificar)
  {
    this.modificar = modificar;
  }
  
  public Cuenta getSelectedCuentas()
  {
    return this.selectedCuentas;
  }
  
  public void setSelectedCuentas(Cuenta selectedCuentas)
  {
    this.selectedCuentas = selectedCuentas;
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
  
  public Banco getSelectedBanco()
  {
    return this.selectedBanco;
  }
  
  public void setSelectedBanco(Banco selectedBanco)
  {
    this.selectedBanco = selectedBanco;
  }
  
  public List<Banco> getListBancos()
  {
    return this.listBancos;
  }
  
  public void setListBancos(List<Banco> listBancos)
  {
    this.listBancos = listBancos;
  }
  
  public List<Chequera> getListChequeras()
  {
    return this.listChequeras;
  }
  
  public void setListChequeras(List<Chequera> listChequeras)
  {
    this.listChequeras = listChequeras;
  }
  
  public Chequera getChequera()
  {
    return this.chequera;
  }
  
  public void setChequera(Chequera chequera)
  {
    this.chequera = chequera;
  }
  
  public boolean isSeleccionadaChequera()
  {
    return this.seleccionadaChequera;
  }
  
  public void setSeleccionadaChequera(boolean seleccionadaChequera)
  {
    this.seleccionadaChequera = seleccionadaChequera;
  }
  
  public Chequera getSelectedChequera()
  {
    return this.selectedChequera;
  }
  
  public void setSelectedChequera(Chequera selectedChequera)
  {
    this.selectedChequera = selectedChequera;
  }
  
  public boolean isEstadoButtonDialog()
  {
    return this.estadoButtonDialog;
  }
  
  public void setEstadoButtonDialog(boolean estadoButtonDialog)
  {
    this.estadoButtonDialog = estadoButtonDialog;
  }
  
  public String getFilterByCuenta()
  {
    return this.filterByCuenta;
  }
  
  public void setFilterByCuenta(String filterByCuenta)
  {
    this.filterByCuenta = filterByCuenta;
  }
  
  public boolean isMostrarTableBusqueda()
  {
    return this.mostrarTableBusqueda;
  }
  
  public void setMostrarTableBusqueda(boolean mostrarTableBusqueda)
  {
    this.mostrarTableBusqueda = mostrarTableBusqueda;
  }
  
  public TreeNode getSelectedNodeCuenta()
  {
    return this.selectedNodeCuenta;
  }
  
  public void setSelectedNodeCuenta(TreeNode selectedNodeCuenta)
  {
    this.selectedNodeCuenta = selectedNodeCuenta;
  }
  
  public TreeNode getSelectedNode()
  {
    return this.selectedNode;
  }
  
  public void setSelectedNode(TreeNode selectedNode)
  {
    this.selectedNode = selectedNode;
  }
}
