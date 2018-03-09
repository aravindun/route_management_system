package com.ford.navigation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class MapGenerator {

    private HashMap<Integer, Node> nodes;
    private HashMap<Integer, Route> routes;

    public MapGenerator(){
        this.nodes = new HashMap<Integer, Node>();
        this.routes = new HashMap<Integer, Route>();
    }

    public MapGenerator(ArrayList<Node> nodes){
        this.nodes = new HashMap<Integer, Node>();
        this.routes = new HashMap<Integer, Route>();

        for(Node v: nodes){
            this.nodes.put(v.getSourceId(), v);
        }
    }

    public boolean addRoute(Node one, Node two){
        return addRoute(one, two, 1, 1);
    }

    public boolean addRoute(Node one, Node two, int weight, int modeID){
        if(one.equals(two)){
            return false;
        }

        //ensures the Route is not in the Graph
        Route e = new Route(one, two, weight, modeID);
        if(routes.containsKey(e.hashCode())){
            return false;
        }

        //and that the Route isn't already incident to one of the nodes
        else if(one.containsNeighbor(e) || two.containsNeighbor(e)){
            return false;
        }

        routes.put(e.hashCode(), e);
        one.addNeighbor(e);
        //two.addNeighbor(e);
        return true;
    }

    public boolean containsRoute(Route e){
        if(e.getOne() == null || e.getTwo() == null){
            return false;
        }

        return this.routes.containsKey(e.hashCode());
    }

    public Route removeRoute(Route e){
        e.getOne().removeNeighbor(e);
        e.getTwo().removeNeighbor(e);
        return this.routes.remove(e.hashCode());
    }

    public boolean containsNode(Node vertex){
        return this.nodes.get(vertex.getSourceId()) != null;
    }

    public Node getNode(Integer label){
        return nodes.get(label);
    }

    public boolean addNode(Node vertex, boolean overwriteExisting){
        Node current = this.nodes.get(vertex.getSourceId());
        if(current != null){
            if(!overwriteExisting){
                return false;
            }

            while(current.getNeighborCount() > 0){
                this.removeRoute(current.getNeighbor(0));
            }
        }


        nodes.put(vertex.getSourceId(), vertex);
        return true;
    }

    public Node removeNode(String label){
        Node v = nodes.remove(label);

        while(v.getNeighborCount() > 0){
            this.removeRoute(v.getNeighbor((0)));
        }

        return v;
    }

    public Set<Integer> vertexKeys(){
        return this.nodes.keySet();
    }

    public Set<Route> getRoutes(){
        return new HashSet<Route>(this.routes.values());
    }

    public Set<Node> getNodes(){
        return new HashSet<Node>(this.nodes.values());
    }

}