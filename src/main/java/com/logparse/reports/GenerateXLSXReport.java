package com.logparse.reports;

import com.logparse.beans.ipaddresslocation.IpAddressCntByCntry;
import com.logparse.beans.ipaddresslocation.IpAddressLocation;
import com.logparse.beans.timeaccessed.AvgTimeAccessedDayCnt;
import com.logparse.beans.timeaccessed.TimeAccessedDayCnt;
import com.logparse.utils.LogUtils;
import com.logparse.utils.OSUtils;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by Seth on 4/10/2017.
 */
public class GenerateXLSXReport {

    /*
    The purpose of this class is to consolidate the following reports:
    writeTimeAccessedDayCntReportInfoToCSVDDocument,
    writeIpAccessLocationCntInfoToTxtDocument,
    writeIpAccessCntryCntInfoToTxtDocument
    Into one Excel Document, so they can all be retrieved by accessing one file, instead of multiple files.
    That way the data can be easily manipulated, and worked with for the purposes of reporting.

    There will three Excel Sheets created from this process and they are listed, as follows:
    TimeAccessedDayCnt,
    IpAddressLocation,
    and IpAddressCntByCntry
     */

    private Logger logger = Logger.getLogger(GenerateXLSXReport.class);

    // TODO need to test this method more with other log files before it goes into production
    // This method is the flow control for the Excel Sheet reports
    public void generateExcelSheetReports(List<TimeAccessedDayCnt> timeAccessedDayCntList
            , AvgTimeAccessedDayCnt avgTimeAccessedDayCnt
            , List<IpAddressLocation> ipAddressLocationCntsList
            , List<IpAddressCntByCntry> ipAddressCntByCntryList) {

        File excelFile = new File(OSUtils.getFilePathAboveCurrentOne()
                + "PletcherWebDesignUsage" + LogUtils.generateFileTimeStamp() + ".xlsx");
        Boolean fileCreated;
        try {
            if (!excelFile.exists()) {
                fileCreated = excelFile.createNewFile();
                logger.info("File Created: " + excelFile + " " + fileCreated);
            }
            // Creating the work book and the individual sheets themselves
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet timeAccessedDayCntSheet = workbook.createSheet("TimeAccessedDayCnt");
            XSSFSheet ipAddressLocationSheet = workbook.createSheet("IpAddressLocation");
            XSSFSheet ipAddressCntByCntrySheet = workbook.createSheet("IpAddressCntByCntry");

            // Calling the methods to create the sheets
            writeTimeAccessedDayCntReportInfoToExcelSheet(timeAccessedDayCntSheet, timeAccessedDayCntList, avgTimeAccessedDayCnt);
            writeIpAddressLocationCntInfoToExcelSheet(ipAddressLocationSheet, ipAddressLocationCntsList);
            writeIpAddressCntryCntInfoToExcelSheet(ipAddressCntByCntrySheet, ipAddressCntByCntryList);

            // Writing the sheets to the excel file
            FileOutputStream outputStream = new FileOutputStream(excelFile);
            workbook.write(outputStream);
            workbook.close();
        } catch (IOException e) {
            logger.error("An error occurred while creating/working with the file: \n" + e);
        }
    }

