/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resourceTambahan;

import controller.TbPemesananFacade;
import controller.TbSewalapanganFacade;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import model.TbPemesanan;
import model.TbPengelola;
import model.TbSewalapangan;
import org.primefaces.event.ItemSelectEvent;
import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.line.LineChartDataSet;
import org.primefaces.model.charts.line.LineChartModel;
import org.primefaces.model.charts.line.LineChartOptions;
import org.primefaces.model.charts.optionconfig.title.Title;



/**
 *
 * @author Danis
 */
@Named
@RequestScoped
public class ChartJSView implements Serializable {
     
    
    private LineChartModel lineModelPemesanan;
    private TbPengelola tbPengelola;
    
    private Date currentDate = new Date();

    @EJB
    private TbPemesananFacade ejbPemesananFacade;
    
    

    public TbPemesananFacade getEjbPemesananFacade() {
        return ejbPemesananFacade;
    }
    
    @PostConstruct
    public void init(){
        
        createLineModelPemesanan();
    }
     
    public Date getCurrentDate() {
        return currentDate;
    }
    
    public void createLineModelPemesanan(){
        
        lineModelPemesanan = new LineChartModel();
        ChartData dataPemesanan = new ChartData();
        String[] bulanPemesanan = {"Januari", "Februari", "Maret", "April", "Mei","Juni","Juli","Agustus","September","Oktober","November","Desember"};
        
        LineChartDataSet dataSetPemesanan = new LineChartDataSet();
        
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
        
        tbPengelola = (TbPengelola) session.getAttribute("loggedPengelola");
        
        //Add Data
        List<Object> valuesP = new ArrayList<>();
        List<Integer> resultListP = ejbPemesananFacade.getChartData(getYearNow(),tbPengelola.getIdFutsal().getIdFutsal());
        for(int i=0;i<resultListP.size();i++){
                //System.out.println(resultListP.get(i));
                valuesP.add(resultListP.get(i));
        }
        
        dataSetPemesanan.setData(valuesP);
        dataSetPemesanan.setFill(false);
        dataSetPemesanan.setLabel("Pendapatan dalam bentuk Rupiah");
        dataSetPemesanan.setBorderColor("rgb(75, 192, 192)");
        dataSetPemesanan.setLineTension(0.1);
        dataPemesanan.addChartDataSet(dataSetPemesanan);
        
        //Add Tanggal
        List<String> labelsP = new ArrayList<>();
        List<Integer> resultListLabelP = ejbPemesananFacade.getChartLabel(getYearNow(),tbPengelola.getIdFutsal().getIdFutsal());
        for(int i=0;i<resultListLabelP.size();i++){
            //System.out.println(resultListLabelP.get(i));
            labelsP.add(bulanPemesanan[resultListLabelP.get(i)-1]);
            
        }
        
        getYearNow();
        
        dataPemesanan.setLabels(labelsP);
         
        //Options
        LineChartOptions optionsP = new LineChartOptions();        
        Title titleP = new Title();
        titleP.setDisplay(true);
        titleP.setText("Grafik Pendapatan dari Pemesanan Futsal");
        optionsP.setTitle(titleP);
        
        
        
        lineModelPemesanan.setOptions(optionsP);
        lineModelPemesanan.setData(dataPemesanan);
    }
     
    
     
    
    public Integer getYearNow(){
        Date date = getCurrentDate(); // your date
        // Choose time zone in which you want to interpret your Date
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Asia/Jakarta"));
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        //System.out.println(year);
        return year;
    }
    
    public void itemSelect(ItemSelectEvent event) {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Item selected",
                "Item Index: " + event.getItemIndex() + ", DataSet Index:" + event.getDataSetIndex());
 
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
     
   
 

    public LineChartModel getLineModelPemesanan() {
        return lineModelPemesanan;
    }

    public void setLineModelPemesanan(LineChartModel lineModelPemesanan) {
        this.lineModelPemesanan = lineModelPemesanan;
    }
 
    
 
}
