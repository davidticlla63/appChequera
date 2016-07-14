package bo.com.erp360.util;

import bo.com.erp360.data.UsuarioRepository;
import bo.com.erp360.model.Usuario;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.security.Principal;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

@WebServlet({"/ServletImageUser"})
public class ServletImageUser
  extends HttpServlet
{
  private static final long serialVersionUID = 1L;
  @Inject
  private UsuarioRepository usuarioRepository;
  
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    System.out.println("ServletImageUser --->  doGet  : " + request.getContextPath());
    byte[] imagenData = null;
    try
    {
      Usuario usuarioSession = this.usuarioRepository.findByLogin(request.getUserPrincipal().getName());
      System.out.println("usuarioSession: " + usuarioSession.getLogin());
      imagenData = usuarioSession.getFotoPerfil();
      if (imagenData == null) {
        imagenData = toByteArrayUsingJava(getImageDefaul().getStream());
      }
      try
      {
        response.setContentType("image/jpeg");
        response.setHeader("Content-Disposition", "inline; filename=imagen.jpg");
        response.setHeader("Cache-control", "public");
        ServletOutputStream sout = response.getOutputStream();
        sout.write(imagenData);
        sout.flush();
        sout.close();
      }
      catch (Exception e)
      {
        System.out.println("Error imagen: " + e.getMessage());
      }
      return;
    }
    catch (Exception e)
    {
      System.out.println("Error doGet: " + e.getMessage());
    }
  }
  
  private StreamedContent getImageDefaul()
  {
    ClassLoader classLoader = Thread.currentThread()
      .getContextClassLoader();
    InputStream stream = classLoader
      .getResourceAsStream("avatar.jpg");
    return new DefaultStreamedContent(stream, "image/jpeg");
  }
  
  public static byte[] toByteArrayUsingJava(InputStream is)
    throws IOException
  {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    for (int reads = is.read(); reads != -1; reads = is.read()) {
      baos.write(reads);
    }
    return baos.toByteArray();
  }
  
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {}
}
