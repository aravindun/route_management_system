package com.ford.navigation;
import java.util.ArrayList;

public class Node {

    private Integer sourceId;
    private ArrayList<Route> neighborhood;

    public Node(int sourceId) {
        this.sourceId = sourceId;
        this.neighborhood = new ArrayList<Route>();
    }

    public Integer getSourceId() {
        return sourceId;
    }

    public void addNeighbor(Route edge){
        if(this.neighborhood.contains(edge)){
            return;
        }

        this.neighborhood.add(edge);
    }

    public boolean containsNeighbor(Route other){
        return this.neighborhood.contains(other);
    }

    public Route getNeighbor(int index){
        return this.neighborhood.get(index);
    }

    Route removeNeighbor(int index){
        return this.neighborhood.remove(index);
    }

    public void removeNeighbor(Route e){
        this.neighborhood.remove(e);
    }

    public int getNeighborCount(){
        return this.neighborhood.size();
    }

    public String toString(){
        return "Route " + sourceId;
    }

    public int hashCode(){
        return this.sourceId.hashCode();
    }

    public boolean equals(Object other){
        if(!(other instanceof Route)){
            return false;
        }

        Node v = (Node) other;
        return this.sourceId.equals(v.getSourceId());
    }

    public ArrayList<Route> getNeighbors(){
        return new ArrayList<Route>(this.neighborhood);
    }
}