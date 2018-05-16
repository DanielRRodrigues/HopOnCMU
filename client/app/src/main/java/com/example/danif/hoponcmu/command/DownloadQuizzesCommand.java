package com.example.danif.hoponcmu.command;

public class DownloadQuizzesCommand implements Command {

  private static final long serialVersionUID = 1974509871547525317L;

  private String monument;

  public DownloadQuizzesCommand(String monument) {
    this.monument = monument;
  }

  @Override
  public void handle(CommandHandler ch) {
    ch.handle(this);
  }

}
