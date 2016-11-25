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
 * Created by Seth on 11/25/2016.
 */
public class GenerateCSVReport {

    public void writeReportInfoToCSVDDocument(List<TimeAccessedDayCnt> timeAccessedDayCntList, AvgTimeAccessedDayCnt avgTimeAccessedDayCnt) {

        try {
            File csvReport = new File(OSUtils.getFilePathAboveCurrentOne() + "PletcherWebDesignUsage" + LogUtils.generateFileTimeStamp() + ".csv");

            // if file doesnt exists, then create it
            if (!csvReport.exists()) {
                csvReport.createNewFile();
            }

            FileWriter fw = new FileWriter(csvReport.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);

            bw.write("Time Accessed,"
                    + "12:00 am,"
                    + "1:00 am,"
                    + "2:00 am,"
                    + "3:00 am,"
                    + "4:00 am,"
                    + "5:00 am,"
                    + "6:00 am,"
                    + "7:00 am,"
                    + "8:00 am,"
                    + "9:00 am,"
                    + "10:00 am,"
                    + "11:00 am,"
                    + "12:00 pm,"
                    + "1:00 pm,"
                    + "2:00 pm,"
                    + "3:00 pm,"
                    + "4:00 pm,"
                    + "5:00 pm,"
                    + "6:00 pm,"
                    + "7:00 pm,"
                    + "8:00 pm,"
                    + "9:00 pm,"
                    + "10:00 pm,"
                    + "11:00 pm,"
                    + "Total Day Cnt,"
                    + "Time Entered\n");

            for (TimeAccessedDayCnt timeAccessedDayCnt : timeAccessedDayCntList) {
                bw.write(timeAccessedDayCnt.getTimeEntered() + ","
                         + timeAccessedDayCnt.getTwelveAm() + ","
                         + timeAccessedDayCnt.getOneAm() + ","
                         + timeAccessedDayCnt.getTwoAm() + ","
                         + timeAccessedDayCnt.getThreeAm() + ","
                         + timeAccessedDayCnt.getFourAm() + ","
                         + timeAccessedDayCnt.getFiveAm() + ","
                         + timeAccessedDayCnt.getSixAm() + ","
                         + timeAccessedDayCnt.getSevenAm() + ","
                         + timeAccessedDayCnt.getEightAm() + ","
                         + timeAccessedDayCnt.getNineAm() + ","
                         + timeAccessedDayCnt.getTenAm() + ","
                         + timeAccessedDayCnt.getElevenAm() + ","
                         + timeAccessedDayCnt.getTwelveAm() + ","
                         + timeAccessedDayCnt.getOnePm() + ","
                         + timeAccessedDayCnt.getTwoPm() + ","
                         + timeAccessedDayCnt.getThreePm() + ","
                         + timeAccessedDayCnt.getFourPm() + ","
                         + timeAccessedDayCnt.getFivePm() + ","
                         + timeAccessedDayCnt.getSixPm() + ","
                         + timeAccessedDayCnt.getSevenPm() + ","
                         + timeAccessedDayCnt.getEightPm() + ","
                         + timeAccessedDayCnt.getNinePm() + ","
                         + timeAccessedDayCnt.getTenPm() + ","
                         + timeAccessedDayCnt.getElevenPm() + ","
                         + timeAccessedDayCnt.getTotalDayCnt() + ","
                         + timeAccessedDayCnt.getTimeEntered() + "\n");
            }

            bw.write("Hourly Averages,"
                    + avgTimeAccessedDayCnt.getTwelveAmAvgCnt() + ","
                    + avgTimeAccessedDayCnt.getOneAmAvgCnt() + ","
                    + avgTimeAccessedDayCnt.getTwoAmAvgCnt() + ","
                    + avgTimeAccessedDayCnt.getThreeAmAvgCnt() + ","
                    + avgTimeAccessedDayCnt.getFourAmAvgCnt() + ","
                    + avgTimeAccessedDayCnt.getFiveAmAvgCnt() + ","
                    + avgTimeAccessedDayCnt.getSixAmAvgCnt() + ","
                    + avgTimeAccessedDayCnt.getSevenAmAvgCnt() + ","
                    + avgTimeAccessedDayCnt.getEightAmAvgCnt() + ","
                    + avgTimeAccessedDayCnt.getNineAmAvgCnt() + ","
                    + avgTimeAccessedDayCnt.getTenAmAvgCnt() + ","
                    + avgTimeAccessedDayCnt.getElevenAmAvgCnt() + ","
                    + avgTimeAccessedDayCnt.getTwelvePmAvgCnt() + ","
                    + avgTimeAccessedDayCnt.getOnePmAvgCnt() + ","
                    + avgTimeAccessedDayCnt.getTwoPmAvgCnt() + ","
                    + avgTimeAccessedDayCnt.getThreePmAvgCnt() + ","
                    + avgTimeAccessedDayCnt.getFourPmAvgCnt() + ","
                    + avgTimeAccessedDayCnt.getFivePmAvgCnt() + ","
                    + avgTimeAccessedDayCnt.getSixPmAvgCnt() + ","
                    + avgTimeAccessedDayCnt.getSevenPmAvgCnt() + ","
                    + avgTimeAccessedDayCnt.getEightPmAvgCnt() + ","
                    + avgTimeAccessedDayCnt.getNinePmAvgCnt() + ","
                    + avgTimeAccessedDayCnt.getTenPmAvgCnt() + ","
                    + avgTimeAccessedDayCnt.getElevenPmAvgCnt() + ","
                    + avgTimeAccessedDayCnt.getTotalAvgCnt() + "\n");

            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
