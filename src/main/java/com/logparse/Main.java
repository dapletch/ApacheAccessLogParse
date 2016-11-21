package com.logparse;

import com.logparse.bean.LogRecord;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    private static final String errorMessage = "Please enter a valid file to be parsed, and try again.";

    public static void main(String[] args) {
        if (args.length == 1) {
            File file = new File(args[0]);
            ParseLog parseLog = new ParseLog();

            if (!isFileValid(file)) {
                System.out.println(errorMessage);
                System.exit(0);
            }
            System.out.println("A valid file has been inputted to be parsed.");
            LogRecord logRecord = new LogRecord(file);
            System.out.println("The file to be parsed: " + logRecord.getFile());
            parseLog.parseApacheLogFile(logRecord);
        } else {
            System.out.println(errorMessage);
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
