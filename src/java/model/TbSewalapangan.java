/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
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
@Table(name = "tb_sewalapangan")
@NamedQueries({
    @NamedQuery(name = "TbSewalapangan.findAll", query = "SELECT t FROM TbSewalapangan t"),
    @NamedQuery(name = "TbSewalapangan.findByIdSewalapangan", query = "SELECT t FROM TbSewalapangan t WHERE t.idSewalapangan = :idSewalapangan"),
    @NamedQuery(name = "TbSewalapangan.findByTglPembayaran", query = "SELECT t FROM TbSewalapangan t WHERE t.tglPembayaran = :tglPembayaran"),
    @NamedQuery(name = "TbSewalapangan.findByTglSewa", query = "SELECT t FROM TbSewalapangan t WHERE t.tglSewa = :tglSewa"),
    @NamedQuery(name = "TbSewalapangan.findByTglBerakhir", query = "SELECT t FROM TbSewalapangan t WHERE t.tglBerakhir = :tglBerakhir"),
    @NamedQuery(name = "TbSewalapangan.findByWaktuSewa", query = "SELECT t FROM TbSewalapangan t WHERE t.waktuSewa = :waktuSewa"),
    @NamedQuery(name = "TbSewalapangan.findByJumlahUang", query = "SELECT t FROM TbSewalapangan t WHERE t.jumlahUang = :jumlahUang"),
    @NamedQuery(name = "TbSewalapangan.findByStatusBayar", query = "SELECT t FROM TbSewalapangan t WHERE t.statusBayar = :statusBayar")})
public class TbSewalapangan implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "status_bayar")
    private int statusBayar;
    @Size(max = 2147483647)
    @Column(name = "bukti_transfer")
    private String buktiTransfer;

    

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size( max = 20)
    @Column(name = "id_sewalapangan")
    private String idSewalapangan;
    @Column(name = "tgl_pembayaran")
    @Temporal(TemporalType.DATE)
    private Date tglPembayaran;
    @Column(name = "tgl_sewa")
    @Temporal(TemporalType.DATE)
    private Date tglSewa;
    @Column(name = "tgl_berakhir")
    @Temporal(TemporalType.DATE)
    private Date tglBerakhir;
    @Column(name = "waktu_sewa")
    private Integer waktuSewa;
    @Column(name = "jumlah_uang")
    private Integer jumlahUang;
    @JoinColumn(name = "id_admin", referencedColumnName = "id_admin")
    @ManyToOne
    private TbAdmin idAdmin;
    @JoinColumn(name = "id_bank", referencedColumnName = "id_bank")
    @ManyToOne(optional = false)
    private TbBank idBank;
    @JoinColumn(name = "id_futsal", referencedColumnName = "id_futsal")
    @ManyToOne(optional = false)
    private TbFutsal idFutsal;
    public TbSewalapangan() {
    }

    public TbSewalapangan(String idSewalapangan) {
        this.idSewalapangan = idSewalapangan;
    }

    public TbSewalapangan(String idSewalapangan, int statusBayar) {
        this.idSewalapangan = idSewalapangan;
        this.statusBayar = statusBayar;
    }

    public String getIdSewalapangan() {
        return idSewalapangan;
    }

    public void setIdSewalapangan(String idSewalapangan) {
        this.idSewalapangan = idSewalapangan;
    }

    public Date getTglPembayaran() {
        return tglPembayaran;
    }

    public void setTglPembayaran(Date tglPembayaran) {
        this.tglPembayaran = tglPembayaran;
    }

    public Date getTglSewa() {
        return tglSewa;
    }

    public void setTglSewa(Date tglSewa) {
        this.tglSewa = tglSewa;
    }

    public Date getTglBerakhir() {
        return tglBerakhir;
    }

    public void setTglBerakhir(Date tglBerakhir) {
        this.tglBerakhir = tglBerakhir;
    }

    public Integer getWaktuSewa() {
        return waktuSewa;
    }

    public void setWaktuSewa(Integer waktuSewa) {
        this.waktuSewa = waktuSewa;
    }

    public Integer getJumlahUang() {
        return jumlahUang;
    }

    public void setJumlahUang(Integer jumlahUang) {
        this.jumlahUang = jumlahUang;
    }

    public int getStatusBayar() {
        return statusBayar;
    }

    public void setStatusBayar(int statusBayar) {
        this.statusBayar = statusBayar;
    }

    public TbAdmin getIdAdmin() {
        return idAdmin;
    }

    public void setIdAdmin(TbAdmin idAdmin) {
        this.idAdmin = idAdmin;
    }

    public TbBank getIdBank() {
        return idBank;
    }

    public void setIdBank(TbBank idBank) {
        this.idBank = idBank;
    }

    public TbFutsal getIdFutsal() {
        return idFutsal;
    }

    public void setIdFutsal(TbFutsal idFutsal) {
        this.idFutsal = idFutsal;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idSewalapangan != null ? idSewalapangan.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TbSewalapangan)) {
            return false;
        }
        TbSewalapangan other = (TbSewalapangan) object;
        if ((this.idSewalapangan == null && other.idSewalapangan != null) || (this.idSewalapangan != null && !this.idSewalapangan.equals(other.idSewalapangan))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.TbSewalapangan[ idSewalapangan=" + idSewalapangan + " ]";
    }



    public String getBuktiTransfer() {
        return buktiTransfer;
    }

    public void setBuktiTransfer(String buktiTransfer) {
        this.buktiTransfer = buktiTransfer;
    }



    

 




    
}
