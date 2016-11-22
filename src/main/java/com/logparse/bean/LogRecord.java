package com.logparse.bean;

import org.joda.time.DateTime;

import java.io.File;

/**
 * Created by Seth on 11/17/2016.
 */
public class LogRecord {

    private String ipAddress;
    private String remoteUser;
    private String request;
    private Integer statCode;
    private Integer bytesSent;
    private DateTime timeAccessed;
    private DateTime timeEntered;

    public LogRecord() {
        super();
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

    public DateTime getTimeAccessed() {
        return timeAccessed;
    }

    public void setTimeAccessed(DateTime timeAccessed) {
        this.timeAccessed = timeAccessed;
    }

    @Override
    public String toString() {
        return "LogRecord{" +
                "ipAddress='" + ipAddress + '\'' +
                ", remoteUser='" + remoteUser + '\'' +
                ", request='" + request + '\'' +
                ", statCode=" + statCode +
                ", bytesSent=" + bytesSent +
                ", timeAccessed=" + timeAccessed +
                ", timeEntered=" + timeEntered +
                '}';
    }
}
