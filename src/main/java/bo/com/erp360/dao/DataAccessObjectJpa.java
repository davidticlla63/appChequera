package bo.com.erp360.dao;

import bo.com.erp360.util.Time;
import java.io.PrintStream;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@Stateless
public abstract class DataAccessObjectJpa<T, E, R, S, O, P, Q, U, V, W>
  implements DataAccessObject<T, E, R, S, O, P, Q, U, V, W>
{
  @Inject
  private EntityManager em;
  @Inject
  private Event<T> tEventSrc;
  @Inject
  private Event<E> eEventSrc;
  @Inject
  private Event<R> rEventSrc;
  @Inject
  private Event<S> sEventSrc;
  @Inject
  private Event<O> oEventSrc;
  @Inject
  private Event<P> pEventSrc;
  @Inject
  private Event<Q> qEventSrc;
  @Inject
  private Event<U> uEventSrc;
  @Inject
  private Event<V> vEventSrc;
  @Inject
  private Event<W> wEventSrc;
  private EntityTransaction tx = null;
  private Class<T> typeT;
  private Class<E> typeE;
  private Class<R> typeR;
  private Class<S> typeS;
  private Class<O> typeO;
  private Class<P> typeP;
  private Class<Q> typeQ;
  private Class<U> typeU;
  private Class<V> typeV;
  private Class<W> typeW;
  
  public DataAccessObjectJpa() {}
  
  public DataAccessObjectJpa(Class<T> typeT)
  {
    this.typeT = typeT;
  }
  
  public DataAccessObjectJpa(Class<T> typeT, Class<E> typeE)
  {
    this.typeT = typeT;
    this.typeE = typeE;
  }
  
  public DataAccessObjectJpa(Class<T> typeT, Class<E> typeE, Class<R> typeR)
  {
    this.typeT = typeT;
    this.typeE = typeE;
    this.typeR = typeR;
  }
  
  public DataAccessObjectJpa(Class<T> typeT, Class<E> typeE, Class<R> typeR, Class<S> typeS)
  {
    this.typeT = typeT;
    this.typeE = typeE;
    this.typeR = typeR;
    this.typeS = typeS;
  }
  
  public DataAccessObjectJpa(Class<T> typeT, Class<E> typeE, Class<R> typeR, Class<S> typeS, Class<O> typeO)
  {
    this.typeT = typeT;
    this.typeE = typeE;
    this.typeR = typeR;
    this.typeS = typeS;
    this.typeO = typeO;
  }
  
  public DataAccessObjectJpa(Class<T> typeT, Class<E> typeE, Class<R> typeR, Class<S> typeS, Class<O> typeO, Class<P> typeP)
  {
    this.typeT = typeT;
    this.typeE = typeE;
    this.typeR = typeR;
    this.typeS = typeS;
    this.typeO = typeO;
    this.typeP = typeP;
  }
  
  public DataAccessObjectJpa(Class<T> typeT, Class<E> typeE, Class<R> typeR, Class<S> typeS, Class<O> typeO, Class<P> typeP, Class<Q> typeQ)
  {
    this.typeT = typeT;
    this.typeE = typeE;
    this.typeR = typeR;
    this.typeS = typeS;
    this.typeO = typeO;
    this.typeP = typeP;
    this.typeQ = typeQ;
  }
  
  public DataAccessObjectJpa(Class<T> typeT, Class<E> typeE, Class<R> typeR, Class<S> typeS, Class<O> typeO, Class<P> typeP, Class<Q> typeQ, Class<U> typeU)
  {
    this.typeT = typeT;
    this.typeE = typeE;
    this.typeR = typeR;
    this.typeS = typeS;
    this.typeO = typeO;
    this.typeP = typeP;
    this.typeQ = typeQ;
    this.typeU = typeU;
  }
  
  public DataAccessObjectJpa(Class<T> typeT, Class<E> typeE, Class<R> typeR, Class<S> typeS, Class<O> typeO, Class<P> typeP, Class<Q> typeQ, Class<U> typeU, Class<V> typeV)
  {
    this.typeT = typeT;
    this.typeE = typeE;
    this.typeR = typeR;
    this.typeS = typeS;
    this.typeO = typeO;
    this.typeP = typeP;
    this.typeQ = typeQ;
    this.typeU = typeU;
    this.typeV = typeV;
  }
  
  public DataAccessObjectJpa(Class<T> typeT, Class<E> typeE, Class<R> typeR, Class<S> typeS, Class<O> typeO, Class<P> typeP, Class<Q> typeQ, Class<U> typeU, Class<V> typeV, Class<W> typeW)
  {
    this.typeT = typeT;
    this.typeE = typeE;
    this.typeR = typeR;
    this.typeS = typeS;
    this.typeO = typeO;
    this.typeP = typeP;
    this.typeQ = typeQ;
    this.typeU = typeU;
    this.typeV = typeV;
    this.typeW = typeW;
  }
  
  public T create(T entity)
  {
    this.em.persist(entity);
    this.em.flush();
    this.em.refresh(entity);
    this.tEventSrc.fire(entity);
    return entity;
  }
  
  public E createE(E entity)
  {
    this.em.persist(entity);
    this.em.flush();
    this.em.refresh(entity);
    this.eEventSrc.fire(entity);
    
    return entity;
  }
  
  public R createR(R entity)
  {
    this.em.persist(entity);
    this.em.flush();
    this.em.refresh(entity);
    this.rEventSrc.fire(entity);
    
    return entity;
  }
  
  public S createS(S entity)
  {
    this.em.persist(entity);
    this.em.flush();
    this.em.refresh(entity);
    this.sEventSrc.fire(entity);
    
    return entity;
  }
  
  public O createO(O entity)
  {
    this.em.persist(entity);
    this.em.flush();
    this.em.refresh(entity);
    this.oEventSrc.fire(entity);
    
    return entity;
  }
  
  public P createP(P entity)
  {
    this.em.persist(entity);
    this.em.flush();
    this.em.refresh(entity);
    this.pEventSrc.fire(entity);
    
    return entity;
  }
  
  public Q createQ(Q entity)
  {
    this.em.persist(entity);
    this.em.flush();
    this.em.refresh(entity);
    this.qEventSrc.fire(entity);
    
    return entity;
  }
  
  public U createU(U entity)
  {
    this.em.persist(entity);
    this.em.flush();
    this.em.refresh(entity);
    this.uEventSrc.fire(entity);
    
    return entity;
  }
  
  public V createV(V entity)
  {
    this.em.persist(entity);
    this.em.flush();
    this.em.refresh(entity);
    this.vEventSrc.fire(entity);
    
    return entity;
  }
  
  public W createW(W entity)
  {
    this.em.persist(entity);
    this.em.flush();
    this.em.refresh(entity);
    this.wEventSrc.fire(entity);
    
    return entity;
  }
  
  public T update(T entity)
  {
    T t = this.em.merge(entity);
    
    return t;
  }
  
  public E updateE(E entity)
  {
    E e = this.em.merge(entity);
    
    return e;
  }
  
  public R updateR(R entity)
  {
    R r = this.em.merge(entity);
    
    return r;
  }
  
  public S updateS(S entity)
  {
    S s = this.em.merge(entity);
    
    return s;
  }
  
  public O updateO(O entity)
  {
    O o = this.em.merge(entity);
    
    return o;
  }
  
  public P updateP(P entity)
  {
    P o = this.em.merge(entity);
    
    return o;
  }
  
  public Q updateQ(Q entity)
  {
    Q o = this.em.merge(entity);
    
    return o;
  }
  
  public U updateU(U entity)
  {
    U o = this.em.merge(entity);
    
    return o;
  }
  
  public V updateV(V entity)
  {
    V o = this.em.merge(entity);
    
    return o;
  }
  
  public W updateW(W entity)
  {
    W o = this.em.merge(entity);
    
    return o;
  }
  
  public boolean delete(Object id)
  {
    Object ref = this.em.getReference(this.typeT, id);
    this.em.remove(ref);
    return true;
  }
  
  public T findById(Object id)
  {
    return (T)this.em.find(this.typeT, id);
  }
  
  public List<T> findAll()
  {
    return null;
  }
  
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
  
  public List<T> findDescAllOrderedByParameter(String parameter)
  {
    CriteriaBuilder cb = this.em.getCriteriaBuilder();
    CriteriaQuery<T> criteria = cb.createQuery(this.typeT);
    Root<T> object = criteria.from(this.typeT);
    criteria.select(object).orderBy(new Order[] { cb.desc(object.get(parameter)) });
    return this.em.createQuery(criteria).getResultList();
  }
  
  public List<T> findAscAllOrderedByParameter(String parameter)
  {
    CriteriaBuilder cb = this.em.getCriteriaBuilder();
    CriteriaQuery<T> criteria = cb.createQuery(this.typeT);
    Root<T> object = criteria.from(this.typeT);
    criteria.select(object).orderBy(new Order[] { cb.asc(object.get(parameter)) });
    return this.em.createQuery(criteria).getResultList();
  }
  
  public List<T> findAllByEstadoOrderByAsc(String estado)
  {
    CriteriaBuilder cb = this.em.getCriteriaBuilder();
    CriteriaQuery<T> criteria = cb.createQuery(this.typeT);
    Root<T> object = criteria.from(this.typeT);
    criteria.select(object).where(cb.equal(object.get("estado"), estado))
      .orderBy(new Order[] {cb.asc(object.get("id")) });
    return this.em.createQuery(criteria).getResultList();
  }
  
  public List<T> findAllByEstadoOrderByDesc(String estado)
  {
    CriteriaBuilder cb = this.em.getCriteriaBuilder();
    CriteriaQuery<T> criteria = cb.createQuery(this.typeT);
    Root<T> object = criteria.from(this.typeT);
    criteria.select(object).where(cb.equal(object.get("estado"), estado))
      .orderBy(new Order[] {cb.desc(object.get("id")) });
    return this.em.createQuery(criteria).getResultList();
  }
  
  public T findByParameter(String parameter, Object valor)
  {
    CriteriaBuilder cb = this.em.getCriteriaBuilder();
    CriteriaQuery<T> criteria = cb.createQuery(this.typeT);
    Root<T> object = criteria.from(this.typeT);
    criteria.select(object).where(cb.equal(object.get(parameter), valor));
    return (T)this.em.createQuery(criteria).getSingleResult();
  }
  
  public BigInteger countTotalRecord()
  {
    String query = "select count(em) from " + this.typeT.getSimpleName() + 
      " em where em.estado='AC' or em.estado='IN' ";
    System.out.println("query:" + query);
    return (BigInteger)this.em.createNativeQuery(query).getSingleResult();
  }
  
  public List<T> findAllBySize(int start, int maxRows)
  {
    Query q = this.em
      .createQuery("SELECT em FROM " + 
      this.typeT.getSimpleName() + 
      " em where em.estado='AC' or em.estado='IN' order by em.id desc");
    q.setFirstResult(start);
    q.setMaxResults(maxRows);
    return q.getResultList();
  }
  
  public List<T> findAllActivosByMaxResultOrderByDesc(int maxRows)
  {
    Query q = this.em.createQuery("SELECT em FROM " + this.typeT.getSimpleName() + 
      " em where em.estado='AC' order by em.id desc");
    q.setMaxResults(maxRows);
    return q.getResultList();
  }
  
  public List<T> findAllActivosByMaxResultOrderByAsc(int maxRows)
  {
    Query q = this.em.createQuery("SELECT em FROM " + this.typeT.getSimpleName() + 
      " em where em.estado='AC' order by em.id desc");
    q.setMaxResults(maxRows);
    return q.getResultList();
  }
  
  public List<T> findAllByParameter(String parameter, Object valor)
  {
    CriteriaBuilder cb = this.em.getCriteriaBuilder();
    CriteriaQuery<T> criteria = cb.createQuery(this.typeT);
    Root<T> object = criteria.from(this.typeT);
    criteria.select(object).where(cb.equal(object.get(parameter), valor));
    return this.em.createQuery(criteria).getResultList();
  }
  
  public List<T> findAllActivosByParameter(String parameter, Object valor)
  {
    CriteriaBuilder cb = this.em.getCriteriaBuilder();
    CriteriaQuery<T> criteria = cb.createQuery(this.typeT);
    Root<T> object = criteria.from(this.typeT);
    criteria.select(object).where(new Predicate[] { cb.equal(object.get(parameter), valor), 
      cb.equal(object.get("estado"), "AC") });
    return this.em.createQuery(criteria).getResultList();
  }
  
  public List<T> findAllByParameterObjectTwo(String parameter, String parameterTwo, Object valor, Object valorTwo)
  {
    CriteriaBuilder cb = this.em.getCriteriaBuilder();
    CriteriaQuery<T> criteria = cb.createQuery(this.typeT);
    Root<T> object = criteria.from(this.typeT);
    criteria.select(object)
      .where(new Predicate[] {cb.equal(object.get(parameter), valor), 
      cb.equal(object.get(parameterTwo), valorTwo) })
      .orderBy(new Order[] {cb.desc(object.get("id")) });
    return this.em.createQuery(criteria).getResultList();
  }
  
  public List<T> executeQueryResulList(String query)
  {
    Query q = this.em.createQuery(query);
    return q.getResultList();
  }
  
  public T executeQuerySingleResult(String query)
  {
    Query q = this.em.createQuery(query);
    return (T)q.getSingleResult();
  }
  
  public T findByParameterObjectTwo(String parameter, String parameterTwo, Object valor, Object valorTwo)
  {
    CriteriaBuilder cb = this.em.getCriteriaBuilder();
    CriteriaQuery<T> criteria = cb.createQuery(this.typeT);
    Root<T> object = criteria.from(this.typeT);
    criteria.select(object).where(new Predicate[] { cb.equal(object.get(parameter), valor), 
      cb.equal(object.get(parameterTwo), valorTwo) });
    return (T)this.em.createQuery(criteria).getSingleResult();
  }
  
  public List<T> findAllActivosByQuery(String parameter, Object valor)
  {
    String upperQuery = valor.toString().toUpperCase();
    Query q = this.em
      .createQuery("SELECT em FROM " + 
      this.typeT.getSimpleName() + 
      " em where upper(translate(em." + 
      parameter + 
      ", 'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñ', 'aeiouAEIOUaeiouAEIOUÑ')) like '%" + 
      upperQuery + "%' and em.estado='AC' order by em." + 
      parameter + " asc");
    return q.getResultList();
  }
  
  public List<T> findAllActivosByQueryAndTwoParameter(String parameter1, Object value1, String parameter2, Object value2)
  {
    String upperQuery = value2.toString().toUpperCase();
    String qu = "SELECT em FROM " + 
      this.typeT.getSimpleName() + 
      " em where upper(translate(em." + 
      parameter2 + 
      ", 'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñ', 'aeiouAEIOUaeiouAEIOUÑ')) like '%" + 
      upperQuery + "%' and em." + parameter1 + "='" + value1 + 
      "'  and em.estado='AC' order by em." + parameter2 + " desc";
    System.out.println(qu);
    Query q = this.em.createQuery(qu);
    return q.getResultList();
  }
  
  public List<T> findAllActiveInactiveOtherTableAndParameter(String nameTableObject, String nameTableQuery, String paramter, Object value)
  {
    String query = "select em from " + this.typeT.getSimpleName() + " em," + 
      nameTableObject + 
      " gcc where (em.estado='AC' or em.estado='IN') and em." + 
      nameTableQuery + ".id=gcc.id and gcc." + paramter + ".id=" + 
      value;
    System.out.println("Query " + query);
    return this.em.createQuery(query).getResultList();
  }
  
  public List<T> findAllByParameterDate(String parameter, Date valor)
  {
    CriteriaBuilder cb = this.em.getCriteriaBuilder();
    CriteriaQuery<T> criteria = cb.createQuery(this.typeT);
    Root<T> object = criteria.from(this.typeT);
    criteria.select(object).where(cb.equal(object.get(parameter), valor))
      .orderBy(new Order[] {cb.desc(object.get("id")) });
    return this.em.createQuery(criteria).getResultList();
  }
  
  public T findAllByParameterDateAndTwoParameter(String parameter, Date valor, String parameterTwo, Object valueTwo)
  {
    CriteriaBuilder cb = this.em.getCriteriaBuilder();
    CriteriaQuery<T> criteria = cb.createQuery(this.typeT);
    Root<T> object = criteria.from(this.typeT);
    criteria.select(object)
      .where(new Predicate[] {cb.equal(object.get(parameter), valor), 
      cb.equal(object.get(parameterTwo), valueTwo) })
      .orderBy(new Order[] {cb.desc(object.get("id")) });
    return (T)this.em.createQuery(criteria).getSingleResult();
  }
  
  public EntityManager getEm()
  {
    return this.em;
  }
  
  public List<T> findAllActiveParameter(String paramter, Object value)
  {
    String query = "select em from " + this.typeT.getSimpleName() + 
      " em where (em.estado='AC' OR em.estado='IN') and  em." + 
      paramter + ".id=" + value;
    System.out.println("Query " + query);
    return this.em.createQuery(query).getResultList();
  }
  
  public List<T> findAllActiveOtherTableAndParameter(String nameTableObject, String nameTableQuery, String paramter, Object value)
  {
    String query = "select em from " + this.typeT.getSimpleName() + " em," + 
      nameTableObject + " gcc where em.estado='AC' and em." + 
      nameTableQuery + ".id=gcc.id and gcc." + paramter + ".id=" + 
      value;
    System.out.println("Query " + query);
    return this.em.createQuery(query).getResultList();
  }
  
  public List<T> findAllActiveParameterOrderedForId(String paramter, Object value)
  {
    String query = "select em from " + this.typeT.getSimpleName() + 
      " em where (em.estado='AC' OR em.estado='IN') and  em." + 
      paramter + ".id=" + value + " order by em.id asc";
    System.out.println("Query " + query);
    return this.em.createQuery(query).getResultList();
  }
  
  public List<T> findAllActiveOrderedForId()
  {
    String query = "select em from " + this.typeT.getSimpleName() + 
      " em where (em.estado='AC' OR em.estado='IN')  order by em.id asc";
    System.out.println("Query " + query);
    return this.em.createQuery(query).getResultList();
  }
  
  public List<T> findActiveForThwoDatesAndThwoObject(String parameterUsuer, String usuario, String nameTableQuery, Object value1, String nameTable2Query, Object value2, String paramterBegin, Date fechaini, String paramterEnd, Date fechafin)
  {
    String query = "select em from " + this.typeT.getSimpleName() + 
      " em where  em." + nameTableQuery + ".id=" + value1 + 
      " and  em." + nameTable2Query + ".id=" + value2 + " and (em." + 
      parameterUsuer + " like '" + usuario + "' or em." + 
      parameterUsuer + " like '" + usuario + 
      "') and  to_number(to_char(em." + paramterEnd + 
      "  ,'YYYYMMDD'), '999999999')>=" + 
      Time.obtenerFormatoYYYYMMDD(fechaini) + 
      " and  to_number(to_char(em." + paramterEnd + 
      " ,'YYYYMMDD'), '999999999')<=" + 
      Time.obtenerFormatoYYYYMMDD(fechafin) + 
      "  order by em.id desc";
    System.out.println("Query Factura: " + query);
    return this.em.createQuery(query).getResultList();
  }
  
  public List<T> findAllActiveForThwoDatesAndThwoObject(String nameTableQuery, Object value1, String nameTable2Query, Object value2, String paramterBegin, Date fechaini, String paramterEnd, Date fechafin)
  {
    String query = "select em from " + this.typeT.getSimpleName() + 
      " em where  em." + nameTableQuery + ".id=" + value1 + 
      " and  em." + nameTable2Query + ".id=" + value2 + 
      " and to_number(to_char(em." + paramterEnd + 
      "  ,'YYYYMMDD'), '999999999')>=" + 
      Time.obtenerFormatoYYYYMMDD(fechaini) + 
      " and  to_number(to_char(em." + paramterEnd + 
      " ,'YYYYMMDD'), '999999999')<=" + 
      Time.obtenerFormatoYYYYMMDD(fechafin) + 
      "  order by em.id desc";
    System.out.println("Query Factura: " + query);
    return this.em.createQuery(query).getResultList();
  }
  
  public List<T> findActiveForThwoDatesAndThwoObject(String parameterUsuer, String usuario, String nameTableQuery, Object value, String paramterBegin, Date fechaini, String paramterEnd, Date fechafin)
  {
    String query = "select em from " + this.typeT.getSimpleName() + 
      " em  where   em." + nameTableQuery + ".id=" + value + 
      " and (em." + parameterUsuer + " like '" + usuario + 
      "' or em." + parameterUsuer + " like '" + usuario + 
      "') and  to_number(to_char(em." + paramterEnd + 
      "  ,'YYYYMMDD'), '999999999')>=" + 
      Time.obtenerFormatoYYYYMMDD(fechaini) + 
      " and  to_number(to_char(em." + paramterEnd + 
      " ,'YYYYMMDD'), '999999999')<=" + 
      Time.obtenerFormatoYYYYMMDD(fechafin) + 
      "  order by em.id desc";
    System.out.println("Query Factura: " + query);
    return this.em.createQuery(query).getResultList();
  }
  
  public List<T> findAllActiveForThwoDatesAndThwoObject(String nameTableQuery, Object value, String paramterBegin, Date fechaini, String paramterEnd, Date fechafin)
  {
    String query = "select em from " + this.typeT.getSimpleName() + 
      " em  where   em." + nameTableQuery + ".id=" + value + 
      " and  to_number(to_char(em." + paramterEnd + 
      "  ,'YYYYMMDD'), '999999999')>=" + 
      Time.obtenerFormatoYYYYMMDD(fechaini) + 
      " and  to_number(to_char(em." + paramterEnd + 
      " ,'YYYYMMDD'), '999999999')<=" + 
      Time.obtenerFormatoYYYYMMDD(fechafin) + 
      "  order by em.id desc";
    System.out.println("Query Factura: " + query);
    return this.em.createQuery(query).getResultList();
  }
  
  public List<T> findAllActiveForThwoDatesAndThwoObject(String nameTableQuery, Object value, String paramterBegin, Date fechaini, String paramterEnd, Date fechafin, String paramEstado, Object estadoObject)
  {
    String query = "select em from " + this.typeT.getSimpleName() + 
      " em  where   em." + nameTableQuery + ".id=" + value + 
      " and  to_number(to_char(em." + paramterEnd + 
      "  ,'YYYYMMDD'), '999999999')>=" + 
      Time.obtenerFormatoYYYYMMDD(fechaini) + 
      " and  to_number(to_char(em." + paramterEnd + 
      " ,'YYYYMMDD'), '999999999')<=" + 
      Time.obtenerFormatoYYYYMMDD(fechafin) + 
      " and em." + paramEstado + "=" + estadoObject + "  order by em.id desc";
    System.out.println("Query Factura: " + query);
    return this.em.createQuery(query).getResultList();
  }
}
