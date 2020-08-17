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
import javax.persistence.ManyToMany;
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
@Table(name = "tb_pemain")
@NamedQueries({
    @NamedQuery(name = "TbPemain.findAll", query = "SELECT t FROM TbPemain t"),
    @NamedQuery(name = "TbPemain.findByIdPemain", query = "SELECT t FROM TbPemain t WHERE t.idPemain = :idPemain"),
    @NamedQuery(name = "TbPemain.findByNama", query = "SELECT t FROM TbPemain t WHERE t.nama = :nama"),
    @NamedQuery(name = "TbPemain.findByGender", query = "SELECT t FROM TbPemain t WHERE t.gender = :gender"),
    @NamedQuery(name = "TbPemain.findByAlamat", query = "SELECT t FROM TbPemain t WHERE t.alamat = :alamat"),
    @NamedQuery(name = "TbPemain.findByNotelp", query = "SELECT t FROM TbPemain t WHERE t.notelp = :notelp"),
    @NamedQuery(name = "TbPemain.findByEmail", query = "SELECT t FROM TbPemain t WHERE t.email = :email"),
    @NamedQuery(name = "TbPemain.findByPassword", query = "SELECT t FROM TbPemain t WHERE t.password = :password")})
public class TbPemain implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "nama")
    private String nama;
    @Size(max = 2147483647)
    @Column(name = "alamat")
    private String alamat;
    @Size(max = 15)
    @Column(name = "notelp")
    private String notelp;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
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

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_pemain")
    private Integer idPemain;
    @Column(name = "gender")
    private Integer gender;
    @ManyToMany(mappedBy = "tbPemainCollection")
    private Collection<TbMatchteam> tbMatchteamCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "captain")
    private Collection<TbTeam> tbTeamCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idPemain")
    private Collection<TbRating> tbRatingCollection;
    @JoinColumn(name = "id_team", referencedColumnName = "id_team")
    @ManyToOne
    private TbTeam idTeam;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idPemain")
    private Collection<TbPemesanan> tbPemesananCollection;

    public TbPemain() {
    }

    public TbPemain(Integer idPemain) {
        this.idPemain = idPemain;
    }

    public TbPemain(Integer idPemain, String nama, String email, String password) {
        this.idPemain = idPemain;
        this.nama = nama;
        this.email = email;
        this.password = password;
    }

    public Integer getIdPemain() {
        return idPemain;
    }

    public void setIdPemain(Integer idPemain) {
        this.idPemain = idPemain;
    }


    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }


    public Collection<TbMatchteam> getTbMatchteamCollection() {
        return tbMatchteamCollection;
    }

    public void setTbMatchteamCollection(Collection<TbMatchteam> tbMatchteamCollection) {
        this.tbMatchteamCollection = tbMatchteamCollection;
    }

    public Collection<TbTeam> getTbTeamCollection() {
        return tbTeamCollection;
    }

    public void setTbTeamCollection(Collection<TbTeam> tbTeamCollection) {
        this.tbTeamCollection = tbTeamCollection;
    }

    public Collection<TbRating> getTbRatingCollection() {
        return tbRatingCollection;
    }

    public void setTbRatingCollection(Collection<TbRating> tbRatingCollection) {
        this.tbRatingCollection = tbRatingCollection;
    }

    public TbTeam getIdTeam() {
        return idTeam;
    }

    public void setIdTeam(TbTeam idTeam) {
        this.idTeam = idTeam;
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
        hash += (idPemain != null ? idPemain.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TbPemain)) {
            return false;
        }
        TbPemain other = (TbPemain) object;
        if ((this.idPemain == null && other.idPemain != null) || (this.idPemain != null && !this.idPemain.equals(other.idPemain))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.TbPemain[ idPemain=" + idPemain + " ]";
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
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
    
}
