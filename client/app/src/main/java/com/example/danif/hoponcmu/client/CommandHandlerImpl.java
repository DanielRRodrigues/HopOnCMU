package com.example.danif.hoponcmu.client;

import com.example.danif.hoponcmu.command.Command;
import com.example.danif.hoponcmu.command.CommandHandler;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by danif on 11-May-18.
 */

public class CommandHandlerImpl implements CommandHandler{
    @Override
    public void handle(Command c) {
        System.out.println("--- hue");
        Socket server = null;
        try {
            server = new Socket("10.0.2.2", 9090);

            ObjectOutputStream oos = new ObjectOutputStream(server.getOutputStream());
            oos.writeObject(c);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
