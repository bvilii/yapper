package io.github.bvilii.yapper.client.network;

import com.google.gson.Gson;
import io.github.bvilii.yapper.shared.model.message.Message;

import java.io.IOException;

public class ServerSession {
    private String username;
    private final ServerConnection connection;
    private final Gson gson;

    public ServerSession(ServerConnection connection) {
        this.connection = connection;
        this.gson = new Gson();
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
