package pt.ulisboa.tecnico.cmu.server;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Question implements Serializable {

	private static final long serialVersionUID = -601610588550948437L;

	private String text;
	private String answer;
	private List<String> options;

	public Question(String text, String answer, List<String> options) {
		this.text = text;
		this.answer = answer;
		this.options = new ArrayList<String>(options);
	}

	public Question(String text, String answer) {
		this.text = text;
		this.answer = answer;
		this.options = new ArrayList<String>();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((answer == null) ? 0 : answer.hashCode());
		result = prime * result + ((text == null) ? 0 : text.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Question))
			return false;
		Question other = (Question) obj;
		if (answer == null) {
			if (other.answer != null)
				return false;
		} else if (!answer.equals(other.answer))
			return false;
		if (text == null) {
			if (other.text != null)
				return false;
		} else if (!text.equals(other.text))
			return false;
		return true;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public List<String> getOptions() {
		return Collections.unmodifiableList(this.options);
	}

	public void setOptions(List<String> options) {
		this.options = new ArrayList<String>(options);
	}

	public boolean addOption(String option) {
		return this.options.add(option);
	}

	public boolean removeOption(String option) {
		return this.options.remove(option);
	}

}
