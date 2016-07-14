package bo.com.erp360.controller;

import bo.com.erp360.data.UsuarioRolRepository;
import bo.com.erp360.model.Empresa;
import bo.com.erp360.model.Usuario;
import bo.com.erp360.model.UsuarioRol;
import bo.com.erp360.service.EmpresaRegistration;
import bo.com.erp360.service.UsuarioRegistration;
import bo.com.erp360.util.DateUtility;
import bo.com.erp360.util.FacesUtil;
import bo.com.erp360.util.SessionMain;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.Serializable;
import java.security.Principal;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

@Named("loginController")
@SessionScoped
public class LoginController
  implements Serializable
{
  @Inject
  private SessionMain sessionMain;
  @Inject
  private UsuarioRolRepository usuerRolRepository;
  private String username;
  private String password;
  private StreamedContent fotoPerfilTemp;
  private UploadedFile file;
  private UploadedFile fileLogo;
  private boolean modificar = false;
  @Inject
  private UsuarioRegistration usuarioRegistration;
  @Inject
  private EmpresaRegistration empresaRegistration;
  private Usuario usuarioSession;
  
  @PostConstruct
  public void initNewLogin()
  {
    System.out.println("initNewLogin()");
    this.username = "";
    this.password = "";
  }
  
  public void login()
  {
    System.out.println(" ------- login() ----");
    if ((this.username.isEmpty()) || (this.password.isEmpty()))
    {
      System.out.println("login() -> Usuario o Password sin datos.");
      FacesUtil.errorMessage("Ingresar Usuario y Contraseña.");
      return;
    }
    FacesContext context = FacesContext.getCurrentInstance();
    HttpServletRequest request = (HttpServletRequest)context.getExternalContext().getRequest();
    Usuario usuarioSession = this.sessionMain.validarUsuario_(this.username, this.password);
    if (usuarioSession != null)
    {
      if (usuarioSession.getState().equals("RM"))
      {
        FacesUtil.infoMessage("Verificar!", "Usuario o contraseña incorrecta");
        return;
      }
      if (usuarioSession.getState().equals("IN"))
      {
        FacesUtil.infoMessage("Usuario Inactivo", "");
        return;
      }
      try
      {
        if (request.getUserPrincipal() != null) {
          logout();
        }
        request.login(this.username, this.password);
        load(usuarioSession);
        sessionEmpresa();
        try
        {
          context.getExternalContext().redirect(request.getContextPath() + "/pages/dashboard.xhtml");
        }
        catch (IOException ex)
        {
          context.addMessage(null, new FacesMessage("Error!", "Ocurrio un Error!"));
        }
        System.out.println("login() -> No existe Usuario");
      }
      catch (ServletException e)
      {
        System.out.println("login() -> " + e.toString());
        context.addMessage(null, new FacesMessage("Verificar!", "Usuario o contraseña incorrecta"));
      }
    }
    else
    {
      FacesUtil.errorMessage("Revisar Usuario o Contraseña.");
    }
  }
  
  private void sessionEmpresa()
  {
    HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);
    session.setAttribute("empresa", "ERP360 SRL");
  }
  
  private void load(Usuario usuario)
  {
    this.usuarioSession = usuario;
    UsuarioRol usuarioRolV1 = this.usuerRolRepository.findByUsuario(usuario);
    this.sessionMain.setUsuarioLogin(usuario);
    
    setImageUserSession();
    setImageLogo();
  }
  
  public void logout()
  {
    FacesContext context = FacesContext.getCurrentInstance();
    HttpServletRequest request = (HttpServletRequest)context.getExternalContext().getRequest();
    HttpSession session = request.getSession(false);
    System.out.println("User ({0}) Cerrando sesion #" + DateUtility.getCurrentDateTime() + " user" + request.getUserPrincipal().getName());
    if (session != null)
    {
      session.invalidate();
      try
      {
        context.getExternalContext().redirect(request.getContextPath() + "/login.xhtml");
      }
      catch (IOException e)
      {
        System.out.println("logout() -> " + e.toString());
      }
    }
  }
  
  public void verificarTipoCambio()
  {
    System.out.println("verificarTipoCambio()");
    RequestContext.getCurrentInstance().execute("stickyTipoCambio()");
    int test = 0;
  }
  
  private static byte[] toByteArrayUsingJava(InputStream is)
    throws IOException
  {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    for (int reads = is.read(); reads != -1; reads = is.read()) {
      baos.write(reads);
    }
    return baos.toByteArray();
  }
  
  public StreamedContent getImageUserSession()
  {
    String mimeType = "image/jpg";
    
    InputStream is = null;
    try
    {
      HttpSession request = (HttpSession)
        FacesContext.getCurrentInstance().getExternalContext()
        .getSession(false);
      byte[] image = (byte[])request.getAttribute("imageUser");
      is = new ByteArrayInputStream(image);
      return new DefaultStreamedContent(new ByteArrayInputStream(
        toByteArrayUsingJava(is)), mimeType);
    }
    catch (Exception e)
    {
      System.out.println("getEmpresaSession() -> error : " + 
        e.getMessage());
    }
    return null;
  }
  
  public void setImageUserSession()
  {
    try
    {
      HttpSession session = (HttpSession)
        FacesContext.getCurrentInstance().getExternalContext()
        .getSession(false);
      byte[] image = this.sessionMain.getUsuarioLogin().getFotoPerfil();
      if (image == null) {
        image = toByteArrayUsingJava(getImageDefault().getStream());
      }
      session.setAttribute("imageUser", image);
    }
    catch (Exception e)
    {
      System.out.println("setImageUserSession() - Error: " + 
        e.getMessage());
    }
  }
  
  private StreamedContent getImageDefault()
  {
    ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
    InputStream stream = classLoader.getResourceAsStream("avatar.png");
    return new DefaultStreamedContent(stream, "image/png");
  }
  
  private StreamedContent getImageDefaultLogo()
  {
    ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
    InputStream stream = classLoader.getResourceAsStream("logo.png");
    return new DefaultStreamedContent(stream, "image/png");
  }
  
  public void upload()
  {
    setModificar(true);
    System.out.println("upload()  file:" + this.file);
    if (this.file != null)
    {
      this.usuarioSession.setFotoPerfil(this.file.getContents());
      this.usuarioSession.setPesoFoto(this.file.getContents().length);
      this.usuarioRegistration.update(this.usuarioSession);
      setImageUserSession2();
      System.out.println("upload()  OK");
    }
  }
  
  public void setImageUserSession2()
  {
    try
    {
      HttpSession session = (HttpSession)
        FacesContext.getCurrentInstance().getExternalContext()
        .getSession(false);
      byte[] image = this.usuarioSession.getFotoPerfil();
      session.setAttribute("imageUser", image);
    }
    catch (Exception e)
    {
      System.out.println("setImageUserSession() - Error: " + 
        e.getMessage());
    }
  }
  
  public void uploadLogo()
  {
    setModificar(true);
    System.out.println("upload()  fileLogo:" + this.fileLogo);
    if (this.fileLogo != null)
    {
      Empresa empresa = this.sessionMain.getEmpresaLogin();
      empresa.setFotoPerfil(this.fileLogo.getContents());
      empresa.setPesoFoto(this.fileLogo.getContents().length);
      empresa = (Empresa)this.empresaRegistration.update(empresa);
      this.sessionMain.setEmpresaLogin(empresa);
      setImageLogo2();
      System.out.println("uploadLogo()  OK");
    }
  }
  
  public void setImageLogo()
  {
    try
    {
      HttpSession session = (HttpSession)
        FacesContext.getCurrentInstance().getExternalContext()
        .getSession(false);
      byte[] image = this.sessionMain.getEmpresaLogin().getFotoPerfil();
      if (image == null) {
        image = toByteArrayUsingJava(getImageDefaultLogo().getStream());
      }
      session.setAttribute("imageLogo", image);
    }
    catch (Exception e)
    {
      System.out.println("setImageUserSession() - Error: " + 
        e.getMessage());
    }
  }
  
  public void setImageLogo2()
  {
    try
    {
      HttpSession session = (HttpSession)
        FacesContext.getCurrentInstance().getExternalContext()
        .getSession(false);
      byte[] image = this.sessionMain.getEmpresaLogin().getFotoPerfil();
      session.setAttribute("imageLogo", image);
    }
    catch (Exception e)
    {
      System.out.println("setImageUserSession() - Error: " + 
        e.getMessage());
    }
  }
  
  public StreamedContent getImageLogo()
  {
    String mimeType = "image/png";
    
    InputStream is = null;
    try
    {
      HttpSession request = (HttpSession)
        FacesContext.getCurrentInstance().getExternalContext()
        .getSession(false);
      byte[] image = (byte[])request.getAttribute("imageLogo");
      is = new ByteArrayInputStream(image);
      return new DefaultStreamedContent(new ByteArrayInputStream(
        toByteArrayUsingJava(is)), mimeType);
    }
    catch (Exception e)
    {
      System.out.println("getEmpresaSession() -> error : " + 
        e.getMessage());
    }
    return null;
  }
  
  private void cargarPermisosUserSession()
  {
    try
    {
      HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);
      UsuarioRol localUsuarioRol = this.usuerRolRepository.findByUsuario(this.usuarioSession);
    }
    catch (Exception localException) {}
  }
  
  public void permisoValidado(String permiso)
  {
    try
    {
      System.out.println("permisoValidado(" + permiso + ")");
      FacesContext context = FacesContext.getCurrentInstance();
      HttpServletRequest request1 = (HttpServletRequest)context
        .getExternalContext().getRequest();
      HttpSession request = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);
      String permisoAux = request.getAttribute(permiso) != null ? (String)request.getAttribute(permiso) : "IN";
      if (!permisoAux.equals("AC")) {
        FacesContext.getCurrentInstance().getExternalContext().redirect(request1.getContextPath() + "/error403.xhtml");
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }
  
  public String getUsername()
  {
    return this.username;
  }
  
  public void setUsername(String username)
  {
    this.username = username;
  }
  
  public String getPassword()
  {
    return this.password;
  }
  
  public void setPassword(String password)
  {
    this.password = password;
  }
  
  public StreamedContent getFotoPerfilTemp()
  {
    return this.fotoPerfilTemp;
  }
  
  public void setFotoPerfilTemp(StreamedContent fotoPerfilTemp)
  {
    this.fotoPerfilTemp = fotoPerfilTemp;
  }
  
  public UploadedFile getFile()
  {
    return this.file;
  }
  
  public void setFile(UploadedFile file)
  {
    this.file = file;
  }
  
  public boolean isModificar()
  {
    return this.modificar;
  }
  
  public void setModificar(boolean modificar)
  {
    this.modificar = modificar;
  }
  
  public UploadedFile getFileLogo()
  {
    return this.fileLogo;
  }
  
  public void setFileLogo(UploadedFile fileLogo)
  {
    this.fileLogo = fileLogo;
  }
}
