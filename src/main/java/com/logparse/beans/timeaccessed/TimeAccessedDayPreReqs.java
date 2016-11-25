package com.logparse.beans.timeaccessed;

import org.joda.time.DateTime;


/**
 * Created by Seth on 11/23/2016.
 */
public class TimeAccessedDayPreReqs {

    private DateTime maxTimeEntered;
    private DateTime minTimeAccessed;
    private DateTime maxTimeAccessed;

    public TimeAccessedDayPreReqs() {
        super();
    }

    public TimeAccessedDayPreReqs(DateTime maxTimeEntered, DateTime minTimeAccessed, DateTime maxTimeAccessed) {
        this.maxTimeEntered = maxTimeEntered;
        this.minTimeAccessed = minTimeAccessed;
        this.maxTimeAccessed = maxTimeAccessed;
    }

    public DateTime getMaxTimeEntered() {
        return maxTimeEntered;
    }

    public void setMaxTimeEntered(DateTime maxTimeEntered) {
        this.maxTimeEntered = maxTimeEntered;
    }

    public DateTime getMinTimeAccessed() {
        return minTimeAccessed;
    }

    public void setMinTimeAccessed(DateTime minTimeAccessed) {
        this.minTimeAccessed = minTimeAccessed;
    }

    public DateTime getMaxTimeAccessed() {
        return maxTimeAccessed;
    }

    public void setMaxTimeAccessed(DateTime maxTimeAccessed) {
        this.maxTimeAccessed = maxTimeAccessed;
    }

    @Override
    public String toString() {
        return "TimeAccessedDayPreReqs{" +
                "maxTimeEntered=" + maxTimeEntered +
                ", minTimeAccessed=" + minTimeAccessed +
                ", maxTimeAccessed=" + maxTimeAccessed +
                '}';
    }
}
