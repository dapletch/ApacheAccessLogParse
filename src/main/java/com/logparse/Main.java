package com.logparse;

import com.logparse.bean.LogRecord;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {
        File file = new File(args[0]);
        ParseLog parseLog = new ParseLog();

        testRegex();

        if (!isFileValid(file)) {
            System.out.println("Please enter a valid file to be parsed, and try again.");
            System.exit(0);
        }
        System.out.println("A valid file has been inputted to be parsed.");
        LogRecord logRecord = new LogRecord(file);
        System.out.println("The file to be parsed: " + logRecord.getFile());
        parseLog.parseApacheLogFile(logRecord);
    }

    private static Boolean isFileValid(File file) {
        if (file.exists()
                && !file.isDirectory()
                && file.isFile()) {
            return true;
        }
        return false;
    }

    private static void testRegex() {
        String txt="69.171.228.119 - pletcher [03/Jun/2016:18:53:20 -0400] \"GET /manager/html HTTP/1.1\" 200 17675";

        String re1="((?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?))(?![\\d])";	// IPv4 IP Address 1
        String re2=".*?";	// Non-greedy match on filler
        String re3="((?:[a-z][a-z0-9_]*))";	// Variable Name 1
        String re4=".*?";	// Non-greedy match on filler
        String re5="(\\[.*?\\])";	// Square Braces 1
        String re6=".*?";	// Non-greedy match on filler
        String re7="(\".*?\")";	// Double Quote String 1
        String re8=".*?";	// Non-greedy match on filler
        String re9="(\\d+)";	// Integer Number 1
        String re10=".*?";	// Non-greedy match on filler
        String re11="(\\d+)";	// Integer Number 2

        Pattern p = Pattern.compile(re1+re2+re3+re4+re5+re6+re7+re8+re9+re10+re11,Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
        Matcher m = p.matcher(txt);
        if (m.find())
        {
            String ipaddress1=m.group(1);
            String var1=m.group(2);
            String sbraces1=m.group(3);
            String string1=m.group(4);
            String int1=m.group(5);
            String int2=m.group(6);
            System.out.print("("+ipaddress1.toString()+")"+"("+var1.toString()+")"+"("+sbraces1.toString()+")"+"("+string1.toString()+")"+"("+int1.toString()+")"+"("+int2.toString()+")"+"\n");
        }
    }
}
