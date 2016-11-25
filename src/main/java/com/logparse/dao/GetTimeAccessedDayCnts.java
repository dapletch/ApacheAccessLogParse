package com.logparse.dao;

import com.logparse.beans.timeaccessed.AvgTimeAccessedDayCnt;
import com.logparse.beans.timeaccessed.TimeAccessedDayCnt;
import com.logparse.beans.timeaccessed.TimeAccessedDayPreReqs;
import com.logparse.utils.LogUtils;
import com.sun.org.apache.regexp.internal.RESyntaxException;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Seth on 11/23/2016.
 */
public class GetTimeAccessedDayCnts {

    private Logger logger = Logger.getLogger(GetTimeAccessedDayCnts.class);

    private TimeAccessedDayPreReqs timeAccessedDayPreReqs = new TimeAccessedDayPreReqs();

    private AvgTimeAccessedDayCnt avgTimeAccessedDayCnt = new AvgTimeAccessedDayCnt();

    private PreparedStatement preparedStatement = null;

    private ResultSet resultSet = null;

    private JDBCConnectionUtils jdbcConnectionUtils = new JDBCConnectionUtils();

    private List<TimeAccessedDayCnt> timeAccessedDayCntList = new ArrayList<TimeAccessedDayCnt>();

    private String getMaxTimeEnteredQuery = "SELECT date(max(time_entered)) AS max_time_entered FROM log_data;";

    private String getTimeAccessedDateRangesQuery = "SELECT date(min(time_accessed)), date(max(time_accessed)) FROM log_data WHERE date(time_entered) = ?;";

    private String getTimeAccessedDayCntReportQuery = "SELECT date(time_accessed)\n"
            + ", count(CASE WHEN hour(time_accessed)=1 THEN 1 ELSE NULL END)\n"
            + ", count(CASE WHEN hour(time_accessed)=2 THEN 1 ELSE NULL END)\n"
            + ", count(CASE WHEN hour(time_accessed)=3 THEN 1 ELSE NULL END)\n"
            + ", count(CASE WHEN hour(time_accessed)=4 THEN 1 ELSE NULL END)\n"
            + ", count(CASE WHEN hour(time_accessed)=5 THEN 1 ELSE NULL END)\n"
            + ", count(CASE WHEN hour(time_accessed)=6 THEN 1 ELSE NULL END)\n"
            + ", count(CASE WHEN hour(time_accessed)=7 THEN 1 ELSE NULL END)\n"
            + ", count(CASE WHEN hour(time_accessed)=8 THEN 1 ELSE NULL END)\n"
            + ", count(CASE WHEN hour(time_accessed)=9 THEN 1 ELSE NULL END)\n"
            + ", count(CASE WHEN hour(time_accessed)=10 THEN 1 ELSE NULL END)\n"
            + ", count(CASE WHEN hour(time_accessed)=11 THEN 1 ELSE NULL END)\n"
            + ", count(CASE WHEN hour(time_accessed)=12 THEN 1 ELSE NULL END)\n"
            + ", count(CASE WHEN hour(time_accessed)=13 THEN 1 ELSE NULL END)\n"
            + ", count(CASE WHEN hour(time_accessed)=14 THEN 1 ELSE NULL END)\n"
            + ", count(CASE WHEN hour(time_accessed)=15 THEN 1 ELSE NULL END)\n"
            + ", count(CASE WHEN hour(time_accessed)=16 THEN 1 ELSE NULL END)\n"
            + ", count(CASE WHEN hour(time_accessed)=17 THEN 1 ELSE NULL END)\n"
            + ", count(CASE WHEN hour(time_accessed)=18 THEN 1 ELSE NULL END)\n"
            + ", count(CASE WHEN hour(time_accessed)=19 THEN 1 ELSE NULL END)\n"
            + ", count(CASE WHEN hour(time_accessed)=20 THEN 1 ELSE NULL END)\n"
            + ", count(CASE WHEN hour(time_accessed)=21 THEN 1 ELSE NULL END)\n"
            + ", count(CASE WHEN hour(time_accessed)=22 THEN 1 ELSE NULL END)\n"
            + ", count(CASE WHEN hour(time_accessed)=23 THEN 1 ELSE NULL END)\n"
            + ", count(CASE WHEN hour(time_accessed)=24 THEN 1 ELSE NULL END)\n"
            + ", count(*) AS tot_day_cnt\n"
            + " FROM log_data\n"
            + " WHERE date(time_accessed) = ?"
            + " AND date(time_entered) = ?;";

