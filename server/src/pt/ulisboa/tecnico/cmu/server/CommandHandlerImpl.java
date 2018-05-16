package pt.ulisboa.tecnico.cmu.server;

import java.util.ArrayList;
import java.util.List;

import pt.ulisboa.tecnico.cmu.command.CommandHandler;
import pt.ulisboa.tecnico.cmu.command.DownloadQuizzesCommand;
import pt.ulisboa.tecnico.cmu.command.HelloCommand;
import pt.ulisboa.tecnico.cmu.command.LogInCommand;
import pt.ulisboa.tecnico.cmu.command.SignUpCommand;
import pt.ulisboa.tecnico.cmu.response.HelloResponse;
import pt.ulisboa.tecnico.cmu.response.Response;

public class CommandHandlerImpl implements CommandHandler {
	
	private List<String> availableCodes = new ArrayList<String>();
	private List<Account> accounts = new ArrayList<Account>();

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
	public Response handle(LogInCommand lc) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response handle(SignUpCommand suc) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
