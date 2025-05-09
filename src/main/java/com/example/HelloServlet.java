//package com.example;
//
//import java.io.IOException;
//import java.sql.*;
//import java.util.ArrayList;
//import java.util.List;
//
//import com.example.model.User;
//import com.google.gson.Gson;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.*;
//
//@WebServlet("/hello")
//public class HelloServlet extends HttpServlet {
//
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response)
//            throws IOException {
//
//        response.setContentType("application/json");
//        response.setCharacterEncoding("UTF-8");
//
//        List<User> users = new ArrayList<>();
//
//        try {
//            Connection connection = DBConnection.getConnection();
//            Statement stmt = connection.createStatement();
//            ResultSet rs = stmt.executeQuery("SELECT * FROM users");
//
//            while(rs.next()) {
//                String name = rs.getString("name");
//                String email = rs.getString("email");
//                int id = rs.getInt("id");
//
//                users.add(new User(id,name, email));
//            }
//
//            Gson gson = new Gson();
//            String json = gson.toJson(users);
//            response.getWriter().write(json);
//        } catch (SQLException e) {
//            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//            response.getWriter().write("{\"error\":\"" + e.getMessage() + "\"}");
//        }
//    }
//}