package io.github.bvilii.yapper.server.service;

import io.github.bvilii.yapper.server.network.ClientSession;
import io.github.bvilii.yapper.server.repository.SessionRepository;
import io.github.bvilii.yapper.server.repository.UserRepository;
import io.github.bvilii.yapper.shared.model.message.MessageType;
import io.github.bvilii.yapper.shared.model.message.RegisterRequestMessage;
import io.github.bvilii.yapper.shared.model.message.RegisterResponseMessage;
import org.mindrot.jbcrypt.BCrypt;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

public class RegisterService {
    private final UserRepository userRepository;
    private final ClientRegistry registry;
    private final SessionRepository sessionRepository;

    public RegisterService(UserRepository userRepository, ClientRegistry registry, SessionRepository sessionRepository) {
        this.userRepository = userRepository;
        this.registry = registry;
        this.sessionRepository = sessionRepository;
    }

    public void handleRegister(RegisterRequestMessage message, ClientSession session) {

        String username = message.getUsername();

        if (userRepository.existsByUsername(username)) {
            session.sendMessage(new RegisterResponseMessage(MessageType.REGISTER_ERROR, "Username is already taken!", null));

        } else {
            String hashedPassword = BCrypt.hashpw(message.getPassword(), BCrypt.gensalt(12));
            long userId = userRepository.saveUser(username, hashedPassword);
            session.setUsername(username);
            registry.addSession(username, session);


            String sessionToken = UUID.randomUUID().toString();

            Instant expiresAt = Instant.now().plus(Duration.ofDays(30));
            sessionRepository.saveSession(sessionToken, userId, expiresAt);
            session.sendMessage(new RegisterResponseMessage(MessageType.REGISTER_SUCCESS, "User registered successfully!", sessionToken));

        }
    }
}
