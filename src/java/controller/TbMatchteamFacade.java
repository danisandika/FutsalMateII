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
import model.TbPemain;
import model.TbIndividuMatch;
import model.TbPemesanan;

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
    
    public void joinMatchIndividu(TbMatchteam match, TbPemain pemain) {
        em.createNativeQuery("INSERT INTO tb_individumatch (id_matchteam, id_pemain) VALUES (?1, ?2)")
                .setParameter("1", match.getIdMatchteam())
                .setParameter("2", pemain.getIdPemain())
                .executeUpdate();
    }
    
    public List<TbIndividuMatch> getMatchIndividu(TbMatchteam match) {
        return em.createNativeQuery(""
                + "SELECT tb_individumatch.id_matchteam, tb_pemain.nama, tb_pemain.notelp, tb_pemain.alamat "
                + "FROM tb_individumatch "
                + "INNER JOIN tb_pemain ON tb_pemain.id_pemain = tb_individumatch.id_pemain "
                + "WHERE id_matchteam = ?1")
                .setParameter("1", match.getIdMatchteam())
                .getResultList();
    }
    
    public boolean getAutentikasiIndividuMatch(String idMatch, Integer idPemain) {
        try {
            em.createNativeQuery("SELECT * FROM tb_individumatch WHERE id_matchteam = ?1 AND id_pemain = ?2")
                    .setParameter("1", idMatch)
                    .setParameter("2", idPemain)
                    .getSingleResult();
        } catch (Exception e) {
            return false;
        }
        return em != null;
    }
    
    public boolean getAutentikasiPemesanan(String idPemesanan) {                // cek udh ada di tb_matchteam apa ga
        try {
            em.createQuery("SELECT t FROM TbMatchteam t WHERE t.idPemesanan.idPemesanan = :idPesan")
                    .setParameter("idPesan", idPemesanan)
                    .getSingleResult();         // true
        } catch (Exception e) {
            return false;
        }
        return em != null;
    }
    
    public boolean getAutentikasiPemesananConfrim(String idPemesanan) {         // cek idnya ada di tb_pemesanan ga
        try {                                                                   // sama cek statusnya udh confirm blm *2
            em.createQuery("SELECT t FROM TbPemesanan t WHERE t.idPemesanan = :idPesan AND t.status = :stat")
                    .setParameter("idPesan", idPemesanan)
                    .setParameter("stat", 2)
                    .getSingleResult();         // true
        } catch (Exception e) {
            return false;
        }
        return em != null;
    }

    public TbPemesanan getDataPemesanan(String idPesan) {
        return (TbPemesanan) em.createQuery("SELECT t FROM TbPemesanan t WHERE t.idPemesanan = :idPesan")
                .setParameter("idPesan", idPesan)
                .getSingleResult();
    }
    
}
