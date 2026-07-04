package io.github.bvilii.yapper.server.service;

import io.github.bvilii.yapper.server.model.Message;
import io.github.bvilii.yapper.server.model.MessageType;
import io.github.bvilii.yapper.server.network.ClientSession;

public class LoginService {
    private final ClientRegistry registry;

    public LoginService(ClientRegistry registry) {
        this.registry = registry;
    }

    public void handleJoin(ClientSession session, Message message) {
        String username = message.getSender();

        if(registry.isUsernameTaken(username)) {
            session.sendMessage(new Message("Username already taken", "server", MessageType.JOIN_ERROR));
            return;
        }

        session.setUsername(username);
        registry.addSession(username, session);
        session.sendMessage(new Message("", "server", MessageType.JOIN_SUCCESS));
    }
}
