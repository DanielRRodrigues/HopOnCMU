package pt.ulisboa.tecnico.cmu.command;

import pt.ulisboa.tecnico.cmu.response.Response;

public interface CommandHandler {
  Response handle(HelloCommand hc);
  Response handle(DownloadQuizzesCommand dqc);
  Response handle(LoginCommand lc);
  Response handle(SignUpCommand suc);
}
