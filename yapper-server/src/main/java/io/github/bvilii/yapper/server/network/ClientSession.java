package io.github.bvilii.yapper.server.network;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.bvilii.yapper.shared.model.message.Message;


import java.io.IOException;

public class ClientSession {
    private String username;
    private final ClientConnection connection;
    private final ObjectMapper mapper = new ObjectMapper();


    public ClientSession(ClientConnection connection) {
        this.connection = connection;
    }

    public void sendMessage(Message message) {
        try {
            connection.sendLine(mapper.writeValueAsString(message));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public Message receiveMessage() throws IOException {
        String line = connection.readLine();

        if(line == null){
            return null;
        }

        return mapper.readValue(line, Message.class);
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public boolean isAuthenticated() {
        return username != null;
    }
}
