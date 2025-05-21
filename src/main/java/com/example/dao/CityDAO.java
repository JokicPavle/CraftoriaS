package com.example.dao;

import com.example.DBConnection;
import com.example.model.City;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CityDAO {

    private final String GET_ALL_CITIES = "SELECT * FROM `cities`";


    public List<City> getAllCities() throws SQLException {
        Connection connection = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        List<City> citiesList = new ArrayList<>();
        City city = null;
        int id = 0;
        try {
            connection = DBConnection.getConnection();
            pstm = connection.prepareStatement(GET_ALL_CITIES);

            pstm.execute();
            rs = pstm.getResultSet();

            while(rs.next()) {
                city = new City();
                city.setId(rs.getInt("id"));
                city.setCity(rs.getString("city"));
                citiesList.add(city);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return citiesList;
    }


}
