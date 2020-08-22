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
import javax.persistence.Query;
import model.TbAdmin;
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
    
    public List<TbSewalapangan> getListSewa(){
        return em.createNamedQuery("TbSewalapangan.findAll")
                .getResultList();
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

        } catch(ArrayIndexOutOfBoundsException ex) { 
            System.out.println("Exception caught in Catch block"); 
            return "SW"+dateFormat.format(date)+"01";
        } 
        return "SW"+dateFormat.format(date)+"01";
    }


    public void ubahStatusPengelola(Integer id,Date tglAkhir) {
        em.createQuery("UPDATE TbPengelola t SET t.status = 1,t.tglBerakhir = :tglAkhir WHERE t.idFutsal.idFutsal = :idFutsal")
                .setParameter("tglAkhir", tglAkhir)
                .setParameter("idFutsal", id)
                .executeUpdate();
    }
    
    

    
    
    public TbAdmin getAdminByID(Integer id){
        return em.createNamedQuery("TbAdmin.findByIdAdmin", TbAdmin.class)
                .setParameter("idAdmin", id)
                .getSingleResult();
    }
    
    
    public String getBatasSewaAkhir(Date batas){
        String jumlah =  em.createQuery("SELECT COUNT(t.idPengelola) FROM TbPengelola t WHERE t.tglBerakhir < :tglAkhir AND t.status = 1")
                .setParameter("tglAkhir", batas)
                .getSingleResult().toString();
        return jumlah;
    }
    
    
    public String getEmailPengellaFromFutsal(Integer id){
        return em.createQuery("SELECT t.email FROM TbPengelola t WHERE t.idFutsal.idFutsal = :idFutsal")
                .setParameter("idFutsal", id)
                .getSingleResult().toString();
    }
    
    
    public List<Integer>  getChartData(Integer year){
        List<Integer> result = em.createQuery("SELECT SUM(t.jumlahUang) as jumlah FROM TbSewalapangan t WHERE FUNCTION('YEAR',t.tglPembayaran)= :Year GROUP BY FUNCTION('MONTH',t.tglPembayaran)")
                .setParameter("Year", year)
                .getResultList();
        return result;
    }
    
    public List<Integer>  getChartLabel(Integer year){
        List<Integer> result = em.createQuery("SELECT FUNCTION('MONTH',t.tglPembayaran) as bulan FROM TbSewalapangan t WHERE FUNCTION('YEAR',t.tglPembayaran)= :Year GROUP BY FUNCTION('MONTH',t.tglPembayaran)")
                .setParameter("Year", year)
                .getResultList();
        return result;
    }

    
    public List<TbSewalapangan> getSewaFilterByDate(Date awal,Date akhir){
        return em.createQuery("SELECT t FROM TbSewalapangan t WHERE t.tglPembayaran BETWEEN :DateAwal AND :DateAkhir")
                .setParameter("DateAwal", awal)
                .setParameter("DateAkhir", akhir)
                .getResultList();
        
    }
}
