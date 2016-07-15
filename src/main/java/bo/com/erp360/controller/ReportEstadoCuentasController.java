package bo.com.erp360.controller;

import bo.com.erp360.dao.BancoDao;
import bo.com.erp360.dao.CuentaDao;
import bo.com.erp360.dao.MovimientoCuentasDao;
import bo.com.erp360.model.Banco;
import bo.com.erp360.model.Cuenta;
import bo.com.erp360.model.Empresa;
import bo.com.erp360.model.MovimientoCuentas;
import bo.com.erp360.model.Usuario;
import bo.com.erp360.util.FacesUtil;
import bo.com.erp360.util.SessionMain;
import bo.com.erp360.util.Time;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.Serializable;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.event.Event;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.richfaces.cdi.push.Push;

@Named("reportEstadoCuentasController")
@ConversationScoped
public class ReportEstadoCuentasController implements Serializable {
	private static final long serialVersionUID = -7962140858225961561L;
	public static final String PUSH_CDI_TOPIC = "pushCdi";
	@Inject
	@Push(topic = "pushCdi")
	Event<String> pushEventCuenta;
	@Inject
	private FacesContext facesContext;
	@Inject
	Conversation conversation;
	@Inject
	private CuentaDao cuentaDao;
	@Inject
	private BancoDao bancoDao;
	@Inject
	private MovimientoCuentasDao movimientoCuentasDao;
	private Date fechaInicio = Time.getPrimerDiaDelMes(new Date());
	private Date fechaFin = Time.getUltimoDiaDelMes(new Date());
	private String nombreEstado = "ACTIVO";
	private Cuenta selectedCuentas;
	private List<Cuenta> listCuentas = new ArrayList();
	private List<MovimientoCuentas> listMovimientoCuentas = new ArrayList();
	@Inject
	private SessionMain sessionMain;
	private Usuario usuarioLogin;
	private Empresa empresaLogin;
	private String urlReport;
	private StreamedContent streamedUrlExcel;
	private StreamedContent streamedUrlPdf;

	@PostConstruct
	public void initNewCuentas() {
		System.out.println(" init new initNewCuenta");
		this.usuarioLogin = this.sessionMain.getUsuarioLogin();
		this.empresaLogin = this.sessionMain.getEmpresaLogin();
		loadDefault();
	}

	private void loadDefault() {
		System.out.println("Incio loadDefault()");

		this.selectedCuentas = new Cuenta();
		this.listCuentas = this.cuentaDao.obtenerOrdenDescPorId();
	}

	public void initConversation() {
		if ((!FacesContext.getCurrentInstance().isPostback())
				&& (this.conversation.isTransient())) {
			this.conversation.begin();
			System.out.println(">>>>>>>>>> CONVERSACION INICIADA...");
		}
	}

	public String endConversation() {
		if (!this.conversation.isTransient()) {
			this.conversation.end();
			System.out.println(">>>>>>>>>> CONVERSACION TERMINADA...");
		}
		return "kardex_producto.xhtml?faces-redirect=true";
	}

	public void consultar() {
		try {
			System.out.println("Ingreso a consultar");
			this.listMovimientoCuentas = this.movimientoCuentasDao
					.obtenerTodoMovimientosSobreFechasPorCuenta(
							this.selectedCuentas, this.empresaLogin,
							this.fechaInicio, this.fechaFin);
		} catch (Exception e) {
			FacesUtil.infoMessage("Error en consiltar", "MovimientoCuentas "
					+ this.listMovimientoCuentas.size());
		}
	}

	public String obtenerUrMovimiento() {
		HttpServletRequest request = (HttpServletRequest) this.facesContext
				.getExternalContext().getRequest();
		String urlPath = request.getRequestURL().toString();
		urlPath = urlPath.substring(0, urlPath.length()
				- request.getRequestURI().length())
				+ request.getContextPath() + "/";
		String urlPDFreporte = urlPath + "ReportMovimiento?pIdEmpresa="
				+ this.empresaLogin.getId() + "&pNombreEmpresa="
				+ this.empresaLogin.getRazonSocial() + "&pFechaInicio="
				+ Time.obtenerFormatoYYYYMMDD(this.fechaInicio) + "&pFechaFin="
				+ Time.obtenerFormatoYYYYMMDD(this.fechaFin)
				+ "&pFechaInicioTxt="
				+ Time.convertSimpleDateToString(this.fechaInicio)
				+ "&pFechaFinTxt="
				+ Time.convertSimpleDateToString(this.fechaFin);
		return urlPDFreporte;
	}

