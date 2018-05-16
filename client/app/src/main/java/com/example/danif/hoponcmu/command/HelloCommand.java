package com.example.danif.hoponcmu.command;

public class HelloCommand implements Command {

  private static final long serialVersionUID = -8807331723807741905L;
  private String message;

  public HelloCommand(String message) {
    this.message = message;
  }

  @Override
  public void handle(CommandHandler chi) {
    chi.handle(this);
  }

  public String getMessage() {
    return this.message;
  }
}
