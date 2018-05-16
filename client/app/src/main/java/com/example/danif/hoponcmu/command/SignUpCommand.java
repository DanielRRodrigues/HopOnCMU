package com.example.danif.hoponcmu.command;

/**
 * Created by danif on 11-May-18.
 */

public class SignUpCommand implements Command {

  private static final long serialVersionUID = -7490260852113884025L;

  private String username;
  private String code;

  public SignUpCommand(String username, String code) {
    this.username = username;
    this.code = code;
  }

  @Override
  public void handle(CommandHandler ch) {
    ch.handle(this);
  }

}
