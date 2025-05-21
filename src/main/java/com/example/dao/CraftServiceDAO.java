package com.example.dao;

import com.example.DBConnection;
import com.example.dto.ServiceDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CraftServiceDAO {

    private final String GET_ALL_SERVICES = "SELECT * FROM `services`";
    private final String GET_SERVICES_BY_CRAFT_TYPE_ID = "SELECT * FROM `services` WHERE `craft_type_id` = ?";


    public List<ServiceDTO> getAllServices() throws SQLException {
        Connection connection = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        List<ServiceDTO> serviceList = new ArrayList<>();
        ServiceDTO service = null;
        try {
            connection = DBConnection.getConnection();
            pstm = connection.prepareStatement(GET_ALL_SERVICES);

            pstm.execute();
            rs = pstm.getResultSet();

            while (rs.next()) {
                service = new ServiceDTO();
                service.setId(rs.getInt("id"));
                service.setServiceName(rs.getString("name"));
                service.setCraftTypeId(rs.getInt("craft_type_id"));
                serviceList.add(service);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return serviceList;
    }


    public List<ServiceDTO> getServicesByCraftTypeId(int craftTypeId) throws SQLException {
        Connection connection = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        List<ServiceDTO> serviceList = new ArrayList<>();
        ServiceDTO service = null;
        try {
            connection = DBConnection.getConnection();
            pstm = connection.prepareStatement(GET_SERVICES_BY_CRAFT_TYPE_ID);
                pstm.setInt(1, craftTypeId);

                pstm.execute();
                rs = pstm.getResultSet();

                while (rs.next()) {
                    service = new ServiceDTO();
                    service.setId(rs.getInt("id"));
                    service.setServiceName(rs.getString("name"));
                    service.setCraftTypeId(rs.getInt("craft_type_id"));
                    serviceList.add(service);
                }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return serviceList;
    }
}
