package com.logparse;

import com.logparse.parse.ParseLog;
import com.logparse.reports.RunReports;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;

import java.io.File;
import java.sql.SQLException;

public class Main {

    private static Logger logger = Logger.getLogger(ParseLog.class);

    private static final String errorMessage = "Please enter a valid file to be parsed, and try again.";

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        if (args.length == 1) {
            File file = new File(args[0]);

            if (!isFileValid(file)) {
                logger.error(errorMessage);
                System.exit(0);
            }

            ParseLog parseLog = new ParseLog(file, DateTime.now().toDateTime());
            logger.info("A valid file has been inputted to be parsed.");
            logger.info("The file to be parsed: " + parseLog.getFile());
            logger.info("Time stamp for log records: " + parseLog.getTimeEntered());
            parseLog.parseApacheLogFileWriteToDb(parseLog);
            logger.info("Data Import Completed.");

            logger.info("Starting reporting process now.");
            RunReports runReports = new RunReports();
            runReports.getPreReqsRunReports();
            logger.info("Report has been generated for viewing.");

        } else {
            logger.error(errorMessage);
            System.exit(0);
        }
    }

    private static Boolean isFileValid(File file) {
        if (file.exists()
                && !file.isDirectory()
                && file.isFile()) {
            return true;
        }
        return false;
    }
}
