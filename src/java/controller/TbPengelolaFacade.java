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
import model.TbPengelola;

/**
 *
 * @author Danis
 */
@Stateless
public class TbPengelolaFacade extends AbstractFacade<TbPengelola> {

    @PersistenceContext(unitName = "FutsalMateIIPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TbPengelolaFacade() {
        super(TbPengelola.class);
    }
    
    public void ubahStatus(TbPengelola t, int stat) {
        em.createQuery("UPDATE TbPengelola t SET t.status = :stat WHERE t.idPengelola = :id")
                .setParameter("stat", stat)
                .setParameter("id", t.getIdPengelola())
                .executeUpdate();
    }    
    
    public boolean getAutentikasi(String Email, String Password) {
        try {
            em.createQuery("SELECT p FROM TbPengelola p WHERE p.email = :Email and p.password= :Password ")
                    .setParameter("Email", Email)
                    .setParameter("Password", Password)
                    .getSingleResult();
        } catch (Exception e) {
            return false;
        }
        return em != null;
    }

    public List<TbPengelola> getData(String Email) {
        return em.createQuery("SELECT p FROM TbPengelola p WHERE p.email= :Email")
                .setParameter("Email", Email)
                .getResultList();
    }
    
    public TbPengelola getDataLogin(String Email){
        return em.createQuery("SELECT p FROM TbPengelola p WHERE p.email= :Email", TbPengelola.class)
                .setParameter("Email", Email)
                .getSingleResult();
    }
    
    
    
    public boolean getEmailNotExist(String email){
        try {
            em.createQuery("SELECT p FROM TbPengelola p WHERE p.email = :Email ")
                    .setParameter("Email", email)
                    .getSingleResult();
        } catch (Exception e) {
            return false;
        }
        return em != null;
    }
    
    
    public TbPengelola getDataPengelola(Integer id){
        return em.createNamedQuery("TbPengelola.findByIdPengelola", TbPengelola.class)
                .setParameter("idPengelola", id)
                .getSingleResult();
    }
    
    
    public String getCountLapangan(Integer id){
        
       String res = em.createQuery("SELECT COUNT(t.idLapangan) as Lapangan FROM TbLapangan t WHERE t.idFutsal.idFutsal = :idFutsal")
                .setParameter("idFutsal", id)
                .getSingleResult().toString();
       return  res;
    }
    
    public String getCountPemesananLapangan(Integer id){
        
       String res = em.createQuery("SELECT COUNT(t.idPemesanan) as pemesanan FROM TbPemesanan t WHERE t.idLapangan.idFutsal.idFutsal= :idFutsal")
                .setParameter("idFutsal", id)
                .getSingleResult().toString();
       return  res;
    }
    
    public String getSUMBayarLapangan(Integer id){
        
       String res = em.createQuery("SELECT SUM(k.jumlahBayar) FROM TbKonfirmasi k WHERE k.idPemesanan.idLapangan.idFutsal.idFutsal= :idFutsal AND k.idPemesanan.status > 1")
                .setParameter("idFutsal", id)
                .getSingleResult().toString();
       return  res;
    }
    
    
    public boolean getBooleanchart(Integer id) {
        try {
            em.createQuery("SELECT t FROM TbPemesanan t WHERE t.idLapangan.idFutsal.idFutsal= :idFutsal")
                    .setParameter("idFutsal", id)
                    .getResultList().get(0);
        } catch (Exception e) {
            return false;
        }
        return em != null;
    }
    
    public void ubahPasswordPengelola(String email,String password) {
        em.createQuery("UPDATE TbPengelola t SET t.password = :password WHERE t.email= :email")
                .setParameter("password", password)
                .setParameter("email", email)
                .executeUpdate();
    }
    
    
    public List<TbPengelola> listNonAktifPengelola(Date current){
        return em.createQuery("SELECT t FROM TbPengelola t WHERE t.tglBerakhir < :tglAkhir AND t.status = 1")
                .setParameter("tglAkhir", current)
                .getResultList();
    }
    
    
    public void ubahStatusSewaBerakhir(Integer id) {
        em.createQuery("UPDATE TbSewalapangan t SET t.statusBayar = 3 WHERE t.idFutsal.idFutsal = :idFutsal")
                .setParameter("idFutsal", id)
                .executeUpdate();
    }
}
