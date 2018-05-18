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
import pt.ulisboa.tecnico.cmu.response.SignUpResponse;

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
		String username = suc.getUsername();
		String code = suc.getCode();
		String id;
		if (code.length() > 3)
			id = code.substring(0, 3);
		else
			id = code;
		Tour tour = getTourById(id);

		if (tour == null)
			return new SignUpResponse(null);
		if (tour.useCode(code)) {
			Account a = new Account(username, code, tour);
			String sessionId = a.generateSessionId();
			tour.addAccount(a);
			sessions.put(sessionId, a);
			return new SignUpResponse(sessionId);
		}
		return new SignUpResponse(null);
	}

	private Tour getTourById(String id) {
		for (Tour t : this.tours) {
			if (t.getId().equals(id))
				return t;
		}
		return null;
	}

	public void testing() {
		Tour testTour = new Tour("TST", "TestTour");
		testTour.addAvailableCode("TST123");
		testTour.addAvailableCode("124");
		this.tours.add(testTour);
	}

}
