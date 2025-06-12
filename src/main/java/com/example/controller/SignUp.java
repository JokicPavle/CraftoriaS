package com.example.controller;

import com.example.auth.JwtUtil;
import com.example.auth.PasswordUtil;
import com.example.dao.CraftmanDAO;
import com.example.dao.UserDAO;
import com.example.model.Craftman;
import com.example.model.User;
import com.example.validators.LoginValidation;
import com.google.gson.Gson;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;


@WebServlet("/api/signup")
public class SignUp extends HttpServlet {

    private static final long serialVersionUID = 1L;

    public SignUp(){
        super();
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserDAO userDAO = new UserDAO();
        CraftmanDAO craftmanDAO = new CraftmanDAO();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();
        Gson gson = new Gson();
        Map<String, String> errors = new HashMap<>();
        String fullName = req.getParameter("fullName");
        String email = req.getParameter("email");
        String password = req.getParameter("password");



        try {

            if (fullName == null || fullName.trim().isEmpty()) {
                errors.put("fullName", "Ime i prezime je obavezno");
            } else if (fullName.length() > 30) {
                errors.put("fullName", "Ime i prezime može imati najviše 30 karaktera");
            }
            if (email == null || email.trim().isEmpty()) {
                errors.put("email", "Email je obavezan");
            } else if (!LoginValidation.isValidEmail(email)) {
                errors.put("email", "Email nije validan");
            }
            if (password == null || password.trim().isEmpty()) {
                errors.put("password", "Lozinka je obavezna");
            } else if (!LoginValidation.isValidPassword(password)) {
                errors.put("password", "Lozinka nije validna");
            }

            String isCraftmanParam = req.getParameter("isCraftman");
            if (!"true".equalsIgnoreCase(isCraftmanParam) && !"false".equalsIgnoreCase(isCraftmanParam)) {
                errors.put("isCraftman", "Vrednost mora biti 'true' ili 'false'");
            }
            boolean isCraftman = Boolean.parseBoolean(isCraftmanParam);


            if (!errors.isEmpty()) {
                out.print(gson.toJson(errors));
                return;
            }
            String hashedPassword = PasswordUtil.hashPassword(password);
            User newUser = userDAO.insertUser(fullName,email,hashedPassword,isCraftman);
            String token = JwtUtil.generateToken(newUser.getId(),newUser.getEmail());

            if(newUser.isCraftman()) {
                Craftman newCraftman = craftmanDAO.insertCraftman(newUser.getId());
                Map<String, String> response = new HashMap<>();
                response.put("token", token);
                response.put("message", "User je uspesno dodat u bazu i ulogovan sa email: "+newUser.getEmail()+" Novi craftman je dodat u bazu sa userID: "+newCraftman.getUserId());
                out.print(gson.toJson(response));
            } else {
                Map<String, String> response = new HashMap<>();
                response.put("token", token);
                response.put("message", "User je uspesno dodat u bazu i ulogovan sa email: " + newUser.getEmail());
                out.print(gson.toJson(response));
            }

        } catch(Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            errors.put("error", "Greska na serveru "+e.getMessage());
            out.print(gson.toJson(errors));
        } finally {
            out.flush();
        }
    }
}
