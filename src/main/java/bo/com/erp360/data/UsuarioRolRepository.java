package bo.com.erp360.data;

import bo.com.erp360.model.Usuario;
import bo.com.erp360.model.UsuarioRol;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

@Stateless
public class UsuarioRolRepository
{
  @Inject
  private EntityManager em;
  
  public UsuarioRol findById(Long id)
  {
    return (UsuarioRol)this.em.find(UsuarioRol.class, id);
  }
  
  public List<UsuarioRol> findById(Integer id)
  {
    try
    {
      String query = "select em from UsuarioRol em where em.user.id='" + id + "'";
      return this.em.createQuery(query).getResultList();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return null;
  }
  
  public UsuarioRol findByUsuario(Usuario usuario)
  {
    String query = "select em from UsuarioRol em where em.usuario.id=" + usuario.getId();
    return (UsuarioRol)this.em.createQuery(query).getSingleResult();
  }
  
  public List<UsuarioRol> findAllOrderedByNombre()
  {
    String query = "select em from UsuarioRol em where (em.estado ='AC' or em.estado='IN') order by em.id desc";
    return this.em.createQuery(query).getResultList();
  }
}
