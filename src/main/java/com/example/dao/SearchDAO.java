package com.example.dao;

import com.example.DBConnection;
import com.example.dto.ServiceDTO;
import com.example.dto.UserDTO;
import com.example.model.Comment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class SearchDAO {

    //METHOD FOR GET CRAFTMANS BY SEARCH VALUES
    public ArrayList<UserDTO> searchCraftmans(String fullName, String city, String description, String serviceName, String craftTypeName) {
        // Priprema parametara za LIKE '%value%'
        if (fullName == null || fullName.trim().isEmpty()) fullName = "%";
        else fullName = "%" + fullName.trim() + "%";

        if (city == null || city.trim().isEmpty()) city = "%";
        else city = "%" + city.trim() + "%";

        if (description == null || description.trim().isEmpty()) description = "%";
        else description = "%" + description.trim() + "%";

        if (serviceName == null || serviceName.trim().isEmpty()) serviceName = "%";
        else serviceName = "%" + serviceName.trim() + "%";

        if (craftTypeName == null || craftTypeName.trim().isEmpty()) craftTypeName = "%";
        else craftTypeName = "%" + craftTypeName.trim() + "%";


        String baseQuery = "SELECT \n" +
                "u.id AS user_id,\n" +
                "u.fullName,\n" +
                "u.username,\n" +
                "u.email,\n" +
                "cities.city,\n" +
                "u.phone,\n" +
                "u.profile_image,\n" +
                "u.isCraftman,\n" +
                "c.id AS craftman_id,\n" +
                "c.description,\n" +
                "c.experience,\n" +
                "i.image_url,\n" +
                "s.id AS service_id,\n" +
                "s.name AS service_name,\n" +
                "ct.id AS craft_type_id,\n" +
                "ct.name AS craft_type_name,\n" +
                "cm.id AS comment_id,\n" +
                "cm.comment_text,\n" +
                "cm.rating,\n" +
                "cm.to_user_id AS comment_user_id,\n" +
                "rating_avg.avg_rating \n" +
                "FROM craftman c\n" +
                "JOIN users u ON c.user_id = u.id\n" +
                "LEFT JOIN images i ON i.craftman_id = c.id\n" +
                "LEFT JOIN cities cities ON cities.id = u.city_id\n" +
                "LEFT JOIN craftman_service cs ON cs.craftman_id = c.id\n" +
                "LEFT JOIN services s ON cs.service_id = s.id\n" +
                "LEFT JOIN craft_type ct ON s.craft_type_id = ct.id\n" +
                "LEFT JOIN comments cm ON cm.to_user_id = u.id\n" +
                "LEFT JOIN (\n" +
                "    SELECT to_user_id, AVG(rating) AS avg_rating\n" +
                "    FROM comments \n" +
                "    GROUP BY to_user_id\n" +
                ") AS rating_avg ON rating_avg.to_user_id = u.id\n" +
                "WHERE u.fullName LIKE ?\n" +
                "  AND (cities.city LIKE ? OR cities.city IS NULL)\n" +
                "  AND (c.description LIKE ? OR c.description IS NULL)\n" +
                "  AND (s.name LIKE ? OR s.name IS NULL)\n" +
                "  AND (ct.name LIKE ? OR ct.name IS NULL)";

        Connection connection = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        Map<Integer, UserDTO> listCraftmans = new LinkedHashMap<>();


        try {
            connection = DBConnection.getConnection();
            pstm = connection.prepareStatement(baseQuery);
            pstm.setString(1, fullName);
            pstm.setString(2,city);
            pstm.setString(3,description);
            pstm.setString(4,serviceName);
            pstm.setString(5,craftTypeName);


            pstm.execute();

            rs = pstm.getResultSet();

            while(rs.next()) {
                int userId = rs.getInt("user_id");
                UserDTO user = listCraftmans.get(userId);

                if (user == null) {
                    user = new UserDTO();

                    user.setId(userId);
                    user.setFullName(rs.getString("fullName"));
                    user.setUsername(rs.getString("username"));
                    user.setEmail(rs.getString("email"));
                    user.setCityName(rs.getString("city"));
                    user.setPhone(rs.getString("phone"));
                    user.setProfileImage(rs.getString("profile_image"));
                    user.setDescription(rs.getString("description"));
                    user.setExperience(rs.getInt("experience"));
                    user.setCraftman(rs.getBoolean("isCraftman"));

                    double avgRating = rs.getDouble("avg_rating");
                    if(!rs.wasNull()) {
                        user.setAvgRating(avgRating);
                    }

                    listCraftmans.put(userId, user);
                }

                int commentId = rs.getInt("comment_id");
                if(!rs.wasNull()) {
                    Comment comment = new Comment();
                    comment.setId(commentId);
                    comment.setComment(rs.getString("comment_text"));
                    comment.setUserRating(rs.getInt("rating"));
                    comment.setUserId(rs.getInt("comment_user_id"));
                    if(!user.getComments().contains(comment)) {
                        user.getComments().add(comment);
                    }
                }

                String imageUrl = rs.getString("image_url");
                if(imageUrl != null && !user.getImages().contains(imageUrl)) {
                    user.getImages().add(imageUrl);
                }

                int serviceId = rs.getInt("service_id");
                if (!rs.wasNull()) {
                    ServiceDTO service = new ServiceDTO();
                    service.setId(serviceId);
                    service.setServiceName(rs.getString("service_name"));
                    service.setCraftTypeId(rs.getInt("craft_type_id"));
                    if (!user.getServices().contains(service)) {
                        user.getServices().add(service);
                    }
                }

            }

        } catch(Exception e) {
            e.printStackTrace();
        }

        return new ArrayList<>(listCraftmans.values());
    }
}
