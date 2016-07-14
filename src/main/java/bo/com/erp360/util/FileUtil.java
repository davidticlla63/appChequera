package bo.com.erp360.util;

public class FileUtil
{
  public static String obtenerExtencionFile(String fileName)
  {
    String extension = "";
    int i = fileName.lastIndexOf('.');
    if (i > 0) {
      extension = fileName.substring(i + 1);
    }
    return extension;
  }
}
