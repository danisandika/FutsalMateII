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
import model.TbFutsal;
import model.TbLapangan;

/**
 *
 * @author Danis
 */
@Stateless
public class TbLapanganFacade extends AbstractFacade<TbLapangan> {

    @PersistenceContext(unitName = "FutsalMateIIPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TbLapanganFacade() {
        super(TbLapangan.class);
    }
    
    //METHOD SENDIRI UNTUK AMBIL DATA LAPANGAN
    public List<TbLapangan>  getLapanganByIDFutsal(Integer idFutsal){
            return em.createQuery("SELECT p FROM TbLapangan p WHERE p.idFutsal.idFutsal= :idFutsal")
                .setParameter("idFutsal", idFutsal)
                .getResultList();
        
    }
    
    //METHOD SENDIRI UNTUK AMBIL DATA LAPANGAN
    public TbLapangan getTbLapanganByIDLapangan(Integer idLapangan){
        return em.createNamedQuery("TbLapangan.findByIdLapangan",TbLapangan.class)
                .setParameter("idLapangan", idLapangan)
                .getSingleResult();
    }
}
