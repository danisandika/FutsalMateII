/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import model.TbBank;
import model.TbFutsal;
import model.TbSewalapangan;

/**
 *
 * @author Danis
 */
@Stateless
public class TbSewalapanganFacade extends AbstractFacade<TbSewalapangan> {

    @PersistenceContext(unitName = "FutsalMateIIPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TbSewalapanganFacade() {
        super(TbSewalapangan.class);
    }
    
    public List<TbSewalapangan> getLapanganByID(Integer id){
        return em.createQuery("SELECT t FROM TbSewalapangan t WHERE t.idFutsal.idFutsal= :idFutsal ORDER BY t.idSewalapangan DESC")
                .setParameter("idFutsal", id)
                .getResultList();
    }
    
    public TbFutsal getFutsalByIDFutsal(Integer id){
        return  (TbFutsal) em.createQuery("SELECT s FROM TbFutsal s WHERE s.idFutsal = :idFutsal ")
                .setParameter("idFutsal", id)
                .getSingleResult();
    }
    
    
    public List<TbBank> getBank(){
        return em.createNamedQuery("TbBank.findAll",TbBank.class)
                .getResultList();
    }
    
    
    public TbSewalapangan getSewaByID(String id){
        return em.createNamedQuery("TbSewalapangan.findByIdSewalapangan", TbSewalapangan.class)
                .setParameter("idSewalapangan", id)
                .getSingleResult();
    }
    
    public String getLastIDSewa()
    {
        
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");  
        Date date = new Date();
        String ID="SW"+dateFormat.format(date);
        
        
        try{ 
            TbSewalapangan sw = (TbSewalapangan) em.createQuery("SELECT p FROM TbSewalapangan p ORDER BY p.idSewalapangan DESC").getResultList().get(0);
            if(ID.equals(sw.getIdSewalapangan().substring(0,10))) {
                int lastNum = Integer.parseInt(sw.getIdSewalapangan().substring(10)) + 1;
                String numZero = "00";
                return "SW" + dateFormat.format(date)+numZero.substring(String.valueOf(lastNum).length()) + lastNum;
            }
        } catch (NumberFormatException ex) {
            System.out.println("ErrorLastID: " + ex);
            
        }
        return "SW"+dateFormat.format(date)+"01";
    } 
    
    
    
    
}
