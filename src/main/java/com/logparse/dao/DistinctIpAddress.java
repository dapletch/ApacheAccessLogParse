package com.logparse.dao;

import com.logparse.beans.IpAddress;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Seth on 11/27/2016.
 */
public class DistinctIpAddress {

    private List<IpAddress> distinctIpAddressList = new ArrayList<IpAddress>();

    private PreparedStatement preparedStatement = null;

    private ResultSet resultSet = null;

    private JDBCConnectionUtils jdbcConnectionUtils = new JDBCConnectionUtils();

    private String distinctIpAddressQuery = "select ip_address from ip_cnt;";

    public List<IpAddress> distinctIpAddresses(Connection connection) throws SQLException, ClassNotFoundException {

        preparedStatement = null;

        if (connection == null) {
            connection = jdbcConnectionUtils.getConnection();
        }
        if (preparedStatement == null) {
            preparedStatement = connection.prepareStatement(distinctIpAddressQuery);
        }

        resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            IpAddress ipAddress = new IpAddress(resultSet.getString(1));
            distinctIpAddressList.add(ipAddress);
        }
        return distinctIpAddressList;
    }
}
