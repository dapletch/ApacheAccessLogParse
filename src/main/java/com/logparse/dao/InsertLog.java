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

    private PreparedStatement preparedStatement = null;

    private JDBCConnectionUtils jdbcConnectionUtils = new JDBCConnectionUtils();

    private String insertRecord = "INSERT INTO log_data (ip_address, remote_user, time_accessed, request, stat_cd, bytes_sent, time_entered)"
            + " VALUES (?, ?, ?, ?, ?, ?, ?)";

    private static final Integer batchSize = 1000;
    private Integer count = 0;

    public void writeLogToDb(Connection connection, List<LogRecord> logRecords) throws SQLException, ClassNotFoundException {

        if (connection == null) {
            connection = jdbcConnectionUtils.getConnection();
        }
        if (preparedStatement == null) {
            preparedStatement = connection.prepareStatement(insertRecord);
        }

        for (LogRecord logRecord: logRecords) {
            // Set auto commit to false to allow the batch statement to work properly
            connection.setAutoCommit(false);
            preparedStatement.clearParameters();
            preparedStatement.setString(1, logRecord.getIpAddress());
            preparedStatement.setString(2, logRecord.getRemoteUser());
            preparedStatement.setTimestamp(3, new Timestamp(logRecord.getTimeAccessed().getMillis()));
            preparedStatement.setString(4, logRecord.getRequest());
            preparedStatement.setInt(5, logRecord.getStatCode());
            preparedStatement.setInt(6, logRecord.getBytesSent());
            preparedStatement.setTimestamp(7, new Timestamp(logRecord.getTimeEntered().getMillis()));
            //logger.info(logRecord.toString());
            preparedStatement.addBatch();

            if (++count % batchSize == 0) {
                preparedStatement.executeBatch();
                connection.setAutoCommit(true);
            }
        }

        if (!connection.getAutoCommit()) {
            // Set auto commit back to true to submit the rest of the remaining records
            connection.setAutoCommit(true);
        }

        // Insert the remaining records
        preparedStatement.executeBatch();
        logger.info("Records written to database.");
        preparedStatement.close();
    }
}
