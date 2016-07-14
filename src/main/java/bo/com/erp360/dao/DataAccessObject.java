package bo.com.erp360.dao;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

public abstract interface DataAccessObject<T, E, R, S, O, P, Q, U, V, W>
{
  public abstract T create(T paramT)
    throws Exception;
  
  public abstract E createE(E paramE)
    throws Exception;
  
  public abstract R createR(R paramR)
    throws Exception;
  
  public abstract S createS(S paramS)
    throws Exception;
  
  public abstract O createO(O paramO)
    throws Exception;
  
  public abstract P createP(P paramP)
    throws Exception;
  
  public abstract Q createQ(Q paramQ)
    throws Exception;
  
  public abstract U createU(U paramU)
    throws Exception;
  
  public abstract V createV(V paramV)
    throws Exception;
  
  public abstract W createW(W paramW)
    throws Exception;
  
  public abstract T update(T paramT)
    throws Exception;
  
  public abstract E updateE(E paramE)
    throws Exception;
  
  public abstract R updateR(R paramR)
    throws Exception;
  
  public abstract S updateS(S paramS)
    throws Exception;
  
  public abstract O updateO(O paramO)
    throws Exception;
  
  public abstract P updateP(P paramP)
    throws Exception;
  
  public abstract Q updateQ(Q paramQ)
    throws Exception;
  
  public abstract U updateU(U paramU)
    throws Exception;
  
  public abstract V updateV(V paramV)
    throws Exception;
  
  public abstract W updateW(W paramW)
    throws Exception;
  
  public abstract boolean delete(Object paramObject)
    throws Exception;
  
  public abstract T findById(Object paramObject)
    throws Exception;
  
  public abstract List<T> findAll()
    throws Exception;
  
  public abstract List<T> findDescAllOrderedByParameter(String paramString);
  
  public abstract List<T> findAscAllOrderedByParameter(String paramString);
  
  public abstract List<T> findAllByEstadoOrderByAsc(String paramString);
  
  public abstract List<T> findAllByEstadoOrderByDesc(String paramString);
  
  public abstract BigInteger countTotalRecord();
  
  public abstract List<T> findAllBySize(int paramInt1, int paramInt2);
  
  public abstract T findByParameter(String paramString, Object paramObject);
  
  public abstract List<T> findAllByParameter(String paramString, Object paramObject);
  
  public abstract List<T> findAllByParameterObjectTwo(String paramString1, String paramString2, Object paramObject1, Object paramObject2);
  
  public abstract T findByParameterObjectTwo(String paramString1, String paramString2, Object paramObject1, Object paramObject2);
  
  public abstract List<T> findAllActivosByMaxResultOrderByDesc(int paramInt);
  
  public abstract List<T> findAllActivosByMaxResultOrderByAsc(int paramInt);
  
  public abstract List<T> findAllActivosByQuery(String paramString, Object paramObject);
  
  public abstract List<T> findAllActivosByParameter(String paramString, Object paramObject);
  
  public abstract List<T> findAllActivosByQueryAndTwoParameter(String paramString1, Object paramObject1, String paramString2, Object paramObject2);
  
  public abstract List<T> findAllActiveInactiveOtherTableAndParameter(String paramString1, String paramString2, String paramString3, Object paramObject);
  
  public abstract List<T> findAllByParameterDate(String paramString, Date paramDate);
  
  public abstract T findAllByParameterDateAndTwoParameter(String paramString1, Date paramDate, String paramString2, Object paramObject);
  
  public abstract void beginTransaction();
  
  public abstract void commitTransaction();
  
  public abstract void rollbackTransaction();
}
