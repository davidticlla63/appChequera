package bo.com.erp360.util;

import java.io.PrintStream;

public class Cifrado
{
  private static String caracteres = "abcdefghijklmnopqrstuvwxyzáéíóú 1234567890@.,-_|";
  
  public static String Encriptar(String text, int key)
  {
    String texto = LimpiarCadena(text);
    
    String res = "";
    for (int i = 0; i < texto.length(); i++)
    {
      int pos = caracteres.indexOf(texto.charAt(i));
      if (pos + key < caracteres.length()) {
        res = res + caracteres.charAt(pos + key);
      } else {
        res = res + caracteres.charAt(pos + key - caracteres.length());
      }
    }
    return res;
  }
  
  public static String Desencriptar(String text, int key)
  {
    String texto = LimpiarCadena(text);
    String res = "";
    for (int i = 0; i < texto.length(); i++)
    {
      int pos = caracteres.indexOf(texto.charAt(i));
      if (pos - key < 0) {
        res = res + caracteres.charAt(pos - key + caracteres.length());
      } else {
        res = res + caracteres.charAt(pos - key);
      }
    }
    return res;
  }
  
  private static String LimpiarCadena(String t)
  {
    t = t.toLowerCase();
    
    t = t.replaceAll("\n", "");
    for (int i = 0; i < t.length(); i++)
    {
      int pos = caracteres.indexOf(t.charAt(i));
      if (pos == -1) {
        t = t.replace(t.charAt(i), ' ');
      }
    }
    return t;
  }
  
  public static void main(String[] arg)
  {
    int key = 12;
    String dato = "|mauricio|bejarano|rivera|";
    
    String datoEncriptado = Encriptar(dato, key);
    System.out.println("datoEncriptado: " + datoEncriptado);
    String datoDesencriptado = Desencriptar(datoEncriptado, key);
    System.out.println("datoDesencriptado: " + datoDesencriptado);
  }
}