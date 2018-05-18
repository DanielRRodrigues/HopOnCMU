package pt.ulisboa.tecnico.cmu.command;

import pt.ulisboa.tecnico.cmu.response.Response;

public class DownloadQuizzesCommand implements Command {

  private String sessionId;
  private String currentLocation;

  public DownloadQuizzesCommand(String sessionId, String currentLocation) {
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
