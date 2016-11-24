package com.logparse.parse;

import com.logparse.beans.LogRecord;
import com.logparse.dao.InsertLog;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Seth on 11/20/2016.
 */
public class ParseLog {

    private File file;
    private DateTime timeEntered;

    public ParseLog(File file, DateTime timeEntered) {
        this.file = file;
        this.timeEntered = timeEntered;
    }

    private InsertLog insertLog = new InsertLog();

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

    public void parseApacheLogFileWriteToDb(ParseLog parseLog) throws SQLException, ClassNotFoundException {
        logRecords = addLogRecordsToList(parseLog);
        insertLog.writeLogToDb(logRecords);
    }

    private List<LogRecord> addLogRecordsToList(ParseLog parseLog) {

        try {
            BufferedReader br = new BufferedReader(new FileReader(parseLog.getFile()));
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
                    logRecord.setTimeEntered(parseLog.getTimeEntered());
                    logRecords.add(logRecord);
                    //logger.info("Log Record no remote user: " + logRecord.toString());
                } else {
                    logRecord = logRecordRemoteUserIncluded(logRecord, parseLog.getTimeEntered(), line);
                    if (logRecord != null) {
                        logRecords.add(logRecord);
                        //logger.info("Log Record with remote user: " + logRecord.toString());
                    }
                }
            }
        } catch (FileNotFoundException e) {
            logger.error("Error : ", e);
        } catch (IOException e) {
            logger.error("Error: ", e);
        }
        return logRecords;
    }

    private LogRecord logRecordRemoteUserIncluded(LogRecord logRecord, DateTime timeEntered, String line) {
        Pattern p = Pattern.compile(regexRemoteUserIncluded, Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
        Matcher m = p.matcher(line);
        if (m.find()) {
            logRecord.setIpAddress(m.group(1));
            logRecord.setRemoteUser(m.group(2));
            logRecord.setTimeAccessed(formatDate(m.group(3).substring(1, 27)));
            logRecord.setRequest(m.group(4));
            logRecord.setStatCode(Integer.parseInt(m.group(5)));
            logRecord.setBytesSent(Integer.parseInt(m.group(6)));
            logRecord.setTimeEntered(timeEntered);
            return logRecord;
        }
        return null;
    }

    private DateTime formatDate(String dateStr) {
        return DateTime.parse(dateStr, DateTimeFormat.forPattern("dd/MMM/yyyy:HH:mm:ss Z")).toDateTime();
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public DateTime getTimeEntered() {
        return timeEntered;
    }

    public void setTimeEntered(DateTime timeEntered) {
        this.timeEntered = timeEntered;
    }
}
