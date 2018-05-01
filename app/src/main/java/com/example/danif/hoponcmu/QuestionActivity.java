package com.example.danif.hoponcmu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.example.danif.hoponcmu.DataObejcts.Quizz;

public class QuestionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        Intent intent = getIntent();
        Quizz quizz = (Quizz) intent.getSerializableExtra("Quizz");

        TextView txtQuizzTitle = (TextView) findViewById(R.id.txtQuizzTitle);
        txtQuizzTitle.setText(quizz.getTitle());

        ListView listViewQuestions = (ListView) findViewById(R.id.listViewQuestions);

    }
}
