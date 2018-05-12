package com.example.danif.hoponcmu.command;

import com.example.danif.hoponcmu.response.Response;

import java.io.Serializable;

public interface Command extends Serializable {
    public void handle(CommandHandler ch);
}

