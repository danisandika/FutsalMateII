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
@Table(name = "tb_lapangan")
@NamedQueries({
    @NamedQuery(name = "TbLapangan.findAll", query = "SELECT t FROM TbLapangan t"),
    @NamedQuery(name = "TbLapangan.findByIdLapangan", query = "SELECT t FROM TbLapangan t WHERE t.idLapangan = :idLapangan"),
    @NamedQuery(name = "TbLapangan.findByNamaLapangan", query = "SELECT t FROM TbLapangan t WHERE t.namaLapangan = :namaLapangan"),
    @NamedQuery(name = "TbLapangan.findByJenisLapangan", query = "SELECT t FROM TbLapangan t WHERE t.jenisLapangan = :jenisLapangan"),
    @NamedQuery(name = "TbLapangan.findByHarga", query = "SELECT t FROM TbLapangan t WHERE t.harga = :harga")})
public class TbLapangan implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "nama_lapangan")
    private String namaLapangan;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "jenis_lapangan")
    private String jenisLapangan;
    @Basic(optional = false)
    @NotNull
    @Column(name = "harga")
    private int harga;
    @Size(max = 2147483647)
    @Column(name = "gambar")
    private String gambar;
    @Basic(optional = false)
    @NotNull
    @Column(name = "status")
    private int status;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_lapangan")
    private Integer idLapangan;
    @JoinColumn(name = "id_futsal", referencedColumnName = "id_futsal")
    @ManyToOne(optional = false)
    private TbFutsal idFutsal;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idLapangan")
    private Collection<TbPemesanan> tbPemesananCollection;

    public TbLapangan() {
    }

    public TbLapangan(Integer idLapangan) {
        this.idLapangan = idLapangan;
    }

    public TbLapangan(Integer idLapangan, String namaLapangan, String jenisLapangan, int harga) {
        this.idLapangan = idLapangan;
        this.namaLapangan = namaLapangan;
        this.jenisLapangan = jenisLapangan;
        this.harga = harga;
    }

    public Integer getIdLapangan() {
        return idLapangan;
    }

    public void setIdLapangan(Integer idLapangan) {
        this.idLapangan = idLapangan;
    }

    public String getNamaLapangan() {
        return namaLapangan;
    }

    public void setNamaLapangan(String namaLapangan) {
        this.namaLapangan = namaLapangan;
    }

    public String getJenisLapangan() {
        return jenisLapangan;
    }

    public void setJenisLapangan(String jenisLapangan) {
        this.jenisLapangan = jenisLapangan;
    }


    public TbFutsal getIdFutsal() {
        return idFutsal;
    }

    public void setIdFutsal(TbFutsal idFutsal) {
        this.idFutsal = idFutsal;
    }

    public Collection<TbPemesanan> getTbPemesananCollection() {
        return tbPemesananCollection;
    }

    public void setTbPemesananCollection(Collection<TbPemesanan> tbPemesananCollection) {
        this.tbPemesananCollection = tbPemesananCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idLapangan != null ? idLapangan.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TbLapangan)) {
            return false;
        }
        TbLapangan other = (TbLapangan) object;
        if ((this.idLapangan == null && other.idLapangan != null) || (this.idLapangan != null && !this.idLapangan.equals(other.idLapangan))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.TbLapangan[ idLapangan=" + idLapangan + " ]";
    }

   


    

    public int getHarga() {
        return harga;
    }

    public void setHarga(int harga) {
        this.harga = harga;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    
}
