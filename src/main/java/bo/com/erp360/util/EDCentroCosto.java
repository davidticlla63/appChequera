package bo.com.erp360.util;

public class EDCentroCosto
{
  private int id;
  private String grupo;
  private String nombre;
  
  public EDCentroCosto(int id, String grupo, String nombre)
  {
    this.id = id;
    this.grupo = grupo;
    this.nombre = nombre;
  }
  
  public int getId()
  {
    return this.id;
  }
  
  public void setId(int id)
  {
    this.id = id;
  }
  
  public String getGrupo()
  {
    return this.grupo;
  }
  
  public void setGrupo(String grupo)
  {
    this.grupo = grupo;
  }
  
  public String getNombre()
  {
    return this.nombre;
  }
  
  public void setNombre(String nombre)
  {
    this.nombre = nombre;
  }
  
  public String toString()
  {
    return 
      "EDCentroCosto [id=" + this.id + ", grupo=" + this.grupo + ", nombre=" + this.nombre + "]";
  }
}
