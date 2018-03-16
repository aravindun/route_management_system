package com.ford.navigation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    Spinner sourceSpinner;
    Spinner destinationSpinner;
    DataBaseHandler dataBaseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataBaseHandler = new DataBaseHandler(this);
        setSourceSpinner();
        setDestinationSpinner();

    }

    private void setDestinationSpinner() {
        List<String> arraySpinner = dataBaseHandler.getSources();
        destinationSpinner = (Spinner) findViewById(R.id.destination_selector_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.view_ford_spinner, arraySpinner);
        adapter.setDropDownViewResource(R.layout.view_ford_spinner);
        destinationSpinner.setAdapter(adapter);
    }

    private void setSourceSpinner() {
        List<String> arraySpinner = dataBaseHandler.getSources();
        sourceSpinner = (Spinner) findViewById(R.id.source_selector_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.view_ford_spinner, arraySpinner);
        adapter.setDropDownViewResource(R.layout.view_ford_spinner);
        sourceSpinner.setAdapter(adapter);
    }

    public void navigate(View view) {
        String source = sourceSpinner.getSelectedItem().toString();
        String destination = destinationSpinner.getSelectedItem().toString();

        JourneyMapper journeyMapper = new JourneyMapper(dataBaseHandler);


        Bundle b = new Bundle();
        FinalJourney time = journeyMapper.getJourney(source, destination, JourneyMapper.KPI.TIME);
        b.putParcelable("time", time);
        FinalJourney cost = journeyMapper.getJourney(source, destination, JourneyMapper.KPI.COST);
        b.putParcelable("cost", cost);
        FinalJourney distance = journeyMapper.getJourney(source, destination, JourneyMapper.KPI.DISTANCE);
        b.putParcelable("distance", distance);
        Intent intent = new Intent(this, RouteInfoActivity.class);
        intent.putExtra("bundle", b);

        startActivity(intent);
    }
}
