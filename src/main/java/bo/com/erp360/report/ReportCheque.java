package bo.com.erp360.report;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

@WebServlet("/ReportCheque")
public class ReportCheque extends HttpServlet {


	/**
	 * 
	 */
	private static final long serialVersionUID = -6308433847370414322L;
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
				/*String pNombreEmpresa = request.getParameter("pNombreEmpresa");*/
				Integer pIdCheque = Integer.parseInt(request.getParameter("pIdCheque"));
				
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
				/*parameters.put("NOMBRE_EMPRESA", pNombreEmpresa);*/
				parameters.put("id", pIdCheque);

				String rutaReporte =  urlPath + "resources/report/tesoreria/reportCheque.jasper";
				
				System.out.println("Parametros : " + parameters.toString());
				System.out.println("rutaReporte: " + rutaReporte);


				// find file .jasper
				jasperReport = (JasperReport) JRLoader.loadObject(new URL(
						rutaReporte));

				// fill JasperPrint using fillReport() method
				jasperPrint = JasperFillManager.fillReport(jasperReport,
						parameters, conn);

				response.setContentType("application/pdf");
				JasperExportManager.exportReportToPdfStream(jasperPrint,
						servletOutputStream);

				servletOutputStream.flush();
				servletOutputStream.close();

			} catch (Exception e) {
				// display stack trace in the browser
				e.printStackTrace();
				System.out.println("Error al ingresar JasperReportServlet: "
						+ e.getMessage());
				StringWriter stringWriter = new StringWriter();
				PrintWriter printWriter = new PrintWriter(stringWriter);
				e.printStackTrace(printWriter);
				response.setContentType("text/plain");
				response.getOutputStream().print(stringWriter.toString());

			}

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

}
