package io.github.bvilii.yapper.server.service;

import io.github.bvilii.yapper.server.network.ClientSession;
import io.github.bvilii.yapper.shared.model.message.LoginRequestMessage;
import io.github.bvilii.yapper.shared.model.message.Message;
import io.github.bvilii.yapper.shared.model.message.RegisterRequestMessage;

public class MessageRouter implements MessageListener {
    private final ClientRegistry registry;
    private final LoginService loginService;
    private final BroadcastService broadcastService;
    private final RegisterService registerService;

    public MessageRouter(ClientRegistry registry, LoginService loginService, BroadcastService broadcastService, RegisterService registerService) {
        this.registry = registry;
        this.loginService = loginService;
        this.broadcastService = broadcastService;
        this.registerService = registerService;
    }

    @Override
    public void onMessageReceived(ClientSession session, Message message) {
        switch (message.getType()) {

            case REGISTER: {
                registerService.handleRegister((RegisterRequestMessage) message, session);
                break;
            }

            case LOGIN: {
                loginService.handleLogin(session, (LoginRequestMessage) message);
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
