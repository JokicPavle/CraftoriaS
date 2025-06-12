package com.example.controller;

import com.example.dao.UserDAO;
import com.example.dto.ServiceDTO;
import com.example.dto.UserDTO;
import com.example.dto.UserWithServicesDTO;
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
import java.util.List;
import java.util.Map;

@WebServlet("/api/users")
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
                User user = dao.getUserByEmail(email);
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
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        PrintWriter out = resp.getWriter();
        Gson gson = new Gson();
        Map<String, String> errors = new HashMap<>();
        UserDAO userDAO = new UserDAO();


        try {
            // Učitavanje JSON tela
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = req.getReader().readLine()) != null) {
                sb.append(line);
            }

            String jsonInput = sb.toString();

            UserWithServicesDTO input = gson.fromJson(jsonInput,UserWithServicesDTO.class);

            UserDTO userDTO = input.getUser();
            List<ServiceDTO> selectedServices = input.getServices();

            userDAO.updateUserProfile(userDTO,selectedServices);

            out.print(gson.toJson("Uspešno ažuriran korisnik!"));
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            errors.put("error", "Greška: " + e.getMessage());
            out.print(gson.toJson(errors));
        } finally {
            out.flush();
        }
    }

}

