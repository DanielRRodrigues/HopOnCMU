package pt.ulisboa.tecnico.cmu.response;

public class LoginResponse implements Response {

  private static final long serialVersionUID = 3532085036234447629L;

  private String sessionId;

  public LoginResponse(String sessionId) {
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
