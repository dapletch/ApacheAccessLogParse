package com.logparse.beans;

/**
 * Created by Seth on 11/27/2016.
 */
public class IpAddress {
    private String ipAddress;

    public IpAddress() {
        super();
    }

    public IpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    @Override
    public String toString() {
        return "IpAddress{" +
                "ipAddress='" + ipAddress + '\'' +
                '}';
    }
}
