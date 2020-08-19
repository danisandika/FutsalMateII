/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import model.TbKonfirmasi;
import model.TbPemesanan;

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
}
