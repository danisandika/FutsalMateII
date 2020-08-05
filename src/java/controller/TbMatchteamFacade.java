/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

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
    
}
