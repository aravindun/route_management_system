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
    private RecyclerView shortestRecyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<HopPoints> mDataSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_info);
        Bundle b = getIntent().getBundleExtra("bundle");
        FinalJourney shortestTime = b.getParcelable("time");
        FinalJourney shortestCost = b.getParcelable("cost");
        FinalJourney shortestDistance = b.getParcelable("distance");
        setCheapestRecyclerView(shortestCost);
        setFastestRecyclerView(shortestTime);
        setShortestRecyclerView(shortestDistance);

        AppCompatTextView cost = findViewById(R.id.cheapest_price);
        cost.setText("$"+shortestCost.getTotalcost());

        AppCompatTextView distance = findViewById(R.id.cheapest_distance);
        distance.setText(shortestCost.getTotaldistance()/1000+"km");

        AppCompatTextView fastestDist = findViewById(R.id.fastest_distance);
        fastestDist.setText(shortestDistance.getTotaldistance()/1000+"km");
        AppCompatTextView fastestcost = findViewById(R.id.fastest_price);
        fastestcost.setText("$"+shortestDistance.getTotalcost());
        AppCompatTextView shortestcost = findViewById(R.id.shortest_price);
        shortestcost.setText("$"+shortestTime.getTotalcost());
        AppCompatTextView shortestDist = findViewById(R.id.shortest_distance);
        shortestDist.setText(shortestTime.getTotaldistance()/1000+"km");
    }

    private void setShortestRecyclerView(FinalJourney shortestDistance) {
        fastestRecyclerView = (RecyclerView) findViewById(R.id.shortest_route_info_recycler_view);
        layoutManager = new LinearLayoutManager(this);
        fastestRecyclerView.setLayoutManager(layoutManager);
        adapter = new MyAdapter(createDataSet(shortestDistance.getHops()), this);
        fastestRecyclerView.setAdapter(adapter);
    }

    private void setFastestRecyclerView(FinalJourney shortestTime) {
        fastestRecyclerView = (RecyclerView) findViewById(R.id.fastest_route_info_recycler_view);
        layoutManager = new LinearLayoutManager(this);
        fastestRecyclerView.setLayoutManager(layoutManager);
        adapter = new MyAdapter(createDataSet(shortestTime.getHops()), this);
        fastestRecyclerView.setAdapter(adapter);
    }

    private void setCheapestRecyclerView(FinalJourney shortestCost) {
        cheapestRecyclerView = (RecyclerView) findViewById(R.id.cheapest_route_info_recycler_view);
        layoutManager = new LinearLayoutManager(this);
        cheapestRecyclerView.setLayoutManager(layoutManager);
        adapter = new MyAdapter(createDataSet(shortestCost.getHops()), this);
        cheapestRecyclerView.setAdapter(adapter);
    }

    private List<TravelOptions> createDataSet(List<Hop1> hops) {
        String mode="";
        String hopsDetail="";
        String hopsCommaSeparated="";
        String modesCommaSeparated="";
        for(int count=0;count<hops.size();count++){
            mode+=hops.get(count).getMode();
            modesCommaSeparated+=hops.get(count).getMode();
            hopsDetail+=hops.get(count).getSource()+" to "+hops.get(count).getNeighbor()+" by "+hops.get(count).getMode();
            hopsCommaSeparated+=hops.get(count).getSource()+","+hops.get(count).getNeighbor();
            if(count!=hops.size()-1) {
                mode += "/";
                hopsDetail+="\n";
                hopsCommaSeparated+=",";
                modesCommaSeparated+=",";
            }
        }
        List<TravelOptions> options = new ArrayList();
        options.add(new TravelOptions(mode, "next in 13 mins", hopsDetail, hops, hopsCommaSeparated, modesCommaSeparated));
        return options;
    }
}
