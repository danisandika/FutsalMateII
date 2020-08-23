/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.io.Serializable;
import java.util.List;
import java.util.Random;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import model.TbAdmin;
import view.util.JsfUtil;

/**
 *
 * @author Danis
 */
@Named("loginAdministratorController")
@SessionScoped
public class loginAdministratorController implements Serializable{
    private String loginEmail,loginPassword;

    @EJB
    private controller.TbAdminFacade ejbAdminFacade;

   
    private List<TbAdmin> adminList;
    private TbAdmin dataAdmin;

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

    

    
    
    
    public String loginAdministrator() {
        try {
            boolean auth = ejbAdminFacade.getAutentikasi(loginEmail, loginPassword);
            adminList = ejbAdminFacade.getData(loginEmail);
            dataAdmin = ejbAdminFacade.getDataLogin(loginEmail);
            
            //name = penggunaList.get(0).getMsPenggunaNama();
            //jabatan = penggunaList.get(0).getMsPenggunaRole();
            //msPenggunaId = penggunaList.get(0).getMsPenggunaId();
            dataAdmin = adminList.get(0);
            FacesContext context = FacesContext.getCurrentInstance();   
            if (auth == true) {
               context.getExternalContext().getSessionMap().put("loggedAdmin", dataAdmin);

                return "Admin/admin";
            } else {
                JsfUtil.addErrorMessage("Login Gagal Username atau Password Salah");
                return "loginAdministrator";
            }

        } catch (Exception e) {
            JsfUtil.addErrorMessage("Administrator tidak ditemukan!");
            setLoginEmail(null);
            setLoginPassword(null);

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

        
        public String logoutAdmin() {
                //FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		//HttpSession ses = SessionUtils.getSession();
		//ses.invalidate();
                FacesContext facesContext = FacesContext.getCurrentInstance();
                HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
                session.removeAttribute("loggedAdmin");
                session.invalidate();
		return "/loginAdministrator";
	}
        
        private boolean showChart;

        public boolean isShowChart() {
            return showChart;
        }

        public void setShowChart(boolean showChart) {
            this.showChart = showChart;
        }

        public void tampilChart()
        {
            showChart =  ejbAdminFacade.getBooleanchart();
        }
        
    protected String getSaltString() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 11) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;

    }
    
    public String gantiPassAdmin(){
        String newPass = getSaltString();
        MailController mctr = new MailController();
        
        mctr.setFromEmail("pendekarbayangan66@gmail.com");
        mctr.setUsername("pendekarbayangan66@gmail.com");
        mctr.setPassword("praditya");
        mctr.setSubject("Ubah Password");
        mctr.setToMail(loginEmail);
        mctr.setMessage("Selamat, Ubah Password Anda Berhasil. Silahkan Masukan Password baru anda : "+newPass+". Dan Jangan lupa untuk mengganti password secara berkala.\n Terima Kasih");
        
         try{
             ejbAdminFacade.ubahPasswordAdmin(loginEmail, newPass);
             JsfUtil.addSuccessMessage("Ubah Password Anda Berhasil");
             mctr.send();
        }catch(Exception e){
            JsfUtil.addErrorMessage("Ubah Password Gagal : "+e.toString());
        }
        
        
        return "loginAdministrator";
    }
}
