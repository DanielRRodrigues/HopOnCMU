package pt.ulisboa.tecnico.cmu.command;

import pt.ulisboa.tecnico.cmu.response.Response;

public interface CommandHandler {
	public Response handle(HelloCommand hc);
	public Response handle(DownloadQuizzesCommand dqc);
	public Response handle(LoginCommand lc);
	public Response handle(SignUpCommand suc);
}
