package io.github.bvilii.yapper.server.service;

import io.github.bvilii.yapper.server.network.ClientSession;
import io.github.bvilii.yapper.server.repository.SessionRepository;
import io.github.bvilii.yapper.shared.model.message.LoginResponseMessage;
import io.github.bvilii.yapper.shared.model.message.MessageType;
import io.github.bvilii.yapper.shared.model.message.TokenLoginRequestMessage;
import io.github.bvilii.yapper.shared.model.message.TokenLoginResponseMessage;

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
            session.sendMessage(new TokenLoginResponseMessage(MessageType.TOKEN_LOGIN_ERROR, null));
            return;
        }

        String username = usernameOpt.get();

        if(registry.isUserOnline(username)) {
            session.sendMessage(new TokenLoginResponseMessage(MessageType.TOKEN_LOGIN_ERROR, null));
            return;
        }

        session.setUsername(username);
        registry.addSession(username, session);

        sessionRepository.refreshSession(token, Instant.now().plus(Duration.ofDays(30)));

        session.sendMessage(new TokenLoginResponseMessage(MessageType.TOKEN_LOGIN_SUCCESS, username));
    }
}
