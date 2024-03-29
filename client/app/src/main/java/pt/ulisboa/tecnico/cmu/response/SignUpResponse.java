package pt.ulisboa.tecnico.cmu.response;

public class SignUpResponse implements Response {

  private static final long serialVersionUID = 5902122372240663924L;

  private String sessionId;

  public SignUpResponse(String sessionId) {
    this.sessionId = sessionId;
  }

  @Override
  public void handle(ResponseHandler rh) {
    rh.handle(this);
  }

  public String getSessionId() {
    return sessionId;
  }

}
