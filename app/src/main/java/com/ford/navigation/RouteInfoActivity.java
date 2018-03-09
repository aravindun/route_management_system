package com.ford.navigation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RouteInfoActivity extends AppCompatActivity{
    private RecyclerView cheapestRecyclerView;
    private RecyclerView fastestRecyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<HopPoints> mDataSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_info);

        setCheapestRecyclerView();
        setFastestRecyclerView();

        AppCompatTextView cost = findViewById(R.id.cheapest_price);
        cost.setText("$25");

        AppCompatTextView distance = findViewById(R.id.cheapest_distance);
        distance.setText("25 km");

        AppCompatTextView fastestDist = findViewById(R.id.fastest_distance);
        fastestDist.setText("10 km");
        AppCompatTextView fastestcost = findViewById(R.id.fastest_price);
        fastestcost.setText("$30");
    }

    private void setFastestRecyclerView() {
        fastestRecyclerView = (RecyclerView) findViewById(R.id.fastest_route_info_recycler_view);
        layoutManager = new LinearLayoutManager(this);
        fastestRecyclerView.setLayoutManager(layoutManager);
        adapter = new MyAdapter(createDataSet(), this);
        fastestRecyclerView.setAdapter(adapter);
    }

    private void setCheapestRecyclerView() {
        cheapestRecyclerView = (RecyclerView) findViewById(R.id.cheapest_route_info_recycler_view);
        layoutManager = new LinearLayoutManager(this);
        cheapestRecyclerView.setLayoutManager(layoutManager);
        adapter = new MyAdapter(createDataSet(), this);
        cheapestRecyclerView.setAdapter(adapter);
    }

    private List<TravelOptions> createDataSet() {
        List<TravelOptions> options = new ArrayList();
        options.add(new TravelOptions("shuttle", "next in 13 mins"));
        options.add(new TravelOptions("shuttle/taxi", "next in 7 mins"));
        options.add(new TravelOptions("bus/taxi/walk", "next in 10 mins"));
        return options;
    }
}
