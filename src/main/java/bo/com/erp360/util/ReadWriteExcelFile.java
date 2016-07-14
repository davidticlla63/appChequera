package bo.com.erp360.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadWriteExcelFile
{
  private Object object;
  private List<Object> listObject = new ArrayList();
  
  public static void readXLSFile(InputStream ExcelFileToRead)
    throws IOException
  {
    HSSFWorkbook wb = new HSSFWorkbook(ExcelFileToRead);
    
    HSSFSheet sheet = wb.getSheetAt(0);
    
    Iterator rows = sheet.rowIterator();
    while (rows.hasNext())
    {
      HSSFRow row = (HSSFRow)rows.next();
      Iterator cells = row.cellIterator();
      while (cells.hasNext())
      {
        HSSFCell cell = (HSSFCell)cells.next();
        if (cell.getCellType() == 1) {
          System.out.print(cell.getStringCellValue() + " ");
        } else if (cell.getCellType() == 0) {
          System.out.print(cell.getNumericCellValue() + " ");
        }
      }
      System.out.println();
    }
  }
  
  public static void writeXLSFile(String excelFileName)
    throws IOException
  {
    String sheetName = "Sheet1";
    
    HSSFWorkbook wb = new HSSFWorkbook();
    HSSFSheet sheet = wb.createSheet(sheetName);
    for (int r = 0; r < 5; r++)
    {
      HSSFRow row = sheet.createRow(r);
      for (int c = 0; c < 5; c++)
      {
        HSSFCell cell = row.createCell(c);
        
        cell.setCellValue("Cell " + r + " " + c);
      }
    }
    FileOutputStream fileOut = new FileOutputStream(excelFileName);
    
    wb.write(fileOut);
    fileOut.flush();
    fileOut.close();
  }
  
  public static void readXLSXFile(InputStream ExcelFileToRead)
    throws IOException
  {
    XSSFWorkbook wb = new XSSFWorkbook(ExcelFileToRead);
    
    XSSFWorkbook test = new XSSFWorkbook();
    
    XSSFSheet sheet = wb.getSheetAt(0);
    
    Iterator rows = sheet.rowIterator();
    while (rows.hasNext())
    {
      XSSFRow row = (XSSFRow)rows.next();
      Iterator cells = row.cellIterator();
      while (cells.hasNext())
      {
        XSSFCell cell = (XSSFCell)cells.next();
        if (cell.getCellType() == 1) {
          System.out.print(cell.getStringCellValue() + " ");
        } else if (cell.getCellType() == 0) {
          System.out.print(cell.getNumericCellValue() + " ");
        }
      }
      System.out.println();
    }
  }
  
  public static void writeXLSXFile(String excelFileName)
    throws IOException
  {
    String sheetName = "Sheet1";
    
    XSSFWorkbook wb = new XSSFWorkbook();
    XSSFSheet sheet = wb.createSheet(sheetName);
    for (int r = 0; r < 5; r++)
    {
      XSSFRow row = sheet.createRow(r);
      for (int c = 0; c < 5; c++)
      {
        XSSFCell cell = row.createCell(c);
        
        cell.setCellValue("Cell " + r + " " + c);
      }
    }
    FileOutputStream fileOut = new FileOutputStream(excelFileName);
    
    wb.write(fileOut);
    fileOut.flush();
    fileOut.close();
  }
  
  public static void main(String[] args)
    throws IOException
  {}
  
  public Object getObject()
  {
    return this.object;
  }
  
  public void setObject(Object object)
  {
    this.object = object;
  }
  
  public List<Object> getListObject()
  {
    return this.listObject;
  }
  
  public void setListObject(List<Object> listObject)
  {
    this.listObject = listObject;
  }
}
