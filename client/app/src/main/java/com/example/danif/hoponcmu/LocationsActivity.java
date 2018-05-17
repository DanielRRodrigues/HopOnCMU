package com.example.danif.hoponcmu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.example.danif.hoponcmu.DataObjects.Tour;
import java.util.ArrayList;

public class LocationsActivity extends AppCompatActivity {

  private TextView textLocationName;
  private ListView listLocations;
  private ListAdapter locationAdapter;

  private Tour tour;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_locations);

    Intent intent = getIntent();
    this.tour = (Tour) intent.getSerializableExtra(Constants.EXTRA_TOUR);

    this.textLocationName = (TextView) findViewById(R.id.text_tour_title);
    this.listLocations = (ListView) findViewById(R.id.list_locations);

    this.textLocationName.setText(this.tour.getName());

    this.locationAdapter = new CustomLocationListAdapter(this, (ArrayList) this.tour.getLocations());
    this.listLocations.setAdapter(this.locationAdapter);

  }

}
