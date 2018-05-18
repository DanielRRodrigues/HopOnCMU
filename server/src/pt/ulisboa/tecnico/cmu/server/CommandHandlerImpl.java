package pt.ulisboa.tecnico.cmu.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pt.ulisboa.tecnico.cmu.command.CommandHandler;
import pt.ulisboa.tecnico.cmu.command.LoginCommand;
import pt.ulisboa.tecnico.cmu.command.LogoutCommand;
import pt.ulisboa.tecnico.cmu.command.SignUpCommand;
import pt.ulisboa.tecnico.cmu.response.LoginResponse;
import pt.ulisboa.tecnico.cmu.response.LogoutResponse;
import pt.ulisboa.tecnico.cmu.response.Response;
import pt.ulisboa.tecnico.cmu.response.SignUpResponse;

public class CommandHandlerImpl implements CommandHandler {

	private Map<String, Account> sessions = new HashMap<String, Account>();
	private List<Tour> tours = new ArrayList<Tour>();

	@Override
	public Response handle(LoginCommand lc) {
		String username = lc.getUsername();
		String code = lc.getCode();
		String id;
		if (code.length() > 3)
			id = code.substring(0, 3);
		else
			id = code;
		Tour tour = getTourById(id);
		if (tour == null)
			return new LoginResponse(null);
		Account account = tour.getAcountByUsername(username);
		if (account == null || !account.getCode().equals(code))
			return new LoginResponse(null);
		sessions.remove(account.getSessionId());
		String sessionId = account.generateSessionId();
		sessions.put(sessionId, account);
		return new LoginResponse(sessionId);
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
		if (tour == null || tour.getAcountByUsername(username) != null)
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

	@Override
	public Response handle(LogoutCommand lc) {
		String sessionId = lc.getSessionId();
		Account account = this.sessions.get(sessionId);
		if (account == null)
			return new LogoutResponse(false);
		account.setSessionId(null);
		this.sessions.remove(sessionId);
		return new LogoutResponse(true);
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
		testTour.addAvailableCode("TST125");
		testTour.addAvailableCode("TST126");
		Account testAccount = new Account("testUser", "TST125", testTour);
		testTour.addAccount(testAccount);
		Account usedAccount = new Account("usedUser", "TST126", testTour);
		usedAccount.setSessionId("123455123");
		testTour.addAccount(usedAccount);
		this.tours.add(testTour);
	}

}
