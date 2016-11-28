package com.logparse.beans.ipaddresslocation;

import java.sql.Timestamp;

/**
 * Created by Seth on 11/27/2016.
 */
public class IpAddressLocation {

    private String ipAddress;
    private String countryCode;
    private String countryName;
    private String region;
    private String regionName;
    private String city;
    private String postalCode;
    private Float latitude;
    private Float longitude;
    private Integer dmaCode;
    private Integer areaCode;
    private Integer metroCode;
    private Integer totalCnt;

    public IpAddressLocation() {
        super();
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public Integer getDmaCode() {
        return dmaCode;
    }

    public void setDmaCode(Integer dmaCode) {
        this.dmaCode = dmaCode;
    }

    public Integer getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(Integer areaCode) {
        this.areaCode = areaCode;
    }

    public Integer getMetroCode() {
        return metroCode;
    }

    public void setMetroCode(Integer metroCode) {
        this.metroCode = metroCode;
    }

    public Integer getTotalCnt() {
        return totalCnt;
    }

    public void setTotalCnt(Integer totalCnt) {
        this.totalCnt = totalCnt;
    }

    @Override
    public String toString() {
        return "IpAddressLocation{" +
                "ipAddress='" + ipAddress + '\'' +
                ", countryCode='" + countryCode + '\'' +
                ", countryName='" + countryName + '\'' +
                ", region='" + region + '\'' +
                ", regionName='" + regionName + '\'' +
                ", city='" + city + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", dmaCode=" + dmaCode +
                ", areaCode=" + areaCode +
                ", metroCode=" + metroCode +
                ", totalCnt=" + totalCnt +
                '}';
    }
}
