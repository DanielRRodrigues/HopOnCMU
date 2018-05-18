package pt.ulisboa.tecnico.cmu.command;

import pt.ulisboa.tecnico.cmu.response.Response;

public class UpdateRankingCommand implements Command {

  private static final long serialVersionUID = -5449437393030679358L;

  private String sessionId;

  public UpdateRankingCommand(String sessionId) {
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
