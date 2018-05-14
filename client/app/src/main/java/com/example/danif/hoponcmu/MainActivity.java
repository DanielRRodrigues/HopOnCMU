package com.example.danif.hoponcmu;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Button;
import android.widget.Toast;

import com.example.danif.hoponcmu.DataObjects.Constants;
import com.example.danif.hoponcmu.DataObjects.Question;
import com.example.danif.hoponcmu.DataObjects.Quiz;
import com.example.danif.hoponcmu.client.CommandHandlerImpl;
import com.example.danif.hoponcmu.command.DownloadQuizzesCommand;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    private boolean loggedIn = false;

    private Button btnLogOut;
    private Button btnDownloadQuizzes;

    private String currentMonument;

    public class DownloadQuizzesAction extends AsyncTask<Void, Void, Void> {

        private boolean successDownload = false;

        @Override
        protected Void doInBackground(Void... voids) {
            CommandHandlerImpl chi = new CommandHandlerImpl();

            // Random used for test purpose
            this.successDownload = new Random().nextBoolean();

            DownloadQuizzesCommand suc = new DownloadQuizzesCommand(MainActivity.this.currentMonument);
            // suc.handle(chi);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if (this.successDownload) {
                Toast.makeText(MainActivity.this, "Quizzes for " + MainActivity.this.currentMonument + " downloaded", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(MainActivity.this, "Failed to download quizzes", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Tests
        this.testingQuizzes();
        this.currentMonument = "TestMonument";

        if (!this.loggedIn) {
            this.authenticate();
        }

        this.getViews();
        this.setListeners();

        ListAdapter quizAdapter = new CustomQuizListAdapter(this, Quiz.quizzes);
        final ListView quizListView = (ListView) findViewById(R.id.quizListView);
        quizListView.setAdapter(quizAdapter);

        quizListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Quiz item = (Quiz) quizListView.getItemAtPosition(position);
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
        Quiz quiz1 = new Quiz("Quiz1", questions);
        new Quiz("quiz2", null);
        new Quiz("Quiz3", null);
        new Quiz("Quiz4", null);

        System.out.println("------- " + Integer.toString(Quiz.quizzes.size()));
    }

    private void getViews() {
        this.btnLogOut = (Button) findViewById(R.id.btnLogOut_Main);
        this.btnDownloadQuizzes = (Button) findViewById(R.id.btnDonwloadQuizzes_Main);
    }

    private void setListeners() {
        this.btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.this.loggedIn = false;
                MainActivity.this.authenticate();
            }
        });
        this.btnDownloadQuizzes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AsyncTask<Void, Void, Void> task = new DownloadQuizzesAction();
                try {
                    task.execute().get();
                } catch (InterruptedException e){
                    // TODO
                } catch (ExecutionException e) {
                    // TODO
                }
            }
        });
    }
}
