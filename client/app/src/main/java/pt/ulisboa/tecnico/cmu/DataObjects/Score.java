package pt.ulisboa.tecnico.cmu.DataObjects;

import java.io.Serializable;

/**
 * Created by danif on 17-May-18.
 */

public class Score implements Serializable {

  private String name;
  private int correctAnswers;
  private int totalQuestions;


  public Score(String name, int totalQuestions) {
    this.name = name;
    this.correctAnswers = 0;
    this.totalQuestions = totalQuestions;
  }

  public Score(String name, int correctAnswers, int totalQuestions) {
    this.name = name;
    this.correctAnswers = correctAnswers;
    this.totalQuestions = totalQuestions;
  }

  public int getCorrectAnswers() {
    return this.correctAnswers;
  }

  public int getTotalQuestions() {
    return this.totalQuestions;
  }

  public String getName() {
    return this.name;
  }

  public void updateCorrectAnswers(int correctAnswers) {
    this.correctAnswers += correctAnswers;
  }
}
