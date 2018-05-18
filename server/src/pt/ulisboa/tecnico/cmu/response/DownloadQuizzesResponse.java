package pt.ulisboa.tecnico.cmu.response;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DownloadQuizzesResponse implements Response {

	private static final long serialVersionUID = 5856310459259595752L;

	private boolean error;
	private List<QuizResponseObject> quizzes;

	public DownloadQuizzesResponse(boolean error) {
		this.error = error;
		this.quizzes = null;
	}

	public DownloadQuizzesResponse(List<QuizResponseObject> quizzes) {
		this.error = false;
		this.quizzes = new ArrayList<QuizResponseObject>(quizzes);
	}

	@Override
	public void handle(ResponseHandler rh) {
		rh.handle(this);
	}

	public boolean getError() {
		return error;
	}

	public List<QuizResponseObject> getQuizzes() {
		return Collections.unmodifiableList(this.quizzes);
	}
}
