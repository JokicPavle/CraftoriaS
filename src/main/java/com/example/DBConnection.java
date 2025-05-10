package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch(ClassNotFoundException e) {
            throw new RuntimeException("MySQL JDBC driver nije pronadjen",e);
        }
        String url = "jdbc:mysql://host.docker.internal:3306/testProject";
        String username = "jokara";
        String password = "jokara";

        return DriverManager.getConnection(url,username,password);
    }
}
