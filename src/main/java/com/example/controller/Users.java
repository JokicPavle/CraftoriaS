package com.example.controller;

import com.example.dao.UserDAO;
import com.example.model.User;
import com.example.validators.LoginValidation;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.xml.validation.Validator;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/users")
public class Users extends HttpServlet {

    private static final long serialVersionUID = 1L;



    public Users(){
        super();
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserDAO dao = new UserDAO();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();
        Gson gson = new Gson();
        Map<String, String> errors = new HashMap<>();
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        try {
                User user = dao.getUserByEmailAndPassword(email,password);
              if(user != null) {
                  int userId = user.getId();
                  out.print(gson.toJson("User sa Id-em: "+userId+ "i email:"+user.getEmail()+" je uspesno logovan!"));
              } else {
                  errors.put("Login", "User sa ovim email i password ne postoji!");
                  out.print(gson.toJson(errors));
              }
        } catch(Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            errors.put("error", "Greska na serveru "+e.getMessage());
            out.print(gson.toJson(errors));
        } finally {
            out.flush();
        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserDAO dao = new UserDAO();
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
            User newUser = dao.insertUser(fullName,email,password,isCraftman);

            out.print(gson.toJson("Novi korisnik je uspešno dodat: " + newUser.getFullName()));

        } catch(Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            errors.put("error", "Greska na serveru "+e.getMessage());
            out.print(gson.toJson(errors));
        } finally {
            out.flush();
        }
    }
}

