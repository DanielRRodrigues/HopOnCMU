package pt.ulisboa.tecnico.cmu.response;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UpdateRankingResponse implements Response {

	private static final long serialVersionUID = 5797996666100641325L;

	private boolean error;
	private List<ScoreResponseObject> scores;

	public UpdateRankingResponse(boolean error) {
		this.error = error;
		this.scores = null;
	}

	public UpdateRankingResponse(List<ScoreResponseObject> scores) {
		this.error = false;
		this.scores = new ArrayList<ScoreResponseObject>(scores);
	}

	@Override
	public void handle(ResponseHandler rh) {
		rh.handle(this);
	}

	public boolean getError() {
		return error;
	}

	public List<ScoreResponseObject> getScores() {
		return Collections.unmodifiableList(this.scores);
	}
}
