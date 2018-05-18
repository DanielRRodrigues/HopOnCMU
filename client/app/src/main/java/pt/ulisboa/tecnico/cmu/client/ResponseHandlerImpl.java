package pt.ulisboa.tecnico.cmu.client;

import android.util.Log;
import java.util.ArrayList;
import java.util.List;
import pt.ulisboa.tecnico.cmu.Constants;
import pt.ulisboa.tecnico.cmu.DataObjects.Location;
import pt.ulisboa.tecnico.cmu.DataObjects.Question;
import pt.ulisboa.tecnico.cmu.DataObjects.Quiz;
import pt.ulisboa.tecnico.cmu.DataObjects.Score;
import pt.ulisboa.tecnico.cmu.DataObjects.Tour;
import pt.ulisboa.tecnico.cmu.LoginActivity;
import pt.ulisboa.tecnico.cmu.MainActivity;
import pt.ulisboa.tecnico.cmu.RankingActivity;
import pt.ulisboa.tecnico.cmu.SignUpActivity;
import pt.ulisboa.tecnico.cmu.response.DownloadQuizzesResponse;
import pt.ulisboa.tecnico.cmu.response.GetTourDetailsResponse;
import pt.ulisboa.tecnico.cmu.response.LoginResponse;
import pt.ulisboa.tecnico.cmu.response.LogoutResponse;
import pt.ulisboa.tecnico.cmu.response.QuestionResponseObject;
import pt.ulisboa.tecnico.cmu.response.QuizResponseObject;
import pt.ulisboa.tecnico.cmu.response.ResponseHandler;
import pt.ulisboa.tecnico.cmu.response.ScoreResponseObject;
import pt.ulisboa.tecnico.cmu.response.SignUpResponse;
import pt.ulisboa.tecnico.cmu.response.UpdateRankingResponse;

/**
 * Created by danif on 16-May-18.
 */

public class ResponseHandlerImpl implements ResponseHandler {

  @Override
  public void handle(SignUpResponse sur) {
    Log.d(Constants.LOG_TAG, "------------------------ START -- handle: SignUpResponse");
    String sessionId = sur.getSessionId();
    if (sessionId != null) {
      SignUpActivity.sessionId = sessionId;
    } else {
      SignUpActivity.sessionId = null;
    }
    Log.d(Constants.LOG_TAG, "handle: SignUpResponse -- " + sessionId);
    Log.d(Constants.LOG_TAG, "handle: SignUpResponse -- " + SignUpActivity.sessionId);
    Log.d(Constants.LOG_TAG, "------------------------ END -- handle: SignUpResponse");
  }

  @Override
  public void handle(LoginResponse lr) {
    Log.d(Constants.LOG_TAG, "------------------------ START -- handle: LoginResponse");
    String sessionId = lr.getSessionId();
    if (sessionId != null) {
      LoginActivity.sessionId = sessionId;
    } else {
      LoginActivity.sessionId = null;
    }
    Log.d(Constants.LOG_TAG, "handle: LoginResponse -- " + sessionId);
    Log.d(Constants.LOG_TAG, "handle: LoginResponse -- " + LoginActivity.sessionId);
    Log.d(Constants.LOG_TAG, "------------------------ END -- handle: LoginResponse");
  }

  @Override
  public void handle(LogoutResponse lr) {
    Log.d(Constants.LOG_TAG, "------------------------ START -- handle: LogoutResponse");
    boolean loggedOut = lr.getLoggedOut();
    if (loggedOut) {
      MainActivity.sessionId = null;
    }
    Log.d(Constants.LOG_TAG, "handle: LogoutResponse -- " + loggedOut);
    Log.d(Constants.LOG_TAG, "handle: LogoutResponse -- " + MainActivity.sessionId);
    Log.d(Constants.LOG_TAG, "------------------------ END -- handle: LogoutResponse");
  }

