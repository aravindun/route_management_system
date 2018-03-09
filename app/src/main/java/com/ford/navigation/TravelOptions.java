package com.ford.navigation;

public class TravelOptions {

    private String modeOfTransport;
    private String duration;

    public TravelOptions(String modeOfTransport, String duration) {
        this.modeOfTransport = modeOfTransport;
        this.duration = duration;
    }

    public String getModeOfTransport() {
        return modeOfTransport;
    }

    public String getDuration() {
        return duration;
    }
}
