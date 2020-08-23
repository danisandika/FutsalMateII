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
public class ChartJSViewAdmin implements Serializable {
     
    
    private LineChartModel lineModel;
    
    private Date currentDate = new Date();
    @EJB
    private TbSewalapanganFacade ejbFacadeSewa;
    
    
    public TbSewalapanganFacade getEjbFacadeSewa() {
        return ejbFacadeSewa;
    }
    
     
    @PostConstruct
    public void init() {
        createLineModel();
    }

    public Date getCurrentDate() {
        return currentDate;
    }
    
     
    public void createLineModel() {
        lineModel = new LineChartModel();
        ChartData data = new ChartData();
        String[] bulan = {"Januari", "Februari", "Maret", "April", "Mei","Juni","Juli","Agustus","September","Oktober","November","Desember"};
        
        LineChartDataSet dataSet = new LineChartDataSet();
        
        //Add Data
        List<Object> values = new ArrayList<>();
        List<Integer> resultList = ejbFacadeSewa.getChartData(getYearNow());
        for(int i=0;i<resultList.size();i++){
                //System.out.println(resultList.get(i));
                values.add(resultList.get(i));
        }
        
        dataSet.setData(values);
        dataSet.setFill(false);
        dataSet.setLabel("Pendapatan dalam bentuk Rupiah");
        dataSet.setBorderColor("rgb(75, 192, 192)");
        dataSet.setLineTension(0.1);
        data.addChartDataSet(dataSet);
        
        //Add Tanggal
        List<String> labels = new ArrayList<>();
        List<Integer> resultListLabel = ejbFacadeSewa.getChartLabel(getYearNow());
        for(int i=0;i<resultListLabel.size();i++){
            //System.out.println(resultListLabel.get(i));
            labels.add(bulan[resultListLabel.get(i)-1]);
            
        }
        
        getYearNow();
        
        data.setLabels(labels);
         
        //Options
        LineChartOptions options = new LineChartOptions();        
        Title title = new Title();
        title.setDisplay(true);
        title.setText("Grafik Pendapatan Website");
        options.setTitle(title);
        
        
        
        lineModel.setOptions(options);
        lineModel.setData(data);
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
     
   
 
    public LineChartModel getLineModel() {
        return lineModel;
    }
 
    public void setLineModel(LineChartModel lineModel) {
        this.lineModel = lineModel;
    }

    
 
}
