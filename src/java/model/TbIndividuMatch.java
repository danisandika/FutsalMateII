/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Rika AD
 */
@Entity
@Table(name = "tb_individumatch")
public class TbIndividuMatch implements Serializable {
    @Id
    private TbMatchteam idMatchTeam;
    private TbPemain idPemain;

    public TbIndividuMatch() {
    }

    public TbMatchteam getIdMatchTeam() {
        return idMatchTeam;
    }

    public void setIdMatchTeam(TbMatchteam idMatchTeam) {
        this.idMatchTeam = idMatchTeam;
    }

    public TbPemain getIdPemain() {
        return idPemain;
    }

    public void setIdPemain(TbPemain idPemain) {
        this.idPemain = idPemain;
    }

}
