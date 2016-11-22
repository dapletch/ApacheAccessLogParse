package com.logparse.dao;

import com.logparse.bean.LogRecord;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Seth on 11/21/2016.
 */
public class InsertLog {

    private Logger logger = Logger.getLogger(InsertLog.class);

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

    private void closeConnection(Connection connection) throws SQLException {
        if (connection != null) {
            connection.close();
            logger.info("Database connection closed successfully");
        }
    }

    public void writeLogToDb(List<LogRecord> logRecords) {

    }
}
