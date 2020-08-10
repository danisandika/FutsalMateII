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
    
}