    private void writeTimeAccessedDayCntReportInfoToExcelSheet(XSSFSheet timeAccessedDayCntSheet
            , List<TimeAccessedDayCnt> timeAccessedDayCntList
            , AvgTimeAccessedDayCnt avgTimeAccessedDayCnt) {

        // Creating the header for the TimeAccessedDayCnt Sheet
        Row rowHeader = timeAccessedDayCntSheet.createRow(0);
        rowHeader.createCell(0).setCellValue("Time Accessed");
        rowHeader.createCell(1).setCellValue("12:00 am");
        rowHeader.createCell(2).setCellValue("1:00 am");
        rowHeader.createCell(3).setCellValue("2:00 am");
        rowHeader.createCell(4).setCellValue("3:00 am");
        rowHeader.createCell(5).setCellValue("4:00 am");
        rowHeader.createCell(6).setCellValue("5:00 am");
        rowHeader.createCell(7).setCellValue("6:00 am");
        rowHeader.createCell(8).setCellValue("7:00 am");
        rowHeader.createCell(9).setCellValue("8:00 am");
        rowHeader.createCell(10).setCellValue("9:00 am");
        rowHeader.createCell(11).setCellValue("10:00 am");
        rowHeader.createCell(12).setCellValue("11:00 am");
        rowHeader.createCell(13).setCellValue("12:00 pm");
        rowHeader.createCell(14).setCellValue("1:00 pm");
        rowHeader.createCell(15).setCellValue("2:00 pm");
        rowHeader.createCell(16).setCellValue("3:00 pm");
        rowHeader.createCell(17).setCellValue("4:00 pm");
        rowHeader.createCell(18).setCellValue("5:00 pm");
        rowHeader.createCell(19).setCellValue("6:00 pm");
        rowHeader.createCell(20).setCellValue("7:00 pm");
        rowHeader.createCell(21).setCellValue("8:00 pm");
        rowHeader.createCell(22).setCellValue("9:00 pm");
        rowHeader.createCell(23).setCellValue("10:00 pm");
        rowHeader.createCell(24).setCellValue("11:00 pm");
        rowHeader.createCell(25).setCellValue("Total Day Cnt");
        rowHeader.createCell(26).setCellValue("Time Entered");

        ListIterator<TimeAccessedDayCnt> timeAccessedDayCntIterator = timeAccessedDayCntList.listIterator();
        Integer rowIndex = 1;
        // The content of the report
        while (timeAccessedDayCntIterator.hasNext()) {
            TimeAccessedDayCnt timeAccessedDayCnt = timeAccessedDayCntIterator.next();
            // Increment the row index to write to a new row each time the loop iterates
            Row row = timeAccessedDayCntSheet.createRow(rowIndex++);
            row.createCell(0).setCellValue(String.valueOf(timeAccessedDayCnt.getTimeAccessed()));
            row.createCell(1).setCellValue(timeAccessedDayCnt.getTwelveAm());
            row.createCell(2).setCellValue(timeAccessedDayCnt.getOneAm());
            row.createCell(3).setCellValue(timeAccessedDayCnt.getTwoAm());
            row.createCell(4).setCellValue(timeAccessedDayCnt.getThreeAm());
            row.createCell(5).setCellValue(timeAccessedDayCnt.getFourAm());
            row.createCell(6).setCellValue(timeAccessedDayCnt.getFiveAm());
            row.createCell(7).setCellValue(timeAccessedDayCnt.getSixAm());
            row.createCell(8).setCellValue(timeAccessedDayCnt.getSevenAm());
            row.createCell(9).setCellValue(timeAccessedDayCnt.getEightAm());
            row.createCell(10).setCellValue(timeAccessedDayCnt.getNineAm());
            row.createCell(11).setCellValue(timeAccessedDayCnt.getTenAm());
            row.createCell(12).setCellValue(timeAccessedDayCnt.getElevenAm());
            row.createCell(13).setCellValue(timeAccessedDayCnt.getTwelvePm());
            row.createCell(14).setCellValue(timeAccessedDayCnt.getOnePm());
            row.createCell(15).setCellValue(timeAccessedDayCnt.getTwoPm());
            row.createCell(16).setCellValue(timeAccessedDayCnt.getThreePm());
            row.createCell(17).setCellValue(timeAccessedDayCnt.getFourPm());
            row.createCell(18).setCellValue(timeAccessedDayCnt.getFivePm());
            row.createCell(19).setCellValue(timeAccessedDayCnt.getSixPm());
            row.createCell(20).setCellValue(timeAccessedDayCnt.getSevenPm());
            row.createCell(21).setCellValue(timeAccessedDayCnt.getEightPm());
            row.createCell(22).setCellValue(timeAccessedDayCnt.getNinePm());
            row.createCell(23).setCellValue(timeAccessedDayCnt.getTenPm());
            row.createCell(24).setCellValue(timeAccessedDayCnt.getElevenPm());
            row.createCell(25).setCellValue(timeAccessedDayCnt.getTotalDayCnt());
            row.createCell(26).setCellValue(String.valueOf(timeAccessedDayCnt.getTimeEntered()));
        }
        // The footer for the report containing the hourly averages the index will the last value of rowIndex
        Row rowFooter = timeAccessedDayCntSheet.createRow(rowIndex);
        rowFooter.createCell(0).setCellValue("Hourly Averages");
        rowFooter.createCell(1).setCellValue(avgTimeAccessedDayCnt.getTwelveAmAvgCnt());
        rowFooter.createCell(2).setCellValue(avgTimeAccessedDayCnt.getOneAmAvgCnt());
        rowFooter.createCell(3).setCellValue(avgTimeAccessedDayCnt.getTwoAmAvgCnt());
        rowFooter.createCell(4).setCellValue(avgTimeAccessedDayCnt.getThreeAmAvgCnt());
        rowFooter.createCell(5).setCellValue(avgTimeAccessedDayCnt.getFourAmAvgCnt());
        rowFooter.createCell(6).setCellValue(avgTimeAccessedDayCnt.getFiveAmAvgCnt());
        rowFooter.createCell(7).setCellValue(avgTimeAccessedDayCnt.getSixAmAvgCnt());
        rowFooter.createCell(8).setCellValue(avgTimeAccessedDayCnt.getSevenAmAvgCnt());
        rowFooter.createCell(9).setCellValue(avgTimeAccessedDayCnt.getEightAmAvgCnt());
        rowFooter.createCell(10).setCellValue(avgTimeAccessedDayCnt.getNineAmAvgCnt());
        rowFooter.createCell(11).setCellValue(avgTimeAccessedDayCnt.getTenAmAvgCnt());
        rowFooter.createCell(12).setCellValue(avgTimeAccessedDayCnt.getElevenAmAvgCnt());
        rowFooter.createCell(13).setCellValue(avgTimeAccessedDayCnt.getTwelvePmAvgCnt());
        rowFooter.createCell(14).setCellValue(avgTimeAccessedDayCnt.getOnePmAvgCnt());
        rowFooter.createCell(15).setCellValue(avgTimeAccessedDayCnt.getTwoPmAvgCnt());
        rowFooter.createCell(16).setCellValue(avgTimeAccessedDayCnt.getThreePmAvgCnt());
        rowFooter.createCell(17).setCellValue(avgTimeAccessedDayCnt.getFourPmAvgCnt());
        rowFooter.createCell(18).setCellValue(avgTimeAccessedDayCnt.getFivePmAvgCnt());
        rowFooter.createCell(19).setCellValue(avgTimeAccessedDayCnt.getSixPmAvgCnt());
        rowFooter.createCell(20).setCellValue(avgTimeAccessedDayCnt.getSevenPmAvgCnt());
        rowFooter.createCell(21).setCellValue(avgTimeAccessedDayCnt.getEightPmAvgCnt());
        rowFooter.createCell(22).setCellValue(avgTimeAccessedDayCnt.getNinePmAvgCnt());
        rowFooter.createCell(23).setCellValue(avgTimeAccessedDayCnt.getTenPmAvgCnt());
        rowFooter.createCell(24).setCellValue(avgTimeAccessedDayCnt.getElevenPmAvgCnt());
        rowFooter.createCell(25).setCellValue(avgTimeAccessedDayCnt.getTotalAvgCnt());
    }

