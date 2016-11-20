package com.logparse.bean;

import org.joda.time.DateTime;

import java.io.File;

/**
 * Created by Seth on 11/17/2016.
 */
public class LogRecord {

    private File file;
    private String ipAddress;
    private String remoteUser;
    private String authenticatedUser;
    private String request;
    private Integer statCode;
    private Integer bytesSent;
    private DateTime timeEntered;

    public LogRecord() {
        super();
    }

    public LogRecord(File file) {
        this.file = file;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getRemoteUser() {
        return remoteUser;
    }

    public void setRemoteUser(String remoteUser) {
        this.remoteUser = remoteUser;
    }

    public String getAuthenticatedUser() {
        return authenticatedUser;
    }

    public void setAuthenticatedUser(String authenticatedUser) {
        this.authenticatedUser = authenticatedUser;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public Integer getStatCode() {
        return statCode;
    }

    public void setStatCode(Integer statCode) {
        this.statCode = statCode;
    }

    public Integer getBytesSent() {
        return bytesSent;
    }

    public void setBytesSent(Integer bytesSent) {
        this.bytesSent = bytesSent;
    }

    public DateTime getTimeEntered() {
        return timeEntered;
    }

    public void setTimeEntered(DateTime timeEntered) {
        this.timeEntered = timeEntered;
    }

}
