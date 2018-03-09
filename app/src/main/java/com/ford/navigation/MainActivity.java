package com.ford.navigation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {

    Spinner sourceSpinner;
    Spinner destinationSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setSourceSpinner();
        setDestinationSpinner();

    }

    private void setDestinationSpinner() {
        String[] arraySpinner = new String[] {
                "cmbt", "anna nagar"
        };
        destinationSpinner = (Spinner) findViewById(R.id.destination_selector_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.view_ford_spinner, arraySpinner);
        adapter.setDropDownViewResource(R.layout.view_ford_spinner);
        destinationSpinner.setAdapter(adapter);
    }

    private void setSourceSpinner() {
        String[] arraySpinner = new String[] {
                "thoraipakkam", "perungudi"
        };
        sourceSpinner = (Spinner) findViewById(R.id.source_selector_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.view_ford_spinner, arraySpinner);
        adapter.setDropDownViewResource(R.layout.view_ford_spinner);
        sourceSpinner.setAdapter(adapter);
    }

    public void navigate(View view) {
        String source = sourceSpinner.getSelectedItem().toString();
        String destination = destinationSpinner.getSelectedItem().toString();

        Intent intent = new Intent(this, RouteInfoActivity.class);
        intent.putExtra("source", source);
        intent.putExtra("destination", destination);
        startActivity(intent);
    }
}
