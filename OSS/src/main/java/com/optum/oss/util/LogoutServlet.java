/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.optum.oss.util;

import com.optum.oss.constants.ApplicationConstants;
import com.optum.oss.constants.SessionConstants;
import com.optum.oss.dto.UserProfileDTO;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;

/**
 *
 * @author hpasupuleti
 */
public class LogoutServlet extends HttpServlet {

    
    private static final Logger log = Logger.getLogger(LogoutServlet.class);
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession();
         String appPath = System.getProperty("OSS_APPLICATION_URL");
         PrintWriter out = response.getWriter();
        try  {
            DeleteCookies deleteCookies=new DeleteCookies();
            SessionTimeOutDAO sessTimeOutDAO = new  SessionTimeOutDAO();
           
            if (session.getAttribute(SessionConstants.USER_SESSION) != null) {
                log.info("In logOut:User Session Found");
                UserProfileDTO userSessionDto = (UserProfileDTO) session.getAttribute(SessionConstants.USER_SESSION);
                if (userSessionDto != null) {
                    try {
                        sessTimeOutDAO.inValidateUsersessionInfo
                                (userSessionDto.getUserProfileKey(),
                                 "L",
                                 userSessionDto.getUserSessionID());
                    }
                    catch(Exception e)
                    {
                        e.getMessage();
                    }
                }
            }
            session.invalidate();
            deleteCookies.eraseCookies(request, response);
            String path = request.getContextPath();
            response.sendRedirect(path+"/index.jsp");
        }
        catch(Exception se)
        {
            se.printStackTrace();
        }
        finally {            
            out.close();
        }
//        RequestDispatcher rd = getServletConfig().getServletContext().getRequestDispatcher("/index.jsp");
//        rd.forward(request, response);
//        return;
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
