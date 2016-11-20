package com.logparse;

import com.logparse.bean.LogRecord;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggerFactory;

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

    private String re1 = "((?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?))(?![\\d])";    // IPv4 IP Address 1
    private String re2 = ".*?";    // Non-greedy match on filler
    private String re3 = "((?:[a-z][a-z0-9_]*))";    // Variable Name 1
    private String re4 = ".*?";    // Non-greedy match on filler
    private String re5 = "(\\[.*?\\])";    // Square Braces 1
    private String re6 = ".*?";    // Non-greedy match on filler
    private String re7 = "(\".*?\")";    // Double Quote String 1
    private String re8 = ".*?";    // Non-greedy match on filler
    private String re9 = "(\\d+)";    // Integer Number 1
    private String re10 = ".*?";    // Non-greedy match on filler
    private String re11 = "(\\d+)";    // Integer Number 2

    public void parseApacheLogFile(LogRecord logRecord) {
        logRecords = addLogRecordsToList(logRecord.getFile());
    }

    private List<LogRecord> addLogRecordsToList(File file) {

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line = null;

            Pattern p = Pattern.compile(re1 + re2 + re3 + re4 + re5 + re6 + re7 + re8 + re9 + re10 + re11, Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
            while ((line = br.readLine()) != null) {
                Matcher m = p.matcher(line);
                LogRecord logRecord = new LogRecord();
                if (m.find()) {
                    logRecord.setIpAddress(m.group(1));
                    logRecord.setRemoteUser(m.group(2));
                    logRecord.setAuthenticatedUser(m.group(3));
                    logRecord.setRequest(m.group(4));
                    logRecord.setStatCode(Integer.parseInt(m.group(5)));
                    logRecord.setBytesSent(Integer.parseInt(m.group(6)));
                    logRecords.add(logRecord);
                    logger.info(logRecord.toString());
                } else {
                    logger.error("Regex failed to parse log record.");
                }
            }
        } catch (FileNotFoundException e) {
            logger.error("Error :", e);
        } catch (IOException e) {
            logger.error("Error: ", e);
        }
        return logRecords;
    }
}
