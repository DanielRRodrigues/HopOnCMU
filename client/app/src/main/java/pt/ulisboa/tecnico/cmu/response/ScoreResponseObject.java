package pt.ulisboa.tecnico.cmu.response;

import java.io.Serializable;

public class ScoreResponseObject implements Serializable {

	private static final long serialVersionUID = -8047342766515530391L;

	private String username;
	private int correctAnswers;
	private int totalQuestions;

	public ScoreResponseObject(String username, int correctAnswers,
			int totalQuestions) {
		this.username = username;
		this.correctAnswers = correctAnswers;
		this.totalQuestions = totalQuestions;
	}

	public String getUsername() {
		return username;
	}

	public int getCorrectAnswers() {
		return correctAnswers;
	}

	public int getTotalQuestions() {
		return totalQuestions;
	}

}
