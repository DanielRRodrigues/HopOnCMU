package pt.ulisboa.tecnico.cmu.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pt.ulisboa.tecnico.cmu.command.CommandHandler;
import pt.ulisboa.tecnico.cmu.command.DownloadQuizzesCommand;
import pt.ulisboa.tecnico.cmu.command.HelloCommand;
import pt.ulisboa.tecnico.cmu.command.LoginCommand;
import pt.ulisboa.tecnico.cmu.command.SignUpCommand;
import pt.ulisboa.tecnico.cmu.response.HelloResponse;
import pt.ulisboa.tecnico.cmu.response.Response;

public class CommandHandlerImpl implements CommandHandler {

	private Map<String, Account> sessions = new HashMap<String, Account>();
	private List<Tour> tours = new ArrayList<Tour>();

	@Override
	public Response handle(HelloCommand hc) {
		System.out.println("Received: " + hc.getMessage());
		return new HelloResponse("Hi from Server!");
	}

	@Override
	public Response handle(DownloadQuizzesCommand dqc) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response handle(LoginCommand lc) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response handle(SignUpCommand suc) {
		// TODO Auto-generated method stub
		return null;
	}

}
