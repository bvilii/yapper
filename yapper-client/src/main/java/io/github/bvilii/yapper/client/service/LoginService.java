package io.github.bvilii.yapper.client.service;

import io.github.bvilii.yapper.client.network.ServerSession;
import io.github.bvilii.yapper.shared.model.Message;
import io.github.bvilii.yapper.shared.model.MessageType;

import java.util.concurrent.CompletableFuture;

public class LoginService {
    private final ServerSession session;
    private CompletableFuture<Boolean> loginResult;

    public LoginService(ServerSession session) {
        this.session = session;
    }

    public CompletableFuture<Boolean> attemptLogin(String username) {
        loginResult = new  CompletableFuture<>();
        session.sendMessage(new Message("", username, MessageType.JOIN));
        return loginResult;
    }

    public void onLoginError() {
        loginResult.complete(false);
    }

    public void onLoginSuccess(String username) {
        session.setUsername(username);
        loginResult.complete(true);
    }




}
