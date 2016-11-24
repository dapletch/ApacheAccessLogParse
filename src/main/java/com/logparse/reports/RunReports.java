package com.logparse.reports;

import com.logparse.beans.TimeAccessedDayCnt;
import com.logparse.beans.TimeAccessedDayPreReqs;
import com.logparse.dao.GetTimeAccessedDayCnts;
import com.logparse.dao.JDBCConnectionUtils;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Seth on 11/23/2016.
 */
public class RunReports {

    private Logger logger = Logger.getLogger(RunReports.class);

    private GetTimeAccessedDayCnts getTimeAccessedDayCnts = new GetTimeAccessedDayCnts();

    private TimeAccessedDayPreReqs timeAccessedDayPreReqs = new TimeAccessedDayPreReqs();

    private JDBCConnectionUtils jdbcConnectionUtils = new JDBCConnectionUtils();

    private List<TimeAccessedDayCnt> timeAccessedDayCntList = new ArrayList<TimeAccessedDayCnt>();

    private Connection connection = null;

    public void getPreReqsRunReports() throws SQLException, ClassNotFoundException {

        if (connection == null) {
            connection = jdbcConnectionUtils.getConnection();
        }

        timeAccessedDayPreReqs = getTimeAccessedDayCnts.getMaxTimeEntered();
        logger.info("Max Time Entered: " + timeAccessedDayPreReqs.getMaxTimeEntered());

        timeAccessedDayPreReqs = getTimeAccessedDayCnts.getTimeAccessedDateRange(timeAccessedDayPreReqs);
        logger.info("Prerequisite Date Ranges: " + timeAccessedDayPreReqs.toString());

        timeAccessedDayCntList = getTimeAccessedDayCnts.timeAccessedDayCntReport(timeAccessedDayPreReqs);

        jdbcConnectionUtils.closeConnection();
    }
}