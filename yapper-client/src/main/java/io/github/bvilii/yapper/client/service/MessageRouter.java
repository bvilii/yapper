package io.github.bvilii.yapper.client.service;

import io.github.bvilii.yapper.client.network.ServerSession;
import io.github.bvilii.yapper.client.ui.ClientDisplay;
import io.github.bvilii.yapper.shared.model.message.ChatMessage;
import io.github.bvilii.yapper.shared.model.message.LoginResponseMessage;
import io.github.bvilii.yapper.shared.model.message.Message;

public class MessageRouter {
    private final ServerSession session;
    private final ClientDisplay display;
    private final LoginService loginService;
    private final RegisterService registerService;

    public MessageRouter(ServerSession session, ClientDisplay display, LoginService loginService, RegisterService registerService) {
        this.session = session;
        this.display = display;
        this.loginService = loginService;
        this.registerService = registerService;
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
                loginService.onLoginSuccess();
                break;
            }
            case REGISTER_ERROR: {
                registerService.onRegisterError();
                break;
            }
            case REGISTER_SUCCESS: {
                registerService.onRegisterSuccess();
                break;
            }
        }
    }

    public void onConnectionLost() {
        display.displayInfo("Lost connection to server. Exiting...");
    }
}
