package com.example.dao;

import com.example.DBConnection;
import com.example.dto.ServiceDTO;
import com.example.dto.UserDTO;
import com.example.model.Comment;
import com.example.model.Craftman;
import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class CraftmanDAO {


    private final String INSERT_CRAFTMAN = "INSERT INTO `craftman` (`id`, `user_id`) VALUES (NULL, ?)";
    //    METHOD FOR GET ALL CRAFTMANS
    public ArrayList<UserDTO> getAllCraftmans() {
        String baseQuery = "SELECT \n" +
                "u.id AS user_id,\n" +
                "u.fullName,\n" +
                "u.username,\n" +
                "u.email,\n" +
                "cities.city,\n" +
                "cities.id as city_id,\n"+
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
                "LEFT JOIN comments cm ON cm.to_user_id = u.id \n" +
                "LEFT JOIN (SELECT to_user_id, AVG(rating) AS avg_rating \n" +
                "FROM comments GROUP BY to_user_id) AS rating_avg \n "+
                "ON rating_avg.to_user_id = u.id";

        Connection connection = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        Map<Integer, UserDTO> usersMap = new LinkedHashMap<>();

        try {
            connection = DBConnection.getConnection();
            pstm = connection.prepareStatement(baseQuery);

            pstm.execute();

            rs = pstm.getResultSet();

            while(rs.next()) {
                int userId = rs.getInt("user_id");
                UserDTO user = usersMap.get(userId);

                if(user == null) {
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
                    user.setCraftmanId(rs.getInt("craftman_id"));
                    user.setCityId(rs.getInt("city_id"));


                    double avgRating = rs.getDouble("avg_rating");
                    if(!rs.wasNull()) {
                        user.setAvgRating(avgRating);
                    }

                    usersMap.put(userId, user);
                }




                // Dodaj komentar ako postoji i nije null
                int commentId = rs.getInt("comment_id");
                if (!rs.wasNull()) {
                    Comment comment = new Comment();
                    comment.setId(commentId);
                    comment.setComment(rs.getString("comment_text"));
                    comment.setUserRating(rs.getInt("rating"));
                    comment.setUserId(rs.getInt("comment_user_id"));

                    if (!user.getComments().contains(comment)) {
                        user.getComments().add(comment);
                    }
                }
                // Dodaj sliku ako postoji i nije null
                String imageUrl = rs.getString("image_url");
                if (imageUrl != null && !user.getImages().contains(imageUrl)) {
                    user.getImages().add(imageUrl);
                }

                // Dodaj servis ako postoji i nije null
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
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return new ArrayList<>(usersMap.values());
    }

    //METHOD FOR GET CRAFTMAN BY ID

    public UserDTO getCraftmanById(int id) {
        String baseQuery = "SELECT \n" +
                "u.id AS user_id,\n" +
                "u.fullName,\n" +
                "u.username,\n" +
                "u.email,\n" +
                "cities.city,\n" +
                "cities.id as city_id,\n"+
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
                "LEFT JOIN comments cm ON cm.to_user_id = u.id \n" +
                "LEFT JOIN (SELECT to_user_id, AVG(rating) AS avg_rating \n" +
                "FROM comments GROUP BY to_user_id) AS rating_avg \n "+
                "ON rating_avg.to_user_id = u.id WHERE c.id = ?";


        Connection connection = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        UserDTO user = null;

        try {
            connection = DBConnection.getConnection();
            pstm = connection.prepareStatement(baseQuery);

            pstm.setInt(1,id);
            pstm.execute();

            rs = pstm.getResultSet();

            while(rs.next()) {
                if(user == null) {
                    user = new UserDTO();

                    user.setId(rs.getInt("user_id"));
                    user.setFullName(rs.getString("fullName"));
                    user.setUsername(rs.getString("username"));
                    user.setEmail(rs.getString("email"));
                    user.setCityName(rs.getString("city"));
                    user.setPhone(rs.getString("phone"));
                    user.setProfileImage(rs.getString("profile_image"));
                    user.setDescription(rs.getString("description"));
                    user.setExperience(rs.getInt("experience"));
                    user.setCraftman(rs.getBoolean("isCraftman"));
                    user.setCraftmanId(id);
                    user.setCityId(rs.getInt("city_id"));

                    double avgRating = rs.getDouble("avg_rating");
                    if(!rs.wasNull()) {
                        user.setAvgRating(avgRating);
                    }
                }

                // Dodaj komentar ako postoji i nije null
                int commentId = rs.getInt("comment_id");
                if (!rs.wasNull()) {
                    Comment comment = new Comment();
                    comment.setId(commentId);
                    comment.setComment(rs.getString("comment_text"));
                    comment.setUserRating(rs.getInt("rating"));
                    comment.setUserId(rs.getInt("comment_user_id"));

                    if (!user.getComments().contains(comment)) {
                        user.getComments().add(comment);
                    }
                }
                // Dodaj sliku ako postoji i nije null
                String imageUrl = rs.getString("image_url");
                if (imageUrl != null && !user.getImages().contains(imageUrl)) {
                    user.getImages().add(imageUrl);
                }

                // Dodaj servis ako postoji i nije null
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
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }


    //    METHOD FOR GET ALL CRAFTMANS ORDER BY RATING
    public ArrayList<UserDTO> getAllCraftmansOrderByRating(String orderDir) {
        String baseQuery = "SELECT \n" +
                "    u.id AS user_id,\n" +
                "    u.fullName,\n" +
                "    u.username,\n" +
                "    u.email,\n" +
                "    cities.city,\n" +
                "    u.phone,\n" +
                "u.profile_image,\n" +
                "    u.isCraftman,\n" +
                "    c.id AS craftman_id,\n" +
                "    c.description,\n" +
                "    c.experience,\n" +
                "    i.image_url,\n" +
                "    s.id AS service_id,\n" +
                "    s.name AS service_name,\n" +
                "    ct.id AS craft_type_id,\n" +
                "    ct.name AS craft_type_name,\n" +
                "    cm.id AS comment_id,\n" +
                "    cm.comment_text,\n" +
                "    cm.rating,\n" +
                "    cm.to_user_id AS comment_user_id, \n" +
                "    rating_avg.avg_rating \n" +
                "FROM craftman c\n" +
                "JOIN users u ON c.user_id = u.id\n" +
                "LEFT JOIN images i ON i.craftman_id = c.id\n" +
                "LEFT JOIN cities cities ON cities.id = u.city_id\n" +
                "LEFT JOIN craftman_service cs ON cs.craftman_id = c.id\n" +
                "LEFT JOIN services s ON cs.service_id = s.id\n" +
                "LEFT JOIN craft_type ct ON s.craft_type_id = ct.id\n" +
                "LEFT JOIN comments cm ON cm.to_user_id = u.id \n" +
                "LEFT JOIN (\n" +
                "    SELECT to_user_id, AVG(rating) AS avg_rating \n" +
                "    FROM comments \n" +
                "    GROUP BY to_user_id\n" +
                ") AS rating_avg ON rating_avg.to_user_id = u.id";

        if ("ASC".equalsIgnoreCase(orderDir)) {
            baseQuery += " ORDER BY rating_avg.avg_rating ASC";
        } else if ("DESC".equalsIgnoreCase(orderDir)) {
            baseQuery += " ORDER BY rating_avg.avg_rating DESC";
        }
        Connection connection = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        Map<Integer, UserDTO> usersMap = new LinkedHashMap<>();

        try {
            connection = DBConnection.getConnection();
            pstm = connection.prepareStatement(baseQuery);

            pstm.execute();

            rs = pstm.getResultSet();

            while(rs.next()) {
                int userId = rs.getInt("user_id");
                UserDTO user = usersMap.get(userId);

                if(user == null) {
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


                    usersMap.put(userId, user);
                }


                // Dodaj komentar ako postoji i nije null
                int commentId = rs.getInt("comment_id");
                if (!rs.wasNull()) {
                    Comment comment = new Comment();
                    comment.setId(commentId);
                    comment.setComment(rs.getString("comment_text"));
                    comment.setUserRating(rs.getInt("rating"));
                    comment.setUserId(rs.getInt("comment_user_id"));
                    if (!user.getComments().contains(comment)) {
                        user.getComments().add(comment);
                    }
                }
                // Dodaj sliku ako postoji i nije null
                String imageUrl = rs.getString("image_url");
                if(imageUrl != null && !user.getImages().contains(imageUrl)) {
                    user.getImages().add(imageUrl);
                }


                // Dodaj servis ako postoji i nije null
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
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return new ArrayList<>(usersMap.values());
    }



    //    METHOD FOR GET ALL CRAFTMANS ORDER BY RATING
    public ArrayList<UserDTO> getAllCraftmansOrderByComments(String orderDir) {
        String baseQuery = "SELECT \n" +
                "    u.id AS user_id,\n" +
                "    u.fullName,\n" +
                "    u.username,\n" +
                "    u.email,\n" +
                "    cities.city,\n" +
                "    u.phone,\n" +
                "u.profile_image,\n" +
                "    u.isCraftman,\n" +
                "    c.id AS craftman_id,\n" +
                "    c.description,\n" +
                "    c.experience,\n" +
                "    i.image_url,\n" +
                "    s.id AS service_id,\n" +
                "    s.name AS service_name,\n" +
                "    ct.id AS craft_type_id,\n" +
                "    ct.name AS craft_type_name,\n" +
                "    cm.id AS comment_id,\n" +
                "    cm.comment_text,\n" +
                "    cm.rating,\n" +
                "    cm.to_user_id AS comment_user_id,\n" +
                "    rating_avg.avg_rating,\n" +
                "    comment_count.num_comments\n" +
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
                "    FROM comments\n" +
                "    GROUP BY to_user_id\n" +
                ") AS rating_avg ON rating_avg.to_user_id = u.id\n" +
                "LEFT JOIN (\n" +
                "    SELECT to_user_id, COUNT(*) AS num_comments\n" +
                "    FROM comments\n" +
                "    GROUP BY to_user_id\n" +
                ") AS comment_count ON comment_count.to_user_id = u.id";

        if ("ASC".equalsIgnoreCase(orderDir)) {
            baseQuery += " ORDER BY comment_count.num_comments ASC";
        } else if ("DESC".equalsIgnoreCase(orderDir)) {
            baseQuery += " ORDER BY comment_count.num_comments DESC";
        }
        Connection connection = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        Map<Integer, UserDTO> usersMap = new LinkedHashMap<>();

        try {
            connection = DBConnection.getConnection();
            pstm = connection.prepareStatement(baseQuery);

            pstm.execute();

            rs = pstm.getResultSet();

            while(rs.next()) {
                int userId = rs.getInt("user_id");
                UserDTO user = usersMap.get(userId);

                if(user == null) {
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


                    usersMap.put(userId, user);
                }


                // Dodaj komentar ako postoji i nije null
                int commentId = rs.getInt("comment_id");
                if (!rs.wasNull()) {
                    Comment comment = new Comment();
                    comment.setId(commentId);
                    comment.setComment(rs.getString("comment_text"));
                    comment.setUserRating(rs.getInt("rating"));
                    comment.setUserId(rs.getInt("comment_user_id"));
                    if (!user.getComments().contains(comment)) {
                        user.getComments().add(comment);
                    }
                }
                // Dodaj sliku ako postoji i nije null
                String imageUrl = rs.getString("image_url");
                if (imageUrl != null && !user.getImages().contains(imageUrl)) {
                    user.getImages().add(imageUrl);
                }

                // Dodaj servis ako postoji i nije null
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
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return new ArrayList<>(usersMap.values());
    }

    //METHOD FOR INSERT CRAFTMAN
    public Craftman insertCraftman(int userId) {
        Connection connection = null;
        PreparedStatement pstm = null;
        ResultSet gk = null;
        Craftman craftman = null;
        try {
            connection = DBConnection.getConnection();
            pstm = connection.prepareStatement(INSERT_CRAFTMAN,Statement.RETURN_GENERATED_KEYS);

            pstm.setInt(1, userId);
            pstm.executeUpdate();

            gk = pstm.getGeneratedKeys();
            if(gk.next()) {
                int newId = gk.getInt(1);
                craftman = new Craftman();

                craftman.setId(newId);
                craftman.setUserId(userId);
            }

            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return craftman;
    }


}

