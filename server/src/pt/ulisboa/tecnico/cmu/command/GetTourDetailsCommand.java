package pt.ulisboa.tecnico.cmu.command;

import pt.ulisboa.tecnico.cmu.response.Response;

public class GetTourDetailsCommand implements Command {

  private static final long serialVersionUID = -1495281420599588609L;

  private String sessionId;
  private String currentLocation;

  public GetTourDetailsCommand(String sessionId, String currentLocation) {
    this.sessionId = sessionId;
    this.currentLocation = currentLocation;
  }

  @Override
  public Response handle(CommandHandler ch) {
    return ch.handle(this);
  }

  public String getSessionId() {
    return sessionId;
  }

  public String getCurrentLocation() {
    return currentLocation;
  }
}
