package bo.com.erp360.service;

import bo.com.erp360.model.UsuarioRol;
import javax.ejb.Stateless;

@Stateless
public class UsuarioRolRegistration
  extends DataAccessService<UsuarioRol>
{
  public UsuarioRolRegistration()
  {
    super(UsuarioRol.class);
  }
}
