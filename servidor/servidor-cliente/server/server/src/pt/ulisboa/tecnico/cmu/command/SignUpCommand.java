package pt.ulisboa.tecnico.cmu.command;

import pt.ulisboa.tecnico.cmu.response.Response;

public class SignUpCommand implements Command{

	private static final long serialVersionUID = -7490260852113884025L;
	
	private String _username;
    private String _code;

	@Override
	public Response handle(CommandHandler ch) {
		ch.signUpCommandHandle(this);
		return null;
	}
	
	public String getUsername() {
        return _username;
    }
    public String getCode() {
        return _code;
    }

}
