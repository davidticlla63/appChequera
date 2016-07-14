package bo.com.erp360.report;

import bo.com.erp360.data.EmpresaRepository;
import bo.com.erp360.data.UsuarioRepository;
import bo.com.erp360.model.Empresa;
import bo.com.erp360.model.Usuario;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

@WebServlet({"/ServletImageLogo"})
public class ServletImageLogo
  extends HttpServlet
{
  private static final long serialVersionUID = 1L;
  @Inject
  private UsuarioRepository usuarioRepository;
  @Inject
  private EmpresaRepository empresaRepository;
  
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    String id = "";
    String type = "";
    id = request.getParameter("id");
    type = request.getParameter("type");
    System.out.println("ServletImageLogo --->  doGet  : " + request.getContextPath() + "  |  id:" + id + "- type:" + type);
    byte[] imagenData = null;
    try
    {
      if (type.equals("EMPRESA"))
      {
        Empresa empresa = this.empresaRepository.findById(Integer.valueOf(id).intValue());
        imagenData = empresa.getFotoPerfil();
      }
      else
      {
        Usuario usuario = this.usuarioRepository.findById(Integer.valueOf(id));
        imagenData = usuario.getFotoPerfil();
      }
      if (imagenData == null) {
        if (type.equals("EMPRESA")) {
          imagenData = toByteArrayUsingJava(getImageDefaul("logo.png", "image/jpeg").getStream());
        } else {
          imagenData = toByteArrayUsingJava(getImageDefaul("avatar.png", "image/jpg").getStream());
        }
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
  
  private StreamedContent getImageDefaul(String namePhoto, String type)
  {
    ClassLoader classLoader = Thread.currentThread()
      .getContextClassLoader();
    InputStream stream = classLoader
      .getResourceAsStream(namePhoto);
    return new DefaultStreamedContent(stream, type);
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