    private void writeIpAddressLocationCntInfoToExcelSheet(XSSFSheet ipAddressLocationSheet
            , List<IpAddressLocation> ipAddressLocationCntsList) {

        // Creating the header for the IpAddressLocation Sheet
        Row rowHeader = ipAddressLocationSheet.createRow(0);
        rowHeader.createCell(0).setCellValue("IP Address");
        rowHeader.createCell(1).setCellValue("Country Code");
        rowHeader.createCell(2).setCellValue("Country Name");
        rowHeader.createCell(3).setCellValue("Region");
        rowHeader.createCell(4).setCellValue("Region Name");
        rowHeader.createCell(5).setCellValue("City");
        rowHeader.createCell(6).setCellValue("Postal Code");
        rowHeader.createCell(7).setCellValue("Latitude");
        rowHeader.createCell(8).setCellValue("Longitude");
        rowHeader.createCell(9).setCellValue("DMA Code");
        rowHeader.createCell(10).setCellValue("Area Code");
        rowHeader.createCell(11).setCellValue("Metro Code");
        rowHeader.createCell(12).setCellValue("Total Count");

        ListIterator<IpAddressLocation> ipAddressLocationListIterator = ipAddressLocationCntsList.listIterator();
        Integer rowIndex = 1;
        // The content of the IpAddressLocation Report
        while (ipAddressLocationListIterator.hasNext()) {
            IpAddressLocation ipAddressLocation = ipAddressLocationListIterator.next();
            Row row = ipAddressLocationSheet.createRow(rowIndex++);
            row.createCell(0).setCellValue(ipAddressLocation.getIpAddress());
            row.createCell(1).setCellValue(ipAddressLocation.getCountryCode());
            row.createCell(2).setCellValue(ipAddressLocation.getCountryName());
            row.createCell(3).setCellValue(ipAddressLocation.getRegion());
            row.createCell(4).setCellValue(ipAddressLocation.getRegionName());
            row.createCell(5).setCellValue(ipAddressLocation.getCity());
            row.createCell(6).setCellValue(ipAddressLocation.getPostalCode());
            row.createCell(7).setCellValue(ipAddressLocation.getLatitude());
            row.createCell(8).setCellValue(ipAddressLocation.getLongitude());
            row.createCell(9).setCellValue(ipAddressLocation.getDmaCode());
            row.createCell(10).setCellValue(ipAddressLocation.getAreaCode());
            row.createCell(11).setCellValue(ipAddressLocation.getMetroCode());
            row.createCell(12).setCellValue(ipAddressLocation.getTotalCnt());
        }
    }

