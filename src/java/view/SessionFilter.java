/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.io.IOException;
import javax.faces.application.ResourceHandler;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Danis
 */
@WebFilter(filterName = "SessionFilter",urlPatterns = {"/faces/app/*","/faces/loginPengelola.xhtml"})
public class SessionFilter implements Filter{

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //Filter.super.init(filterConfig); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);
        String loginURI = req.getContextPath()+"/faces/loginPengelola.xhtml";
        
        boolean loggedInPengelola = session != null && session.getAttribute("loggedPengelola") != null;
        boolean loggedInAdmin = session != null && session.getAttribute("loggedAdmin") != null;
        boolean loginRequest = req.getRequestURI().equals(loginURI);
        boolean resourceRequest = req.getRequestURI().startsWith(req.getContextPath() + ResourceHandler.RESOURCE_IDENTIFIER);
        
        if(loggedInPengelola && loginRequest){
            res.sendRedirect("Pengelola/pengelola.xhtml");
            chain.doFilter(request, response);
        }else if(loggedInAdmin && loginRequest){
            res.sendRedirect("Admin/admin.xhtml");
            chain.doFilter(request, response);
        }
        
        if(loggedInAdmin || loggedInPengelola || loginRequest || resourceRequest){
            chain.doFilter(request, response);
        }else{
            res.sendRedirect(loginURI);
        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy(); //To change body of generated methods, choose Tools | Templates.
    }
    
}
