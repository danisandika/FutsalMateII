/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.TbAdminFacade;
import controller.TbPengelolaFacade;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.servlet.http.Part;
import model.TbAdmin;
import model.TbPengelola;
import view.util.JsfUtil;

/**
 *
 * @author Danis
 */
@Named("editUser")
@SessionScoped
public class editUser implements Serializable{
    private TbPengelola tbPengelola;
    private TbAdmin tbAdmin;
    @EJB
    private controller.TbPengelolaFacade ejbPengelolaFacade;
    @EJB
    private controller.TbAdminFacade ejbAdminFacade;
    private String url;
    private Part gambar;

    public TbPengelola getTbPengelola() {
        return tbPengelola;
    }

    public void setTbPengelola(TbPengelola tbPengelola) {
        this.tbPengelola = tbPengelola;
    }
    
    public void editProfilAdmin(Integer id){
        
        tbAdmin = ejbAdminFacade.getDataAdmin(id);
    }
    
    public void editPasswordAdmin(Integer id){
        
        tbAdmin = ejbAdminFacade.getDataAdmin(id);
    }
    
    public void editProfilPengelola(Integer id){
        tbPengelola = ejbPengelolaFacade.getDataPengelola(id);
    }

    public void editPasswordPengelola(Integer id){
        tbPengelola = ejbPengelolaFacade.getDataPengelola(id);
    }
    
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Part getGambar() {
        return gambar;
    }

    public void setGambar(Part gambar) {
        this.gambar = gambar;
    }

    public TbPengelolaFacade getEjbPengelolaFacade() {
        return ejbPengelolaFacade;
    }

    public TbAdminFacade getEjbAdminFacade() {
        return ejbAdminFacade;
    }

    public TbAdmin getTbAdmin() {
        return tbAdmin;
    }

    public void setTbAdmin(TbAdmin tbAdmin) {
        this.tbAdmin = tbAdmin;
    }
    
    
    
    
    
    public String uploadPengelola() {
        
        
        try {
            InputStream in = gambar.getInputStream();
            setGambar(gambar);
            File f = new File("C://Users//Danis//Desktop//PRG7//FutsalMateII//web//Image_profil_pengelola//" + gambar.getSubmittedFileName());
            f.createNewFile();
//            url = f.toString();
            url = gambar.getSubmittedFileName();
            FileOutputStream out = new FileOutputStream(f);
            try (InputStream input = gambar.getInputStream()) {
                Files.copy(input, new File("C://Users//Danis//Desktop//PRG7//FutsalMateII//web//Image_profil_pengelola//" + gambar.getSubmittedFileName()).toPath());
            } catch (IOException e) {
                // Show faces message?
            }
            byte[] buffer = new byte[1024];
            int length;

            while ((length = in.read(buffer)) > 0) {
                out.write(buffer);
            }
            out.close();
            in.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return url;
    }
    
    
    public String uploadAdmin() {
        
        
        try {
            InputStream in = gambar.getInputStream();
            setGambar(gambar);
            File f = new File("C://Users//Danis//Desktop//PRG7//FutsalMateII//web//Image_profil_admin//" + gambar.getSubmittedFileName());
            f.createNewFile();
//            url = f.toString();
            url = gambar.getSubmittedFileName();
            FileOutputStream out = new FileOutputStream(f);
            try (InputStream input = gambar.getInputStream()) {
                Files.copy(input, new File("C://Users//Danis//Desktop//PRG7//FutsalMateII//web//Image_profil_admin//" + gambar.getSubmittedFileName()).toPath());
            } catch (IOException e) {
                // Show faces message?
            }
            byte[] buffer = new byte[1024];
            int length;

            while ((length = in.read(buffer)) > 0) {
                out.write(buffer);
            }
            out.close();
            in.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return url;
    }
    
    public String updatePengelola() {
        try {
            if(gambar != null){
                uploadPengelola();
                tbPengelola.setFoto(url);
            }
            getEjbPengelolaFacade().edit(tbPengelola);
            JsfUtil.addSuccessMessage("Sukses Update Profil Pengelola");
            return "pengelola";
        } catch (Exception e) {
            JsfUtil.addErrorMessage("Gagal Update Profil Pengelola : "+e.toString());
            return null;
        }
    }
    
    public String updateAdmin() {
        try {
            if(gambar != null){
                uploadAdmin();
                tbAdmin.setFoto(url);
            }
            getEjbAdminFacade().edit(tbAdmin);
            JsfUtil.addSuccessMessage("Sukses Update Profil Administrator");
            return "Admin/admin";
        } catch (Exception e) {
            JsfUtil.addErrorMessage("Gagal Update Profil Administrator : "+e.toString());
            return null;
        }
    }
}
