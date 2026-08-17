package io.github.bvilii.yapper.server.service;


import io.github.bvilii.yapper.server.network.ClientSession;
import io.github.bvilii.yapper.shared.model.message.ChatMessage;

public class BroadcastService {
    private final ClientRegistry registry;

    public BroadcastService(ClientRegistry registry) {
        this.registry = registry;
    }

    public void broadcast(ClientSession sender, ChatMessage message) {

        if(!sender.isAuthenticated()){
            sender.sendMessage(new ChatMessage("System", "You are not authenticated!"));
            return;
        }

        ChatMessage outgoingMessage = new ChatMessage(sender.getUsername(), message.getContent());

        for (ClientSession session : registry.getAllSessions()) {
            if (session != sender) {
                session.sendMessage(outgoingMessage);
            }
        }

    }
}
