package pt.ulisboa.tecnico.cmu.DataObjects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import pt.ulisboa.tecnico.cmu.Constants;

/**
 * Created by danif on 4/26/2018.
 */

public class Quiz implements Serializable {

  private Location location;
  private List<Question> questions;
  private String title;

  private int status;

  public Quiz(String title, Location location) {
    this.questions = new ArrayList<Question>();
    this.location = location;
    this.title = title;
    this.status = Constants.STATUS_QUIZ_AVAILABLE;
  }

  public Quiz(String title, Location location, List<Question> questions) {
    this.questions = questions;
    this.location = location;
    this.title = title;
    this.status = Constants.STATUS_QUIZ_AVAILABLE;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof Quiz)) {
      return false;
    }
    Quiz objQuiz = (Quiz) obj;
    return this.title.equals(objQuiz.getTitle()) && this.questions.size() == objQuiz.getQuestions()
        .size() && this.questions.containsAll(objQuiz.getQuestions());
  }

  public String getTitle() {
    return this.title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Question getQuestionByText(String text) {
    for (Question q : this.questions) {
      if (q.getText().equals(text)) {
        return q;
      }
    }
    return null;
  }

  public List<Question> getQuestions() {
    return this.questions;
  }

  public void setQuestions(List<Question> questions) {
    this.questions = questions;
  }

  public void addQuestion(Question question) {
    if (!this.questions.contains(question)) {
      this.questions.add(question);
    }
  }

  public void removeQuestion(Question question) {
    this.questions.remove(question);
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public Location getLocation() {
    return location;
  }

  public void setLocation(Location location) {
    this.location = location;
  }

  public void complete() {
    this.status = Constants.STATUS_QUIZ_COMPLETED;
  }

  public void disable() {
    this.status = Constants.STATUS_QUIZ_DISABLED;
  }

  public boolean isAvailable() {
    return this.status == Constants.STATUS_QUIZ_AVAILABLE;
  }

  public boolean isDisabled() {
    return this.status == Constants.STATUS_QUIZ_COMPLETED;
  }

  public boolean isCompleted() {
    return this.status == Constants.STATUS_QUIZ_DISABLED;
  }

  public String status() {
    if (this.status == Constants.STATUS_QUIZ_AVAILABLE)
      return Constants.STATUS_QUIZ_AVAILABLE_TEXT;
    if (this.status == Constants.STATUS_QUIZ_COMPLETED)
      return Constants.STATUS_QUIZ_COMPLETED_TEXT;
    return Constants.STATUS_QUIZ_DISABLED_TEXT;
  }
}
