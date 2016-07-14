package bo.com.erp360.util;

public class EDPermisoV1
{
  private String nombre;
  private String icon;
  private String tipo;
  private Object object;
  
  public EDPermisoV1() {}
  
  public EDPermisoV1(String nombre, String icon, String tipo, Object object)
  {
    this.nombre = nombre;
    this.icon = icon;
    this.tipo = tipo;
    this.object = object;
  }
  
  public String getNombre()
  {
    return this.nombre;
  }
  
  public void setNombre(String nombre)
  {
    this.nombre = nombre;
  }
  
  public String getIcon()
  {
    return this.icon;
  }
  
  public void setIcon(String icon)
  {
    this.icon = icon;
  }
  
  public String getTipo()
  {
    return this.tipo;
  }
  
  public void setTipo(String tipo)
  {
    this.tipo = tipo;
  }
  
  public Object getObject()
  {
    return this.object;
  }
  
  public void setObject(Object object)
  {
    this.object = object;
  }
}
