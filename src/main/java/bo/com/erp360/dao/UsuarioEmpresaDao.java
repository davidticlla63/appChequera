package bo.com.erp360.dao;

import bo.com.erp360.model.UsuarioEmpresa;
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
import javax.ejb.Stateless;

@Stateless
public class UsuarioEmpresaDao
  extends DataAccessObjectJpa<UsuarioEmpresa, E, R, S, O, P, Q, U, V, W>
{
  public UsuarioEmpresaDao()
  {
    super(UsuarioEmpresa.class);
  }
  
  public UsuarioEmpresa registrar(UsuarioEmpresa usuarioEmpresa)
  {
    try
    {
      beginTransaction();
      usuarioEmpresa = (UsuarioEmpresa)create(usuarioEmpresa);
      commitTransaction();
      FacesUtil.infoMessage("Registro Correcto", "nivel " + usuarioEmpresa.getId());
      return usuarioEmpresa;
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
}
