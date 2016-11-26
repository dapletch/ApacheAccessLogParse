package com.logparse.reports;

import com.logparse.beans.timeaccessed.AvgTimeAccessedDayCnt;
import com.logparse.beans.timeaccessed.TimeAccessedDayCnt;
import com.logparse.utils.LogUtils;
import com.logparse.utils.OSUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Created by Seth on 11/24/2016.
 */
public class GenerateHTMLReport {

    public void writeReportInfoToHTMLDocument(List<TimeAccessedDayCnt> timeAccessedDayCntList, AvgTimeAccessedDayCnt avgTimeAccessedDayCnt) {

        try {
            File htmlReport = new File(OSUtils.getFilePathAboveCurrentOne() + "PletcherWebDesignUsage" + LogUtils.generateFileTimeStamp() + ".html");

            // if file doesnt exists, then create it
            if (!htmlReport.exists()) {
                htmlReport.createNewFile();
            }

            FileWriter fw = new FileWriter(htmlReport.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("<!DOCTYPE html>\n"
                    + "<html lang=\"en\">\n"
                    + "<head>\n"
                    + "<meta charset=\"utf-8\">\n"
                    + "<title>PletcherWebDesign Usage Report - "
                    + LogUtils.generateFileTimeStamp() + "</title>\n"
                    + "</head>\n"
                    + "<body>\n");

            bw.write("<p align=\"center\">This report was generated for the records that were inputted on "
                    + LogUtils.generateFileTimeStamp() + "</p>");

            bw.write("<table border=\"1\">\n"
                    + "<tr>\n"
                    + "<th>Time Accessed</th>\n"
                    + "<th>12:00 am</th>\n"
                    + "<th>1:00 am</th>\n"
                    + "<th>2:00 am</th>\n"
                    + "<th>3:00 am</th>\n"
                    + "<th>4:00 am</th>\n"
                    + "<th>5:00 am</th>\n"
                    + "<th>6:00 am</th>\n"
                    + "<th>7:00 am</th>\n"
                    + "<th>8:00 am</th>\n"
                    + "<th>9:00 am</th>\n"
                    + "<th>10:00 am</th>\n"
                    + "<th>11:00 am</th>\n"
                    + "<th>12:00 pm</th>\n"
                    + "<th>1:00 pm</th>\n"
                    + "<th>2:00 pm</th>\n"
                    + "<th>3:00 pm</th>\n"
                    + "<th>4:00 pm</th>\n"
                    + "<th>5:00 pm</th>\n"
                    + "<th>6:00 pm</th>\n"
                    + "<th>7:00 pm</th>\n"
                    + "<th>8:00 pm</th>\n"
                    + "<th>9:00 pm</th>\n"
                    + "<th>10:00 pm</th>\n"
                    + "<th>11:00 pm</th>\n"
                    + "<th>Total Day Cnt</th>\n"
                    + "<th>Time Entered</th>\n"
                    + "</tr>");

            for (TimeAccessedDayCnt timeAccessedDayCnt : timeAccessedDayCntList) {
                bw.write("<tr><td>" + timeAccessedDayCnt.getTimeEntered() + "</td>\n"
                        + "<td>" + timeAccessedDayCnt.getTwelveAm() + "</td>\n"
                        + "<td>" + timeAccessedDayCnt.getOneAm() + "</td>\n"
                        + "<td>" + timeAccessedDayCnt.getTwoAm() + "</td>\n"
                        + "<td>" + timeAccessedDayCnt.getThreeAm() + "</td>\n"
                        + "<td>" + timeAccessedDayCnt.getFourAm() + "</td>\n"
                        + "<td>" + timeAccessedDayCnt.getFiveAm() + "</td>\n"
                        + "<td>" + timeAccessedDayCnt.getSixAm() + "</td>\n"
                        + "<td>" + timeAccessedDayCnt.getSevenAm() + "</td>\n"
                        + "<td>" + timeAccessedDayCnt.getEightAm() + "</td>\n"
                        + "<td>" + timeAccessedDayCnt.getNineAm() + "</td>\n"
                        + "<td>" + timeAccessedDayCnt.getTenAm() + "</td>\n"
                        + "<td>" + timeAccessedDayCnt.getElevenAm() + "</td>\n"
                        + "<td>" + timeAccessedDayCnt.getTwelvePm() + "</td>\n"
                        + "<td>" + timeAccessedDayCnt.getOnePm() + "</td>\n"
                        + "<td>" + timeAccessedDayCnt.getTwoPm() + "</td>\n"
                        + "<td>" + timeAccessedDayCnt.getThreePm() + "</td>\n"
                        + "<td>" + timeAccessedDayCnt.getFourPm() + "</td>\n"
                        + "<td>" + timeAccessedDayCnt.getFivePm() + "</td>\n"
                        + "<td>" + timeAccessedDayCnt.getSixPm() + "</td>\n"
                        + "<td>" + timeAccessedDayCnt.getSevenPm() + "</td>\n"
                        + "<td>" + timeAccessedDayCnt.getEightPm() + "</td>\n"
                        + "<td>" + timeAccessedDayCnt.getNinePm() + "</td>\n"
                        + "<td>" + timeAccessedDayCnt.getTenPm() + "</td>\n"
                        + "<td>" + timeAccessedDayCnt.getElevenPm() + "</td>\n"
                        + "<td>" + timeAccessedDayCnt.getTotalDayCnt() + "</td>\n"
                        + "<td>" + timeAccessedDayCnt.getTimeEntered() + "</td></tr>\n");
            }

            bw.write("<tr><td><b>Hourly Averages</b></td><td>" + avgTimeAccessedDayCnt.getTwelveAmAvgCnt() + "</td>\n"
                    + "<td>" + avgTimeAccessedDayCnt.getOneAmAvgCnt() + "</td>\n"
                    + "<td>" + avgTimeAccessedDayCnt.getTwoAmAvgCnt() + "</td>\n"
                    + "<td>" + avgTimeAccessedDayCnt.getThreeAmAvgCnt() + "</td>\n"
                    + "<td>" + avgTimeAccessedDayCnt.getFourAmAvgCnt() + "</td>\n"
                    + "<td>" + avgTimeAccessedDayCnt.getFiveAmAvgCnt() + "</td>\n"
                    + "<td>" + avgTimeAccessedDayCnt.getSixAmAvgCnt() + "</td>\n"
                    + "<td>" + avgTimeAccessedDayCnt.getSevenAmAvgCnt() + "</td>\n"
                    + "<td>" + avgTimeAccessedDayCnt.getEightAmAvgCnt() + "</td>\n"
                    + "<td>" + avgTimeAccessedDayCnt.getNineAmAvgCnt() + "</td>\n"
                    + "<td>" + avgTimeAccessedDayCnt.getTenAmAvgCnt() + "</td>\n"
                    + "<td>" + avgTimeAccessedDayCnt.getElevenAmAvgCnt() + "</td>\n"
                    + "<td>" + avgTimeAccessedDayCnt.getTwelvePmAvgCnt() + "</td>\n"
                    + "<td>" + avgTimeAccessedDayCnt.getOnePmAvgCnt() + "</td>\n"
                    + "<td>" + avgTimeAccessedDayCnt.getTwoPmAvgCnt() + "</td>\n"
                    + "<td>" + avgTimeAccessedDayCnt.getThreePmAvgCnt() + "</td>\n"
                    + "<td>" + avgTimeAccessedDayCnt.getFourPmAvgCnt() + "</td>\n"
                    + "<td>" + avgTimeAccessedDayCnt.getFivePmAvgCnt() + "</td>\n"
                    + "<td>" + avgTimeAccessedDayCnt.getSixPmAvgCnt() + "</td>\n"
                    + "<td>" + avgTimeAccessedDayCnt.getSevenPmAvgCnt() + "</td>\n"
                    + "<td>" + avgTimeAccessedDayCnt.getEightPmAvgCnt() + "</td>\n"
                    + "<td>" + avgTimeAccessedDayCnt.getNinePmAvgCnt() + "</td>\n"
                    + "<td>" + avgTimeAccessedDayCnt.getTenPmAvgCnt() + "</td>\n"
                    + "<td>" + avgTimeAccessedDayCnt.getElevenPmAvgCnt() + "</td>\n"
                    + "<td>" + avgTimeAccessedDayCnt.getTotalAvgCnt() + "</td><td></td></tr>\n");

            bw.write("</body>\n"
                    + "</html>");
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
