package pt.ulisboa.tecnico.cmu;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import pt.ulisboa.tecnico.cmu.DataObjects.Score;

/**
 * Created by danif on 17-May-18.
 */

public class CustomRankingListAdapter extends ArrayAdapter<Score> {

    private TextView text_score_name;
    private TextView text_score_ranking;

    public CustomRankingListAdapter(@NonNull Context context, ArrayList<Score> scores) {
        super(context, R.layout.custom_ranking_row, scores);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View customView = layoutInflater.inflate(R.layout.custom_ranking_row, parent, false);

        Score item = getItem(position);
        this.text_score_name = customView.findViewById(R.id.text_score_name);
        this.text_score_name.setText(item.getName());

        this.text_score_ranking = customView.findViewById(R.id.text_score_ranking);
        this.text_score_ranking.setText(Integer.toString(item.getCorrectAnswers()) + "/"
                + Integer.toString(item.getTotalQuestions()));

        return customView;
    }
}
