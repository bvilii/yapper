package io.github.bvilii.yapper.server.service;

import io.github.bvilii.yapper.server.network.ClientSession;
import io.github.bvilii.yapper.shared.model.message.Message;

public class MessageRouter implements MessageListener {
    private final ClientRegistry registry;
    private final LoginService loginService;
    private final BroadcastService broadcastService;

    public MessageRouter(ClientRegistry registry, LoginService loginService, BroadcastService broadcastService) {
        this.registry = registry;
        this.loginService = loginService;
        this.broadcastService = broadcastService;
    }

    @Override
    public void onMessageReceived(ClientSession session, Message message) {
        switch (message.getType()) {

            case LOGIN: {
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

    @Override
    public void onClientDisconnected(ClientSession session) {
        registry.removeSession(session.getUsername());
    }
}
