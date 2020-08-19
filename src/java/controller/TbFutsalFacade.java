/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import model.TbFutsal;

/**
 *
 * @author Danis
 */
@Stateless
public class TbFutsalFacade extends AbstractFacade<TbFutsal> {

    @PersistenceContext(unitName = "FutsalMateIIPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TbFutsalFacade() {
        super(TbFutsal.class);
    }
    
    
   public TbFutsal getFutsal(Integer id){
        return em.createNamedQuery("TbFutsal.findByIdFutsal",TbFutsal.class)
                .setParameter("idFutsal", id)
                .getSingleResult();
    }
   
   
   public TbFutsal getFutsalByID(Integer id){
       return (TbFutsal) em.createQuery("SELECT t FROM TbFutsal t WHERE t.idFutsal = :idFutsal")
               .setParameter("idFutsal", id)
               .getSingleResult();
   }
    
    public void ubahStatus(TbFutsal t, int stat) {
        em.createQuery("UPDATE TbFutsal t SET t.status = :stat WHERE t.idFutsal = :id")
                .setParameter("stat", stat)
                .setParameter("id", t.getIdFutsal())
                .executeUpdate();
    }
 
    
}
