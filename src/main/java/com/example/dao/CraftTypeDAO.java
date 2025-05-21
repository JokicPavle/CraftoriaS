package com.example.dao;

import com.example.DBConnection;
import com.example.model.CraftType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CraftTypeDAO {

    private final String GET_ALL_CRAFT_TYPES = "SELECT * FROM `craft_type`";


    public List<CraftType> getAllCraftTypes() throws SQLException {
        Connection connection = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        List<CraftType> craftTypesList = new ArrayList<>();
        CraftType craftType = null;
        int id = 0;
        try {
            connection = DBConnection.getConnection();
            pstm = connection.prepareStatement(GET_ALL_CRAFT_TYPES);

            pstm.execute();
            rs = pstm.getResultSet();

            while(rs.next()) {
                craftType = new CraftType();
                craftType.setId(rs.getInt("id"));
                craftType.setCraftName(rs.getString("name"));
                craftTypesList.add(craftType);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return craftTypesList;
    }


}
