package bo.com.erp360.dao;

import bo.com.erp360.model.Banco;
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
import java.io.PrintStream;
import java.util.List;
import javax.ejb.Stateless;

@Stateless
public class CuentaDao
  extends DataAccessObjectJpa<Cuenta, E, R, S, O, P, Q, U, V, W>
{
  public CuentaDao()
  {
    super(Cuenta.class);
  }
  
  public Cuenta registrar(Cuenta cuenta)
  {
    try
    {
      System.out.println("Ingreso a registrar ");
      beginTransaction();
      cuenta = (Cuenta)create(cuenta);
      
      commitTransaction();
      FacesUtil.infoMessage("Registro Correcto", 
        "Cuenta " + cuenta.getNumeroCuenta());
      return cuenta;
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
  
  public Cuenta modificar(Cuenta cuenta)
  {
    try
    {
      System.out.println("Ingreso a modificar ");
      beginTransaction();
      cuenta = (Cuenta)update(cuenta);
      
      commitTransaction();
      FacesUtil.infoMessage("Modificación Correcta", 
        "Cuenta " + cuenta.getNumeroCuenta());
      return cuenta;
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
  
  public boolean eliminar(Cuenta cuenta)
  {
    try
    {
      cuenta.setEstado("RM");
      cuenta.setNumeroCuenta(cuenta.getNumeroCuenta());
      Cuenta usr = modificar(cuenta);
      
      FacesUtil.infoMessage("Eliminación Correcta", 
        "Cuenta " + cuenta.getNumeroCuenta());
      return usr != null;
    }
    catch (Exception e)
    {
      FacesUtil.errorMessage("Error al eliminar");
      rollbackTransaction();
    }
    return false;
  }
  
  public List<Cuenta> findAllActiveParameterOrderedForId(Empresa empresa)
  {
    return findAllActiveParameterOrderedForId("empresa", empresa.getId());
  }
  
  public List<Cuenta> obtenerOrdenAscPorId()
  {
    return findAscAllOrderedByParameter("id");
  }
  
  public List<Cuenta> obtenerOrdenDescPorId()
  {
    return findDescAllOrderedByParameter("id");
  }
  
  public Cuenta obtenerPrimer()
  {
    try
    {
      return (Cuenta)findAllActivosByMaxResultOrderByAsc(1).get(0);
    }
    catch (Exception e) {}
    return new Cuenta();
  }
  
  public Cuenta obtenerCuenta(Cuenta chequera)
  {
    try
    {
      return (Cuenta)findByParameter("id", chequera.getId());
    }
    catch (Exception e) {}
    return new Cuenta();
  }
  
  public List<Cuenta> obtenerPorBanco(Banco banco)
  {
    return findAllActiveParameter("banco", banco.getId());
  }
  
  public List<Cuenta> obtenerPorCuenta(Cuenta cuenta)
  {
    return findAllActiveParameter("cuenta", cuenta.getId());
  }
  
  public List<Cuenta> obtenerPorConsulta(String query)
  {
    return findAllActivosByQueryAndTwoParameter("estado", "AC", "nombre", 
      query);
  }
  
  public List<Cuenta> obtenerPorConsultaNit(String query)
  {
    return findAllActivosByQueryAndTwoParameter("estado", "AC", "nit", 
      query);
  }
}
