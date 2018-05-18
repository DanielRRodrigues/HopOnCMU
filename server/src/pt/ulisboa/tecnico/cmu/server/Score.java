package pt.ulisboa.tecnico.cmu.server;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Score implements Serializable, Comparable<Score> {

	private static final long serialVersionUID = 5873448885151347406L;

	private Tour tour;
	private Account account;
	private Map<Quiz, Integer> quizzesScore;

	public Score(Tour tour, Account account) {
		this.tour = tour;
		this.account = account;
		this.quizzesScore = new HashMap<Quiz, Integer>();
		this.account.setScore(this);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((account == null) ? 0 : account.hashCode());
		result = prime * result + ((tour == null) ? 0 : tour.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Score))
			return false;
		Score other = (Score) obj;
		if (account == null) {
			if (other.account != null)
				return false;
		} else if (!account.equals(other.account))
			return false;
		if (tour == null) {
			if (other.tour != null)
				return false;
		} else if (!tour.equals(other.tour))
			return false;
		return true;
	}

	@Override
	public int compareTo(Score other) {
		int myTotal = this.getTotalScore();
		int otherTotal = other.getTotalScore();
		return myTotal > otherTotal ? -1 : myTotal < otherTotal ? 1 : 0;
	}

	public Tour getTour() {
		return tour;
	}

	public void setTour(Tour tour) {
		this.tour = tour;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public int getQuizScore(Quiz quiz) {
		Integer score = this.quizzesScore.get(quiz);
		if (score == null)
			return -1;
		return score.intValue();
	}

	public void updateQuizScore(Quiz quiz, int score) {
		int currentScore = 0;
		if (this.quizzesScore.containsKey(quiz)) {
			currentScore = this.quizzesScore.get(quiz);
		}
		this.quizzesScore.put(quiz, currentScore + score);
	}

	public boolean removeQuizScore(Quiz quiz) {
		return this.quizzesScore.remove(quiz) != null;
	}

	public int getTotalScore() {
		int totalScore = 0;
		for (Map.Entry<Quiz, Integer> entry : this.quizzesScore.entrySet()) {
			totalScore += entry.getValue().intValue();
		}
		return totalScore;
	}

	public int getTotalQuestions() {
		int totalQuestions = 0;
		for (Quiz quiz : this.quizzesScore.keySet()) {
			totalQuestions += quiz.getQuestions().size();
		}
		return totalQuestions;
	}
}
