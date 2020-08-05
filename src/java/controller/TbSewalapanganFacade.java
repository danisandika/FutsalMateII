/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import model.TbSewalapangan;

/**
 *
 * @author Danis
 */
@Stateless
public class TbSewalapanganFacade extends AbstractFacade<TbSewalapangan> {

    @PersistenceContext(unitName = "FutsalMateIIPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TbSewalapanganFacade() {
        super(TbSewalapangan.class);
    }
    
}
