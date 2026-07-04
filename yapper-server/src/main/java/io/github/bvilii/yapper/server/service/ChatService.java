package io.github.bvilii.yapper.server.service;

import io.github.bvilii.yapper.server.model.Message;
import io.github.bvilii.yapper.server.network.ClientSession;

public class ChatService implements MessageListener {
    private final ClientRegistry registry;
    private final LoginService loginService;
    private final BroadcastService broadcastService;

    public ChatService(ClientRegistry registry, LoginService loginService, BroadcastService broadcastService) {
        this.registry = registry;
        this.loginService = loginService;
        this.broadcastService = broadcastService;
    }

    @Override
    public void onMessageReceived(ClientSession session, Message message) {
        switch (message.getType()) {

            case JOIN: {
                loginService.handleJoin(session, message);
                break;
            }

            case CHAT: {
                broadcastService.broadcast(session, message);
                break;
            }

            default: {

                break;
            }
        }
    }
}
