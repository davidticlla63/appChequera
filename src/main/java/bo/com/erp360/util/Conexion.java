package bo.com.erp360.util;

import java.io.Serializable;

public class Conexion
  implements Serializable
{
  private static final long serialVersionUID = -2579990640927344648L;
  public static String datasourse = "java:jboss/datasources/chequeraDS";
  
  public String getDatasourse()
  {
    return datasourse;
  }
  
  public void setDatasourse(String datasourse)
  {
    datasourse = datasourse;
  }
}
