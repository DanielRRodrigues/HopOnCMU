package pt.ulisboa.tecnico.cmu.command;

import pt.ulisboa.tecnico.cmu.response.Response;

public interface CommandHandler {

	Response handle(SignUpCommand sc);

	Response handle(LoginCommand lc);

	Response handle(LogoutCommand lc);

	Response handle(GetTourDetailsCommand gtdc);

	Response handle(DownloadQuizzesCommand dqc);
}
