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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
@Table(name = "tb_pengelola")
@NamedQueries({
    @NamedQuery(name = "TbPengelola.findAll", query = "SELECT t FROM TbPengelola t"),
    @NamedQuery(name = "TbPengelola.findByIdPengelola", query = "SELECT t FROM TbPengelola t WHERE t.idPengelola = :idPengelola"),
    @NamedQuery(name = "TbPengelola.findByEmail", query = "SELECT t FROM TbPengelola t WHERE t.email = :email"),
    @NamedQuery(name = "TbPengelola.findByPassword", query = "SELECT t FROM TbPengelola t WHERE t.password = :password"),
    @NamedQuery(name = "TbPengelola.findByAlamat", query = "SELECT t FROM TbPengelola t WHERE t.alamat = :alamat"),
    @NamedQuery(name = "TbPengelola.findByTglLahir", query = "SELECT t FROM TbPengelola t WHERE t.tglLahir = :tglLahir"),
    @NamedQuery(name = "TbPengelola.findByFotoKtp", query = "SELECT t FROM TbPengelola t WHERE t.fotoKtp = :fotoKtp"),
    @NamedQuery(name = "TbPengelola.findByFoto", query = "SELECT t FROM TbPengelola t WHERE t.foto = :foto"),
    @NamedQuery(name = "TbPengelola.findByStatus", query = "SELECT t FROM TbPengelola t WHERE t.status = :status")})
public class TbPengelola implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "nama")
    private String nama;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "notelp")
    private String notelp;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "email")
    private String email;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "password")
    private String password;
    @Size(max = 2147483647)
    @Column(name = "alamat")
    private String alamat;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "foto_ktp")
    private String fotoKtp;
    @Size(max = 2147483647)
    @Column(name = "foto")
    private String foto;
    @Basic(optional = false)
    @NotNull
    @Column(name = "status")
    private int status;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_pengelola")
    private Integer idPengelola;
    @Column(name = "tgl_lahir")
    @Temporal(TemporalType.DATE)
    private Date tglLahir;
    @JoinColumn(name = "id_futsal", referencedColumnName = "id_futsal")
    @ManyToOne
    private TbFutsal idFutsal;

    public TbPengelola() {
    }

    public TbPengelola(Integer idPengelola) {
        this.idPengelola = idPengelola;
    }

    public TbPengelola(Integer idPengelola, String email, String password, String fotoKtp, int status) {
        this.idPengelola = idPengelola;
        this.email = email;
        this.password = password;
        this.fotoKtp = fotoKtp;
        this.status = status;
    }

    public Integer getIdPengelola() {
        return idPengelola;
    }

    public void setIdPengelola(Integer idPengelola) {
        this.idPengelola = idPengelola;
    }


    public Date getTglLahir() {
        return tglLahir;
    }

    public void setTglLahir(Date tglLahir) {
        this.tglLahir = tglLahir;
    }

    public String getFotoKtp() {
        return fotoKtp;
    }

    public void setFotoKtp(String fotoKtp) {
        this.fotoKtp = fotoKtp;
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
        hash += (idPengelola != null ? idPengelola.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TbPengelola)) {
            return false;
        }
        TbPengelola other = (TbPengelola) object;
        if ((this.idPengelola == null && other.idPengelola != null) || (this.idPengelola != null && !this.idPengelola.equals(other.idPengelola))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.TbPengelola[ idPengelola=" + idPengelola + " ]";
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNotelp() {
        return notelp;
    }

    public void setNotelp(String notelp) {
        this.notelp = notelp;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

   


    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    
}
