package com.example.danif.hoponcmu;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.danif.hoponcmu.DataObjects.Quiz;

import java.util.ArrayList;

public class CustomQuizListAdapter extends ArrayAdapter<Quiz> {

    CustomQuizListAdapter(Context context, ArrayList<Quiz> quizzes) {
        super(context, R.layout.custom_quizz_row, quizzes);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View customView = layoutInflater.inflate(R.layout.custom_quizz_row, parent, false);

        Quiz item = getItem(position);
        TextView txtQuizz = (TextView) customView.findViewById(R.id.txtQuizzListView);
        txtQuizz.setText(item.getTitle());

        return customView;
    }
}