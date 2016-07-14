package bo.com.erp360.data;

import bo.com.erp360.model.Roles;
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
import javax.persistence.criteria.Root;

@Stateless
public class RolesRepository
{
  @Inject
  private EntityManager em;
  
  public Roles findById(int id)
  {
    return (Roles)this.em.find(Roles.class, Integer.valueOf(id));
  }
  
  public List<Roles> findAllOrderByAsc()
  {
    String query = "select em from Roles em where em.estado='AC' or em.estado='IN' or em.estado='SU' order by em.id asc";
    System.out.println("Query Roles: " + query);
    return this.em.createQuery(query).getResultList();
  }
  
  public Roles findRolByNombre(String name)
  {
    try
    {
      String query = "select em from Roles em  where em.nombre='" + name + "'";
      System.out.println("Query Roles: " + query);
      return (Roles)this.em.createQuery(query).getSingleResult();
    }
    catch (Exception e)
    {
      System.out.println("ERROR :" + e.getMessage());
    }
    return null;
  }
  
  public List<Roles> findAllOrderedByID()
  {
    CriteriaBuilder cb = this.em.getCriteriaBuilder();
    CriteriaQuery<Roles> criteria = cb.createQuery(Roles.class);
    Root<Roles> rol = criteria.from(Roles.class);
    criteria.select(rol).orderBy(new Order[] { cb.asc(rol.get("id")) });
    return this.em.createQuery(criteria).getResultList();
  }
}
