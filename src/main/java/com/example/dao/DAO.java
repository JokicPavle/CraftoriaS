package com.example.dao;

import com.example.DBConnection;
import com.example.model.User;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;

public class DAO {


    private static String GET_ALL_USERS = "SELECT * FROM `users`";


    public ArrayList<User> getAllUsers() {
        Connection connection = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;

        ArrayList<User> users = new ArrayList<User>();
        User user = null;

        try {
            connection = DBConnection.getConnection();
            pstm = connection.prepareStatement(GET_ALL_USERS);

            pstm.execute();

            rs = pstm.getResultSet();

            while(rs.next()) {
                user = new User();

                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));

                users.add(user);
            }
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }
}
