package io.github.bvilii.yapper.client.service;

import io.github.bvilii.yapper.client.network.ServerSession;
import io.github.bvilii.yapper.client.ui.ClientDisplay;
import io.github.bvilii.yapper.shared.model.Message;
import io.github.bvilii.yapper.shared.model.MessageType;

public class ChatService {
    private final ServerSession session;
    private final ClientDisplay display;
    private final LoginService loginService;

    public ChatService(ServerSession session, ClientDisplay display, LoginService loginService) {
        this.session = session;
        this.display = display;
        this.loginService = loginService;
    }

    public void onUserInput(String content) {
        Message message = new Message(content, session.getUsername(), MessageType.CHAT);

        session.sendMessage(message);
    }

    public void onMessageFromServer(Message message) {
        switch (message.getType()) {
            case CHAT: {
                display.displayChatMessage(message.getSender(), message.getContent());
                break;
            }
            case JOIN_ERROR: {
                loginService.onLoginError();
            }
            case JOIN_SUCCESS: {
                loginService.onLoginSuccess(message.getContent());
            }
        }
    }
}
