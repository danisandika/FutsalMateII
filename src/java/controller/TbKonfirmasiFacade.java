/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import model.TbKonfirmasi;
import model.TbPengelola;
import model.TbPemesanan;

/**
 *
 * @author Danis
 */
@Stateless
public class TbKonfirmasiFacade extends AbstractFacade<TbKonfirmasi> {

    @PersistenceContext(unitName = "FutsalMateIIPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TbKonfirmasiFacade() {
        super(TbKonfirmasi.class);
    }

    public TbPengelola getDataPengelola(Integer idFutsal) {
        return (TbPengelola) em.createQuery("SELECT t FROM TbPengelola t WHERE t.idFutsal.idFutsal = :idFutsal")
                .setParameter("idFutsal", idFutsal)
                .getSingleResult();
    }
    
    public void setStatusPesanan(String idPesan, Integer stat) {
        em.createQuery("UPDATE TbPemesanan t SET t.status = :stat WHERE t.idPemesanan = :idPesan")
                .setParameter("stat", stat)
                .setParameter("idPesan", idPesan)
                .executeUpdate();
    }

    
}
