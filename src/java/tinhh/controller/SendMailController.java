/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tinhh.controller;

import java.io.IOException;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.Logger;
import tinhh.user.UserDAO;
import tinhh.user.UserDTO;

/**
 *
 * @author No
 */
public class SendMailController extends HttpServlet {

    private static final String HOST_NAME = "smtp.gmail.com";

    private static final int SSL_PORT = 465; // Port for SSL

    private static final String APP_EMAIL = "tinhuynhhuu250500@gmail.com"; // your email

    private static final String APP_PASSWORD = "sosadgivemealone"; // your password

    
    
    private static final String ERROR = "error.jsp";
    
    private static final String SUCCESS = "verify.jsp";
    private static final Logger LOGGER = Logger.getLogger(SendMailController.class);

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
        String url = ERROR;
        String RECEIVE_EMAIL = "";
        HttpSession ses = request.getSession();
        try {
            UserDTO dto = (UserDTO) ses.getAttribute("ACCOUNT");
            UserDAO dao = new UserDAO();
                                String newVerify=RandomStringUtils.randomAlphanumeric(6);
                    dao.updateVerify(newVerify, dto.getEmail());
                    dto.setVerifyCode(newVerify);
                    ses.setAttribute("ACCOUNT", dto);
            RECEIVE_EMAIL=dto.getEmail();
            
            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.host", HOST_NAME);
            props.put("mail.smtp.socketFactory.port", SSL_PORT);
            props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            props.put("mail.smtp.port", SSL_PORT);

            // get Session
            Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(APP_EMAIL, APP_PASSWORD);
                }
            });

            // compose message
            try {
                MimeMessage message = new MimeMessage(session);
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(RECEIVE_EMAIL));
                message.setSubject("Verify Account");
                message.setText(dto.getVerifyCode());

                // send message
                Transport.send(message);
                url=SUCCESS;
                
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
        } catch (Exception e) {
            log("Error at SendMailController: " + e.getMessage());
            LOGGER.error("Error at SendMailController: " + e.getMessage());
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
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
