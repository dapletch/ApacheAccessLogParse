package com.logparse.reports;

import com.logparse.beans.ipaddresslocation.IpAddress;
import com.logparse.beans.ipaddresslocation.IpAddressCntByCntry;
import com.logparse.beans.ipaddresslocation.IpAddressLocation;
import com.logparse.beans.timeaccessed.AvgTimeAccessedDayCnt;
import com.logparse.beans.timeaccessed.TimeAccessedDayCnt;
import com.logparse.beans.timeaccessed.TimeAccessedDayPreReqs;
import com.logparse.dao.ipaddresslocation.DistinctIpAddress;
import com.logparse.dao.timeaccessed.GetTimeAccessedDayCnts;
import com.logparse.dao.ipaddresslocation.InsertIpAddressLocation;
import com.logparse.dao.ipaddresslocation.IpAddressLocationCnts;
import com.logparse.dao.jdbc.JDBCConnectionUtils;
import com.logparse.geolocation.GeoLocation;
import com.logparse.utils.LogUtils;
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

    private AvgTimeAccessedDayCnt avgTimeAccessedDayCnt = new AvgTimeAccessedDayCnt();

    private GenerateHTMLReport generateHTMLReport = new GenerateHTMLReport();

    private GenerateCSVReport generateCSVReport = new GenerateCSVReport();

    private JDBCConnectionUtils jdbcConnectionUtils = new JDBCConnectionUtils();

    private DistinctIpAddress distinctIpAddress = new DistinctIpAddress();

    private GeoLocation geoLocation = new GeoLocation();

    private InsertIpAddressLocation insertIpAddressLocation = new InsertIpAddressLocation();

    private IpAddressLocationCnts ipAddressLocationCnts = new IpAddressLocationCnts();

    private List<TimeAccessedDayCnt> timeAccessedDayCntList = new ArrayList<TimeAccessedDayCnt>();

    private List<IpAddress> distinctIpAddressList = new ArrayList<IpAddress>();

    private List<IpAddressLocation> ipAddressLocationList = new ArrayList<IpAddressLocation>();

    private List<IpAddressLocation> ipAddressLocationCntsList = new ArrayList<IpAddressLocation>();

    private List<IpAddressCntByCntry> ipAddressCntByCntryList = new ArrayList<IpAddressCntByCntry>();

    private GenerateXLSXReport generateXLSXReport = new GenerateXLSXReport();

    private Connection connection = null;

    public void getPreReqsRunReports() throws SQLException, ClassNotFoundException {

        if (connection == null) {
            connection = jdbcConnectionUtils.getConnection();
        }

        // Get the the most recent time entered
        timeAccessedDayPreReqs = getTimeAccessedDayCnts.getMaxTimeEntered(connection);
        logger.info("Max Time Entered: " + timeAccessedDayPreReqs.getMaxTimeEntered());

        // Get the date ranges for the time accessed to be looped over
        timeAccessedDayPreReqs = getTimeAccessedDayCnts.getTimeAccessedDateRange(connection, timeAccessedDayPreReqs);
        logger.info("Prerequisite Date Ranges: " + timeAccessedDayPreReqs.toString());

        // Get counts for each hour of each day spanning the min and max time_accessed
        timeAccessedDayCntList = getTimeAccessedDayCnts.timeAccessedDayCntReport(connection, timeAccessedDayPreReqs);
        // Insert the counts for hours of each day into time_accessed_day_cnt
        getTimeAccessedDayCnts.insertDayCntReportToDatabase(connection, timeAccessedDayCntList);

        // Obtain the averages for each hour of each day and for the days themselves
        avgTimeAccessedDayCnt = getTimeAccessedDayCnts.getAvgTimeAccessedDayCnt(connection, timeAccessedDayPreReqs.getMaxTimeEntered());

        // Get the list of distinct ip_addresses from the ip_cnt view
        distinctIpAddressList = distinctIpAddress.distinctIpAddresses(connection);
        // get the location data for the ip_addresses using the external dat file.
        ipAddressLocationList = geoLocation.getLocation(distinctIpAddressList);
        // insert the the location data for the ip_addresses
        insertIpAddressLocation.insertIpAddressLocationData(connection, ipAddressLocationList);

        // Getting the total counts by using ip_cnt view with ip_address_location_table
        ipAddressLocationCntsList = ipAddressLocationCnts.getIpAddressCnt(connection);

        // Getting the country counts by querying ip_address_location table based off the last_date_entered
        ipAddressCntByCntryList = ipAddressLocationCnts.getIpAddressCntryCnts(connection);

        generateHTMLReport.writeReportInfoToHTMLDocument(timeAccessedDayCntList, avgTimeAccessedDayCnt);

        // Replaced the the generateCSVReport functionality/methods with the methods in the GenerateXLSXReport class
        // that way all the reports generated will be in one single ExcelSheet, which will far easier to obtain and perform reporting on
        generateXLSXReport.generateExcelSheetReports(
                timeAccessedDayCntList
                , avgTimeAccessedDayCnt
                , ipAddressLocationCntsList
                , ipAddressCntByCntryList
        );
        logger.info("Reporting Done...");

        jdbcConnectionUtils.closeConnection();
    }
}
