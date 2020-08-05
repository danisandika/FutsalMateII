/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Danis
 */
@Entity
@Table(name = "tb_pemesanan")
@NamedQueries({
    @NamedQuery(name = "TbPemesanan.findAll", query = "SELECT t FROM TbPemesanan t"),
    @NamedQuery(name = "TbPemesanan.findByIdPemesanan", query = "SELECT t FROM TbPemesanan t WHERE t.idPemesanan = :idPemesanan"),
    @NamedQuery(name = "TbPemesanan.findByTglPemesanan", query = "SELECT t FROM TbPemesanan t WHERE t.tglPemesanan = :tglPemesanan"),
    @NamedQuery(name = "TbPemesanan.findByTglMain", query = "SELECT t FROM TbPemesanan t WHERE t.tglMain = :tglMain"),
    @NamedQuery(name = "TbPemesanan.findByJamMainMulai", query = "SELECT t FROM TbPemesanan t WHERE t.jamMainMulai = :jamMainMulai"),
    @NamedQuery(name = "TbPemesanan.findByJamMainSelesai", query = "SELECT t FROM TbPemesanan t WHERE t.jamMainSelesai = :jamMainSelesai"),
    @NamedQuery(name = "TbPemesanan.findByDurasi", query = "SELECT t FROM TbPemesanan t WHERE t.durasi = :durasi"),
    @NamedQuery(name = "TbPemesanan.findByStatus", query = "SELECT t FROM TbPemesanan t WHERE t.status = :status")})
public class TbPemesanan implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "tgl_pemesanan")
    @Temporal(TemporalType.DATE)
    private Date tglPemesanan;
    @Basic(optional = false)
    @NotNull
    @Column(name = "tgl_main")
    @Temporal(TemporalType.DATE)
    private Date tglMain;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "id_pemesanan")
    private String idPemesanan;
    @Column(name = "jam_main_mulai")
    @Temporal(TemporalType.TIME)
    private Date jamMainMulai;
    @Column(name = "jam_main_selesai")
    @Temporal(TemporalType.TIME)
    private Date jamMainSelesai;
    @Column(name = "durasi")
    private Integer durasi;
    @Column(name = "status")
    private Integer status;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idPemesanan")
    private Collection<TbKonfirmasi> tbKonfirmasiCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idPemesanan")
    private Collection<TbMatchteam> tbMatchteamCollection;
    @JoinColumn(name = "id_lapangan", referencedColumnName = "id_lapangan")
    @ManyToOne(optional = false)
    private TbLapangan idLapangan;
    @JoinColumn(name = "id_pemain", referencedColumnName = "id_pemain")
    @ManyToOne(optional = false)
    private TbPemain idPemain;

    public TbPemesanan() {
    }

    public TbPemesanan(String idPemesanan) {
        this.idPemesanan = idPemesanan;
    }

    public TbPemesanan(String idPemesanan, Date tglPemesanan, Date tglMain) {
        this.idPemesanan = idPemesanan;
        this.tglPemesanan = tglPemesanan;
        this.tglMain = tglMain;
    }

    public String getIdPemesanan() {
        return idPemesanan;
    }

    public void setIdPemesanan(String idPemesanan) {
        this.idPemesanan = idPemesanan;
    }

    public Date getTglPemesanan() {
        return tglPemesanan;
    }

    public void setTglPemesanan(Date tglPemesanan) {
        this.tglPemesanan = tglPemesanan;
    }

    public Date getTglMain() {
        return tglMain;
    }

    public void setTglMain(Date tglMain) {
        this.tglMain = tglMain;
    }

    public Date getJamMainMulai() {
        return jamMainMulai;
    }

    public void setJamMainMulai(Date jamMainMulai) {
        this.jamMainMulai = jamMainMulai;
    }

    public Date getJamMainSelesai() {
        return jamMainSelesai;
    }

    public void setJamMainSelesai(Date jamMainSelesai) {
        this.jamMainSelesai = jamMainSelesai;
    }

    public Integer getDurasi() {
        return durasi;
    }

    public void setDurasi(Integer durasi) {
        this.durasi = durasi;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Collection<TbKonfirmasi> getTbKonfirmasiCollection() {
        return tbKonfirmasiCollection;
    }

    public void setTbKonfirmasiCollection(Collection<TbKonfirmasi> tbKonfirmasiCollection) {
        this.tbKonfirmasiCollection = tbKonfirmasiCollection;
    }

    public Collection<TbMatchteam> getTbMatchteamCollection() {
        return tbMatchteamCollection;
    }

    public void setTbMatchteamCollection(Collection<TbMatchteam> tbMatchteamCollection) {
        this.tbMatchteamCollection = tbMatchteamCollection;
    }

    public TbLapangan getIdLapangan() {
        return idLapangan;
    }

    public void setIdLapangan(TbLapangan idLapangan) {
        this.idLapangan = idLapangan;
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
        hash += (idPemesanan != null ? idPemesanan.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TbPemesanan)) {
            return false;
        }
        TbPemesanan other = (TbPemesanan) object;
        if ((this.idPemesanan == null && other.idPemesanan != null) || (this.idPemesanan != null && !this.idPemesanan.equals(other.idPemesanan))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.TbPemesanan[ idPemesanan=" + idPemesanan + " ]";
    }


   
    
}
