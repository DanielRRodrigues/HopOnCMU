package pt.ulisboa.tecnico.cmu;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import pt.ulisboa.tecnico.cmu.DataObjects.Quiz;
import pt.ulisboa.tecnico.cmu.command.HelloCommand;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class QuizActivity extends AppCompatActivity {

  private TextView textQuizTitle;
  private ListView listQuestions;
  private ListAdapter quizAdapter;

  private Quiz quiz;
  private Button btnSubmit;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(pt.ulisboa.tecnico.cmu.R.layout.activity_quiz);

    Intent intent = getIntent();
    this.quiz = (Quiz) intent.getSerializableExtra(Constants.EXTRA_QUIZ);

    this.textQuizTitle = (TextView) findViewById(pt.ulisboa.tecnico.cmu.R.id.text_quiz_title);
    this.listQuestions = (ListView) findViewById(pt.ulisboa.tecnico.cmu.R.id.list_questions);

    this.textQuizTitle.setText(this.quiz.getTitle());

    this.quizAdapter = new CustomQuestionListAdapter(this, this.quiz.getQuestions());
    this.listQuestions.setAdapter(this.quizAdapter);

    this.btnSubmit = (Button) findViewById(pt.ulisboa.tecnico.cmu.R.id.btnSubmit);
    this.btnSubmit.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Toast.makeText(getApplicationContext(), Constants.TOAST_QUIZ_SUBMITED,
                Toast.LENGTH_SHORT).show();

        AsyncTask.execute(new Runnable() {
          @Override
          public void run() {
            HelloCommand hc = new HelloCommand("huehuehe");
            Socket server = null;
            try {
              server = new Socket("10.0.2.2", 9090);
              ObjectOutputStream oos = new ObjectOutputStream(server.getOutputStream());
              oos.writeObject(hc);
            } catch (IOException e) {
              e.printStackTrace();
            }
          }
        });
        finish();
      }
    });
  }
}
