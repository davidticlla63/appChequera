package bo.com.erp360.report;

import java.sql.Connection;
import java.sql.SQLException;
import javax.ejb.Stateless;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

@Stateless
public class ConnectionDatasource
{
  private Connection conn = null;
  
  public Connection getConnection()
  {
    try
    {
      if (this.conn == null)
      {
        Context ctx = new InitialContext();
        DataSource ds = (DataSource)ctx.lookup("java:jboss/datasources/WebAppErpDS");
        this.conn = ds.getConnection();
      }
      return this.conn;
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return null;
  }
  
  public void closeConnection()
  {
    try
    {
      if (this.conn != null) {
        this.conn.close();
      }
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
  }
}
