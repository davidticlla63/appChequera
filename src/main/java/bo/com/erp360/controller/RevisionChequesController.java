package bo.com.erp360.controller;

import bo.com.erp360.dao.MovimientoCuentasDao;
import bo.com.erp360.model.Chequera;
import bo.com.erp360.model.Empresa;
import bo.com.erp360.model.MovimientoCuentas;
import bo.com.erp360.model.Usuario;
import bo.com.erp360.util.FacesUtil;
import bo.com.erp360.util.Fechas;
import bo.com.erp360.util.NumerosToLetras;
import bo.com.erp360.util.SessionMain;
import bo.com.erp360.util.Time;
import java.io.PrintStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.event.Event;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.richfaces.cdi.push.Push;

@Named("revisionChequesController")
@ConversationScoped
public class RevisionChequesController
  implements Serializable
{
  private static final long serialVersionUID = 4519549147362581643L;
  public static final String PUSH_CDI_TOPIC = "pushCdi";
  @Inject
  Conversation conversation;
  @Inject
  private FacesContext facesContext;
  @Inject
  @Push(topic="pushCdi")
  Event<String> pushEventSucursal;
  @Inject
  private MovimientoCuentasDao movimientoCuentasDao;
  private boolean modificar = false;
  private boolean registrar = false;
  private boolean crear = true;
  private MovimientoCuentas newMovimientoCuentas;
  private MovimientoCuentas selectedMovimientoCuentas;
  private Chequera selectedChequera;
  private List<Chequera> listChequeras = new ArrayList();
  private List<MovimientoCuentas> listMovimientoCuentas = new ArrayList();
  @Inject
  private SessionMain sessionMain;
  private Usuario usuarioLogin;
  private Empresa empresaLogin;
  private Date fechaInicio = Time.getPrimerDiaDelMes(new Date());
  private Date fechaFin = Time.getUltimoDiaDelMes(new Date());
  private String urlCheque;
  private String tipoMoneda;
  private double totalNacional = 0.0D;
  private String totalLiteral;
  
  @PostConstruct
  public void init()
  {
    System.out.println(" ... init New Pago...");
    
    this.usuarioLogin = this.sessionMain.getUsuarioLogin();
    this.empresaLogin = this.sessionMain.getEmpresaLogin();
    
    loadDefault();
  }
  
  public void loadDefault()
  {
    this.newMovimientoCuentas = new MovimientoCuentas();
    this.newMovimientoCuentas.setTransaccion("EGRESO");
    this.newMovimientoCuentas.setFechaLimiteEmision(Fechas.sumarFechaDia(
      new Date(), 30));
    this.selectedChequera = new Chequera();
    
    this.modificar = false;
    this.registrar = false;
    this.crear = true;
    
    this.listMovimientoCuentas = this.movimientoCuentasDao
      .obtenerTodoMovimientosSobreFechasPorEmpresa(this.empresaLogin, 
      this.fechaInicio, this.fechaFin);
  }
  
  public void actualizarFormReg()
  {
    loadDefault();
  }
  
  public void actualizar(MovimientoCuentas movimientoCuentas)
  {
    try
    {
      System.out.println("Ingreso a actualizar");
      MovimientoCuentas mov = this.movimientoCuentasDao
        .modificar(movimientoCuentas);
      if (mov != null) {
        this.listMovimientoCuentas = this.movimientoCuentasDao
          .obtenerTodoMovimientosSobreFechasPorEmpresa(
          this.empresaLogin, this.fechaInicio, this.fechaFin);
      }
    }
    catch (Exception e)
    {
      System.err.println("Error en actualizar  ..." + e.getMessage());
    }
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
    return "page.xhtml?faces-redirect=true";
  }
  
  public void consultar()
  {
    try
    {
      System.out.println("Ingreso a consultar : ");
      this.listMovimientoCuentas = this.movimientoCuentasDao
        .obtenerTodoMovimientosSobreFechasPorEmpresa(this.empresaLogin, 
        this.fechaInicio, this.fechaFin);
    }
    catch (Exception e)
    {
      FacesUtil.infoMessage("Error en consiltar", "MovimientoCuentas " + 
        this.listMovimientoCuentas.size());
    }
  }
  
  public void onRowSelect(SelectEvent event)
  {
    this.newMovimientoCuentas = this.selectedMovimientoCuentas;
    this.tipoMoneda = this.newMovimientoCuentas.getTipoMoneda();
    this.totalLiteral = this.newMovimientoCuentas.getMontoLiteral();
    if (this.newMovimientoCuentas.getTipoMoneda().equals("BOLIVIANOS")) {
      this.totalNacional = this.newMovimientoCuentas.getMontoNacional();
    } else if (this.newMovimientoCuentas.getTipoMoneda().equals("EXTRANJERA")) {
      this.totalNacional = this.newMovimientoCuentas.getMontoExtranjero();
    }
    this.crear = false;
    this.registrar = false;
    this.modificar = true;
  }
  
  public void convertToLeteral()
  {
    setTotalLiteral(NumerosToLetras.convertNumberToLetterSM(this.totalNacional));
  }
  
  public void loadDlgFacturaCompra()
  {
    FacesUtil.updateComponent("formDlgFacturaCompra");
    FacesUtil.showDialog("dlgFacturaCompra");
  }
  
  public void loadDialogCuenta()
  {
    FacesUtil.showDialog("dlgPlanCuenta");
  }
  
  public void AnularMovimientoCuenta()
  {
    System.out.println("registrar()");
    if ((this.selectedChequera == null) && (this.newMovimientoCuentas.getId().intValue() == 0))
    {
      FacesUtil.infoMessage("Debe seleccionar la Chequera.", "");
      return;
    }
    if ((this.newMovimientoCuentas.getPagare().equals("")) && 
      (this.newMovimientoCuentas.getId().intValue() == 0))
    {
      FacesUtil.infoMessage("Debe rellenar a quiene se Pagara.", "");
      return;
    }
    if ((this.newMovimientoCuentas.getDescripcion().equals("")) && 
      (this.newMovimientoCuentas.getId().intValue() == 0))
    {
      FacesUtil.infoMessage("Debe rellar el concepto.", "");
      return;
    }
    this.newMovimientoCuentas.setEstado("A");
    this.newMovimientoCuentas.setUsuarioRegistro(this.usuarioLogin.getLogin());
    this.newMovimientoCuentas.setFechaModificacion(new Date());
    MovimientoCuentas g = this.movimientoCuentasDao
      .modificar(this.newMovimientoCuentas);
    if (g != null)
    {
      loadDefault();
      this.newMovimientoCuentas = g;
      
      FacesUtil.updateComponent("form001");
    }
  }
  
  public void modificarMovimientoCuenta()
  {
    System.out.println("registrar()");
    if (this.totalNacional == 0.0D)
    {
      FacesUtil.infoMessage("Debe revisar el Monto de la Chequera.", "");
      return;
    }
    if (this.newMovimientoCuentas.getTipoCambio() == 0.0D)
    {
      FacesUtil.infoMessage("Debe revisar tipo de Cambio.", "");
      return;
    }
    if ((this.totalNacional == 0.0D) && (this.newMovimientoCuentas.getId().intValue() == 0))
    {
      FacesUtil.infoMessage("No puede hacer con valor Cero.", "");
      return;
    }
    if ((this.newMovimientoCuentas.getPagare().equals("")) && 
      (this.newMovimientoCuentas.getId().intValue() == 0))
    {
      FacesUtil.infoMessage("Debe rellenar a quiene se Pagara.", "");
      return;
    }
    if ((this.newMovimientoCuentas.getDescripcion().equals("")) && 
      (this.newMovimientoCuentas.getId().intValue() == 0))
    {
      FacesUtil.infoMessage("Debe rellar el concepto.", "");
      return;
    }
    if (this.newMovimientoCuentas.getTipoMoneda().equals("BOLIVIANOS"))
    {
      this.newMovimientoCuentas.setMontoNacional(this.totalNacional);
      this.newMovimientoCuentas.setMontoExtranjero(this.totalNacional / 
        this.newMovimientoCuentas.getTipoCambio());
      this.newMovimientoCuentas.setSaldo(this.newMovimientoCuentas
        .getMontoNacional());
    }
    else if (this.newMovimientoCuentas.getTipoMoneda().equals("EXTRANJERA"))
    {
      this.newMovimientoCuentas.setMontoExtranjero(this.totalNacional);
      this.newMovimientoCuentas.setMontoNacional(this.totalNacional * 
        this.newMovimientoCuentas.getTipoCambio());
      this.newMovimientoCuentas.setItf(this.newMovimientoCuentas
        .getMontoExtranjero() * 0.02D);
      this.newMovimientoCuentas.setSaldo(this.newMovimientoCuentas
        .getMontoExtranjero() - this.newMovimientoCuentas.getItf());
    }
    this.newMovimientoCuentas.setFechaModificacion(new Date());
    this.newMovimientoCuentas.setMontoLiteral(this.totalLiteral);
    this.newMovimientoCuentas.setUsuarioRegistro(this.usuarioLogin.getLogin());
    MovimientoCuentas g = this.movimientoCuentasDao
      .modificar(this.newMovimientoCuentas);
    if (g != null)
    {
      loadDefault();
      this.newMovimientoCuentas = g;
      reimprimir();
      FacesUtil.updateComponent("form001");
    }
  }
  
  public void reimprimir()
  {
    this.urlCheque = obtenerUrCheque();
    if (this.urlCheque.trim().length() != 0)
    {
      RequestContext context = RequestContext.getCurrentInstance();
      context.execute("PF('dlgFacturaVistaPrevia').show();");
    }
  }
  
  public String obtenerUrCheque()
  {
    HttpServletRequest request = (HttpServletRequest)this.facesContext
      .getExternalContext().getRequest();
    String urlPath = request.getRequestURL().toString();
    urlPath = urlPath
      .substring(0, urlPath.length() - request.getRequestURI().length()) + 
      request.getContextPath() + "/";
    String urlPDFreporte = urlPath + "ReportCheque?pIdEmpresa=" + 
      this.empresaLogin.getId() + "&pIdCheque=" + 
      this.newMovimientoCuentas.getId();
    return urlPDFreporte;
  }
  
  public String obtenerMontoLiteral(double totalFactura)
  {
    System.out.println("Total Entero Factura >>>>> " + totalFactura);
    NumerosToLetras convert = new NumerosToLetras();
    try
    {
      return NumerosToLetras.convertNumberToLetterSM(totalFactura);
    }
    catch (Exception e)
    {
      System.out.println("Error en obtenerMontoLiteral: " + 
        e.getMessage());
    }
    return "Error Literal";
  }
  
  public void cambiarAspecto()
  {
    this.crear = false;
    this.registrar = true;
    this.modificar = false;
  }
  
  public boolean isModificar()
  {
    return this.modificar;
  }
  
  public void setModificar(boolean modificar)
  {
    this.modificar = modificar;
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
  
  public List<Chequera> getListChequeras()
  {
    return this.listChequeras;
  }
  
  public void setListChequeras(List<Chequera> listChequeras)
  {
    this.listChequeras = listChequeras;
  }
  
  public Chequera getSelectedChequera()
  {
    return this.selectedChequera;
  }
  
  public void setSelectedChequera(Chequera selectedChequera)
  {
    this.selectedChequera = selectedChequera;
  }
  
  public MovimientoCuentas getNewMovimientoCuentas()
  {
    return this.newMovimientoCuentas;
  }
  
  public void setNewMovimientoCheque(MovimientoCuentas newMovimientoCheque)
  {
    this.newMovimientoCuentas = newMovimientoCheque;
  }
  
  public List<MovimientoCuentas> getListMovimientoCuentas()
  {
    return this.listMovimientoCuentas;
  }
  
  public void setListMovimientoCuentas(List<MovimientoCuentas> listMovimientoCuentas)
  {
    this.listMovimientoCuentas = listMovimientoCuentas;
  }
  
  public Date getFechaInicio()
  {
    return this.fechaInicio;
  }
  
  public void setFechaInicio(Date fechaInicio)
  {
    this.fechaInicio = fechaInicio;
  }
  
  public Date getFechaFin()
  {
    return this.fechaFin;
  }
  
  public void setFechaFin(Date fechaFin)
  {
    this.fechaFin = fechaFin;
  }
  
  public MovimientoCuentas getSelectedMovimientoCuentas()
  {
    return this.selectedMovimientoCuentas;
  }
  
  public void setSelectedMovimientoCuentas(MovimientoCuentas selectedMovimientoCuentas)
  {
    this.selectedMovimientoCuentas = selectedMovimientoCuentas;
  }
  
  public String getUrlCheque()
  {
    return this.urlCheque;
  }
  
  public void setUrlCheque(String urlCheque)
  {
    this.urlCheque = urlCheque;
  }
  
  public String getTipoMoneda()
  {
    return this.tipoMoneda;
  }
  
  public void setTipoMoneda(String tipoMoneda)
  {
    this.tipoMoneda = tipoMoneda;
  }
  
  public String getTotalLiteral()
  {
    return this.totalLiteral;
  }
  
  public void setTotalLiteral(String totalLiteral)
  {
    this.totalLiteral = totalLiteral;
  }
  
  public double getTotalNacional()
  {
    return this.totalNacional;
  }
  
  public void setTotalNacional(double totalNacional)
  {
    this.totalNacional = totalNacional;
  }
  
  public Usuario getUsuarioLogin()
  {
    return this.usuarioLogin;
  }
  
  public void setUsuarioLogin(Usuario usuarioLogin)
  {
    this.usuarioLogin = usuarioLogin;
  }
}
