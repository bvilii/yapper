package io.github.bvilii.yapper.server.service;

import io.github.bvilii.yapper.server.network.ClientSession;
import io.github.bvilii.yapper.shared.model.message.*;

public class MessageRouter implements MessageListener {
    private final ClientRegistry registry;
    private final LoginService loginService;
    private final BroadcastService broadcastService;
    private final RegisterService registerService;
    private final TokenLoginService tokenLoginService;

    public MessageRouter(ClientRegistry registry, LoginService loginService, BroadcastService broadcastService, RegisterService registerService, TokenLoginService tokenLoginService) {
        this.registry = registry;
        this.loginService = loginService;
        this.broadcastService = broadcastService;
        this.registerService = registerService;
        this.tokenLoginService = tokenLoginService;
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
                broadcastService.broadcast(session, (ChatMessage) message);
                break;
            }
            case TOKEN_LOGIN: {
                tokenLoginService.handleTokenLogin(session, (TokenLoginRequestMessage) message);
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
