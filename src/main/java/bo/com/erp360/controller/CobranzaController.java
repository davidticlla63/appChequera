package bo.com.erp360.controller;

import bo.com.erp360.dao.BancoDao;
import bo.com.erp360.dao.CuentaDao;
import bo.com.erp360.dao.MovimientoCuentasDao;
import bo.com.erp360.model.Banco;
import bo.com.erp360.model.Cuenta;
import bo.com.erp360.model.MovimientoCuentas;
import bo.com.erp360.model.Usuario;
import bo.com.erp360.util.FacesUtil;
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
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.SelectEvent;
import org.richfaces.cdi.push.Push;

@Named("cobranzaController")
@ConversationScoped
public class CobranzaController
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
  private MovimientoCuentasDao movimientoCuentasDao;
  @Inject
  private CuentaDao cuentaDao;
  private boolean crear;
  private boolean registrar;
  private boolean modificar;
  private boolean permitirCredito;
  private String nombreEstado = "ACTIVO";
  @Produces
  @Named
  private MovimientoCuentas newMovimientoCuentas;
  private MovimientoCuentas selectedMovimientoCuentas;
  private Cuenta selectedCuenta;
  private List<MovimientoCuentas> listMovimientoCuentas = new ArrayList();
  private String[] listEstado = { "ACTIVO", "INACTIVO" };
  private List<Cuenta> listCuentas = new ArrayList();
  @Inject
  private SessionMain sessionMain;
  private Usuario usuarioLogin;
  private boolean estadoButtonDialog = false;
  private String totalLiteral;
  private double montoCancelado = 0.0D;
  @Inject
  private BancoDao bancoDao;
  
  @PostConstruct
  public void initNewMovimientoCuentas()
  {
    System.out.println(" init new initNewCuenta");
    this.usuarioLogin = this.sessionMain.getUsuarioLogin();
    loadDefault();
  }
  
  private void loadDefault()
  {
    System.out.println("Incio loadDefault()");
    this.montoCancelado = 0.0D;
    this.crear = true;
    this.registrar = false;
    this.modificar = false;
    this.permitirCredito = false;
    
    this.selectedCuenta = new Cuenta();
    this.newMovimientoCuentas = new MovimientoCuentas();
    this.selectedMovimientoCuentas = new MovimientoCuentas();
    this.listMovimientoCuentas = this.movimientoCuentasDao.obtenerOrdenDescPorId();
    this.listCuentas = this.cuentaDao.obtenerOrdenAscPorId();
  }
  
  public List<MovimientoCuentas> getListMovimientoCuentas()
  {
    return this.listMovimientoCuentas;
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
      if ((this.montoCancelado == 0.0D) && (this.newMovimientoCuentas.getId().intValue() == 0))
      {
        FacesUtil.infoMessage("Debe rellenar el monto.", "");
        return;
      }
      if ((this.newMovimientoCuentas.getDescripcion().equals("")) && 
        (this.newMovimientoCuentas.getId().intValue() == 0))
      {
        FacesUtil.infoMessage("Debe rellar el concepto.", "");
        return;
      }
      if (this.newMovimientoCuentas.getTipoTransaccion().equals("DEPOSITO")) {
        this.newMovimientoCuentas.setTransaccion("INGRESO");
      }
      if (this.newMovimientoCuentas.getTipoTransaccion().equals("RETIRO")) {
        this.newMovimientoCuentas.setTransaccion("EGRESO");
      }
      if (this.selectedCuenta.getTipoMoneda().equals("NACIONAL"))
      {
        this.newMovimientoCuentas.setMontoNacional(this.montoCancelado);
        this.newMovimientoCuentas.setMontoExtranjero(this.montoCancelado / 6.9D);
      }
      if (this.selectedCuenta.getTipoMoneda().equals("EXTRANJERA"))
      {
        this.newMovimientoCuentas.setMontoExtranjero(this.montoCancelado);
        this.newMovimientoCuentas.setMontoNacional(this.montoCancelado * 6.9D);
      }
      this.newMovimientoCuentas.setNumeroTransaccion(this.cuentaDao.obtenerCuenta(
        this.selectedCuenta).getId().intValue());
      this.newMovimientoCuentas.setMontoLiteral(this.totalLiteral);
      this.newMovimientoCuentas.setCuenta(this.selectedCuenta);
      this.newMovimientoCuentas.setTipoMoneda(this.selectedCuenta.getTipoMoneda());
      this.newMovimientoCuentas.setTipoCambio(6.9D);
      this.newMovimientoCuentas.setUsuarioRegistro(this.usuarioLogin.getLogin());
      this.newMovimientoCuentas.setFechaLimiteEmision(new Date());
      
      MovimientoCuentas g = this.movimientoCuentasDao
        .registrar(this.newMovimientoCuentas);
      if (g != null)
      {
        loadDefault();
        resetearFitrosTabla("formTableMovimientoCuentas:dataTableCuenta");
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
      this.newMovimientoCuentas.setFechaRegistro(new Date());
      this.newMovimientoCuentas.setUsuarioRegistro(this.usuarioLogin.getLogin());
      MovimientoCuentas c = this.movimientoCuentasDao
        .modificar(this.newMovimientoCuentas);
      if (c != null)
      {
        loadDefault();
        resetearFitrosTabla("formTableMovimientoCuentas:dataTableCuenta");
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
      boolean sw = this.movimientoCuentasDao.eliminar(this.newMovimientoCuentas);
      if (sw) {
        resetearFitrosTabla("formTableMovimientoCuentas:dataTableCuenta");
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
    resetearFitrosTabla("formTableMovimientoCuentas:dataTableCuenta");
    this.newMovimientoCuentas = new MovimientoCuentas();
    this.selectedMovimientoCuentas = new MovimientoCuentas();
  }
  
  public void cambiarAspecto()
  {
    this.crear = false;
    this.registrar = true;
    this.modificar = false;
    resetearFitrosTabla("formTableMovimientoCuentas:dataTableCuenta");
  }
  
  public void onRowSelect(SelectEvent event)
  {
    this.newMovimientoCuentas = new MovimientoCuentas();
    this.newMovimientoCuentas = this.selectedMovimientoCuentas;
    this.nombreEstado = (this.newMovimientoCuentas.getEstado().equals("AC") ? "ACTIVO" : 
      "INACTIVO");
    this.selectedCuenta = this.newMovimientoCuentas.getCuenta();
    this.crear = false;
    this.registrar = false;
    this.modificar = true;
    resetearFitrosTabla("formTableMovimientoCuentas:dataTableCuenta");
  }
  
  public List<Cuenta> completeCuenta(String query)
  {
    this.listCuentas = new ArrayList();
    List<Cuenta> results = new ArrayList();
    List<Banco> listbBancos = this.bancoDao.obtenerPorNombreConsulta(query
      .toUpperCase());
    Iterator localIterator2;
    for (Iterator localIterator1 = listbBancos.iterator(); localIterator1.hasNext(); localIterator2.hasNext())
    {
      Banco banco = (Banco)localIterator1.next();
      this.listCuentas = this.cuentaDao.obtenerPorBanco(banco);
      localIterator2 = this.listCuentas.iterator(); continue;Cuenta i = (Cuenta)localIterator2.next();
      if (i.getBanco().getNombre().toUpperCase().startsWith(query.toUpperCase())) {
        results.add(i);
      }
    }
    this.listCuentas = results;
    return results;
  }
  
  public void onRowSelectCuentaClick(SelectEvent event)
  {
    Integer idChequera = Integer.valueOf(Integer.parseInt(event.getObject().toString()));
    System.out.println("Ingreso a onRowSelectCuentaClick : " + idChequera);
    System.out.println(this.listCuentas.size());
    for (Cuenta i : this.listCuentas)
    {
      System.out.println(i.getBanco().getNombre());
      if (i.getId().intValue() == idChequera.intValue())
      {
        setSelectedCuenta(i);
        this.newMovimientoCuentas.setNumeroCheque(this.selectedCuenta.getId().intValue());
        this.newMovimientoCuentas.setCuenta(this.selectedCuenta);
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
  
  public MovimientoCuentas getSelectedMovimientoCuentas()
  {
    return this.selectedMovimientoCuentas;
  }
  
  public void setSelectedMovimientoCuentas(MovimientoCuentas selectedMovimientoCuentas)
  {
    this.selectedMovimientoCuentas = selectedMovimientoCuentas;
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
  
  public Cuenta getSelectedCuenta()
  {
    return this.selectedCuenta;
  }
  
  public void setSelectedCuenta(Cuenta selectedCuenta)
  {
    this.selectedCuenta = selectedCuenta;
  }
  
  public List<Cuenta> getListCuentas()
  {
    return this.listCuentas;
  }
  
  public void setListCuentas(List<Cuenta> listCuentas)
  {
    this.listCuentas = listCuentas;
  }
  
  public boolean isEstadoButtonDialog()
  {
    return this.estadoButtonDialog;
  }
  
  public void setEstadoButtonDialog(boolean estadoButtonDialog)
  {
    this.estadoButtonDialog = estadoButtonDialog;
  }
  
  public String getTotalLiteral()
  {
    return this.totalLiteral;
  }
  
  public void setTotalLiteral(String totalLiteral)
  {
    this.totalLiteral = totalLiteral;
  }
  
  public double getMontoCancelado()
  {
    return this.montoCancelado;
  }
  
  public void setMontoCancelado(double montoCancelado)
  {
    this.montoCancelado = montoCancelado;
  }
}
