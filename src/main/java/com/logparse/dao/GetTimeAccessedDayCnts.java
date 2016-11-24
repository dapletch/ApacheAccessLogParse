package com.logparse.dao;

import com.logparse.beans.TimeAccessedDayCnt;
import com.logparse.beans.TimeAccessedDayPreReqs;
import com.logparse.utils.LogUtils;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

    private List<TimeAccessedDayCnt> timeAccessedDayCntList = new ArrayList<TimeAccessedDayCnt>();

    private String getMaxTimeEnteredQuery = "select date(max(time_entered)) as max_time_entered from log_data;";

    private String getTimeAccessedDateRangesQuery = "select date(min(time_accessed)), date(max(time_accessed)) from log_data where date(time_entered) = ?;";

    private String getTimeAccessedDayCntReportQuery = "select date(time_accessed) as time_accessed\n"
            + ", count(case when hour(time_accessed)=1 then 1 else null end) as one_am\n"
            + ", count(case when hour(time_accessed)=2 then 1 else null end) as two_am\n"
            + ", count(case when hour(time_accessed)=3 then 1 else null end) as three_am\n"
            + ", count(case when hour(time_accessed)=4 then 1 else null end) as four_am\n"
            + ", count(case when hour(time_accessed)=5 then 1 else null end) as five_am\n"
            + ", count(case when hour(time_accessed)=6 then 1 else null end) as six_am\n"
            + ", count(case when hour(time_accessed)=7 then 1 else null end) as seven_am\n"
            + ", count(case when hour(time_accessed)=8 then 1 else null end) as eight_am\n"
            + ", count(case when hour(time_accessed)=9 then 1 else null end) as nine_am\n"
            + ", count(case when hour(time_accessed)=10 then 1 else null end) as ten_am\n"
            + ", count(case when hour(time_accessed)=11 then 1 else null end) as eleven_am\n"
            + ", count(case when hour(time_accessed)=12 then 1 else null end) as twelve_pm\n"
            + ", count(case when hour(time_accessed)=13 then 1 else null end) as one_pm\n"
            + ", count(case when hour(time_accessed)=14 then 1 else null end) as two_pm\n"
            + ", count(case when hour(time_accessed)=15 then 1 else null end) as three_pm\n"
            + ", count(case when hour(time_accessed)=16 then 1 else null end) as four_pm\n"
            + ", count(case when hour(time_accessed)=17 then 1 else null end) as five_pm\n"
            + ", count(case when hour(time_accessed)=18 then 1 else null end) as six_pm\n"
            + ", count(case when hour(time_accessed)=19 then 1 else null end) as seven_pm\n"
            + ", count(case when hour(time_accessed)=20 then 1 else null end) as eight_pm\n"
            + ", count(case when hour(time_accessed)=21 then 1 else null end) as nine_pm\n"
            + ", count(case when hour(time_accessed)=22 then 1 else null end) as ten_pm\n"
            + ", count(case when hour(time_accessed)=23 then 1 else null end) as eleven_pm\n"
            + ", count(case when hour(time_accessed)=24 then 1 else null end) as twelve_am\n"
            + ", count(*) as tot_day_cnt\n"
            + " from log_data\n"
            + " where date(time_accessed) = ?"
            + " and date(time_entered) = ?;";

    public TimeAccessedDayPreReqs getMaxTimeEntered() throws SQLException, ClassNotFoundException {

        if (connection == null) {
            connection = jdbcConnectionUtils.getConnection();
        }
        if (preparedStatement == null) {
            preparedStatement = connection.prepareStatement(getMaxTimeEnteredQuery);
        }
        resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            timeAccessedDayPreReqs.setMaxTimeEntered(LogUtils.sqlDateToDateTime(resultSet.getString(1)));
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
        preparedStatement.setDate(1, LogUtils.dateTimeToSqlDate(timeAccessedDayPreReqs.getMaxTimeEntered()));
        resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            timeAccessedDayPreReqs.setMinTimeAccessed(LogUtils.sqlDateToDateTime(resultSet.getString(1)));
            timeAccessedDayPreReqs.setMaxTimeAccessed(LogUtils.sqlDateToDateTime(resultSet.getString(2)));
        }

        return timeAccessedDayPreReqs;
    }

    public List<TimeAccessedDayCnt> timeAccessedDayCntReport(TimeAccessedDayPreReqs timeAccessedDayPreReqs) throws SQLException, ClassNotFoundException {

        DateTime date;
        preparedStatement = null;

        if (connection == null) {
            connection = jdbcConnectionUtils.getConnection();
        }
        if (preparedStatement == null) {
            preparedStatement = connection.prepareStatement(getTimeAccessedDayCntReportQuery);
        }

        for (date = timeAccessedDayPreReqs.getMinTimeAccessed(); date.isBefore(timeAccessedDayPreReqs.getMaxTimeAccessed()); date = date.plusDays(1)) {
            TimeAccessedDayCnt timeAccessedDayCnt = new TimeAccessedDayCnt();
            preparedStatement.clearParameters();
            preparedStatement.setDate(1, LogUtils.dateTimeToSqlDate(date));
            preparedStatement.setDate(2, LogUtils.dateTimeToSqlDate(timeAccessedDayPreReqs.getMaxTimeEntered()));
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                if (!LogUtils.isDateStringValid(resultSet.getString(1))) {
                    // Just skip over record if the dateStr is null, that means there were no records for that day
                    continue;
                }
                timeAccessedDayCnt.setTimeAccessed(LogUtils.sqlDateToDateTime(resultSet.getString(1)));
                timeAccessedDayCnt.setOneAm(resultSet.getInt(2));
                timeAccessedDayCnt.setTwoAm(resultSet.getInt(3));
                timeAccessedDayCnt.setThreeAm(resultSet.getInt(4));
                timeAccessedDayCnt.setFourAm(resultSet.getInt(5));
                timeAccessedDayCnt.setFiveAm(resultSet.getInt(6));
                timeAccessedDayCnt.setSixAm(resultSet.getInt(7));
                timeAccessedDayCnt.setSevenAm(resultSet.getInt(8));
                timeAccessedDayCnt.setEightAm(resultSet.getInt(9));
                timeAccessedDayCnt.setNineAm(resultSet.getInt(10));
                timeAccessedDayCnt.setTenAm(resultSet.getInt(11));
                timeAccessedDayCnt.setElevenAm(resultSet.getInt(12));
                timeAccessedDayCnt.setTwelvePm(resultSet.getInt(13));
                timeAccessedDayCnt.setOnePm(resultSet.getInt(14));
                timeAccessedDayCnt.setTwoPm(resultSet.getInt(15));
                timeAccessedDayCnt.setThreePm(resultSet.getInt(16));
                timeAccessedDayCnt.setFourPm(resultSet.getInt(17));
                timeAccessedDayCnt.setFivePm(resultSet.getInt(18));
                timeAccessedDayCnt.setSixPm(resultSet.getInt(19));
                timeAccessedDayCnt.setSevenPm(resultSet.getInt(20));
                timeAccessedDayCnt.setEightPm(resultSet.getInt(21));
                timeAccessedDayCnt.setNinePm(resultSet.getInt(22));
                timeAccessedDayCnt.setTenPm(resultSet.getInt(23));
                timeAccessedDayCnt.setElevenPm(resultSet.getInt(24));
                timeAccessedDayCnt.setTwelveAm(resultSet.getInt(25));
                timeAccessedDayCnt.setTotalDayCnt(resultSet.getInt(26));
                timeAccessedDayCnt.setTimeEntered(timeAccessedDayPreReqs.getMaxTimeEntered());
            }
            logger.info(timeAccessedDayCnt.toString());
            timeAccessedDayCntList.add(timeAccessedDayCnt);
        }
        return timeAccessedDayCntList;
    }
}
