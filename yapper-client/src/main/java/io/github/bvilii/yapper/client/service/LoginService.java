package io.github.bvilii.yapper.client.service;

import io.github.bvilii.yapper.client.network.ServerSession;
import io.github.bvilii.yapper.client.storage.TokenStorage;
import io.github.bvilii.yapper.shared.model.message.LoginRequestMessage;
import io.github.bvilii.yapper.shared.model.message.LoginResponseMessage;
import io.github.bvilii.yapper.shared.model.message.Message;
import io.github.bvilii.yapper.shared.model.message.MessageType;

import java.util.concurrent.CompletableFuture;

public class LoginService {
    private final ServerSession session;
    private CompletableFuture<Boolean> loginResult;
    private String username;
    private final TokenStorage  tokenStorage;

    public LoginService(ServerSession session, TokenStorage tokenStorage) {
        this.session = session;
        this.tokenStorage = tokenStorage;
    }

    public CompletableFuture<Boolean> attemptLogin(String username, String password) {
        loginResult = new  CompletableFuture<>();
        session.sendMessage(new LoginRequestMessage(username, password));
        this.username = username;
        return loginResult;
    }

    public void onLoginError() {
        loginResult.complete(false);
    }

    public void onLoginSuccess(LoginResponseMessage response) {
        session.setUsername(username);

        String token = response.getToken();
        tokenStorage.saveToken(token);

        loginResult.complete(true);
    }

}
