package pt.ulisboa.tecnico.cmu.command;

import pt.ulisboa.tecnico.cmu.response.Response;

public class DownloadQuizzesCommand implements Command {

  private static final long serialVersionUID = 1974509871547525317L;

  private String monument;

  public DownloadQuizzesCommand(String monument) {
    this.monument = monument;
  }

  @Override
  public Response handle(CommandHandler chi) {
    return chi.handle(this);
  }

}
