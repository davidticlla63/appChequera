package bo.com.erp360.data;

import bo.com.erp360.model.Empresa;
import bo.com.erp360.model.Usuario;
import java.util.List;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Stateless
public class EmpresaRepository
{
  @Inject
  private EntityManager em;
  @Inject
  private Logger log;
  
  public Empresa findById(int id)
  {
    return (Empresa)this.em.find(Empresa.class, Integer.valueOf(id));
  }
  
  public List<Empresa> findAllOrderedByID()
  {
    String query = "select em from Empresa em where em.estado='AC' or ser.estado='IN' order by em.id desc";
    this.log.info("Query Empresa: " + query);
    return this.em.createQuery(query).getResultList();
  }
  
  public Empresa findByRazonSocial(String razonSocial)
  {
    String query = "select em from Empresa em where em.razonSocial='" + razonSocial + "'";
    this.log.info("Query Empresa: " + query);
    return (Empresa)this.em.createQuery(query).getSingleResult();
  }
  
  public Empresa findByNIT(String NIT)
  {
    String query = "select em from Empresa em where em.NIT='" + NIT + "'";
    this.log.info("Query Empresa: " + query);
    return (Empresa)this.em.createQuery(query).getSingleResult();
  }
  
  public List<Empresa> findAll()
  {
    CriteriaBuilder cb = this.em.getCriteriaBuilder();
    CriteriaQuery<Empresa> criteria = cb.createQuery(Empresa.class);
    Root<Empresa> company = criteria.from(Empresa.class);
    criteria.select(company);
    return this.em.createQuery(criteria).getResultList();
  }
  
  public List<Empresa> findAllByUsuario(Usuario u)
  {
    String query = "select em from Empresa em ,UsuarioEmpresa ue where (em.estado='AC' or em.estado='IN') and ue.usuario.id=" + u.getId() + 
      " and em.id=ue.empresa.id  order by em.id desc";
    this.log.info("Query Empresa: " + query);
    return this.em.createQuery(query).getResultList();
  }
  
  public List<Empresa> findAllActivasByUsuario(Usuario u)
  {
    String query = "select em from Empresa em ,UsuarioEmpresa ue where em.estado='AC' and ue.usuario.id=" + u.getId() + 
      " and em.id=ue.empresa.id  order by em.id desc";
    this.log.info("Query Empresa: " + query);
    return this.em.createQuery(query).getResultList();
  }
  
  public Empresa findByUsuarioEmpresa(Usuario u, String nombreEmpresa)
  {
    String query = "select em from Empresa em ,UsuarioEmpresa ue where ue.usuario.id=" + u.getId() + 
      " and em.id=ue.empresa.id and em.razonSocial='" + nombreEmpresa + "' order by em.id desc";
    this.log.info("Query Empresa: " + query);
    return (Empresa)this.em.createQuery(query).getSingleResult();
  }
}
