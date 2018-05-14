package com.example.danif.hoponcmu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.danif.hoponcmu.DataObjects.Constants;
import com.example.danif.hoponcmu.DataObjects.Question;
import com.example.danif.hoponcmu.DataObjects.Quiz;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private boolean loggedIn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Tests
        testingQuizzes();

        if (!this.loggedIn) {
            this.authenticate();
        }

        ListAdapter quizzAdapter = new CustomQuizListAdapter(this, Quiz.quizzes);
        final ListView quizzListView = (ListView) findViewById(R.id.quizzListView);
        quizzListView.setAdapter(quizzAdapter);

        quizzListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Quiz item = (Quiz) quizzListView.getItemAtPosition(position);
                Intent intent = new Intent(getApplicationContext(), QuestionActivity.class);
                intent.putExtra("Quiz", item);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.REQUEST_AUTH) {
            if (resultCode == Constants.AUTH_OK) {
                this.loggedIn = true;
            } else {  // resultCode == Constants.AUTH_FAILED
                this.finish();
            }
        }
    }

    private void authenticate() {
        Intent intent = new Intent(this, CredentialsActivity.class);
        startActivityForResult(intent, Constants.REQUEST_AUTH);
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
        Quiz quiz1 = new Quiz("Quizz1", questions);
        new Quiz("quizz2", null);
        new Quiz("Quizz3", null);
        new Quiz("Quizz4", null);

        System.out.println("------- " + Integer.toString(Quiz.quizzes.size()));

    }
}