  @Override
  public void handle(GetTourDetailsResponse gtdr) {
    Log.d(Constants.LOG_TAG, "------------------------ START -- handle: GetTourDetailsResponse");
    boolean error = gtdr.getError();
    if (error) {
      Log.d(Constants.LOG_TAG, "handle: GetTourDetailsResponse -- ERROR");
    } else {
      String tourName = gtdr.getTourName();
      List<String> locations = (ArrayList) gtdr.getLocationList();
      String currentLocation = gtdr.getCurrentLocation();
      String nextLocation = gtdr.getNextLocation();
      List<Location> locationList = new ArrayList<Location>();
      for (String s : locations) {
        locationList.add(new Location(s));
      }
      Tour newTour = new Tour(tourName, locationList);
      MainActivity.tour = newTour;
      MainActivity.currentLocation = new Location(currentLocation);
      MainActivity.nextLocation = new Location(nextLocation);
      Log.d(Constants.LOG_TAG, "handle: GetTourDetailsResponse -- " + tourName);
      Log.d(Constants.LOG_TAG, "handle: GetTourDetailsResponse -- " + locations.size());
      Log.d(Constants.LOG_TAG, "handle: GetTourDetailsResponse -- " + currentLocation);
      Log.d(Constants.LOG_TAG, "handle: GetTourDetailsResponse -- " + nextLocation);
    }
    Log.d(Constants.LOG_TAG, "handle: GetTourDetailsResponse -- " + MainActivity.sessionId);
    Log.d(Constants.LOG_TAG, "------------------------ END -- handle: GetTourDetailsResponse");
  }

  @Override
  public void handle(DownloadQuizzesResponse dqr) {
    Log.d(Constants.LOG_TAG, "------------------------ START -- handle: DownloadQuizzesResponse");
    boolean error = dqr.getError();
    if (error) {
      Log.d(Constants.LOG_TAG, "handle: DownloadQuizzesResponse -- ERROR");
    } else {
      MainActivity.quizzesUpdated = true;
      List<QuizResponseObject> quizzes = dqr.getQuizzes();
      Log.d(Constants.LOG_TAG, "handle: DownloadQuizzesResponse -- " + quizzes.size());
      for (QuizResponseObject quiz : quizzes) {
        String quizTitle = quiz.getTitle();
        List<QuestionResponseObject> questions = quiz.getQuestions();
        List<Question> newQuestions = new ArrayList<Question>();
        for (QuestionResponseObject question : questions) {
          newQuestions
              .add(new Question(question.getText(), question.getOptions(), question.getAnswer()));
        }
        Quiz newQuiz = new Quiz(quizTitle, MainActivity.currentLocation, newQuestions);
        if (!MainActivity.quizzesList.contains(newQuiz))
          MainActivity.quizzesList.add(newQuiz);
        Log.d(Constants.LOG_TAG, "handle: DownloadQuizzesResponse -- " + quizTitle);
        Log.d(Constants.LOG_TAG, "handle: DownloadQuizzesResponse -- " + questions.size());
      }
    }
    Log.d(Constants.LOG_TAG, "handle: DownloadQuizzesResponse -- " + MainActivity.sessionId);
    Log.d(Constants.LOG_TAG, "------------------------ END -- handle: DownloadQuizzesResponse");
  }

  @Override
  public void handle(UpdateRankingResponse urr) {
    Log.d(Constants.LOG_TAG, "------------------------ START -- handle: UpdateRankingResponse");
    boolean error = urr.getError();
    if (error) {
      Log.d(Constants.LOG_TAG, "handle: UpdateRankingResponse -- ERROR");
    } else {
      RankingActivity.updated = true;
      List<ScoreResponseObject> scores = urr.getScores();
      Log.d(Constants.LOG_TAG, "handle: UpdateRankingResponse -- " + scores.size());
      List<Score> newScores = new ArrayList<Score>();
      for (ScoreResponseObject score : scores) {
        String username = score.getUsername();
        int correctAnswers = score.getCorrectAnswers();
        int totalQuestions = score.getTotalQuestions();
        newScores.add(new Score(username, correctAnswers, totalQuestions));
        Log.d(Constants.LOG_TAG, "handle: UpdateRankingResponse -- " + username);
        Log.d(Constants.LOG_TAG, "handle: UpdateRankingResponse -- " + correctAnswers);
        Log.d(Constants.LOG_TAG, "handle: UpdateRankingResponse -- " + totalQuestions);
      }
      MainActivity.tour.updateScores(newScores);
    }
    Log.d(Constants.LOG_TAG, "handle: UpdateRankingResponse -- " + MainActivity.sessionId);
    Log.d(Constants.LOG_TAG, "------------------------ END -- handle: UpdateRankingResponse");
  }
}
