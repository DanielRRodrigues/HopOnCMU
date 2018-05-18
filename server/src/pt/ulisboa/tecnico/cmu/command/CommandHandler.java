package pt.ulisboa.tecnico.cmu.command;

import pt.ulisboa.tecnico.cmu.response.Response;

public interface CommandHandler {
	Response handle(LoginCommand lc);

	Response handle(SignUpCommand suc);
}
