package com.logparse.dao.ipaddresslocation;

import com.logparse.beans.ipaddresslocation.IpAddressCntByCntry;
import com.logparse.beans.ipaddresslocation.IpAddressLocation;
import com.logparse.dao.jdbc.JDBCConnectionUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Seth on 11/27/2016.
 */
public class IpAddressLocationCnts {

    private PreparedStatement preparedStatement = null;

    private ResultSet resultSet = null;

    private JDBCConnectionUtils jdbcConnectionUtils = new JDBCConnectionUtils();

    private List<IpAddressLocation> ipAddressLocationList = new ArrayList<IpAddressLocation>();

    private List<IpAddressCntByCntry> ipAddressCntByCntryList = new ArrayList<IpAddressCntByCntry>();

    private String getLastDateEnteredQuery = "select max(date_entered) from ip_address_location;";

    private String getIpAddressLocationCntsQuery = "select l.ip_address\n"
            + ", l.country_code\n"
            + ", l.country_name\n"
            + ", l.region\n"
            + ", l.region_name\n"
            + ", l.city\n"
            + ", l.postal_code\n"
            + ", l.latitude\n"
            + ", l.longitude\n"
            + ", l.dma_code\n"
            + ", l.area_code\n"
            + ", l.metro_code\n"
            + ", a.tot_ip_cnt\n"
            + "from ip_address_location l\n"
            + "inner join ip_cnt a on l.ip_address = a.ip_address\n"
            + "where l.date_entered = ?\n"
            + "order by tot_ip_cnt desc;";

    private String getIpAddressCntsByCntryQuery = "select country_name\n" +
            ", country_code\n" +
            ", region_name\n" +
            ", region\n" +
            ", city\n" +
            ", count(distinct ip_address) \n" +
            "from ip_address_location\n" +
            "where date_entered = ?\n" +
            "group by 1, 2, 3, 4, 5\n" +
            "order by count(distinct ip_address) desc\n" +
            ", country_name\n" +
            ", region\n" +
            ", city;";

    public List<IpAddressLocation> getIpAddressCnt (Connection connection) throws SQLException, ClassNotFoundException {
        return getIpAddressLocationCntList(connection, getLastDateEntered(connection));
    }

    private java.sql.Timestamp getLastDateEntered(Connection connection) throws SQLException, ClassNotFoundException {
        preparedStatement = null;
        java.sql.Timestamp lastTimeEntered = null;

        if (connection == null) {
            connection = jdbcConnectionUtils.getConnection();
        }
        if (preparedStatement == null) {
            preparedStatement = connection.prepareStatement(getLastDateEnteredQuery);
        }
        resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            lastTimeEntered = resultSet.getTimestamp(1);
        }
        preparedStatement.close();
        return lastTimeEntered;
    }

    private List<IpAddressLocation> getIpAddressLocationCntList (Connection connection, java.sql.Timestamp lastDateEntered) throws SQLException, ClassNotFoundException {

        preparedStatement = null;

        if (connection == null) {
            connection = jdbcConnectionUtils.getConnection();
        }
        if (preparedStatement == null) {
            preparedStatement = connection.prepareStatement(getIpAddressLocationCntsQuery);
        }
        preparedStatement.setTimestamp(1, lastDateEntered);
        resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            IpAddressLocation ipAddressLocation = new IpAddressLocation();
            ipAddressLocation.setIpAddress(resultSet.getString(1));
            ipAddressLocation.setCountryCode(resultSet.getString(2));
            ipAddressLocation.setCountryName(resultSet.getString(3));
            ipAddressLocation.setRegion(resultSet.getString(4));
            ipAddressLocation.setRegionName(resultSet.getString(5));
            ipAddressLocation.setCity(resultSet.getString(6));
            ipAddressLocation.setPostalCode(resultSet.getString(7));
            ipAddressLocation.setLatitude(resultSet.getFloat(8));
            ipAddressLocation.setLongitude(resultSet.getFloat(9));
            ipAddressLocation.setDmaCode(resultSet.getInt(10));
            ipAddressLocation.setAreaCode(resultSet.getInt(11));
            ipAddressLocation.setMetroCode(resultSet.getInt(12));
            ipAddressLocation.setTotalCnt(resultSet.getInt(13));
            ipAddressLocationList.add(ipAddressLocation);
        }

        preparedStatement.close();
        return ipAddressLocationList;
    }

    public List<IpAddressCntByCntry> getIpAddressCntryCnts(Connection connection) throws SQLException, ClassNotFoundException {
        return getIpAddressCntsByCntryList(connection, getLastDateEntered(connection));
    }

    private List<IpAddressCntByCntry> getIpAddressCntsByCntryList(Connection connection, java.sql.Timestamp lastDateEntered) throws SQLException, ClassNotFoundException {

        preparedStatement = null;

        if (connection == null) {
            connection = jdbcConnectionUtils.getConnection();
        }
        if (preparedStatement == null) {
            preparedStatement = connection.prepareStatement(getIpAddressCntsByCntryQuery);
        }

        preparedStatement.setTimestamp(1, lastDateEntered);
        resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            IpAddressCntByCntry ipAddressCntByCntry = new IpAddressCntByCntry();
            ipAddressCntByCntry.setCountryName(resultSet.getString(1));
            ipAddressCntByCntry.setCountryCode(resultSet.getString(2));
            ipAddressCntByCntry.setRegionName(resultSet.getString(3));
            ipAddressCntByCntry.setRegion(resultSet.getString(4));
            ipAddressCntByCntry.setCity(resultSet.getString(5));
            ipAddressCntByCntry.setDistinctIpCnt(resultSet.getInt(6));
            ipAddressCntByCntryList.add(ipAddressCntByCntry);
        }
        preparedStatement.close();
        return ipAddressCntByCntryList;
    }
}
