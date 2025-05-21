package com.example.controller;

import com.example.dao.CraftServiceDAO;
import com.example.dao.CraftTypeDAO;
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

@WebServlet("/services")
public class CraftServices extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String craftTypeIdParam = req.getParameter("craftTypeId");
        Integer craftTypeId = null;

        CraftServiceDAO craftServiceDAO = new CraftServiceDAO();
//        UserDAO userDAO = new UserDAO();

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();
        Gson gson = new Gson();
        Map<String, String> errors = new HashMap<>();
        try {

        if (craftTypeIdParam != null && !craftTypeIdParam.isEmpty()) {
            try {
                craftTypeId = Integer.parseInt(craftTypeIdParam);
            } catch (NumberFormatException e) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                errors.put("error", "Nevalidan craftTypeId parametar.");
                out.print(gson.toJson(errors));
                out.flush();
                return;
            }
        }

            if(craftTypeId == null || craftTypeId == 0) {
                out.print(gson.toJson(craftServiceDAO.getAllServices()));
            } else {
                out.print(gson.toJson(craftServiceDAO.getServicesByCraftTypeId(craftTypeId)));
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
