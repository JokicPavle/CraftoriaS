package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch(ClassNotFoundException e) {
            throw new RuntimeException("MySQL JDBC driver nije pronađen", e);
        }

        // Railway database connection string
        String envUrl = System.getenv("MYSQL_URL");

        if (envUrl == null) {
            // fallback na lokalnu bazu ako radis lokalno
            String localUrl = "jdbc:mysql://localhost:3306/testProject";
            String username = "jokara";
            String password = "jokara";
            return DriverManager.getConnection(localUrl, username, password);
        }

        // Parsiranje Railway URL-a
        envUrl = envUrl.replace("mysql://", "");
        String[] parts = envUrl.split("@");
        String[] userPass = parts[0].split(":");
        String[] hostPortDb = parts[1].split("/");

        String[] hostPort = hostPortDb[0].split(":");

        String username = userPass[0];
        String password = userPass[1];
        String host = hostPort[0];
        String port = hostPort[1];
        String database = hostPortDb[1];

        String jdbcUrl = "jdbc:mysql://" + host + ":" + port + "/" + database + "?useSSL=false&allowPublicKeyRetrieval=true";

        return DriverManager.getConnection(jdbcUrl, username, password);
    }
}
