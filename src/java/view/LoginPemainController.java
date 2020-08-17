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
import model.TbPemain;
import model.TbTeam;
import view.util.JsfUtil;

/**
 *
 * @author Rika AD
 */
@Named("loginPemainController")
@SessionScoped
public class LoginPemainController implements Serializable{
    private String loginEmail,loginPassword;
    
    @EJB
    private controller.TbPemainFacade ejbPemainFacade;
    
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

    public List<TbPemain> getPemainList() {
        return pemainList;
    }

    public void setPemainList(List<TbPemain> pemainList) {
        this.pemainList = pemainList;
    }

    public TbPemain getDataPemain() {
        return dataPemain;
    }

    public void setDataPemain(TbPemain dataPemain) {
        this.dataPemain = dataPemain;
    }
    
    
    
    
    public String loginPemain() {
        boolean autentikasi = ejbPemainFacade.getAutentikasi(loginEmail, loginPassword);
        try {
            autentikasi = ejbPemainFacade.getAutentikasi(loginEmail, loginPassword);
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
                session.setAttribute("templateLogin", autentikasi);
                
                session.setAttribute("templateIsCaptain", isCaptain);
                session.setAttribute("templateIdTeam", idTeam);
                session.setAttribute("templateDataPemain", dataPemain);
         
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
    
    public void refreshDataCreateNewTeam(String emailUser, String passUser) {
        boolean autentikasi = ejbPemainFacade.getAutentikasi(emailUser, passUser);
        try {
            autentikasi = ejbPemainFacade.getAutentikasi(emailUser, passUser);
            pemainList = ejbPemainFacade.getData(emailUser);
            dataPemain = ejbPemainFacade.getDataLogin(emailUser);
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
                session.setAttribute("templateLogin", autentikasi);
                session.setAttribute("templateDataPemain", dataPemain);
         
            } else {
                JsfUtil.addErrorMessage("Login Gagal Email : "+loginEmail);
            }

        } catch (Exception e) {
            JsfUtil.addErrorMessage("Pemain tidak ditemukan!");
        }
    }
    
    public String logout() {
        HttpSession session = SessionUtils.getSession();
        session.removeAttribute("templateEmail");
        session.removeAttribute("templateNama");
        session.removeAttribute("templateIDPemain");
        session.removeAttribute("templateIsCaptain");
        session.removeAttribute("templateIdTeam");
        session.removeAttribute("templateLogin");
        session.removeAttribute("templateDataPemain");
        session.invalidate();
        dataPemain = null;
        return "index";
    }
}
