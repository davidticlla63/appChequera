package bo.com.erp360.dao;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

@Stateless
public class FactoryDao
{
  @Inject
  private EntityManager em;
  private EntityTransaction tx = null;
  
  public void beginTransaction()
  {
    this.tx = this.em.getTransaction();
    if (!this.tx.isActive()) {
      this.tx.begin();
    }
  }
  
  public void commitTransaction()
  {
    this.tx.commit();
  }
  
  public void rollbackTransaction()
  {
    if (!this.tx.isActive()) {
      this.tx.rollback();
    }
  }
  
  public EntityManager getEm()
  {
    return this.em;
  }
  
  public void setEm(EntityManager em)
  {
    this.em = em;
  }
  
  public EntityTransaction getTx()
  {
    return this.tx;
  }
  
  public void setTx(EntityTransaction tx)
  {
    this.tx = tx;
  }
}
