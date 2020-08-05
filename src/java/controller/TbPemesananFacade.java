/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import model.TbPemesanan;

/**
 *
 * @author Danis
 */
@Stateless
public class TbPemesananFacade extends AbstractFacade<TbPemesanan> {

    @PersistenceContext(unitName = "FutsalMateIIPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TbPemesananFacade() {
        super(TbPemesanan.class);
    }
    
}
