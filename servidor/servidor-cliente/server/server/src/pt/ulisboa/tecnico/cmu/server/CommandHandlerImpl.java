package pt.ulisboa.tecnico.cmu.server;

import pt.ulisboa.tecnico.cmu.command.CommandHandler;
import pt.ulisboa.tecnico.cmu.command.HelloCommand;
import pt.ulisboa.tecnico.cmu.command.SignUpCommand;
import pt.ulisboa.tecnico.cmu.response.HelloResponse;
import pt.ulisboa.tecnico.cmu.response.Response;

public class CommandHandlerImpl implements CommandHandler {

	@Override
	public Response helloCommandHandle(HelloCommand hc) {
		System.out.println("Received: " + hc.getMessage());
		return new HelloResponse("Hi from Server!");
	}

	@Override
	public Response signUpCommandHandle(SignUpCommand sup) {
		System.out.println("Username: " + sup.getUsername() + "  Code: " + sup.getCode());
		return null;
	}
	
}
