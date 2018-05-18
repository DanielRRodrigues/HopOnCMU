package pt.ulisboa.tecnico.cmu.response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuestionResponseObject implements Serializable {

	private static final long serialVersionUID = 5304947550279239823L;

	private String text;
	private String answer;
	private List<String> options;

	public QuestionResponseObject(String text, String answer,
			List<String> options) {
		this.text = text;
		this.answer = answer;
		this.options = new ArrayList<String>(options);
	}

	public String getText() {
		return text;
	}

	public String getAnswer() {
		return answer;
	}

	public List<String> getOptions() {
		return Collections.unmodifiableList(this.options);
	}

}
