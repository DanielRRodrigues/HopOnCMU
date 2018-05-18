package pt.ulisboa.tecnico.cmu.client;

import android.util.Log;
import java.util.ArrayList;
import java.util.List;
import pt.ulisboa.tecnico.cmu.Constants;
import pt.ulisboa.tecnico.cmu.DataObjects.Location;
import pt.ulisboa.tecnico.cmu.DataObjects.Tour;
import pt.ulisboa.tecnico.cmu.LoginActivity;
import pt.ulisboa.tecnico.cmu.MainActivity;
import pt.ulisboa.tecnico.cmu.SignUpActivity;
import pt.ulisboa.tecnico.cmu.response.GetTourDetailsResponse;
import pt.ulisboa.tecnico.cmu.response.LoginResponse;
import pt.ulisboa.tecnico.cmu.response.LogoutResponse;
import pt.ulisboa.tecnico.cmu.response.ResponseHandler;
import pt.ulisboa.tecnico.cmu.response.SignUpResponse;

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
    Log.d(Constants.LOG_TAG, "handle: GetTourDetailsResponse -- ");
    Log.d(Constants.LOG_TAG, "handle: GetTourDetailsResponse -- " + MainActivity.sessionId);
    Log.d(Constants.LOG_TAG, "------------------------ END -- handle: GetTourDetailsResponse");
  }
}
