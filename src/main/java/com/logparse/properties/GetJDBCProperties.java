package com.logparse.properties;

import com.logparse.beans.properties.JDBCProperties;
import com.logparse.utils.OSUtils;
import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by Seth on 11/22/2016.
 */
public class GetJDBCProperties {

    private Logger logger = Logger.getLogger(GetJDBCProperties.class);

    public JDBCProperties loadJdbcProperties() {

        Properties prop = new Properties();
        try {
            // Get the directory that the jdbc.properties file currently resides in
            FileInputStream in = new FileInputStream(OSUtils.getFilePathAboveCurrentOne() + "jdbc.properties");
            prop.load(in);
            in.close();
            JDBCProperties jdbcProperties = new JDBCProperties(prop.getProperty("jdbc.driver")
                    , prop.getProperty("jdbc.url")
                    , prop.getProperty("jdbc.username")
                    , prop.getProperty("jdbc.password"));

            if (!isJdbcPropertiesValid(jdbcProperties)) {
                logger.error("The JDBC properties is either not formatted correctly or does not exist.\n"
                        + "Please rectify the issue and try again.");
                System.exit(0);
            }
            return jdbcProperties;
        } catch (FileNotFoundException e) {
            logger.error("File Not Found Exception: \n", e);
        } catch (IOException e) {
            logger.error("File was unable to be read: \n", e);
        }
        return null;
    }

    private Boolean isJdbcPropertiesValid(JDBCProperties jdbcProperties) {
        if (jdbcProperties.getClassName() != null
                || jdbcProperties.getUrl() != null
                || jdbcProperties.getUsername() != null
                || jdbcProperties.getPassword() != null) {
            logger.info("All properties jdbc properties are valid: \n" + jdbcProperties.toString());
            return true;
        }
        return false;
    }
}
