package bo.com.erp360.service;

import bo.com.erp360.model.Empresa;
import javax.ejb.Stateless;

@Stateless
public class EmpresaRegistration
  extends DataAccessService<Empresa>
{
  public EmpresaRegistration()
  {
    super(Empresa.class);
  }
}
