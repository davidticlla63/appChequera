package bo.com.erp360.util;

import java.util.Date;

public class EDTipoCambio
{
  private Date fecha;
  private double tipoCambio;
  private double ufv;
  
  public EDTipoCambio(Date fecha, double tipoCambio, double ufv)
  {
    setFecha(fecha);
    setTipoCambio(tipoCambio);
    setUfv(ufv);
  }
  
  public Date getFecha()
  {
    return this.fecha;
  }
  
  public void setFecha(Date fecha)
  {
    this.fecha = fecha;
  }
  
  public double getTipoCambio()
  {
    return this.tipoCambio;
  }
  
  public void setTipoCambio(double tipoCambio)
  {
    this.tipoCambio = tipoCambio;
  }
  
  public double getUfv()
  {
    return this.ufv;
  }
  
  public void setUfv(double ufv)
  {
    this.ufv = ufv;
  }
}
