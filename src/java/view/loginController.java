/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import loginPackage.SessionUtils;
import model.TbPengelola;
import model.TbPemain;
import model.TbTeam;
import view.util.JsfUtil;

/**
 *
 * @author Danis
 */
@Named("loginController")
@SessionScoped
public class loginController implements Serializable{

    private String loginEmail,loginPassword;
    @EJB
    private controller.TbPengelolaFacade ejbPengelolaFacade;
    private controller.TbPemainFacade ejbPemainFacade;

    private List<TbPengelola> pengelolaList;
    private TbPengelola dataPengelola;

    private List<TbPemain> pemainList;
    private TbPemain dataPemain;

    public String getLoginEmail() {
        return loginEmail;
    }

    public void setLoginEmail(String loginEmail) {
        this.loginEmail = loginEmail;
    }

    public String getLoginPassword() {
        return loginPassword;
    }




    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword;
    }

    public List<TbPengelola> getPengelolaList() {
        return pengelolaList;
    }

    public void setPengelolaList(List<TbPengelola> pengelolaList) {
        this.pengelolaList = pengelolaList;
    }

    public TbPengelola getDataPengelola() {
        return dataPengelola;
    }

    public void setDataPengelola(TbPengelola dataPengelola) {
        this.dataPengelola = dataPengelola;
    }


    public List<TbPemain> getPemainList() {
        return pemainList;
    }

    public void setPemainList(List<TbPemain> pemainList) {
        this.pemainList = pemainList;


    public String loginPengelola() {
        try {
            boolean autentikasi = ejbPengelolaFacade.getAutentikasi(loginEmail, loginPassword);
            pengelolaList = ejbPengelolaFacade.getData(loginEmail);
            dataPengelola = ejbPengelolaFacade.getDataLogin(loginEmail);
            //name = penggunaList.get(0).getMsPenggunaNama();
            //jabatan = penggunaList.get(0).getMsPenggunaRole();
            //msPenggunaId = penggunaList.get(0).getMsPenggunaId();
            dataPengelola = pengelolaList.get(0);

            if (autentikasi == true) {
                HttpSession session = SessionUtils.getSession();
                session.setAttribute("templateEmail", pengelolaList.get(0).getEmail());
                session.setAttribute("templateNama", pengelolaList.get(0).getNama());
                session.setAttribute("templateID", pengelolaList.get(0).getIdPengelola());
                session.setAttribute("templateIDFutsal", pengelolaList.get(0).getIdFutsal().getIdFutsal());
                session.setAttribute("templateNamaFutsal", pengelolaList.get(0).getIdFutsal().getNamaFutsal());
                session.setAttribute("templateFoto", pengelolaList.get(0).getFoto());
                session.setAttribute("templateStatus", pengelolaList.get(0).getStatus());

                return "pengelola";
            } else {
                JsfUtil.addErrorMessage("Login Gagal Email : "+loginEmail);
                return "loginPengelola";
            }

        } catch (Exception e) {
            JsfUtil.addErrorMessage("Pengelola tidak ditemukan!");
            setLoginEmail(null);
            setLoginPassword(null);

            return null;
        }
    }

    public String loginPemain() {
        try {
            boolean autentikasi = ejbPemainFacade.getAutentikasi(loginEmail, loginPassword);
            pemainList = ejbPemainFacade.getData(loginEmail);
            dataPemain = ejbPemainFacade.getDataLogin(loginEmail);
            //name = penggunaList.get(0).getMsPenggunaNama();
            //jabatan = penggunaList.get(0).getMsPenggunaRole();
            //msPenggunaId = penggunaList.get(0).getMsPenggunaId();

            if (autentikasi == true) {
                boolean isCaptain = ejbPemainFacade.getAutentikasiCaptain(pemainList.get(0).getIdPemain());
                TbTeam idTeam = null;

                if (pemainList.get(0).getIdTeam() != null) {
                    idTeam = pemainList.get(0).getIdTeam();
                }

                HttpSession session = SessionUtils.getSession();
                session.setAttribute("templateEmail", pemainList.get(0).getEmail());
                session.setAttribute("templateNama", pemainList.get(0).getNama());
                session.setAttribute("templateIDPemain", pemainList.get(0).getIdPemain());
                session.setAttribute("templateIsCaptain", isCaptain);
                session.setAttribute("templateIdTeam", idTeam);

                return "index";
            } else {
                JsfUtil.addErrorMessage("Login Gagal Email : "+loginEmail);
                return "UserPemain/SignIn";
            }

        } catch (Exception e) {
            JsfUtil.addErrorMessage("Pemain tidak ditemukan!");
            setLoginEmail(null);
            setLoginPassword(null);

            return null;
        }
    }


        public String logout() {
		HttpSession session = SessionUtils.getSession();
		session.invalidate();
		return "loginPengelola";
	}
}
