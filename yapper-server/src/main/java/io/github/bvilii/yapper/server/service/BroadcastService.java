package io.github.bvilii.yapper.server.service;


import io.github.bvilii.yapper.server.network.ClientSession;
import io.github.bvilii.yapper.shared.model.message.Message;
import io.github.bvilii.yapper.shared.model.message.MessageType;

public class BroadcastService {
    private final ClientRegistry registry;

    public BroadcastService(ClientRegistry registry) {
        this.registry = registry;
    }

    public void broadcast(ClientSession sender, Message message) {

        if(!sender.isAuthenticated()){
            return;
        }

        Message outgoingMessage = new Message(message.getContent(), sender.getUsername(), MessageType.CHAT);

        for (ClientSession session : registry.getSessions()) {
            if (session != sender) {
                session.sendMessage(outgoingMessage);
            }
        }

    }
}
