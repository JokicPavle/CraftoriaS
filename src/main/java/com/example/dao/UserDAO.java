package com.example.dao;

import com.example.DBConnection;
import com.example.dto.ServiceDTO;
import com.example.dto.UserDTO;
import com.example.model.*;
import com.google.gson.Gson;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class UserDAO {

    private final String SELECT_ALL_USERS = "SELECT * FROM `users`";
    private final String INSERT_USER = "INSERT INTO `users` (`id`, `fullName`, `email`, `password`, `isCraftman`) VALUES (NULL, ? , ? , ? , ? )";
    private final String SELECT_USER_BY_EMAIL_AND_PASSWORD = "SELECT * FROM `users` WHERE `email` = ? AND `password` = ?";



//METHOD FOR INSERT USER
    public User insertUser(String fullName,  String email, String password, boolean isCraftman) {
        Connection connection = null;
        PreparedStatement pstm = null;
        ResultSet gk = null;
        User user = null;
        try {
            connection = DBConnection.getConnection();
            pstm = connection.prepareStatement(INSERT_USER, Statement.RETURN_GENERATED_KEYS);

            pstm.setString(1, fullName);
            pstm.setString(2, email);
            pstm.setString(3, password);
            pstm.setBoolean(4,isCraftman);

            pstm.executeUpdate();

            gk = pstm.getGeneratedKeys();

            if(gk.next()) {
                int newId = gk.getInt(1);
                user = new User();

                user.setId(newId);
                user.setFullName(fullName);
                user.setEmail(email);
                user.setPassword(password);
                user.setCraftman(isCraftman);

            }

            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    //METHOD FOR SELECT ALL USERS
    public ArrayList<User> selectAllUsers() {
        Connection connection = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        ArrayList<User> usersList = new ArrayList<>();
        User user = null;
        try {
            connection = DBConnection.getConnection();
            pstm = connection.prepareStatement(SELECT_USER_BY_EMAIL_AND_PASSWORD);


            pstm.execute();

            rs = pstm.getResultSet();

            if(rs.next()) {
                user = new User();

                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                usersList.add(user);
            }

            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return usersList;
    }


//METHOD FOR GET USER BY EMAIL AND PASSWORD
public User getUserByEmailAndPassword( String email, String password) {
    Connection connection = null;
    PreparedStatement pstm = null;
    ResultSet rs = null;
    User user = null;
    try {
        connection = DBConnection.getConnection();
        pstm = connection.prepareStatement(SELECT_USER_BY_EMAIL_AND_PASSWORD);

        pstm.setString(1, email);
        pstm.setString(2, password);

        pstm.execute();

        rs = pstm.executeQuery();

        if(rs.next()) {
            user = new User();
            user.setId(rs.getInt("id"));
            user.setEmail(rs.getString("email"));
            user.setPassword(rs.getString("password"));
        }

        connection.close();
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }
    return user;
}

}



