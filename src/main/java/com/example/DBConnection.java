package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    public static Connection getConnection() throws SQLException {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
            // fallback na lokalnu bazu ako radis lokalno
            String url = "jdbc:mysql://yamabiko.proxy.rlwy.net:22057/railway";
            String username = "root";
            String password = "aQDIepupKqOhOLylnvhiXGcOkCkSzlEC";
            return DriverManager.getConnection(url, username, password);
    }
}
