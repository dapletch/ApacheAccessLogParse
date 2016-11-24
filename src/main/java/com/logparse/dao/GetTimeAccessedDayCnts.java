package com.logparse.dao;

import com.logparse.beans.TimeAccessedDayCnt;
import com.logparse.beans.TimeAccessedDayPreReqs;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.sql.*;

/**
 * Created by Seth on 11/23/2016.
 */
public class GetTimeAccessedDayCnts {

    private Logger logger = Logger.getLogger(GetTimeAccessedDayCnts.class);

    private TimeAccessedDayPreReqs timeAccessedDayPreReqs = new TimeAccessedDayPreReqs();

    private Connection connection = null;

    private PreparedStatement preparedStatement = null;

    private ResultSet resultSet = null;

    private JDBCConnectionUtils jdbcConnectionUtils = new JDBCConnectionUtils();

    private String getMaxTimeEnteredQuery = "select date(max(time_entered)) as max_time_entered from log_data;";

    private String getTimeAccessedDateRangesQuery = "select date(min(time_accessed)), date(max(time_accessed)) from log_data where date(time_entered) = ?;";

    public TimeAccessedDayPreReqs getMaxTimeEntered() throws SQLException, ClassNotFoundException {

        if (connection == null) {
            connection = jdbcConnectionUtils.getConnection();
        }
        if (preparedStatement == null) {
            preparedStatement = connection.prepareStatement(getMaxTimeEnteredQuery);
        }
        resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            timeAccessedDayPreReqs.setMaxTimeEntered(timeStampToDateTime(resultSet.getString(1)));
        }
        return timeAccessedDayPreReqs;
    }

    public TimeAccessedDayPreReqs getTimeAccessedDateRange(TimeAccessedDayPreReqs timeAccessedDayPreReqs) throws SQLException, ClassNotFoundException {

        preparedStatement = null;

        if (connection == null) {
            connection = jdbcConnectionUtils.getConnection();
        }
        if (preparedStatement == null) {
            preparedStatement = connection.prepareStatement(getTimeAccessedDateRangesQuery);
        }
        preparedStatement.setDate(1, dateToSqlDate(timeAccessedDayPreReqs.getMaxTimeEntered()));
        resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            timeAccessedDayPreReqs.setMinTimeAccessed(timeStampToDateTime(resultSet.getString(1)));
            timeAccessedDayPreReqs.setMaxTimeAccessed(timeStampToDateTime(resultSet.getString(2)));
        }

        return timeAccessedDayPreReqs;
    }

    private DateTime timeStampToDateTime(String dateStr) {
        return DateTime.parse(dateStr, DateTimeFormat.forPattern("yyyy-MM-dd")).toDateTime();
    }

    private java.sql.Date dateToSqlDate(DateTime date) {
        return new java.sql.Date(date.getMillis());
    }
}
