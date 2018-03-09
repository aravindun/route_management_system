package com.ford.navigation;

public class HopPoints {
    private String source;
    private String neightbour;
    private String mode;

    public HopPoints(String source, String neightbour, String mode) {
        this.source = source;
        this.neightbour = neightbour;
        this.mode = mode;
    }

    public String getSource() {
        return source;
    }

    public String getNeightbour() {
        return neightbour;
    }

    public String getMode() {
        return mode;
    }
}
