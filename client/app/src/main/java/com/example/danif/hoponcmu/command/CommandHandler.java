package com.example.danif.hoponcmu.command;

import com.example.danif.hoponcmu.client.CommandHandlerImpl;
import com.example.danif.hoponcmu.response.Response;

public interface CommandHandler {
    void handle(Command c);
}
