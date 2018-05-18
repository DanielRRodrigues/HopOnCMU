package pt.ulisboa.tecnico.cmu.server;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Quiz implements Serializable {

	private static final long serialVersionUID = -5126409282428505252L;

	private String title;
	private Location location;
	private List<Question> questions;

	public Quiz(String title, Location location) {
		this.title = title;
		this.location = location;
		this.questions = new ArrayList<Question>();
		this.location.addQuiz(this);
	}

	public Quiz(String title, Location location, List<Question> questions) {
		this.title = title;
		this.location = location;
		this.questions = new ArrayList<Question>(questions);
		this.location.addQuiz(this);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((location == null) ? 0 : location.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Quiz))
			return false;
		Quiz other = (Quiz) obj;
		if (location == null) {
			if (other.location != null)
				return false;
		} else if (!location.equals(other.location))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public List<Question> getQuestions() {
		return Collections.unmodifiableList(this.questions);
	}

	public void setQuestions(List<Question> questions) {
		this.questions = new ArrayList<Question>(questions);
	}

	public boolean addOption(Question question) {
		return this.questions.add(question);
	}

	public boolean removeOption(Question question) {
		return this.questions.remove(question);
	}

}
