package com.logparse.beans.ipaddresslocation;

/**
 * Created by Seth on 11/29/2016.
 */
public class IpAddressCntByCntry {

    private String countryName;
    private String countryCode;
    private String regionName;
    private String region;
    private String city;
    private Integer distinctIpCnt;

    public IpAddressCntByCntry() {
        super();
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getDistinctIpCnt() {
        return distinctIpCnt;
    }

    public void setDistinctIpCnt(Integer distinctIpCnt) {
        this.distinctIpCnt = distinctIpCnt;
    }

    @Override
    public String toString() {
        return "IpAddressCntByCntry{" +
                "countryName='" + countryName + '\'' +
                ", countryCode='" + countryCode + '\'' +
                ", regionName='" + regionName + '\'' +
                ", region='" + region + '\'' +
                ", city='" + city + '\'' +
                ", distinctIpCnt=" + distinctIpCnt +
                '}';
    }
}
