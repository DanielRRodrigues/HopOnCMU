package pt.ulisboa.tecnico.cmu.response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuizResponseObject implements Serializable {

  private static final long serialVersionUID = -3999022845287635554L;

  private String title;
  private List<QuestionResponseObject> questions;

  public QuizResponseObject(String title,
      List<QuestionResponseObject> questions) {
    this.title = title;
    this.questions = new ArrayList<QuestionResponseObject>(questions);
  }

  public String getTitle() {
    return title;
  }

  public List<QuestionResponseObject> getQuestions() {
    return Collections.unmodifiableList(this.questions);
  }

}
