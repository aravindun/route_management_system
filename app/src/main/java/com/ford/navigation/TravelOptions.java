package com.ford.navigation;

import java.util.List;

public class TravelOptions {

    private String modeOfTransport;
    private String duration;
    private String hopsDetail;
    private List<Hop1> hops;
    private String hopsCommaSeparated;
    private String modesCommaSeparated;

    public TravelOptions(String modeOfTransport, String duration, String hopsDetail, List<Hop1> hops, String hopsCommaSeparated, String modesCommaSeparated) {
        this.modeOfTransport = modeOfTransport;
        this.duration = duration;
        this.hopsDetail = hopsDetail;
        this.hops = hops;
        this.hopsCommaSeparated = hopsCommaSeparated;
        this.modesCommaSeparated = modesCommaSeparated;
    }

    public String getModeOfTransport() {
        return modeOfTransport;
    }

    public String getDuration() {
        return duration;
    }

    public String getHopsDetail() {
        return hopsDetail;
    }

    public List<Hop1> getHops() {
        return hops;
    }

    public String getHopsCommaSeparated() {
        return hopsCommaSeparated;
    }

    public String getModesCommaSeparated() {
        return modesCommaSeparated;
    }
}
