package bo.com.erp360.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFHeader;
import org.apache.poi.hssf.usermodel.HeaderFooter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Footer;
import org.apache.poi.ss.usermodel.Header;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFPrintSetup;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelGenerateReport
{
  private CellStyle cs = null;
  private CellStyle csBold = null;
  private CellStyle csTop = null;
  private CellStyle csRight = null;
  private CellStyle csBottom = null;
  private CellStyle csLeft = null;
  private CellStyle csTopLeft = null;
  private CellStyle csTopRight = null;
  private CellStyle csBottomLeft = null;
  private CellStyle csBottomRight = null;
  Logger log = Logger.getLogger(ExcelGenerateReport.class);
  
  public static void main(String[] args)
  {
    ExcelGenerateReport myReport = new ExcelGenerateReport();
    myReport.createExcel();
  }
  
  public void createExcel()
  {
    try
    {
      XSSFWorkbook wb = new XSSFWorkbook();
      XSSFSheet sheet = wb.createSheet("My Excel Report");
      
      sheet.getPrintSetup().setLandscape(true);
      sheet.getPrintSetup().setPaperSize(
        (short)5);
      sheet.setDisplayGridlines(true);
      
      setCellStyles(wb);
      
      Date date = new Date(System.currentTimeMillis());
      DateFormat df = new SimpleDateFormat("MM/dd/yy HH:mm:ss");
      
      sheet.setColumnWidth(0, 1000);
      sheet.setColumnWidth(1, 2500);
      sheet.setColumnWidth(2, 2500);
      sheet.setColumnWidth(3, 2500);
      sheet.setColumnWidth(4, 2500);
      sheet.setColumnWidth(5, 4000);
      sheet.setColumnWidth(6, 4000);
      sheet.setColumnWidth(7, 2000);
      sheet.setColumnWidth(8, 2000);
      sheet.setColumnWidth(9, 2000);
      sheet.setColumnWidth(10, 2000);
      sheet.setColumnWidth(11, 2000);
      sheet.setColumnWidth(12, 2000);
      sheet.setColumnWidth(13, 2000);
      sheet.setColumnWidth(14, 2000);
      sheet.setColumnWidth(15, 2000);
      
      Header header = sheet.getHeader();
      header.setLeft("*** ORIGINAL COPY ***");
      header.setCenter(HSSFHeader.font("Arial", "Bold") + 
        HSSFHeader.fontSize((short)14) + "LIBRO DE VENTA");
      header.setRight(df.format(date));
      
      Footer footer = sheet.getFooter();
      footer.setRight("Page " + HeaderFooter.page() + " of " + 
        HeaderFooter.numPages());
      
      int rowIndex = 0;
      rowIndex = insertHeaderInfo(sheet, rowIndex);
      rowIndex = insertDetailInfo(sheet, rowIndex);
      
      FileOutputStream fileOut = null;
      File folder = new File("archivo");
      if (!folder.exists())
      {
        folder.mkdirs();
        System.out.println(folder.getAbsolutePath());
      }
      fileOut = new FileOutputStream("archivo/myReport.xlsx");
      wb.write(fileOut);
      fileOut.close();
    }
    catch (Exception e)
    {
      System.out.println(e);
    }
  }
  
  private void setCellStyles(XSSFWorkbook wb)
  {
    Font f = wb.createFont();
    f.setFontHeightInPoints((short)7);
    
    this.cs = wb.createCellStyle();
    this.cs.setFont(f);
    
    Font bold = wb.createFont();
    bold.setBoldweight((short)700);
    bold.setFontHeightInPoints((short)7);
    
    this.csBold = wb.createCellStyle();
    this.csBold.setBorderBottom((short)1);
    this.csBold.setBottomBorderColor(IndexedColors.BLACK.getIndex());
    this.csBold.setFont(bold);
    
    this.csTop = wb.createCellStyle();
    this.csTop.setBorderTop((short)1);
    this.csTop.setTopBorderColor(IndexedColors.BLACK.getIndex());
    this.csTop.setFont(f);
    
    this.csRight = wb.createCellStyle();
    this.csRight.setBorderRight((short)1);
    this.csRight.setRightBorderColor(IndexedColors.BLACK.getIndex());
    this.csRight.setFont(f);
    
    this.csBottom = wb.createCellStyle();
    this.csBottom.setBorderBottom((short)1);
    this.csBottom.setBottomBorderColor(IndexedColors.BLACK.getIndex());
    this.csBottom.setFont(f);
    
    this.csLeft = wb.createCellStyle();
    this.csLeft.setBorderLeft((short)1);
    this.csLeft.setLeftBorderColor(IndexedColors.BLACK.getIndex());
    this.csLeft.setFont(f);
    
    this.csTopLeft = wb.createCellStyle();
    this.csTopLeft.setBorderTop((short)1);
    this.csTopLeft.setTopBorderColor(IndexedColors.BLACK.getIndex());
    this.csTopLeft.setBorderLeft((short)1);
    this.csTopLeft.setLeftBorderColor(IndexedColors.BLACK.getIndex());
    this.csTopLeft.setFont(f);
    
    this.csTopRight = wb.createCellStyle();
    this.csTopRight.setBorderTop((short)1);
    this.csTopRight.setTopBorderColor(IndexedColors.BLACK.getIndex());
    this.csTopRight.setBorderRight((short)1);
    this.csTopRight.setRightBorderColor(IndexedColors.BLACK.getIndex());
    this.csTopRight.setFont(f);
    
    this.csBottomLeft = wb.createCellStyle();
    this.csBottomLeft.setBorderBottom((short)1);
    this.csBottomLeft.setBottomBorderColor(IndexedColors.BLACK.getIndex());
    this.csBottomLeft.setBorderLeft((short)1);
    this.csBottomLeft.setLeftBorderColor(IndexedColors.BLACK.getIndex());
    this.csBottomLeft.setFont(f);
    
    this.csBottomRight = wb.createCellStyle();
    this.csBottomRight.setBorderBottom((short)1);
    this.csBottomRight.setBottomBorderColor(IndexedColors.BLACK.getIndex());
    this.csBottomRight.setBorderRight((short)1);
    this.csBottomRight.setRightBorderColor(IndexedColors.BLACK.getIndex());
    this.csBottomRight.setFont(f);
  }
  
  private int insertHeaderInfo(XSSFSheet sheet, int index)
  {
    int rowIndex = index;
    Row row = null;
    Cell c = null;
    
    rowIndex++;
    
    row = sheet.createRow(rowIndex);
    c = row.createCell(1);
    c.setCellValue("PERIODO:");
    c.setCellStyle(this.cs);
    
    rowIndex++;
    row = sheet.createRow(rowIndex);
    c = row.createCell(1);
    c.setCellValue("AÑO:");
    c.setCellStyle(this.cs);
    c = row.createCell(2);
    c.setCellStyle(this.cs);
    c = row.createCell(3);
    c.setCellStyle(this.csTopLeft);
    c = row.createCell(4);
    c.setCellValue("MES:");
    c.setCellStyle(this.csLeft);
    c = row.createCell(5);
    c.setCellStyle(this.csTopLeft);
    c = row.createCell(6);
    c.setCellStyle(this.csLeft);
    
    rowIndex++;
    row = sheet.createRow(rowIndex);
    c = row.createCell(1);
    c.setCellStyle(this.cs);
    c = row.createCell(2);
    c.setCellStyle(this.cs);
    c = row.createCell(3);
    c.setCellStyle(this.csTop);
    c = row.createCell(4);
    c.setCellStyle(this.cs);
    c = row.createCell(5);
    c.setCellStyle(this.csTop);
    c = row.createCell(6);
    c.setCellStyle(this.cs);
    
    rowIndex++;
    row = sheet.createRow(rowIndex);
    c = row.createCell(1);
    c.setCellValue("NOMBRE O \t\t RAZON SOCIAL:");
    
    c.setCellStyle(this.cs);
    c = row.createCell(2);
    c.setCellStyle(this.cs);
    c = row.createCell(3);
    c.setCellStyle(this.csTopLeft);
    c = row.createCell(4);
    c.setCellValue("NIT:");
    c.setCellStyle(this.csLeft);
    c = row.createCell(5);
    c.setCellStyle(this.csTopLeft);
    c = row.createCell(6);
    c.setCellStyle(this.csLeft);
    
    rowIndex++;
    row = sheet.createRow(rowIndex);
    c = row.createCell(1);
    c.setCellStyle(this.cs);
    c = row.createCell(2);
    c.setCellStyle(this.cs);
    c = row.createCell(3);
    c.setCellStyle(this.csTop);
    c = row.createCell(4);
    c.setCellStyle(this.cs);
    c = row.createCell(5);
    c.setCellStyle(this.csTop);
    c = row.createCell(6);
    c.setCellStyle(this.cs);
    
    rowIndex += 3;
    row = sheet.createRow(rowIndex);
    c = row.createCell(0);
    c.setCellValue("NRO.");
    c.setCellStyle(this.csTopLeft);
    
    c = row.createCell(1);
    c.setCellValue("FECHA \t\tFACTURA");
    c.setCellStyle(this.csTopLeft);
    
    c = row.createCell(2);
    c.setCellValue("NRO. \t\tFACTURA");
    c.setCellStyle(this.csTopLeft);
    
    c = row.createCell(3);
    c.setCellValue("NRO. \t\tAUTORIZACION");
    c.setCellStyle(this.csTopLeft);
    
    c = row.createCell(4);
    c.setCellValue("ESTADO");
    c.setCellStyle(this.csTopLeft);
    
    c = row.createCell(5);
    c.setCellValue("NIT/CI \t\tCLIENTE");
    c.setCellStyle(this.csTopLeft);
    
    c = row.createCell(6);
    c.setCellValue("NOMBRE O \t\t RAZON SOCIAL");
    c.setCellStyle(this.csTopLeft);
    
    c = row.createCell(7);
    c.setCellValue("IMPORTE \t\t TOTAL DE \t\t VENTA");
    c.setCellStyle(this.csTopLeft);
    
    c = row.createCell(8);
    c.setCellValue("IMPORTE \t\t ICE/IEHD \t\t TASAS");
    c.setCellStyle(this.csTopLeft);
    
    c = row.createCell(9);
    c.setCellValue("EXPORTACIONES \t\t Y OPERACIONES");
    c.setCellStyle(this.csTopLeft);
    
    c = row.createCell(10);
    c.setCellValue("VENTAS \t\t GRABADAS \t\t TASA CERO");
    c.setCellStyle(this.csTopLeft);
    
    c = row.createCell(11);
    c.setCellValue("SUB TOTAL");
    c.setCellStyle(this.csTopLeft);
    
    c = row.createCell(12);
    c.setCellValue("DESCUENTOS \t\t Y \t\t BONIFICACIONES");
    c.setCellStyle(this.csTopLeft);
    
    c = row.createCell(13);
    c.setCellValue("IMPORTE \t\t BASE PARA \t\t DEBITO FISCAL");
    c.setCellStyle(this.csTopLeft);
    
    c = row.createCell(14);
    c.setCellValue("DEBITO \t\t FISCAL");
    c.setCellStyle(this.csTopLeft);
    
    c = row.createCell(15);
    c.setCellValue("CODIGO \t\t CONTROL");
    c.setCellStyle(this.csTopLeft);
    
    return rowIndex;
  }
  
  private int insertDetailInfo(XSSFSheet sheet, int index)
  {
    int rowIndex = 0;
    Row row = null;
    Cell c = null;
    for (int i = 1; i < 35; i++)
    {
      rowIndex = index + i;
      row = sheet.createRow(rowIndex);
      c = row.createCell(0);
      c.setCellValue(i);
      c.setCellStyle(this.cs);
      c = row.createCell(1);
      c.setCellValue(10 + i);
      c.setCellStyle(this.cs);
      c = row.createCell(2);
      c.setCellValue("ITEM" + i);
      c.setCellStyle(this.cs);
      c = row.createCell(3);
      c.setCellValue("My ITEM" + i + " Decscription");
      c.setCellStyle(this.cs);
      c = row.createCell(4);
      c.setCellValue(1.11D * i);
      c.setCellStyle(this.cs);
    }
    System.out.println("FINALIZO EL FOR" + row.getRowNum());
    return rowIndex;
  }
}
