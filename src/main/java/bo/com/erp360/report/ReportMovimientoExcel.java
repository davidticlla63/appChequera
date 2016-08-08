package bo.com.erp360.report;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import bo.com.erp360.util.Conexion;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;

@WebServlet("/ReportMovimientoExcel")
public class ReportMovimientoExcel extends HttpServlet {

	private static final long serialVersionUID = -4777861118160332227L;

	@Inject
	private EntityManager em;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		ServletOutputStream servletOutputStream = response.getOutputStream();
		Connection conn = null;
		JasperReport jasperReport;
		JasperPrint jasperPrint;

		Statement stmt = null;
		ResultSet rs = null;

		try {
			Context ctx = new InitialContext();
			DataSource ds = (DataSource) ctx
					.lookup(Conexion.datasourse);
			conn = ds.getConnection();

			if (conn != null) {
				System.out.println("Conexion Exitosa JDBC com.edb.Driver...");
			} else {
				System.out.println("Error Conexion JDBC com.edb.Driver...");
			}

		} catch (Exception e) {
			System.out.println("Error al conectar JDBC: " + e.getMessage());
		}

		try {

			try {
//				Integer pIdSucursal = Integer.parseInt(request.getParameter("pIdSucursal"));
				Integer pIdEmpresa = Integer.parseInt(request.getParameter("pIdEmpresa"));
				String pNombreEmpresa = request.getParameter("pNombreEmpresa");
				Integer pFechaInicio = Integer.parseInt(request.getParameter("pFechaInicio"));
				Integer pFechaFin = Integer.parseInt(request.getParameter("pFechaFin"));
				String pFechaInicioTxt = request.getParameter("pFechaInicioTxt");
				String pFechaFinTxt = request.getParameter("pFechaFinTxt");
				
				System.out.println("Conexion em: " + em.isOpen());

				String realPath = request.getRealPath("/");
				System.out.println("Real Path: " + realPath);

				// load JasperDesign from XML and compile it into
				// JasperReport
				System.out.println("Context getServletContext: "
						+ request.getServletContext().getContextPath());
				System.out.println("Context getServletPath: "
						+ request.getServletPath());
				System.out
				.println("Context getSession().getServletContext(): "+ request.getSession().getServletContext()
						.getRealPath("/"));

				String urlPath = request.getRequestURL().toString();
				urlPath = urlPath.substring(0, urlPath.length()- request.getRequestURI().length())+ request.getContextPath() + "/";
				System.out.println("URL ::::: " + urlPath);

				String URL_SERVLET_LOGO = urlPath+"ServletImageLogo?id="+1+"&type=EMPRESA";

				System.out.println("URL_SERVLET_LOGO: " + URL_SERVLET_LOGO);
				// create a map of parameters to pass to the report.
				Map parameters = new HashMap();
//				parameters.put("ID_SUCURSAL", pIdSucursal);
				//parameters.put("pLogo", URL_SERVLET_LOGO);
				parameters.put("ID_EMPRESA", pIdEmpresa);
				parameters.put("NOMBRE_EMPRESA", pNombreEmpresa);
				parameters.put("FECHA_FIN", pFechaFin);
				parameters.put("FECHA_FIN_TEX", pFechaFinTxt);
				parameters.put("FECHA_INICIO", pFechaInicio);
				parameters.put("FECHA_INICIO_TEX", pFechaInicioTxt);

				String rutaReporte =  urlPath + "resources/report/tesoreria/reportCheques.jasper";
				
				System.out.println("Parametros : " + parameters.toString());
				System.out.println("rutaReporte: " + rutaReporte);


				// find file .jasper
				jasperReport = (JasperReport) JRLoader.loadObject(new URL(
						rutaReporte));

				// fill JasperPrint using fillReport() method
				jasperPrint = JasperFillManager.fillReport(jasperReport,parameters, conn);

				// -------------------------------------------------------
				ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
				JRXlsExporter exporterXLS = new JRXlsExporter();

				exporterXLS.setParameter(JRXlsExporterParameter.IS_FONT_SIZE_FIX_ENABLED,Boolean.TRUE);
				exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);
				exporterXLS.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
				exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
				exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
				exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_COLUMNS, Boolean.TRUE);
				exporterXLS.setParameter(JRXlsExporterParameter.IS_COLLAPSE_ROW_SPAN, Boolean.TRUE);
				exporterXLS.setParameter(JRXlsExporterParameter.IGNORE_PAGE_MARGINS, Boolean.TRUE);
				exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
				exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, byteArrayOutputStream);
				exporterXLS.setParameter(JRXlsExporterParameter.IS_IGNORE_CELL_BORDER, Boolean.TRUE);
				
				try {
					exporterXLS.exportReport();
				} catch (JRException ex) {
					System.out.println("No se pudo crear el excel..."+ex.getMessage());
				}

				response.setContentType("application/vnd.ms-excel");
				response.setContentLength(byteArrayOutputStream.toByteArray().length);
				response.setHeader("Content-disposition","attachment; filename=\"LibroVentasDaVinci.xls\"");


				servletOutputStream = response.getOutputStream();
				servletOutputStream.write( byteArrayOutputStream.toByteArray());
				servletOutputStream.flush();
				servletOutputStream.close();

				conn.close();
	    	
			} catch (Exception e) {
				// display stack trace in the browser
				e.printStackTrace();
				System.out.println("Error en ReporteLibroVentasDaVinci: " + e.getMessage());
				StringWriter stringWriter = new StringWriter();
				PrintWriter printWriter = new PrintWriter(stringWriter);
				e.printStackTrace(printWriter);
				response.setContentType("text/plain");
				response.getOutputStream().print(stringWriter.toString());			
			} 
			}finally{
				try{
					if(!conn.isClosed()){
						System.out.println("cerrando conexion...");
						conn.close();
					}
				}catch(Exception e){
					System.out.println("No se pudo cerrar la conexion, Error: "+e.getMessage());
				}
			}

		}
		
	}
