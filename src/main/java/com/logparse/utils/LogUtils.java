package com.logparse.utils;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Seth on 11/24/2016.
 */
public class LogUtils {

    public static DateTime formatLogDate(String dateStr) {
        return DateTime.parse(dateStr, DateTimeFormat.forPattern("dd/MMM/yyyy:HH:mm:ss Z")).toDateTime();
    }

    public static DateTime sqlDateToDateTime(String dateStr) {
        return DateTime.parse(dateStr, DateTimeFormat.forPattern("yyyy-MM-dd")).toDateTime();
    }

    public static java.sql.Date dateTimeToSqlDate(DateTime date) {
        return new java.sql.Date(date.getMillis());
    }

    public static Boolean isDateStringValid(String dateStr) {
        return dateStr != null;
    }

    public static String generateFileTimeStamp() {
        return new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date());
    }
}
