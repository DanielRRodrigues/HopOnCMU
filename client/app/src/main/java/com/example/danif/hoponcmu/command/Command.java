package com.example.danif.hoponcmu.command;

import java.io.Serializable;

public interface Command extends Serializable {

  public void handle(CommandHandler ch);
}

