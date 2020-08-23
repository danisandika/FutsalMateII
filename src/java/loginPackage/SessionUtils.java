/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loginPackage;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import model.TbAdmin;
import model.TbPengelola;

/**
 *
 * @author Danis
 */
public class SessionUtils {
        public static HttpSession getSession(){
        return (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(false);
    }
    
    public static HttpServletRequest getRequest(){
        return (HttpServletRequest) FacesContext.getCurrentInstance()
                .getExternalContext().getRequest();
    }
    
    public static Integer getId(){
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
        //HttpSession session = getSession();
        TbPengelola tbPengelola = (TbPengelola) session.getAttribute("loggedPengelola");
        return tbPengelola.getIdPengelola();
    }
    
    public static Integer getIdAdmin(){
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
        //HttpSession session = getSession();
        TbAdmin tbAdmin = (TbAdmin) session.getAttribute("loggedAdmin");
        return tbAdmin.getIdAdmin();
    }
}
