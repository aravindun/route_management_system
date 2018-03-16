package com.ford.navigation;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JourneyMapper {
    private DataBaseHandler dataBaseHandler;

    public JourneyMapper(DataBaseHandler dataBaseHandler) {
        this.dataBaseHandler = dataBaseHandler;
    }


//    public void readCsvFileAndPopulateDB(InputStream inputStream) {
//        BufferedReader br = null;
//        String line = "";
//        try {
//            dataBaseHandler.insertModeTable();
//            br = new BufferedReader(new InputStreamReader(inputStream));
//            br.readLine();
//            while ((line = br.readLine()) != null) {
//
//                String[] data = line.split(",");
//                int sourceID = dataBaseHandler.getSourceID(data[0]);
//                int neighborID = dataBaseHandler.getSourceID(data[1]);
//                int modeID = dataBaseHandler.getModeID(data[2]);
//                if (sourceID == -1) {
//                    sourceID = dataBaseHandler.insertIntoSourceTable(data[0]);
//                    mapGeneratorTime.addNode(new Node(sourceID), true);
//                    mapGeneratorCost.addNode(new Node(sourceID), true);
//                    mapGeneratorDistance.addNode(new Node(sourceID), true);
//                }
//
//                if (neighborID == -1) {
//                    neighborID = dataBaseHandler.insertIntoSourceTable(data[1]);
//                    mapGeneratorTime.addNode(new Node(neighborID), true);
//                    mapGeneratorCost.addNode(new Node(neighborID), true);
//                    mapGeneratorDistance.addNode(new Node(neighborID), true);
//                }
//
//                int time = Integer.parseInt(data[3]);
//                int neighborPrimaryKey = dataBaseHandler.insertIntoNeighborTable(sourceID, neighborID, modeID, time, Integer.parseInt(data[4]), Integer.parseInt(data[5]));
//                dataBaseHandler.insertIntoIndexTable(neighborID, neighborPrimaryKey, time, Integer.parseInt(data[4]), Integer.parseInt(data[5]));
//                //dataBaseHandler.insertIntoRouting(indexPrimaryKey);
//            }
//
//            int graph[][] = dataBaseHandler.insertIntoRouting(mapGeneratorTime, "time");
//            int graph1[][] = dataBaseHandler.insertIntoRouting(mapGeneratorCost, "cost");
//            int graph2[][] = dataBaseHandler.insertIntoRouting(mapGeneratorDistance, "distance");
//
//            for (int i = 0; i< dataBaseHandler.getMaxSourceID(); i++) {
//                ShortestPath.dijkstra(graph, i, dataBaseHandler, "time");
//                ShortestPath.dijkstra(graph1, i, dataBaseHandler, "cost");
//                ShortestPath.dijkstra(graph2, i, dataBaseHandler, "distance");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (br != null) {
//                try {
//                    br.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }

    public FinalJourney getJourney(String source, String destination, KPI kpi) {
        int sourceId = dataBaseHandler.getSourceID1(source.toLowerCase());
        int destinationId = dataBaseHandler.getSourceID1(destination.toLowerCase());
        switch (kpi) {
            case TIME:
                return getJourney(sourceId, destinationId, "time");
            case COST:
                return getJourney(sourceId, destinationId, "cost");
            case DISTANCE:
                return getJourney(sourceId, destinationId, "distance");
            default:
                return null;
        }
    }

    private FinalJourney getJourney(int sourceId, int destinationId, String kpi) {
        int totaltime = 0;
        int totalCost = 0;
        int totalDistance = 0;
        ArrayList<Hop1> hops = new ArrayList<>();
        int nextHopId = sourceId;
        while (true) {
            if (nextHopId == destinationId) {
                break;
            }
            Hop1 hop1 = dataBaseHandler.getNextHopNode(nextHopId, destinationId, kpi);
            if (hop1 != null) {
                hops.add(hop1);
                totaltime += hop1.getTime();
                totalCost += hop1.getCost();
                totalDistance += hop1.getDistance();
                nextHopId = dataBaseHandler.getSourceID1(hop1.getNeighbor());
            }
        }
        return new FinalJourney(totaltime, totalCost, totalDistance, kpi, hops);
    }

    enum KPI {
        TIME,
        COST,
        DISTANCE
    }
//    private static Timestamp getConvertedTime(String s) {
//        int hours = Integer.parseInt(s) / 3600;
//        int remainder = Integer.parseInt(s) - (hours * 3600);
//        int mins = remainder / 60;
//        remainder = remainder - mins * 60;
//        int secs = remainder;
//        return new Timestamp(hours, mins, secs);
//    }
}
