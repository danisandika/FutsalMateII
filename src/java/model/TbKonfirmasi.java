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
import javax.validation.constraints.Size;

/**
 *
 * @author Danis
 */
@Entity
@Table(name = "tb_konfirmasi")
@NamedQueries({
    @NamedQuery(name = "TbKonfirmasi.findAll", query = "SELECT t FROM TbKonfirmasi t"),
    @NamedQuery(name = "TbKonfirmasi.findByIdKonfirmasi", query = "SELECT t FROM TbKonfirmasi t WHERE t.idKonfirmasi = :idKonfirmasi"),
    @NamedQuery(name = "TbKonfirmasi.findByNamaPengirim", query = "SELECT t FROM TbKonfirmasi t WHERE t.namaPengirim = :namaPengirim"),
    @NamedQuery(name = "TbKonfirmasi.findByNorekPengirim", query = "SELECT t FROM TbKonfirmasi t WHERE t.norekPengirim = :norekPengirim"),
    @NamedQuery(name = "TbKonfirmasi.findByJumlahBayar", query = "SELECT t FROM TbKonfirmasi t WHERE t.jumlahBayar = :jumlahBayar")})
public class TbKonfirmasi implements Serializable {

    @Size(max = 50)
    @Column(name = "nama_pengirim")
    private String namaPengirim;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_konfirmasi")
    private Integer idKonfirmasi;
    @Column(name = "norek_pengirim")
    private Integer norekPengirim;
    @Column(name = "jumlah_bayar")
    private Integer jumlahBayar;
    @JoinColumn(name = "id_pemesanan", referencedColumnName = "id_pemesanan")
    @ManyToOne(optional = false)
    private TbPemesanan idPemesanan;

    public TbKonfirmasi() {
    }

    public TbKonfirmasi(Integer idKonfirmasi) {
        this.idKonfirmasi = idKonfirmasi;
    }

    public Integer getIdKonfirmasi() {
        return idKonfirmasi;
    }

    public void setIdKonfirmasi(Integer idKonfirmasi) {
        this.idKonfirmasi = idKonfirmasi;
    }

    public String getNamaPengirim() {
        return namaPengirim;
    }

    public void setNamaPengirim(String namaPengirim) {
        this.namaPengirim = namaPengirim;
    }

    public Integer getNorekPengirim() {
        return norekPengirim;
    }

    public void setNorekPengirim(Integer norekPengirim) {
        this.norekPengirim = norekPengirim;
    }

    public Integer getJumlahBayar() {
        return jumlahBayar;
    }

    public void setJumlahBayar(Integer jumlahBayar) {
        this.jumlahBayar = jumlahBayar;
    }

    public TbPemesanan getIdPemesanan() {
        return idPemesanan;
    }

    public void setIdPemesanan(TbPemesanan idPemesanan) {
        this.idPemesanan = idPemesanan;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idKonfirmasi != null ? idKonfirmasi.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TbKonfirmasi)) {
            return false;
        }
        TbKonfirmasi other = (TbKonfirmasi) object;
        if ((this.idKonfirmasi == null && other.idKonfirmasi != null) || (this.idKonfirmasi != null && !this.idKonfirmasi.equals(other.idKonfirmasi))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.TbKonfirmasi[ idKonfirmasi=" + idKonfirmasi + " ]";
    }




    
}