	public void setStreamedUrlExcel(StreamedContent streamedUrlExcel) {
		this.streamedUrlExcel = streamedUrlExcel;
	}

	public StreamedContent getStreamedUrlExcel() {
		try {
			HttpServletRequest request = (HttpServletRequest) this.facesContext
					.getExternalContext().getRequest();
			String urlPath = request.getRequestURL().toString();
			urlPath = urlPath.substring(0, urlPath.length()
					- request.getRequestURI().length())
					+ request.getContextPath() + "/";
			String urlPDFreporte = urlPath
					+ "ReportEstadoCuentasExcel?pIdEmpresa="
					+ URLEncoder.encode(
							new StringBuilder().append(
									this.empresaLogin.getId()).toString(),
							"UTF-8")
					+ "&pIdCuenta="
					+ URLEncoder.encode(
							new StringBuilder().append(
									this.selectedCuentas.getId()).toString(),
							"UTF-8")
					+ "&pNombreEmpresa="
					+ URLEncoder.encode(
							new StringBuilder().append(
									this.empresaLogin.getRazonSocial())
									.toString(), "ISO-8859-1")
					+ "&pFechaInicio="
					+ URLEncoder
							.encode(new StringBuilder()
									.append(Time
											.obtenerFormatoYYYYMMDD(this.fechaInicio))
									.toString(), "UTF-8")
					+ "&pFechaFin="
					+

					URLEncoder.encode(
							new StringBuilder().append(
									Time.obtenerFormatoYYYYMMDD(this.fechaFin))
									.toString(), "UTF-8")
					+ "&pFechaInicioTxt="
					+ URLEncoder
							.encode(new StringBuilder()
									.append(Time
											.convertSimpleDateToString(this.fechaInicio))
									.toString(), "ISO-8859-1")
					+ "&pFechaFinTxt="
					+ URLEncoder
							.encode(new StringBuilder()
									.append(Time
											.convertSimpleDateToString(this.fechaFin))
									.toString(), "ISO-8859-1");
			System.out.println(urlPDFreporte);
			URL url = new URL(urlPDFreporte);

			InputStream is1 = url.openStream();
			File f = stream2fileExcel(is1);
			System.out.println("Size Bytes: " + f.length());
			InputStream stream = new FileInputStream(f);
			this.streamedUrlExcel = new DefaultStreamedContent(stream,
					"application/vnd.ms-excel", "LibroEstadoCuentas.xls");

			return this.streamedUrlExcel;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error en getStreamedLibroVentas: "
					+ e.getMessage());
		}
		return null;
	}

	public StreamedContent getStreamedUrlPdf() {
		try {
			HttpServletRequest request = (HttpServletRequest) this.facesContext
					.getExternalContext().getRequest();
			String urlPath = request.getRequestURL().toString();
			urlPath = urlPath.substring(0, urlPath.length()
					- request.getRequestURI().length())
					+ request.getContextPath() + "/";
			String urlPDFreporte = urlPath
					+ "ReportEstadoCuentasPdf?pIdEmpresa="
					+ URLEncoder.encode(
							new StringBuilder().append(
									this.empresaLogin.getId()).toString(),
							"UTF-8")
					+ "&pIdCuenta="
					+ URLEncoder.encode(
							new StringBuilder().append(
									this.selectedCuentas.getId()).toString(),
							"UTF-8")
					+ "&pNombreEmpresa="
					+ URLEncoder.encode(
							new StringBuilder().append(
									this.empresaLogin.getRazonSocial())
									.toString(), "ISO-8859-1")
					+ "&pFechaInicio="
					+ URLEncoder
							.encode(new StringBuilder()
									.append(Time
											.obtenerFormatoYYYYMMDD(this.fechaInicio))
									.toString(), "UTF-8")
					+ "&pFechaFin="
					+

					URLEncoder.encode(
							new StringBuilder().append(
									Time.obtenerFormatoYYYYMMDD(this.fechaFin))
									.toString(), "UTF-8")
					+ "&pFechaInicioTxt="
					+ URLEncoder
							.encode(new StringBuilder()
									.append(Time
											.convertSimpleDateToString(this.fechaInicio))
									.toString(), "ISO-8859-1")
					+ "&pFechaFinTxt="
					+ URLEncoder
							.encode(new StringBuilder()
									.append(Time
											.convertSimpleDateToString(this.fechaFin))
									.toString(), "ISO-8859-1");
			System.out.println(urlPDFreporte);
			URL url = new URL(urlPDFreporte);

			InputStream is1 = url.openStream();
			File f = stream2file(is1);
			System.out.println("Size Bytes: " + f.length());
			InputStream stream = new FileInputStream(f);
			this.streamedUrlPdf = new DefaultStreamedContent(stream,
					"application/pdf", "LibroEstadoCuentas.pdf");
			return this.streamedUrlPdf;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error en getStreamedLibroVentas: "
					+ e.getMessage());
		}
		return null;
	}

	private static File stream2file(InputStream in) throws IOException {

		final File tempFile = File.createTempFile("Reporte", ".pdf");
		tempFile.deleteOnExit();

		try (FileOutputStream out = new FileOutputStream(tempFile)) {
			IOUtils.copy(in, out);
		}

		return tempFile;
	}

	private static File stream2fileTXT(InputStream in) throws IOException {

		final File tempFile = File.createTempFile("Reporte", ".txt");
		tempFile.deleteOnExit();

		try (FileOutputStream out = new FileOutputStream(tempFile)) {
			IOUtils.copy(in, out);
		}

		return tempFile;
	}

	private static File stream2fileExcel(InputStream in) throws IOException {

		final File tempFile = File.createTempFile("Reporte", ".xls");
		tempFile.deleteOnExit();

		try (FileOutputStream out = new FileOutputStream(tempFile)) {
			IOUtils.copy(in, out);
		}

		return tempFile;
	}

	public void ArmarReportExcel() {
		System.out.println("Ingreso a ArmarReportExcel");
		this.urlReport = obtenerUrMovimiento();
	}

	// ONCOMPLETETEXT Cuenta
	public List<Cuenta> completeCuenta(String query) {
		listCuentas = new ArrayList<Cuenta>();// reset
		List<Cuenta> results = new ArrayList<Cuenta>();
		List<Banco> listbBancos = bancoDao.obtenerPorNombreConsulta(query
				.toUpperCase());
		for (Banco banco : listbBancos) {
			listCuentas = cuentaDao.obtenerPorBanco(banco);
			for (Cuenta i : listCuentas) {
				if (i.getBanco().getNombre().toUpperCase()
						.startsWith(query.toUpperCase())) {
					results.add(i);
				}
			}
		}
		listCuentas = results;
		return results;
	}

	public void onRowSelectCuentaClick(SelectEvent event) {
		Integer idChequera = Integer.parseInt(event.getObject().toString());
		System.out
				.println("Ingreso a onRowSelectChequeraClick : " + idChequera);
		for (Cuenta i : listCuentas) {
			System.out.println(i.getBanco().getNombre());
			if (i.getId().intValue() == idChequera.intValue()) {
				selectedCuentas = i;
				return;
			}
		}
	}

	public Cuenta getSelectedCuentas() {
		return this.selectedCuentas;
	}

	public void setSelectedCuentas(Cuenta selectedCuentas) {
		this.selectedCuentas = selectedCuentas;
	}

	public String getNombreEstado() {
		return this.nombreEstado;
	}

	public void setNombreEstado(String nombreEstado) {
		this.nombreEstado = nombreEstado;
	}

	public Date getFechaInicio() {
		return this.fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return this.fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public List<Cuenta> getListCuentas() {
		return this.listCuentas;
	}

	public void setListCuentas(List<Cuenta> listCuentas) {
		this.listCuentas = listCuentas;
	}

	public List<MovimientoCuentas> getListMovimientoCuentas() {
		return this.listMovimientoCuentas;
	}

	public void setListMovimientoCuentas(
			List<MovimientoCuentas> listMovimientoCuentas) {
		this.listMovimientoCuentas = listMovimientoCuentas;
	}

	public String getUrlReport() {
		return this.urlReport;
	}

	public void setUrlReport(String urlReport) {
		this.urlReport = urlReport;
	}

	public void setStreamedUrlPdf(StreamedContent streamedUrlPdf) {
		this.streamedUrlPdf = streamedUrlPdf;
	}
}
