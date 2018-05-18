package pt.ulisboa.tecnico.cmu;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
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
      if (allQuestionsAnswered()) {
        Intent resultIntent = new Intent();
        resultIntent.putExtra(Constants.EXTRA_QUIZ, QuizActivity.this.quiz);
        resultIntent.putExtra(Constants.EXTRA_CORRECT_ANSWERS, QuizActivity.this.getCorrectAnswers());
        setResult(Constants.QUIZ_PLAY_FINISH, resultIntent);
        Toast.makeText(getApplicationContext(), Constants.TOAST_QUIZ_SUBMITED,
            Toast.LENGTH_SHORT).show();
        QuizActivity.this.finish();
      } else {
        Log.d(Constants.LOG_TAG, "[QuizActivity] submitButton: Failed");
      }
    }
  });

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(pt.ulisboa.tecnico.cmu.R.layout.activity_quiz);
    setResult(Constants.QUIZ_PLAY_STOPPED);

    Intent intent = getIntent();
    this.quiz = (Quiz) intent.getSerializableExtra(Constants.EXTRA_QUIZ);

    this.textQuizTitle = (TextView) findViewById(pt.ulisboa.tecnico.cmu.R.id.text_quiz_title);
    this.listQuestions = (ListView) findViewById(pt.ulisboa.tecnico.cmu.R.id.list_questions);

    this.textQuizTitle.setText(this.quiz.getTitle());

    this.quizAdapter = new CustomQuestionListAdapter(this, (ArrayList) this.quiz.getQuestions());
    this.listQuestions.setAdapter(this.quizAdapter);

    this.btnSubmit = (Button) findViewById(R.id.button_submit);
    this.btnSubmit.setOnClickListener(this.btnSubmitClickListener);

    if (quiz.isCompleted() || quiz.isDisabled()) {
      showSolutions();
      btnSubmit.setEnabled(false);
    }

  }

  @Override
  protected void onStart() {
    super.onStart();
  }

  private int getCorrectAnswers() {
    int correctAnswers = 0;
    for (int i = 0; i < this.quiz.getQuestions().size(); i++) {
      View questionView = getViewByPosition(i, listQuestions);
      TextView txtQuestion = (TextView) questionView.findViewById(R.id.text_question_adapter);
      RadioButton checkOption1 = (RadioButton) questionView.findViewById(R.id.radio_option_0);
      RadioButton checkOption2 = (RadioButton) questionView.findViewById(R.id.radio_option_1);
      RadioButton checkOption3 = (RadioButton) questionView.findViewById(R.id.radio_option_2);
      RadioButton checkOption4 = (RadioButton) questionView.findViewById(R.id.radio_option_3);
      String correctAnswer = quiz.getQuestionByText(txtQuestion.getText().toString())
          .getCorrectAnswer();
      if (checkOption1.isChecked() && checkOption1.getText().toString().equals(correctAnswer)) {
        correctAnswers += 1;
      }
      if (checkOption2.isChecked() && checkOption2.getText().toString().equals(correctAnswer)) {
        correctAnswers += 1;
      }
      if (checkOption3.isChecked() && checkOption3.getText().toString().equals(correctAnswer)) {
        correctAnswers += 1;
      }
      if (checkOption4.isChecked() && checkOption4.getText().toString().equals(correctAnswer)) {
        correctAnswers += 1;
      }
    }
    return correctAnswers;
  }

  private boolean allQuestionsAnswered() {
    Log.d(Constants.LOG_TAG, "[QuizActivity] questions: " + this.quiz.getQuestions().size());
    for (int i = 0; i < this.quiz.getQuestions().size(); i++) {
      Log.d(Constants.LOG_TAG, "[QuizActivity] allQuestionsAnswered: " + i);
      View questionView = getViewByPosition(i, listQuestions);
      TextView txtQuestion = (TextView) questionView.findViewById(R.id.text_question_adapter);
      RadioButton checkOption1 = (RadioButton) questionView.findViewById(R.id.radio_option_0);
      RadioButton checkOption2 = (RadioButton) questionView.findViewById(R.id.radio_option_1);
      RadioButton checkOption3 = (RadioButton) questionView.findViewById(R.id.radio_option_2);
      RadioButton checkOption4 = (RadioButton) questionView.findViewById(R.id.radio_option_3);
      Log.d(Constants.LOG_TAG, "[QuizActivity] question: " + txtQuestion.getText().toString());
      if (!checkOption1.isChecked() && !checkOption2.isChecked() && !checkOption3.isChecked()
          && !checkOption4.isChecked()) {
        txtQuestion.setError(Constants.ERROR_QUESTION_UNANSWERED);
        return false;
      } else {
        txtQuestion.setError(null);
      }
    }
    return true;
  }

  private void showSolutions() {
    for (int i = 0; i < this.quiz.getQuestions().size(); i++) {
      View questionView = getViewByPosition(i, listQuestions);
      TextView txtQuestion = (TextView) questionView.findViewById(R.id.text_question_adapter);
      RadioButton checkOption1 = (RadioButton) questionView.findViewById(R.id.radio_option_0);
      RadioButton checkOption2 = (RadioButton) questionView.findViewById(R.id.radio_option_1);
      RadioButton checkOption3 = (RadioButton) questionView.findViewById(R.id.radio_option_2);
      RadioButton checkOption4 = (RadioButton) questionView.findViewById(R.id.radio_option_3);
      String correctAnswer = quiz.getQuestionByText(txtQuestion.getText().toString())
          .getCorrectAnswer();
      if (checkOption1.getText().toString().equals(correctAnswer)) {
        checkOption1.setBackgroundColor(Color.GREEN);
        checkOption1.setTextColor(Color.GREEN);
        checkOption1.setChecked(true);
      }
      if (checkOption2.getText().toString().equals(correctAnswer)) {
        checkOption2.setBackgroundColor(Color.GREEN);
        checkOption2.setChecked(true);
      }
      if (checkOption3.getText().toString().equals(correctAnswer)) {
        checkOption3.setBackgroundColor(Color.GREEN);
        checkOption3.setChecked(true);
      }
      if (checkOption4.getText().toString().equals(correctAnswer)) {
        checkOption4.setBackgroundColor(Color.GREEN);
        checkOption4.setChecked(true);
      }
      Log.d(Constants.LOG_TAG, "[QuizActivity] showSolutions: end");
    }
  }

  private View getViewByPosition(int position, ListView listView) {
    final int firstListItemPosition = listView.getFirstVisiblePosition();
    final int lastListItemPosition = firstListItemPosition + listView.getChildCount() - 1;

    if (position < firstListItemPosition || position > lastListItemPosition) {
      return listView.getAdapter().getView(position, listView.getChildAt(position), listView);
    } else {
      final int childIndex = position - firstListItemPosition;
      return listView.getChildAt(childIndex);
    }
  }
}
