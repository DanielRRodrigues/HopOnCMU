package pt.ulisboa.tecnico.cmu.command;

import pt.ulisboa.tecnico.cmu.response.Response;

public class LoginCommand implements Command {
	  private static final long serialVersionUID = -8464572709559954332L;

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

}
