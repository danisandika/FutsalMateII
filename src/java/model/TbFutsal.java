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
@Table(name = "tb_futsal")
@NamedQueries({
    @NamedQuery(name = "TbFutsal.findAll", query = "SELECT t FROM TbFutsal t"),
    @NamedQuery(name = "TbFutsal.findByIdFutsal", query = "SELECT t FROM TbFutsal t WHERE t.idFutsal = :idFutsal"),
    @NamedQuery(name = "TbFutsal.findByNamaFutsal", query = "SELECT t FROM TbFutsal t WHERE t.namaFutsal = :namaFutsal"),
    @NamedQuery(name = "TbFutsal.findByAlamatFutsal", query = "SELECT t FROM TbFutsal t WHERE t.alamatFutsal = :alamatFutsal"),
    @NamedQuery(name = "TbFutsal.findByRating", query = "SELECT t FROM TbFutsal t WHERE t.rating = :rating"),
    @NamedQuery(name = "TbFutsal.findByDeskripsiFutsal", query = "SELECT t FROM TbFutsal t WHERE t.deskripsiFutsal = :deskripsiFutsal"),
    @NamedQuery(name = "TbFutsal.findByNotelpFutsal", query = "SELECT t FROM TbFutsal t WHERE t.notelpFutsal = :notelpFutsal"),
    @NamedQuery(name = "TbFutsal.findByFasilitas", query = "SELECT t FROM TbFutsal t WHERE t.fasilitas = :fasilitas"),
    @NamedQuery(name = "TbFutsal.findByGambar", query = "SELECT t FROM TbFutsal t WHERE t.gambar = :gambar"),
    @NamedQuery(name = "TbFutsal.findByLatitude", query = "SELECT t FROM TbFutsal t WHERE t.latitude = :latitude"),
    @NamedQuery(name = "TbFutsal.findByLongitude", query = "SELECT t FROM TbFutsal t WHERE t.longitude = :longitude"),
    @NamedQuery(name = "TbFutsal.findByNamaBank", query = "SELECT t FROM TbFutsal t WHERE t.namaBank = :namaBank"),
    @NamedQuery(name = "TbFutsal.findByNoRekening", query = "SELECT t FROM TbFutsal t WHERE t.noRekening = :noRekening"),
    @NamedQuery(name = "TbFutsal.findByNamaRekening", query = "SELECT t FROM TbFutsal t WHERE t.namaRekening = :namaRekening"),
    @NamedQuery(name = "TbFutsal.findByStatus", query = "SELECT t FROM TbFutsal t WHERE t.status = :status")})
public class TbFutsal implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "nama_futsal")
    private String namaFutsal;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "alamat_futsal")
    private String alamatFutsal;
    @Size(max = 2147483647)
    @Column(name = "deskripsi_futsal")
    private String deskripsiFutsal;
    @Size(max = 15)
    @Column(name = "notelp_futsal")
    private String notelpFutsal;
    @Size(max = 2147483647)
    @Column(name = "fasilitas")
    private String fasilitas;
    @Size(max = 2147483647)
    @Column(name = "gambar")
    private String gambar;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "nama_bank")
    private String namaBank;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "no_rekening")
    private String noRekening;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "nama_rekening")
    private String namaRekening;
    @Basic(optional = false)
    @NotNull
    @Column(name = "status")
    private int status;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_futsal")
    private Integer idFutsal;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "rating")
    private Double rating;
    @Column(name = "latitude")
    private Double latitude;
    @Column(name = "longitude")
    private Double longitude;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idFutsal")
    private Collection<TbLapangan> tbLapanganCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idFutsal")
    private Collection<TbRating> tbRatingCollection;
    @OneToMany(mappedBy = "idFutsal")
    private Collection<TbPengelola> tbPengelolaCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idFutsal")
    private Collection<TbSewalapangan> tbSewalapanganCollection;

    public TbFutsal() {
    }

    public TbFutsal(Integer idFutsal) {
        this.idFutsal = idFutsal;
    }

    public TbFutsal(Integer idFutsal, String namaFutsal, String alamatFutsal, String namaBank, String noRekening, String namaRekening, int status) {
        this.idFutsal = idFutsal;
        this.namaFutsal = namaFutsal;
        this.alamatFutsal = alamatFutsal;
        this.namaBank = namaBank;
        this.noRekening = noRekening;
        this.namaRekening = namaRekening;
        this.status = status;
    }

    public Integer getIdFutsal() {
        return idFutsal;
    }

    public void setIdFutsal(Integer idFutsal) {
        this.idFutsal = idFutsal;
    }

    public String getNamaFutsal() {
        return namaFutsal;
    }

    public void setNamaFutsal(String namaFutsal) {
        this.namaFutsal = namaFutsal;
    }

    public String getAlamatFutsal() {
        return alamatFutsal;
    }

    public void setAlamatFutsal(String alamatFutsal) {
        this.alamatFutsal = alamatFutsal;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getDeskripsiFutsal() {
        return deskripsiFutsal;
    }

    public void setDeskripsiFutsal(String deskripsiFutsal) {
        this.deskripsiFutsal = deskripsiFutsal;
    }

    public String getNotelpFutsal() {
        return notelpFutsal;
    }

    public void setNotelpFutsal(String notelpFutsal) {
        this.notelpFutsal = notelpFutsal;
    }


    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getNamaBank() {
        return namaBank;
    }

    public void setNamaBank(String namaBank) {
        this.namaBank = namaBank;
    }

    public String getNoRekening() {
        return noRekening;
    }

    public void setNoRekening(String noRekening) {
        this.noRekening = noRekening;
    }

    public String getNamaRekening() {
        return namaRekening;
    }

    public void setNamaRekening(String namaRekening) {
        this.namaRekening = namaRekening;
    }


    public Collection<TbLapangan> getTbLapanganCollection() {
        return tbLapanganCollection;
    }

    public void setTbLapanganCollection(Collection<TbLapangan> tbLapanganCollection) {
        this.tbLapanganCollection = tbLapanganCollection;
    }

    public Collection<TbRating> getTbRatingCollection() {
        return tbRatingCollection;
    }

    public void setTbRatingCollection(Collection<TbRating> tbRatingCollection) {
        this.tbRatingCollection = tbRatingCollection;
    }

    public Collection<TbPengelola> getTbPengelolaCollection() {
        return tbPengelolaCollection;
    }

    public void setTbPengelolaCollection(Collection<TbPengelola> tbPengelolaCollection) {
        this.tbPengelolaCollection = tbPengelolaCollection;
    }

    public Collection<TbSewalapangan> getTbSewalapanganCollection() {
        return tbSewalapanganCollection;
    }

    public void setTbSewalapanganCollection(Collection<TbSewalapangan> tbSewalapanganCollection) {
        this.tbSewalapanganCollection = tbSewalapanganCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idFutsal != null ? idFutsal.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TbFutsal)) {
            return false;
        }
        TbFutsal other = (TbFutsal) object;
        if ((this.idFutsal == null && other.idFutsal != null) || (this.idFutsal != null && !this.idFutsal.equals(other.idFutsal))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.TbFutsal[ idFutsal=" + idFutsal + " ]";
    }


    

    public String getFasilitas() {
        return fasilitas;
    }

    public void setFasilitas(String fasilitas) {
        this.fasilitas = fasilitas;
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
