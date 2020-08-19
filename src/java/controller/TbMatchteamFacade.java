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
import model.TbMatchteam;

/**
 *
 * @author Danis
 */
@Stateless
public class TbMatchteamFacade extends AbstractFacade<TbMatchteam> {

    @PersistenceContext(unitName = "FutsalMateIIPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TbMatchteamFacade() {
        super(TbMatchteam.class);
    }
    
    public List<TbMatchteam> getMatchByTeam(Integer id){
        return em.createQuery("SELECT t FROM TbMatchteam t WHERE t.idHomeTeam.idTeam = :idTeam OR t.idAwayTeam.idTeam = :idTeam")
                .setParameter("idTeam", id)
                .getResultList();
    }
    
    public List<TbMatchteam> getMatchByHomeTeam(Integer id){
        return em.createQuery("SELECT t FROM TbMatchteam t WHERE t.idHomeTeam.idTeam = :idTeam")
                .setParameter("idTeam", id)
                .getResultList();
    }
    
    public void joinMatchTeam(Integer idTeam, String idMatch) {
        em.createQuery("UPDATE TbMatchteam t SET t.idAwayTeam.idTeam = :idTeam WHERE t.idMatchteam = :idMatch")
                .setParameter("idTeam", idTeam)
                .setParameter("idMatch", idMatch)
                .executeUpdate();
    }
    
}
