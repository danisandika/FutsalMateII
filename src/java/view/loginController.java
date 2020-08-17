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
import model.TbAdmin;
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
    @EJB
    private controller.TbPemainFacade ejbPemainFacade;
    @EJB
    private controller.TbAdminFacade ejbAdminFacade;

    private List<TbPengelola> pengelolaList;
    private TbPengelola dataPengelola;
    
    private List<TbAdmin> adminList;
    private TbAdmin dataAdmin;

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

    public List<TbAdmin> getAdminList() {
        return adminList;
    }

    public void setAdminList(List<TbAdmin> adminList) {
        this.adminList = adminList;
    }

    public TbAdmin getDataAdmin() {
        return dataAdmin;
    }

    public void setDataAdmin(TbAdmin dataAdmin) {
        this.dataAdmin = dataAdmin;
    }

    public TbPemain getDataPemain() {
        return dataPemain;
    }

    public void setDataPemain(TbPemain dataPemain) {
        this.dataPemain = dataPemain;
    }

    

    public List<TbPemain> getPemainList() {
        return pemainList;
    }

    public void setPemainList(List<TbPemain> pemainList) {
        this.pemainList = pemainList;
    }

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
    
    
    public String loginAdministrator() {
        try {
            boolean auth = ejbAdminFacade.getAutentikasi(loginEmail, loginPassword);
            adminList = ejbAdminFacade.getData(loginEmail);
            dataAdmin = ejbAdminFacade.getDataLogin(loginEmail);
            
            //name = penggunaList.get(0).getMsPenggunaNama();
            //jabatan = penggunaList.get(0).getMsPenggunaRole();
            //msPenggunaId = penggunaList.get(0).getMsPenggunaId();
            dataAdmin = adminList.get(0);

            if (auth == true) {
                HttpSession session = SessionUtils.getSession();
                session.setAttribute("templateEmailAdmin", adminList.get(0).getEmail());
                session.setAttribute("templateNamaAdmin", adminList.get(0).getNama());
                session.setAttribute("templateIDAdmin", adminList.get(0).getIdAdmin());
              
                session.setAttribute("templateFotoAdmin", adminList.get(0).getFoto());
                session.setAttribute("templateStatusAdmin", adminList.get(0).getStatus());

                return "Admin/admin";
            } else {
                JsfUtil.addErrorMessage("Login Gagal Email : "+loginEmail);
                return "loginAdministrator";
            }

        } catch (Exception e) {
            JsfUtil.addErrorMessage("Administrator tidak ditemukan!");
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

    
        public String dasTotalLapangan(String id){
            String totalLap = null;
            try{
                totalLap = ejbPengelolaFacade.getCountLapangan(Integer.valueOf(id));
                return totalLap;
            }catch(Exception e){
                return null;
            } 
        }
        
        public String dasPemesananLapangan(String id){
            String totalPemesanan = null;
            try{
                totalPemesanan = ejbPengelolaFacade.getCountPemesananLapangan(Integer.valueOf(id));
                return totalPemesanan;
            }catch(Exception e){
                return null;
            } 
        }
        
        public Integer dasTotalBayarKonfirmasi(String id){
            Integer totalBayar = null;
            try{
                totalBayar = Integer.valueOf(ejbPengelolaFacade.getSUMBayarLapangan(Integer.valueOf(id)));
                return totalBayar;
            }catch(Exception e){
                return null;
            } 
        }
        
        
        public String dasTotalMember(){
            
            String totalMem = null;
            try{
                totalMem = ejbAdminFacade.getCountMember();
                return totalMem;
            }catch(Exception e){
                return null;
            } 
        }
        
        public String dasTotalUangMasukSewaFutsal(){
            
            String totalSewa = null;
            try{
                totalSewa = ejbAdminFacade.getSumSewaLap();
                return totalSewa;
            }catch(Exception e){
                return null;
            } 
        }
        
        
        public String dasTransaksiSewaFutsal(){
            
            String totalSewa = null;
            try{
                totalSewa = ejbAdminFacade.getCountSewaLap();
                return totalSewa;
            }catch(Exception e){
                return null;
            } 
        }

        public String logoutPengelola() {
		HttpSession session = SessionUtils.getSession();
		session.invalidate();
		return "loginPengelola";
	}
        
        public String logoutAdmin() {
		HttpSession ses = SessionUtils.getSession();
		ses.invalidate();
		return "loginAdministrator";
	}
        
        
}
