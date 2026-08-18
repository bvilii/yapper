package io.github.bvilii.yapper.server.service;

import io.github.bvilii.yapper.server.network.ClientSession;
import io.github.bvilii.yapper.server.repository.SessionRepository;
import io.github.bvilii.yapper.server.repository.UserRepository;
import io.github.bvilii.yapper.shared.model.message.LoginRequestMessage;
import io.github.bvilii.yapper.shared.model.message.LoginResponseMessage;
import io.github.bvilii.yapper.shared.model.message.MessageType;
import org.mindrot.jbcrypt.BCrypt;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

public class LoginService {
    private final ClientRegistry registry;
    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;

    public LoginService(ClientRegistry registry, UserRepository userRepository, SessionRepository sessionRepository) {
        this.registry = registry;
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
    }

    public void handleLogin(ClientSession session, LoginRequestMessage message) {
        String username = message.getUsername();
        String password = message.getPassword();


        Optional<String> storedHashedPassword = userRepository.findPasswordHashByUsername(username);
        if (!(storedHashedPassword.isPresent() && BCrypt.checkpw(password, storedHashedPassword.get()))) {
            session.sendMessage(new LoginResponseMessage(MessageType.LOGIN_ERROR, "Invalid username or password", null));
            return;
        }

        if(registry.isUserOnline(username)){
            session.sendMessage(new LoginResponseMessage(MessageType.LOGIN_ERROR, "User is already online!", null));
            return;
        }

        session.setUsername(username);
        registry.addSession(username, session);

        String sessionToken = UUID.randomUUID().toString();
        Optional<Long> id = userRepository.findIdByUsername(username);
        long userId = id.orElseThrow();
        Instant expiresAt = Instant.now().plus(Duration.ofDays(30));

        sessionRepository.saveSession(sessionToken, userId, expiresAt);
        session.sendMessage(new LoginResponseMessage(MessageType.LOGIN_SUCCESS, "Login successful!", sessionToken));
    }
}
