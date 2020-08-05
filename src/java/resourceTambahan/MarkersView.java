/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resourceTambahan;

import controller.TbFutsalFacade;
import java.io.Serializable;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import model.TbFutsal;
import org.primefaces.event.map.PointSelectEvent;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

/**
 *
 * @author Danis
 */

@Named
@RequestScoped
public class MarkersView implements Serializable {
    
    @EJB
    TbFutsalFacade futsalFacade;
    
    private MapModel simpleModel;
  
    @PostConstruct
    public void init() {
        simpleModel = new DefaultMapModel();
        

        for(TbFutsal futsal:futsalFacade.findAll()){
            //Shared coordinates
           
            //Basic marker
            simpleModel.addOverlay(new Marker(new LatLng(futsal.getLatitude(), futsal.getLongitude()), futsal.getNamaFutsal()));
        }

         
    }
    

    public MapModel getSimpleModel() {
        return simpleModel;
    }
    
    
    
} 

