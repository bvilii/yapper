package io.github.bvilii.yapper.client.service;

import io.github.bvilii.yapper.client.network.ServerSession;
import io.github.bvilii.yapper.client.storage.TokenStorage;
import io.github.bvilii.yapper.shared.model.message.TokenLoginRequestMessage;
import io.github.bvilii.yapper.shared.model.message.TokenLoginResponseMessage;

import java.util.concurrent.CompletableFuture;

public class TokenLoginService {
    private final ServerSession session;
    private CompletableFuture<Boolean> result;
    private final TokenStorage tokenStorage;

    public TokenLoginService(ServerSession session, TokenStorage tokenStorage) {
        this.session = session;
        this.tokenStorage = tokenStorage;
    }

    public CompletableFuture<Boolean> attemptTokenLogin(String token) {
        this.result = new CompletableFuture<>();
        session.sendMessage(new TokenLoginRequestMessage(token));
        return result;
    }

    public void onTokenLoginSuccess(TokenLoginResponseMessage response) {
        session.setUsername(response.getUsername());
        result.complete(true);
    }

    public void onTokenLoginError() {
        tokenStorage.deleteToken();
        result.complete(false);
    }
}
