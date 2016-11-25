package com.logparse.utils;

import org.apache.log4j.Logger;

/**
 * Created by Seth on 11/23/2016.
 */
public class OSUtils {

    private static String OS = System.getProperty("os.name").toLowerCase();

    private static String workingDirectory = System.getProperty("user.dir");

    private static boolean isWindows() {
        return (OS.contains("win"));
    }

    private static Logger logger = Logger.getLogger(OSUtils.class);

    public static String getFilePathAboveCurrentOne() {

        Integer index;

        if (isWindows()) {
            logger.info("Operating System: " + OS);
            index = workingDirectory.lastIndexOf("\\");
            return workingDirectory.substring(0, index) + "\\";
        } else {
            // Will work other operating systems, such as Unix/Linux, Mac, and Solaris
            logger.info("Operating System: " + OS);
            return workingDirectory + "/";
        }
    }
}
