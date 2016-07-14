package bo.com.erp360.util;

public class EDPrivilegio
{
  private String nombre;
  private String nivel;
  private String icon;
  private String padre;
  
  public EDPrivilegio() {}
  
  public EDPrivilegio(String nombre, String nivel, String icon, String padre)
  {
    this.nombre = nombre;
    this.nivel = nivel;
    this.icon = icon;
    this.padre = padre;
  }
  
  public String getNombre()
  {
    return this.nombre;
  }
  
  public void setNombre(String nombre)
  {
    this.nombre = nombre;
  }
  
  public String getNivel()
  {
    return this.nivel;
  }
  
  public void setNivel(String nivel)
  {
    this.nivel = nivel;
  }
  
  public String getIcon()
  {
    return this.icon;
  }
  
  public void setIcon(String icon)
  {
    this.icon = icon;
  }
  
  public String getPadre()
  {
    return this.padre;
  }
  
  public void setPadre(String padre)
  {
    this.padre = padre;
  }
}
