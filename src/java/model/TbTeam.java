/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Danis
 */
@Entity
@Table(name = "tb_team")
@NamedQueries({
    @NamedQuery(name = "TbTeam.findAll", query = "SELECT t FROM TbTeam t"),
    @NamedQuery(name = "TbTeam.findByIdTeam", query = "SELECT t FROM TbTeam t WHERE t.idTeam = :idTeam"),
    @NamedQuery(name = "TbTeam.findByNamaTeam", query = "SELECT t FROM TbTeam t WHERE t.namaTeam = :namaTeam"),
    @NamedQuery(name = "TbTeam.findByDeskripsi", query = "SELECT t FROM TbTeam t WHERE t.deskripsi = :deskripsi"),
    @NamedQuery(name = "TbTeam.findByLogo", query = "SELECT t FROM TbTeam t WHERE t.logo = :logo"),
    @NamedQuery(name = "TbTeam.findByWin", query = "SELECT t FROM TbTeam t WHERE t.win = :win"),
    @NamedQuery(name = "TbTeam.findByLose", query = "SELECT t FROM TbTeam t WHERE t.lose = :lose"),
    @NamedQuery(name = "TbTeam.findByRate", query = "SELECT t FROM TbTeam t WHERE t.rate = :rate")})
public class TbTeam implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "nama_team")
    private String namaTeam;
    @Size(max = 2147483647)
    @Column(name = "deskripsi")
    private String deskripsi;
    @Size(max = 2147483647)
    @Column(name = "logo")
    private String logo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "win")
    private int win;
    @Basic(optional = false)
    @NotNull
    @Column(name = "lose")
    private int lose;
    @Basic(optional = false)
    @NotNull
    @Column(name = "rate")
    private double rate;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_team")
    private Integer idTeam;
    @JoinColumn(name = "captain", referencedColumnName = "id_pemain")
    @ManyToOne(optional = false)
    private TbPemain captain;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idHomeTeam")
    private Collection<TbMatchteam> tbMatchteamCollection;
    @OneToMany(mappedBy = "idAwayTeam")
    private Collection<TbMatchteam> tbMatchteamCollection1;
    @OneToMany(mappedBy = "idTeam")
    private Collection<TbPemain> tbPemainCollection;

    public TbTeam() {
    }

    public TbTeam(Integer idTeam) {
        this.idTeam = idTeam;
    }

    public TbTeam(Integer idTeam, String namaTeam, int win, int lose, double rate) {
        this.idTeam = idTeam;
        this.namaTeam = namaTeam;
        this.win = win;
        this.lose = lose;
        this.rate = rate;
    }

    public Integer getIdTeam() {
        return idTeam;
    }

    public void setIdTeam(Integer idTeam) {
        this.idTeam = idTeam;
    }

    public String getNamaTeam() {
        return namaTeam;
    }

    public void setNamaTeam(String namaTeam) {
        this.namaTeam = namaTeam;
    }


    public TbPemain getCaptain() {
        return captain;
    }

    public void setCaptain(TbPemain captain) {
        this.captain = captain;
    }

    public Collection<TbMatchteam> getTbMatchteamCollection() {
        return tbMatchteamCollection;
    }

    public void setTbMatchteamCollection(Collection<TbMatchteam> tbMatchteamCollection) {
        this.tbMatchteamCollection = tbMatchteamCollection;
    }

    public Collection<TbMatchteam> getTbMatchteamCollection1() {
        return tbMatchteamCollection1;
    }

    public void setTbMatchteamCollection1(Collection<TbMatchteam> tbMatchteamCollection1) {
        this.tbMatchteamCollection1 = tbMatchteamCollection1;
    }

    public Collection<TbPemain> getTbPemainCollection() {
        return tbPemainCollection;
    }

    public void setTbPemainCollection(Collection<TbPemain> tbPemainCollection) {
        this.tbPemainCollection = tbPemainCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTeam != null ? idTeam.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TbTeam)) {
            return false;
        }
        TbTeam other = (TbTeam) object;
        if ((this.idTeam == null && other.idTeam != null) || (this.idTeam != null && !this.idTeam.equals(other.idTeam))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.TbTeam[ idTeam=" + idTeam + " ]";
    }

   

   

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public int getWin() {
        return win;
    }

    public void setWin(int win) {
        this.win = win;
    }

    public int getLose() {
        return lose;
    }

    public void setLose(int lose) {
        this.lose = lose;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }
    
}
