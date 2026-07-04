package io.github.bvilii.yapper.server.network;

import com.google.gson.Gson;
import io.github.bvilii.yapper.server.model.Message;

import java.io.IOException;

public class ClientSession {
    private String username;
    private final ClientConnection connection;
    private final Gson gson;


    public ClientSession(ClientConnection connection) {
        this.connection = connection;
        this.gson = new  Gson();
    }

    public void sendMessage(Message message) {
        connection.sendLine(gson.toJson(message));
    }

    public Message receiveMessage() throws IOException {
        String line = connection.readLine();

        if(line == null){
            return null;
        }

        return gson.fromJson(line, Message.class);
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
