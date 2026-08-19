package io.github.bvilii.yapper.client.service;

import io.github.bvilii.yapper.client.network.ServerSession;
import io.github.bvilii.yapper.client.ui.ClientDisplay;
import io.github.bvilii.yapper.shared.model.message.*;

public class MessageRouter {
    private final ServerSession session;
    private final ClientDisplay display;
    private final LoginService loginService;
    private final RegisterService registerService;
    private final TokenLoginService tokenLoginService;

    public MessageRouter(ServerSession session, ClientDisplay display, LoginService loginService, RegisterService registerService, TokenLoginService tokenLoginService) {
        this.session = session;
        this.display = display;
        this.loginService = loginService;
        this.registerService = registerService;
        this.tokenLoginService = tokenLoginService;
    }

    public void onUserInput(String content) {
        Message message = new ChatMessage(session.getUsername(), content);
        session.sendMessage(message);
    }

    public void onMessageFromServer(Message message) {
        switch (message.getType()) {
            case CHAT: {
                ChatMessage chat = (ChatMessage) message;
                display.displayChatMessage(chat.getSender(), chat.getContent());
                break;
            }
            case LOGIN_ERROR: {
                loginService.onLoginError();
                break;
            }
            case LOGIN_SUCCESS: {
                LoginResponseMessage response = (LoginResponseMessage) message;
                loginService.onLoginSuccess(response);
                break;
            }
            case REGISTER_ERROR: {
                registerService.onRegisterError();
                break;
            }
            case REGISTER_SUCCESS: {
                RegisterResponseMessage response = (RegisterResponseMessage) message;
                registerService.onRegisterSuccess(response);
                break;
            }
            case TOKEN_LOGIN_SUCCESS: {
                TokenLoginResponseMessage response = (TokenLoginResponseMessage) message;
                tokenLoginService.onTokenLoginSuccess(response);
                break;
            }
            case TOKEN_LOGIN_ERROR: {
                tokenLoginService.onTokenLoginError();
                break;
            }

            default : {
                break;
            }
        }
    }

    public void onConnectionLost() {
        display.displayInfo("Lost connection to server. Exiting...");
    }
}
