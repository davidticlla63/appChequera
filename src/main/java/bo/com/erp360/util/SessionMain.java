package bo.com.erp360.util;

import bo.com.erp360.data.EmpresaRepository;
import bo.com.erp360.data.UsuarioRepository;
import bo.com.erp360.model.Empresa;
import bo.com.erp360.model.Usuario;
import bo.com.erp360.service.UsuarioRegistration;
import java.io.PrintStream;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.primefaces.model.UploadedFile;

@Named
@SessionScoped
public class SessionMain
  implements Serializable
{
  @Inject
  private FacesContext facesContext;
  private Logger log = Logger.getLogger(getClass());
  @Inject
  private UsuarioRepository usuarioRepository;
  @Inject
  private EmpresaRepository empresaRepository;
  @Inject
  private UsuarioRegistration usuarioRegistration;
  private Usuario usuarioLogin;
  private Empresa empresaLogin;
  private UploadedFile file;
  private String pathFisico;
  private String urlPath;
  private boolean modificar = false;
  
  @PostConstruct
  public void initSessionMain()
  {
    System.out.println("----- initSessionMain() --------");
    this.usuarioLogin = null;
    this.empresaLogin = null;
    
    this.pathFisico = "";
    this.urlPath = "";
  }
  
  public Usuario validarUsuario_(String username, String password)
  {
    return this.usuarioRepository.findByLogin(username, password);
  }
  
  public boolean tienePermisoPagina(String pagina)
  {
    if ((pagina.equals("profile.xhtml")) || (pagina.equals("dashboard.xhtml")) || 
      (pagina.equals("manual_usuario.xhtml")) || 
      (pagina.equals("orden_servicio_index.xhtml")) || 
      (pagina.equals("documento.xhtml")) || 
      (pagina.equals("cliente.xhtml")) || 
      (pagina.equals("orden_servicio.xhtml")) || 
      (pagina.equals("facturacion_index.xhtml")) || 
      (pagina.equals("factura.xhtml")) || 
      (pagina.equals("factura_list.xhtml")) || 
      (pagina.equals("factura_orden_servicio.xhtml")) || 
      (pagina.equals("certificacion.xhtml")) || 
      (pagina.equals("tipo_hoja.xhtml")) || 
      (pagina.equals("directorio.xhtml"))) {
      return true;
    }
    return false;
  }
  
  public boolean tienePermisoPaginaAccion(String pagina, String accion)
  {
    return false;
  }
  
  public String getParameterRequest(String name)
  {
    HttpServletRequest request = (HttpServletRequest)this.facesContext
      .getExternalContext().getRequest();
    return request.getParameter(name);
  }
  
  public void setAttributeSession(String key, String value)
  {
    try
    {
      HttpSession session = (HttpSession)
        FacesContext.getCurrentInstance().getExternalContext()
        .getSession(false);
      session.setAttribute(key, value);
    }
    catch (Exception e)
    {
      this.log.error("setAttributeSession() ERROR: " + e.getMessage());
    }
  }
  
  public String getAttributeSession(String key)
  {
    try
    {
      HttpSession request = (HttpSession)
        FacesContext.getCurrentInstance().getExternalContext()
        .getSession(false);
      return request.getAttribute(key) != null ? 
        (String)request.getAttribute(key) : null;
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return null;
  }
  
  public boolean removeAttributeSession(String key)
  {
    try
    {
      HttpSession request = (HttpSession)
        FacesContext.getCurrentInstance().getExternalContext()
        .getSession(false);
      request.removeAttribute(key);
      return true;
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return false;
  }
  
  public Usuario getUsuarioLogin()
  {
    return this.usuarioLogin;
  }
  
  public void setUsuarioLogin(Usuario usuarioLogin)
  {
    this.usuarioLogin = usuarioLogin;
  }
  
  public void actualizarrUsuario()
  {
    this.usuarioRegistration.update(this.usuarioLogin);
  }
  
  public Empresa getEmpresaLogin()
  {
    if (this.empresaLogin == null) {
      try
      {
        this.empresaLogin = this.empresaRepository.findById(1);
      }
      catch (Exception e)
      {
        this.empresaLogin = null;
        this.log.error("getEmpresaLoggin() ERROR: " + e.getMessage());
      }
    }
    return this.empresaLogin;
  }
  
  public void setEmpresaLogin(Empresa empresaLogin)
  {
    this.empresaLogin = empresaLogin;
  }
  
  public UploadedFile getFile()
  {
    System.out.println("getFile " + this.file);
    return this.file;
  }
  
  public void setFile(UploadedFile file)
  {
    this.file = file;
    
    System.out.println("setFile " + file);
  }
  
  public boolean isModificar()
  {
    return this.modificar;
  }
  
  public void setModificar(boolean modificar)
  {
    this.modificar = modificar;
  }
  
  public String getPathFisico()
  {
    if (this.pathFisico.trim().isEmpty())
    {
      ServletContext servletContext = (ServletContext)
        FacesContext.getCurrentInstance().getExternalContext().getContext();
      this.pathFisico = servletContext
        .getRealPath("/resources/temp/");
      return this.pathFisico;
    }
    return this.pathFisico;
  }
  
  public void setPathFisico(String pathFisico)
  {
    this.pathFisico = pathFisico;
  }
  
  public String getUrlPath()
  {
    if (this.urlPath.trim().isEmpty())
    {
      HttpServletRequest request = (HttpServletRequest)this.facesContext
        .getExternalContext().getRequest();
      String urlPath2 = request.getRequestURL().toString();
      this.urlPath = 
      
        (urlPath2.substring(0, urlPath2.length() - request.getRequestURI().length()) + request.getContextPath() + "/");
      return this.urlPath;
    }
    return this.urlPath;
  }
  
  public void setUrlPath(String urlPath)
  {
    this.urlPath = urlPath;
  }
}
