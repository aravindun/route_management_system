package com.ford.navigation;
import java.sql.SQLException;

class ShortestPath
{
    private int V;

    private static final int NO_PARENT = -1;
    private static int i = 0;
    private static int nextHopNode = -1;

    public ShortestPath(int size) {
        V = size;
    }

    public static void dijkstra(int[][] adjacencyMatrix,
                                 int startVertex,
                                DataBaseHandler createDataBase,
                                String kpi) throws SQLException {
        int nVertices = adjacencyMatrix[0].length;

        int[] shortestDistances = new int[nVertices];

        boolean[] added = new boolean[nVertices];

        for (int vertexIndex = 0; vertexIndex < nVertices; vertexIndex++) {
            shortestDistances[vertexIndex] = Integer.MAX_VALUE;
            added[vertexIndex] = false;
        }

        shortestDistances[startVertex] = 0;

        int[] parents = new int[nVertices];

        parents[startVertex] = NO_PARENT;

        for (int i = 1; i < nVertices; i++) {

            int nearestVertex = -1;
            int shortestDistance = Integer.MAX_VALUE;
            for (int vertexIndex = 0; vertexIndex < nVertices; vertexIndex++) {
                if (!added[vertexIndex] &&
                        shortestDistances[vertexIndex] < shortestDistance) {
                    nearestVertex = vertexIndex;
                    shortestDistance = shortestDistances[vertexIndex];
                }
            }

            added[nearestVertex] = true;

            for (int vertexIndex = 0; vertexIndex < nVertices; vertexIndex++) {
                int edgeDistance = adjacencyMatrix[nearestVertex][vertexIndex];

                if (edgeDistance > 0
                        && ((shortestDistance + edgeDistance) <
                        shortestDistances[vertexIndex])) {
                    parents[vertexIndex] = nearestVertex;
                    shortestDistances[vertexIndex] = shortestDistance + edgeDistance;
                }
            }
        }
        printSolution(startVertex, shortestDistances, parents, createDataBase, kpi);
    }

    private static void printSolution(int startVertex,
                                      int[] distances,
                                      int[] parents,
                                      DataBaseHandler createDataBase,
                                      String kpi) throws SQLException {
        int nVertices = distances.length;
        //System.out.print("Vertex\t Distance\tPath");
        for (int vertexIndex = 0; vertexIndex < nVertices; vertexIndex++)
        {
            if (vertexIndex != startVertex)
            {
                //System.out.print("\n" + startVertex + " -> ");
//                System.out.print(vertexIndex + " \t\t ");
//                System.out.print(distances[vertexIndex] + "\t\t");
                //System.out.print("\n"+"NextHopNode" + nextHopNode);
                printPath(vertexIndex, parents);
                if (nextHopNode != -1) {
                    int neighborTableId = createDataBase.getNeighborTableID(startVertex + 1, nextHopNode, kpi);
                    int indexId = -1;
                    switch (kpi) {
                        case "time":
                            indexId = createDataBase.insertIntoIndexTable(vertexIndex + 1, neighborTableId, distances[vertexIndex], 0, 0);
                            break;
                        case "cost":
                            indexId = createDataBase.insertIntoIndexTable(vertexIndex + 1, neighborTableId, 0, distances[vertexIndex], 0);
                            break;
                        case "distance":
                            indexId = createDataBase.insertIntoIndexTable(vertexIndex + 1, neighborTableId, 0, 0, distances[vertexIndex]);
                            break;
                        default:
                            break;
                    }
                    if (indexId != -1) {
                        createDataBase.insertIntoRouting(indexId, kpi);
                    }
                }
            }
            i = 0;
        }
    }
    private static void printPath(int currentVertex,
                                  int[] parents) throws SQLException {
        if (currentVertex == NO_PARENT)
        {
            return;
        }
        printPath(parents[currentVertex], parents);

        if (i ==1) {
            nextHopNode =  currentVertex+1;
        }
        i++;
        System.out.print(currentVertex+" ");
    }
}