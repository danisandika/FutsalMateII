/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import resourceTambahan.MailSender;




/**
 *
 * @author Danis
 */
@Named("mailCtr")
@SessionScoped
public class MailController implements Serializable{
    private String fromEmail;
    private String username;
    private String password;
    private String toMail;
    private String subject;
    private String message;

    
   
    
    public String getFromEmail() {
        return fromEmail;
    }

    public void setFromEmail(String fromEmail) {
        this.fromEmail = fromEmail;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToMail() {
        return toMail;
    }

    public void setToMail(String toMail) {
        this.toMail = toMail;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
    public void send(){
        try {
            MailSender mailSender = new MailSender();
            mailSender.sendEmail(fromEmail, username, password, toMail, subject, message);
        } catch (Exception e) {
            System.out.println("Gagal : "+e.toString());
        }
    }
}
