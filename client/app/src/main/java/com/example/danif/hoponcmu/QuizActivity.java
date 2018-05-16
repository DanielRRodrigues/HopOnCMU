package com.example.danif.hoponcmu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;
import com.example.danif.hoponcmu.DataObjects.Quiz;

public class QuizActivity extends AppCompatActivity {

  private TextView textQuizTitle;
  private ListView listQuestions;

  private Quiz quiz;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_quiz);

    Intent intent = getIntent();
    this.quiz = (Quiz) intent.getSerializableExtra(Constants.EXTRA_QUIZ);

    this.textQuizTitle = (TextView) findViewById(R.id.text_quiz_title);
    this.listQuestions = (ListView) findViewById(R.id.list_questions);

    this.textQuizTitle.setText(this.quiz.getTitle());
  }
}
