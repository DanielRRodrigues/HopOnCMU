package pt.ulisboa.tecnico.cmu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import pt.ulisboa.tecnico.cmu.DataObjects.Quiz;

public class QuizActivity extends AppCompatActivity {

  private TextView textQuizTitle;
  private ListView listQuestions;
  private ListAdapter quizAdapter;

  private Quiz quiz;
  private Button btnSubmit;

  private OnClickListener btnSubmitClickListener = (new View.OnClickListener() {
    @Override
    public void onClick(View view) {
      Toast.makeText(getApplicationContext(), Constants.TOAST_QUIZ_SUBMITED,
          Toast.LENGTH_SHORT).show();
      finish();
    }
  });

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(pt.ulisboa.tecnico.cmu.R.layout.activity_quiz);

    Intent intent = getIntent();
    this.quiz = (Quiz) intent.getSerializableExtra(Constants.EXTRA_QUIZ);

    this.textQuizTitle = (TextView) findViewById(pt.ulisboa.tecnico.cmu.R.id.text_quiz_title);
    this.listQuestions = (ListView) findViewById(pt.ulisboa.tecnico.cmu.R.id.list_questions);

    this.textQuizTitle.setText(this.quiz.getTitle());

    this.quizAdapter = new CustomQuestionListAdapter(this, (ArrayList) this.quiz.getQuestions());
    this.listQuestions.setAdapter(this.quizAdapter);

    this.btnSubmit = (Button) findViewById(R.id.button_submit);
    this.btnSubmit.setOnClickListener(this.btnSubmitClickListener);
  }
}
