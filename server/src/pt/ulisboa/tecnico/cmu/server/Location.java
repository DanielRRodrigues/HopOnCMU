package pt.ulisboa.tecnico.cmu.server;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Location implements Serializable {

	private static final long serialVersionUID = 3669996767495964532L;

	private String name;
	private List<Quiz> quizzes;

	public Location(String name, List<Quiz> quizzes) {
		this.name = name;
		this.quizzes = new ArrayList<Quiz>(quizzes);
	}

	public Location(String name) {
		this.name = name;
		this.quizzes = new ArrayList<Quiz>();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Location))
			return false;
		Location other = (Location) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Quiz> getQuizzes() {
		return Collections.unmodifiableList(this.quizzes);
	}

	public void setOptions(List<Quiz> quizzes) {
		this.quizzes = new ArrayList<Quiz>(quizzes);
	}

	public boolean addQuiz(Quiz quiz) {
		return this.quizzes.add(quiz);
	}

	public boolean removeQuiz(String quiz) {
		return this.quizzes.remove(quiz);
	}

}
