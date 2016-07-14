package bo.com.erp360.util;

import java.io.IOException;
import java.util.List;

public class PlanCuentaUtil
{
  public static String llenarDelanteConCeroCodificacion(String codigo, int tamanioDigito)
    throws IOException
  {
    int tamanioDigitoAux = codigo.length();
    if (tamanioDigitoAux < tamanioDigito) {
      for (int i = tamanioDigitoAux; i < tamanioDigito; i++) {
        codigo = "0" + codigo;
      }
    }
    return codigo;
  }
  
  public static String llenarDespuesConCeroCodificacion(String codigo, int tamanioDigito, List<Integer> listTamanio)
    throws IOException
  {
    String codigoAux = llenarDelanteConCeroCodificacion(codigo, tamanioDigito);
    int tamanioDigitoAux = codigoAux.length();
    int tamanioDigitoAux2 = obtenerTamanioTotalCodificacion(listTamanio);
    for (int i = tamanioDigitoAux; i <= tamanioDigitoAux2; i++) {
      codigo = codigo + "0";
    }
    return codigo;
  }
  
  public static int obtenerTamanioTotalCodificacion(List<Integer> listTamanio)
    throws IOException
  {
    int tam = 0;
    for (Integer i : listTamanio) {
      tam += i.intValue();
    }
    return tam;
  }
  
  public static String cargarCodificacion(String parteCodigoAuxiliarPadre, String codigo, int tamanioDigito, List<Integer> listTamanio)
    throws IOException
  {
    String aux = "";
    
    int tamanioCodigoAuxiliarPadre = parteCodigoAuxiliarPadre.length();
    
    codigo = llenarDelanteConCeroCodificacion(codigo, tamanioDigito);
    
    aux = parteCodigoAuxiliarPadre + codigo;
    
    int tamanioTotalPrevio = tamanioDigito + tamanioCodigoAuxiliarPadre;
    
    int tamanioTotalCod = obtenerTamanioTotalCodificacion(listTamanio);
    for (int i = tamanioTotalPrevio; i <= tamanioTotalCod; i++) {
      aux = aux + "0";
    }
    return aux;
  }
  
  public static int obtenerTamanioHastaNIvel(int nivel, List<Integer> listTamanio)
  {
    int tamanio = 0;
    for (int i = 0; i < nivel; i++)
    {
      Integer tamAux = (Integer)listTamanio.get(i);
      tamanio += tamAux.intValue();
    }
    return tamanio;
  }
}