    private String insertTimeAccessedDayReport = "INSERT INTO time_accessed_day_cnt (\n"
            + " time_accessed, one_am, two_am, three_am, four_am, five_am, six_am, seven_am\n"
            + " , eight_am, nine_am, ten_am, eleven_am, twelve_pm, one_pm, two_pm, three_pm\n"
            + " , four_pm, five_pm, six_pm, seven_pm, eight_pm, nine_pm, ten_pm, eleven_pm\n"
            + " , twelve_am, tot_day_cnt, time_entered\n"
            + " ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

    private String averageTimeAccessDayCntsQuery = "select round(avg(one_am), 0)\n"
            + ", round(avg(two_am), 0)\n"
            + ", round(avg(three_am), 0)\n"
            + ", round(avg(four_am), 0)\n"
            + ", round(avg(five_am), 0)\n"
            + ", round(avg(six_am), 0)\n"
            + ", round(avg(seven_am), 0)\n"
            + ", round(avg(eight_am), 0)\n"
            + ", round(avg(nine_am), 0)\n"
            + ", round(avg(ten_am), 0)\n"
            + ", round(avg(eleven_am), 0)\n"
            + ", round(avg(twelve_pm), 0)\n"
            + ", round(avg(one_pm), 0)\n"
            + ", round(avg(two_pm), 0)\n"
            + ", round(avg(three_pm), 0)\n"
            + ", round(avg(four_pm), 0)\n"
            + ", round(avg(five_pm), 0)\n"
            + ", round(avg(six_pm), 0)\n"
            + ", round(avg(seven_pm), 0)\n"
            + ", round(avg(eight_pm), 0)\n"
            + ", round(avg(nine_pm), 0)\n"
            + ", round(avg(ten_pm), 0)\n"
            + ", round(avg(eleven_pm), 0)\n"
            + ", round(avg(twelve_am), 0)\n"
            + ", round(avg(tot_day_cnt), 0)\n"
            + " from time_accessed_day_cnt\n"
            + " where time_entered = ?;";

