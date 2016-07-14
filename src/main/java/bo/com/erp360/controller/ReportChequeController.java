package bo.com.erp360.controller;

import bo.com.erp360.dao.BancoDao;
import bo.com.erp360.dao.CuentaDao;
import bo.com.erp360.dao.MovimientoCuentasDao;
import bo.com.erp360.model.Banco;
import bo.com.erp360.model.Cuenta;
import bo.com.erp360.model.Empresa;
import bo.com.erp360.model.MovimientoCuentas;
import bo.com.erp360.model.Usuario;
import bo.com.erp360.util.FacesUtil;
import bo.com.erp360.util.SessionMain;
import bo.com.erp360.util.Time;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.Serializable;
import java.net.URL;
import java.net.URLEncoder;
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
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.richfaces.cdi.push.Push;

@Named("reportChequeController")
@ConversationScoped
public class ReportChequeController
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
  private MovimientoCuentasDao movimientoCuentasDao;
  private Date fechaInicio = Time.getPrimerDiaDelMes(new Date());
  private Date fechaFin = Time.getUltimoDiaDelMes(new Date());
  private String nombreEstado = "ACTIVO";
  private Cuenta selectedCuentas;
  private List<Cuenta> listCuentas = new ArrayList();
  private List<MovimientoCuentas> listMovimientoCuentas = new ArrayList();
  @Inject
  private SessionMain sessionMain;
  private Usuario usuarioLogin;
  private Empresa empresaLogin;
  private String urlReport;
  private StreamedContent streamedUrlExcel;
  private StreamedContent streamedUrlPdf;
  
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
    
    this.selectedCuentas = new Cuenta();
    this.listCuentas = this.cuentaDao.obtenerOrdenDescPorId();
    
    this.listMovimientoCuentas = this.movimientoCuentasDao
      .obtenerTodoMovimientosSobreFechasPorEmpresa(this.empresaLogin, 
      this.fechaInicio, this.fechaFin);
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
  
  public void consultar()
  {
    try
    {
      System.out.println("Ingreso a consultar");
      this.listMovimientoCuentas = this.movimientoCuentasDao
        .obtenerTodoMovimientosSobreFechasPorCuenta(
        this.selectedCuentas, this.empresaLogin, this.fechaInicio, 
        this.fechaFin);
    }
    catch (Exception e)
    {
      FacesUtil.infoMessage("Error en consiltar", "MovimientoCuentas " + 
        this.listMovimientoCuentas.size());
    }
  }
  
  public String obtenerUrMovimiento()
  {
    HttpServletRequest request = (HttpServletRequest)this.facesContext
      .getExternalContext().getRequest();
    String urlPath = request.getRequestURL().toString();
    urlPath = urlPath
      .substring(0, urlPath.length() - request.getRequestURI().length()) + 
      request.getContextPath() + "/";
    String urlPDFreporte = urlPath + "ReportMovimiento?pIdEmpresa=" + 
      this.empresaLogin.getId() + "&pNombreEmpresa=" + 
      this.empresaLogin.getRazonSocial() + "&pFechaInicio=" + 
      Time.obtenerFormatoYYYYMMDD(this.fechaInicio) + "&pFechaFin=" + 
      Time.obtenerFormatoYYYYMMDD(this.fechaFin) + "&pFechaInicioTxt=" + 
      Time.convertSimpleDateToString(this.fechaInicio) + 
      "&pFechaFinTxt=" + Time.convertSimpleDateToString(this.fechaFin);
    return urlPDFreporte;
  }
  
  public void setStreamedUrlExcel(StreamedContent streamedUrlExcel)
  {
    this.streamedUrlExcel = streamedUrlExcel;
  }
  
  public StreamedContent getStreamedUrlExcel()
  {
    try
    {
      HttpServletRequest request = (HttpServletRequest)this.facesContext
        .getExternalContext().getRequest();
      String urlPath = request.getRequestURL().toString();
      urlPath = urlPath
        .substring(0, urlPath.length() - request.getRequestURI().length()) + 
        request.getContextPath() + "/";
      String urlPDFreporte = urlPath + 
        "ReportMovimientoExcel?pIdEmpresa=" + 
        URLEncoder.encode(new StringBuilder().append(this.empresaLogin.getId()).toString(), "UTF-8") + 
        "&pIdCuenta=" + 
        URLEncoder.encode(new StringBuilder().append(this.selectedCuentas.getId()).toString(), "UTF-8") + 
        "&pNombreEmpresa=" + 
        URLEncoder.encode(new StringBuilder().append(this.empresaLogin.getRazonSocial()).toString(), 
        "ISO-8859-1") + 
        "&pFechaInicio=" + 
        URLEncoder.encode(
        new StringBuilder().append(Time.obtenerFormatoYYYYMMDD(this.fechaInicio)).toString(), 
        "UTF-8") + 
        "&pFechaFin=" + 
        
        URLEncoder.encode(new StringBuilder().append(Time.obtenerFormatoYYYYMMDD(this.fechaFin)).toString(), 
        "UTF-8") + 
        "&pFechaInicioTxt=" + 
        URLEncoder.encode(
        new StringBuilder().append(Time.convertSimpleDateToString(this.fechaInicio)).toString(), 
        "ISO-8859-1") + 
        "&pFechaFinTxt=" + 
        URLEncoder.encode(
        new StringBuilder().append(Time.convertSimpleDateToString(this.fechaFin)).toString(), 
        "ISO-8859-1");
      System.out.println(urlPDFreporte);
      URL url = new URL(urlPDFreporte);
      
      InputStream is1 = url.openStream();
      File f = stream2fileExcel(is1);
      System.out.println("Size Bytes: " + f.length());
      InputStream stream = new FileInputStream(f);
      this.streamedUrlExcel = new DefaultStreamedContent(stream, 
        "application/vnd.ms-excel", "LibroVentasSFV.xls");
      
      return this.streamedUrlExcel;
    }
    catch (Exception e)
    {
      e.printStackTrace();
      System.out.println("Error en getStreamedLibroVentas: " + 
        e.getMessage());
    }
    return null;
  }
  
  public StreamedContent getStreamedUrlPdf()
  {
    try
    {
      HttpServletRequest request = (HttpServletRequest)this.facesContext
        .getExternalContext().getRequest();
      String urlPath = request.getRequestURL().toString();
      urlPath = urlPath
        .substring(0, urlPath.length() - request.getRequestURI().length()) + 
        request.getContextPath() + "/";
      String urlPDFreporte = urlPath + 
        "ReportMovimiento?pIdEmpresa=" + 
        URLEncoder.encode(new StringBuilder().append(this.empresaLogin.getId()).toString(), "UTF-8") + 
        "&pIdCuenta=" + 
        URLEncoder.encode(new StringBuilder().append(this.selectedCuentas.getId()).toString(), "UTF-8") + 
        "&pNombreEmpresa=" + 
        URLEncoder.encode(new StringBuilder().append(this.empresaLogin.getRazonSocial()).toString(), 
        "ISO-8859-1") + 
        "&pFechaInicio=" + 
        URLEncoder.encode(
        new StringBuilder().append(Time.obtenerFormatoYYYYMMDD(this.fechaInicio)).toString(), 
        "UTF-8") + 
        "&pFechaFin=" + 
        
        URLEncoder.encode(new StringBuilder().append(Time.obtenerFormatoYYYYMMDD(this.fechaFin)).toString(), 
        "UTF-8") + 
        "&pFechaInicioTxt=" + 
        URLEncoder.encode(
        new StringBuilder().append(Time.convertSimpleDateToString(this.fechaInicio)).toString(), 
        "ISO-8859-1") + 
        "&pFechaFinTxt=" + 
        URLEncoder.encode(
        new StringBuilder().append(Time.convertSimpleDateToString(this.fechaFin)).toString(), 
        "ISO-8859-1");
      System.out.println(urlPDFreporte);
      URL url = new URL(urlPDFreporte);
      
      InputStream is1 = url.openStream();
      File f = stream2file(is1);
      System.out.println("Size Bytes: " + f.length());
      InputStream stream = new FileInputStream(f);
      this.streamedUrlPdf = new DefaultStreamedContent(stream, 
        "application/pdf", "ListaReporte.pdf");
      return this.streamedUrlPdf;
    }
    catch (Exception e)
    {
      e.printStackTrace();
      System.out.println("Error en getStreamedLibroVentas: " + 
        e.getMessage());
    }
    return null;
  }
  
  /* Error */
  private static File stream2fileExcel(InputStream in)
    throws java.io.IOException
  {
    // Byte code:
    //   0: ldc_w 386
    //   3: ldc_w 388
    //   6: invokestatic 390	java/io/File:createTempFile	(Ljava/lang/String;Ljava/lang/String;)Ljava/io/File;
    //   9: astore_1
    //   10: aload_1
    //   11: invokevirtual 394	java/io/File:deleteOnExit	()V
    //   14: aconst_null
    //   15: astore_2
    //   16: aconst_null
    //   17: astore_3
    //   18: new 397	java/io/FileOutputStream
    //   21: dup
    //   22: aload_1
    //   23: invokespecial 399	java/io/FileOutputStream:<init>	(Ljava/io/File;)V
    //   26: astore 4
    //   28: aload_0
    //   29: aload 4
    //   31: invokestatic 400	org/apache/commons/io/IOUtils:copy	(Ljava/io/InputStream;Ljava/io/OutputStream;)I
    //   34: pop
    //   35: aload 4
    //   37: ifnull +46 -> 83
    //   40: aload 4
    //   42: invokevirtual 406	java/io/FileOutputStream:close	()V
    //   45: goto +38 -> 83
    //   48: astore_2
    //   49: aload 4
    //   51: ifnull +8 -> 59
    //   54: aload 4
    //   56: invokevirtual 406	java/io/FileOutputStream:close	()V
    //   59: aload_2
    //   60: athrow
    //   61: astore_3
    //   62: aload_2
    //   63: ifnonnull +8 -> 71
    //   66: aload_3
    //   67: astore_2
    //   68: goto +13 -> 81
    //   71: aload_2
    //   72: aload_3
    //   73: if_acmpeq +8 -> 81
    //   76: aload_2
    //   77: aload_3
    //   78: invokevirtual 409	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
    //   81: aload_2
    //   82: athrow
    //   83: aload_1
    //   84: areturn
    // Line number table:
    //   Java source line #266	-> byte code offset #0
    //   Java source line #267	-> byte code offset #10
    //   Java source line #269	-> byte code offset #14
    //   Java source line #269	-> byte code offset #18
    //   Java source line #270	-> byte code offset #28
    //   Java source line #271	-> byte code offset #35
    //   Java source line #273	-> byte code offset #83
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	85	0	in	InputStream
    //   9	75	1	tempFile	File
    //   15	1	2	localObject1	Object
    //   48	15	2	localObject2	Object
    //   67	15	2	localObject3	Object
    //   17	1	3	localObject4	Object
    //   61	17	3	localThrowable	Throwable
    //   26	29	4	out	java.io.FileOutputStream
    // Exception table:
    //   from	to	target	type
    //   28	35	48	finally
    //   18	61	61	finally
  }
  
  /* Error */
  private static File stream2file(InputStream in)
    throws java.io.IOException
  {
    // Byte code:
    //   0: ldc_w 386
    //   3: ldc_w 420
    //   6: invokestatic 390	java/io/File:createTempFile	(Ljava/lang/String;Ljava/lang/String;)Ljava/io/File;
    //   9: astore_1
    //   10: aload_1
    //   11: invokevirtual 394	java/io/File:deleteOnExit	()V
    //   14: aconst_null
    //   15: astore_2
    //   16: aconst_null
    //   17: astore_3
    //   18: new 397	java/io/FileOutputStream
    //   21: dup
    //   22: aload_1
    //   23: invokespecial 399	java/io/FileOutputStream:<init>	(Ljava/io/File;)V
    //   26: astore 4
    //   28: aload_0
    //   29: aload 4
    //   31: invokestatic 400	org/apache/commons/io/IOUtils:copy	(Ljava/io/InputStream;Ljava/io/OutputStream;)I
    //   34: pop
    //   35: aload 4
    //   37: ifnull +46 -> 83
    //   40: aload 4
    //   42: invokevirtual 406	java/io/FileOutputStream:close	()V
    //   45: goto +38 -> 83
    //   48: astore_2
    //   49: aload 4
    //   51: ifnull +8 -> 59
    //   54: aload 4
    //   56: invokevirtual 406	java/io/FileOutputStream:close	()V
    //   59: aload_2
    //   60: athrow
    //   61: astore_3
    //   62: aload_2
    //   63: ifnonnull +8 -> 71
    //   66: aload_3
    //   67: astore_2
    //   68: goto +13 -> 81
    //   71: aload_2
    //   72: aload_3
    //   73: if_acmpeq +8 -> 81
    //   76: aload_2
    //   77: aload_3
    //   78: invokevirtual 409	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
    //   81: aload_2
    //   82: athrow
    //   83: aload_1
    //   84: areturn
    // Line number table:
    //   Java source line #278	-> byte code offset #0
    //   Java source line #279	-> byte code offset #10
    //   Java source line #281	-> byte code offset #14
    //   Java source line #281	-> byte code offset #18
    //   Java source line #282	-> byte code offset #28
    //   Java source line #283	-> byte code offset #35
    //   Java source line #285	-> byte code offset #83
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	85	0	in	InputStream
    //   9	75	1	tempFile	File
    //   15	1	2	localObject1	Object
    //   48	15	2	localObject2	Object
    //   67	15	2	localObject3	Object
    //   17	1	3	localObject4	Object
    //   61	17	3	localThrowable	Throwable
    //   26	29	4	out	java.io.FileOutputStream
    // Exception table:
    //   from	to	target	type
    //   28	35	48	finally
    //   18	61	61	finally
  }
  
  public void ArmarReportExcel()
  {
    System.out.println("Ingreso a ArmarReportExcel");
    this.urlReport = obtenerUrMovimiento();
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
    System.out
      .println("Ingreso a onRowSelectChequeraClick : " + idChequera);
    for (Cuenta i : this.listCuentas)
    {
      System.out.println(i.getBanco().getNombre());
      if (i.getId().intValue() == idChequera.intValue())
      {
        this.selectedCuentas = i;
        return;
      }
    }
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
  
  public List<Cuenta> getListCuentas()
  {
    return this.listCuentas;
  }
  
  public void setListCuentas(List<Cuenta> listCuentas)
  {
    this.listCuentas = listCuentas;
  }
  
  public List<MovimientoCuentas> getListMovimientoCuentas()
  {
    return this.listMovimientoCuentas;
  }
  
  public void setListMovimientoCuentas(List<MovimientoCuentas> listMovimientoCuentas)
  {
    this.listMovimientoCuentas = listMovimientoCuentas;
  }
  
  public String getUrlReport()
  {
    return this.urlReport;
  }
  
  public void setUrlReport(String urlReport)
  {
    this.urlReport = urlReport;
  }
  
  public void setStreamedUrlPdf(StreamedContent streamedUrlPdf)
  {
    this.streamedUrlPdf = streamedUrlPdf;
  }
}
