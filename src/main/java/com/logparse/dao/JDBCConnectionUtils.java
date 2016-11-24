package com.logparse.dao;

import com.logparse.beans.JDBCProperties;
import com.logparse.properties.GetProperties;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by Seth on 11/23/2016.
 */
public class JDBCConnectionUtils {

    private Logger logger = Logger.getLogger(InsertLog.class);

    private GetProperties getJdbcProperties = new GetProperties();

    private JDBCProperties jdbcProperties = null;

    private Connection connection = null;

    public Connection getConnection() throws SQLException, ClassNotFoundException {
        if (jdbcProperties == null) {
            jdbcProperties = getJdbcProperties.loadJdbcProperties();
        }

        Class.forName(jdbcProperties.getClassName());
        connection = DriverManager.getConnection(jdbcProperties.getUrl(), jdbcProperties.getUsername(), jdbcProperties.getPassword());
        if (connection != null) {
            logger.info("Database connection established.");
            return connection;
        }
        logger.info("Database connection failed.");
        return null;
    }

    public void closeConnection() {
        try {
            connection.close();
            logger.info("Database connection closed.");
        } catch (SQLException e) {
            logger.error("Database connection failed to close.");
            e.printStackTrace();
        }
    }
}