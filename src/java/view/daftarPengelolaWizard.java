/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.TbFutsalFacade;
import controller.TbPengelolaFacade;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.servlet.http.Part;
import model.TbFutsal;
import model.TbPengelola;
import view.util.JsfUtil;

/**
 *
 * @author Danis
 */
@Named("daftarPengelolaWizard")
@SessionScoped
public class daftarPengelolaWizard implements Serializable {
 
    private TbPengelola pengelola = new TbPengelola();
    private TbFutsal futsal = new TbFutsal();
    private String url;
    private Part gambar;
    @EJB
    private TbPengelolaFacade facadePengelola;
    @EJB
    private TbFutsalFacade facadeFutsal;
     
     
    public TbPengelola getPengelola() {
        return pengelola;
    }
 
    public void setUser(TbPengelola pengelola) {
        this.pengelola = pengelola;
    }

    public TbFutsal getFutsal() {
        return futsal;
    }

    public void setFutsal(TbFutsal futsal) {
        this.futsal = futsal;
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

    public TbPengelolaFacade getFacadePengelola() {
        return facadePengelola;
    }

    public void setFacadePengelola(TbPengelolaFacade facadePengelola) {
        this.facadePengelola = facadePengelola;
    }

    
    public TbFutsalFacade getFacadeFutsal() {
        return facadeFutsal;
    }

    public void setFacadeFutsal(TbFutsalFacade facadeFutsal) {
        this.facadeFutsal = facadeFutsal;
    }
    

    
    public String upload() {
        
        
        try {
            InputStream in = gambar.getInputStream();
            setGambar(gambar);
            File f = new File("C://Users//Danis//Desktop//PRG7//FutsalMateII//web//Image_ktp_pengelola//" + gambar.getSubmittedFileName());
            f.createNewFile();
//            url = f.toString();
            url = gambar.getSubmittedFileName();
            FileOutputStream out = new FileOutputStream(f);
            try (InputStream input = gambar.getInputStream()) {
                Files.copy(input, new File("C://Users//Danis//Desktop//PRG7//FutsalMateII//web//Image_ktp_pengelola//" + gambar.getSubmittedFileName()).toPath());
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
     
    public String save() {   
        boolean EmailExists = getFacadePengelola().getEmailNotExist(pengelola.getEmail());
        
        if(EmailExists == false){
            try {
               upload();
               pengelola.setStatus(0);
               pengelola.setFotoKtp(url);
               getFacadePengelola().create(pengelola);
               //JsfUtil.addSuccessMessage("Sukses Memasukan Data");
           } catch (Exception e) {
               JsfUtil.addErrorMessage("Gagal Memasukan Data Pengelola: "+e.toString());
           }


           try{
               futsal.setLongitude(106.827153);
               futsal.setLatitude(-6.175392);
               futsal.setStatus(1);
               futsal.setNotelpFutsal(pengelola.getNotelp());
               getFacadeFutsal().create(futsal);
               JsfUtil.addSuccessMessage("Sukses Memasukan Data");
               return "loginPengelola";
           }catch(Exception e){
               JsfUtil.addErrorMessage("Gagal Memasukan Data Futsal : "+e.toString());
               return null;
           }
        }else{
            JsfUtil.addErrorMessage("Email sudah terdaftar sebagai Pengelola Futsal !");
            return null;
        }
        //return "loginPengelola";
    }
     
}
