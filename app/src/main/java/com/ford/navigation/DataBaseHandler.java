package com.ford.navigation;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class DataBaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    private SQLiteDatabase db;
    private Context context;
    private static int size;
    // Database Name
    private static final String DATABASE_NAME = "fordsmartmobility";

    public DataBaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
        this.getWritableDatabase();
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_MODE_TABLLE = "CREATE TABLE MODE(id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT)";
        String CREATE_SOURCE_TABLE = "CREATE TABLE SOURCE(id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT)";
        String CREATE_NEIGHBOR_TABLE = "CREATE TABLE NEIGHBOR(id INTEGER PRIMARY KEY AUTOINCREMENT, sourceID INTEGER, neighborID INTEGER, modeID INTEGER, TIME INTEGER , cost INTEGER, distance INTEGER," +
                " FOREIGN KEY (sourceID) references SOURCE(id), FOREIGN KEY (neighborID) REFERENCES SOURCE(id), FOREIGN KEY (modeID) REFERENCES MODE(id))";
        String CREATE_INDEX_TABLE = "CREATE TABLE INDEX1(id INTEGER PRIMARY KEY AUTOINCREMENT, destinationID INTEGER, neighborTableID INTEGER, totalTime INTEGER, totalCost INTEGER, totalDistance INTEGER," +
                "  FOREIGN KEY (destinationID) REFERENCES SOURCE(id), FOREIGN KEY (neighborTableID) REFERENCES NEIGHBOR(id))";
        String CREATE_ROUTING_TABLE = "CREATE TABLE ROUTING(id INTEGER PRIMARY KEY AUTOINCREMENT, indexID INTEGER, kpi VARCHAR(255)," +
                " FOREIGN KEY (indexID) REFERENCES INDEX1(id))";

        db.execSQL(CREATE_MODE_TABLLE);
        db.execSQL(CREATE_SOURCE_TABLE);
        db.execSQL(CREATE_NEIGHBOR_TABLE);
        db.execSQL(CREATE_INDEX_TABLE);
        db.execSQL(CREATE_ROUTING_TABLE);
        this.db = db;
        insertModeTable();
        readCsvFileAndPopulateDB(context.getResources().openRawResource(R.raw.input));
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public int insertIntoSourceTable(String source) {

        ContentValues values = new ContentValues();
        values.put("name", source);

        db.insert("SOURCE", null, values);
        return getSourceID(source);
    }

    public int insertIntoNeighborTable(int sourceID, int neighborID, int modeID, int time, int cost, int distance) {

        ContentValues values = new ContentValues();
        values.put("sourceID", sourceID);
        values.put("neighborID", neighborID);
        values.put("modeID", modeID);
        values.put("time", time);
        values.put("cost", cost);
        values.put("distance", distance);

        db.insert("NEIGHBOR", null, values);
        return getNeighborID();
    }

    public int insertIntoIndexTable(int destinationID, int neighborTableID, int totalTime, int totalCost, int totalDistance) {

        ContentValues values = new ContentValues();
        values.put("destinationID", destinationID);
        values.put("neighborTableID", neighborTableID);
        values.put("totalTime", totalTime);
        values.put("totalCost", totalCost);
        values.put("totalDistance", totalDistance);

        db.insert("INDEX1", null, values);
        return getIndexID();
    }

    public int[][] insertIntoRouting(String kpi1) {
        int size = getMaxSourceID();
        int graph[][] = new int[size][size];

        String selectQuery = "select sourceID, neighborID, min("+ kpi1 +") from neighbor group by NEIGHBORID, sourceid";
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                graph[cursor.getInt(0) - 1][cursor.getInt(1) - 1] = cursor.getInt(2);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return graph;
    }


    public void insertIntoRouting(int indexId, String kpi) {

        ContentValues values = new ContentValues();
        values.put("indexID", indexId);
        values.put("kpi", kpi);

        db.insert("ROUTING", null, values);
    }


    public int getModeID(String mode) {
        String selectQuery = "SELECT id FROM MODE where name = ?";
        Cursor cursor = db.rawQuery(selectQuery, new String[] {mode});

        if (cursor.moveToFirst()) {
            return cursor.getInt(0);
        }
        cursor.close();
        return -1;
    }

    public String getMode(int modeID) {
        String selectQuery = "SELECT name FROM MODE where id = ?";
        Cursor cursor = db.rawQuery(selectQuery, new String[] {String.valueOf(modeID)});

        if (cursor.moveToFirst()) {
            return cursor.getString(0);
        }
        cursor.close();
        return "";
    }

    public String getMode1(int modeID) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT name FROM MODE where id = ?";
        Cursor cursor = db.rawQuery(selectQuery, new String[] {String.valueOf(modeID)});

        if (cursor.moveToFirst()) {
            return cursor.getString(0);
        }
        cursor.close();
        return "";
    }

    public int getSourceID(String source) {
        String selectQuery = "SELECT id FROM SOURCE where name = ?";
        Cursor cursor = db.rawQuery(selectQuery, new String[] {source});

        if (cursor.moveToFirst()) {
            return cursor.getInt(0);
        }
        cursor.close();
        return -1;
    }

    public int getSourceID1(String source) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT id FROM SOURCE where name = ?";
        Cursor cursor = db.rawQuery(selectQuery, new String[] {source});

        if (cursor.moveToFirst()) {
            return cursor.getInt(0);
        }
        cursor.close();
        return -1;
    }

    public int getNeighborID() {
        String selectQuery = "SELECT ID FROM NEIGHBOR order by ID DESC ";
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            return cursor.getInt(0);
        }
        cursor.close();
        return -1;
    }

    public int getIndexID() {
        String selectQuery = "SELECT ID FROM INDEX1 order by ID DESC";
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            return cursor.getInt(0);
        }
        cursor.close();
        return -1;
    }

    public int getMaxSourceID() {
        String selectQuery = "SELECT ID FROM SOURCE order by ID DESC";
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            return cursor.getInt(0);
        }
        cursor.close();
        return -1;
    }

    public int getNeighborTableID(int startNode, int nextNode, String kpi) {
        String selectQuery = "select id from neighbor where sourceid = ? and NEIGHBORID = ? order by ? asc";
        Cursor cursor = db.rawQuery(selectQuery, new String[] {String.valueOf(startNode), String.valueOf(nextNode), kpi});

        if (cursor.moveToFirst()) {
            return cursor.getInt(0);
        }
        cursor.close();
        return -1;
    }


    public boolean doesIndexRecordExists(int destId, int neighborTableId, int kpivalue, String kpi) {
        String selectQuery = "select id from index where destinationid = ? and NEIGHBORTABLEID = ? and ? = ?";
        Cursor cursor = db.rawQuery(selectQuery, new String[] {String.valueOf(destId), String.valueOf(neighborTableId), kpi, String.valueOf(kpivalue)});

        if (cursor.moveToFirst()) {
            cursor.close();
            return true;
        }
        cursor.close();
        return false;
    }


    public Hop1 getNextHopNode(int sourceId, int destinationId, String kpi) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "select nei.sourceID, nei.neighborID, nei.modeID, nei.TIME, nei.cost, nei.distance from routing r, INDEX1 ind, NEIGHBOR nei where ind.ID = r.INDEXID and ind.destinationid = ? and ind.neighbortableid = nei.id and nei.sourceid = ? and kpi = ?";
        Cursor cursor = db.rawQuery(selectQuery, new String[] {String.valueOf(destinationId), String.valueOf(sourceId), kpi});

        if (cursor.moveToFirst()) {
            return new Hop1(cursor.getInt(3), cursor.getInt(4), cursor.getInt(5), getMode1(cursor.getInt(2)), getSource1(cursor.getInt(0)), getSource1(cursor.getInt(1))) ;
        }
        cursor.close();
        return null;
    }

    public List<String> getSources() {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "select name from SOURCE";
        Cursor cursor = db.rawQuery(selectQuery, null);
        List<String> sources = new ArrayList<>();
        int i = 0;
        if (cursor.moveToFirst()) {
            do {
                sources.add(cursor.getString(0).toUpperCase());
            } while (cursor.moveToNext());
        }
        cursor.close();
        return sources;
    }

    private String getSource(int sourceId) {

        String selectQuery = "SELECT name FROM SOURCE where ID = ?";
        Cursor cursor = db.rawQuery(selectQuery, new String[] {String.valueOf(sourceId)});

        if (cursor.moveToFirst()) {
            return cursor.getString(0);
        }
        cursor.close();
        return null;
    }

    private String getSource1(int sourceId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT name FROM SOURCE where ID = ?";
        Cursor cursor = db.rawQuery(selectQuery, new String[] {String.valueOf(sourceId)});

        if (cursor.moveToFirst()) {
            return cursor.getString(0);
        }
        cursor.close();
        return null;
    }

    public void insertModeTable() {
        String[] modes = new String[] {"Bus", "Metro", "LocalTrain", "Ola", "FordShuttle"};

        for (String mode: modes) {
            ContentValues values = new ContentValues();
            values.put("name", mode);
            db.insert("MODE", null, values);
        }
    }

    public void readCsvFileAndPopulateDB(InputStream inputStream) {
        BufferedReader br = null;
        String line = "";
        try {
            br = new BufferedReader(new InputStreamReader(inputStream));
            br.readLine();
            while ((line = br.readLine()) != null) {

                String[] data = line.split(",");
                int sourceID = getSourceID(data[0]);
                int neighborID = getSourceID(data[1]);
                int modeID = getModeID(data[2]);
                if (sourceID == -1) {
                    sourceID = insertIntoSourceTable(data[0]);
                }

                if (neighborID == -1) {
                    neighborID = insertIntoSourceTable(data[1]);
                }

                int time = Integer.parseInt(data[3]);
                int neighborPrimaryKey = insertIntoNeighborTable(sourceID, neighborID, modeID, time, Integer.parseInt(data[4]), Integer.parseInt(data[5]));
                insertIntoIndexTable(neighborID, neighborPrimaryKey, time, Integer.parseInt(data[4]), Integer.parseInt(data[5]));
                //insertIntoRouting(indexPrimaryKey);
            }

            int graph[][] = insertIntoRouting("time");
            int graph1[][] = insertIntoRouting("cost");
            int graph2[][] = insertIntoRouting("distance");

            for (int i = 0; i< getMaxSourceID(); i++) {
                ShortestPath.dijkstra(graph, i, this, "time");
                ShortestPath.dijkstra(graph1, i, this, "cost");
                ShortestPath.dijkstra(graph2, i, this, "distance");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
