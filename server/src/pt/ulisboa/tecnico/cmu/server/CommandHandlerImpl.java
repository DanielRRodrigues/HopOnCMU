package pt.ulisboa.tecnico.cmu.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pt.ulisboa.tecnico.cmu.command.CommandHandler;
import pt.ulisboa.tecnico.cmu.command.DownloadQuizzesCommand;
import pt.ulisboa.tecnico.cmu.command.GetTourDetailsCommand;
import pt.ulisboa.tecnico.cmu.command.LoginCommand;
import pt.ulisboa.tecnico.cmu.command.LogoutCommand;
import pt.ulisboa.tecnico.cmu.command.SignUpCommand;
import pt.ulisboa.tecnico.cmu.command.UpdateRankingCommand;
import pt.ulisboa.tecnico.cmu.response.DownloadQuizzesResponse;
import pt.ulisboa.tecnico.cmu.response.GetTourDetailsResponse;
import pt.ulisboa.tecnico.cmu.response.LoginResponse;
import pt.ulisboa.tecnico.cmu.response.LogoutResponse;
import pt.ulisboa.tecnico.cmu.response.QuestionResponseObject;
import pt.ulisboa.tecnico.cmu.response.QuizResponseObject;
import pt.ulisboa.tecnico.cmu.response.Response;
import pt.ulisboa.tecnico.cmu.response.ScoreResponseObject;
import pt.ulisboa.tecnico.cmu.response.SignUpResponse;
import pt.ulisboa.tecnico.cmu.response.UpdateRankingResponse;

public class CommandHandlerImpl implements CommandHandler {

	private Map<String, Account> sessions = new HashMap<String, Account>();
	private List<Tour> tours = new ArrayList<Tour>();

