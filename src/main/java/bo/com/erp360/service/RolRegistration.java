package bo.com.erp360.service;

import bo.com.erp360.model.Roles;
import javax.ejb.Stateless;

@Stateless
public class RolRegistration
  extends DataAccessService<Roles>
{
  public RolRegistration()
  {
    super(Roles.class);
  }
}
