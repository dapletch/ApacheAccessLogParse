package com.logparse.dao;

import com.logparse.bean.JDBCProperties;
import com.logparse.bean.LogRecord;
import com.logparse.properties.GetProperties;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.List;

/**
 * Created by Seth on 11/21/2016.
 */
public class InsertLog {

    private Logger logger = Logger.getLogger(InsertLog.class);

    private GetProperties getJdbcProperties = new GetProperties();

    private JDBCProperties jdbcProperties = null;

    private PreparedStatement preparedStatement = null;

    private Connection connection = null;

    private String insertRecord = "INSERT INTO log_data (ip_address, remote_user, time_accessed, request, stat_cd, bytes_sent, time_entered)"
            + " VALUES (?, ?, ?, ?, ?, ?, ?)";

    private Connection getConnection() throws SQLException, ClassNotFoundException {
        if (jdbcProperties == null) {
            jdbcProperties = getJdbcProperties.loadJdbcProperties();
        }

        Connection connection;
        Class.forName(jdbcProperties.getClassName());
        connection = DriverManager.getConnection(jdbcProperties.getUrl(), jdbcProperties.getUsername(), jdbcProperties.getPassword());
        if (connection != null) {
            logger.info("Database connection established.");
            return connection;
        }
        logger.info("Database connection failed.");
        return null;
    }

    private void closeConnection() {
        try {
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void writeLogToDb(List<LogRecord> logRecords) throws SQLException, ClassNotFoundException {

        if (connection == null) {
            connection = getConnection();
        }

        if (preparedStatement == null) {
            preparedStatement = connection.prepareStatement(insertRecord);
        }

        preparedStatement.clearParameters();

        for (LogRecord logRecord: logRecords) {
            preparedStatement.setString(1, logRecord.getIpAddress());
            preparedStatement.setString(2, logRecord.getRemoteUser());
            preparedStatement.setTimestamp(3, new Timestamp(logRecord.getTimeAccessed().getMillis()));
            preparedStatement.setString(4, logRecord.getRequest());
            preparedStatement.setInt(5, logRecord.getStatCode());
            preparedStatement.setInt(6, logRecord.getBytesSent());
            preparedStatement.setTimestamp(7, new Timestamp(logRecord.getTimeEntered().getMillis()));
            //logger.info(logRecord.toString());
            preparedStatement.execute();
        }

        closeConnection();
    }
}
