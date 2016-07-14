package bo.com.erp360.service;

import bo.com.erp360.model.Usuario;
import javax.ejb.Stateless;

@Stateless
public class UsuarioRegistration
  extends DataAccessService<Usuario>
{
  public UsuarioRegistration()
  {
    super(Usuario.class);
  }
}
