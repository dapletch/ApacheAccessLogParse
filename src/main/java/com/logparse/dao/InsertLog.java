package com.logparse.dao;

import com.logparse.beans.LogRecord;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.List;

/**
 * Created by Seth on 11/21/2016.
 */
public class InsertLog {

    private Logger logger = Logger.getLogger(InsertLog.class);

    private Connection connection = null;

    private PreparedStatement preparedStatement = null;

    private JDBCConnectionUtils jdbcConnectionUtils = new JDBCConnectionUtils();

    private String insertRecord = "INSERT INTO log_data (ip_address, remote_user, time_accessed, request, stat_cd, bytes_sent, time_entered)"
            + " VALUES (?, ?, ?, ?, ?, ?, ?)";

    public void writeLogToDb(List<LogRecord> logRecords) throws SQLException, ClassNotFoundException {

        if (connection == null) {
            connection = jdbcConnectionUtils.getConnection();
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

        logger.info("Records written to database.");

        preparedStatement.close();
        jdbcConnectionUtils.closeConnection();
    }
}
