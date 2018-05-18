package pt.ulisboa.tecnico.cmu.command;

import pt.ulisboa.tecnico.cmu.response.Response;

public class LoginCommand implements Command {

  private static final long serialVersionUID = 376009019875211029L;

  private String username;
  private String code;

  public LoginCommand(String username, String code) {
    this.username = username;
    this.code = code;
  }

  @Override
  public Response handle(CommandHandler ch) {
    return ch.handle(this);
  }

  public String getUsername() {
    return username;
  }

  public String getCode() {
    return code;
  }

}
