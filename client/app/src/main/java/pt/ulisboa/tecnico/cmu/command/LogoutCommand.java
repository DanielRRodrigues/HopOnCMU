package pt.ulisboa.tecnico.cmu.command;

import pt.ulisboa.tecnico.cmu.response.Response;

public class LogoutCommand implements Command {

  private static final long serialVersionUID = 6142566383028637568L;

  private String sessionId;

  public LogoutCommand(String sessionId) {
    this.sessionId = sessionId;
  }

  @Override
  public Response handle(CommandHandler ch) {
    return ch.handle(this);
  }

  public String getSessionId() {
    return sessionId;
  }

}