    private void writeIpAddressCntryCntInfoToExcelSheet(XSSFSheet ipAddressCntByCntrySheet
            , List<IpAddressCntByCntry> ipAddressCntByCntryList) {

        // Creating the header for the IpAddressCntByCntry Sheet
        Row rowHeader = ipAddressCntByCntrySheet.createRow(0);
        rowHeader.createCell(0).setCellValue("Country Name");
        rowHeader.createCell(1).setCellValue("Country Code");
        rowHeader.createCell(2).setCellValue("Region Name");
        rowHeader.createCell(3).setCellValue("City");
        rowHeader.createCell(4).setCellValue("Region Name");
        rowHeader.createCell(5).setCellValue("Distinct IP Address Cnt");

        ListIterator<IpAddressCntByCntry> ipAddressLocationListIterator = ipAddressCntByCntryList.listIterator();
        Integer rowIndex = 1;
        while (ipAddressLocationListIterator.hasNext()) {
            IpAddressCntByCntry ipAddressCntByCntry = ipAddressLocationListIterator.next();
            Row row = ipAddressCntByCntrySheet.createRow(rowIndex++);
            row.createCell(0).setCellValue(ipAddressCntByCntry.getCountryName());
            row.createCell(1).setCellValue(ipAddressCntByCntry.getCountryCode());
            row.createCell(2).setCellValue(ipAddressCntByCntry.getRegionName());
            row.createCell(3).setCellValue(ipAddressCntByCntry.getRegion());
            row.createCell(4).setCellValue(ipAddressCntByCntry.getCity());
            row.createCell(5).setCellValue(ipAddressCntByCntry.getDistinctIpCnt());
        }
    }
}
