package com.example.controller;

import com.example.auth.JwtUtil;
import com.example.auth.PasswordUtil;
import com.example.dao.UserDAO;
import com.example.model.User;
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


@WebServlet("/api/login")
public class Login extends HttpServlet {



    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    }



    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserDAO dao = new UserDAO();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();
        Gson gson = new Gson();
        Map<String, String> errors = new HashMap<>();
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        try {
            User user = dao.getUserByEmail(email);
            if(user != null && PasswordUtil.checkPassword(password, user.getPassword())) {
                String token = JwtUtil.generateToken(user.getId(),user.getEmail());

                Map<String, Object> response = new HashMap<>();
                response.put("token", token);
                response.put("message", "User je uspesno ulogovan sa email: "+user.getEmail());
                response.put("user",user);
                out.print(gson.toJson(response));
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


}
