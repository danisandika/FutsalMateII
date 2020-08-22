/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import model.TbKonfirmasi;
import model.TbPemesanan;
import model.TbPengelola;

/**
 *
 * @author Danis
 */
@Stateless
public class TbPemesananFacade extends AbstractFacade<TbPemesanan> {

    @PersistenceContext(unitName = "FutsalMateIIPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TbPemesananFacade() {
        super(TbPemesanan.class);
    }
    
    public List<TbPemesanan> getPemesanan(Integer id,Integer sts){
        return em.createQuery("SELECT p FROM TbPemesanan p,TbLapangan l WHERE p.idLapangan.idLapangan = l.idLapangan AND l.idFutsal.idFutsal = :idFutsal AND p.status = :status")
                .setParameter("idFutsal", id)
                .setParameter("status", sts)
                .getResultList();
    }
    
    public List<TbPemesanan> getRiwayatPemesanan(Integer id){
        return em.createQuery("SELECT p FROM TbPemesanan p,TbLapangan l WHERE p.idLapangan.idLapangan = l.idLapangan AND l.idFutsal.idFutsal = :idFutsal AND p.status = 2 OR p.status = 3")
                .setParameter("idFutsal", id)
                .getResultList();
    }
    
    
    public TbPemesanan getPemesananByIDPemesanan(String id){
        return em.createNamedQuery("TbPemesanan.findByIdPemesanan", TbPemesanan.class)
                .setParameter("idPemesanan", id)
                .getSingleResult();
    }
    
    
    public TbKonfirmasi getKonfirmasiByIDPemesanan(String id){
        return (TbKonfirmasi) em.createQuery("SELECT k FROM TbKonfirmasi k WHERE k.idPemesanan.idPemesanan = :idPemesanan")
                .setParameter("idPemesanan", id)
                .getSingleResult();
    }
    
    public List<TbPemesanan> getPemesananByIDPemain(Integer id){
        return em.createQuery("SELECT t FROM TbPemesanan t WHERE t.idPemain.idPemain = :idPemain")
                .setParameter("idPemain", id)
                .getResultList();
    }
    
    public List<Integer>  getChartData(Integer year,Integer id){
        List<Integer> result = em.createQuery("SELECT SUM(t.jumlahBayar) as jumlah FROM TbKonfirmasi t WHERE FUNCTION('YEAR',t.idPemesanan.tglPemesanan)= :Year AND t.idPemesanan.status > 1 AND t.idPemesanan.idLapangan.idFutsal.idFutsal = :idFutsal GROUP BY FUNCTION('MONTH',t.idPemesanan.tglPemesanan)")
                .setParameter("Year", year)
                .setParameter("idFutsal", id)
                .getResultList();
        return result;
    }
    

    
    
    public List<Integer>  getChartLabel(Integer year,Integer id){
        List<Integer> result = em.createQuery("SELECT FUNCTION('MONTH',t.idPemesanan.tglPemesanan) as bulan FROM TbKonfirmasi t WHERE FUNCTION('YEAR',t.idPemesanan.tglPemesanan)= :Year AND t.idPemesanan.status > 1 AND t.idPemesanan.idLapangan.idFutsal.idFutsal = :idFutsal GROUP BY FUNCTION('MONTH',t.idPemesanan.tglPemesanan)")
                .setParameter("Year", year)
                .setParameter("idFutsal", id)
                .getResultList();
        return result;
    }
    
    
    public TbPengelola getPengelolaFutsal(Integer id){
        return (TbPengelola) em.createQuery("SELECT t FROM TbPengelola t WHERE t.idPengelola = :idPengelola")
                .setParameter("idPengelola", id)
                .getSingleResult();
    }
    
    public List<TbKonfirmasi> getPemesananFilterByDate(Date awal,Date akhir,Integer id){
        return em.createQuery("SELECT t FROM TbKonfirmasi t WHERE t.idPemesanan.tglPemesanan BETWEEN :DateAwal AND :DateAkhir AND t.idPemesanan.status > 1 AND t.idPemesanan.idLapangan.idFutsal.idFutsal= :idFutsal")
                .setParameter("DateAwal", awal)
                .setParameter("DateAkhir", akhir)
                .setParameter("idFutsal", id)
                .getResultList();
        
    }
    
    public List<TbKonfirmasi> getListKonfirmasi(Integer id){
        return em.createQuery("SELECT t FROM TbKonfirmasi t WHERE t.idPemesanan.status > 1 AND t.idPemesanan.idLapangan.idFutsal.idFutsal= :idFutsal")
                .setParameter("idFutsal", id)
                .getResultList();
    }
    
}
