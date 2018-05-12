package pt.ulisboa.tecnico.cmu.command;

import pt.ulisboa.tecnico.cmu.response.Response;

public interface CommandHandler {
	public Response helloCommandHandle(HelloCommand hc);
	public Response signUpCommandHandle(SignUpCommand sup);
}
