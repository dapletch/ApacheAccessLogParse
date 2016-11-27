package com.logparse.geolocation;

import com.logparse.beans.ipaddresslocation.IpAddress;
import com.logparse.beans.ipaddresslocation.IpAddressLocation;
import com.logparse.utils.LogUtils;
import com.logparse.utils.OSUtils;
import com.maxmind.geoip.Location;
import com.maxmind.geoip.LookupService;
import com.maxmind.geoip.regionName;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Seth on 11/27/2016.
 */
public class GeoLocation {

    private File geoLiteDbFile = new File(OSUtils.getFilePathAboveCurrentOne() + "GeoLiteCity.dat");

    private Logger logger = Logger.getLogger(GeoLocation.class);

    private List<IpAddressLocation> ipAddressLocationList = new ArrayList<IpAddressLocation>();

    public List<IpAddressLocation> getLocation(List<IpAddress> distinctIpAddress) {

        if (!LogUtils.isFileValid(geoLiteDbFile)) {
            logger.error("The GeoLiteCity.dat file does not exist. Please check again to see if is in the correct directory.");
            System.exit(1);
        }

        try {
            LookupService lookup = new LookupService(geoLiteDbFile, LookupService.GEOIP_MEMORY_CACHE);

            for (IpAddress ipAddress: distinctIpAddress) {
                Location locationServices = lookup.getLocation(ipAddress.getIpAddress());

                IpAddressLocation ipAddressLocation = new IpAddressLocation();
                ipAddressLocation.setIpAddress(ipAddress.getIpAddress());
                ipAddressLocation.setCountryCode(locationServices.countryCode);
                ipAddressLocation.setCountryName(locationServices.countryName);
                ipAddressLocation.setRegion(locationServices.region);
                ipAddressLocation.setRegionName(regionName.regionNameByCode(locationServices.countryCode, locationServices.region));
                ipAddressLocation.setCity(locationServices.city);
                ipAddressLocation.setPostalCode(locationServices.postalCode);
                ipAddressLocation.setLatitude(locationServices.latitude);
                ipAddressLocation.setLongitude(locationServices.longitude);
                ipAddressLocation.setDmaCode(locationServices.dma_code);
                ipAddressLocation.setAreaCode(locationServices.area_code);
                ipAddressLocation.setMetroCode(locationServices.metro_code);
                ipAddressLocationList.add(ipAddressLocation);
            }

        } catch (IOException e) {
            logger.error("There was an error adding to the ipAddressLocationList: ", e);
        }
        return ipAddressLocationList;
    }
}
