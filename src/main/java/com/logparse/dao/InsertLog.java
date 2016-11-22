package com.logparse.dao;

import com.logparse.bean.LogRecord;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.List;

/**
 * Created by Seth on 11/21/2016.
 */
public class InsertLog {

    private Logger logger = Logger.getLogger(InsertLog.class);

    private String insertRecord = "INSERT INTO log_data (ip_address, remote_user, time_accessed, request, stat_cd, bytes_sent, time_entered)"
            + " VALUES (?, ?, ?, ?, ?, ?, ?)";

    private Connection getConnection() throws SQLException, ClassNotFoundException {
        Connection connection;
        Class.forName("com.mysql.jdbc.Driver");
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/apache_log_parse", "root", "");
        if (connection != null) {
            logger.info("Database connection established.");
            return connection;
        }
        logger.info("Database connection failed.");
        return null;
    }

    private void closeConnection(Connection connection, PreparedStatement ps) throws SQLException {
        if (ps != null) {
            ps.close();
            logger.info("Prepared Statement closed successfully");
        }
        if (connection != null) {
            connection.close();
            logger.info("Database connection closed successfully");
        }
    }

    public void writeLogToDb(List<LogRecord> logRecords) throws SQLException, ClassNotFoundException {

        Connection connection = getConnection();
        PreparedStatement ps;

        ps = connection.prepareStatement(insertRecord);

        for (LogRecord logRecord: logRecords) {
            ps.setString(1, logRecord.getIpAddress());
            ps.setString(2, logRecord.getRemoteUser());
            ps.setTimestamp(3, new Timestamp(logRecord.getTimeAccessed().getMillis()));
            ps.setString(4, logRecord.getRequest());
            ps.setInt(5, logRecord.getStatCode());
            ps.setInt(6, logRecord.getBytesSent());
            ps.setTimestamp(7, new Timestamp(logRecord.getTimeEntered().getMillis()));
            //logger.info(logRecord.toString());
            ps.executeUpdate();
        }

        closeConnection(connection, ps);
    }
}
