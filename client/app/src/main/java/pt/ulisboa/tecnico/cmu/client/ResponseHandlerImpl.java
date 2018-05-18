package pt.ulisboa.tecnico.cmu.client;

import android.util.Log;
import pt.ulisboa.tecnico.cmu.Constants;
import pt.ulisboa.tecnico.cmu.SignUpActivity;
import pt.ulisboa.tecnico.cmu.response.HelloResponse;
import pt.ulisboa.tecnico.cmu.response.ResponseHandler;
import pt.ulisboa.tecnico.cmu.response.SignUpResponse;

/**
 * Created by danif on 16-May-18.
 */

public class ResponseHandlerImpl implements ResponseHandler {

  @Override
  public void handle(HelloResponse hr) {
    String responseMessage = hr.getMessage();
    System.out.println("Response from server: " + responseMessage);
  }

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
}
