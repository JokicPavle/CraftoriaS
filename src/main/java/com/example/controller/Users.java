package com.example.controller;

import com.example.dao.DAO;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/users/*")
public class Users extends HttpServlet {

    private static final long serialVersionUID = 1L;


    public Users(){
        super();
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DAO dao = new DAO();
        String path = req.getPathInfo();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();
        Gson gson = new Gson();
        Map<String, String> errors = new HashMap<>();

        try {
            if ("/getAllUsers".equals(path)) {
                out.print(gson.toJson(dao.getAllUsers()));
            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                errors.put("error", "Nepoznata ruta: " + path);
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
