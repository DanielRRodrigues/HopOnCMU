package com.example.danif.hoponcmu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.danif.hoponcmu.DataObjects.Quiz;

public class QuizActivity extends AppCompatActivity {

  private TextView textQuizTitle;
  private ListView listQuestions;
  private ListAdapter quizAdapter;

  private Quiz quiz;
  private Button btnSubmit;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_quiz);

    Intent intent = getIntent();
    this.quiz = (Quiz) intent.getSerializableExtra(Constants.EXTRA_QUIZ);

    this.textQuizTitle = (TextView) findViewById(R.id.text_quiz_title);
    this.listQuestions = (ListView) findViewById(R.id.list_questions);

    this.textQuizTitle.setText(this.quiz.getTitle());

    this.quizAdapter = new CustomQuestionListAdapter(this, this.quiz.getQuestions());
    this.listQuestions.setAdapter(this.quizAdapter);

    this.btnSubmit = (Button) findViewById(R.id.btnSubmit);
    this.btnSubmit.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Toast.makeText(getApplicationContext(), Constants.TOAST_QUIZ_SUBMITED,
                Toast.LENGTH_SHORT).show();
        finish();
      }
    });


  }
}
