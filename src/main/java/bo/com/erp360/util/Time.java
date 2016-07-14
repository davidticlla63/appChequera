package bo.com.erp360.util;

import java.io.PrintStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Time
  implements Serializable
{
  private static final long serialVersionUID = -1808620452373692675L;
  private Integer hora;
  private Integer minuto;
  private Integer segundo;
  
  public Time()
  {
    this.hora = Integer.valueOf(0);
    this.minuto = Integer.valueOf(0);
    this.segundo = Integer.valueOf(0);
  }
  
  public Time(Integer hora, Integer minuto, Integer segundo)
  {
    this.hora = hora;
    this.minuto = minuto;
    this.segundo = segundo;
  }
  
  public Integer getHora()
  {
    return this.hora;
  }
  
  public void setHora(Integer hora)
  {
    this.hora = hora;
  }
  
  public Integer getMinuto()
  {
    return this.minuto;
  }
  
  public void setMinuto(Integer minuto)
  {
    this.minuto = minuto;
  }
  
  public Integer getSegundo()
  {
    return this.segundo;
  }
  
  public void setSegundo(Integer segundo)
  {
    this.segundo = segundo;
  }
  
  public static Time toTime(String time)
  {
    String[] value = time.split(":");
    Time hora = new Time(Integer.valueOf(value[0]), 
      Integer.valueOf(value[1]), Integer.valueOf(value[2]));
    return hora;
  }
  
  public static String convertTimeToString(Date time)
  {
    return 
      time.getHours() + ":" + time.getMinutes() + ":" + time.getSeconds();
  }
  
  public static String convertDateToString(Date time)
  {
    SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    String fecha = dt.format(time);
    System.out.println("Fecha: " + fecha);
    return fecha;
  }
  
  public static String convertSimpleDateToString(Date time)
  {
    SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy");
    String fecha = dt.format(time);
    System.out.println("Fecha: " + fecha);
    return fecha;
  }
  
  public static boolean mayorT1T2(Time inicio, Time fin)
  {
    if (inicio.getHora().doubleValue() > fin.getHora().doubleValue()) {
      return true;
    }
    if (inicio.getHora().doubleValue() == fin.getHora()
      .doubleValue())
    {
      if (inicio.getMinuto().doubleValue() > fin.getMinuto().doubleValue()) {
        return true;
      }
      if (inicio.getMinuto().doubleValue() == fin.getMinuto()
        .doubleValue())
      {
        if (inicio.getSegundo().doubleValue() > fin.getSegundo().doubleValue()) {
          return true;
        }
        return false;
      }
      return false;
    }
    return false;
  }
  
  public static Date setCeroMillisecond(Date date)
  {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.set(14, 0);
    
    return calendar.getTime();
  }
  
  public static boolean bettweenToDate(Time time1, Time time2, Time time3)
  {
    if (mayorT1T2(time3, time2)) {
      return false;
    }
    if (!mayorT1T2(time1, time2))
    {
      if (mayorT1T2(time3, time1))
      {
        if (mayorT1T2(time3, time2)) {
          return false;
        }
        return true;
      }
      return false;
    }
    return false;
  }
  
  public static boolean may(Date time1, Date time2)
  {
    long lantes = time1.getTime();
    long lahora = time2.getTime();
    
    long hora = (lahora - lantes) / 3600000L;
    if (lantes > lahora) {
      return true;
    }
    return false;
  }
  
  public static Date sumarRestarDiasFecha(Date fecha, int dias)
  {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(fecha);
    calendar.add(6, dias);
    
    return calendar.getTime();
  }
  
  public static Date sumarRestarHorasFecha(Date fecha, int horas)
  {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(fecha);
    calendar.add(11, horas);
    
    return calendar.getTime();
  }
  
  public String toString()
  {
    return 
      "Time [hora=" + this.hora + ", minuto=" + this.minuto + ", segundo=" + this.segundo + "]";
  }
  
  public String toConvertToString()
  {
    return this.hora + ":" + this.minuto + ":" + this.segundo;
  }
  
  public static String obtenerFormatoYYYYMMDD(Date date)
  {
    try
    {
      String DATE_FORMAT = "yyyyMMdd";
      SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
      String fecha = sdf.format(date);
      System.out.println(fecha);
      return fecha;
    }
    catch (Exception e)
    {
      System.out.println("Error en obtenerFormatoYYYYMMDD: " + 
        e.getMessage());
    }
    return null;
  }
  
  public static String obtenerFormatoYYYYMMDDHH(Date date)
  {
    try
    {
      String DATE_FORMAT = "yyyyMMddkk";
      SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
      String fecha = sdf.format(date);
      System.out.println(fecha);
      return fecha;
    }
    catch (Exception e)
    {
      System.out.println("Error en obtenerFormatoYYYYMMDDHH: " + 
        e.getMessage());
    }
    return null;
  }
  
  public static String obtenerFormatoYYYYMMDDHHMISS(Date date)
  {
    try
    {
      String DATE_FORMAT = "yyyyMMddkkmmss";
      SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
      String fecha = sdf.format(date);
      System.out.println(fecha);
      return fecha;
    }
    catch (Exception e)
    {
      System.out.println("Error en obtenerFormatoYYYYMMDDHHMISS: " + 
        e.getMessage());
    }
    return null;
  }
  
  public static String mes(int mes)
  {
    switch (mes)
    {
    case 1: 
      return "Enero";
    case 2: 
      return "Febrero";
    case 3: 
      return "Marzo";
    case 4: 
      return "Abril";
    case 5: 
      return "Mayo";
    case 6: 
      return "Junio";
    case 7: 
      return "Julio";
    case 8: 
      return "Agosto";
    case 9: 
      return "Septiembre";
    case 10: 
      return "Octubre";
    case 11: 
      return "Noviembre";
    case 12: 
      return "Diciembre";
    }
    return null;
  }
  
  public static String obtenerFormatoYYYYMM(Date date)
  {
    try
    {
      String DATE_FORMAT = "yyyyMM";
      SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
      return sdf.format(date);
    }
    catch (Exception e)
    {
      System.out.println("Error en obtenerFormatoYYYYMM: " + 
        e.getMessage());
    }
    return null;
  }
  
  public static String obtenerFormatoMM(Date date)
  {
    try
    {
      String DATE_FORMAT = "MM";
      SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
      return sdf.format(date);
    }
    catch (Exception e)
    {
      System.out.println("Error en obtenerFormatoMM: " + 
        e.getMessage());
    }
    return null;
  }
  
  public static Date getPrimerDiaDelMes(Date fecha)
  {
    Calendar cal = Calendar.getInstance();
    cal.setTime(fecha);
    cal.set(cal.get(1), cal.get(2), 
      cal.getActualMinimum(5), 
      cal.getMinimum(11), 
      cal.getMinimum(12), 
      cal.getMinimum(13));
    System.out.println(cal.getTime());
    return cal.getTime();
  }
  
  public static Date getUltimoDiaDelMes(Date fecha)
  {
    Calendar cal = Calendar.getInstance();
    cal.setTime(fecha);
    cal.set(cal.get(1), cal.get(2), 
      cal.getActualMaximum(5), 
      cal.getMaximum(11), 
      cal.getMaximum(12), 
      cal.getMaximum(13));
    System.out.println(cal.getTime());
    return cal.getTime();
  }
}
