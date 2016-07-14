package bo.com.erp360.data;

import bo.com.erp360.model.Roles;
import bo.com.erp360.model.Usuario;
import java.io.PrintStream;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@Stateless
public class UsuarioRepository
{
  @Inject
  private EntityManager em;
  
  public Usuario findById(Long id)
  {
    return (Usuario)this.em.find(Usuario.class, id);
  }
  
  public Usuario findById(Integer id)
  {
    return (Usuario)this.em.find(Usuario.class, id);
  }
  
  public Usuario findByLogin(String name)
  {
    try
    {
      CriteriaBuilder cb = this.em.getCriteriaBuilder();
      CriteriaQuery<Usuario> criteria = cb.createQuery(Usuario.class);
      Root<Usuario> user = criteria.from(Usuario.class);
      criteria.select(user).where(cb.equal(user.get("login"), name));
      return (Usuario)this.em.createQuery(criteria).getSingleResult();
    }
    catch (Exception e)
    {
      System.out.println("ERROR :" + e.getMessage());
    }
    return null;
  }
  
  public Usuario findByLogin(String login, String password, Roles rol)
  {
    try
    {
      CriteriaBuilder cb = this.em.getCriteriaBuilder();
      CriteriaQuery<Usuario> criteria = cb.createQuery(Usuario.class);
      Root<Usuario> user = criteria.from(Usuario.class);
      criteria.select(user).where(new Predicate[] { cb.equal(user.get("login"), login), cb.equal(user.get("password"), password) });
      return (Usuario)this.em.createQuery(criteria).getSingleResult();
    }
    catch (Exception e) {}
    return null;
  }
  
  public List<Usuario> findAllOrderedByID()
  {
    String query = "select em from Usuario em where em.state='AC' or em.state='IN' or em.state='SU' order by em.id desc";
    System.out.println("Query Usuario: " + query);
    return this.em.createQuery(query).getResultList();
  }
  
  public Usuario findByLogin(String login, String password)
  {
    try
    {
      CriteriaBuilder cb = this.em.getCriteriaBuilder();
      CriteriaQuery<Usuario> criteria = cb.createQuery(Usuario.class);
      Root<Usuario> user = criteria.from(Usuario.class);
      criteria.select(user).where(new Predicate[] { cb.equal(user.get("login"), login), cb.equal(user.get("password"), password) });
      return (Usuario)this.em.createQuery(criteria).getSingleResult();
    }
    catch (Exception e)
    {
      System.out.println("usuario no valido error: " + e.getMessage());
    }
    return null;
  }
  
  public Usuario findByEmail(String email)
  {
    CriteriaBuilder cb = this.em.getCriteriaBuilder();
    CriteriaQuery<Usuario> criteria = cb.createQuery(Usuario.class);
    Root<Usuario> user = criteria.from(Usuario.class);
    criteria.select(user).where(cb.equal(user.get("email"), email));
    return (Usuario)this.em.createQuery(criteria).getSingleResult();
  }
  
  public List<Usuario> findAllOrderedByLogin()
  {
    CriteriaBuilder cb = this.em.getCriteriaBuilder();
    CriteriaQuery<Usuario> criteria = cb.createQuery(Usuario.class);
    Root<Usuario> user = criteria.from(Usuario.class);
    criteria.select(user).orderBy(new Order[] { cb.asc(user.get("login")) });
    return this.em.createQuery(criteria).getResultList();
  }
  
  public List<Roles> findAllRol()
  {
    CriteriaBuilder cb = this.em.getCriteriaBuilder();
    CriteriaQuery<Roles> criteria = cb.createQuery(Roles.class);
    Root<Roles> user = criteria.from(Roles.class);
    criteria.select(user).orderBy(new Order[] { cb.asc(user.get("login")) });
    return this.em.createQuery(criteria).getResultList();
  }
}
