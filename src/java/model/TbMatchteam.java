/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Danis
 */
@Entity
@Table(name = "tb_matchteam")
@NamedQueries({
    @NamedQuery(name = "TbMatchteam.findAll", query = "SELECT t FROM TbMatchteam t"),
    @NamedQuery(name = "TbMatchteam.findByIdMatchteam", query = "SELECT t FROM TbMatchteam t WHERE t.idMatchteam = :idMatchteam"),
    @NamedQuery(name = "TbMatchteam.findByHomeScore", query = "SELECT t FROM TbMatchteam t WHERE t.homeScore = :homeScore"),
    @NamedQuery(name = "TbMatchteam.findByAwayScore", query = "SELECT t FROM TbMatchteam t WHERE t.awayScore = :awayScore"),
    @NamedQuery(name = "TbMatchteam.findByDeskripsi", query = "SELECT t FROM TbMatchteam t WHERE t.deskripsi = :deskripsi")})
public class TbMatchteam implements Serializable {

    @Size(max = 2147483647)
    @Column(name = "deskripsi")
    private String deskripsi;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "id_matchteam")
    private String idMatchteam;
    @Column(name = "home_score")
    private Integer homeScore;
    @Column(name = "away_score")
    private Integer awayScore;
    @JoinTable(name = "tb_individumatch", joinColumns = {
        @JoinColumn(name = "id_matchteam", referencedColumnName = "id_matchteam")}, inverseJoinColumns = {
        @JoinColumn(name = "id_pemain", referencedColumnName = "id_pemain")})
    @ManyToMany
    private Collection<TbPemain> tbPemainCollection;
    @JoinColumn(name = "id_pemesanan", referencedColumnName = "id_pemesanan")
    @ManyToOne(optional = false)
    private TbPemesanan idPemesanan;
    @JoinColumn(name = "id_home_team", referencedColumnName = "id_team")
    @ManyToOne(optional = false)
    private TbTeam idHomeTeam;
    @JoinColumn(name = "id_away_team", referencedColumnName = "id_team")
    @ManyToOne
    private TbTeam idAwayTeam;

    public TbMatchteam() {
    }

    public TbMatchteam(String idMatchteam) {
        this.idMatchteam = idMatchteam;
    }

    public String getIdMatchteam() {
        return idMatchteam;
    }

    public void setIdMatchteam(String idMatchteam) {
        this.idMatchteam = idMatchteam;
    }

    public Integer getHomeScore() {
        return homeScore;
    }

    public void setHomeScore(Integer homeScore) {
        this.homeScore = homeScore;
    }

    public Integer getAwayScore() {
        return awayScore;
    }

    public void setAwayScore(Integer awayScore) {
        this.awayScore = awayScore;
    }


    public Collection<TbPemain> getTbPemainCollection() {
        return tbPemainCollection;
    }

    public void setTbPemainCollection(Collection<TbPemain> tbPemainCollection) {
        this.tbPemainCollection = tbPemainCollection;
    }

    public TbPemesanan getIdPemesanan() {
        return idPemesanan;
    }

    public void setIdPemesanan(TbPemesanan idPemesanan) {
        this.idPemesanan = idPemesanan;
    }

    public TbTeam getIdHomeTeam() {
        return idHomeTeam;
    }

    public void setIdHomeTeam(TbTeam idHomeTeam) {
        this.idHomeTeam = idHomeTeam;
    }

    public TbTeam getIdAwayTeam() {
        return idAwayTeam;
    }

    public void setIdAwayTeam(TbTeam idAwayTeam) {
        this.idAwayTeam = idAwayTeam;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idMatchteam != null ? idMatchteam.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TbMatchteam)) {
            return false;
        }
        TbMatchteam other = (TbMatchteam) object;
        if ((this.idMatchteam == null && other.idMatchteam != null) || (this.idMatchteam != null && !this.idMatchteam.equals(other.idMatchteam))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.TbMatchteam[ idMatchteam=" + idMatchteam + " ]";
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }
    
}
