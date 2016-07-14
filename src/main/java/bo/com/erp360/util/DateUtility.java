package bo.com.erp360.util;

import java.io.PrintStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class DateUtility
{
  public static final String DATE_FORMAT = "dd/MM/yyyy HH:mm:ss";
  public static final String[] diasLiteral = { "", "DOMINGO", "LUNES", "MARTES", "MIERCOLES", "JUEVES", "VIERNES", "SABADO" };
  public static final String[] mesLiteral = { "", "ENERO", "FEBRERO", "MARZO", "ABRIL", "MAYO", "JUNIO", "JULIO", "AGOSTO", "SEPTIEMBRE", "OCTUBRE", "NOVIEMBRE", "DICIEMBRE" };
  
  public static String getCurrentDateTime()
  {
    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    java.util.Date date = new java.util.Date();
    return dateFormat.format(date);
  }
  
  public static int obtenerDiaNumeral(java.util.Date fecha)
  {
    Calendar cal = Calendar.getInstance();
    cal.setTime(fecha);
    return cal.get(5);
  }
  
  public static int getDayOfTheWeek(java.util.Date d)
  {
    GregorianCalendar cal = new GregorianCalendar();
    cal.setTime(d);
    return cal.get(7);
  }
  
  public static int obtenerMesNumeral(java.util.Date fecha)
  {
    Calendar cal = Calendar.getInstance();
    cal.setTime(fecha);
    return cal.get(2) + 1;
  }
  
  public static int obtenerYearNumeral(java.util.Date fecha)
  {
    Calendar cal = Calendar.getInstance();
    cal.setTime(fecha);
    return cal.get(1);
  }
  
  public static String obtenerDiaLiteral(int dia)
  {
    return diasLiteral[dia];
  }
  
  public static String obtenerMesLiteral(int mes)
  {
    return mesLiteral[mes];
  }
  
  public static String getFechaActual()
  {
    java.util.Date ahora = new java.util.Date();
    SimpleDateFormat formateador = new SimpleDateFormat("dd-MM-yyyy");
    return formateador.format(ahora);
  }
  
  public static String deDateAString(java.util.Date date)
  {
    SimpleDateFormat formateador = new SimpleDateFormat("dd-MM-yyyy");
    return formateador.format(date);
  }
  
  public static String getHoraActual()
  {
    java.util.Date ahora = new java.util.Date();
    SimpleDateFormat formateador = new SimpleDateFormat("hh:mm:ss");
    return formateador.format(ahora);
  }
  
  public static java.sql.Date sumarFechasDias(java.sql.Date fch, int dias)
  {
    Calendar cal = new GregorianCalendar();
    cal.setTimeInMillis(fch.getTime());
    cal.add(5, dias);
    return new java.sql.Date(cal.getTimeInMillis());
  }
  
  public static synchronized java.sql.Date restarFechasDias(java.sql.Date fch, int dias)
  {
    Calendar cal = new GregorianCalendar();
    cal.setTimeInMillis(fch.getTime());
    cal.add(5, -dias);
    return new java.sql.Date(cal.getTimeInMillis());
  }
  
  public static synchronized int diferenciasDeFechas(java.util.Date fechaInicial, java.util.Date fechaFinal)
  {
    DateFormat df = DateFormat.getDateInstance(2);
    String fechaInicioString = df.format(fechaInicial);
    try
    {
      fechaInicial = df.parse(fechaInicioString);
    }
    catch (ParseException localParseException) {}
    String fechaFinalString = df.format(fechaFinal);
    try
    {
      fechaFinal = df.parse(fechaFinalString);
    }
    catch (ParseException localParseException1) {}
    long fechaInicialMs = fechaInicial.getTime();
    long fechaFinalMs = fechaFinal.getTime();
    long diferencia = fechaFinalMs - fechaInicialMs;
    double dias = Math.floor(diferencia / 86400000L);
    return (int)dias;
  }
  
  public static synchronized java.util.Date deStringToDate(String fecha)
  {
    SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd-MM-yyyy");
    java.util.Date fechaEnviar = null;
    try
    {
      return formatoDelTexto.parse(fecha);
    }
    catch (ParseException ex)
    {
      ex.printStackTrace();
    }
    return null;
  }
  
  public static java.util.Date cambiarYearDate(int year)
  {
    java.util.Date date = new java.util.Date();
    GregorianCalendar otroCalendario = new GregorianCalendar();
    otroCalendario.setTime(date);
    otroCalendario.set(1, year);
    return otroCalendario.getTime();
  }
  
  public static java.util.Date restarDiasFecha(java.util.Date fecha, int dias)
  {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(fecha);
    calendar.add(6, -dias);
    return calendar.getTime();
  }
  
  public static java.util.Date sumarFechaDia(java.util.Date fch, int dias)
  {
    Calendar cal = new GregorianCalendar();
    cal.setTimeInMillis(fch.getTime());
    cal.add(5, dias);
    return new java.util.Date(cal.getTimeInMillis());
  }
  
  public static synchronized java.util.Date restarFechasDias(java.util.Date fch, int dias)
  {
    Calendar cal = new GregorianCalendar();
    cal.setTimeInMillis(fch.getTime());
    cal.add(5, -dias);
    return new java.sql.Date(cal.getTimeInMillis());
  }
  
  public static java.util.Date getUltimoDiaDelMesActual()
  {
    Calendar cal = Calendar.getInstance();
    cal.set(cal.get(1), 
      cal.get(2), 
      cal.getActualMaximum(5), 
      cal.getMaximum(11), 
      cal.getMaximum(12), 
      cal.getMaximum(13));
    return cal.getTime();
  }
  
  public static int getUltimoDiaMes(int anio, int mes)
  {
    Calendar calendario = Calendar.getInstance();
    calendario.set(anio, mes - 1, 1);
    return calendario.getActualMaximum(5);
  }
  
  public static String obtenerFormatoYYYY_MM_DD(java.util.Date date)
  {
    try
    {
      String DATE_FORMAT = "yyyy-MM-dd";
      SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
      return sdf.format(date);
    }
    catch (Exception e)
    {
      System.out.println("Error en obtenerFormatoYYYYMMDD: " + e.getMessage());
    }
    return null;
  }
  
  public static String obtenerFormatoYYYYMMDD(java.util.Date date)
  {
    try
    {
      String DATE_FORMAT = "yyyyMMdd";
      SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
      return sdf.format(date);
    }
    catch (Exception e)
    {
      System.out.println("Error en obtenerFormatoYYYYMMDD: " + e.getMessage());
    }
    return null;
  }
  
  public static String obtenerFormatoDDMMYYYY(java.util.Date date)
  {
    try
    {
      String DATE_FORMAT = "dd/MM/yyyy";
      SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
      return sdf.format(date);
    }
    catch (Exception e)
    {
      System.out.println("Error en obtenerFormatoDDMMYYYY: " + e.getMessage());
    }
    return null;
  }
  
  public static void main(String[] args)
  {
    System.out.println("hora actual Java.util " + new java.util.Date());
    System.out.println("hora actual restarFechasDias" + restarDiasFecha(new java.util.Date(), 1));
    System.out.println("hora actual " + sumarFechaDia(new java.util.Date(), 1));
  }
}
