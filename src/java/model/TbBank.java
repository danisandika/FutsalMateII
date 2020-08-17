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
@Table(name = "tb_bank")
@NamedQueries({
    @NamedQuery(name = "TbBank.findAll", query = "SELECT t FROM TbBank t"),
    @NamedQuery(name = "TbBank.findByIdBank", query = "SELECT t FROM TbBank t WHERE t.idBank = :idBank"),
    @NamedQuery(name = "TbBank.findByNamaBank", query = "SELECT t FROM TbBank t WHERE t.namaBank = :namaBank"),
    @NamedQuery(name = "TbBank.findByNoRekening", query = "SELECT t FROM TbBank t WHERE t.noRekening = :noRekening"),
    @NamedQuery(name = "TbBank.findByKodeBank", query = "SELECT t FROM TbBank t WHERE t.kodeBank = :kodeBank"),
    @NamedQuery(name = "TbBank.findByNamaRekening", query = "SELECT t FROM TbBank t WHERE t.namaRekening = :namaRekening")})
public class TbBank implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "nama_bank")
    private String namaBank;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "no_rekening")
    private String noRekening;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "kode_bank")
    private String kodeBank;
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
    @Column(name = "id_bank")
    private Integer idBank;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idBank")
    private Collection<TbSewalapangan> tbSewalapanganCollection;

    public TbBank() {
    }

    public TbBank(Integer idBank) {
        this.idBank = idBank;
    }

    public TbBank(Integer idBank, String namaBank, String noRekening, String kodeBank, String namaRekening) {
        this.idBank = idBank;
        this.namaBank = namaBank;
        this.noRekening = noRekening;
        this.kodeBank = kodeBank;
        this.namaRekening = namaRekening;
    }

    public Integer getIdBank() {
        return idBank;
    }

    public void setIdBank(Integer idBank) {
        this.idBank = idBank;
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

    public String getKodeBank() {
        return kodeBank;
    }

    public void setKodeBank(String kodeBank) {
        this.kodeBank = kodeBank;
    }

    public String getNamaRekening() {
        return namaRekening;
    }

    public void setNamaRekening(String namaRekening) {
        this.namaRekening = namaRekening;
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
        hash += (idBank != null ? idBank.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TbBank)) {
            return false;
        }
        TbBank other = (TbBank) object;
        if ((this.idBank == null && other.idBank != null) || (this.idBank != null && !this.idBank.equals(other.idBank))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return namaBank;
    }




    

    

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    
}
