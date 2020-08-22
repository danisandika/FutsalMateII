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
import model.TbAdmin;

/**
 *
 * @author Danis
 */
@Stateless
public class TbAdminFacade extends AbstractFacade<TbAdmin> {

    @PersistenceContext(unitName = "FutsalMateIIPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TbAdminFacade() {
        super(TbAdmin.class);
    }
    
    
    public boolean getAutentikasi(String Email, String Password) {
        try {
            em.createQuery("SELECT p FROM TbAdmin p WHERE p.email = :Email and p.password= :Password ")
                    .setParameter("Email", Email)
                    .setParameter("Password", Password)
                    .getSingleResult();
        } catch (Exception e) {
            return false;
        }
        return em != null;
    }

    public List<TbAdmin> getData(String Email) {
        return em.createQuery("SELECT p FROM TbAdmin p WHERE p.email = :Email")
                .setParameter("Email", Email)
                .getResultList();
    }
    
    public boolean getEmailAdminNotExist(String email){
        try {
            em.createQuery("SELECT p FROM TbAdmin p WHERE p.email = :Email ")
                    .setParameter("Email", email)
                    .getSingleResult();
        } catch (Exception e) {
            return false;
        }
        return em != null;
    }
    
    public TbAdmin getDataLogin(String Email){
        return em.createQuery("SELECT p FROM TbAdmin p WHERE p.email= :Email", TbAdmin.class)
                .setParameter("Email", Email)
                .getSingleResult();
    }
    
    
     public TbAdmin getDataAdmin(Integer id){
        return em.createNamedQuery("TbAdmin.findByIdAdmin", TbAdmin.class)
                .setParameter("idAdmin", id)
                .getSingleResult();
    }
     
     
    public String getCountMember(){
        
       String res = em.createQuery("SELECT COUNT(t.idPengelola) as pengelola FROM TbPengelola t ")
                .getSingleResult().toString();
       return  res;
    }
    
    public String getSumSewaLap(){
        
       String res = em.createQuery("SELECT SUM(t.jumlahUang) as sewa FROM TbSewalapangan t WHERE t.statusBayar > 1")
                .getSingleResult().toString();
       return  res;
    }
    
    public String getCountSewaLap(){
        
       String res = em.createQuery("SELECT COUNT(t.idSewalapangan) as sewa FROM TbSewalapangan t WHERE t.statusBayar > 1")
                .getSingleResult().toString();
       return  res;
    }
    
    public boolean getBooleanchart() {
        try {
            em.createQuery("SELECT t FROM TbSewalapangan t")
                    .getResultList().get(0);
        } catch (Exception e) {
            return false;
        }
        return em != null;
    }
    
    public void ubahPasswordAdmin(String email,String password) {
        em.createQuery("UPDATE TbAdmin t SET t.password = :password WHERE t.email= :email")
                .setParameter("password", password)
                .setParameter("email", email)
                .executeUpdate();
    }
}
