package com.logparse.dao;

import com.logparse.beans.ipaddresslocation.IpAddressLocation;
import com.logparse.utils.LogUtils;
import com.mysql.jdbc.log.Log;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created by Seth on 11/27/2016.
 */
public class InsertIpAddressLocation {

    private PreparedStatement preparedStatement = null;

    private JDBCConnectionUtils jdbcConnectionUtils = new JDBCConnectionUtils();

    private String insertIpAddressLoationQuery = "insert into ip_address_location (ip_address, country_code, country_name, region, region_name\n" +
            ", city, postal_code, latitude, longitude, dma_code, area_code, metro_code, date_entered) \n" +
            "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

    public void insertIpAddressLocationData(Connection connection, List<IpAddressLocation> ipAddressLocationList) throws SQLException, ClassNotFoundException {

        preparedStatement = null;

        if (connection == null) {
            connection = jdbcConnectionUtils.getConnection();
        }
        if (preparedStatement == null) {
            preparedStatement = connection.prepareStatement(insertIpAddressLoationQuery);
        }

        java.sql.Timestamp timestamp = LogUtils.getCurrentTimeStamp();

        for (IpAddressLocation ipAddressLocation : ipAddressLocationList) {
            preparedStatement.clearParameters();
            preparedStatement.setString(1 , ipAddressLocation.getIpAddress());
            preparedStatement.setString(2, ipAddressLocation.getCountryCode());
            preparedStatement.setString(3, ipAddressLocation.getCountryName());
            preparedStatement.setString(4, ipAddressLocation.getRegion());
            preparedStatement.setString(5, ipAddressLocation.getRegionName());
            preparedStatement.setString(6, ipAddressLocation.getCity());
            preparedStatement.setString(7, ipAddressLocation.getPostalCode());
            preparedStatement.setFloat(8, ipAddressLocation.getLatitude());
            preparedStatement.setFloat(9, ipAddressLocation.getLongitude());
            preparedStatement.setInt(10, ipAddressLocation.getDmaCode());
            preparedStatement.setInt(11, ipAddressLocation.getAreaCode());
            preparedStatement.setInt(12, ipAddressLocation.getMetroCode());
            preparedStatement.setTimestamp(13, timestamp);
            preparedStatement.execute();
        }

        preparedStatement.close();
    }
}
