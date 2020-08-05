/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
@Table(name = "tb_rating")
@NamedQueries({
    @NamedQuery(name = "TbRating.findAll", query = "SELECT t FROM TbRating t"),
    @NamedQuery(name = "TbRating.findByIdRating", query = "SELECT t FROM TbRating t WHERE t.idRating = :idRating"),
    @NamedQuery(name = "TbRating.findByRating", query = "SELECT t FROM TbRating t WHERE t.rating = :rating"),
    @NamedQuery(name = "TbRating.findByKomentar", query = "SELECT t FROM TbRating t WHERE t.komentar = :komentar")})
public class TbRating implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "rating")
    private double rating;
    @Size(max = 2147483647)
    @Column(name = "komentar")
    private String komentar;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_rating")
    private Integer idRating;
    @JoinColumn(name = "id_futsal", referencedColumnName = "id_futsal")
    @ManyToOne(optional = false)
    private TbFutsal idFutsal;
    @JoinColumn(name = "id_pemain", referencedColumnName = "id_pemain")
    @ManyToOne(optional = false)
    private TbPemain idPemain;

    public TbRating() {
    }

    public TbRating(Integer idRating) {
        this.idRating = idRating;
    }

    public TbRating(Integer idRating, double rating) {
        this.idRating = idRating;
        this.rating = rating;
    }

    public Integer getIdRating() {
        return idRating;
    }

    public void setIdRating(Integer idRating) {
        this.idRating = idRating;
    }


    public TbFutsal getIdFutsal() {
        return idFutsal;
    }

    public void setIdFutsal(TbFutsal idFutsal) {
        this.idFutsal = idFutsal;
    }

    public TbPemain getIdPemain() {
        return idPemain;
    }

    public void setIdPemain(TbPemain idPemain) {
        this.idPemain = idPemain;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idRating != null ? idRating.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TbRating)) {
            return false;
        }
        TbRating other = (TbRating) object;
        if ((this.idRating == null && other.idRating != null) || (this.idRating != null && !this.idRating.equals(other.idRating))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.TbRating[ idRating=" + idRating + " ]";
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getKomentar() {
        return komentar;
    }

    public void setKomentar(String komentar) {
        this.komentar = komentar;
    }
    
}
