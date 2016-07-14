package bo.com.erp360.dao;

import bo.com.erp360.model.Banco;
import bo.com.erp360.model.Empresa;
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
public class BancoDao
  extends DataAccessObjectJpa<Banco, E, R, S, O, P, Q, U, V, W>
{
  public BancoDao()
  {
    super(Banco.class);
  }
  
  public Banco registrar(Banco usuario)
  {
    try
    {
      beginTransaction();
      usuario = (Banco)create(usuario);
      commitTransaction();
      FacesUtil.infoMessage("Registro Correcto", "Banco " + usuario.getNombre());
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
  
  public Banco modificar(Banco usuario)
  {
    try
    {
      beginTransaction();
      usuario = (Banco)update(usuario);
      commitTransaction();
      FacesUtil.infoMessage("Modificación Correcta", "Banco " + usuario.getNombre());
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
  
  public boolean eliminar(Banco banco)
  {
    try
    {
      beginTransaction();
      banco.setEstado("RM");
      banco.setNombre(new Date() + "|" + banco.getNombre());
      Banco usr = modificar(banco);
      commitTransaction();
      FacesUtil.infoMessage("Eliminación Correcta", "Banco " + banco.getNombre());
      return usr != null;
    }
    catch (Exception e)
    {
      FacesUtil.errorMessage("Error al eliminar");
      rollbackTransaction();
    }
    return false;
  }
  
  public List<Banco> obtenerOrdenAscPorId()
  {
    return findAscAllOrderedByParameter("id");
  }
  
  public List<Banco> obtenerOrdenDescPorId()
  {
    return findDescAllOrderedByParameter("id");
  }
  
  public Banco obtenerPrimer()
  {
    try
    {
      return (Banco)findAllActivosByMaxResultOrderByAsc(1).get(0);
    }
    catch (Exception e) {}
    return new Banco();
  }
  
  public List<Banco> obtenerPorConsulta(String query)
  {
    return findAllActivosByQueryAndTwoParameter("estado", "AC", "nombre", query);
  }
  
  public List<Banco> obtenerPorConsultaNit(String query)
  {
    return findAllActivosByQueryAndTwoParameter("estado", "AC", "nit", query);
  }
  
  public List<Banco> obtenerPorEmpresa(Empresa empresa)
  {
    return findAllActiveParameter("empresa", empresa.getId());
  }
  
  public List<Banco> obtenerPorNombreConsulta(String query)
  {
    return findAllActivosByQuery("nombre", query);
  }
}
