package bo.com.erp360.dao;

import bo.com.erp360.model.Usuario;
import bo.com.erp360.util.E;
import bo.com.erp360.util.FacesUtil;
import bo.com.erp360.util.O;
import bo.com.erp360.util.P;
import bo.com.erp360.util.Q;
import bo.com.erp360.util.R;
import bo.com.erp360.util.S;
import bo.com.erp360.util.U;
import bo.com.erp360.util.V;
import bo.com.erp360.util.W;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;

@Stateless
public class UsuarioDao
  extends DataAccessObjectJpa<Usuario, E, R, S, O, P, Q, U, V, W>
{
  public UsuarioDao()
  {
    super(Usuario.class);
  }
  
  public Usuario registrarUsuario(Usuario usuario)
  {
    try
    {
      beginTransaction();
      usuario = (Usuario)create(usuario);
      commitTransaction();
      FacesUtil.infoMessage("Registro Correcto", "Usuario " + usuario.getNombre());
      return usuario;
    }
    catch (Exception e)
    {
      String cause = e.getMessage();
      if (cause.contains("org.hibernate.exception.ConstraintViolationException: could not execute statement")) {
        FacesUtil.errorMessage("Ya existe un registro igual.");
      } else {
        FacesUtil.errorMessage("Error al registrar");
      }
      rollbackTransaction();
    }
    return null;
  }
  
  public Usuario modificarUsuario(Usuario usuario)
  {
    try
    {
      beginTransaction();
      usuario = (Usuario)update(usuario);
      commitTransaction();
      FacesUtil.infoMessage("Modificación Correcta", "Usuario " + usuario.getNombre());
      return usuario;
    }
    catch (Exception e)
    {
      String cause = e.getMessage();
      if (cause.contains("org.hibernate.exception.ConstraintViolationException: could not execute statement")) {
        FacesUtil.errorMessage("Ya existe un registro igual.");
      } else {
        FacesUtil.errorMessage("Error al modificar");
      }
      rollbackTransaction();
    }
    return null;
  }
  
  public boolean eliminarUsuario(Usuario usuario)
  {
    try
    {
      beginTransaction();
      usuario.setLogin(new Date() + "|" + usuario.getLogin());
      Usuario usr = modificarUsuario(usuario);
      commitTransaction();
      FacesUtil.infoMessage("Eliminación Correcta", "Usuario " + usuario.getNombre());
      return usr != null;
    }
    catch (Exception e)
    {
      FacesUtil.errorMessage("Error al eliminar");
      rollbackTransaction();
    }
    return false;
  }
  
  public List<Usuario> obtenerUsuarioOrdenAscPorId()
  {
    return findAscAllOrderedByParameter("id");
  }
  
  public List<Usuario> obtenerUsuarioOrdenDescPorId()
  {
    return findDescAllOrderedByParameter("id");
  }
}
