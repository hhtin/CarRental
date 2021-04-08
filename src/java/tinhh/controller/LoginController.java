/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tinhh.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import tinhh.user.UserDAO;
import tinhh.user.UserDTO;
import tinhh.user.UserErrorObj;
import tinhh.utils.VerifyRecaptcha;

/**
 *
 * @author No
 */
public class LoginController extends HttpServlet {

    private static final String ERROR = "error.jsp";
    private static final String STUDENT = "member.jsp";
    private static final String ADMIN = "admin.jsp";
    private static final String LOGIN = "login.jsp";
    private static final String VERIFY = "SendMailController";
    private static final String INVALID = "login.jsp";
    private static final Logger LOGGER = Logger.getLogger(LoginController.class);

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
        HttpSession session = request.getSession();
        try {

            String email = request.getParameter("txtEmail");
            String password = request.getParameter("txtPassword");

            String gRecaptchaResponse = request
                        .getParameter("g-recaptcha-response");
            boolean verify = VerifyRecaptcha.verify(gRecaptchaResponse);
            boolean valid = true;
            UserErrorObj loginErrorObject = new UserErrorObj();
            if (email.length() == 0) {
                loginErrorObject.setUsernameError("Email can't be blank");
                valid = false;
            }
            if (password.length() == 0) {
                loginErrorObject.setPasswordError("Password can't be blank");
                valid = false;
            }
            if(!verify){
                loginErrorObject.setCatchaError("Captcha need to click");
                valid = false;
            }
            if (valid == true) {
                UserDAO dao = new UserDAO();
                UserDTO dto = new UserDTO();
                dto = dao.checkLogin(email, password);
                if (dto.getRole().equals("invalid")) {
                    url = INVALID;
                    loginErrorObject.setUsernameError("Email or password is incorrect");
                    loginErrorObject.setPasswordError("");
                    request.setAttribute("INVALID", loginErrorObject);
                    url = INVALID;
                } else if (dto.getRole().equals("member") && dto.isStatus()) {
                    session.setAttribute("ACCOUNT", dto);
                    url = STUDENT;
                } else if (dto.getRole().equals("admin") && dto.isStatus()) {
                    session.setAttribute("ACCOUNT", dto);
                    url = ADMIN;
                } else if((dto.getRole().equals("member") ||dto.getRole().equals("admin")) && !dto.isStatus()){
                    session.setAttribute("ACCOUNT", dto);
                    url = VERIFY;         
                } else {
                    url=LOGIN;
                }
            } else {
                request.setAttribute("INVALID", loginErrorObject);
                url = INVALID;
            }

        } catch (Exception e) {
            log("Error at LoginController: " + e.getMessage());
            LOGGER.error("Error at LoginController: " + e.getMessage());
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
