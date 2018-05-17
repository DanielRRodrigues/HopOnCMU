package pt.ulisboa.tecnico.cmu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import pt.ulisboa.tecnico.cmu.DataObjects.Tour;

public class RankingActivity extends AppCompatActivity {
    private Tour tour;

    private TextView text_tour_title_ranking;
    private ListView list_ranking;
    private ListAdapter scoreAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        Intent intent = getIntent();
        this.tour = (Tour) intent.getSerializableExtra(Constants.EXTRA_TOUR);

        this.text_tour_title_ranking = findViewById(R.id.text_tour_title_ranking);
        this.text_tour_title_ranking.setText(this.tour.getName());

        this.list_ranking = findViewById(R.id.list_ranking);
        this.scoreAdapter = new CustomRankingListAdapter(this, (ArrayList) this.tour.getScores());
        this.list_ranking.setAdapter(this.scoreAdapter);
    }
}
