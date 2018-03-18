package com.ford.navigation;

public class Route implements Comparable<Route> {

    private Node one, two;
    private int kpi;
    private int modeID;

    public Route(Node one, Node two){
        this(one, two, 1, 1);
    }

    public Route(Node one, Node two, int kpi, int modeID){
        this.one = (one.getSourceId().compareTo(two.getSourceId()) <= 0) ? one : two;
        this.two = (this.one == one) ? two : one;
        this.kpi = kpi;
        this.modeID = modeID;
    }


    public Node getNeighbor(Node current){
        if(!(current.equals(one) || current.equals(two))){
            return null;
        }

        return (current.equals(one)) ? two : one;
    }

    public Node getOne(){
        return this.one;
    }

    public Node getTwo(){
        return this.two;
    }

    public int getWeight(){
        return this.kpi;
    }

    public void setWeight(int kpi){
        this.kpi = kpi;
    }

    public int getModeID(){
        return this.modeID;
    }

    public void setModeID(int modeID){
        this.modeID = modeID;
    }

    public int compareTo(Route other){
        return this.kpi - other.kpi;
    }

    public String toString(){
        return "({" + one + ", " + two + "}, " + kpi + ")";
    }

    public int hashCode(){
        return (one.getSourceId().toString() + two.getSourceId().toString() + kpi).hashCode();
    }

    public boolean equals(Object other){
        if(!(other instanceof Route)){
            return false;
        }

        Route e = (Route)other;

        return e.one.equals(this.one) && e.two.equals(this.two);
    }
}
