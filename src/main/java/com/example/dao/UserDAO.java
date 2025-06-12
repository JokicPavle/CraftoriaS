package com.example.dao;

import com.example.DBConnection;
import com.example.auth.PasswordUtil;
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
    private final String SELECT_USER_BY_EMAIL = "SELECT * FROM `users` WHERE `email` = ?";



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
            pstm = connection.prepareStatement("");


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
public User getUserByEmail( String email) {
    Connection connection = null;
    PreparedStatement pstm = null;
    ResultSet rs = null;
    User user = null;
    try {
        connection = DBConnection.getConnection();
        pstm = connection.prepareStatement(SELECT_USER_BY_EMAIL);

        pstm.setString(1, email);

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




public void updateUserProfile(UserDTO userDTO, List<ServiceDTO> services){
        Connection connection = null;
    PreparedStatement pstmUserUpdate = null;
    PreparedStatement pstmCraftmanUpdate = null;
    PreparedStatement pstmServiceUpdate = null;
    PreparedStatement pstmDeleteService = null;
        ResultSet rs = null;

        try {
            connection = DBConnection.getConnection();
            connection.setAutoCommit(false);

            String userUpdateSql = "UPDATE users SET fullName = ?, phone = ?, profile_image = ?, city_id = ? WHERE id = ?";
            pstmUserUpdate =  connection.prepareStatement(userUpdateSql);
            pstmUserUpdate.setString(1, userDTO.getFullName());
            pstmUserUpdate.setString(2, userDTO.getPhone());
            pstmUserUpdate.setString(3,userDTO.getProfileImage());
            pstmUserUpdate.setInt(4,userDTO.getCityId());
            pstmUserUpdate.setInt(5, userDTO.getId());
            pstmUserUpdate.executeUpdate();

            String craftmanUpdateSQL = "UPDATE craftman SET description = ?, experience = ? WHERE user_id = ?";
            pstmCraftmanUpdate = connection.prepareStatement(craftmanUpdateSQL);
            pstmCraftmanUpdate.setString(1,userDTO.getDescription());
            pstmCraftmanUpdate.setInt(2,userDTO.getExperience());
            pstmCraftmanUpdate.setInt(3,userDTO.getId());
            pstmCraftmanUpdate.executeUpdate();


            String deleteOldServicesSql = "DELETE FROM craftman_service WHERE craftman_id = ?";
            pstmDeleteService = connection.prepareStatement(deleteOldServicesSql);
            pstmDeleteService.setInt(1, userDTO.getCraftmanId());
            pstmDeleteService.executeUpdate();

            String craftmanServiceUpdateSQL = "INSERT INTO craftman_service (craftman_id, service_id) VALUES (? , ?)";
            pstmServiceUpdate = connection.prepareStatement(craftmanServiceUpdateSQL);

            for(ServiceDTO service : services) {
                pstmServiceUpdate.setInt(1, userDTO.getCraftmanId());
                pstmServiceUpdate.setInt(2,service.getId());
                pstmServiceUpdate.addBatch();
            }
            pstmServiceUpdate.executeBatch();

            connection.commit();
        } catch (SQLException e) {
            try{
                if(connection != null) connection.rollback();
            }
            catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            throw new RuntimeException(e);
        }
}

}



