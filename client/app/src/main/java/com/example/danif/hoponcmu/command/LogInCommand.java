package com.example.danif.hoponcmu.command;

public class LogInCommand implements Command {

  private static final long serialVersionUID = -8464572709559954332L;

  private String username;
  private String code;

  public LogInCommand(String username, String code) {
    this.username = username;
    this.code = code;
  }

  @Override
  public void handle(CommandHandler ch) {
    ch.handle(this);
  }

}
