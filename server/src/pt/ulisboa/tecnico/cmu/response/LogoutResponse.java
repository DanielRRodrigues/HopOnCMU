package pt.ulisboa.tecnico.cmu.response;

public class LogoutResponse implements Response {

  private static final long serialVersionUID = 6831027213648782458L;

  private boolean loggedOut;

  public LogoutResponse(boolean loggedOut) {
    this.loggedOut = loggedOut;
  }

  @Override
  public void handle(ResponseHandler rh) {
    rh.handle(this);
  }

  public boolean getLoggedOut() {
    return loggedOut;
  }

}
