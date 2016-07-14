package bo.com.erp360.dao;

import bo.com.erp360.model.Chequera;
import bo.com.erp360.model.Cuenta;
import bo.com.erp360.model.Empresa;
import bo.com.erp360.model.MovimientoCuentas;
import bo.com.erp360.util.FacesUtil;
import bo.com.erp360.util.O;
import bo.com.erp360.util.P;
import bo.com.erp360.util.Q;
import bo.com.erp360.util.S;
import bo.com.erp360.util.U;
import bo.com.erp360.util.V;
import bo.com.erp360.util.W;
import java.io.PrintStream;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;

@Stateless
public class MovimientoCuentasDao
  extends DataAccessObjectJpa<MovimientoCuentas, Chequera, Cuenta, S, O, P, Q, U, V, W>
{
  public MovimientoCuentasDao()
  {
    super(MovimientoCuentas.class, Chequera.class, Cuenta.class);
  }
  
  public MovimientoCuentas registrar(MovimientoCuentas movimientoCuentas, Chequera chequera)
  {
    try
    {
      System.out.println("Ingreso a registrar ");
      beginTransaction();
      movimientoCuentas = (MovimientoCuentas)create(movimientoCuentas);
      chequera.setSecuencia(chequera.getSecuencia() + 1);
      chequera = (Chequera)updateE(chequera);
      commitTransaction();
      FacesUtil.infoMessage("Registro Correcto", "MovimientoCuentas " + 
        movimientoCuentas.getNumeroTransaccion());
      return movimientoCuentas;
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
  
  public MovimientoCuentas registrar(MovimientoCuentas movimientoCuentas)
  {
    try
    {
      System.out.println("Ingreso a registrar ");
      beginTransaction();
      movimientoCuentas = (MovimientoCuentas)create(movimientoCuentas);
      Cuenta cuenta = movimientoCuentas.getCuenta();
      
      cuenta = (Cuenta)updateR(cuenta);
      commitTransaction();
      FacesUtil.infoMessage("Registro Correcto", "MovimientoCuentas " + 
        movimientoCuentas.getNumeroTransaccion());
      return movimientoCuentas;
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
  
  public MovimientoCuentas modificar(MovimientoCuentas movimientoCuentas)
  {
    try
    {
      System.out.println("Ingreso a modificar ");
      beginTransaction();
      movimientoCuentas = (MovimientoCuentas)update(movimientoCuentas);
      commitTransaction();
      FacesUtil.infoMessage("Modificación Correcta", "MovimientoCuentas " + 
        movimientoCuentas.getNumeroTransaccion());
      return movimientoCuentas;
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
  
  public boolean eliminar(MovimientoCuentas movimientoCuentas)
  {
    try
    {
      movimientoCuentas.setEstado("RM");
      movimientoCuentas.setNumeroTransaccion(movimientoCuentas
        .getNumeroTransaccion());
      MovimientoCuentas usr = modificar(movimientoCuentas);
      
      FacesUtil.infoMessage("Eliminación Correcta", "MovimientoCuentas " + 
        movimientoCuentas.getNumeroTransaccion());
      return usr != null;
    }
    catch (Exception e)
    {
      FacesUtil.errorMessage("Error al eliminar");
      rollbackTransaction();
    }
    return false;
  }
  
  public List<MovimientoCuentas> obtenerOrdenAscPorId()
  {
    return findAscAllOrderedByParameter("id");
  }
  
  public List<MovimientoCuentas> obtenerOrdenDescPorId()
  {
    return findDescAllOrderedByParameter("id");
  }
  
  public List<MovimientoCuentas> obtenerPorCuenta(Cuenta cuenta)
  {
    return findAllActiveParameter("cuenta", cuenta.getId());
  }
  
  public List<MovimientoCuentas> obtenerMovimientosSobreFechasPorCuenta(String usuario, Cuenta cuenta, Empresa empresa, Date fechaini, Date fechafin)
  {
    return findActiveForThwoDatesAndThwoObject("usuarioRegistro", usuario, "cuenta", cuenta.getId(), "empresa", empresa.getId(), "fechaRegistro", fechaini, "fechaRegistro", fechafin);
  }
  
  public List<MovimientoCuentas> obtenerMovimientosSobreFechasPorEmpresa(String usuario, Empresa empresa, Date fechaini, Date fechafin)
  {
    return findActiveForThwoDatesAndThwoObject("usuarioRegistro", usuario, "empresa", empresa.getId(), "fechaRegistro", fechaini, "fechaRegistro", fechafin);
  }
  
  public List<MovimientoCuentas> obtenerTodoMovimientosSobreFechasPorCuenta(Cuenta cuenta, Empresa empresa, Date fechaini, Date fechafin)
  {
    return findAllActiveForThwoDatesAndThwoObject("cuenta", cuenta.getId(), "empresa", empresa.getId(), "fechaRegistro", fechaini, "fechaRegistro", fechafin);
  }
  
  public List<MovimientoCuentas> obtenerTodoMovimientosSobreFechasPorEmpresa(Empresa empresa, Date fechaini, Date fechafin)
  {
    return findAllActiveForThwoDatesAndThwoObject("empresa", empresa.getId(), "fechaRegistro", fechaini, "fechaRegistro", fechafin);
  }
  
  public List<MovimientoCuentas> obtenerCobradosMovimientosSobreFechasPorEmpresa(Empresa empresa, Date fechaini, Date fechafin)
  {
    return findAllActiveForThwoDatesAndThwoObject("empresa", empresa.getId(), "fechaRegistro", fechaini, "fechaRegistro", fechafin, "cobrado", Boolean.valueOf(true));
  }
  
  public List<MovimientoCuentas> obtenerSinCobrarMovimientosSobreFechasPorEmpresa(Empresa empresa, Date fechaini, Date fechafin)
  {
    return findAllActiveForThwoDatesAndThwoObject("empresa", empresa.getId(), "fechaRegistro", fechaini, "fechaRegistro", fechafin, "cobrado", Boolean.valueOf(false));
  }
}
