package bo.com.erp360.util;

public class NumberUtil
{
  public static boolean isNumeric(Object value)
  {
    try
    {
      Integer.parseInt((String)value);
      return true;
    }
    catch (NumberFormatException nfe) {}
    return false;
  }
}
