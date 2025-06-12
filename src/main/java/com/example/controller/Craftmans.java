package com.example.controller;

import com.example.dao.CraftmanDAO;
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

@WebServlet("/api/craftmans/*")
public class Craftmans extends HttpServlet {

    private static final long serialVersionUID = 1L;


    public Craftmans(){
        super();
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CraftmanDAO craftmanDAO = new CraftmanDAO();
//        UserDAO userDAO = new UserDAO();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        String pathInfo = req.getPathInfo();
        PrintWriter out = resp.getWriter();
        Gson gson = new Gson();
        Map<String, String> errors = new HashMap<>();

        try {
        if(pathInfo == null || pathInfo.equals("/")) {
            out.print(gson.toJson(craftmanDAO.getAllCraftmans()));
        } else {
            String idStr = pathInfo.substring(1);
            int id = Integer.parseInt(idStr);
            Object craftman = craftmanDAO.getCraftmanById(id);
            if (craftman != null) {
                out.print(gson.toJson(craftman));
            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                errors.put("error", "Craftman sa id " + id + " nije pronađen.");
                out.print(gson.toJson(errors));
            }
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
        CraftmanDAO craftmanDAO = new CraftmanDAO();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();
        Gson gson = new Gson();
        Map<String, String> errors = new HashMap<>();

        try {

        } catch(Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            errors.put("error", "Greska na serveru "+e.getMessage());
            out.print(gson.toJson(errors));
        } finally {
            out.flush();
        }
    }
}
