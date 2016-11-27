package com.logparse.dao;

import com.logparse.utils.LogUtils;
import com.logparse.utils.OSUtils;

import javax.naming.CompositeName;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Seth on 11/26/2016.
 */
public class BackUpDeleteReportData {

    private PreparedStatement preparedStatement = null;

    private JDBCConnectionUtils jdbcConnectionUtils = new JDBCConnectionUtils();

    private ResultSet resultSet = null;

    private String logDataFile = OSUtils.getFilePathAboveCurrentOne() + "log_data" + LogUtils.generateFileTimeStamp() + ".txt";
    private String timeAccessedFile = OSUtils.getFilePathAboveCurrentOne() + "time_accessed" + LogUtils.generateFileTimeStamp() + ".txt";

    public BackUpDeleteReportData () {
        super();
    }

    private String backUpLogDataTableQuery = "SELECT * FROM log_data\n;";

    private String truncateLogDataQuery = "TRUNCATE log_data;";

    private String backUpTimeAccessedDayCntTableQuery = "SELECT * FROM time_accessed_day_cnt;";

    private String truncateTimeAccessedDayQuery = "TRUNCATE time_accessed_day_cnt;";

    private void backUpLogDataTable(Connection connection) throws SQLException, ClassNotFoundException, IOException {

        File logDataBackUpFile = new File(logDataFile);

        // if file doesnt exists, then create it
        if (!logDataBackUpFile.exists()) {
            logDataBackUpFile.createNewFile();
        }
        FileWriter fw = new FileWriter(logDataBackUpFile.getAbsoluteFile());
        BufferedWriter bw = new BufferedWriter(fw);

        preparedStatement = null;

        if (connection == null) {
            connection = jdbcConnectionUtils.getConnection();
        }
        if (preparedStatement == null) {
            preparedStatement = connection.prepareStatement(backUpLogDataTableQuery);
        }

        resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            bw.write(resultSet.getString(1) + "|"
            + resultSet.getString(2) + "|"
            + resultSet.getTimestamp(3) + "|"
            + resultSet.getString(4) + "|"
            + resultSet.getInt(5) + "|"
            + resultSet.getInt(6) + "|"
            + resultSet.getTimestamp(7) + "\n");
        }
        preparedStatement.close();
        bw.close();
    }

    public void backUpTimeAccessedDayCntTable(Connection connection) throws SQLException, ClassNotFoundException, IOException {

        File timeAccessedBackUpFile = new File(timeAccessedFile);

        // if file doesnt exists, then create it
        if (!timeAccessedBackUpFile.exists()) {
            timeAccessedBackUpFile.createNewFile();
        }
        FileWriter fw = new FileWriter(timeAccessedBackUpFile.getAbsoluteFile());
        BufferedWriter bw = new BufferedWriter(fw);

        preparedStatement = null;

        if (connection == null) {
            connection = jdbcConnectionUtils.getConnection();
        }
        if (preparedStatement == null) {
            preparedStatement = connection.prepareStatement(backUpTimeAccessedDayCntTableQuery);
        }

        resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            bw.write(resultSet.getTimestamp(1) + "|"
                    + resultSet.getInt(2) + "|"
                    + resultSet.getInt(3) + "|"
                    + resultSet.getInt(4) + "|"
                    + resultSet.getInt(5) + "|"
                    + resultSet.getInt(6) + "|"
                    + resultSet.getInt(7) + "|"
                    + resultSet.getInt(8) + "|"
                    + resultSet.getInt(9) + "|"
                    + resultSet.getInt(10) + "|"
                    + resultSet.getInt(11) + "|"
                    + resultSet.getInt(12) + "|"
                    + resultSet.getInt(13) + "|"
                    + resultSet.getInt(14) + "|"
                    + resultSet.getInt(15) + "|"
                    + resultSet.getInt(16) + "|"
                    + resultSet.getInt(17) + "|"
                    + resultSet.getInt(18) + "|"
                    + resultSet.getInt(19) + "|"
                    + resultSet.getInt(20) + "|"
                    + resultSet.getInt(21) + "|"
                    + resultSet.getInt(22) + "|"
                    + resultSet.getInt(23) + "|"
                    + resultSet.getInt(24) + "|"
                    + resultSet.getInt(25) + "|"
                    + resultSet.getInt(26) + "|"
                    + resultSet.getTimestamp(27) + "\n");
        }
        preparedStatement.close();
        bw.close();
    }

    private void runTruncateTable(Connection connection, String query) throws SQLException, ClassNotFoundException {

        preparedStatement = null;

        if (connection == null) {
            connection = jdbcConnectionUtils.getConnection();
        }
        if (preparedStatement == null) {
            preparedStatement = connection.prepareStatement(query);
        }
        preparedStatement.execute();
        preparedStatement.close();
    }

    public void backUpTruncateLogData(Connection connection) throws SQLException, ClassNotFoundException, IOException {
        // back-up log_data table
        backUpLogDataTable(connection);
        // truncate the log_data table
        runTruncateTable(connection, truncateLogDataQuery);
        // back-up the time_accessed_day_cnt table
        backUpTimeAccessedDayCntTable(connection);
        // truncate the time_accessed_day_cnt table
        runTruncateTable(connection, truncateTimeAccessedDayQuery);
    }
}
