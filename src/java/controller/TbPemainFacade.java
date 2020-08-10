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
import model.TbPemain;

/**
 *
 * @author Danis
 */
@Stateless
public class TbPemainFacade extends AbstractFacade<TbPemain> {

    @PersistenceContext(unitName = "FutsalMateIIPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TbPemainFacade() {
        super(TbPemain.class);
    }
    
    public List<TbPemain> getByTeam(Integer id) {
        return em.createQuery("SELECT t FROM TbPemain t WHERE t.idTeam = :id")
                .setParameter("id", id)
                .getResultList();
    }
    
    public boolean getAutentikasiCaptain(Integer idCaptain) {
        try {
            em.createQuery("SELECT t FROM TbTeam t WHERE t.captain = :captain")
                    .setParameter("captain", idCaptain)
                    .getSingleResult();
        } catch (Exception e) {
            return false;
        }
        return em != null;
    }
    
    public boolean getAutentikasi(String Email, String Password) {
        try {
            em.createQuery("SELECT t FROM TbPemain t WHERE t.email = :email and t.password = :password ")
                    .setParameter("email", Email)
                    .setParameter("password", Password)
                    .getSingleResult();
        } catch (Exception e) {
            return false;
        }
        return em != null;
    }

    public List<TbPemain> getData(String Email) {
        return em.createQuery("SELECT t FROM TbPemain t WHERE t.email = :email")
                .setParameter("email", Email)
                .getResultList();
    }
    
    public TbPemain getDataLogin(String Email){
        return em.createQuery("SELECT t FROM TbPemain t WHERE t.email = :email", TbPemain.class)
                .setParameter("email", Email)
                .getSingleResult();
    }
}
