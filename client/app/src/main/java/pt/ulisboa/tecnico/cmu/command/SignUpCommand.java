package pt.ulisboa.tecnico.cmu.command;

import pt.ulisboa.tecnico.cmu.response.Response;

/**
 * Created by danif on 11-May-18.
 */

public class SignUpCommand implements Command {

  private static final long serialVersionUID = -7490260852113884025L;

  private String username;
  private String code;

  public SignUpCommand(String username, String code) {
    this.username = username;
    this.code = code;
  }

  @Override
  public Response handle(CommandHandler ch) {
    return ch.handle(this);
  }

}
