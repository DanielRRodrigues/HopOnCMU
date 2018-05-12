package com.example.danif.hoponcmu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.danif.hoponcmu.DataObejcts.Question;
import com.example.danif.hoponcmu.DataObejcts.Quizz;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        testingQuizzes();

        Intent intent = new Intent(this, CredentialsActivity.class);
        startActivity(intent);

        ListAdapter quizzAdapter = new CustomQuizzListAdapter(this, Quizz.quizzes);
        final ListView quizzListView = (ListView) findViewById(R.id.quizzListView);
        quizzListView.setAdapter(quizzAdapter);

        quizzListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Quizz item = (Quizz) quizzListView.getItemAtPosition(position);
                Intent intent = new Intent(getApplicationContext(), QuestionActivity.class);
                intent.putExtra("Quizz", item);
                startActivity(intent);
            }
        });

    }

    public void testingQuizzes() {
        ArrayList<String> options1 = new ArrayList<>();
        options1.add("A");
        options1.add("B");
        options1.add("C");
        options1.add("D");
        Question question1 = new Question("Q1", options1);
        Question question2 = new Question("Q2", options1);
        Question question3 = new Question("Q3", options1);
        Question question4 = new Question("Q4", options1);
        ArrayList<Question> questions = new ArrayList<Question>();
        questions.add(question1);
        questions.add(question2);
        questions.add(question3);
        questions.add(question4);
        Quizz quizz1 = new Quizz("Quizz1", questions);
        new Quizz("quizz2", null);
        new Quizz("Quizz3", null);
        new Quizz("Quizz4", null);

        System.out.println("------- " + Integer.toString(Quizz.quizzes.size()));

    }
}
