package bo.com.erp360.controller;

import bo.com.erp360.dao.BancoDao;
import bo.com.erp360.dao.ChequeraDao;
import bo.com.erp360.dao.MovimientoCuentasDao;
import bo.com.erp360.model.Banco;
import bo.com.erp360.model.Chequera;
import bo.com.erp360.model.Cuenta;
import bo.com.erp360.model.Empresa;
import bo.com.erp360.model.MovimientoCuentas;
import bo.com.erp360.model.Usuario;
import bo.com.erp360.util.FacesUtil;
import bo.com.erp360.util.Fechas;
import bo.com.erp360.util.NumerosToLetras;
import bo.com.erp360.util.SessionMain;
import java.io.PrintStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
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
import org.primefaces.event.SelectEvent;
import org.primefaces.model.TreeNode;
import org.richfaces.cdi.push.Push;

@Named("registroChequesController")
@ConversationScoped
public class RegistroChequesController
  implements Serializable
{
  private static final long serialVersionUID = 6616998315599191334L;
  public static final String PUSH_CDI_TOPIC = "pushCdi";
  @Inject
  Conversation conversation;
  @Inject
  private FacesContext facesContext;
  @Inject
  @Push(topic="pushCdi")
  Event<String> pushEventSucursal;
  @Inject
  private ChequeraDao chequeraDao;
  @Inject
  private BancoDao bancoDao;
  @Inject
  private MovimientoCuentasDao movimientoCuentasDao;
  private boolean modificar = false;
  private boolean registrar = false;
  private boolean crear = true;
  private boolean verReporte;
  private MovimientoCuentas newMovimientoCuentas;
  private Chequera selectedChequera;
  private List<Chequera> listChequeras = new ArrayList();
  private List<MovimientoCuentas> listMovimientoCuentas = new ArrayList();
  private String nombreMonedaEmpresa;
  private double totalSaldoNacional;
  private double totalSaldoExtranjero;
  private double totalPagadoNacional;
  private double totalPagadoExtranjero;
  private double totalNuevoSaldoNacional;
  private double totalNuevoSaldoExtranjero;
  private String tipoParametroCuenta;
  private String filterByCuenta;
  private boolean mostrarTableBusqueda;
  private String urlCheque;
  @Inject
  private SessionMain sessionMain;
  private Usuario usuarioLogin;
  private Empresa empresaLogin;
  private TreeNode selectedNodeCuenta;
  private double totalNacional = 0.0D;
  private String totalLiteral;
  private String tipoMoneda;
  
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
    this.totalLiteral = "";
    this.totalNacional = 0.0D;
    this.verReporte = false;
    
    this.mostrarTableBusqueda = true;
    this.filterByCuenta = "";
    this.tipoParametroCuenta = "1";
    this.modificar = false;
    this.registrar = false;
    this.crear = true;
    
    this.listMovimientoCuentas = this.movimientoCuentasDao.obtenerOrdenDescPorId();
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
  
  public void onRowSelect(SelectEvent event)
  {
    this.crear = false;
    this.registrar = false;
    this.modificar = true;
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
  
  public void registrarMovimientoCuenta()
  {
    System.out.println("registrar()");
    if ((this.selectedChequera == null) && (this.newMovimientoCuentas.getId().intValue() == 0))
    {
      FacesUtil.infoMessage("Debe seleccionar la Chequera.", "");
      return;
    }
    if ((this.totalNacional == 0.0D) && (this.newMovimientoCuentas.getId().intValue() == 0))
    {
      FacesUtil.infoMessage("No puede hacer con valor Cero.", "");
      return;
    }
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
    if ((this.selectedChequera == null) && (this.newMovimientoCuentas.getId().intValue() == 0))
    {
      FacesUtil.infoMessage("Debe seleccionar la Chequera.", "");
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
    if (this.selectedChequera.getCuenta().getTipoMoneda().equals("BOLIVIANOS"))
    {
      this.newMovimientoCuentas.setMontoNacional(this.totalNacional);
      this.newMovimientoCuentas.setMontoExtranjero(this.totalNacional / 
        this.newMovimientoCuentas.getTipoCambio());
      this.newMovimientoCuentas.setSaldo(this.newMovimientoCuentas.getMontoNacional());
    }
    else if (this.selectedChequera.getCuenta().getTipoMoneda().equals("EXTRANJERA"))
    {
      this.newMovimientoCuentas.setMontoExtranjero(this.totalNacional);
      this.newMovimientoCuentas.setMontoNacional(this.totalNacional * this.newMovimientoCuentas.getTipoCambio());
      this.newMovimientoCuentas.setItf(this.newMovimientoCuentas.getMontoExtranjero() * 0.02D);
      this.newMovimientoCuentas.setSaldo(this.newMovimientoCuentas.getMontoExtranjero() - this.newMovimientoCuentas.getItf());
    }
    this.newMovimientoCuentas.setNumeroTransaccion(this.chequeraDao.obtenerChequera(
      this.selectedChequera).getSecuencia());
    this.newMovimientoCuentas.setEmpresa(this.empresaLogin);
    this.newMovimientoCuentas.setTipoTransaccion("CHEQUE");
    this.newMovimientoCuentas.setMontoLiteral(this.totalLiteral);
    this.newMovimientoCuentas.setCuenta(this.selectedChequera.getCuenta());
    this.newMovimientoCuentas.setTipoMoneda(this.selectedChequera.getCuenta()
      .getTipoMoneda());
    this.newMovimientoCuentas.setUsuarioRegistro(this.usuarioLogin.getLogin());
    MovimientoCuentas g = this.movimientoCuentasDao.registrar(
      this.newMovimientoCuentas, this.selectedChequera);
    if (g != null)
    {
      loadDefault();
      this.newMovimientoCuentas = g;
      setUrlCheque(obtenerUrCheque());
      this.verReporte = true;
      FacesUtil.updateComponent("form001");
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
  
  public void modificarFacturaCompra() {}
  
  public void cambiarAspecto()
  {
    this.crear = false;
    this.registrar = true;
    this.modificar = false;
  }
  
  public void convertToLeteral()
  {
    this.totalLiteral = NumerosToLetras.convertNumberToLetterSM(this.totalNacional);
  }
  
  private void convetirTipoMoneda()
  {
    if (this.selectedChequera.getCuenta().getTipoMoneda().equals("BOLIVIANOS")) {
      this.tipoMoneda = "Bolivianos";
    } else if (this.selectedChequera.getCuenta().getTipoMoneda().equals("EXTRANJERA")) {
      this.tipoMoneda = "Dolares Americanos";
    }
  }
  
  public List<Chequera> completeChequera(String query)
  {
    this.listChequeras = new ArrayList();
    List<Chequera> results = new ArrayList();
    List<Banco> listbBancos = this.bancoDao.obtenerPorNombreConsulta(query
      .toUpperCase());
    Iterator localIterator2;
    for (Iterator localIterator1 = listbBancos.iterator(); localIterator1.hasNext(); localIterator2.hasNext())
    {
      Banco banco = (Banco)localIterator1.next();
      this.listChequeras = this.chequeraDao.obtenerPorBanco(banco);
      localIterator2 = this.listChequeras.iterator(); continue;Chequera i = (Chequera)localIterator2.next();
      if (i.getBanco().getNombre().toUpperCase().startsWith(query.toUpperCase())) {
        results.add(i);
      }
    }
    this.listChequeras = results;
    return results;
  }
  
  public void onRowSelectChequeraClick(SelectEvent event)
  {
    Integer idChequera = Integer.valueOf(Integer.parseInt(event.getObject().toString()));
    System.out
      .println("Ingreso a onRowSelectChequeraClick : " + idChequera);
    System.out.println(this.listChequeras.size());
    for (Chequera i : this.listChequeras)
    {
      System.out.println(i.getBanco().getNombre());
      if (i.getId().intValue() == idChequera.intValue())
      {
        setSelectedChequera(i);
        this.newMovimientoCuentas.setCuenta(this.selectedChequera.getCuenta());
        this.newMovimientoCuentas.setNumeroCheque(this.selectedChequera
          .getSecuencia());
        this.newMovimientoCuentas.setLugar(this.selectedChequera.getCuenta()
          .getCiudad());
        convetirTipoMoneda();
        return;
      }
    }
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
  
  public double getTotalSaldoNacional()
  {
    return this.totalSaldoNacional;
  }
  
  public void setTotalSaldoNacional(double totalSaldoNacional)
  {
    this.totalSaldoNacional = totalSaldoNacional;
  }
  
  public double getTotalSaldoExtranjero()
  {
    return this.totalSaldoExtranjero;
  }
  
  public void setTotalSaldoExtranjero(double totalSaldoExtranjero)
  {
    this.totalSaldoExtranjero = totalSaldoExtranjero;
  }
  
  public double getTotalPagadoNacional()
  {
    return this.totalPagadoNacional;
  }
  
  public void setTotalPagadoNacional(double totalPagadoNacional)
  {
    this.totalPagadoNacional = totalPagadoNacional;
  }
  
  public double getTotalPagadoExtranjero()
  {
    return this.totalPagadoExtranjero;
  }
  
  public void setTotalPagadoExtranjero(double totalPagadoExtranjero)
  {
    this.totalPagadoExtranjero = totalPagadoExtranjero;
  }
  
  public double getTotalNuevoSaldoNacional()
  {
    return this.totalNuevoSaldoNacional;
  }
  
  public void setTotalNuevoSaldoNacional(double totalNuevoSaldoNacional)
  {
    this.totalNuevoSaldoNacional = totalNuevoSaldoNacional;
  }
  
  public double getTotalNuevoSaldoExtranjero()
  {
    return this.totalNuevoSaldoExtranjero;
  }
  
  public void setTotalNuevoSaldoExtranjero(double totalNuevoSaldoExtranjero)
  {
    this.totalNuevoSaldoExtranjero = totalNuevoSaldoExtranjero;
  }
  
  public String getTipoParametroCuenta()
  {
    return this.tipoParametroCuenta;
  }
  
  public void setTipoParametroCuenta(String tipoParametroCuenta)
  {
    this.tipoParametroCuenta = tipoParametroCuenta;
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
  
  public boolean isVerReporte()
  {
    return this.verReporte;
  }
  
  public void setVerReporte(boolean verReporte)
  {
    this.verReporte = verReporte;
  }
  
  public double getTotalNacional()
  {
    return this.totalNacional;
  }
  
  public void setTotalNacional(double totalNacional)
  {
    this.totalNacional = totalNacional;
  }
  
  public String getTotalLiteral()
  {
    return this.totalLiteral;
  }
  
  public void setTotalLiteral(String totalLiteral)
  {
    this.totalLiteral = totalLiteral;
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
}
