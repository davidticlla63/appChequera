package bo.com.erp360.util;

import java.io.PrintStream;
import java.util.Date;
import java.util.Map;

public class UtilPostGre
{
  private String user = "erp360";
  private String database = "erp360_db";
  private String password = "erp360";
  private String puerto = "5432";
  private String host = "localhost";
  private String path = "D:\\BACKUP";
  private String dirBin = "C:\\Program Files\\PostgreSQL\\9.3\\bin";
  
  public boolean backUp()
  {
    try
    {
      String archivo = this.database + Time.obtenerFormatoYYYYMMDDHHMISS(new Date()) + ".backup";
      this.path = (this.path + "\\" + archivo);
      ProcessBuilder pb = new ProcessBuilder(new String[] {
        this.dirBin + "\\pg_dump.exe", 
        "-i", "-h", this.host, "-p", this.puerto, "-U", this.user, "-F", "c", 
        "-b", "-v", "-f", this.path, this.database });
      pb.environment().put("PGPASSWORD", this.password);
      pb.redirectErrorStream(true);
      Process p = pb.start();
      System.out.println("Termino respaldo");
      return true;
    }
    catch (Exception e)
    {
      System.err.println("Error en : " + e.getStackTrace());
    }
    return false;
  }
  
  public boolean restore()
  {
    try
    {
      ProcessBuilder pb = new ProcessBuilder(new String[] {
        this.dirBin + "\\pg_restore.exe", 
        "-i", "-h", this.host, "-p", this.puerto, "-U", this.user, "-d", this.database, 
        "-v", this.path });
      pb.environment().put("PGPASSWORD", this.password);
      pb.redirectErrorStream(true);
      pb.start();
      System.out.println("Termino restauracion");
      return true;
    }
    catch (Exception e)
    {
      System.err.println("Error en : " + e.getStackTrace());
    }
    return false;
  }
  
  public String getUser()
  {
    return this.user;
  }
  
  public void setUser(String user)
  {
    this.user = user;
  }
  
  public String getDatabase()
  {
    return this.database;
  }
  
  public void setDatabase(String database)
  {
    this.database = database;
  }
  
  public String getPassword()
  {
    return this.password;
  }
  
  public void setPassword(String password)
  {
    this.password = password;
  }
  
  public String getPuerto()
  {
    return this.puerto;
  }
  
  public void setPuerto(String puerto)
  {
    this.puerto = puerto;
  }
  
  public String getHost()
  {
    return this.host;
  }
  
  public void setHost(String host)
  {
    this.host = host;
  }
  
  public String getPath()
  {
    return this.path;
  }
  
  public void setPath(String path)
  {
    this.path = path;
  }
  
  public String getDirBin()
  {
    return this.dirBin;
  }
  
  public void setDirBin(String dirBin)
  {
    this.dirBin = dirBin;
  }
  
  public static void main(String[] args)
  {
    UtilPostGre p = new UtilPostGre();
    p.backUp();
  }
}
