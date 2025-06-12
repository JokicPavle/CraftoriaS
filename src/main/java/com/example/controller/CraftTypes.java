package com.example.controller;

import com.example.dao.CraftTypeDAO;
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

@WebServlet("/api/craft-type")
public class CraftTypes extends HttpServlet {

    private static final long serialVersionUID = 1L;


    public CraftTypes () {}


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CraftTypeDAO craftTypeDAO = new CraftTypeDAO();
//        UserDAO userDAO = new UserDAO();

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();
        Gson gson = new Gson();
        Map<String, String> errors = new HashMap<>();

        try {
            out.print(gson.toJson(craftTypeDAO.getAllCraftTypes()));
        } catch(Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            errors.put("error", "Greska na serveru "+e.getMessage());
            out.print(gson.toJson(errors));
        } finally {
            out.flush();
        }
    }




}
