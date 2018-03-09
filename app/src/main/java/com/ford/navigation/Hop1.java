
package com.ford.navigation;
public class Hop1 {

    private int cost;
    private int distance;
    private int time;
    private String mode;
    private String source;
    private String neighbor;

    public Hop1(int time, int cost, int distance, String mode, String source, String neighbor) {
        this.time = time;
        this.distance = distance;
        this.cost = cost;
        this.mode = mode;
        this.source = source;
        this.neighbor = neighbor;
    }

    public int getCost() {
        return cost;
    }

    public int getDistance() {
        return distance;
    }

    public int getTime() {
        return time;
    }

    public String getMode() {
        return mode;
    }

    public String getSource() {
        return source;
    }

    public String getNeighbor() {
        return neighbor;
    }
}
