package pt.ulisboa.tecnico.cmu;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import pt.ulisboa.tecnico.cmu.DataObjects.Location;
import pt.ulisboa.tecnico.cmu.DataObjects.Question;
import pt.ulisboa.tecnico.cmu.DataObjects.Quiz;
import pt.ulisboa.tecnico.cmu.DataObjects.Tour;
import pt.ulisboa.tecnico.cmu.command.DownloadQuizzesCommand;

public class MainActivity extends AppCompatActivity {

  private Button btnLogOut;
  private Button btnDownloadQuizzes;
  private Button btnRanking;
  private Button btnFinishDay;
  private Button btnLocations;
  private ListAdapter adapterQuiz;
  private ListView listQuizzes;

  private boolean loggedIn = false;
  private String currentMonument;
  private List<Quiz> quizzesList = new ArrayList<Quiz>();
  private Tour tour;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(pt.ulisboa.tecnico.cmu.R.layout.activity_main);

    // Tests
    this.testingQuizzes();

    this.adapterQuiz = new CustomQuizListAdapter(this, (ArrayList) this.quizzesList);
    this.btnLogOut = (Button) findViewById(R.id.button_logout);
    this.btnDownloadQuizzes = (Button) findViewById(R.id.button_download_quizzes);
    this.btnRanking = (Button) findViewById(R.id.button_ranking);
    this.btnFinishDay = (Button) findViewById(R.id.button_finish_day);
    this.btnLocations = (Button) findViewById(R.id.button_locations);
    this.listQuizzes = (ListView) findViewById(R.id.list_quizzes);
    this.listQuizzes.setAdapter(this.adapterQuiz);

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
        } catch (InterruptedException e) {
          Log.d(Constants.LOG_TAG, "onClick error: InterruptedException");
        } catch (ExecutionException e) {
          Log.d(Constants.LOG_TAG, "onClick error: ExecutionException");
        }
      }
    });

    this.btnRanking.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Log.d(Constants.LOG_TAG, "onClick info: Ranking");
      }
    });

    this.btnFinishDay.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Toast.makeText(MainActivity.this, Constants.TOAST_DAY_FINISHED, Toast.LENGTH_SHORT).show();
      }
    });

    this.btnLocations.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent intent = new Intent(getApplicationContext(), LocationsActivity.class);
        intent.putExtra(Constants.EXTRA_TOUR, MainActivity.this.tour);
        startActivity(intent);
      }
    });

    this.listQuizzes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        Quiz item = (Quiz) MainActivity.this.listQuizzes.getItemAtPosition(position);
        Intent intent = new Intent(getApplicationContext(), QuizActivity.class);
        intent.putExtra(Constants.EXTRA_QUIZ, item);
        startActivity(intent);
      }
    });
  }

  @Override
  protected void onStart() {
    super.onStart();

    if (!this.loggedIn) {
      this.authenticate();
    }
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

  private void testingQuizzes() {
    this.currentMonument = "TestMonument";
    //Question 1
    Question testQuestion1 = new Question("TestQuestion1", "C");
    testQuestion1.addOption("A");
    testQuestion1.addOption("B");
    testQuestion1.addOption("C");
    testQuestion1.addOption("D");
    //Question 2
    Question testQuestion2 = new Question("TestQuestion2", "C");
    testQuestion2.addOption("A");
    testQuestion2.addOption("B");
    testQuestion2.addOption("C");
    testQuestion2.addOption("D");
    //Question 3
    Question testQuestion3 = new Question("TestQuestion3", "C");
    testQuestion3.addOption("A");
    testQuestion3.addOption("B");
    testQuestion3.addOption("C");
    testQuestion3.addOption("D");
    Quiz testQuiz = new Quiz("TestQuiz");
    this.quizzesList.add(new Quiz("TestQuiz2"));
    this.quizzesList.add(new Quiz("TestQuiz3"));
    this.quizzesList.add(new Quiz("TestQuiz4"));
    this.quizzesList.add(new Quiz("TestQuiz5"));
    testQuiz.addQuestion(testQuestion1);
    testQuiz.addQuestion(testQuestion1);
    testQuiz.addQuestion(testQuestion2);
    testQuiz.addQuestion(testQuestion3);
    this.quizzesList.add(testQuiz);
    Log.d(Constants.LOG_TAG, "Quiz questions: " + Integer.toString(testQuiz.getQuestions().size()));
    // Locations
    List<Location> testLocations = new ArrayList<Location>();
    testLocations.add(new Location("L1"));
    testLocations.add(new Location("L2"));
    testLocations.add(new Location("L3"));
    testLocations.add(new Location("L4"));
    this.tour = new Tour("TestTour", testLocations);
    Log.d(Constants.LOG_TAG,
        "Tour locations: " + Integer.toString(this.tour.getLocations().size()));
  }

  public class DownloadQuizzesAction extends AsyncTask<Void, Void, Void> {

    private boolean mSuccessDownload = false;

    @Override
    protected Void doInBackground(Void... voids) {

      // Random used for test purpose
      this.mSuccessDownload = new Random().nextBoolean();

      DownloadQuizzesCommand suc = new DownloadQuizzesCommand(MainActivity.this.currentMonument);
      // suc.handle(chi);
      // TODO: 16/05/2018

      return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
      if (this.mSuccessDownload) {
        Toast.makeText(MainActivity.this, Constants.TOAST_DOWNLOAD_QUIZZES_SUCCESS,
            Toast.LENGTH_SHORT).show();
      } else {
        Toast.makeText(MainActivity.this, Constants.TOAST_DOWNLOAD_QUIZZES_FAILED,
            Toast.LENGTH_SHORT).show();
      }
    }
  }

}
