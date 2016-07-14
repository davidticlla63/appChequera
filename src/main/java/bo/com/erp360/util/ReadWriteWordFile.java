package bo.com.erp360.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.URL;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

public class ReadWriteWordFile
{
  public static void main(String[] args)
  {
    File archivoDocx = new File("/Users/mauriciobejaranorivera/Desktop/TestDOCX.docx");
    File archivoDoc = new File("/Users/mauriciobejaranorivera/Desktop/TestDOC.doc");
    String textoDeDocx = "";
    String textoDeDoc = "";
    try
    {
      InputStream entradaArch1 = new FileInputStream(archivoDoc);
      InputStream entradaArch2 = new FileInputStream(archivoDocx);
      
      textoDeDocx = leerDocx(entradaArch2);
    }
    catch (Exception localException) {}
    System.out.println(textoDeDoc);
    System.out.println("==========================================");
    System.out.println(textoDeDocx);
    
    ClassLoader classloader = 
      POIFSFileSystem.class.getClassLoader();
    URL res = classloader.getResource(
      "org/apache/poi/poifs/filesystem/POIFSFileSystem.class");
    String path = res.getPath();
    System.out.println("Core POI came from " + path);
  }
  
  public static String leerDoc(InputStream doc)
    throws IOException
  {
    WordExtractor we = new WordExtractor(doc);
    
    return we.getText();
  }
  
  public static String leerDocx(InputStream docx)
    throws IOException
  {
    XWPFDocument fdocx = new XWPFDocument(docx);
    XWPFWordExtractor xwpf_we = new XWPFWordExtractor(fdocx);
    
    return xwpf_we.getText();
  }
  
  public static void readDoc2()
  {
    File docFile = null;
    WordExtractor docExtractor = null;
    WordExtractor exprExtractor = null;
    try
    {
      docFile = new File("/Users/mauriciobejaranorivera/Desktop/TestDOC.doc");
      
      FileInputStream fis = new FileInputStream(docFile.getAbsolutePath());
      
      HWPFDocument doc = new HWPFDocument(fis);
      
      docExtractor = new WordExtractor(doc);
    }
    catch (Exception exep)
    {
      System.out.println(exep.getMessage());
    }
  }
}
