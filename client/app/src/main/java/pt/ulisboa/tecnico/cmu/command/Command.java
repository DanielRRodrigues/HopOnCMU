package pt.ulisboa.tecnico.cmu.command;

import pt.ulisboa.tecnico.cmu.response.Response;

import java.io.Serializable;

public interface Command extends Serializable {
  Response handle(CommandHandler ch);
}

