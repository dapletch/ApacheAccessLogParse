package com.logparse;

import com.logparse.bean.LogRecord;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Seth on 11/20/2016.
 */
public class ParseLog {

    private Logger logger = Logger.getLogger(ParseLog.class);

    private List<LogRecord> logRecords = new ArrayList<LogRecord>();

    private String regexNoRemoteUser = "((?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?))(?![\\d])" // IPv4 IP Address 1
            + ".*?" // Non-greedy match on filler
            + "(\\[.*?\\])" // Square Braces 1
            + ".*?" // Non-greedy match on filler
            + "(\".*?\")" // Double Quote String 1
            + ".*?" // Non-greedy match on filler
            + "(\\d+)" // Integer Number 1
            + ".*?" // Non-greedy match on filler
            + "(\\d+)"; // Integer Number 2

    private String regexRemoteUserIncluded ="((?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?))(?![\\d])"	// IPv4 IP Address 1
            + ".*?"	// Non-greedy match on filler
            + "((?:[a-z][a-z]+))"	// Word 1
            + ".*?"	// Non-greedy match on filler
            + "(\\[.*?\\])"	// Square Braces 1
            + ".*?"	// Non-greedy match on filler
            + "(\".*?\")"	// Double Quote String 1
            + ".*?"	// Non-greedy match on filler
            + "(\\d+)"	// Integer Number 1
            + ".*?"	// Non-greedy match on filler
            + "(\\d+)";	// Integer Number 2


    public void parseApacheLogFile(LogRecord logRecord) {
        logRecords = addLogRecordsToList(logRecord.getFile());
    }

    private List<LogRecord> addLogRecordsToList(File file) {

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line = null;

            Pattern p = Pattern.compile(regexNoRemoteUser, Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
            while ((line = br.readLine()) != null) {
                LogRecord logRecord = new LogRecord();
                Matcher m = p.matcher(line);
                if (m.find()) {
                    logRecord.setIpAddress(m.group(1));
                    logRecord.setTimeAccessed(formatDate(m.group(2).substring(1, 27)));
                    logRecord.setRequest(m.group(3));
                    logRecord.setStatCode(Integer.parseInt(m.group(4)));
                    logRecord.setBytesSent(Integer.parseInt(m.group(5)));
                    logRecords.add(logRecord);
                    logger.info("Log Record no remote user: " + logRecord.toString());
                } else {
                    logRecord = logRecordRemoteUserIncluded(logRecord, line);
                    if (logRecord != null) {
                        logRecords.add(logRecord);
                        logger.info("Log Record with remote user: " + logRecord.toString());
                    }
                }
            }
        } catch (FileNotFoundException e) {
            logger.error("Error :", e);
        } catch (IOException e) {
            logger.error("Error: ", e);
        }
        return logRecords;
    }

    private LogRecord logRecordRemoteUserIncluded(LogRecord logRecord, String line) {
        Pattern p = Pattern.compile(regexRemoteUserIncluded, Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
        Matcher m = p.matcher(line);
        if (m.find()) {
            logRecord.setIpAddress(m.group(1));
            logRecord.setRemoteUser(m.group(2));
            logRecord.setTimeAccessed(formatDate(m.group(3).substring(1, 27)));
            logRecord.setRequest(m.group(4));
            logRecord.setStatCode(Integer.parseInt(m.group(5)));
            logRecord.setBytesSent(Integer.parseInt(m.group(6)));
            return logRecord;
        }
        return null;
    }

    private DateTime formatDate(String dateStr) {
        return DateTime.parse(dateStr, DateTimeFormat.forPattern("dd/MMM/yyyy:HH:mm:ss Z"));
    }
}
