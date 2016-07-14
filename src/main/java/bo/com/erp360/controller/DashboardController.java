package bo.com.erp360.controller;

import bo.com.erp360.util.SessionMain;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.inject.Inject;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.BarChartSeries;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.LineChartSeries;

@ManagedBean
public class DashboardController
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private CartesianChartModel combinedModel;
  @Inject
  private SessionMain sessionMain;
  private int countOrdenIngresoNuevos;
  private int countOrdenIngresoProcesados;
  private int countOrdenIngresoTotal;
  private int countOrdenSalidaNuevos;
  private int countOrdenSalidaProcesados;
  private int countOrdenSalidaTotal;
  private int countOrdenTraspasoNuevos;
  private int countOrdenTraspasoProcesados;
  private int countOrdenTraspasoTotal;
  
  @PostConstruct
  public void init()
  {
    createCombinedModel();
  }
  
  public CartesianChartModel getCombinedModel()
  {
    return this.combinedModel;
  }
  
  private void createCombinedModel()
  {
    this.combinedModel = new BarChartModel();
    
    BarChartSeries compra = new BarChartSeries();
    compra.setLabel("Compra");
    
    compra.set("2004", Integer.valueOf(120));
    compra.set("2005", Integer.valueOf(100));
    compra.set("2006", Integer.valueOf(44));
    compra.set("2007", Integer.valueOf(150));
    compra.set("2008", Integer.valueOf(25));
    
    LineChartSeries donacion = new LineChartSeries();
    donacion.setLabel("Donacion");
    
    donacion.set("2004", Integer.valueOf(52));
    donacion.set("2005", Integer.valueOf(60));
    donacion.set("2006", Integer.valueOf(110));
    donacion.set("2007", Integer.valueOf(135));
    donacion.set("2008", Integer.valueOf(120));
    
    LineChartSeries devolucion = new LineChartSeries();
    devolucion.setLabel("Devolucion");
    
    devolucion.set("2004", Integer.valueOf(25));
    devolucion.set("2005", Integer.valueOf(30));
    devolucion.set("2006", Integer.valueOf(55));
    devolucion.set("2007", Integer.valueOf(40));
    devolucion.set("2008", Integer.valueOf(80));
    
    this.combinedModel.addSeries(compra);
    this.combinedModel.addSeries(donacion);
    this.combinedModel.addSeries(devolucion);
    
    this.combinedModel.setTitle("ORDENES DE INGRESO");
    this.combinedModel.setLegendPosition("ne");
    this.combinedModel.setMouseoverHighlight(false);
    this.combinedModel.setShowDatatip(false);
    this.combinedModel.setShowPointLabels(true);
    Axis yAxis = this.combinedModel.getAxis(AxisType.Y);
    yAxis.setMin(Integer.valueOf(0));
    yAxis.setMax(Integer.valueOf(200));
  }
  
  public int getCountOrdenIngresoNuevos()
  {
    return this.countOrdenIngresoNuevos;
  }
  
  public void setCountOrdenIngresoNuevos(int countOrdenIngresoNuevos)
  {
    this.countOrdenIngresoNuevos = countOrdenIngresoNuevos;
  }
  
  public int getCountOrdenIngresoProcesados()
  {
    return this.countOrdenIngresoProcesados;
  }
  
  public void setCountOrdenIngresoProcesados(int countOrdenIngresoProcesados)
  {
    this.countOrdenIngresoProcesados = countOrdenIngresoProcesados;
  }
  
  public int getCountOrdenIngresoTotal()
  {
    return this.countOrdenIngresoTotal;
  }
  
  public void setCountOrdenIngresoTotal(int countOrdenIngresoTotal)
  {
    this.countOrdenIngresoTotal = countOrdenIngresoTotal;
  }
  
  public int getCountOrdenSalidaNuevos()
  {
    return this.countOrdenSalidaNuevos;
  }
  
  public void setCountOrdenSalidaNuevos(int countOrdenSalidaNuevos)
  {
    this.countOrdenSalidaNuevos = countOrdenSalidaNuevos;
  }
  
  public int getCountOrdenSalidaProcesados()
  {
    return this.countOrdenSalidaProcesados;
  }
  
  public void setCountOrdenSalidaProcesados(int countOrdenSalidaProcesados)
  {
    this.countOrdenSalidaProcesados = countOrdenSalidaProcesados;
  }
  
  public int getCountOrdenSalidaTotal()
  {
    return this.countOrdenSalidaTotal;
  }
  
  public void setCountOrdenSalidaTotal(int countOdenSalidaTotal)
  {
    this.countOrdenSalidaTotal = countOdenSalidaTotal;
  }
  
  public int getCountOrdenTraspasoNuevos()
  {
    return this.countOrdenTraspasoNuevos;
  }
  
  public void setCountOrdenTraspasoNuevos(int countOrdenTraspasoNuevos)
  {
    this.countOrdenTraspasoNuevos = countOrdenTraspasoNuevos;
  }
  
  public int getCountOrdenTraspasoProcesados()
  {
    return this.countOrdenTraspasoProcesados;
  }
  
  public void setCountOrdenTraspasoProcesados(int countOrdenTraspasoProcesados)
  {
    this.countOrdenTraspasoProcesados = countOrdenTraspasoProcesados;
  }
  
  public int getCountOrdenTraspasoTotal()
  {
    return this.countOrdenTraspasoTotal;
  }
  
  public void setCountOrdenTraspasoTotal(int countOdenTraspasoTotal)
  {
    this.countOrdenTraspasoTotal = countOdenTraspasoTotal;
  }
}
