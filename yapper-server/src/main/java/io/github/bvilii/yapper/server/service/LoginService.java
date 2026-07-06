package io.github.bvilii.yapper.server.service;

import io.github.bvilii.yapper.server.network.ClientSession;
import io.github.bvilii.yapper.shared.model.Message;
import io.github.bvilii.yapper.shared.model.MessageType;

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
        session.sendMessage(new Message(username, "server", MessageType.JOIN_SUCCESS));
    }
}