    public TimeAccessedDayPreReqs getMaxTimeEntered(Connection connection) throws SQLException, ClassNotFoundException {

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

    public TimeAccessedDayPreReqs getTimeAccessedDateRange(Connection connection, TimeAccessedDayPreReqs timeAccessedDayPreReqs) throws SQLException, ClassNotFoundException {

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

    public List<TimeAccessedDayCnt> timeAccessedDayCntReport(Connection connection, TimeAccessedDayPreReqs timeAccessedDayPreReqs) throws SQLException, ClassNotFoundException {

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
            //logger.info(timeAccessedDayCnt.toString());
            timeAccessedDayCntList.add(timeAccessedDayCnt);
        }
        return timeAccessedDayCntList;
    }

    public void insertDayCntReportToDatabase(Connection connection, List<TimeAccessedDayCnt> timeAccessedDayCntList) throws SQLException, ClassNotFoundException {

        preparedStatement = null;

        if (connection == null) {
            connection = jdbcConnectionUtils.getConnection();
        }
        if (preparedStatement == null) {
            preparedStatement = connection.prepareStatement(insertTimeAccessedDayReport);
        }

        for (TimeAccessedDayCnt dayCnt : timeAccessedDayCntList) {
            preparedStatement.clearParameters();
            preparedStatement.setDate(1, LogUtils.dateTimeToSqlDate(dayCnt.getTimeAccessed()));
            preparedStatement.setInt(2, dayCnt.getOneAm());
            preparedStatement.setInt(3, dayCnt.getTwoAm());
            preparedStatement.setInt(4, dayCnt.getThreeAm());
            preparedStatement.setInt(5, dayCnt.getFourAm());
            preparedStatement.setInt(6, dayCnt.getFiveAm());
            preparedStatement.setInt(7, dayCnt.getSixAm());
            preparedStatement.setInt(8, dayCnt.getSevenAm());
            preparedStatement.setInt(9, dayCnt.getEightAm());
            preparedStatement.setInt(10, dayCnt.getNineAm());
            preparedStatement.setInt(11, dayCnt.getTenAm());
            preparedStatement.setInt(12, dayCnt.getElevenAm());
            preparedStatement.setInt(13, dayCnt.getTwelveAm());
            preparedStatement.setInt(14, dayCnt.getOnePm());
            preparedStatement.setInt(15, dayCnt.getTwoPm());
            preparedStatement.setInt(16, dayCnt.getThreePm());
            preparedStatement.setInt(17, dayCnt.getFourPm());
            preparedStatement.setInt(18, dayCnt.getFivePm());
            preparedStatement.setInt(19, dayCnt.getSixPm());
            preparedStatement.setInt(20, dayCnt.getSevenPm());
            preparedStatement.setInt(21, dayCnt.getEightPm());
            preparedStatement.setInt(22, dayCnt.getNinePm());
            preparedStatement.setInt(23, dayCnt.getTenPm());
            preparedStatement.setInt(24, dayCnt.getElevenPm());
            preparedStatement.setInt(25, dayCnt.getTwelveAm());
            preparedStatement.setInt(26, dayCnt.getTotalDayCnt());
            preparedStatement.setDate(27, LogUtils.dateTimeToSqlDate(dayCnt.getTimeEntered()));
            preparedStatement.execute();
        }

        logger.info("Records written to database.");
        preparedStatement.close();
    }

    public AvgTimeAccessedDayCnt getAvgTimeAccessedDayCnt(Connection connection, DateTime timeEntered) throws SQLException, ClassNotFoundException {

        preparedStatement = null;

        if (connection == null) {
            connection = jdbcConnectionUtils.getConnection();
        }
        if (preparedStatement == null) {
            preparedStatement = connection.prepareStatement(averageTimeAccessDayCntsQuery);
        }
        preparedStatement.setDate(1, LogUtils.dateTimeToSqlDate(timeEntered));
        resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            avgTimeAccessedDayCnt.setOneAmAvgCnt(resultSet.getInt(1));
            avgTimeAccessedDayCnt.setTwoAmAvgCnt(resultSet.getInt(2));
            avgTimeAccessedDayCnt.setThreeAmAvgCnt(resultSet.getInt(3));
            avgTimeAccessedDayCnt.setFourAmAvgCnt(resultSet.getInt(4));
            avgTimeAccessedDayCnt.setFiveAmAvgCnt(resultSet.getInt(5));
            avgTimeAccessedDayCnt.setSixAmAvgCnt(resultSet.getInt(6));
            avgTimeAccessedDayCnt.setSevenAmAvgCnt(resultSet.getInt(7));
            avgTimeAccessedDayCnt.setEightAmAvgCnt(resultSet.getInt(8));
            avgTimeAccessedDayCnt.setNineAmAvgCnt(resultSet.getInt(9));
            avgTimeAccessedDayCnt.setTenAmAvgCnt(resultSet.getInt(10));
            avgTimeAccessedDayCnt.setElevenAmAvgCnt(resultSet.getInt(11));
            avgTimeAccessedDayCnt.setTwelvePmAvgCnt(resultSet.getInt(12));
            avgTimeAccessedDayCnt.setOnePmAvgCnt(resultSet.getInt(13));
            avgTimeAccessedDayCnt.setTwoPmAvgCnt(resultSet.getInt(14));
            avgTimeAccessedDayCnt.setThreePmAvgCnt(resultSet.getInt(15));
            avgTimeAccessedDayCnt.setFourPmAvgCnt(resultSet.getInt(16));
            avgTimeAccessedDayCnt.setFivePmAvgCnt(resultSet.getInt(17));
            avgTimeAccessedDayCnt.setSixPmAvgCnt(resultSet.getInt(18));
            avgTimeAccessedDayCnt.setSevenPmAvgCnt(resultSet.getInt(19));
            avgTimeAccessedDayCnt.setEightPmAvgCnt(resultSet.getInt(20));
            avgTimeAccessedDayCnt.setNinePmAvgCnt(resultSet.getInt(21));
            avgTimeAccessedDayCnt.setTenPmAvgCnt(resultSet.getInt(22));
            avgTimeAccessedDayCnt.setElevenPmAvgCnt(resultSet.getInt(23));
            avgTimeAccessedDayCnt.setTwelveAmAvgCnt(resultSet.getInt(24));
            avgTimeAccessedDayCnt.setTotalAvgCnt(resultSet.getInt(25));
        }

        preparedStatement.close();
        return avgTimeAccessedDayCnt;
    }
}

