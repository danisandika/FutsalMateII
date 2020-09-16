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
import model.TbRating;

/**
 *
 * @author Danis
 */
@Stateless
public class TbRatingFacade extends AbstractFacade<TbRating> {

    @PersistenceContext(unitName = "FutsalMateIIPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TbRatingFacade() {
        super(TbRating.class);
    }
    
    public List<TbRating> getRatingBy(Integer id){
        return em.createQuery("SELECT t FROM TbRating t WHERE t.idFutsal.idFutsal = :id")
                .setParameter("id", id)
                .getResultList();
    }
    
}
