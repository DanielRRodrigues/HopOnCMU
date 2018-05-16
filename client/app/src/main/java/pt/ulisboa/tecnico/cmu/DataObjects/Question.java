package pt.ulisboa.tecnico.cmu.DataObjects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by danif on 4/26/2018.
 */

public class Question implements Serializable {

  private String text;
  private List<String> options;

  private String correctAnswer;

  public Question(String question, String correctAnswer) {
    this.text = question;
    this.options = new ArrayList<String>();
    this.correctAnswer = correctAnswer;
  }

  public Question(String question, List<String> options, String correctAnswer) {
    this.text = question;
    this.options = options;
    this.correctAnswer = correctAnswer;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof Question)) {
      return false;
    }
    Question objQuestion = (Question) obj;
    return this.text.equals(objQuestion.getText());
  }

  public String getText() {
    return this.text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public List<String> getOptions() {
    return this.options;
  }

  public void setOptions(List<String> options) {
    this.options = options;
  }

  public void addOption(String option) {
    if (!this.options.contains(option)) {
      this.options.add(option);
    }
  }

  public void removeOption(String option) {
    this.options.remove(option);
  }

  public String getCorrectAnswer() {
    return this.correctAnswer;
  }

  public void setCorrectAnswer(String correctAnswer) {
    this.correctAnswer = correctAnswer;
  }

}
