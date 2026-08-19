package io.github.bvilii.yapper.client.service;

import io.github.bvilii.yapper.client.network.ServerSession;
import io.github.bvilii.yapper.client.storage.TokenStorage;
import io.github.bvilii.yapper.shared.model.message.RegisterRequestMessage;
import io.github.bvilii.yapper.shared.model.message.RegisterResponseMessage;

import java.util.concurrent.CompletableFuture;

public class RegisterService {
    private final ServerSession session;
    CompletableFuture<Boolean> registerResult;
    private String username;
    private final TokenStorage tokenStorage;

    public RegisterService(ServerSession session, TokenStorage tokenStorage) {
        this.session = session;
        this.tokenStorage = tokenStorage;
    }


    public CompletableFuture<Boolean> attemptRegister(String username, String password) {
        registerResult = new CompletableFuture<>();
        session.sendMessage(new RegisterRequestMessage(username, password));
        this.username = username;
        return registerResult;
    }

    public void onRegisterError(){
        registerResult.complete(false);
    }

    public void onRegisterSuccess(RegisterResponseMessage response){
        session.setUsername(username);

        String token = response.getToken();
        tokenStorage.saveToken(token);

        registerResult.complete(true);
    }
}