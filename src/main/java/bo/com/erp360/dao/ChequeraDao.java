package bo.com.erp360.dao;

import bo.com.erp360.model.Banco;
import bo.com.erp360.model.Chequera;
import bo.com.erp360.model.Cuenta;
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
import java.util.List;
import javax.ejb.Stateless;

@Stateless
public class ChequeraDao
  extends DataAccessObjectJpa<Chequera, E, R, S, O, P, Q, U, V, W>
{
  public ChequeraDao()
  {
    super(Chequera.class);
  }
  
  public Chequera registrar(Chequera chequera)
  {
    try
    {
      beginTransaction();
      chequera = (Chequera)create(chequera);
      commitTransaction();
      FacesUtil.infoMessage("Registro Correcto", "Chequera " + chequera.getSerie());
      return chequera;
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
  
  public Chequera modificar(Chequera chequera)
  {
    try
    {
      beginTransaction();
      chequera = (Chequera)update(chequera);
      commitTransaction();
      FacesUtil.infoMessage("Modificación Correcta", "Chequera " + chequera.getSerie());
      return chequera;
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
  
  public boolean eliminar(Chequera Chequera)
  {
    try
    {
      beginTransaction();
      Chequera.setEstado("RM");
      Chequera usr = modificar(Chequera);
      commitTransaction();
      FacesUtil.infoMessage("Eliminación Correcta", "Chequera " + Chequera.getSerie());
      return usr != null;
    }
    catch (Exception e)
    {
      FacesUtil.errorMessage("Error al eliminar");
      rollbackTransaction();
    }
    return false;
  }
  
  public List<Chequera> obtenerOrdenAscPorId()
  {
    return findAscAllOrderedByParameter("id");
  }
  
  public List<Chequera> obtenerOrdenDescPorId()
  {
    return findDescAllOrderedByParameter("id");
  }
  
  public Chequera obtenerPrimer()
  {
    try
    {
      return (Chequera)findAllActivosByMaxResultOrderByAsc(1).get(0);
    }
    catch (Exception e) {}
    return new Chequera();
  }
  
  public Chequera obtenerChequera(Chequera chequera)
  {
    try
    {
      return (Chequera)findByParameter("id", chequera.getId());
    }
    catch (Exception e) {}
    return new Chequera();
  }
  
  public List<Chequera> obtenerPorConsulta(String query)
  {
    return findAllActivosByQueryAndTwoParameter("estado", "AC", "nombre", query);
  }
  
  public List<Chequera> obtenerPorConsultaNit(String query)
  {
    return findAllActivosByQueryAndTwoParameter("estado", "AC", "nit", query);
  }
  
  public List<Chequera> obtenerPorCuenta(Cuenta cuenta)
  {
    return findAllActiveParameter("cuenta", cuenta.getId());
  }
  
  public List<Chequera> obtenerPorNombreConsulta(String query)
  {
    return findAllActivosByQuery("nombre", query);
  }
  
  public List<Chequera> obtenerPorBanco(Banco banco)
  {
    return findAllActiveParameter("banco", banco.getId());
  }
  
  public List<Chequera> obtenerChequerasActivePorCuentaYEmpresa(Empresa empresa)
  {
    return findAllActiveOtherTableAndParameter("Cuenta", "cuenta", "empresa", empresa.getId());
  }
}
