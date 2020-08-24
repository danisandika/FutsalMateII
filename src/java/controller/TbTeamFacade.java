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
import model.TbTeam;

/**
 *
 * @author Danis
 */
@Stateless
public class TbTeamFacade extends AbstractFacade<TbTeam> {

    @PersistenceContext(unitName = "FutsalMateIIPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TbTeamFacade() {
        super(TbTeam.class);
    }
    
    public TbTeam getByID(TbTeam id) {
        return em.createNamedQuery("TbTeam.findByIdTeam", TbTeam.class)
                .setParameter("idTeam", id.getIdTeam())
                .getSingleResult();
    }
    
    public TbTeam getByID2(Integer id) {
        return em.createNamedQuery("TbTeam.findByIdTeam", TbTeam.class)
                .setParameter("idTeam", id)
                .getSingleResult();
    }
    
    public List<TbTeam> getTop4Team(){
        return em.createQuery("SELECT t FROM TbTeam t ORDER BY t.rate DESC")
                .setMaxResults(4)
                .getResultList();
    }
    
}
