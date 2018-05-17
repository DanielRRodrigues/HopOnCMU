package com.example.danif.hoponcmu.DataObjects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by danif on 4/26/2018.
 */

public class Quiz implements Serializable {

  private List<Question> questions;
  private String title;

  private int correctAmount;

  public Quiz(String title) {
    this.questions = new ArrayList<Question>();
    this.title = title;
    this.correctAmount = 0;
  }

  public Quiz(String title, List<Question> questions) {
    this.questions = questions;
    this.title = title;
    this.correctAmount = 0;
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

  public int getCorrectAmount() {
    return this.correctAmount;
  }

  public void setCorrectAmount(int correctAmount) {
    this.correctAmount = correctAmount;
  }
}
