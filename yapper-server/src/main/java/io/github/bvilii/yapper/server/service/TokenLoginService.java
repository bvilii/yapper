package io.github.bvilii.yapper.server.service;

import io.github.bvilii.yapper.server.network.ClientSession;
import io.github.bvilii.yapper.server.repository.SessionRepository;
import io.github.bvilii.yapper.shared.model.message.LoginResponseMessage;
import io.github.bvilii.yapper.shared.model.message.MessageType;
import io.github.bvilii.yapper.shared.model.message.TokenLoginRequestMessage;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;

public class TokenLoginService {
    private final ClientRegistry registry;
    private final SessionRepository sessionRepository;

    public TokenLoginService(ClientRegistry registry, SessionRepository sessionRepository) {
        this.registry = registry;
        this.sessionRepository = sessionRepository;
    }


    public void handleTokenLogin(ClientSession session, TokenLoginRequestMessage message) {
        String token = message.getToken();
        Optional<String> usernameOpt = sessionRepository.findUsernameByToken(token);

        if (usernameOpt.isEmpty()) {
            session.sendMessage(new LoginResponseMessage(MessageType.LOGIN_ERROR, "Session expired or doesnt exit!", null));
            return;
        }

        String username = usernameOpt.get();

        if(registry.isUserOnline(username)) {
            session.sendMessage(new LoginResponseMessage(MessageType.LOGIN_ERROR, "User is already online", null));
            return;
        }

        session.setUsername(username);
        registry.addSession(username, session);

        sessionRepository.refreshSession(token, Instant.now().plus(Duration.ofDays(30)));

        session.sendMessage(new LoginResponseMessage(MessageType.LOGIN_SUCCESS, "Session logged in", token));
    }
}