	@Override
	public Response handle(LoginCommand lc) {
		String username = lc.getUsername();
		String code = lc.getCode();
		System.out.println("Login: username: " + username + " code: " + code);
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

	@Override
	public Response handle(GetTourDetailsCommand gtdc) {
		String sessionId = gtdc.getSessionId();
		String currentLocationName = gtdc.getCurrentLocation();
		System.out.println("GetTourDetails: sessionId: " + sessionId + " currentLocationName: " + currentLocationName);
		Account account = this.sessions.get(sessionId);
		if (account == null)
			return new GetTourDetailsResponse(true);
		Tour tour = account.getTour();
		String tourName = tour.getName();
		List<Location> locations = tour.getLocations();
		List<String> locationsName = new ArrayList<String>();
		for (Location l : locations) {
			locationsName.add(l.getName());
		}
		Location currentLocation = tour.getLocationByName(currentLocationName);
		if (currentLocationName != null && currentLocation == null)
			return new GetTourDetailsResponse(true);
		Location nextLocation = null;
		if (currentLocationName == null) {
			if (account.getNextLocation() == null
					&& account.getLastLocation() == null) {
				currentLocation = tour.getNextLocation(null);
				nextLocation = tour.getNextLocation(currentLocation);
			} else {
				currentLocation = account.getLastLocation();
				nextLocation = account.getNextLocation();
			}
		} else {
			nextLocation = tour.getNextLocation(currentLocation);
		}
		account.setLastLocation(currentLocation);
		account.setNextLocation(nextLocation);
		return new GetTourDetailsResponse(tourName, locationsName,
				currentLocation.getName(), nextLocation.getName());
	}

	public Response handle(DownloadQuizzesCommand dqc) {
		String sessionId = dqc.getSessionId();
		String currentLocationName = dqc.getCurrentLocation();
		Account account = this.sessions.get(sessionId);
		if (account == null || account.getLastLocation() == null)
			return new DownloadQuizzesResponse(true);
		if (!account.getLastLocation().getName().equals(currentLocationName))
			return new DownloadQuizzesResponse(true);
		Location currentLocation = account.getLastLocation();
		List<Quiz> quizzes = currentLocation.getQuizzes();
		List<QuizResponseObject> quizzesResponse = new ArrayList<QuizResponseObject>();
		for (Quiz q : quizzes) {
			List<Question> questions = q.getQuestions();
			List<QuestionResponseObject> questionsResponse = new ArrayList<QuestionResponseObject>();
			for (Question qt : questions) {
				questionsResponse.add(new QuestionResponseObject(qt.getText(),
						qt.getAnswer(), qt.getOptions()));
			}
			quizzesResponse.add(new QuizResponseObject(q.getTitle(),
					questionsResponse));
		}
		return new DownloadQuizzesResponse(quizzesResponse);
	}

	public Response handle(UpdateRankingCommand urc) {
		String sessionId = urc.getSessionId();
		Account account = this.sessions.get(sessionId);
		if (account == null)
			return new UpdateRankingResponse(true);
		Tour tour = account.getTour();
		List<Score> scores = tour.getAllScores(true);
		List<ScoreResponseObject> scoresResponse = new ArrayList<ScoreResponseObject>();
		for (Score s : scores) {
			scoresResponse.add(new ScoreResponseObject(s.getAccount()
					.getUsername(), s.getTotalScore(), s.getTotalQuestions()));
		}
		return new UpdateRankingResponse(scoresResponse);
	}

	private Tour getTourById(String id) {
		for (Tour t : this.tours) {
			if (t.getId().equals(id))
				return t;
		}
		return null;
	}

	public void testing() {
		// Tour
		Tour testTour = new Tour("TST", "TestTour");
		// Available Codes
		testTour.addAvailableCode("TST123");
		testTour.addAvailableCode("124");
		testTour.addAvailableCode("TST125");
		testTour.addAvailableCode("TST126");
		// Questions
		List<String> testOptions = new ArrayList<String>();
		testOptions.add("A");
		testOptions.add("B");
		testOptions.add("C");
		testOptions.add("D");
		List<Question> testQuestions = new ArrayList<Question>();
		Question testQuestion0 = new Question("TestQuestion0", "A", testOptions);
		Question testQuestion1 = new Question("TestQuestion1", "B", testOptions);
		Question testQuestion2 = new Question("TestQuestion2", "C", testOptions);
		Question testQuestion3 = new Question("TestQuestion3", "D", testOptions);
		testQuestions.add(testQuestion0);
		testQuestions.add(testQuestion1);
		testQuestions.add(testQuestion2);
		testQuestions.add(testQuestion3);
		// Locations
		Location testLocation0 = new Location("L0");
		Location testLocation1 = new Location("L1");
		Location testLocation2 = new Location("L2");
		Location testLocation3 = new Location("L3");
		Location testLocation4 = new Location("L4");
		testTour.addLocation(testLocation0);
		testTour.addLocation(testLocation1);
		testTour.addLocation(testLocation2);
		testTour.addLocation(testLocation3);
		testTour.addLocation(testLocation4);
		// Quizzes
		Quiz testQuiz0 = new Quiz("TestQuiz0", testLocation0, testQuestions);
		Quiz testQuiz1 = new Quiz("TestQuiz1", testLocation0, testQuestions);
		Quiz testQuiz2 = new Quiz("TestQuiz2", testLocation0, testQuestions);
		Quiz testQuiz3 = new Quiz("TestQuiz44", testLocation1, testQuestions);
		Quiz testQuiz4 = new Quiz("TestQuiz555", testLocation2, testQuestions);
		// Available account
		Account testAccount = new Account("testUser", "TST125", testTour);
		testAccount.setLastLocation(testLocation1);
		testAccount.setNextLocation(testLocation2);
		testTour.addAccount(testAccount);
		// Active account
		Account usedAccount = new Account("usedUser", "TST126", testTour);
		usedAccount.setSessionId("123455123");
		testTour.addAccount(usedAccount);
		// Scores
		Score testScore0 = new Score(testTour, testAccount);
		testScore0.updateQuizScore(testQuiz0, 0);
		testScore0.updateQuizScore(testQuiz1, 3);
		testScore0.updateQuizScore(testQuiz3, 1);
		Score testScore1 = new Score(testTour, usedAccount);
		testScore1.updateQuizScore(testQuiz0, 3);
		testScore1.updateQuizScore(testQuiz2, 4);
		// Add tour to server
		this.tours.add(testTour);
	}
}
